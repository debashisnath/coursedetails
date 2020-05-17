/**
 * 
 */
package com.dcs.businessdetails.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.SerializationUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcs.businessdetails.exception.BusinessDetailsNotFoundException;
import com.dcs.businessdetails.exception.CourseNotFoundException;
import com.dcs.businessdetails.exception.InvalidRequestException;
import com.dcs.businessdetails.exception.UserIdAndPasswordMismatchException;
import com.dcs.businessdetails.model.AuthHeader;
import com.dcs.businessdetails.model.BusinessDetails;
import com.dcs.businessdetails.model.BusinessDetailsRequest;
import com.dcs.businessdetails.model.BusinessDetailsResponse;
import com.dcs.businessdetails.model.ProductDetails;
import com.dcs.businessdetails.model.ProfileDetails;
import com.dcs.businessdetails.model.SubscriptionDetails;
import com.dcs.businessdetails.repository.BusinessRepository;
import com.dcs.businessdetails.util.CommonContants;
import com.dcs.businessdetails.util.CourseStatusType;

/**
 * @author DebashisNath
 *
 */
@Service
public class BusinessServiceImpl implements BusinessService {

	private BusinessRepository businessRepository;
	private CatalogueService catalogueService;
	
	@Autowired
	BusinessServiceImpl(BusinessRepository businessRepo, CatalogueService catalogueService){
		this.businessRepository = businessRepo;
		this.catalogueService = catalogueService;
	}
	@Override
	public String saveBusinessDetails(BusinessDetailsRequest businessDetailsRequest)
			throws UserIdAndPasswordMismatchException, InvalidRequestException, CourseNotFoundException {
		if(businessDetailsRequest!=null && businessDetailsRequest.getAuthHeader()!=null
				&& validRequest(businessDetailsRequest.getAuthHeader())) {
			if(businessDetailsRequest.getPurchaseStatus()!= null && businessDetailsRequest.getProfileDetails()!= null 
					&& userAllowedToSaveOrder(businessDetailsRequest.getPurchaseStatus(), businessDetailsRequest.getProfileDetails())) {
				return processOrder(businessDetailsRequest);
			}else
				throw new InvalidRequestException(CommonContants.PAYMENT_FAILURE);
		}else {
			throw new UserIdAndPasswordMismatchException(CommonContants.WRONG_ID_PWD);
		}
	}

	/**
	 * @param businessDetailsRequest
	 * @throws CourseNotFoundException 
	 */
	private String processOrder(BusinessDetailsRequest businessDetailsRequest) throws CourseNotFoundException {
		BusinessDetails retrievedDetails = null;
		String message = null;
		int noOfCourseTaken = 1;
		if(businessDetailsRequest.getProfileDetails().getEmailId()!=null) {
			retrievedDetails = businessRepository.findByEmailId(businessDetailsRequest.getProfileDetails().getEmailId());
		}
		if(retrievedDetails!=null && retrievedDetails.getProductList()!= null && !retrievedDetails.getProductList().isEmpty()) {
			noOfCourseTaken = retrievedDetails.getProductList().size() + noOfCourseTaken;
			ProductDetails productDetails = catalogueService.extractProductDetails(businessDetailsRequest.getProduct_Id());
			List<ProductDetails> activeProductList = new ArrayList<>();
			activeProductList.addAll(retrievedDetails.getProductList());
			for (ProductDetails product : retrievedDetails.getProductList()) {
				if(productDetails.getProductId().equalsIgnoreCase(product.getProductId())
						&& product.getProductStatusType().equalsIgnoreCase(CourseStatusType.EXPIRED)) {
					List<ProductDetails> expiredProductList = retrievedDetails.getExpiredProductList();
					if(expiredProductList != null) {
						expiredProductList.add(product);
						activeProductList.remove(product);
					}else {
						expiredProductList = new ArrayList<>();
						expiredProductList.add(product);
						retrievedDetails.setExpiredProductList(expiredProductList);
						activeProductList.remove(product);
					}
				}
			}
			retrievedDetails.setProductList(activeProductList);
			if (productDetails != null) {
				retrievedDetails.setNoOfCourseTaken(noOfCourseTaken);
				retrievedDetails.setProfileDetails(businessDetailsRequest.getProfileDetails());
				productDetails.setProductStatusType(CourseStatusType.ACTIVE);
				mapSubScriptionDetails(productDetails);
				List<ProductDetails> productList = retrievedDetails.getProductList();
				productList.add(productDetails);
				retrievedDetails.setProductList(productList);
				retrievedDetails.setPurchaseStatus(businessDetailsRequest.getPurchaseStatus());
				storeBusinessDetailsInDB(retrievedDetails);
				message = CommonContants.DETAILS_UPDATED;
			}
		}
		if(retrievedDetails == null && businessDetailsRequest.getProduct_Id() != null) {
		ProductDetails productDetails = catalogueService.extractProductDetails(businessDetailsRequest.getProduct_Id());
		   if(productDetails != null) {
			   BusinessDetails businessDetails = new BusinessDetails();
			   businessDetails.setEmailId(businessDetailsRequest.getProfileDetails().getEmailId());
			   businessDetails.setNoOfCourseTaken(noOfCourseTaken);
			   businessDetails.setProfileDetails(businessDetailsRequest.getProfileDetails());
			   productDetails.setProductStatusType(CourseStatusType.ACTIVE);
			   mapSubScriptionDetails(productDetails);
			   List<ProductDetails> productList = new ArrayList<>();
			   productList.add(productDetails);
			   businessDetails.setProductList(productList);
			   businessDetails.setPurchaseStatus(businessDetailsRequest.getPurchaseStatus());
			   storeBusinessDetailsInDB(businessDetails);
			   message = CommonContants.DETAILS_SAVED;
		   }else
			   throw new CourseNotFoundException(CommonContants.COURSE_NOT_FOUND);
		}
		return message;
		
	}
	/**
	 * @param businessDetails
	 */
	private void storeBusinessDetailsInDB(BusinessDetails businessDetails) {
		businessRepository.save(businessDetails);
		
	}
	/**
	 * @param businessDetails
	 * @param productDetails
	 */
	private void mapSubScriptionDetails(ProductDetails productDetails) {
		SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
		if(productDetails.getNoOfSubscriptionMonth() != null) {
			Date subscriptionStartDate = new Date();
			subscriptionDetails.setSubscriptionStartDate(subscriptionStartDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(subscriptionStartDate);
			//calendar.add(Calendar.MONTH, Integer.parseInt(productDetails.getNoOfSubscriptionMonth()));
			calendar.add(Calendar.DATE, Integer.parseInt("1"));
			subscriptionDetails.setSubscriptionEndDate(calendar.getTime());
			productDetails.setSubscriptionDetails(subscriptionDetails);
		}
		
	}
	/**
	 * @param purchaseStatus
	 * @param profileDetails
	 * @return
	 */
	private boolean userAllowedToSaveOrder(String purchaseStatus, ProfileDetails profileDetails) {
		
		return purchaseStatus.equals(CommonContants.COMPLETED) 
				&& profileDetails.getPaymentStatus().equals(CommonContants.SUCCESS);
	}
	/**
	 * @param authHeader
	 * @return
	 */
	private boolean validRequest(AuthHeader authHeader) {
		return authHeader.getUsername().equals(CommonContants.BUSINESS_USERNAME) 
				&& authHeader.getPassword().equals(CommonContants.BUSINESS_PASSWORD);
	}
	/* (non-Javadoc)
	 * @see com.dcs.businessdetails.service.BusinessService#getBusinessDetails(java.lang.String)
	 */
	@Override
	public BusinessDetailsResponse getBusinessDetails(String emailId) throws BusinessDetailsNotFoundException {
		BusinessDetails retrievedDetails = null;
		BusinessDetailsResponse businessDetailsResponse = null;
		if(emailId != null) {
			retrievedDetails = businessRepository.findByEmailId(emailId);
			if(retrievedDetails != null) {
			BusinessDetails clonedRetrievedDetails = (BusinessDetails)SerializationUtils.clone(retrievedDetails);
			processRetrievedDetails(clonedRetrievedDetails);
			if(!retrievedDetails.equals(clonedRetrievedDetails)) {
				storeBusinessDetailsInDB(clonedRetrievedDetails);
			}
			businessDetailsResponse = new BusinessDetailsResponse();
			businessDetailsResponse.setProductDetails(clonedRetrievedDetails.getProductList());
			businessDetailsResponse.setNoOfCourseTaken(clonedRetrievedDetails.getNoOfCourseTaken());
			businessDetailsResponse.setProfileDetails(clonedRetrievedDetails.getProfileDetails());
			businessDetailsResponse.setPurchaseStatus(clonedRetrievedDetails.getPurchaseStatus());
			return businessDetailsResponse;
			}
			else
				throw new BusinessDetailsNotFoundException(CommonContants.DETAILS_NOT_FOUND);
		}
		return businessDetailsResponse;
	}
	/**
	 * @param retrievedDetails
	 */
	private void processRetrievedDetails(BusinessDetails retrievedDetails) {
		checkSubscriptionStatus(retrievedDetails.getProductList());
		
	}
	/**
	 * @param productList
	 */
	private void checkSubscriptionStatus(List<ProductDetails> productList) {
		for (ProductDetails productDetails : productList) {
			if(!isSubscriptionExpired(productDetails)) {
				productDetails.setProductStatusType(CourseStatusType.EXPIRED);
			}
		}
		
	}
	/**
	 * @param productDetails
	 * @return
	 */
	private boolean isSubscriptionExpired(ProductDetails productDetails) {
		Date currentDate = null;
		Date subscriptionEndDate = null;
		boolean status = false;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String presentDate = date.format(new Date());
		try {
			currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(presentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date subscriptionEndDateFromDB = productDetails.getSubscriptionDetails().getSubscriptionEndDate();
		String endDate = date.format(subscriptionEndDateFromDB);
		try {
			subscriptionEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(subscriptionEndDate.compareTo(currentDate) >= 0) {
			status = true;
		}
		if(subscriptionEndDate.compareTo(currentDate) < 0) {
			status = false;
		}
		return status;
	}

}
