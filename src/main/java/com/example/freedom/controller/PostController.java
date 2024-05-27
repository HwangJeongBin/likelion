package com.example.freedom.controller;

import com.example.freedom.domain.Post;
import com.example.freedom.dto.PostDto;
import com.example.freedom.service.MemberService;
import com.example.freedom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public PostController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity<PostDto> registerPost(@RequestBody PostDto postDto, Long memberId) {
        Post post = new Post();
        post.setMember(memberService.getMemberById(memberId).get());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post registeredPost = postService.registerPost(post);
        return ResponseEntity.ok(PostDto.from(registeredPost));
    }

    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto dto = PostDto.from(post).from(post);
            postDtos.add(dto);
        }
        return ResponseEntity.ok(postDtos);
    }

    // ID로 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id) {
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isPresent()) {
            PostDto dto = PostDto.from(postOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name="id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name="id") Long id, @RequestBody PostDto postDto) {
        try {
            Post updatedPost = postService.updatePost(id, postDto);
            return ResponseEntity.ok(PostDto.from(updatedPost));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
