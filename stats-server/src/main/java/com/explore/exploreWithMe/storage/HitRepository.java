package com.explore.exploreWithMe.storage;


import com.explore.exploreWithMe.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HitRepository extends JpaRepository<Hit, Long> {
}
