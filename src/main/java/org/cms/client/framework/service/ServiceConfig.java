package org.cms.client.framework.service;

import javafx.collections.ObservableList;
import org.cms.client.framework.session.SessionConfig;
import org.cms.core.files.assignment.Assignment;
import org.cms.core.files.submission.Submission;

public class ServiceConfig {

	private SessionConfig sessionConfig;
	private ObservableList<Assignment> assignmentList;
	private ObservableList<Submission> submissionList;

	public SessionConfig getSessionConfig() {
		return sessionConfig;
	}

	public void setSessionConfig(SessionConfig sessionConfig) {
		this.sessionConfig = sessionConfig;
	}

	public ObservableList<Assignment> getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(ObservableList<Assignment> assignmentList) {
		this.assignmentList = assignmentList;
	}

	public ObservableList<Submission> getSubmissionList() {
		return submissionList;
	}

	public void setSubmissionList(ObservableList<Submission> submissionList) {
		this.submissionList = submissionList;
	}
}
