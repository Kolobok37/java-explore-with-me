package com.ewmservice.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer eventId;
    private String authorName;
    private String created;
    private String text;

}
