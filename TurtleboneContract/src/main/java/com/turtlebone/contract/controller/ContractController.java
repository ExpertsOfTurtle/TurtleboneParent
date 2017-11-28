package com.turtlebone.contract.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.turtlebone.contract.bean.CreateContractRequest;
import com.turtlebone.contract.bean.ModifyContractRequest;
import com.turtlebone.contract.bean.SignContractRequest;
import com.turtlebone.contract.common.ContractType;
import com.turtlebone.contract.common.IActivityAction;
import com.turtlebone.contract.common.IContractStatus;
import com.turtlebone.contract.common.ISignType;
import com.turtlebone.contract.exception.ContractException;
import com.turtlebone.contract.model.ContractActivityModel;
import com.turtlebone.contract.model.ContractModel;
import com.turtlebone.contract.model.UserModel;
import com.turtlebone.contract.service.ContractActivityService;
import com.turtlebone.contract.service.ContractService;
import com.turtlebone.contract.service.UserService;
import com.turtlebone.contract.util.DateUtil;
import com.turtlebone.contract.util.StringUtil;
import com.turtlebone.contract.util.UUIDUtil;
@Controller
@EnableAutoConfiguration
@RequestMapping(value="/contract")
public class ContractController {
	private static Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractActivityService activityService;
	@Autowired
	private UserService userService;
		
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createContract(@RequestBody CreateContractRequest request) {
		logger.info("Creating contract by [{}]: title=[{}]", request.getCreator(), request.getTitle());
		
		try {
			validatCreateRequest(request);
		} catch (ContractException e) {
			logger.error(e.getErrorMesage());
			return ResponseEntity.ok(e.getErrorMesage());
		};
		
		ContractModel contract = new ContractModel();
		Date currentTime = new Date();
		contract.setContractContent(request.getContent());
		contract.setContractName(request.getTitle());
		contract.setCreateBy(request.getCreator());
		contract.setCreatetime(currentTime);
		contract.setContractType(request.getType());
		contract.setContractStatus(IContractStatus.PENDING);
		contract.setContractNo(UUIDUtil.generateId(6));
		contract.setContractParty("");
		Date effectiveDate = DateUtil.parse(request.getEffectiveDate());
		Date expiredDate = DateUtil.parse(request.getExpiredDate());
		if (effectiveDate == null) {
			effectiveDate = DateUtil.getNDaysLaterDate(0, "yyyy-MM-dd");
		}
		if (expiredDate == null) {
			expiredDate = DateUtil.getNDaysLaterDate(7, "yyyy-MM-dd");
		}
		contract.setEffectiveDate(effectiveDate);
		contract.setExpiredDate(expiredDate);
		
		int contractId = contractService.create(contract);
		
		for (String username : request.getPartyList()) {
			ContractActivityModel activity = new ContractActivityModel();
			activity.setContactid(contractId);
			activity.setUsername(username);
			activity.setAction(IActivityAction.PENDING);
			activityService.create(activity);
		}
		
		logger.info("Contract[{}] created, id={}", contract.getContractNo(), contractId);
		return ResponseEntity.ok(contract);
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> modifyContract(@RequestBody ModifyContractRequest request) {
		logger.info("Modify contract by: title=[{}]", request.getTitle());
		
		Integer id = request.getContractId();
		ContractModel contract = contractService.findByPrimaryKey(id);
		if (contract == null) {
			logger.error("no such contract id:{}", id);
			return ResponseEntity.ok("contract id not exist");
		}
		if (!request.getUsername().equals(contract.getCreateBy())) {
			logger.error("This contract [{}] is not created by you!", id);
			return ResponseEntity.ok("Contract can be modified by creator only");
		}
		
		//如果其他人已经进行了操作，那么不允许修改
		ContractActivityModel myActivity = null;
		List<ContractActivityModel> list = activityService.selectBulkSignActivity(id, null);
		for (ContractActivityModel act : list) {
			if (act.getAction() != IActivityAction.PENDING && 
				!request.getUsername().equals(act.getUsername())) {
				logger.error("Contract[{}] has been taken action by {}", id, act.getUsername());
				return ResponseEntity.ok("contract has been signed/rejected"); 
			}
			if (request.getUsername().equals(act.getUsername())) {
				myActivity = act;
			}
		}
		
		if (contract.getContractStatus() != IContractStatus.PENDING) {
			logger.error("Contract status is not pending, not allow to modify");
			return ResponseEntity.ok("Contract status is not pending, not allow to modify");
		}
		
		//重新更新自己的状态为pending
		if (myActivity != null && myActivity.getAction() != IActivityAction.PENDING) {
			myActivity.setAction(IActivityAction.PENDING);
			activityService.updateByPrimaryKey(myActivity);
		}
		
		contract.setContractContent(request.getContent());
		contract.setContractName(request.getTitle());
		Date effectiveDate = DateUtil.parse(request.getEffectiveDate());
		Date expiredDate = DateUtil.parse(request.getExpiredDate());
		if (effectiveDate != null) {
			contract.setEffectiveDate(effectiveDate);	
		}
		if (expiredDate != null) {
			contract.setExpiredDate(expiredDate);	
		}		
		
		contractService.updateByPrimaryKey(contract);
		
		logger.info("Contract[{}] modified, id={}", contract.getContractNo(), id);
		return ResponseEntity.ok(contract);
	}
	
	@RequestMapping(value="/sign", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> sign(@RequestBody SignContractRequest request) {
		try {
			validateSignRequest(request);
		} catch (ContractException e) {
			logger.error(e.getErrorMesage());
			return ResponseEntity.ok(e.getErrorMesage());
		}
		Integer contractId = request.getContractId();
		ContractModel contract = contractService.findByPrimaryKey(contractId);
		if (contract == null) {
			logger.error("Contract[id={}] not exitst", contractId);
			return ResponseEntity.ok("Contract not exitst");
		}
		
		//判断状态是不是PENDING，如果是才可以进行Accept或者Decline操作
		short status = contract.getContractStatus();
		if (status != IContractStatus.PENDING) {
			logger.error("Contract[id={}] is not in PENDING status", contractId);
			return ResponseEntity.ok("Contract is not int PENDING status!");
		}
		
		ContractActivityModel activity = activityService.selectSignActivity(contractId, request.getUsername());
		if (activity == null) {
			logger.error("user[{}] not have authority on contract[{}]", request.getUsername(), contractId);
			return ResponseEntity.ok("Not authority on accepting/declining contract");
		}
		int action = activity.getAction();
		if (action != IActivityAction.PENDING) {
			logger.error("User[{}] has already taken action on contract[{}]", request.getUsername(), contractId);
			return ResponseEntity.ok("User has taken action on contract before");
		}
		if ("ACCEPT".equalsIgnoreCase(request.getActionType())) {
			activity.setAction(IActivityAction.ACCEPT);
		} else if ("DECLINE".equalsIgnoreCase(request.getActionType())) {
			activity.setAction(IActivityAction.DECLINE);
		} else {
			logger.error("No such action {}", request.getActionType());
			return ResponseEntity.ok("No such action");
		}
		
		//Update contract status
		List<ContractActivityModel> caList = activityService.selectBulkSignActivity(contractId, null);
		short contractStatus = getContractStatus(caList);
		if (contract.getContractStatus().shortValue() != contractStatus) {
			contract.setContractStatus(contractStatus);
			contractService.updateByPrimaryKey(contract);
		}
		
		activityService.updateByPrimaryKeySelective(activity);
		
		return ResponseEntity.ok(activity);
	}
	
	@RequestMapping(value = "/list")
	public String listAll(Map<String, Object> model) {
		List<ContractModel> list = contractService.selectAll();
		model.put("list", list);
		return "contract/ajax/list";
	}
	
	@RequestMapping(value = "/details/{id}")
	public String viewDetails(Map<String, Object> model, @PathVariable("id") Integer id) {
		ContractModel detail = contractService.findByPrimaryKey(id);
		model.put("detail", detail);
		return "contract/ajax/details";
	}
	
	@RequestMapping(value = "/editDetail/{id}")
	public String viewDetailsForEdit(Map<String, Object> model, @PathVariable("id") Integer id) {
		ContractModel detail = contractService.findByPrimaryKey(id);
		model.put("detail", detail);
		return "contract/ajax/edit";
	}
	
	private boolean validatCreateRequest(CreateContractRequest request) throws ContractException{
		if (StringUtil.isEmpty(request.getCreator())) {
			throw new ContractException("Missing creator");
		} else if (StringUtil.isEmpty(request.getTitle())) {
			throw new ContractException("Missing title");
		} else if (StringUtil.isEmpty(request.getContent())) {
			throw new ContractException("Missing content");
		} else if (StringUtil.isEmpty(request.getType())) {
			throw new ContractException("Missing contract type");
		} else if (!validateContractType(request.getType())) {
			throw new ContractException("Contract type not exist");
		}
		if (request.getPartyList() == null || request.getPartyList().size() == 0) {
			throw new ContractException("PartyList is empty");
		}
		List<UserModel> userList = userService.selectByUserList(request.getPartyList());
		if (userList.size() != request.getPartyList().size()) {
			throw new ContractException("Some user not exist");
		}
		return true;
	}
	private boolean validateSignRequest(SignContractRequest request) throws ContractException {
		if (request.getContractId() == null) {
			throw new ContractException("Missing contractId");
		}
		if (request.getUsername() == null) {
			throw new ContractException("Missing username");
		}
		if (request.getActionType() == null) {
			throw new ContractException("Missing actionType");
		}
		if (!ISignType.ACCEPT.equalsIgnoreCase(request.getActionType()) && 
			!ISignType.DECLINE.equalsIgnoreCase(request.getActionType())) {
			throw new ContractException("Incorrect actionType");
		}
		return true;
	}
	private boolean validateContractType(String type) {
		for (ContractType ct : ContractType.values()) {
			if (ct.name().equals(type)) {
				return true;
			}
		}
		return false;
	}
	private short getContractStatus(List<ContractActivityModel> caList) {
		if (caList == null || caList.size() == 0) {
			logger.warn("caList is empty!");
			return IContractStatus.CANCEL;
		}
		boolean hasPending = false;
		boolean hasReject = false;
		for (ContractActivityModel ca : caList) {
			if (ca.getAction().intValue() == IActivityAction.PENDING) {
				hasPending = true;
			} else if (ca.getAction().intValue() == IActivityAction.DECLINE) {
				hasReject = true;
				break;
			}
		}
		if (hasReject) {
			return IContractStatus.REJECTED;
		} else if (hasPending) {
			return IContractStatus.PENDING;
		} else {
			return IContractStatus.SIGNED;
		}
	}
}
