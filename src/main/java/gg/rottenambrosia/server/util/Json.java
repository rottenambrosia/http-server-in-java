package gg.rottenambrosia.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Json {
    private static ObjectMapper objectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper () {
        ObjectMapper om = new ObjectMapper ();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    public static JsonNode parse (String jsonSource) throws JsonProcessingException {
        return objectMapper.readTree(jsonSource);
    }

    public static <T> T fromJson (JsonNode jsonSource, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonSource, valueType);
    }

    public static JsonNode toJson (Object object) throws JsonProcessingException {
        return objectMapper.valueToTree(object);
    }

    public static String turnToString (JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }


    private static String generateJson (Object object, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if (pretty) {
            objectWriter = objectWriter.withDefaultPrettyPrinter();
        }
        return objectWriter.writeValueAsString(object);
    }
}