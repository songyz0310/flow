package com.song.flow.boot.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static String getJson(Object obj) {
		try {
			mapper.setSerializationInclusion(Include.NON_NULL);
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		return "";
	}

	public static <T> T readJson(String jsonStr, Class<T> tClass) {
		try {
			return mapper.readValue(jsonStr, tClass);
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * json字符串转换为list集合
	 * 
	 * @param jsonString
	 * @param tClass
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T jsonStrToList(String jsonString, Class<?> tClass) {
		T t = null;
		try {
			t = mapper.readValue(jsonString, mapper.getTypeFactory().constructParametricType(List.class, tClass));
		} catch (JsonParseException e) {
			log.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return t;
	}

	/**
	 * json字符串转换为Map集合
	 * 
	 * @param jsonString
	 * @param mapKeyClass
	 *            keyClass
	 * @param mapValueClass
	 *            valueClass
	 * @return
	 */
	public static <T> T jsonStrToMap(String jsonString, Class<?> mapKeyClass, Class<?> mapValueClass) {
		T t = null;
		try {
			t = mapper.readValue(jsonString,
					mapper.getTypeFactory().constructParametricType(Map.class, mapKeyClass, mapValueClass));
		} catch (JsonParseException e) {
			log.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return t;
	}

}
