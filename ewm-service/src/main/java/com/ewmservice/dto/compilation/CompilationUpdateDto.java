package com.ewmservice.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationUpdateDto {
    Boolean pinned;
    @Length(min = 1, max = 50)
    String title;
    List<Integer> events;

}