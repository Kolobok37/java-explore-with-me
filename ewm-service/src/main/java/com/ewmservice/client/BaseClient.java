package com.ewmservice.client;

import com.ewmservice.dto.dto.AppDto;
import com.ewmservice.dto.dto.HitDto;
import com.ewmservice.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected List<AppDto> get(String path) {
        return makeAndSendRequestGet(path, null);
    }

    protected <T> void post(String path, T body) {
        makeAndSendRequestPost(path, null, body);
    }

    private <T> List<AppDto> makeAndSendRequestGet(String path, @Nullable T body) {
        AppDto[] statsServerResponse;
        try {
            statsServerResponse = rest.getForObject(path, AppDto[].class);
        } catch (HttpStatusCodeException e) {
            throw new NotFoundException("Error giving stats");
        }
        return Arrays.stream(statsServerResponse).collect(Collectors.toList());
    }

    private <T> void makeAndSendRequestPost(String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        try {
            rest.postForObject(path, body, HitDto.class);
        } catch (HttpStatusCodeException e) {
            throw new NotFoundException("Error giving stats");
        }
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
