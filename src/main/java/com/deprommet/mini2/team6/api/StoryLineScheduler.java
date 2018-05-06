package com.deprommet.mini2.team6.api;

import com.deprommet.mini2.team6.model.candidatestoryline.CandidateStoryLine;
import com.deprommet.mini2.team6.model.candidatestoryline.CandidateStoryLineRepository;
import com.deprommet.mini2.team6.model.storyline.StoryLine;
import com.deprommet.mini2.team6.model.storyline.StoryLineRepository;
import com.deprommet.mini2.team6.model.storylinejob.StoryLineJob;
import com.deprommet.mini2.team6.model.storylinejob.StoryLineJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Slf4j
@Service
public class StoryLineScheduler {
	private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 1);

	@Autowired
	private StoryLineRepository storyLineRepository;

	@Autowired
	private StoryLineJobRepository storyLineJobRepository;

	@Autowired
	private CandidateStoryLineRepository candidateStoryLineRepository;

	@Transactional
	@Scheduled(fixedDelay = 60 * 1000)
	public void doJob() {
		log.info("Start do job");
		Optional<StoryLineJob> storyLineJobOptional = storyLineJobRepository.findFirstOrderByCreatedAtDesc(PAGE_REQUEST);

		if (storyLineJobOptional.isPresent()) {
			final StoryLineJob storyLineJob = storyLineJobOptional.get();
			storyLineJob.finish();

			final Optional<CandidateStoryLine> candidateStoryLineOptional = candidateStoryLineRepository.findAllByStoryLineJobId(storyLineJob.getId())
				.stream().max(Comparator.comparing(CandidateStoryLine::calculateTotal));

			if (candidateStoryLineOptional.isPresent()) {
				final CandidateStoryLine candidateStoryLine = candidateStoryLineOptional.get();
				final StoryLine storyLine = StoryLine.builder()
					.contents(candidateStoryLine.getContents())
					.likeCount(candidateStoryLine.getLikeCount())
					.warmCount(candidateStoryLine.getWarmCount())
					.sadCount(candidateStoryLine.getSadCount())
					.wantContinueCount(candidateStoryLine.getWantContinueCount())
					.build();

				storyLineRepository.save(storyLine);
			}
		}

		storyLineJobRepository.save(StoryLineJob.create());
	}
}
