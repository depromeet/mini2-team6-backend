package com.deprommet.mini2.team6.api.hello;

import com.deprommet.mini2.team6.model.sample.Sample;
import com.deprommet.mini2.team6.model.sample.SampleRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {
	@Autowired
	private SampleRepository sampleRepository;

	@GetMapping("/sample")
	public List<Sample> hello() {
		sampleRepository.save(EnhancedRandom.random(Sample.class, "id"));
		return sampleRepository.findAll();
	}
}
