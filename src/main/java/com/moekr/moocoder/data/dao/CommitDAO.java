package com.moekr.moocoder.data.dao;

import com.moekr.moocoder.data.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitDAO extends JpaRepository<Commit, String> {
	Commit findById(int commitId);

	List<Commit> findAllByResult_IdAndFinishedOrderByIdAsc(int resultId, boolean finished);
}