package com.turtlebone.core.velocity;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


public class VelocityGenerator {
	public static String getVMResult(String vmName, Map<String, Object> model) {
		VelocityContext ctx = new VelocityContext();
		for (Entry<String, Object> entry : model.entrySet()) {
			String key = entry.getKey();
			Object obj = entry.getValue();
			ctx.put(key, obj);
		}
		
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");

	/*	boolean isLocal = EnvIdentify.isLocalDebug;
		if (isLocal && !vmName.endsWith("json.vm")) {
			String rootPath = "E:\\Workspace\\BankSim\\Bronze\\webapp\\";
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
			ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, rootPath);
		}*/
		ve.init();

		Template t = ve.getTemplate(vmName);

		StringWriter sw = new StringWriter();
		t.merge(ctx, sw);

		return sw.toString();
	}
}
