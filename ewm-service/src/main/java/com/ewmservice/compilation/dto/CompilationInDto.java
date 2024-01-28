package com.ewmservice.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
public class CompilationInDto {
    Boolean pinned;
    @NotBlank
    @Length(min = 1, max = 50)
    String title;
    List<Integer> events;

}
