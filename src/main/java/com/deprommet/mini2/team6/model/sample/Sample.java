package com.deprommet.mini2.team6.model.sample;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "sample")
public class Sample {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String message;
}
