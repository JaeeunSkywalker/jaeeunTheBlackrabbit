package com.jaeeun.util;

import java.util.Base64;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    // 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력 받아 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    // 객체를 직렬화해 쿠키의 값으로 변환
    // public static String serialize(Object obj) {
    // return
    // Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    // }

    // 쿠키를 역직렬화해 객체로 변환
    // 이건 보안 문제로 deprecated된 방식
    // public static <T> T deserialize(Cookie cookie, Class<T> cls) {
    // return
    // cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
    // }

    // 이 코드도 사용은 가능하나 보안 이슈가 있다.
    // public static <T> T deserialize(Cookie cookie, Class<T> cls) {
    // byte[] bytes = Base64.getUrlDecoder().decode(cookie.getValue());
    // try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    // ObjectInputStream ois = new ObjectInputStream(bis)) {
    // return cls.cast(ois.readObject());
    // } catch (IOException | ClassNotFoundException e) {
    // throw new RuntimeException("Deserialization failed", e);
    // }
    // }

    // Java 기본 직렬화/역직렬화에 비해 보안상 더 안전하고 다른 시스템과의 호환성도 좋은
    // JSON을 사용하기 위해 Jackson 라이브러리를 사용해서 아래 코드를 구현했다.
    // 역직렬화기를 ObjectMapper에 등록하기
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OAuth2AuthorizationResponseType.class, new OAuth2ResponseTypeDeserializer());
        objectMapper.registerModule(module);
    }

    // 객체를 JSON 문자열로 직렬화 후 Base64로 인코딩
    public static String serialize(Object object) throws Exception {
        String jsonString = objectMapper.writeValueAsString(object);
        return Base64.getUrlEncoder().encodeToString(jsonString.getBytes());
    }

    // Base64 디코딩 후 JSON 문자열을 객체로 역직렬화
    public static <T> T deserialize(Cookie cookie, Class<T> cls) throws Exception {
        byte[] bytes = Base64.getUrlDecoder().decode(cookie.getValue());
        return objectMapper.readValue(new String(bytes), cls);
    }
}
