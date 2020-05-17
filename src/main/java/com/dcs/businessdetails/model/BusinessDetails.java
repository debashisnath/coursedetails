/**
 * 
 */
package com.dcs.businessdetails.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author DebashisNath
 *
 */
@Document
public class BusinessDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3861835615500955170L;
	private ProfileDetails profileDetails;
	private List<ProductDetails> productList;
	private List<ProductDetails> expiredProductList;
	private String purchaseStatus;
	private Integer noOfCourseTaken;
	@Id
	private String emailId;
	
	
	/**
	 * @return the expiredProductList
	 */
	public List<ProductDetails> getExpiredProductList() {
		return expiredProductList;
	}
	/**
	 * @param expiredProductList the expiredProductList to set
	 */
	public void setExpiredProductList(List<ProductDetails> expiredProductList) {
		this.expiredProductList = expiredProductList;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((noOfCourseTaken == null) ? 0 : noOfCourseTaken.hashCode());
		result = prime * result + ((productList == null) ? 0 : productList.hashCode());
		result = prime * result + ((profileDetails == null) ? 0 : profileDetails.hashCode());
		result = prime * result + ((purchaseStatus == null) ? 0 : purchaseStatus.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessDetails other = (BusinessDetails) obj;
		if (noOfCourseTaken == null) {
			if (other.noOfCourseTaken != null)
				return false;
		} else if (!noOfCourseTaken.equals(other.noOfCourseTaken))
			return false;
		if (productList == null) {
			if (other.productList != null)
				return false;
		} else if (!productList.equals(other.productList))
			return false;
		if (profileDetails == null) {
			if (other.profileDetails != null)
				return false;
		} else if (!profileDetails.equals(other.profileDetails))
			return false;
		if (purchaseStatus == null) {
			if (other.purchaseStatus != null)
				return false;
		} else if (!purchaseStatus.equals(other.purchaseStatus))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BusinessDetails [profileDetails=" + profileDetails + ", productList=" + productList
				+ ", purchaseStatus=" + purchaseStatus + ", noOfCourseTaken=" + noOfCourseTaken + "]";
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
	 * @return the productList
	 */
	public List<ProductDetails> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<ProductDetails> productList) {
		this.productList = productList;
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
