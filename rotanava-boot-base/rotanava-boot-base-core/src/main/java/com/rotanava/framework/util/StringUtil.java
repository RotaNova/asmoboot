package com.rotanava.framework.util;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	private static final String BLANK_STR_PATTEN = "\\s*|\t|\r|\n";

	private static final String MOBILE_PATTEN = "^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0,5-9]))\\d{8}$";

	private static final String LEGAL_PATTEN = "[A-Za-z0-9_]{3,16}";

	private static final String EMAIL_PATTEN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

	private static final String MD5_PATTEN = "[A-Za-z0-9_]{16,40}";

	public static Integer[] getIntegerParas(Object[] objs) {
		if (isNullOrEmpty(objs)) {
			return null;
		}
		Integer[] ints = new Integer[objs.length];
		for (int i = 0; i < objs.length; i++) {
			try {
				ints[i] = Integer.valueOf(objs[i].toString());
			} catch (Exception e) {
			}
		}
		return ints;
	}

	/**
	 * 生成指定数目字符串按分隔符分割
	 * 
	 * @param baseStr
	 * @param mosaicChr
	 * @param size
	 * @return
	 */
	public static String getByMosaicChr(String baseStr, String mosaicChr, Integer size) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			if (isNullOrEmpty(baseStr)) {
				continue;
			}
			list.add(baseStr);
		}
		return collectionMosaic(list, mosaicChr);
	}

	/**
	 * 根据分割符将字符串分割成String数组
	 * 
	 * @param src
	 *            源字符串
	 * @param separator
	 *            分隔�?
	 * @return String数组
	 */
	public static String[] splitToStringArray(String src, String separator) {
		Vector<String> splitArrays = new Vector<String>();
		int i = 0;
		int j = 0;
		while (i <= src.length()) {
			j = src.indexOf(separator, i);
			if (j < 0) {
				j = src.length();
			}
			splitArrays.addElement(src.substring(i, j));
			i = j + 1;
		}
		int size = splitArrays.size();
		String[] array = new String[size];
		System.arraycopy(splitArrays.toArray(), 0, array, 0, size);
		return array;
	}

	/**
	 * 根据分割符将字符串分割成Integer数组
	 * 
	 * @param src
	 *            源字符串
	 * @param separator
	 *            分隔�?
	 * @return Integer数组
	 */
	public static Integer[] splitToIntgArray(String src, String separator) {
		String[] arr = splitToStringArray(src, separator);
		Integer[] intArr = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++) {
			intArr[i] = Integer.valueOf(arr[i]);
		}
		return intArr;
	}

	/**
	 * 根据分隔符将字符串分割成int数组
	 * 
	 * @param src
	 *            源字符串
	 * @param separator
	 *            分隔�?
	 * @return int数组
	 */
	public static int[] splitToIntArray(String src, String separator) {
		String[] arr = splitToStringArray(src, separator);
		int[] intArr = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			intArr[i] = Integer.parseInt(arr[i]);
		}
		return intArr;
	}

	public static String getInPara(Integer size) {
		return getByMosaicChr("?", ",", size);

	}

	public static String textCutCenter(String allTxt, String firstTxt, String lastTxt) {
		try {
			String tmp = "";
			int n1 = allTxt.indexOf(firstTxt);
			if (n1 == -1) {
				return "";
			}
			tmp = allTxt.substring(n1 + firstTxt.length(), allTxt.length());
			int n2 = tmp.indexOf(lastTxt);
			if (n2 == -1) {
				return "";
			}
			tmp = tmp.substring(0, n2);
			return tmp;
		} catch (Exception e) {
			return "";
		}
	}

	public static List<String> textCutCenters(String allTxt, String firstTxt, String lastTxt) {
		try {
			List<String> results = new ArrayList<String>();
			while (allTxt.contains(firstTxt)) {
				int n = allTxt.indexOf(firstTxt);
				allTxt = allTxt.substring(n + firstTxt.length(), allTxt.length());
				n = allTxt.indexOf(lastTxt);
				if (n == -1) {
					return results;
				}
				String result = allTxt.substring(0, n);
				results.add(result);
				allTxt = allTxt.substring(n + firstTxt.length(), allTxt.length());
			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}

	public static String convertToUnicode(String source) {
		String result = "";
		char[] chrs = source.toCharArray();
		for (int i = 0; i < chrs.length; i++) {
			result += "&#" + Character.codePointAt(chrs, i);
		}
		return result;
	}

	public static Integer toInteger(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Integer.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return String.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Double toDouble(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Double.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Float toFloat(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Float.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Long toLong(Object obj) {
		if (isNullOrEmpty(obj)) {
			return null;
		}
		try {
			return Long.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer getRanDom(int start, int end) {
		return (int) (Math.random() * (end - start + 1)) + start;
	}

	public static float getRanDom(Float start, Float end) {
		String str = String.valueOf(start);
		String[] tabs = str.split("\\.");
		Integer startLength = 1;
		if (tabs.length == 2) {
			startLength = tabs[1].length();
		}
		str = String.valueOf(end);
		tabs = str.split("\\.");
		Integer endLength = 1;
		if (tabs.length == 2) {
			endLength = tabs[1].length();
		}
		if (endLength > startLength) {
			startLength = endLength;
		}
		start = (float) (start * Math.pow(10, startLength));
		end = (float) (end * Math.pow(10, startLength));
		return (float) (getRanDom(start.intValue(), end.intValue()) / Math.pow(10, startLength));
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile(BLANK_STR_PATTEN);
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static Boolean isMatcher(String val, String matcher) {
		Pattern p = Pattern.compile(matcher);
		Matcher m = p.matcher(val);
		return m.matches();
	}

	public static List<String> matchExport(String context, String patten) {
		try {
			Integer index = 0;
			Pattern pattern = Pattern.compile(patten, Pattern.DOTALL);
			Matcher matcher = pattern.matcher(context);
			List<String> results = new ArrayList<String>();
			while (matcher.find(index)) {
				String tmp = matcher.group(1);
				index = matcher.end();
				if (isNullOrEmpty(tmp)) {
					continue;
				}
				results.add(tmp);
			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}

	public static String matchExportFirst(String context, String patten) {
		try {
			Integer index = 0;
			Pattern pattern = Pattern.compile(patten, Pattern.DOTALL);
			Matcher matcher = pattern.matcher(context);
			while (matcher.find(index)) {
				String tmp = matcher.group(1);
				index = matcher.end();
				if (isNullOrEmpty(tmp)) {
					continue;
				}
				return tmp;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isMobile(String mobile) {
		if (isNullOrEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile(MOBILE_PATTEN);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static boolean isLegal(String str) {
		if (isNullOrEmpty(str)) {
			return false;
		}
		Pattern p = Pattern.compile(LEGAL_PATTEN);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isEmail(String email) {
		if (isNullOrEmpty(email)) {
			return false;
		}
		Pattern p = Pattern.compile(EMAIL_PATTEN);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isMd5(String md5) {
		if (isNullOrEmpty(md5)) {
			return false;
		}
		Pattern p = Pattern.compile(MD5_PATTEN);
		Matcher m = p.matcher(md5);
		return m.matches();
	}

	public static boolean isAllNull(Object... obj) {
		if (obj == null || obj.length == 0) {
			return true;
		}
		for (int i = 0; i < obj.length; i++) {
			if (!isNullOrEmpty(obj[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAllNull(List<Object> objs) {
		return isAllNull(objs.toArray());
	}

	/**
	 * 把一个数组按照分隔符拼接成字符串
	 * 
	 * @param 数组参数
	 * @param 分隔符
	 * @return
	 */
	public static String collectionMosaic(Object[] objs, String mosaicChr) {
		if (isNullOrEmpty(objs)) {
			return null;
		}
		List<Object> objList = Arrays.asList(objs);
		return collectionMosaic(objList, mosaicChr);
	}
	/**
	 * 把一个数组按照分隔符拼接成字符串
	 *
	 * @return
	 */
	public static String collectionMosaic(int[] intObjs, String mosaicChr) {
		Object[] objs = new Object[intObjs.length];
		for (int i = 0; i < intObjs.length; i++) {
			objs[i] = String.valueOf(intObjs[i]);
		}
		return collectionMosaic(objs, mosaicChr);
	}

	/**
	 * 把一个或多个字符串按照分隔符拼接成字符串
	 * 
	 * @param 数组参数
	 * @param 分隔符
	 * @return
	 */
	public static String collectionMosaic(String mosaicChr, Object... objs) {
		List<Object> objList = Arrays.asList(objs);
		return collectionMosaic(objList, mosaicChr);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String collectionMosaic(Set<?> objs, String mosaicChr) {
		return collectionMosaic(new ArrayList(objs), mosaicChr);
	}

	/**
	 * 把一个集合按照分隔符拼接成字符串
	 * 
	 * @param 集合参数
	 * @param 分隔符
	 * @return 字符串
	 */
	public static String collectionMosaic(Collection<?> objs, String mosaicChr) {
		if (objs == null || objs.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Object obj : objs) {
			if (isNullOrEmpty(obj)) {
				continue;
			}
			sb.append(obj);
			if (i < objs.size() - 1) {
				sb.append(mosaicChr);
			}
			i++;
		}
		return sb.toString();
	}

	/**
	 * 生成指定数目字符串按分隔符分割
	 * 
	 * @param baseStr
	 * @param mosaicChr
	 * @param size
	 * @return
	 */
	public static String getStringSByMosaicChr(String baseStr, String mosaicChr, Integer size) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			if (isNullOrEmpty(baseStr)) {
				continue;
			}
			list.add(baseStr);
		}
		return collectionMosaic(list, mosaicChr);
	}

	/**
	 * 按照分隔符分割,得到字符串集合
	 * 
	 * @param text
	 *            原字符串
	 * @param mosaiChr
	 *            分隔符
	 * @return list
	 */
	public static List<String> splitByMosaic(String text, String mosaiChr) {
		if (text == null || mosaiChr == null) {
			return null;
		}
		String[] tab = text.split(mosaiChr);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < tab.length; i++) {
			if (isNullOrEmpty(tab[i])) {
				continue;
			}
			list.add(tab[i]);
		}
		return list;
	}

	/**
	 * 按照分隔符分割,得到字符串集合
	 * 
	 * @param text
	 *            原字符串
	 * @param mosaiChr
	 *            分隔符
	 * @return list
	 */
	public static List<Integer> splitByMosaicInteger(String text, String mosaiChr) {
		if (text == null || mosaiChr == null) {
			return null;
		}
		String[] tab = text.split(mosaiChr);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < tab.length; i++) {
			if (isNullOrEmpty(tab[i])) {
				continue;
			}
			try {
				list.add(Integer.valueOf(tab[i]));
			} catch (Exception e) {
			}

		}
		return list;
	}

	/**
	 * 按照分隔符分割,得到字符串集合
	 * 
	 * @param text
	 *            原字符串
	 * @param mosaiChr
	 *            分隔符
	 * @return list
	 */
	public static Integer[] splitByMosaicIntegers(String text, String mosaiChr) {
		if (text == null || mosaiChr == null) {
			return null;
		}
		String[] tab = text.split(mosaiChr);
		Integer[] list = new Integer[tab.length];
		for (int i = 0; i < tab.length; i++) {
			if (isNullOrEmpty(tab[i])) {
				continue;
			}
			try {
				list[i] = Integer.valueOf(tab[i]);
			} catch (Exception e) {
			}

		}
		return list;
	}

	public static List<String> doMatcher(String context, String pat) {
		try {
			List<String> images = new ArrayList<String>();
			Integer index = 0;
			Pattern pattern = Pattern.compile(pat, Pattern.DOTALL);
			Matcher matcher = pattern.matcher(context);
			String tmp = null;
			while (matcher.find(index)) {
				tmp = matcher.group(1);
				index = matcher.end();
				if (StringUtil.isNullOrEmpty(tmp)) {
					continue;
				}
				images.add(tmp);
			}
			return images;
		} catch (Exception e) {
			return null;
		}
	}

	public static String doMatcherFirst(String context, String pat) {
		List<String> strs = doMatcher(context, pat);
		if (StringUtil.isNullOrEmpty(strs)) {
			return null;
		}
		return strs.get(0);
	}

	public static boolean isNullOrEmpty(Object obj) {
		try {
			if (obj == null) {
				return true;
			}
			if (obj instanceof CharSequence) {
				return ((CharSequence) obj).length() == 0;
			}
			if (obj instanceof Collection) {
				return ((Collection<?>) obj).isEmpty();
			}
			if (obj instanceof Map) {
				return ((Map<?, ?>) obj).isEmpty();
			}
			if (obj instanceof Object[]) {
				Object[] object = (Object[]) obj;
				if (object.length == 0) {
					return true;
				}
				boolean empty = true;
				for (int i = 0; i < object.length; i++) {
					if (!isNullOrEmpty(object[i])) {
						empty = false;
						break;
					}
				}
				return empty;
			}
			return false;
		} catch (Exception e) {
			return true;
		}

	}

	public static Integer findNull(Object... objs) {
		if (isNullOrEmpty(objs)) {
			return 0;
		}
		for (int i = 0; i < objs.length; i++) {
			if (isNullOrEmpty(objs[i])) {
				return i;
			}
		}
		return -1;
	}

	public static boolean hasNull(Object... objs) {
		return findNull(objs) > -1;
	}

	// 判断是否为数字
	public static Boolean isNumber(String str) {
		if (isNullOrEmpty(str)) {
			return false;
		}
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String argsToString(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (String tmp : args) {
			sb.append(tmp);
		}

		return sb.toString();
	}

	// 字符串意义分割
	public static String[] splitString(String str) {
		if (isNullOrEmpty(str)) {
			return null;
		}
		String[] finalStrs = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			finalStrs[i] = str.substring(i, i + 1);
		}
		return finalStrs;
	}



	public static String getString(Object... objs) {
		if (isNullOrEmpty(objs)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			if (isNullOrEmpty(obj)) {
				sb.append("null");
			}
			sb.append(String.valueOf(obj));
		}
		return sb.toString();
	}

	public static String stringSort(String str) {
		if (isNullOrEmpty(str)) {
			return "";
		}
		String[] strs = splitString(str);
		Arrays.sort(strs);
		return argsToString(strs);
	}

	/**
	 * 集合碰撞
	 * 
	 * @param needList
	 *            需要的集合
	 * @param actualList
	 *            当前实际集合
	 * @return 缺少的元素
	 */
	public static List<?> collisionList(List<?> needList, List<?> actualList) {
		List<Object> list = new ArrayList<Object>();
		for (Object o : needList) {
			if (actualList.contains(o)) {
				continue;
			}
			list.add(o);
		}
		if (isNullOrEmpty(list)) {
			return null;
		}
		return list;
	}

	public static List<Long> integerListToLong(List<Integer> ids) {
		if (isNullOrEmpty(ids)) {
			return null;
		}
		List<Long> list = new ArrayList<Long>();
		for (Integer id : ids) {
			list.add(Long.valueOf(id));
		}
		return list;
	}

	/**
	 * List碰撞取缺失
	 * 
	 * @param allList
	 *            理论应该出现的List
	 * @param conflictList
	 *            实际出现的List
	 * @return 丢失的List
	 */
	public static List<?> listConflict(List<?> allList, List<?> conflictList) {
		if (isNullOrEmpty(allList)) {
			return null;
		}
		if (isNullOrEmpty(conflictList)) {
			return allList;
		}
		List<Object> list = new ArrayList<Object>();
		for (Object obj : allList) {
			if (conflictList.contains(obj)) {
				continue;
			}
			list.add(obj);
		}
		if (isNullOrEmpty(list)) {
			return null;
		}
		return list;
	}

	public static Integer bambooParse(Integer... prs) {
		Integer prSum = 0;
		for (Integer pr : prs) {
			prSum += pr;
		}
		Integer random = getRanDom(1, prSum);
		prSum = 0;
		for (int i = 0; i < prs.length; i++) {
			prSum += prs[i];
			if (random <= prSum) {
				return i;
			}
		}
		return 0;
	}

	public static Integer sumInteger(Integer... sums) {
		if (isNullOrEmpty(sums)) {
			return -1;
		}
		Integer total = 0;
		for (Integer tmp : sums) {
			total += tmp;
		}
		return total;
	}

	/**
	 * 概率算法
	 * 
	 * @param chances
	 *            各成员概率权重
	 * @return 权重下标
	 */
	public static Integer getBambooIndex(Integer... chances) {
		if (isNullOrEmpty(chances)) {
			return -1;
		}
		Integer total = sumInteger(chances);
		Integer random = getRanDom(1, total);
		total = new Integer(0);
		for (int i = 0; i < chances.length; i++) {
			total += chances[i];
			if (random <= total) {
				return i;
			}
		}
		return -1;
	}

	public static List<?> removeEmpty(List<?> list) {
		if (StringUtil.isNullOrEmpty(list)) {
			return null;
		}
		List<Object> newList = new ArrayList<Object>(list.size());
		for (Object obj : list) {
			if (isNullOrEmpty(obj)) {
				continue;
			}
			newList.add(obj);
		}
		if (isNullOrEmpty(newList)) {
			return null;
		}
		return newList;
	}

	public static Integer getBambooIndex(Float... chanceSources) {
		if (isNullOrEmpty(chanceSources)) {
			return -1;
		}
		Float[] chances = Arrays.copyOf(chanceSources, chanceSources.length);
		Integer smallLength = 0;
		for (Float f : chances) {
			String str = String.valueOf(f);
			String[] tabs = str.split("\\.");
			if (tabs.length != 2) {
				continue;
			}
			smallLength = tabs[1].length();
		}
		if (smallLength > 0) {
			Integer multiple = Double.valueOf(Math.pow(10, smallLength)).intValue();
			for (int i = 0; i < chances.length; i++) {
				chances[i] = chances[i] * multiple;
			}
		}
		Integer[] chanceInts = new Integer[chances.length];
		for (int i = 0; i < chances.length; i++) {
			chanceInts[i] = chances[i].intValue();
		}
		return getBambooIndex(chanceInts);
	}

	public static Float floatCut(Float f1, Float f2) {
		BigDecimal b1 = new BigDecimal(Float.toString(f1));
		BigDecimal b2 = new BigDecimal(Float.toString(f2));
		return b1.subtract(b2).floatValue();
	}

	/**
	 * 获取网址后缀
	 * 
	 * @param url
	 * @return
	 */
	public static String getSuffix(String url) {
		if (isNullOrEmpty(url)) {
			return "";
		}
		String[] tab = url.split("\\.");
		if (tab.length > 1) {
			return tab[tab.length - 1];
		}
		return "";
	}

	public static String formatPath(String path) {
		if (StringUtil.isNullOrEmpty(path)) {
			return null;
		}
		path = path.replace("\\", "/");
		while (path.contains("//")) {
			path = path.replace("//", "/");
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return path;
	}


	/**
	 * 是否存在乱码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMessyCode(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?）
			// 从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
			if ((int) c == 0xfffd) {
				// 存在乱码
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否为空的字符串
	 * @param str 字符串
	 * @author: WeiQiangMiao
	 * @createDate: 2020/5/21
	 * @return: boolean
	 */
	public static boolean isBlank(String str){
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否不为空的字符串
	 * @param str 字符串
	 * @author: WeiQiangMiao
	 * @createDate: 2020/5/21
	 * @return: boolean
	 */
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}

	/**
	 * 随机 强字符串
	 * @param length 长度
	 * @author WeiQiangMiao
	 * @date 2020/8/6
	 * @return java.lang.String
	 * @version 1.0.0
	 */
	public static String randomString(int length) {
		// 最终生成的密码
		StringBuilder password = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i ++) {
			// 随机生成0或1，用来确定是当前使用数字还是字母 (0则输出数字，1则输出字母)
			int charOrNum = random.nextInt(2);
			if (charOrNum == 1) {
				// 随机生成0或1，用来判断是大写字母还是小写字母 (0则输出小写字母，1则输出大写字母)
				int temp = random.nextInt(2) == 1 ? 65 : 97;
				password.append((char) (random.nextInt(26) + temp));
			} else {
				// 生成随机数字
				password.append(random.nextInt(10));
			}
		}
		return password.toString();
	}
}
