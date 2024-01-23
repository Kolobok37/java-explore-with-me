package com.ewmservice.client;

import com.ewmservice.dto.dto.AppDto;
import com.ewmservice.dto.dto.HitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Map;

@Service
public class EventClient extends BaseClient {
    @Autowired
    public EventClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public AppDto getStats(List<String> uris) {
        Map<String, Object> parameters = Map.of(
                "uris", uris
        );
        return get("uris={uris}", parameters);
    }

    public AppDto addHit(HitDto hitDto) {
        return post("/hit", hitDto);
    }
}

