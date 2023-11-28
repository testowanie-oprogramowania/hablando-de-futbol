package com.testowanie.football.mapper;

import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.dto.resource.CommentResource;
import com.testowanie.football.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResource toCommentResource(Comment comment) {
        return CommentResource.builder()
                .id(comment.getId())
                .nickname(comment.getNickname())
                .content(comment.getContent())
                .thumbsUp(comment.getThumbsUp())
                .thumbsDown(comment.getThumbsDown())
                .build();
    }

    public Comment fromCreateCommentRequest(CreateCommentRequest commentRequest) {
        return Comment.builder()
                .nickname(commentRequest.nickname())
                .content(commentRequest.content())
                .build();
    }
}
