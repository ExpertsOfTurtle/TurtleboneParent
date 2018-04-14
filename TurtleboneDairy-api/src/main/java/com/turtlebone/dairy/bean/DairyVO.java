package com.turtlebone.dairy.bean;

import java.util.Date;

import lombok.Data;

@Data
public class DairyVO {
	private Integer id;
	private String title;
	private String creator;
	private Date createtime;
	private Date updatetime;
	private Integer type;
	private Integer subtype;
	private Date expiretime;
	private String content;
}
