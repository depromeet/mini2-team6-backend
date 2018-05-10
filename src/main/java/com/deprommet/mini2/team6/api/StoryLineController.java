package com.deprommet.mini2.team6.api;

import com.deprommet.mini2.team6.model.candidatestoryline.CandidateStoryLine;
import com.deprommet.mini2.team6.model.candidatestoryline.CandidateStoryLineRepository;
import com.deprommet.mini2.team6.model.storyline.StoryLine;
import com.deprommet.mini2.team6.model.storyline.StoryLineRepository;
import com.deprommet.mini2.team6.model.storylinejob.StoryLineJob;
import com.deprommet.mini2.team6.model.storylinejob.StoryLineJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StoryLineController {
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 1);

    @Autowired
    private StoryLineRepository storyLineRepository;

    @Autowired
    private StoryLineJobRepository storyLineJobRepository;

    @Autowired
    private CandidateStoryLineRepository candidateStoryLineRepository;

    @GetMapping("/api/storylines")
    public List<StoryLine> findAllStoryLines() {
        return storyLineRepository.findAll(new Sort(Sort.Direction.ASC, "createdAt"));
    }

    @GetMapping("/api/storylines/latest")
    public ResponseEntity<StoryLine> findLatestStoryLine() {
        Optional<StoryLine> storyLine = storyLineRepository.findAll(new Sort(Sort.Direction.DESC, "createdAt")).stream()
                .findFirst();

        return storyLine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/api/storylines/count")
    public StoryLineCountDto countAllStoryLine() {
        return StoryLineCountDto.create(storyLineRepository.count());
    }

    @RequestMapping(value = "/api/storylines/candidates", method = RequestMethod.GET)
    public List<CandidateStoryLine> findAllCandidateStoryLine() {
        Optional<StoryLineJob> storyLineJobOptional = storyLineJobRepository.findFirstOrderByCreatedAtDesc(PAGE_REQUEST);

        if (storyLineJobOptional.isPresent()) {
            final StoryLineJob storyLineJob = storyLineJobOptional.get();
            return candidateStoryLineRepository.findAllByStoryLineJobId(storyLineJob.getId());
        }

        return Collections.emptyList();
    }

    @RequestMapping(value = "/api/storylines/candidates/top1", method = RequestMethod.GET)
    public ResponseEntity<CandidateStoryLine> findTop1CandidateStoryLine() {
        Optional<StoryLineJob> storyLineJobOptional = storyLineJobRepository.findFirstOrderByCreatedAtDesc(PAGE_REQUEST);

        if (storyLineJobOptional.isPresent()) {
            final StoryLineJob storyLineJob = storyLineJobOptional.get();
            final Optional<CandidateStoryLine> candidateStoryLine =  candidateStoryLineRepository.findAllByStoryLineJobId(storyLineJob.getId())
                    .stream()
                    .sorted((storyLine1, storyLine2) -> storyLine2.calculateTotal().compareTo(storyLine1.calculateTotal()))
                    .findFirst();

            if (candidateStoryLine.isPresent()){
                return ResponseEntity.ok(candidateStoryLine.get());
            }
        }

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/api/storylines/candidates/top3", method = RequestMethod.GET)
    public List<CandidateStoryLine> findTop3CandidateStoryLine() {
        Optional<StoryLineJob> storyLineJobOptional = storyLineJobRepository.findFirstOrderByCreatedAtDesc(PAGE_REQUEST);

        if (storyLineJobOptional.isPresent()) {
            final StoryLineJob storyLineJob = storyLineJobOptional.get();
            return candidateStoryLineRepository.findAllByStoryLineJobId(storyLineJob.getId())
                    .stream()
                    .sorted((storyLine1, storyLine2) -> storyLine2.calculateTotal().compareTo(storyLine1.calculateTotal()))
                    .limit(3)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }


    @RequestMapping(value = "/api/storylines/candidates", method = RequestMethod.POST)
    public CandidateStoryLine insertCandidateStoryLine(@RequestBody CandidateStoryLineDto candidateStoryLineDto) throws Exception {
        Optional<StoryLineJob> storyLineJobOptional = storyLineJobRepository.findFirstOrderByCreatedAtDesc(PAGE_REQUEST);

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

        throw new Exception("Doesn't exist story line job");
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

    @Transactional
    @GetMapping("/api/storylines/candidates/{id}/warm")
    public ResponseEntity<CandidateStoryLine> warmCandidateStoryLine(@PathVariable Long id) {
        Optional<CandidateStoryLine> candidateStoryLineOptional = candidateStoryLineRepository.findById(id);

        if (candidateStoryLineOptional.isPresent()) {
            final CandidateStoryLine candidateStoryLine = candidateStoryLineOptional.get();
            candidateStoryLine.increaseWarmCount();
            return ResponseEntity.ok(candidateStoryLine);
        }

        return ResponseEntity.badRequest().build();
    }

    @Transactional
    @GetMapping("/api/storylines/candidates/{id}/sad")
    public ResponseEntity<CandidateStoryLine> sadCandidateStoryLine(@PathVariable Long id) {
        Optional<CandidateStoryLine> candidateStoryLineOptional = candidateStoryLineRepository.findById(id);

        if (candidateStoryLineOptional.isPresent()) {
            final CandidateStoryLine candidateStoryLine = candidateStoryLineOptional.get();
            candidateStoryLine.increaseSadCount();
            return ResponseEntity.ok(candidateStoryLine);
        }

        return ResponseEntity.badRequest().build();
    }

    @Transactional
    @GetMapping("/api/storylines/candidates/{id}/wantContinue")
    public ResponseEntity<CandidateStoryLine> wantContinueCandidateStoryLine(@PathVariable Long id) {
        Optional<CandidateStoryLine> candidateStoryLineOptional = candidateStoryLineRepository.findById(id);

        if (candidateStoryLineOptional.isPresent()) {
            final CandidateStoryLine candidateStoryLine = candidateStoryLineOptional.get();
            candidateStoryLine.increaseWantContinueCount();
            return ResponseEntity.ok(candidateStoryLine);
        }

        return ResponseEntity.badRequest().build();
    }
}
