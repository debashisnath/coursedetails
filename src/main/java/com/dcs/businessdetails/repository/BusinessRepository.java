package com.dcs.businessdetails.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dcs.businessdetails.model.BusinessDetails;

/**
 * @author DebashisNath
 *
 */
@Repository
public interface BusinessRepository extends MongoRepository<BusinessDetails, String> {

	//public BusinessDetails findByProfileDetailsEmailId(String emailId);
	
	public BusinessDetails findByEmailId(String emailId);
}
