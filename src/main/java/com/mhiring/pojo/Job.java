package com.mhiring.pojo;

import java.util.ArrayList;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Jobs")
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")	
	private long jobID;
	
	@Column(name = "role_name")
	@NotBlank
	@Size(min = 2, message = "Rolename is too short, minimum 2 characters")
	@Size(max = 200, message = "Rolename is too large, maximum 200 characters")
	private String roleName;
	
	@Column(name = "description")
	@NotBlank
	@Size(min = 2, message = "Skills entry is too short, minimum 2 characters")
	@Size(max = 200, message = "Skills entry is too large, maximum 200 characters")
	private String descrp;
	
	@Column(name = "work_ex")
	@NotBlank
	@Size(max = 200, message = "Work Experience is too large, maximum 200 characters")
	private String expReq;
	
	@NotBlank
	@Size(min = 2, message = "Location is too short, minimum 2 characters")
	@Size(max = 200, message = "Location is too large, maximum 200 characters")
	private String location;
	
        @ManyToOne
        @JoinColumn(name = "recruiter_id")
	private Recruiter recruiter;   
        
	@OneToMany(mappedBy="jobDetails", fetch = FetchType.EAGER)
	private List<Application> applications = new ArrayList<>();
	
	@Column(name = "Status")
	private String status;
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Application> getApplications() {
		return applications;
	}
	
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}
 
	
	public Job() {
	}

	public long getJobID() {
		return jobID;
	}

	public void setJobID(long jobID) {
		this.jobID = jobID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}

	public String getExpReq() {
		return expReq;
	}

	public void setExpReq(String expReq) {
		this.expReq = expReq;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
