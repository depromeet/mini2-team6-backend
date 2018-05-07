package com.deprommet.mini2.team6.model.candidatestoryline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidate_story_line")
public class CandidateStoryLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long storyLineJobId;
	private String contents;
	private int likeCount;
	private int warmCount;
	private int sadCount;
	private int wantContinueCount;

	@CreatedDate
	private LocalDateTime createdAt;

	public void increaseLikeCount() {
		likeCount++;
	}
	
	public void increaseWarmCount() {
		warmCount++;
	}
	
	public void increaseSadCount() {
		sadCount++;
	}
	
	public void increaseWantContinueCount() {
		wantContinueCount++;
	}

	public Integer calculateTotal() {
		return likeCount + warmCount + sadCount + wantContinueCount;
	}
}