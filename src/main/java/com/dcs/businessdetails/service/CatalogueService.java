/**
 * 
 */
package com.dcs.businessdetails.service;

import java.util.Map;

import javax.management.modelmbean.XMLParseException;

import com.dcs.businessdetails.exception.CourseNotFoundException;
import com.dcs.businessdetails.model.ProductDetails;

/**
 * @author DebashisNath
 *
 */
public interface CatalogueService {
	public ProductDetails extractProductDetails(String productId) throws CourseNotFoundException;
	
	public Map<String, String> extractCourseIdAndPriceMap() throws XMLParseException;
}
