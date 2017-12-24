package com.turtlebone.core.statistics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.turtlebone.core.statistics.bean.StatisticsObject;
import com.turtlebone.core.statistics.bean.StatisticsResult;
import com.turtlebone.core.util.DateUtil;


public class DataStatisticsUtil {
	public static List<JSONObject> filterData(List list, FilterConfig filterConfig)
			throws Exception, SecurityException {
		List<FilterCriteria> filters = filterConfig.getFilters();

		JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
		List<JSONObject> resultArray = new ArrayList<>();

		final String sumBy = filterConfig.getSumBy();
		final String seperateBy = filterConfig.getSeparateBy();
		// Filter
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			if (!StringUtils.isEmpty(sumBy) && StringUtils.isEmpty(obj.getString(sumBy))) {
				// 如果需要求和，但是sumBy的值又是空，那么这一行数据扔弃
				continue;
			}
			if (!StringUtils.isEmpty(seperateBy) && StringUtils.isEmpty(obj.getString(seperateBy))) {
				// 如果需要分组，但是seperateBy的值又是空，那么这一行数据扔弃
				continue;
			}
			if (isMatch(obj, filters)) {
				resultArray.add(obj);
			}
		}

		return resultArray;
	}
	public static boolean mergeData(JSONObject result, JSONObject obj) {
		if (!hasSameStructor(result, obj)) {
			return false;
		}
		JSONArray yArr1 = result.getJSONArray("yArr");
		JSONArray yArr2 = obj.getJSONArray("yArr");
		if (yArr1 == null) {
			yArr1 = new JSONArray();
		}
		if (yArr2 != null) {
			for (int i = 0; i < yArr2.size(); i++) {
				JSONObject json = yArr2.getJSONObject(i);
				String label = json.getString("labels");
				boolean flag = false;
				for (int j = 0; j < yArr1.size(); j++) {
					JSONObject rsJson = yArr1.getJSONObject(j);
					String rsLabel = rsJson.getString("labels");
					if (label.equals(rsLabel)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					yArr1.add(json);
				}
			}
		}
		return true;
	}
	public static boolean hasSameStructor(JSONObject obj1, JSONObject obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		JSONArray xLbl1 = obj1.getJSONArray("xLabel");
		JSONArray xLbl2 = obj2.getJSONArray("xLabel");
		if (xLbl1 == null || xLbl2 == null) {
			return false;
		}
		if (xLbl1.size() != xLbl2.size()) {
			return false;
		}
		for (int i = 0; i < xLbl1.size(); i++) {
			String lbl1 = xLbl1.getString(i);
			String lbl2 = xLbl2.getString(i);
			if (!lbl1.equals(lbl2)) {
				return false;
			}
		}
		return true;
	}

	public static StatisticsResult groupData(List list, FilterConfig filterConfig)
			throws Exception, SecurityException {
		StatisticsResult result = new StatisticsResult();

		List<JSONObject> listAfterFilter = filterData(list, filterConfig);

		// 按日期排序：从早到晚
		final String sumBy = filterConfig.getSumBy();
		final String sumByType = filterConfig.getSumByType();
		final String seperateBy = filterConfig.getSeparateBy();

		Collections.sort(listAfterFilter, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				String s1 = o1.getString(sumBy);
				String s2 = o2.getString(sumBy);
				return s1.compareTo(s2);
			}
		});
//		System.out.println(JSONArray.fromObject(listAfterFilter).toString());

		// 按"seperateBy"分组
		long startTime;
		long endTime;
		String XLABEL_PATTERN;
		
		String fromDate = StringUtils.isEmpty(filterConfig.getFromDate()) ? listAfterFilter.get(0).getString(sumBy) : filterConfig.getFromDate();
		String toDate = StringUtils.isEmpty(filterConfig.getToDate()) ? listAfterFilter.get(0).getString(sumBy) : filterConfig.getToDate();
		long eariliest = DateUtil.parse(fromDate).getTime();
		long latest = DateUtil.parse(toDate).getTime();
		Map<String, List> datasByLabels = new HashMap<String, List>();
		for (JSONObject json : listAfterFilter) {
			String label = "";
			if (StringUtils.isEmpty(seperateBy)) {
				label = "default";
			} else {
				label = json.getString(seperateBy);
			}
			Object obj = datasByLabels.get(label);
			List arr = null;
			if (obj == null) {
				arr = new ArrayList<String>();
			} else {
				arr = (List) obj;
			}
			Date date = null;
			try {
				date = DateUtil.parse(json.getString(sumBy), "yyyy-MM-dd");
				arr.add(date.getTime());
				if (date.getTime() < eariliest) {
					eariliest = date.getTime();
				}
				if (date.getTime() > latest) {
					latest = date.getTime();
				}
				datasByLabels.put(label, arr);
			} catch (ParseException e) {
			}
		}
		switch (sumByType) {
		case "WEEK":
			startTime = getMonday(eariliest);
//			endTime = getSaturday(latest);
			endTime = getFirstDayOfNextWeek(latest);
			endTime = latest;
			XLABEL_PATTERN = "yy/MM/dd";
			break;
		case "MONTH":
			startTime = getFirstDayOfMonth(eariliest);
			endTime = getFirstDayOfNextMonth(latest);
			XLABEL_PATTERN = "yy/MM";
			break;
		case "DAY":
		default:
			startTime = eariliest;
			endTime = latest+1;
			XLABEL_PATTERN = "yy/MM/dd";
			break;
		}

		List<StatisticsObject> yArr = new ArrayList<>();

		// x轴坐标label
		List<String> xLabel = new ArrayList<String>();
		Calendar cl1 = Calendar.getInstance();
		cl1.setTime(new Date(startTime));
		Calendar cl2 = Calendar.getInstance();
		cl2.setTime(new Date(startTime));
		if ("WEEK".equals(sumByType)) {
			updateToFirstDayOfNextWeek(cl2);
		} else if ("MONTH".equals(sumByType)) {
			updateToFirstDayOfNextMonth(cl2);
		} else if ("DAY".equals(sumByType)) {
			cl2.add(Calendar.DATE, 1);
		} else {
			cl2.setTime(new Date(endTime));
		}
		while (cl1.getTime().getTime() <= endTime) {
			xLabel.add(DateUtil.format(cl1.getTime(), XLABEL_PATTERN));
			cl1 = Calendar.getInstance();
			cl1.setTime(cl2.getTime());
			if ("WEEK".equals(sumByType)) {
				updateToFirstDayOfNextWeek(cl2);
			} else if ("MONTH".equals(sumByType)) {
				updateToFirstDayOfNextMonth(cl2);
			} else if ("DAY".equals(sumByType)) {
				cl2.add(Calendar.DATE, 1);
			} else {
				break;
			}
		}

		for (Entry<String, List> entry : datasByLabels.entrySet()) {
			String label = entry.getKey();
			List<Long> arr = (List<Long>) entry.getValue();
			cl1.setTime(new Date(startTime));
			cl2.setTime(new Date(startTime));
			if ("WEEK".equals(sumByType)) {
				updateToFirstDayOfNextWeek(cl2);
			} else if ("MONTH".equals(sumByType)) {
				updateToFirstDayOfNextMonth(cl2);
			} else if ("DAY".equals(sumByType)) {
				cl2.add(Calendar.DATE, 1);
			} else {
				cl2.setTime(new Date(endTime));
			}
			int count = 0;
			List<Integer> data = new ArrayList<Integer>();
			int i = 0;
			long t;
			while (i < arr.size()) {
				t = arr.get(i);
				if (cl2.getTime().getTime() <= t) {
					data.add(count);
					cl1 = Calendar.getInstance();
					cl1.setTime(cl2.getTime());
					if ("WEEK".equals(sumByType)) {
						updateToFirstDayOfNextWeek(cl2);
					} else if ("MONTH".equals(sumByType)) {
						updateToFirstDayOfNextMonth(cl2);
					} else if ("DAY".equals(sumByType)) {
						cl2.add(Calendar.DATE, 1);
					} 
					count = 0;
				} else if (cl1.getTime().getTime() <= t && t < cl2.getTime().getTime()) {
					count++;
					i++;
				} else {
					data.add(count); 
					count = 0;
					i++;
				}
			}
			while (cl1.getTime().getTime() <= endTime) {
				data.add(count);
				count = 0;
				cl1 = Calendar.getInstance();
				cl1.setTime(cl2.getTime());
				if ("WEEK".equals(sumByType)) {
					updateToFirstDayOfNextWeek(cl2);
				} else if ("MONTH".equals(sumByType)) {
					updateToFirstDayOfNextMonth(cl2);
				} else if ("DAY".equals(sumByType)) {
					cl2.add(Calendar.DATE, 1);
				}  else {
					break;
				}
			}
			StatisticsObject y = new StatisticsObject();
			y.setLabel(label);
			y.setData(data);			
			yArr.add(y);
		}
		result.setLabels(xLabel);
		result.setList(yArr);
		return result;
	}

	private static boolean isMatch(JSONObject obj, List<FilterCriteria> filterConfig) {
		for (FilterCriteria filter : filterConfig) {
			if (!isMatch(obj, filter)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isMatch(JSONObject obj, FilterCriteria filter) {
		boolean flag = false;
		switch (filter.getType()) {
		case "=":
			flag = handleEquals(obj, filter);
			break;
		case "!=":
			flag = handleNotEquals(obj, filter);
			break;
		case "in":
			flag = handleIn(obj, filter);
			break;
		case "not in":
			flag = handleNotIn(obj, filter);
			break;
		case "<":
			flag = handleLessThan(obj, filter);
			break;
		case "<=":
			flag = handleLessThanOrEquals(obj, filter);
			break;
		case ">":
			flag = handleMoreThan(obj, filter);
			break;
		case ">=":
			flag = handleMoreThanOrEquals(obj, filter);
			break;
		case "contains":
			flag = handleContains(obj, filter);
			break;
		default:
			flag = true;
		}
		return flag;
	}

	private static boolean handleEquals(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValue = filter.getFiledValue();
		return expectedValue.equals(value);
	}

	private static boolean handleNotEquals(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValue = filter.getFiledValue();
		return !expectedValue.equals(value);
	}

	private static boolean handleIn(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValues[] = filter.getFiledValue().split(",");
		for (String expectedValue : expectedValues) {
			if (expectedValue.equals(value)) {
				return true;
			}
		}
		return false;
	}

	private static boolean handleNotIn(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValues[] = filter.getFiledValue().split(",");
		for (String expectedValue : expectedValues) {
			if (expectedValue.equals(value)) {
				return false;
			}
		}
		return true;
	}

	private static boolean handleLessThan(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValue = filter.getFiledValue();
		if (StringUtils.isNumeric(value) && StringUtils.isNumeric(expectedValue)) {
			Double d1 = Double.parseDouble(value);
			Double d2 = Double.parseDouble(expectedValue);
			return d1 < d2;
		} else {
			int rt = value.compareTo(expectedValue);
			return rt < 0;
		}
	}

	private static boolean handleLessThanOrEquals(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValue = filter.getFiledValue();
		if (StringUtils.isNumeric(value) && StringUtils.isNumeric(expectedValue)) {
			Double d1 = Double.parseDouble(value);
			Double d2 = Double.parseDouble(expectedValue);
			return d1 < d2;
		} else {
			int rt = value.compareTo(expectedValue);
			return rt <= 0;
		}
	}

	private static boolean handleMoreThan(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValue = filter.getFiledValue();
		if (StringUtils.isNumeric(value) && StringUtils.isNumeric(expectedValue)) {
			Double d1 = Double.parseDouble(value);
			Double d2 = Double.parseDouble(expectedValue);
			return d1 < d2;
		} else {
			int rt = value.compareTo(expectedValue);
			return rt > 0;
		}
	}
	private static boolean handleMoreThanOrEquals(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValue = filter.getFiledValue();
		if (StringUtils.isNumeric(value) && StringUtils.isNumeric(expectedValue)) {
			Double d1 = Double.parseDouble(value);
			Double d2 = Double.parseDouble(expectedValue);
			return d1 < d2;
		} else {
			int rt = value.compareTo(expectedValue);
			return rt >= 0;
		}
	}
	private static boolean handleContains(JSONObject obj, FilterCriteria filter) {
		String value = (String) obj.get(filter.getFiledName());
		String expectedValue = filter.getFiledValue();
		return value.contains(expectedValue);
	}

	protected static long getFirstDayOfMonth(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(time));
		instance.set(Calendar.DATE, 1);
		Date lastDay = instance.getTime();

		return lastDay.getTime();
	}

	protected static long getFirstDayOfNextMonth(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(time));
		instance.add(Calendar.MONTH, 1);
		instance.set(Calendar.DATE, 1);
		Date lastDay = instance.getTime();

		return lastDay.getTime();
	}

	protected static void updateToFirstDayOfNextMonth(Calendar cl) {
		cl.add(Calendar.MONTH, 1);
		cl.set(Calendar.DATE, 1);
	}

	protected static void updateToFirstDayOfNextWeek(Calendar cl) {
		cl.add(Calendar.DATE, 7);
		cl.set(Calendar.DAY_OF_WEEK, 2);
	}

	protected static long getMonday(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(time));
		instance.set(Calendar.DAY_OF_WEEK, 2);
		instance.set(Calendar.HOUR_OF_DAY, 0);
		instance.set(Calendar.MINUTE, 0);
		instance.set(Calendar.SECOND, 0);
		long mondayLong = instance.getTime().getTime();

		return mondayLong;
	}
	protected static long getSaturday(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(time));
		instance.set(Calendar.DAY_OF_WEEK, 7);

		long mondayLong = instance.getTime().getTime();

		return mondayLong;
	}
	protected static long getFirstDayOfNextWeek(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(time));
		instance.set(Calendar.DAY_OF_WEEK, 7);
		instance.set(Calendar.HOUR_OF_DAY,0);
		instance.set(Calendar.MINUTE,0);
		instance.set(Calendar.SECOND,0);
		instance.add(Calendar.DATE, 2);

		long mondayLong = instance.getTime().getTime();

		return mondayLong;
	}
	protected static long getLastDayOfThisWeek(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(time));
		instance.set(Calendar.DAY_OF_WEEK, 7);
		instance.set(Calendar.HOUR_OF_DAY,23);
		instance.set(Calendar.MINUTE,59);
		instance.set(Calendar.SECOND,59);
		instance.add(Calendar.DATE, 1);

		long mondayLong = instance.getTime().getTime();

		return mondayLong;
	}

	protected static long getNextMonday(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(time));
		instance.set(Calendar.DAY_OF_WEEK, 2);
		instance.add(Calendar.DATE, 7);

		long mondayLong = instance.getTime().getTime();

		return mondayLong;
	}

	protected String getRandomDate() {
		long l2 = new Date().getTime();
		long l1 = l2 - (long)1000 * 3600 * 24 * 90;
		long rt = (long) Math.round(Math.random() * (l2 - l1)) + l1;
		Date date = new Date(rt);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
