package com.mhiring.pojo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Applications")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_id")
	private long applicationID;
	
	@Column(name = "status")
	private String appStatus;
	
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "job_id")
	private Job jobDetails;
	
    @ManyToOne
    @JoinColumn(name = "applicant_id")
	private Applicant appDetails;

	public Application() {}

	public long getApplicationID() {
		return applicationID;
	}

	public Job getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(Job jobDetails) {
		this.jobDetails = jobDetails;
	}

	public Applicant getAppDetails() {
		return appDetails;
	}

	public void setAppDetails(Applicant appDetails) {
		this.appDetails = appDetails;
	}

	public void setApplicationID(long applicationID) {
		this.applicationID = applicationID;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	
	
}
