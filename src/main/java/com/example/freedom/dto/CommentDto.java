package com.example.freedom.dto;

import com.example.freedom.domain.Comment;
import com.example.freedom.domain.Member;
import com.example.freedom.domain.Post;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long postId;
    private String content;
    private LocalDate commentDate;
    private Long memberId;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCommentDate(comment.getCommentDate());
        if(comment.getMember()!=null){
            commentDto.setMemberId(comment.getMember().getId());
        }
        if(comment.getPost()!=null){
            commentDto.setPostId(comment.getPost().getId());
        }
        return commentDto;
    }
}