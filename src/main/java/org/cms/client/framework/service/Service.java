package org.cms.client.framework.service;

import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cms.client.framework.globals.Constants;
import org.cms.client.framework.session.Session;
import org.cms.client.kafka.KafkaConnectorInitializer;
import org.cms.core.course.Course;
import org.cms.events.apis.assignment.AssignmentUploadEventFilter;

public class Service {

	private static Service service = null;
	private static final Logger logger = LogManager.getLogger(Service.class);

	private Session session;
	private String userType;
	private String userId;
	private CourseHandler courseHandler;
	private KafkaConnectorInitializer kafkaConnectorInitializer;

	private Service() {}

	public static void init(ServiceConfig serviceConfig) throws Exception {
		if (service != null) {
			logger.warn("CMS Client Service is already initialized");
			return;
		}

		logger.info("Initializing the CMS Client Service");
		service = new Service();

		logger.info("Initializing kafka connectors");
		service.kafkaConnectorInitializer =
			new KafkaConnectorInitializer(
				serviceConfig.getAssignmentList(),
				serviceConfig.getSubmissionList(),
				course -> service.courseHandler.isCourseSubscribed(course),
				serviceConfig.getSessionConfig().getUserId()
			);

		logger.info("Initializing course handler");
		service.courseHandler = new CourseHandler();

		// Create a session for the logged-in user
		logger.info("Initializing session");
		service.session = new Session(serviceConfig.getSessionConfig());

		service.userId = serviceConfig.getSessionConfig().getUserId();
		service.userType = serviceConfig.getSessionConfig().getUserType();

		// Authenticate the logged-in user
		logger.info("Authenticating the logged-in user");
		service.session.authenticate();

		// If admin is logged in, there's no need to do any more initialization
		if (service.userType.equalsIgnoreCase(Constants.ADMIN)) {
			logger.info(
				String.format(
					"Service initialized successfully for %s with id %s",
					service.userType,
					serviceConfig.getSessionConfig().getUserId()
				)
			);
			return;
		}

		// If student is logged in, then initialize assignment upload event consumers
		if (service.userType.equalsIgnoreCase(Constants.STUDENT)) {
			logger.info("Initializing assignment upload event consumer");
			service.courseHandler.lock();
			service.kafkaConnectorInitializer.initializeAssignmentUploadEventConsumer();
		}

		logger.info(
			String.format(
				"Service initialized successfully for %s with id %s",
				service.userType,
				serviceConfig.getSessionConfig().getUserId()
			)
		);
	}

	public static Service getInstance() {
		return service;
	}

	public CourseHandler getCourseHandler() {
		return courseHandler;
	}

	public Session getSession() {
		return session;
	}

	public String getUserId() {
		return userId;
	}
}
