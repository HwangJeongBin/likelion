package com.example.freedom.controller;

import com.example.freedom.domain.Comment;
import com.example.freedom.dto.CommentDto;
import com.example.freedom.service.CommentService;
import com.example.freedom.service.MemberService;
import com.example.freedom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, MemberService memberService, PostService postService) {
        this.commentService = commentService;
        this.memberService = memberService;
        this.postService = postService;
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentDto> registerComment(@RequestBody CommentDto commentDto, Long memberId, Long postId) {
        Comment comment = new Comment();
        comment.setMember(memberService.getMemberById(memberId).get());
        comment.setPost(postService.getPostById(postId).get());
        comment.setContent(commentDto.getContent());

        Comment registeredComment = commentService.registerComment(comment);
        return ResponseEntity.ok(CommentDto.from(registeredComment));
    }

    // 모든 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = CommentDto.from(comment).from(comment);
            commentDtos.add(dto);
        }
        return ResponseEntity.ok(commentDtos);
    }

    // ID로 댓글 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name="id") Long id) {
        Optional<Comment> commentOptional = commentService.getCommentById(id);
        if (commentOptional.isPresent()) {
            CommentDto dto = CommentDto.from(commentOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable(name="id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    // 댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name="id") Long id, @RequestBody CommentDto commentDto) {
        try {
            Comment updatedComment = commentService.updateComment(id, commentDto);
            return ResponseEntity.ok(CommentDto.from(updatedComment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
