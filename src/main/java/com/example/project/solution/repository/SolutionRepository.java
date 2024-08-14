package com.example.project.solution.repository;

import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.domain.Solution;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long>, JpaSpecificationExecutor<Solution> {
    //Page<Solution> findByDifficulty(Difficulty difficulty);


    public interface Specification<T> {
        Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb);
    }


    //실행되는지 모름
    public static Specification<Solution> findByDifficulty(Difficulty difficulty) {
        return new Specification<Solution>() {
            @Override
            public Predicate toPredicate(Root<Solution> root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("country"),difficulty.toString());
            }
        };
    }

}
