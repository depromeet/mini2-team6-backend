package com.deprommet.mini2.team6;

import com.deprommet.mini2.team6.model.Models;
import com.deprommet.mini2.team6.model.storyline.StoryLine;
import com.deprommet.mini2.team6.model.storyline.StoryLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableJpaAuditing
@EnableScheduling
@EnableJpaRepositories
@EntityScan(
	basePackageClasses = { Jsr310JpaConverters.class, Models.class }
)
@SpringBootApplication
public class Team6Application implements CommandLineRunner {
	@Autowired
	private StoryLineRepository storyLineRepository;

	public static void main(String[] args) {
		SpringApplication.run(Team6Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final StoryLine storyLine1 = StoryLine.builder()
			.storyLineJobId(1L)
			.contents("오늘도..내 옆자리에 서있는 이 남자를 흘깃 바라본다. 어벤져스에 새로 들어온지는 얼마 안됐지만, 내 마음에는 어느새 꽤 많이 들어와 있는 사람")
			.likeCount(12345)
			.warmCount(300)
			.sadCount(300)
			.wantContinueCount(300)
			.build();

		storyLineRepository.save(storyLine1);

		final StoryLine storyLine2 = StoryLine.builder()
			.storyLineJobId(1L)
			.contents("고개를 휘저으며 다시금 일에 집중을 해본다. 하지만 머릿속에서는 이미, 다른 남자와 이야기를 나누고 있는 그의 생각뿐이었다")
			.likeCount(12345)
			.warmCount(300)
			.sadCount(300)
			.wantContinueCount(300)
			.build();

		storyLineRepository.save(storyLine2);

		final StoryLine storyLine3 = StoryLine.builder()
			.storyLineJobId(1L)
			.contents("고개를 휘저으며 다시금 일에 집중을 해본다. 하지만 머릿속에서는 이미, 다른 남자와 이야기를 나누고 있는 그의 생각뿐이었다")
			.likeCount(12345)
			.warmCount(300)
			.sadCount(300)
			.wantContinueCount(300)
			.build();

		storyLineRepository.save(storyLine3);

		final StoryLine storyLine4 = StoryLine.builder()
			.storyLineJobId(1L)
			.contents("타노스…관련 이야기인가…신경쓰지 않으려 했지만 역시나 신경쓰인다. 아니, 애초에 어찌 신경을 쓰지 않을 수가 있을까. 처음 본 순간부터 첫눈에 반해버린 사람인데.")
			.likeCount(12345)
			.warmCount(300)
			.sadCount(300)
			.wantContinueCount(300)
			.build();

		storyLineRepository.save(storyLine4);
	}

}
