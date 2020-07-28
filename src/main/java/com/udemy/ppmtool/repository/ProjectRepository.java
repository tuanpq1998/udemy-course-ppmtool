/**
 * @author admin
 * @date 07-02-2020
 */

package com.udemy.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udemy.ppmtool.entity.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	public Project findByProjectIdentifier(String projectId);
	
	public Iterable<Project> findAll();
	
	public Iterable<Project> findAllByProjectLeader(String username);

	public Project getById(Long id);
}
