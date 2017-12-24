package com.test.turtlebone;

import java.util.List;

import org.junit.Test;

import com.turtlebone.codeforces.model.CFSubmissionModel;
import com.turtlebone.codeforces.service.FetchSubmissionsService;
import com.turtlebone.codeforces.service.impl.FetchSubmissionsServiceImpl;

public class ParseSubmission {
	@Test
	public void doParse() {
		FetchSubmissionsService service = new FetchSubmissionsServiceImpl();
		List<CFSubmissionModel> list = service.fetchResult("scorpiowf", 1, 50);
	}
}
