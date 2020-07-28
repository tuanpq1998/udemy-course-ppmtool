package com.udemy.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udemy.ppmtool.entity.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
	Backlog findByProjectIdentifier(String projectIdentifier);
}
