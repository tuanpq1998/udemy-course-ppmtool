/**
 * @author admin
 * @date 07-02-2020
 */

package com.udemy.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.ppmtool.entity.Backlog;
import com.udemy.ppmtool.entity.Project;
import com.udemy.ppmtool.entity.User;
import com.udemy.ppmtool.exception.ProjectIdentifierException;
import com.udemy.ppmtool.exception.ProjectNotFoundException;
import com.udemy.ppmtool.repository.BacklogRepository;
import com.udemy.ppmtool.repository.ProjectRepository;
import com.udemy.ppmtool.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdate(Project project, String username) {

		User user = userRepository.findByUsername(username);
		project.setUser(user);
		project.setProjectLeader(username);
		String identifier = project.getProjectIdentifier().toUpperCase();
		project.setProjectIdentifier(identifier);

		if (project.getId() == null) {
			// not have id case
			Backlog backlog = new Backlog();
			project.setBacklog(backlog);
			backlog.setProject(project);
			backlog.setProjectIdentifier(identifier);
			try {
				return projectRepository.save(project);
			} catch (Exception e) {
				throw new ProjectIdentifierException("Project identifier has existed!");
			}
		} else {
			// have id case
			Project existingProject = projectRepository.findByProjectIdentifier(identifier);
			if (existingProject == null)
				throw new ProjectNotFoundException("Project with ID: '"+identifier+"' cannot be updated because it doesn't exist");
			else if (!existingProject.getProjectLeader().equals(username))
				throw new ProjectNotFoundException("Project not found in your account");
			project.setBacklog(backlogRepository.findByProjectIdentifier(identifier));
		}
		return projectRepository.save(project);
	}

	public Project findByIdentifier(String projectId, String username) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null)
			throw new ProjectIdentifierException("Project identifier hasn't existed!");
		if (!project.getProjectLeader().equals(username))
			throw new ProjectNotFoundException("Project not found in your account!");
		return project;
	}

	public Iterable<Project> findAllByUsername(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteByIdentifier(String projectId, String username) {
		Project p = findByIdentifier(projectId.toUpperCase(), username);
		projectRepository.delete(p);
	}
}
