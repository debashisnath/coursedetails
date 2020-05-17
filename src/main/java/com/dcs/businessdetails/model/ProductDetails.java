/**
 * 
 */
package com.dcs.businessdetails.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author DebashisNath
 *
 */
@Document
public class ProductDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6560149432428629873L;
	private String productId;
	private String productDescription;
	private List<String> subjectNameList;
	private String classDetails;
	private String noOfSubscriptionMonth;
	private String productStatusType;
	private String offeringPrice;
	private SubscriptionDetails subscriptionDetails;
	
	/**
	 * @return the subscriptionDetails
	 */
	public SubscriptionDetails getSubscriptionDetails() {
		return subscriptionDetails;
	}
	/**
	 * @param subscriptionDetails the subscriptionDetails to set
	 */
	public void setSubscriptionDetails(SubscriptionDetails subscriptionDetails) {
		this.subscriptionDetails = subscriptionDetails;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @return the subjectName
	 */
	public List<String> getSubjectNameList() {
		return subjectNameList;
	}
	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectNameList(List<String> subjectNameList) {
		this.subjectNameList = subjectNameList;
	}
	/**
	 * @return the classDetails
	 */
	public String getClassDetails() {
		return classDetails;
	}
	/**
	 * @param classDetails the classDetails to set
	 */
	public void setClassDetails(String classDetails) {
		this.classDetails = classDetails;
	}
	/**
	 * @return the noOfSubscriptionMonth
	 */
	public String getNoOfSubscriptionMonth() {
		return noOfSubscriptionMonth;
	}
	/**
	 * @param noOfSubscriptionMonth the noOfSubscriptionMonth to set
	 */
	public void setNoOfSubscriptionMonth(String noOfSubscriptionMonth) {
		this.noOfSubscriptionMonth = noOfSubscriptionMonth;
	}
	/**
	 * @return the productStatusType
	 */
	public String getProductStatusType() {
		return productStatusType;
	}
	/**
	 * @param productStatusType the productStatusType to set
	 */
	public void setProductStatusType(String productStatusType) {
		this.productStatusType = productStatusType;
	}
	/**
	 * @return the offeringPrice
	 */
	public String getOfferingPrice() {
		return offeringPrice;
	}
	/**
	 * @param offeringPrice the offeringPrice to set
	 */
	public void setOfferingPrice(String offeringPrice) {
		this.offeringPrice = offeringPrice;
	}
}
