package com.turtlebone.stock.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class ReadExcel {
	
	private Workbook workBook = null;
	
	@Test
	public void test() {
		String fileName = "C:\\zd_pazq\\T0002\\export\\沪深Ａ股20180404.xls";
		readFromFile(fileName);
		if (workBook == null) {
			System.out.println("Open excel file fail");
			return;
		}
		Sheet sheet = workBook.getSheetAt(0);
		System.out.println("getFirstRowNum:" + sheet.getFirstRowNum());
		System.out.println("getLastRowNum:" + sheet.getLastRowNum());
	}
	
	protected void readFromFile(String fileName) {
		try {
			InputStream stream = new FileInputStream(fileName);
			if (fileName.endsWith("xls")) {
				workBook = new HSSFWorkbook(stream);
			} else {
				workBook = new XSSFWorkbook(stream);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
