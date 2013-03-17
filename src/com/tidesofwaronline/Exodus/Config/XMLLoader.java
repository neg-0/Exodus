package com.tidesofwaronline.Exodus.Config;

import java.io.File;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tidesofwaronline.Exodus.CustomItem.CustomItem;

public class XMLLoader {

	UUID weaponsUUID = UUID.fromString("bb15a547-acae-4482-9c5f-a45515610466");

	public static void main(String argv[]) {

		try {

			File fXmlFile = new File(
					"E:\\Documents\\Tides of War\\Exodus\\Exodus\\Exodus.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element: "
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Entity");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					if (eElement.getAttribute("ObjectTemplateReferenceName")
							.equalsIgnoreCase("Custom_Item")) {
						parseItem(eElement);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseItem(Element eElement) {
		System.out.println("Parsing defined item: "
				+ eElement.getElementsByTagName("DisplayName").item(0)
						.getTextContent().trim());

		NodeList properties = eElement.getElementsByTagName("Properties")
				.item(0).getChildNodes();
		for (int i = 0; i < properties.getLength(); i++) {
			if (properties.item(i).getNodeType() != 3) {
				Node node = properties.item(i);
				System.out.print(node.getAttributes().getNamedItem("Name")
						.getNodeValue());
				System.out.println(": " + node.getTextContent());
			}

		}

		new CustomItem(eElement);
	}

}
