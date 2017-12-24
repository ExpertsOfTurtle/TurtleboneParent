package com.turtlebone.codeforces.service;

import java.util.List;

import com.turtlebone.codeforces.model.CFSubmissionModel;

public interface FetchSubmissionsService {
	public List<CFSubmissionModel> fetchResult(String username, int from, int count);
}
