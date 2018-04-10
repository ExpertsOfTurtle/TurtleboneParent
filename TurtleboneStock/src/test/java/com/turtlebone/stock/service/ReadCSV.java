package com.turtlebone.stock.service;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.turtlebone.core.util.IOUtil;
import com.turtlebone.stock.model.AStockModel;

public class ReadCSV {
	@Test
	public void resolveIndicate() {
		String filePath = "C:\\zd_pazq\\T0002\\export\\沪深主要指数20180404.txt";
//		filePath = "C:\\zd_pazq\\T0002\\export\\沪深Ａ股20180404.xls";
		String txt = IOUtil.readTxtFile(filePath, "GBK");
		String lines[] = txt.split("\n");
		System.out.println("总共行数："+ lines.length);
		for (String line :lines) {
			String data[] = line.split("\t");
			AStockModel stock = new AStockModel();
			stock.setCode(data[0]);
			stock.setName(data[1]);
			stock.setChangepercentage(NumberUtils.toDouble(data[2]));
			stock.setPrice(NumberUtils.toDouble(data[3]));
			stock.setChange(NumberUtils.toDouble(data[4]));
			stock.setVolume(NumberUtils.toLong(data[7]));
			stock.setTurnoverrate(NumberUtils.toDouble(data[10]));
			stock.setStartprice(NumberUtils.toDouble(data[11]));
			stock.setTopprice(NumberUtils.toDouble(data[12]));
			stock.setLowprice(NumberUtils.toDouble(data[13]));
			System.out.println(JSON.toJSON(stock));
		}
	}
	
	@Test
	public void resolveStock() {
		String filePath = "C:\\zd_pazq\\T0002\\export\\沪深Ａ股20180404.txt";
		String txt = IOUtil.readTxtFile(filePath, "GBK");
		String lines[] = txt.split("\n");
		System.out.println("总共行数："+ lines.length);
		int i = 0;
		for (String line :lines) {
			String data[] = line.split("\t");
			AStockModel stock = new AStockModel();
			stock.setCode(data[0]);
			stock.setName(data[1]);
			stock.setChangepercentage(NumberUtils.toDouble(data[2]));
			stock.setPrice(NumberUtils.toDouble(data[3]));
			stock.setChange(NumberUtils.toDouble(data[4]));
			stock.setVolume(NumberUtils.toLong(data[7]));
			stock.setTurnoverrate(NumberUtils.toDouble(data[10]));
			stock.setStartprice(NumberUtils.toDouble(data[11]));
			stock.setTopprice(NumberUtils.toDouble(data[12]));
			stock.setLowprice(NumberUtils.toDouble(data[13]));
			stock.setPreprice(NumberUtils.toDouble(data[14]));
			stock.setPe(NumberUtils.toDouble(data[15]));
			stock.setQrr(NumberUtils.toDouble(data[17]));
			stock.setCategory(data[18]);
			stock.setAmplitude(NumberUtils.toDouble(data[20]));
			stock.setSell(NumberUtils.toLong(data[22]));
			stock.setBuy(NumberUtils.toLong(data[23]));
			i++;
			if (i % 100 == 47) {
				System.out.println(JSON.toJSON(stock));
			}
		}
	}
}
