/**
 * 
 */
package com.dcs.businessdetails.util;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.dcs.businessdetails.model.HttpResponse;



/**
 * @author DebashisNath
 *
 */
public class CommonUtil {
public static final String NULL = "null";
	
	/**
	 * @param message
	 * @param httpStatus
	 * @return
	 */
	public static HttpResponse createHttpResponse(String message, HttpStatus httpStatus) {
		HttpResponse customHttpResponse = new HttpResponse();
		customHttpResponse.setMessage(message);
		customHttpResponse.setStatus(httpStatus);
		return customHttpResponse;
	}
	
	/**
	 * @param delimiter
	 * @param keyElements
	 * @return
	 */
	public static String createKey(String delimiter, String... keyElements) {
		String localDelimiter = "_";
		if (!isNullOrEmpty(delimiter)) {
			localDelimiter = delimiter;
		}
		if (keyElements != null && keyElements.length > 0) {
			if (keyElements.length == 1) {
				return Arrays.toString(keyElements);
			}
			return String.join(localDelimiter, keyElements);
		}
		return localDelimiter;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0 || NULL.equalsIgnoreCase(value);
	}
}
