package com.tidesofwaronline.Exodus.Config;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLLoader {

	UUID weaponsUUID = UUID.fromString("bb15a547-acae-4482-9c5f-a45515610466");
	
	private static HashMap<String, String> enchantments = new HashMap<String, String>();
	private static HashMap<String, HashMap<String, String>> enums = new HashMap<String, HashMap<String, String>>();

	public static void main(String argv[]) {

		long time = System.currentTimeMillis();
		
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

			NodeList enumList = doc
					.getElementsByTagName("EnumerationPropertyDefinition");

			int enumCount = 0;
			
			for (int temp = 0; temp < enumList.getLength(); temp++) {
				Node nNode = enumList.item(temp);

				{
					if (nNode.getAttributes().getNamedItem("BasedOn") != null) {
						if (nNode
								.getAttributes()
								.getNamedItem("BasedOn")
								.getNodeValue()
								.equalsIgnoreCase(
										"6aaefa6f-e7d8-4a57-94ab-d742f04c126f")) {
							parseEnum((Element) nNode);
							enumCount++;
						}
					}
				}
			}
			
			System.out.println("Parsed " + enumCount + " enums.");

			NodeList entityList = doc.getElementsByTagName("Entity");

			int enchantCount = 0;
			int itemCount = 0;
			
			for (int temp = 0; temp < entityList.getLength(); temp++) {

				Node nNode = entityList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					if (eElement.getAttribute("ObjectTemplateReferenceName")
							.equalsIgnoreCase("Enchantment")) {
						parseEnchantment(eElement);
						enchantCount++;
					}
				}
			}
			
			System.out.println("Parsed " + enchantCount + " enchantments.");

			for (int temp = 0; temp < entityList.getLength(); temp++) {

				Node nNode = entityList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					if (eElement.getAttribute("ObjectTemplateReferenceName")
							.equalsIgnoreCase("Custom_Item")) {
						parseItem(eElement);
						itemCount++;
					}
				}
			}
			
			System.out.println("Parsed " + itemCount + " items.");
			
			System.out.println("Total time: " + (System.currentTimeMillis() - time) + "ms");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseEnchantment(Element eElement) {
		String name = eElement.getElementsByTagName("ExternalId").item(0)
				.getTextContent().trim();
		String uuid = eElement.getAttribute("Guid");
		//System.out.println("Parsing Enchantment: " + name);
		//System.out.println("UUID: " + uuid);
		enchantments.put(uuid, name);
	}

	private static String getEnchantmentByUUID(String uuid) {
		return enchantments.get(uuid);
	}

	private static void parseEnum(Element eElement) {
		String displayName = eElement.getElementsByTagName("DisplayName").item(0)
				.getTextContent().trim();
		System.out.println("Found enum "
				+ displayName);
		HashMap<String, String> map = new HashMap<String, String>();
		
		Element e = (Element) eElement.getElementsByTagName("Values").item(0);
		
		NodeList Value = e.getElementsByTagName("Value");
		NodeList TechnicalName = e.getElementsByTagName("TechnicalName");
		
		for (int i = 0; i < Value.getLength(); i++) {
			map.put(Value.item(i).getTextContent(), TechnicalName.item(i).getTextContent());
		}
		enums.put(displayName, map);

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
				String nodeName = node.getAttributes().getNamedItem("Name")
						.getNodeValue();

				if (nodeName.equalsIgnoreCase("Ench")) {
					for (int x = 0; x < node.getChildNodes().getLength(); x++) {
						if (node.getChildNodes().item(x).hasAttributes()) {
							System.out.print(node.getAttributes()
									.getNamedItem("Name").getNodeValue()
									+ ": ");
							System.out.println(getEnchantmentByUUID(node
									.getChildNodes().item(x).getAttributes()
									.getNamedItem("GuidRef").getNodeValue()
									.trim()));
						}
					}
				} else if (nodeName.equalsIgnoreCase("Glow")) {
					System.out.print(node.getAttributes().getNamedItem("Name")
							.getNodeValue()
							+ ": ");
					System.out.println(node.getTextContent()
							.replace("0", "false").replace("1", "true"));
				} else if (nodeName.equalsIgnoreCase("Color")) {

				}

				else {
					System.out.print(node.getAttributes().getNamedItem("Name")
							.getNodeValue()
							+ ": ");
					System.out.println(node.getTextContent());
				}
			}
		}
	}
}
