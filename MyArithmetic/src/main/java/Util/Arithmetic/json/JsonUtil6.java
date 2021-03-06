package Util.Arithmetic.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON������
 *
 * @Modify chenjzh
 * @Date 10:06 2016/12/27
 */
public class JsonUtil6 {

    // reuse the object mapper to save memory footprint
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper indentMapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        indentMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static <T> T readValue(File src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(src, valueType);
    }

    public static <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(content, valueType);
    }

    public static <T> T readValue(Reader src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(src, valueType);
    }

    public static <T> T readValue(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(src, valueType);
    }

    public static <T> T readValue(byte[] src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return mapper.readValue(src, valueType);
    }

    public static Map<String, String> readValueAsMap(String content) throws IOException {
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };
        return mapper.readValue(content, typeRef);
    }

    public static void writeValueIndent(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        indentMapper.writeValue(out, value);
    }

    public static void writeValue(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        mapper.writeValue(out, value);
    }

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    public static byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        return mapper.writeValueAsBytes(value);
    }

    public static String writeValueAsIndentString(Object value) throws JsonProcessingException {
        return indentMapper.writeValueAsString(value);
    }

}
