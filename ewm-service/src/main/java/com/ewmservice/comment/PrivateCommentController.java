package com.ewmservice.comment;

import com.ewmservice.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class PrivateCommentController {
    @Autowired
    PrivateCommentServiceImpl commentService;

    @PostMapping("/{userId}/event/{eventId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Integer userId,
                                                    @PathVariable Integer eventId, @RequestBody @Valid Comment text) {
        return commentService.createComment(userId, eventId, text);
    }

    @PatchMapping("/{userId}/event/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer userId,
                                                    @PathVariable Integer commentId, @RequestBody @Valid Comment text) {
        return commentService.updateComment(userId, commentId, text);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable @Positive Integer commentId) {
        return commentService.getComment(commentId);
    }

    @GetMapping("/{userId}/comment")
    public ResponseEntity<List<CommentDto>> getMyComments(@PathVariable @Positive Integer userId) {
        return commentService.getMyComments(userId);
    }

    @GetMapping("/event/{eventId}/comment")
    public ResponseEntity<List<CommentDto>> getEventComments(@PathVariable @Positive Integer eventId) {
        return commentService.getEventComments(eventId);
    }
}

