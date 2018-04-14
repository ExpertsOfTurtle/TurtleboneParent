


package com.turtlebone.dairy.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.turtlebone.dairy.service.DairyService;
import com.turtlebone.dairy.model.DairyModel;

import java.util.Base64;
import java.util.Date;

public class DairyServiceTest{

	@Autowired
	private DairyService dairyService;

	@Test
	public void testCreate(){
		byte[] data = Base64.getDecoder().decode("PHA+REREQkJCQ0NDPC9wPg==");
		System.out.println(new String(data));
	}
}
