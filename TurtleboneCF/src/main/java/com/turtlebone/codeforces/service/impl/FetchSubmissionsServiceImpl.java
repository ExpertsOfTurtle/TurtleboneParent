package com.turtlebone.codeforces.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.core.util.DateUtil;
import com.turtlebone.core.util.SendHTTPUtil;

@Service
public class FetchSubmissionsServiceImpl implements FetchSubmissionsService {

	@Override
	public List<CFSubmissionModel> fetchResult(String username, int from, int count) {
		List<CFSubmissionModel> rs = new ArrayList<>();
		String url = String.format("http://codeforces.com/api/user.status?handle=%s&from=%d&count=%d", username, from,
				count);
		String result = "";
		try {
			result = SendHTTPUtil.callApiServer(url, "GET", "", null);
			JSONObject obj = JSON.parseObject(result, JSONObject.class);
			JSONArray list = obj.getJSONArray("result");
			for (int i = 0; i < list.size(); i++) {
				JSONObject json = list.getJSONObject(i);
				CFSubmissionModel model = convert(json, username);
				rs.add(model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	private CFSubmissionModel convert(JSONObject obj, String username) {
		CFSubmissionModel model = new CFSubmissionModel();
		model.setId(obj.getInteger("id"));
		model.setContestid(obj.getInteger("contestId"));
		JSONObject problem = obj.getJSONObject("problem");
		if (problem != null) {
			model.setProblemindex(problem.getString("index"));
			JSONArray tagsArr = problem.getJSONArray("tags");
			if (tagsArr != null && tagsArr.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < tagsArr.size(); i++) {
					sb.append(tagsArr.getString(i)).append("|");
				}
				model.setTags(sb.toString().substring(0, sb.length() - 1));
			}			
		}
		model.setUsername(username);
		model.setResult(obj.getString("verdict"));
		long time = obj.getLongValue("creationTimeSeconds");
		time *= 1000;
		model.setSubmittime(DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"));
		return model;
	}
}
