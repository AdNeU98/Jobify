package com.mhiring.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Recruiter")
public class Recruiter{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recruiter_id")
	private long recruiterID;
	
	@Column(name = "team_name")
	private String teamName;
	
	@Column(name = "expertise")
	private String expertise;

	@JoinColumn(name = "user_id", unique = true)
        @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	private User user;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="recruiter", fetch = FetchType.EAGER)
	private List<Job> jobsAdded;
	
	
	public List<Job> getJobsAdded() {
		return jobsAdded;
	}

	public void setJobsAdded(List<Job> jobsAdded) {
		this.jobsAdded = jobsAdded;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public Recruiter() {}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public long getRecruiterID() {
		return recruiterID;
	}

	public void setRecruiterID(long recruiterID) {
		this.recruiterID = recruiterID;
	}

}
