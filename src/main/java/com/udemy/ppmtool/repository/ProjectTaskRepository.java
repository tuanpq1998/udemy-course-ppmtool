package com.udemy.ppmtool.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.udemy.ppmtool.entity.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);

	ProjectTask findByProjectSequence(String projectSequence);
}
