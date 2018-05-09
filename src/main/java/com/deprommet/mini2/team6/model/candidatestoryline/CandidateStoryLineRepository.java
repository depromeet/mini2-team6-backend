package com.deprommet.mini2.team6.model.candidatestoryline;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateStoryLineRepository extends JpaRepository<CandidateStoryLine, Long> {
	List<CandidateStoryLine> findAllByStoryLineJobId(Long storyLineJobId);
}
