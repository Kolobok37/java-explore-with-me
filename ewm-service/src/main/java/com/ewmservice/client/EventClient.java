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

    public List<AppDto> getStats(List<String> uris) {
        StringBuilder path = new StringBuilder("/stats?unique=true&uris=");
        for (int i = 0; i < uris.size(); i++) {
            path.append(uris.get(i));
            if (i != uris.size() - 1) {
                path.append(",");
            }
        }
        return get(path.toString());
    }

    public void addHit(HitDto hitDto) {
        post("/hit", hitDto);
    }
}

