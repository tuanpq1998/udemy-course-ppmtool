package com.udemy.ppmtool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false, unique = true)
	private String projectSequence;
	@NotBlank(message = "Please include a prj summary")
	private String summary;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dueDate;
	private Date create_At;
	private Date update_At;
	@Column(updatable = false)
	private String projectIdentifier;
	
	@PrePersist
	protected void onCreate() {
		create_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		update_At = new Date();
	}

	public ProjectTask(Long id, String projectSequence,
			@NotBlank(message = "Please include a prj summary") String summary, String acceptanceCriteria,
			String status, Integer priority, Date dueDate, Date create_At, Date update_At, String projectIdentifier) {
		super();
		this.id = id;
		this.projectSequence = projectSequence;
		this.summary = summary;
		this.acceptanceCriteria = acceptanceCriteria;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
		this.create_At = create_At;
		this.update_At = update_At;
		this.projectIdentifier = projectIdentifier;
	}

	public ProjectTask() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreate_At() {
		return create_At;
	}

	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}

	public Date getUpdate_At() {
		return update_At;
	}

	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", create_At=" + create_At + ", update_At=" + update_At
				+ ", projectIdentifier=" + projectIdentifier + "]";
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	
}
