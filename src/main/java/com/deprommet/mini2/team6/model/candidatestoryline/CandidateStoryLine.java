package com.deprommet.mini2.team6.model.candidatestoryline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

	public void increaseLikeCount() {
		likeCount++;
	}
}