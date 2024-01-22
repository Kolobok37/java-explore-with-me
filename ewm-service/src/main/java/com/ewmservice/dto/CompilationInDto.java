package com.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CompilationInDto {
    Boolean pinned;
    @NotBlank
    @Length(min = 1, max = 50)
    String title;
    Integer[] events;

}
