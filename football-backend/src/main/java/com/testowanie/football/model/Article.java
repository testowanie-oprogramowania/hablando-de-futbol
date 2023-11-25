package com.testowanie.football.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = 255, nullable = false)
    private String title;

    @NonNull
    @ManyToOne
    private Editor editor;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @NonNull
    @Column(length = 10000, nullable = false)
    private String content;

    private String photoUrl;

    @NonNull
    @ManyToOne
    private Category category;

    @NonNull
    @OneToMany
    private Set<Comment> comments;
}
