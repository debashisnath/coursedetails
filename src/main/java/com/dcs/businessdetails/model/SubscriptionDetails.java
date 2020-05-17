/**
 * 
 */
package com.dcs.businessdetails.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author DebashisNath
 *
 */
@Document
public class SubscriptionDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7550604321175845475L;

	private Date subscriptionStartDate;
	private Date subscriptionEndDate;
	/**
	 * @return the subscriptionStartDate
	 */
	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}
	/**
	 * @param subscriptionStartDate the subscriptionStartDate to set
	 */
	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}
	/**
	 * @return the subscriptionEndDate
	 */
	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}
	/**
	 * @param subscriptionEndDate the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

}
