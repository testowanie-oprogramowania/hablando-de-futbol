package com.testowanie.football.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String nickname;

    @NonNull
    @Column(length = 500)
    private String content;

    @NonNull
    @Column(nullable = false)
    @Builder.Default
    private Integer thumbsUp = 0;

    @NonNull
    @Column(nullable = false)
    @Builder.Default
    private Integer thumbsDown = 0;

}
