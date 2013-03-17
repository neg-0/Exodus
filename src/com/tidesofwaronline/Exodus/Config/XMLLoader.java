package com.tidesofwaronline.Exodus.Config;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.tidesofwaronline.Exodus.CustomItem.CustomItem;

import java.io.File;
import java.util.UUID;
 
public class XMLLoader {
	
	UUID weaponsUUID = UUID.fromString("bb15a547-acae-4482-9c5f-a45515610466");
 
  public static void main(String argv[]) {
 
    try {
 
	File fXmlFile = new File("E:\\Documents\\Tides of War\\Exodus\\Exodus\\Exodus.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();
 
	System.out.println("Root element: " + doc.getDocumentElement().getNodeName());	
 
	NodeList nList = doc.getElementsByTagName("Entity");
 
	System.out.println("----------------------------");
 
	for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);
  
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode;
			
			if (eElement.getAttribute("ObjectTemplateReferenceName").equalsIgnoreCase("Custom_Item")) {
				parseItem(eElement);
				NodeList properties = eElement.getElementsByTagName("Properties").item(0).getChildNodes();
					for (int i = 0; i < properties.getLength(); i++) {
						if (properties.item(i).getNodeType() != 3) {
							Node node = properties.item(i); 
							System.out.print(node.getAttributes().getNamedItem("Name").getNodeValue());
							System.out.println(": " + node.getTextContent());
						}
							
					}
			}
		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
  
  private static void parseItem(Element eElement) {
	System.out.println("Parsing defined item: " + eElement.getElementsByTagName("DisplayName").item(0).getTextContent().trim());
	new CustomItem(eElement);
}

public static Element getElementByAttributeValue(Node rootElement, String attributeValue) {

	    if (rootElement != null && rootElement.hasChildNodes()) {
	        NodeList nodeList = rootElement.getChildNodes();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	            Node subNode = nodeList.item(i);

	            if (subNode.hasAttributes()) {
	                NamedNodeMap nnm = subNode.getAttributes();

	                for (int j = 0; j < nnm.getLength(); j++) {
	                    Node attrNode = nnm.item(j);

	                    if (attrNode.getNodeType() == Node.ATTRIBUTE_NODE) {
	                        Attr attribute = (Attr) attrNode;

	                        if (attributeValue.equals(attribute.getValue())) {
	                            return (Element)subNode;
	                        } else {
	                            return getElementByAttributeValue(subNode, attributeValue);
	                        }
	                    }
	                }               
	            }
	        }
	    }

	    return null;
	}
 
}
