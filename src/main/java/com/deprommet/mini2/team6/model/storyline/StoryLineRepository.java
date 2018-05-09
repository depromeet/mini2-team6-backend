package com.deprommet.mini2.team6.model.storyline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryLineRepository extends JpaRepository<StoryLine, Long> {
}
