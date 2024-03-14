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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Applicant")
public class Applicant {
	
	public Applicant() {}
	
	@Column(name = "Resume Link")
	private String resumeLink;
	
	@Column(name = "Work Experience")
	private String wrkExp;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applicant_id")
	private long applicantID;
	
	@JoinColumn(name = "user_id", unique = true)
    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	private User user;
	
	@OneToMany(mappedBy="appDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Application> applications;
	
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public String getResumeLink() {
		return resumeLink;
	}

	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
	}

	public String getWrkExp() {
		return wrkExp;
	}

	public void setWrkExp(String wrkExp) {
		this.wrkExp = wrkExp;
	}

	public long getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(long applicantID) {
		this.applicantID = applicantID;
	}
	
	
}
