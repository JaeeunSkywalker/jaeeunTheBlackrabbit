package com.jaeeun.util;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

//OAuth2AuthorizationResponseType은 Jackson이 기본적으로 역직렬화할 수 없는 복잡한 타입임
//그래서 커스텀 JsonDeserializer를 작성해서 Jackson에 등록함
//OAuth2AuthorizationResponseType 역직렬화기
public class OAuth2ResponseTypeDeserializer extends JsonDeserializer<OAuth2AuthorizationResponseType> {

    @Override
    public OAuth2AuthorizationResponseType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String value = node.get("value").asText();
        // return OAuth2AuthorizationResponseType.valueOf(value);
        try {
            Constructor<OAuth2AuthorizationResponseType> constructor = OAuth2AuthorizationResponseType.class
                    .getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            return constructor.newInstance(value);
        } catch (Exception e) {
            throw new IOException("Could not create OAuth2AuthorizationResponseType", e);
        }
    }
}
