package org.flow.boot.modeler.controller.flowable;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import org.flowable.ui.common.service.exception.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 查询配置Json资源<br>
 * 扩展国际化切换,资源缓存
 * 
 * @author songyz<br>
 *         CreateTime: 2018-09-21 18:14
 */
//@RestController
//@RequestMapping("/app")
public class MyStencilSetResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyStencilSetResource.class);

    private static final String P = File.separator;
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String BPMN_JSON_NAME = "stencilset_bpmn.json";
    private static final String CMMN_JSON_NAME = "stencilset_cmmn.json";

    private final ClassLoader classLoader = this.getClass().getClassLoader();

    private static Map<String, JsonNode> cache = new WeakHashMap<>();

    @Autowired
    protected ObjectMapper objectMapper;

    @RequestMapping(value = "/rest/stencil-sets/editor", method = RequestMethod.GET, produces = "application/json")
    public JsonNode getStencilSetForEditor(@RequestHeader HttpHeaders headers) {
        return getResource(headers, BPMN_JSON_NAME);
    }

    @RequestMapping(value = "/rest/stencil-sets/cmmneditor", method = RequestMethod.GET, produces = "application/json")
    public JsonNode getCmmnStencilSetForEditor(@RequestHeader HttpHeaders headers) {
        return getResource(headers, CMMN_JSON_NAME);
    }

    private JsonNode getResource(HttpHeaders headers, String jsonName) {
        List<LanguageRange> acceptLanguages = headers.getAcceptLanguage();
        String language = acceptLanguages.get(0).getRange().toLowerCase();

        String key = "stencilset" + P + language + P + jsonName;
        JsonNode jsonNode = cache.get(key);
        if (Objects.isNull(jsonNode)) {
            InputStream inputStream = classLoader.getResourceAsStream(key);
//            if (Objects.isNull(inputStream))
                inputStream = classLoader.getResourceAsStream("stencilset" + P + DEFAULT_LANGUAGE + P + jsonName);

            try {
                jsonNode = objectMapper.readTree(inputStream);

                cache.put(key, jsonNode);
            }
            catch (Exception e) {
                LOGGER.error("Error reading file:[{}] set json", key, e);
                throw new InternalServerErrorException("Error reading [" + key + "] stencil set json");
            }
        }

        return jsonNode;
    }

}