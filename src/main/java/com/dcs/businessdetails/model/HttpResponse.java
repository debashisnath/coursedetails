/**
 * 
 */
package com.dcs.businessdetails.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

/**
 * @author DebashisNath
 *
 */
public class HttpResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6996211303951321809L;
	private String message;
	private HttpStatus status;
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}
