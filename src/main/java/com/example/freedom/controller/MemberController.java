package com.example.freedom.controller;

import com.example.freedom.domain.Member;
import com.example.freedom.dto.MemberDto;
import com.example.freedom.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")

public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping
    public ResponseEntity<MemberDto> registerMember(@RequestBody MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        Member registeredMember = memberService.registerMember(member);
        return ResponseEntity.ok(MemberDto.from(registeredMember));
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            MemberDto dto = MemberDto.from(member);
            memberDtos.add(dto);
        }
        return ResponseEntity.ok(memberDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable(name="id") Long id) {
        Optional<Member> memberOptional = memberService.getMemberById(id);
        if (memberOptional.isPresent()) {
            MemberDto dto = MemberDto.from(memberOptional.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(name="id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
    // 회원 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<MemberDto> UpdateMember(@PathVariable(name="id") Long id, @RequestBody MemberDto memberDto) {
        try {
            Member updatedMember = memberService.updateMember(id, memberDto);
            return ResponseEntity.ok(MemberDto.from(updatedMember));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}