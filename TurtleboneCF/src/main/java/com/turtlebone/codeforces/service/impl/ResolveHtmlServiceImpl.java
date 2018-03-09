package com.turtlebone.codeforces.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.turtlebone.codeforces.service.ResolveHtmlService;

@Service
public class ResolveHtmlServiceImpl implements ResolveHtmlService {
	private Document root;
	private Element head;
	private Element body;
	
	@Override
	public String resolve (String html) throws Exception {
		init(html);
		resolveHead();
		resolveBody();
		return root.html();
	}

	private void init(String html) throws Exception {
		root = Jsoup.parse(html);
		head = root.getElementsByTag("head").first();
		body = root.getElementsByTag("body").first();
	}


	private void resolveBody() {
		for (Element e : body.children()) {
			resolveElement(e);
		}
	}

	private void resolveElement(Element e) {
		if ("img".equalsIgnoreCase(e.tagName()) || "script".equalsIgnoreCase(e.tagName())) {
			e.remove();
			return;
		}
		for (Element es : e.children()) {
			resolveElement(es);
		}
	}

	private void resolveHead() {
		for (Element e : head.getAllElements()) {
			if ("script".equalsIgnoreCase(e.tagName())) {
				e.remove();
			} else if ("link".equalsIgnoreCase(e.tagName())) {
				resolveLink(e);
			}
		}
	}

	private void resolveLink(Element e) {
		String type = e.attr("type");
		if (!"text/css".equals(type)) {
			e.remove();
			return;
		}
		String href = e.attr("href");
		int idx = href.lastIndexOf("/");
		href = href.substring(idx + 1, href.length());
		e.attr("href", href);
	}
}
