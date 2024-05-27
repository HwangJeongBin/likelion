package com.example.freedom.dto;

import com.example.freedom.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime postDate;
    private Long memberId;

    public static PostDto from(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setPostDate(post.getPostDate());
        if(post.getMember()!=null){
            postDto.setMemberId(post.getMember().getId());
        }
        return postDto;
    }
}