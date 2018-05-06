package com.deprommet.mini2.team6.api;

import com.deprommet.mini2.team6.model.candidatestoryline.CandidateStoryLine;
import com.deprommet.mini2.team6.model.candidatestoryline.CandidateStoryLineRepository;
import com.deprommet.mini2.team6.model.storyline.StoryLine;
import com.deprommet.mini2.team6.model.storyline.StoryLineRepository;
import com.deprommet.mini2.team6.model.storylinejob.StoryLineJob;
import com.deprommet.mini2.team6.model.storylinejob.StoryLineJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class StoryLineController {
	@Autowired
	private StoryLineRepository storyLineRepository;

	@Autowired
	private StoryLineJobRepository storyLineJobRepository;

	@Autowired
	private CandidateStoryLineRepository candidateStoryLineRepository;

	@GetMapping("/api/storylines")
	public List<StoryLine> findAllStoryLines() {
		return storyLineRepository.findAll(new Sort(Sort.Direction.DESC, "createdAt"));
	}

	@GetMapping("/api/storylines/z")
	public StoryLineCountDto countAllStoryLine() {
		return StoryLineCountDto.create(storyLineRepository.count());
	}

	@RequestMapping(value = "/api/storylines/candidates", method = RequestMethod.GET)
	public List<CandidateStoryLine> findAllCandidateStoryLine() {
		Optional<StoryLineJob> storyLineJobOptional = storyLineJobRepository.findFirstOrderByCreatedAtDesc();

		if (storyLineJobOptional.isPresent()) {
			final StoryLineJob storyLineJob = storyLineJobOptional.get();
			return candidateStoryLineRepository.findAllByStoryLineJobId(storyLineJob.getId());
		}

		return Collections.emptyList();
	}

	@RequestMapping(value = "/api/storylines/candidates", method = RequestMethod.POST)
	public CandidateStoryLine insertCandidateStoryLine(@RequestBody CandidateStoryLineDto candidateStoryLineDto) {
		Optional<StoryLineJob> storyLineJobOptional = storyLineJobRepository.findFirstOrderByCreatedAtDesc();

		if (storyLineJobOptional.isPresent()) {
			final StoryLineJob storyLineJob = storyLineJobOptional.get();
			final CandidateStoryLine candidateStoryLine = CandidateStoryLine.builder()
				.storyLineJobId(storyLineJob.getId())
				.contents(candidateStoryLineDto.getContent())
				.likeCount(0)
				.sadCount(0)
				.warmCount(0)
				.wantContinueCount(0)
				.build();

			return candidateStoryLineRepository.save(candidateStoryLine);
		}

		final StoryLineJob storyLineJob = storyLineJobRepository.save(StoryLineJob.create());

		final CandidateStoryLine candidateStoryLine = CandidateStoryLine.builder()
			.storyLineJobId(storyLineJob.getId())
			.contents(candidateStoryLineDto.getContent())
			.likeCount(0)
			.sadCount(0)
			.warmCount(0)
			.wantContinueCount(0)
			.build();

		return candidateStoryLineRepository.save(candidateStoryLine);
	}

	@Transactional
	@GetMapping("/api/storylines/candidates/{id}/like")
	public ResponseEntity<CandidateStoryLine> likeCandidateStoryLine(@PathVariable Long id) {
		Optional<CandidateStoryLine> candidateStoryLineOptional = candidateStoryLineRepository.findById(id);

		if (candidateStoryLineOptional.isPresent()) {
			final CandidateStoryLine candidateStoryLine = candidateStoryLineOptional.get();
			candidateStoryLine.increaseLikeCount();
			return ResponseEntity.ok(candidateStoryLine);
		}

		return ResponseEntity.badRequest().build();
	}
}
