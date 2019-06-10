package cn.company.common.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author donaldhan
 */
public final class JsonUtils {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        /*解决含有反斜杠的转义字符，Jackson报错*/
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转成json字符
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("对象转成json字符异常");
        }

        return null;
    }

    /**
     * json字符串转成map
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonParseException e) {
            logger.error("json字符串转成map异常",e);
        } catch (JsonMappingException e) {
            logger.error("json字符串转成map异常",e);
        } catch (IOException e) {
            logger.error("json字符串转成map异常",e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Object> jsonToLinkedHashMap(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, new TypeReference<LinkedHashMap<String, Object>>() {
            });
        } catch (JsonParseException e) {
            logger.error("json字符串转成LinkedHashMap异常",e);
        } catch (JsonMappingException e) {
            logger.error("json字符串转成LinkedHashMap异常",e);
        } catch (IOException e) {
            logger.error("json字符串转成LinkedHashMap异常",e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T mapJsonToBean(String json, Class<T> cl) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return objectMapper.convertValue(json, cl);
    }

    /**
     * json字符串转成bean
     *
     * @param json
     * @param cl
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBean(String json, Class<T> cl) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return objectMapper.readValue(json, cl);
        } catch (JsonParseException e) {
            logger.error("json字符串转成Bean异常",e);
        } catch (JsonMappingException e) {
            logger.error("json字符串转成Bean异常",e);
        } catch (IOException e) {
            logger.error("json字符串转成Bean异常",e);
        }
        return null;
    }

    /**
     * json字符串转成List
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Object> jsonToList(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, List.class);
        } catch (JsonParseException e) {
            logger.error("json字符串转成List异常",e);
        } catch (JsonMappingException e) {
            logger.error("json字符串转成List异常",e);
        } catch (IOException e) {
            logger.error("json字符串转成List异常",e);
        }
        return null;
    }


    /*public static <T> List<T> jsonToList(String json, Class<T> cl) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        List<T> arrayList = new ArrayList<T>();
        List<Object> list = jsonToList(json);
        for (Object object : list) {
            arrayList.add(jsonToBean(objectToJson(object), cl));
        }

        return arrayList;
    }*/

    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToList(String json, Class<T> cl) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, getCollectionType(objectMapper, ArrayList.class, cl));
        } catch (IOException e) {
            logger.error("json字符串转成List异常",e);
        }
        return null;
    }

    /**
     * 泛型转换-json转换
     *
     * @param json
     * @param collectionClass 泛型的Collection类
     * @param cl              转换类
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToList(String json, Class<?> collectionClass, Class<T> cl) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, getCollectionType(objectMapper, collectionClass, cl));
        } catch (IOException e) {
            logger.error("json字符串转成List异常",e);
        }
        return null;
    }


    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  转换类
     * @return JavaType Java类型
     * @since 1.0
     */
    private static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
