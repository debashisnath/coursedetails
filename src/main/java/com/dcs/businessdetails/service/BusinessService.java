/**
 * 
 */
package com.dcs.businessdetails.service;

import com.dcs.businessdetails.exception.BusinessDetailsNotFoundException;
import com.dcs.businessdetails.exception.CourseNotFoundException;
import com.dcs.businessdetails.exception.InvalidRequestException;
import com.dcs.businessdetails.exception.UserIdAndPasswordMismatchException;
import com.dcs.businessdetails.model.BusinessDetailsRequest;
import com.dcs.businessdetails.model.BusinessDetailsResponse;

/**
 * @author DebashisNath
 *
 */
public interface BusinessService {

	/**
	 * @param businessDetailsRequest
	 * @return
	 * @throws UserIdAndPasswordMismatchException
	 * @throws InvalidRequestException
	 * @throws CourseNotFoundException 
	 */
	public String saveBusinessDetails(BusinessDetailsRequest businessDetailsRequest) throws UserIdAndPasswordMismatchException, InvalidRequestException, CourseNotFoundException;
	
	/**
	 * @param emailId
	 * @return
	 * @throws BusinessDetailsNotFoundException
	 */
	public BusinessDetailsResponse getBusinessDetails(String emailId) throws BusinessDetailsNotFoundException;
}
