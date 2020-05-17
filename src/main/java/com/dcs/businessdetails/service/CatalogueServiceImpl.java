/**
 * 
 */
package com.dcs.businessdetails.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dcs.businessdetails.exception.CourseNotFoundException;
import com.dcs.businessdetails.model.ProductDetails;
import com.dcs.businessdetails.util.CatalogueTagNameType;

/**
 * @author DebashisNath
 *
 */
@Service
public class CatalogueServiceImpl implements CatalogueService{
	private File courseCatalogue;
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	private Document document;
	private NodeList nodeList;
	
	/**
	 * 
	 */
	public CatalogueServiceImpl() {
		this.courseCatalogue = new File("Catalogue/CourseCatalogue.xml");
		this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			this.documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(courseCatalogue);
			document.getDocumentElement().normalize();
			nodeList = document.getElementsByTagName(CatalogueTagNameType.COURSE);
		} catch (Exception e) {
	       	e.printStackTrace();
		}
		
		
	}

	/* (non-Javadoc)
	 * @see com.dcs.businessdetails.service.CatalogueService#extractProductDetails(java.lang.String)
	 */
	@Override
	public ProductDetails extractProductDetails(String productId) throws CourseNotFoundException {
		ProductDetails productDetails = null;
		if(this.nodeList != null) {
			for(int count = 0; count <nodeList.getLength(); count++) {
				Node node = nodeList.item(count);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getAttribute(CatalogueTagNameType.ID).equalsIgnoreCase(productId)) {
						productDetails = new ProductDetails();
						productDetails.setProductId(element.getAttribute(CatalogueTagNameType.ID));
						productDetails.setProductDescription(element
								.getElementsByTagName(CatalogueTagNameType.COURSE_DESC).item(0).getTextContent());
						productDetails.setClassDetails(
								element.getElementsByTagName(CatalogueTagNameType.CLASS_NAME).item(0).getTextContent());

						List<String> subjectNameList = new ArrayList<>();
						//NamedNodeMap nodeMap = node.getAttributes();
						NodeList subjectList = element.getElementsByTagName(CatalogueTagNameType.SUBJECT_LIST).item(0)
								.getChildNodes();
						for (int i = 0; i < subjectList.getLength(); i++) {
							Node subject = subjectList.item(i);
							if (subject.getNodeType() == Node.ELEMENT_NODE) {
								Element sElement = (Element) subject;
								/*System.out.println("attr name : " + sElement.getNodeName());
								System.out.println("attr value : " + sElement.getNodeValue());
								System.out.println("text value : " + sElement.getTextContent());*/
								subjectNameList.add(sElement.getTextContent());
							}
						}

						/*NodeList subjectList = element.getElementsByTagName(CatalogueTagNameType.SUBJECT_LIST);
						for (int temp = 0; temp < subjectList.getLength(); temp++) {
							Node subject = subjectList.item(temp);
							if (subject.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) node;
								subjectNameList.add(eElement.getElementsByTagName(CatalogueTagNameType.SUBJECT_NAME)
										.item(0).getTextContent());
							}
						}*/
						productDetails.setSubjectNameList(subjectNameList);
						productDetails.setNoOfSubscriptionMonth(element
								.getElementsByTagName(CatalogueTagNameType.NO_OF_SUB_MONTH).item(0).getTextContent());
						productDetails.setOfferingPrice(
								element.getElementsByTagName(CatalogueTagNameType.PRICE).item(0).getTextContent());
					}
				}
			}
		}
		return productDetails;
	}

	/* (non-Javadoc)
	 * @see com.dcs.businessdetails.service.CatalogueService#extractCourseIdAndPriceMap()
	 */
	@Override
	public Map<String, String> extractCourseIdAndPriceMap() throws XMLParseException {
		Map<String, String> courseIdAndPriceMap = null;
		if (this.nodeList != null) {
			courseIdAndPriceMap = new HashMap<>();
			for (int count = 0; count < nodeList.getLength(); count++) {
				Node node = nodeList.item(count);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String productId = element.getAttribute(CatalogueTagNameType.ID);
					String price = element.getElementsByTagName(CatalogueTagNameType.PRICE).item(0).getTextContent();
					courseIdAndPriceMap.put(productId, price);
				}
			}
		}
		return courseIdAndPriceMap;
	}
	
		
}
