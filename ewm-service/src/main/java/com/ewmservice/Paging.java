package com.ewmservice;

import com.ewmservice.exception.ValidationDataException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class Paging {
    public static Pageable paging(Integer from, Optional<Integer> size) {
        Pageable pageable;
        if (from < 0 || size.isPresent() && size.get() < 1) {
            throw new ValidationDataException("Date is not valid.");
        }
        pageable = size.map(integer -> PageRequest.of(from / integer, integer)).orElseGet(() -> PageRequest.of(from, 100));
        return pageable;
    }
}