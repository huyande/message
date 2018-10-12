package com.zwxq.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.StringUtils;


/**
 * 字符串工具类
 * 
 * @author WangDuo
 * 
 */
public class StringUtil extends StringUtils{
	

	/**
	 * Ids字符串逗号截取为int数组
	 * 
	 * @param string
	 * @return
	 */
	public static int[] getIds(String string) {
		String[] array = string.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			if (NumberUtils.isDigits(array[i].trim()))
				list.add(new Integer(array[i].trim()));
		}
		if (list.isEmpty()) {
			return null;
		}
		int[] rtn = new int[list.size()];
		int index = 0;
		for (int i : list)
			rtn[index++] = i;
		return rtn;
	}

	/**
	 * Ids字符串逗号截取为long数组
	 * 
	 * @param string
	 * @return
	 */
	public static long[] getLongIds(String string) {
		String[] array = string.split(",");
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < array.length; i++) {
			if (NumberUtils.isDigits(array[i].trim()))
				list.add(new Long(array[i].trim()));
		}
		if (list.isEmpty()) {
			return null;
		}
		long[] rtn = new long[list.size()];
		int index = 0;
		for (Long i : list)
			rtn[index++] = i;
		return rtn;
	}

	

	

	

}
