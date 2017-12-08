package com.turtlebone.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class SendHTTPUtil {
	private static Logger logger = LoggerFactory.getLogger(SendHTTPUtil.class);
	
	public static String callApiServer(String url, String method, String content, Map<String, String> header) throws Exception {

//		url = URLEncoder.encode(url, "UTF-8");
		StringBuilder urlSb = new StringBuilder(url);
		logger.debug("Sending {} to {}", method, url);

		URL obj = new URL(urlSb.toString());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod(method);
		con.setDoInput(true);
		con.setDoOutput(true);

		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept", "*/*");
		if (header != null) {
			for (Entry<String, String> entry : header.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		
		int len = content.length();
		if (len > 0) {
			byte[] buffer = content.getBytes("UTF-8");

			try (OutputStream outputStream = con.getOutputStream();
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8")) {
				outputStreamWriter.write(new String(buffer, "UTF-8"));
				outputStreamWriter.flush();
			}
		}

		int responseCode = con.getResponseCode();
		logger.debug("ReponseCode={}", responseCode);

		StringBuffer resSb = new StringBuffer();
		if (responseCode == HttpStatus.OK.value() || responseCode == HttpStatus.CREATED.value()) {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					resSb.append(inputLine);
				}
			}

		}
		con.disconnect();

		return resSb.toString();
	}
}
