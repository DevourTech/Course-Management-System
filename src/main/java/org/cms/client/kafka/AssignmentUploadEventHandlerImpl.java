package org.cms.client.kafka;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cms.core.files.assignment.Assignment;
import org.cms.events.apis.assignment.AssignmentUploadEventHandler;
import org.controlsfx.control.Notifications;

public class AssignmentUploadEventHandlerImpl implements AssignmentUploadEventHandler {

	private final Logger logger = LogManager.getLogger(AssignmentUploadEventHandlerImpl.class);
	private final ObservableList<Assignment> assignmentList;

	public AssignmentUploadEventHandlerImpl(ObservableList<Assignment> assignmentList) {
		this.assignmentList = assignmentList;
	}

	@Override
	public void handle(Assignment event) {
		logger.info("New assignment upload event - " + event);
		Platform.runLater(() -> Notifications.create().title("New assignment uploaded!").text(event.getCourse().getName()).show());
		assignmentList.add(event);
		logger.info("Added event to assignment list in controller");
	}
}
