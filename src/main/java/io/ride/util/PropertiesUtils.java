package io.ride.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesUtils {

    public static Map<String, Object> readResource(String filename) throws IOException {
        InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(filename);
        Map<String, Object> resources = new HashMap<>();
        ResourceBundle bundle = new PropertyResourceBundle(in);
        bundle.keySet().forEach(value -> resources.put(value, bundle.getObject(value)));
        return resources;
    }

    public static Object readResource(String filename, String key) throws IOException {
        InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(filename);
        ResourceBundle bundle = new PropertyResourceBundle(in);
        if (!bundle.containsKey(key)) {
            return null;
        }
        return bundle.getObject(key);
    }


}
