package com.yc.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ExamUtil {
	// 教师（教员、班主任）
	public static String TEACHER = "1";

	// 管理员
	public static String MANAGER = "2";

	// 分页的大小
	public static int PAGE_SIZE = 10;

	// 共有4种状态 1-4
	public static int PAPER_STATUS_TOTAL = 4;

	// 试卷未考

	public static int PAPER_STATUS_NOT_TEST = 1;

	// 试卷未评
	public static int PAPER_STATUS_NOT_VIEW = 2;

	// 试卷已评
	public static int PAPER_STATUS_VIEWED = 3;

	// 试卷正在考试
	public static int PAPER_STATUS_IS_TESTING = 4;

	// 每套试卷最少有5道题
	public static int PAPER_MIN_NUMBER = 5;

	// 试题的难易度
	public static int QUESTION_HARD = 1;

	public static int QUESTION_MIDDLE = 2;

	public static int QUESTION_EASY = 3;

	/*
	 * 考生做的答案字符串，没做的中间填充""，如： 1,"";2,""; examAnswerString 考生答案字符串 countQuestion
	 * 本试卷有多少题
	 */
	@SuppressWarnings("unchecked")
	public static String fillSpaceAnswer(String examAnswerString,
			int countQuestion) {
		// 学生没有做题
		if (examAnswerString == null || "".equals(examAnswerString)) {
			return "";
		}
		String[] examAnswers = examAnswerString.split(";");
		HashMap map = new HashMap();
		for (int i = 0; i < examAnswers.length; i++) {
			// 如果没有答案，则只会有一个数组元素
			String oneQuestion[] = examAnswers[i].split(",");
			// 没有答案，则写个空在相应的位置
			if (oneQuestion.length == 1) {
				map.put(oneQuestion[0], null);
			} else {
				map.put(oneQuestion[0], oneQuestion[1]);
			}
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= countQuestion; i++) {
			sb.append(i);
			sb.append(",");
			if (map.get(String.valueOf(i)) == null) {
				// 没做加上空格
				sb.append(" ");
				sb.append(";");
			} else {
				sb.append(map.get(String.valueOf(i)).toString());
				sb.append(";");

			}
		}
		return new String(sb);
	}

	/**
	 * 将答案字符串转成一个HashMap，键代表题号，值代码答案
	 */
	public static HashMap<String, String> answerStringToMap(String answer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (answer == null || "".equals(answer)) {
			return null;
		}
		String arrAns[] = answer.split(";");
		for (int i = 0; i < arrAns.length; i++) {
			// 如果没有答案，则只会有一个数组元素
			String oneQuestion[] = arrAns[i].split(",");
			// 没有答案，则写个空在相应的位置
			if (oneQuestion.length == 1) {
				map.put(oneQuestion[0], " ");
			} else {
				map.put(oneQuestion[0], oneQuestion[1]);
			}
		}
		return map;
	}

	/*
	 * HashMap，键代表题号，值代表答案转成一个字符串，用,和;分开
	 */
	public static String mapToAnswerString(HashMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= map.size(); i++) {
			sb.append(i);
			sb.append(",");
			sb.append(map.get(String.valueOf(i)));
			sb.append(";");
		}
		return sb.toString();
	}

	/*
	 * 删除试卷中的多道题后,拼接相应的题目答案;
	 */
	public static String mapToAnswersString(HashMap<String, String> map,
			int size) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= size; i++) {
			if (map.get(String.valueOf(i)) != null) {
				sb.append(i);
				sb.append(",");
				sb.append(map.get(String.valueOf(i)));
				sb.append(";");
			}
		}
		return sb.toString();
	}

	/*
	 * 传入一个有逗号的字符串，删除其中的一个后，再转回去
	 */
	public static String delOneQuestion(String originQuestion,
			String removeQuestion) {
		if (originQuestion == null || "".equals(originQuestion)) {
			return "";
		}
		String questions[] = originQuestion.split(",");
		ArrayList<String> arrQus = new ArrayList<String>();
		for (int i = 0; i < questions.length; i++) {
			arrQus.add(questions[i]);
		}
		if (arrQus.contains(removeQuestion)) {
			arrQus.remove(removeQuestion);
		}
		StringBuffer newQuestion = new StringBuffer();
		for (int i = 0; i < arrQus.size(); i++) {
			newQuestion.append(arrQus.get(i));
			newQuestion.append(",");
		}
		return newQuestion.toString();
	}

	/*
	 * 从正确答案中删除一道题，答案字符串，要删除的编号
	 */
	public static String delOneAnswer(String originAnswer, String removeNo) {
		if (originAnswer == null || "".equals(originAnswer)) {
			return "";
		}
		HashMap<String, String> map = answerStringToMap(originAnswer);
		// 从下一个开始向前移动一个数，到最后一个的前一个
		for (int i = Integer.parseInt(removeNo); i < map.size(); i++) {
			map.put(String.valueOf(i), map.get(String.valueOf(i + 1)));
		}
		// 最后删除最后一个
		map.remove(String.valueOf(map.size()));
		return mapToAnswerString(map);
	}

	/*
	 * 从正确答案中删除多道题,答案字符串，要删除的编号
	 */
	public static String delManyAnswer(String originAnswer, String removeNo) {
		if (originAnswer == null || "".equals(originAnswer)) {
			return "";
		}
		HashMap<String, String> map = answerStringToMap(originAnswer);
		int mapSize = map.size();
		String[] questionNos = removeNo.split(",");
		for (int i = 0; i < questionNos.length; i++) {
			map.remove(questionNos[i]);
		}
		return mapToAnswersString(map,mapSize);
	}

	/**
	 * 获取当前系统日期，格式自定义
	 * 
	 * @param f
	 *            String 日期的格式
	 * @return String 系统当前的日期
	 */
	public static String getNowDate(String strF) {
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat(strF);
		return sf.format(now);
	}

	/**
	 * 格式化传入进来的字符串,为空时转为"",不为空时去除空格
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String formatRequestStr(String str) {
		str = str == null ? "" : str.trim();
		// str = new String(str.trim().getBytes("ISO-8859-1"), "GBK");
		return str;
	}

	// 将一个字符串数组用分隔符，封装成一个String返回
	public static String join(String array[], String delimit) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
			// 最后一个不加
			if (i < array.length - 1) {
				sb.append(delimit);
			}
		}
		return sb.toString();
	}

	/**
	 * 将第二个数组加入到第一个数组的beginIndex的后一个位置，从1开始编号，形成一个新的数组
	 * 
	 * @param arr1
	 *            第一个数组
	 * @param arr2
	 *            第二个数组
	 * @param beginIndex
	 *            第一个数组的位置，位置从1开始数
	 * @return 一个合并后的数组
	 */
	public static String[] merge(String[] arr1, String[] arr2, int beginIndex) {
		int count = 0;
		// 将两个数组合并成一个
		String newArray[] = new String[arr1.length + arr2.length];
		for (int i = 0; i < newArray.length; i++) {
			if (i < beginIndex) {
				newArray[i] = arr1[i];
			} else if (i >= beginIndex && count < arr2.length) {
				newArray[i] = arr2[count];
				count++;
			} else {
				newArray[i] = arr1[i - count];
			}
		}
		return newArray;
	}

	// 将两个HashMap组合成一个HashMap，第二个加入第一个相应的位置的后一个，从1开始
	public static HashMap<String, String> merge(HashMap<String, String> map1,
			HashMap<String, String> map2, int beginIndex) {
		HashMap<String, String> map = new HashMap<String, String>();
		int count = 1;
		for (int i = 1; i <= map1.size() + map2.size(); i++) {
			if (i <= beginIndex) {
				map.put(String.valueOf(i), map1.get(String.valueOf(i)));
			} else if (i > beginIndex && count <= map2.size()) {
				map.put(String.valueOf(i), map2.get(String.valueOf(count)));
				count++;
			} else {
				map.put(String.valueOf(i), map1.get(String.valueOf(i - count
						+ 1)));
			}
		}
		return map;
	}
}
