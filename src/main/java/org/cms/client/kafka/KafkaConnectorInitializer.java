package org.cms.client.kafka;

import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.cms.client.controllers.RootController;
import org.cms.client.framework.config.Config;
import org.cms.client.framework.globals.Constants;
import org.cms.core.files.assignment.Assignment;
import org.cms.core.files.submission.Submission;
import org.cms.events.apis.assignment.AssignmentUploadConsumer;
import org.cms.events.apis.assignment.AssignmentUploadEventFilter;
import org.cms.events.apis.config.ConsumerConfigs;
import org.cms.events.factory.ConsumerFactory;

public class KafkaConnectorInitializer {

	private final ObservableList<Assignment> assignmentList;
	private final ObservableList<Submission> submissionList;
	private final AssignmentUploadEventFilter assignmentUploadEventFilter;
	private final String consumerId;

	private static final String ASSIGNMENTS_TOPIC = "assignments";
	private static final String SUBMISSIONS_TOPIC = "submissions";

	public KafkaConnectorInitializer(
		ObservableList<Assignment> assignmentList,
		ObservableList<Submission> submissionList,
		AssignmentUploadEventFilter assignmentUploadEventFilter,
		String consumerId
	) {
		this.assignmentList = assignmentList;
		this.submissionList = submissionList;
		this.assignmentUploadEventFilter = assignmentUploadEventFilter;
		this.consumerId = consumerId;
	}

	public void initializeAssignmentUploadEventConsumer() {
		ConsumerConfigs configs = getConsumerConfig(ASSIGNMENTS_TOPIC);
		AssignmentUploadConsumer consumer = ConsumerFactory.newAssignmentUploadConsumer(configs);

		AssignmentUploadEventHandlerImpl eventHandler = new AssignmentUploadEventHandlerImpl(assignmentList);

		consumer.registerHandler(eventHandler);
		consumer.registerFilter(assignmentUploadEventFilter);
		consumer.start();
	}

	public void initializeSubmissionUploadEventConsumer() {
		ConsumerConfigs configs = getConsumerConfig(SUBMISSIONS_TOPIC);
	}

	private ConsumerConfigs getConsumerConfig(String topic) {
		Properties properties = new Properties();
		properties.setProperty(Config.BOOTSTRAP_SERVERS, Config.get(Config.BOOTSTRAP_SERVERS));
		properties.setProperty(Config.CONSUMER_ID, consumerId);
		return new ConsumerConfigs(properties, topic);
	}
}
