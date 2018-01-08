package com.turtlebone.core.service.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.turtlebone.core.service.EmailService;
import com.turtlebone.core.util.IOUtil;
import com.turtlebone.core.util.StringUtil;

@Service
public class EmailServiceImpl implements EmailService {
	private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	private static final String ACCESS_KEY = "LTAIv5K5cvw46q0U";
	private static final String SECRET = "WEG1KpNAu8hYbrv7YHu1fHyouBQHAF";
	
	@Autowired
	private Environment env;

	@Value("${constants.emailTemplatePath}")
	private String emailTemplatePath;
	
	@Override
	public String sendEmail(List<String> addressList, String title, String template, String alias) {
		String rs = "OK";
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY, SECRET);
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName("single@turtlebone.top");
			request.setFromAlias(alias);
			request.setAddressType(1);
			request.setTagName("turtle");
			request.setReplyToAddress(true);
			String address = getAddress(addressList);
			request.setToAddress(address);
			request.setSubject(title);
			/*String filePath = getFilePath(template);
			if (StringUtil.isEmpty(filePath)) {
				return "FAIL";
			}
			String html = IOUtil.readTxtFile(filePath);*/
			request.setHtmlBody(template);
			
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
			logger.debug("Email request id:{}", httpResponse.getRequestId());
			logger.info("Sending email to {}", address);
		} catch (ServerException e) {
			e.printStackTrace();
			rs = "Fail";
		} catch (ClientException e) {
			e.printStackTrace();
			rs = "Fail";
		}
		return rs;
	}
	private String getAddress(List<String> addressList) {
		StringBuffer sb = new StringBuffer();
		for (String add : addressList) {
			sb.append(",").append(add);
		}
		return sb.toString().substring(1);
	}
	private String getFilePath(String template) {
		File file = new File(template);
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		file = new File(emailTemplatePath + template);
		if (file.exists()) {
			return file.getAbsolutePath();
		} else {
			return null;
		}
	}
}
