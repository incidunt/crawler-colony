package com.dang.colony.resources.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author dangqihe
 *
 */
public class PropertiesLoaderFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoaderFactory.class);

    private static PropertiesLoaderFactory instance;

    private static Properties properties = new Properties();

    public static PropertiesLoaderFactory getInstance() {
        if (instance == null) {
            instance = new PropertiesLoaderFactory();
            instance.init();
        }
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

    private void init() {

        try {
            InputStream inputStream = PropertiesLoaderFactory.class.getClassLoader().getResourceAsStream("mongodb.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
