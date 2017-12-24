package com.turtlebone.core.statistics.service;

import java.util.List;

public class FilterConfig {
	protected List<FilterCriteria> filters;
	protected String sumBy;
	protected String sumByType;
	protected String separateBy;
	protected String fromDate;
	protected String toDate;
	public List<FilterCriteria> getFilters() {
		return filters;
	}
	public void setFilters(List<FilterCriteria> filters) {
		this.filters = filters;
	}
	public String getSumBy() {
		return sumBy;
	}
	public void setSumBy(String sumBy) {
		this.sumBy = sumBy;
	}
	public String getSumByType() {
		return sumByType;
	}
	public void setSumByType(String sumByType) {
		this.sumByType = sumByType;
	}
	public String getSeparateBy() {
		return separateBy;
	}
	public void setSeparateBy(String separateBy) {
		this.separateBy = separateBy;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}
