package com.deprommet.mini2.team6.model.storylinejob;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "story_line_job")
public class StoryLineJob {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean isFinish;

	@CreatedDate
	private LocalDateTime createdAt;

	public static StoryLineJob create() {
		StoryLineJob storyLineJob = new StoryLineJob();
		storyLineJob.isFinish = false;
		return storyLineJob;
	}
}
