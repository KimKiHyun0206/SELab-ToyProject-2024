package com.example.project.solution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "example_id", nullable = false)
    private Long id;

    private String inExample;
    private String outExample;

    @ManyToOne
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;

    public Example(String inExample, String outExample, Solution solution) {
        this.inExample = inExample;
        this.outExample = outExample;
        this.setSolution(solution); // 관계 설정
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        if (!solution.getExamples().contains(this)) {
            solution.addExample(this);
        }
    }

}
