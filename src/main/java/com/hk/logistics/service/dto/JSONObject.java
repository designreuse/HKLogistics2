package com.hk.logistics.service.dto;

import org.json.simple.JSONValue;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;


@SuppressWarnings("unchecked")
public abstract class JSONObject implements Serializable, Cloneable {

    private static Logger logger = LoggerFactory.getLogger(JSONObject.class);

    /**
     *
     */
    private static final long serialVersionUID = 1036213105972756617L;

    public static final String DEFAULT_ENCODING = "UTF-8";

    private static final Set<Class> numberClasses = new HashSet<Class>();

    static {
        numberClasses.add(Long.class);
        numberClasses.add(Integer.class);
        numberClasses.add(Short.class);
        numberClasses.add(Double.class);
        numberClasses.add(Float.class);
        numberClasses.add(Byte.class);
        numberClasses.add(BigDecimal.class);
        numberClasses.add(BigInteger.class);
    }

    ;

    public static void append(String key, Object value, StringBuilder strBuilder) {
        strBuilder.append("\"");
        strBuilder.append(key);
        strBuilder.append("\":");
    }

    public static void appendValue(Object value, StringBuilder strBuilder) {
        if (value == null) {
            strBuilder.append("null");
        } else if (value instanceof String) {
            strBuilder.append("\"");
            strBuilder.append(escape((String) value));
            strBuilder.append("\"");
        } else if (value.getClass().equals(Boolean.class) || value.getClass().equals(Boolean.TYPE)) {
            strBuilder.append(value.toString());
        } else if (Map.class.isAssignableFrom(value.getClass())) {
            Map<?, ?> map = (Map<?, ?>) value;
            appendJSONMap(map, strBuilder);
        } else if (value.getClass().isArray()) {
            appendJSONList(Arrays.asList(value), strBuilder);
        } else if (Collection.class.isAssignableFrom(value.getClass())) {
            appendJSONList((Collection) value, strBuilder);
        } else if (value instanceof Date) {
            /*Calendar c = Calendar.getInstance();
            c.setTime((Date) value);
            strBuilder.append("\"new Date(");
            strBuilder.append(c.get(Calendar.YEAR));
            strBuilder.append(",");
            strBuilder.append((c.get(Calendar.MONTH)));
            strBuilder.append(",");
            strBuilder.append(c.get(Calendar.DAY_OF_MONTH));
            strBuilder.append(",");
            strBuilder.append(c.get(Calendar.HOUR_OF_DAY));
            strBuilder.append(",");
            strBuilder.append(c.get(Calendar.MINUTE));
            strBuilder.append(",0)\"");*/

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            strBuilder.append("\"");
            strBuilder.append(dateFormat.format((Date) value));
            strBuilder.append("\"");
        }
        // else if (value instanceof Double) {
        // return decimalFormat.format(value);
        // }
        else if (value.getClass().isEnum()) {
            strBuilder.append(value.toString());
        } else if (value instanceof JSONObject) {
            strBuilder.append(((JSONObject) value).toJSON());
        } else if (numberClasses.contains(value.getClass())) {
            //strBuilder.append(Math.round(Double.valueOf(value.toString())));
            strBuilder.append(value.toString());
        } else if (value instanceof File) {
            String valueToAppend = null;
            File file = (File) value;
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                valueToAppend = new String(Base64.encodeBase64(IOUtils.toByteArray(fileInputStream)));
        /*byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(Base64.encode(bytes));*/
            } catch (IOException ioe) {
                logger.error("Error encountered while parsing file in JSONObject", ioe);
            }

            if (StringUtils.isNotBlank(valueToAppend)) {
                strBuilder.append("\"");
                strBuilder.append(escape(valueToAppend));
                strBuilder.append("\"");
            } else {
                strBuilder.append(valueToAppend);
            }
        } else {
            //strBuilder.append(net.sf.json.JSONObject.fromObject(value).toString());
            org.json.JSONObject jsonObject = new org.json.JSONObject(value);
            jsonObject.remove("class");
            strBuilder.append(jsonObject.toString());
        }
    }

    public static void appendJSONMap(Map<?, ?> someMap, StringBuilder strBuilder) {
        Iterator<?> iterator = someMap.keySet().iterator();
        strBuilder.append("{");
        while (iterator.hasNext()) {
            Object key = iterator.next();
            strBuilder.append("\"");
            strBuilder.append(key);
            strBuilder.append("\":");
            appendValue(someMap.get(key), strBuilder);
            if (iterator.hasNext()) {
                strBuilder.append(",");
            }
        }
        strBuilder.append("}");
    }

    public static void appendJSONList(Collection<?> someList, StringBuilder strBuilder) {
        Iterator<?> iterator = someList.iterator();
        strBuilder.append("[");
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            appendValue(obj, strBuilder);
            if (iterator.hasNext()) {
                strBuilder.append(",");
            }
        }
        strBuilder.append("]");
    }

    private final void appendJSON(StringBuilder strBuilder) {
        appendKeyValuePair(getKeys(), getValues(), strBuilder);
    }

    private static void appendKeyValuePair(List<String> keys, List<Object> values, StringBuilder strBuilder) {
        strBuilder.append("{");
        for (int i = 0; i < keys.size(); i++) {
            strBuilder.append("\"");
            strBuilder.append(keys.get(i));
            strBuilder.append("\":");
            appendValue(values.get(i), strBuilder);
            if (i < keys.size() - 1) {
                strBuilder.append(",");
            }
        }
        strBuilder.append("}");
    }

    public final Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<String, Object>();
        List<String> keys = getKeys();
        List<Object> values = getValues();
        for (int i = 0; i < keys.size(); i++) {
            data.put(keys.get(i), values.get(i));
        }
        return data;
    }

    public static final Map<String, Object> toMap(String json) {
        return (Map<String, Object>) JSONValue.parse(json);
    }

    public String toJSON() {
        StringBuilder strBuilder = new StringBuilder();
        appendJSON(strBuilder);
        return strBuilder.toString();
    }

    protected abstract List<String> getKeys();

    protected abstract List<Object> getValues();

    public static String escape(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            switch (ch) {
                case 34: // '"'
                    sb.append("\\\"");
                    break;
                case 92: // '\\'
                    sb.append("\\\\");
                    break;
                case 8: // '\b'
                    sb.append("\\b");
                    break;
                case 12: // '\f'
                    sb.append("\\f");
                    break;
                case 10: // '\n'
                    sb.append("\\n");
                    break;
                case 13: // '\r'
                    sb.append("\\r");
                    break;
                case 9: // '\t'
                    sb.append("\\t");
                    break;
                case 47: // '/'
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= 0 && ch <= '\037') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }

                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
                    break;
            }
        }
        return sb.toString();
    }
}
