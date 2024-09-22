package com.example.project.solution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter

@NoArgsConstructor
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String inExample;

    @Setter
    private String outExample;

    @ManyToOne
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;

    public Example(String inExample, String outExample, Solution solution) {
        this.inExample = inExample;
        this.outExample = outExample;
        this.solution = solution; //관계설정
    }

}
