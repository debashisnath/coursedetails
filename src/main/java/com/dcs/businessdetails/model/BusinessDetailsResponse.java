/**
 * 
 */
package com.dcs.businessdetails.model;

import java.util.List;

/**
 * @author DebashisNath
 *
 */
public class BusinessDetailsResponse {

	private ProfileDetails profileDetails;
	private List<ProductDetails> productDetails;
	private String purchaseStatus;
	private Integer noOfCourseTaken;
	
	/**
	 * @return the productDetails
	 */
	public List<ProductDetails> getProductDetails() {
		return productDetails;
	}
	/**
	 * @param productDetails the productDetails to set
	 */
	public void setProductDetails(List<ProductDetails> productDetails) {
		this.productDetails = productDetails;
	}
	/**
	 * @return the noOfCourseTaken
	 */
	public Integer getNoOfCourseTaken() {
		return noOfCourseTaken;
	}
	/**
	 * @param noOfCourseTaken the noOfCourseTaken to set
	 */
	public void setNoOfCourseTaken(Integer noOfCourseTaken) {
		this.noOfCourseTaken = noOfCourseTaken;
	}
	/**
	 * @return the profileDetails
	 */
	public ProfileDetails getProfileDetails() {
		return profileDetails;
	}
	/**
	 * @param profileDetails the profileDetails to set
	 */
	public void setProfileDetails(ProfileDetails profileDetails) {
		this.profileDetails = profileDetails;
	}
	/**
	 * @return the purchaseStatus
	 */
	public String getPurchaseStatus() {
		return purchaseStatus;
	}
	/**
	 * @param purchaseStatus the purchaseStatus to set
	 */
	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
}
