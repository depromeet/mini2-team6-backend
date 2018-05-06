package com.deprommet.mini2.team6.model.candidatestoryline;

import com.deprommet.mini2.team6.model.candidatestoryline.CandidateStoryLine;
import com.deprommet.mini2.team6.model.storyline.StoryLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateStoryLineRepository extends JpaRepository<CandidateStoryLine, Long> {
	List<CandidateStoryLine> findAllByStoryLineJobId(Long storyLineJobId);
}
