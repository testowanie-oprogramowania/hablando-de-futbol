package com.testowanie.football.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends Auditable {

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
    @Column(length = 10000, nullable = false)
    private String content;

    private String photoUrl;

    @NonNull
    @ManyToOne
    private Category category;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;
}
