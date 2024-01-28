package com.ewmservice.comment.dto;


import com.ewmservice.comment.Comment;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MapperComment {
    public static CommentDto mapToCommentDto(Comment comment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new CommentDto(comment.getId(), comment.getEvent().getId(), comment.getCommentator().getName(),
                comment.getCreated().format(formatter), comment.getText());
    }

    public static List<CommentDto> mapToCommentsDto(List<Comment> comment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (comment != null) {
            return comment.stream().map(MapperComment::mapToCommentDto).collect(Collectors.toList());
        }
        return null;
    }
}
