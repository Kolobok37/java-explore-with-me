package com.ewmservice.compilation.isPublic;

import org.springframework.http.ResponseEntity;

public interface PublicCompilationsService {
    public ResponseEntity<Object> getCompilations(Boolean pinned, Integer from, Integer size);

    public ResponseEntity<Object> getCompilation(Integer compId);
}
