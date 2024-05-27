package com.example.freedom.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")// 외래키를 지정, member_id가 외래키로 사용됨
    private Member member;
    private String title;
    private String content;
    @CreationTimestamp
    private LocalDateTime postDate;

    @OneToMany(mappedBy="post")
    private List<Comment> comments= new ArrayList<>();

}