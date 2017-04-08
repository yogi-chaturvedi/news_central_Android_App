package com.newscentral.service.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Yogesh on 03-12-2016.
 */
public class RestService {

    private static RestService _instance;
    private final RestTemplate restTemplate;

    private RestService() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }

    public static RestService instance() {
        if (_instance == null) {
            _instance = new RestService();
        }
        return _instance;
    }

    public <T> T get(String url, Class<T> responseType, Object... urlVariables) {
        return restTemplate.getForObject(url, responseType, urlVariables);
    }

    public <T> T post(String url, Object request, Class<T> responseType, Object... uriVariables) {
        return restTemplate.postForObject(url, request, responseType, uriVariables);
    }

    public HttpHeaders getHeaders(String url, Object... uriVariables) {
        return restTemplate.headForHeaders(url, uriVariables);
    }

    public void put(String url, Object request) {
        restTemplate.put(url, request);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
                                          Class<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

}
