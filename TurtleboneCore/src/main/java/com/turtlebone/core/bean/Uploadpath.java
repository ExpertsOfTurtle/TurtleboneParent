package com.turtlebone.core.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Data;
@Component  
@ConfigurationProperties(prefix="mypath")
@Data
public class Uploadpath {
	private String  imgpath;
}

