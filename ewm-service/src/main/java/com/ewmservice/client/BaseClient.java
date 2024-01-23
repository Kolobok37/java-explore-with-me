package com.ewmservice.client;

import com.ewmservice.dto.dto.AppDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected AppDto get(String path, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null);
    }

    protected <T> AppDto post(String path, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, null, body);
    }

    private <T> AppDto makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        AppDto statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.getForObject(path, AppDto.class, parameters);
            } else {
                statsServerResponse = rest.getForObject(path, AppDto.class);
            }
        } catch (HttpStatusCodeException e) {
            return new AppDto("1", "1", 1);
        }
        return statsServerResponse;
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
