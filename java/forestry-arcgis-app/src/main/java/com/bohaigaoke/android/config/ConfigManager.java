package com.bohaigaoke.android.config;


import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ConfigManager {

	private final static String FILE_XML_APPCONFIG = "appConfig.xml";
	public final static String FILE_XML_CONFIG = "config.xml";
	private final static String PROPERTY_USERCONFIG_FILE_PATH = "USERCONFIG_FILE_PATH";
	private final static String PROPERTY_CONFIG_FILES = "CONFIG_FILE_NAMES";
	public final static String PROPERTY_ITEMS_DISPLAY = "PROPERTIES_DISPLAY"; // 用户可通过UI修改的属性

	private static ConfigManager instanceCM = null;
	private static Context context;

	private static String userConfigFilePath; // 用户配置文件：存放在设备存储卡的文件，用户可修改
	private static Map<String, String> propertyMap = new HashMap<String, String>();

	public void init(Context context) {
		ConfigManager.context = context;
	}

	public static ConfigManager getInstance() {
		if (instanceCM == null) {
			instanceCM = new ConfigManager();
		}

		return instanceCM;
	}

	public static String getProperty(String key) {
		if (propertyMap.isEmpty()) {
			instanceCM.initConfig();
		}
		String value = propertyMap.get(key);
		return value;
	}

	private void initConfig() {

		try {
			// 加载系统配置文件：Asset目录下
			InputStream in = context.getAssets().open(FILE_XML_APPCONFIG);
			loadXmlFile(in);
			in.close();
			Log.i("ConfigManager", "load config file:"+FILE_XML_APPCONFIG);

			// 加载用户配置文件: 设备存储卡下
			String root = ConfigUtil.getSdCardPath();
			String dir = propertyMap.get(PROPERTY_USERCONFIG_FILE_PATH);
			String desPath = root + (dir.startsWith("/") ? "" : "/") + dir;
			ConfigUtil.copyAssetFileToDisk(context, FILE_XML_CONFIG, desPath);

			userConfigFilePath = desPath + (desPath.endsWith("/") ? "" : "/")
					+ FILE_XML_CONFIG;
			loadXmlFile(userConfigFilePath);
			Log.i("ConfigManager", "load config file:"+userConfigFilePath);

			String configFiles = propertyMap.get(PROPERTY_CONFIG_FILES);
			if (configFiles != null && configFiles.length() > 0) {
				String[] fileNames = configFiles.split(",");
				for (String fileName : fileNames) {
					InputStream inputStream = context.getAssets()
							.open(fileName);
					loadXmlFile(inputStream);
					inputStream.close();
					Log.i("ConfigManager", "load config file:"+fileName);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadXmlFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				Document xmlDoc = builder.parse("file://" + filePath);
				Element root = xmlDoc.getDocumentElement();
				NodeList nodeList = root.getChildNodes();

				if (nodeList != null) {
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node node = nodeList.item(i);
						if (node != null) {
							String key = node.getNodeName();
							Node nodeChild = node.getFirstChild();
							if (nodeChild != null) {
								String value = nodeChild.getNodeValue();
								propertyMap.put(key, value);
							}
						}
					}
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadXmlFile(InputStream in) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(in);
			Element root = xmlDoc.getDocumentElement();
			NodeList nodeList = root.getChildNodes();

			if (nodeList != null) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					if (node != null) {
						String key = node.getNodeName();
						Node nodeChild = node.getFirstChild();
						if (nodeChild != null) {
							String value = nodeChild.getNodeValue();
							propertyMap.put(key, value);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Map<String, String> getUserConfigProperties() {
		Map<String, String> userConfigProperties = new HashMap<String, String>();
		String itemDisplay = propertyMap.get(PROPERTY_ITEMS_DISPLAY);
		userConfigProperties.put(PROPERTY_ITEMS_DISPLAY, itemDisplay);
		if (itemDisplay != null) {
			String items[] = itemDisplay.split(",");
			for (String item : items) {
				userConfigProperties.put(item, propertyMap.get(item));
				String key = item + "_KEY";
				userConfigProperties.put(key, propertyMap.get(key));
			}
		}

		return userConfigProperties;
	}

	public void updateXmlFile(Map<String, String> propertyMap) {
		updateXmlFile(userConfigFilePath, propertyMap);
		// 重新加载配置文件信息
		initConfig();
	}

	private void updateXmlFile(String filePath, Map<String, String> propertyMap) {
		File file = new File(filePath);
		if (file.exists()) {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse("file://" + filePath);

				Element root = doc.getDocumentElement();
				NodeList nodeList = root.getChildNodes();

				if (nodeList != null) {
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node node = nodeList.item(i);
						if (node != null) {
							String key = node.getNodeName();
							String value = propertyMap.get(key);
							if (value != null) {
								Node nodeChild = node.getFirstChild();
								if (nodeChild != null) {
									nodeChild.setNodeValue(value);
								}
							}
						}
					}
				}

				Transformer transformer = TransformerFactory.newInstance()
						.newTransformer();
				// //编码
				// transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.transform(new DOMSource(doc), new StreamResult(
						new FileOutputStream(file)));

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void loadPropertyFile(String fileName) {
		Properties properties = new Properties();
		try {
			InputStream in = context.getAssets().open(fileName);
			properties.load(in);

			Enumeration<?> enumeration = properties.propertyNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String value = properties.getProperty(key);

				propertyMap.put(key, value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
