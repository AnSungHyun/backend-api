package com.spring.backend.utils;

/**
 * 검색관련 유틸리티
 * 
 * @author Soorim
 *
 */
public class SearchUtil {

	/**
	 * 키워드 하이라이트처리
	 * 
	 * @param keyword
	 * @param highlightWord
	 * @return
	 */
	public static String highlightKeyword(String keyword, String highlightWord) {
		if (keyword == null) return "";
		return keyword.replaceAll("(?i)("+highlightWord+")", "<b class=\"netsearch-highlight\">$1</b>"); 
	}
}
