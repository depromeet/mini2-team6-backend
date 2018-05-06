package com.deprommet.mini2.team6.model.storylinejob;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface StoryLineJobRepository extends JpaRepository<StoryLineJob, Long> {
	@Query(value = "SELECT s FROM StoryLineJob s WHERE s.isFinish = false ORDER BY s.createdAt DESC")
	Optional<StoryLineJob> findFirstOrderByCreatedAtDesc(PageRequest pageRequest);
}
