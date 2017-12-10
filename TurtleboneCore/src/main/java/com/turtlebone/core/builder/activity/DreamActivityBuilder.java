package com.turtlebone.core.builder.activity;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.turtlebone.core.bean.Uploadpath;
import com.turtlebone.core.enums.ActivityType;
import com.turtlebone.core.enums.sudoku.SudokuLevel;
import com.turtlebone.core.model.ActivityModel;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.StringUtil;

@Component
public class DreamActivityBuilder {
	private static Logger logger = LoggerFactory.getLogger(DreamActivityBuilder.class);
	
	public ActivityModel build(String username, String datetime, String content, String dreampic) {
		logger.debug("[Dream]username={},datetime={},content={}", username, datetime, content);
		ActivityModel model = new ActivityModel();
		model.setUsername(username);
		if (StringUtil.isEmpty(datetime)) {
			model.setDatetime(DateUtil.getDateTime());
		} else {
			model.setDatetime(datetime);
		}
		model.setType(ActivityType.DREAM.name());
		model.setResult(content);
		model.setStrresult3(dreampic);
		
		String ct = content.length() > 30 ? content.substring(0, 30) + "..." : content;
		String description = String.format("[%s] [发梦]了，内容：[%s]", 
				username, ct);
		model.setDescription(description);
		logger.info(description);
		return model;
	}
	
	/**
	 * 上传图片
	 * @param file
	 * @param params
	 * @return
	 * @throws Exception
	 */
//	@Autowired
//	protected Environment env;
//	@Value("${mypath.imgpath}")
//	protected String localPath;
	@Autowired
	public Uploadpath uploadpath;
	public boolean upload(MultipartFile file, JSONObject params) throws Exception{
	    //过滤合法的文件类型
		
	    String fileName = file.getOriginalFilename();
	    String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
	    String allowSuffixs = "gif,jpg,jpeg,bmp,png,ico";
	    if(allowSuffixs.indexOf(suffix.toLowerCase()) == -1){
	        params.put("resultStr", "not support the file type!");
	        return false;
	    }
	    String localPath =uploadpath.getImgpath();
	    
	    System.out.println("===+++++"+localPath);
	    //创建新目录
	    File dir = new File(localPath);
	    if(!dir.exists()){
	        dir.mkdirs();
	    }
	    
	    //创建新文件
	    String newFileName = new Date().getTime()+fileName;
	    File f = new File(dir.getPath() + File.separator + newFileName);
	    
	    //将输入流中的数据复制到新文件
	    FileUtils.copyInputStreamToFile(file.getInputStream(), f);
	    f.setReadable(true, false);//设置为可读
	    
	    params.put("resultStr", localPath + newFileName);
	    
	    return true;
	}
	
}
