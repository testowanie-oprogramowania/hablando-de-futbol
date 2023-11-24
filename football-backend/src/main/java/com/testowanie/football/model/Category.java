package com.testowanie.football.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @OneToMany
    private Set<Article> articles;
}
