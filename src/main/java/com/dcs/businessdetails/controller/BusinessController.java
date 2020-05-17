/**
 * 
 */
package com.dcs.businessdetails.controller;

import java.util.Map;

import javax.management.modelmbean.XMLParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dcs.businessdetails.exception.BusinessDetailsNotFoundException;
import com.dcs.businessdetails.exception.CourseNotFoundException;
import com.dcs.businessdetails.exception.InvalidRequestException;
import com.dcs.businessdetails.exception.UserIdAndPasswordMismatchException;
import com.dcs.businessdetails.model.BusinessDetailsRequest;
import com.dcs.businessdetails.model.BusinessDetailsResponse;
import com.dcs.businessdetails.model.HttpResponse;
import com.dcs.businessdetails.service.BusinessService;
import com.dcs.businessdetails.service.CatalogueService;
import com.dcs.businessdetails.util.CommonUtil;
import com.dcs.businessdetails.util.JSONUtil;

/**
 * @author DebashisNath
 *
 */
@RestController
@CrossOrigin(origins="*")
public class BusinessController {

	private static final Logger traceLogger = LogManager.getLogger(BusinessController.class);
	
	BusinessService businessService;
	CatalogueService catalogueService;
	@Autowired
	BusinessController(BusinessService bService, CatalogueService cService){
		this.businessService = bService;
		this.catalogueService = cService;
		
	}
	
	/**
	 * @param businessDetailsRequest
	 * @return
	 */
	@PostMapping("/api/user/businessinfo")
	public ResponseEntity<?> storeBusinessInfo(@RequestBody BusinessDetailsRequest businessDetailsRequest){
		
		try {
			traceLogger.info("/Order Process Request: "+JSONUtil.convertObjectToJSON(businessDetailsRequest));
			String message = businessService.saveBusinessDetails(businessDetailsRequest);
			traceLogger.info("/Order Process Response: "+JSONUtil.convertObjectToJSON(CommonUtil.createHttpResponse(message, HttpStatus.NOT_ACCEPTABLE)));
			return new ResponseEntity<HttpResponse>(CommonUtil.createHttpResponse(message, HttpStatus.OK), HttpStatus.OK);
		}catch(UserIdAndPasswordMismatchException | InvalidRequestException | CourseNotFoundException e){
			traceLogger.info("/Order Process Response: "+JSONUtil.convertObjectToJSON(CommonUtil.createHttpResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE)));
			return new ResponseEntity<HttpResponse>(CommonUtil.createHttpResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE), HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	/**
	 * @return
	 */
	@GetMapping("/api/user/findCourse")
	public ResponseEntity<?> findCourseMap(){
		try {
			Map<String, String> courseIdAndPriceMap = catalogueService.extractCourseIdAndPriceMap();
			return new ResponseEntity<>(courseIdAndPriceMap, HttpStatus.OK);
		}catch(XMLParseException e){
			return new ResponseEntity<HttpResponse>(CommonUtil.createHttpResponse("Exception in XML parsing", HttpStatus.EXPECTATION_FAILED), HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	/**
	 * @param emailId
	 * @return
	 */
	@GetMapping("/api/user/coursedetails/{emailId}")
	public ResponseEntity<?> findCourseDetailsByUser(@PathVariable(value = "emailId") String emailId){
		try {
			BusinessDetailsResponse businessDetailsResponse = businessService.getBusinessDetails(emailId);
			return new ResponseEntity<>(businessDetailsResponse, HttpStatus.OK);
		}catch(BusinessDetailsNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
