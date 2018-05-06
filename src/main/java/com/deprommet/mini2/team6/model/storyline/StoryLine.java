package com.deprommet.mini2.team6.model.storyline;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Table(name = "story_line")
public class StoryLine {
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
}
