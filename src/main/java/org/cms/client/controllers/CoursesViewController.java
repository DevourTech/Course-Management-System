package org.cms.client.controllers;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cms.client.framework.service.Service;
import org.cms.client.framework.session.Session;
import org.cms.client.ui.CourseBooleanActionable;
import org.cms.client.ui.UIHelper;
import org.cms.core.course.Course;

public class CoursesViewController implements Initializable {

	public TableView<Course> coursesTable;
	public TableColumn<Course, String> courseIDColumn;
	public TableColumn<Course, String> courseNameColumn;
	public TableColumn<Course, String> courseDescColumn;
	public TableColumn<Course, String> courseBranchColumn;
	public Button subscribeButton, unsubscribeButton;
	public TextField filterField;
	public Label courseStatus;

	private ObservableList<Course> courses;
	private static final Service service = Service.getInstance();
	private static final Logger logger = LogManager.getLogger(CoursesViewController.class);

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			CompletableFuture<List<Course>> futureCourseList = service.getSession().getRestClient().getAllCourses();
			List<Course> courseList = futureCourseList.get();
			courses = FXCollections.observableArrayList(courseList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		coursesTable.setItems(courses);
		UIHelper.initCourseTableColumns(courseIDColumn, courseNameColumn, courseDescColumn, courseBranchColumn);
		bindDisablePropertyForSubscribeButton();
		bindDisablePropertyForUnsubscribeButton();
		UIHelper.bindFilterPropertyInTableView(courses, filterField, coursesTable);
	}

	private void bindDisablePropertyForSubscribeButton() {
		ObservableList<Course> selectedRows = coursesTable.getSelectionModel().getSelectedItems();
		subscribeButton
			.disableProperty()
			.bind(booleanBindingForTableRowSelection(selectedRows, service.getCourseHandler()::isCourseSubscribed));
	}

	private void bindDisablePropertyForUnsubscribeButton() {
		ObservableList<Course> selectedRows = coursesTable.getSelectionModel().getSelectedItems();
		unsubscribeButton
			.disableProperty()
			.bind(booleanBindingForTableRowSelection(selectedRows, c -> !service.getCourseHandler().isCourseSubscribed(c)));
	}

	private BooleanBinding booleanBindingForTableRowSelection(
		ObservableList<Course> selectedRows,
		CourseBooleanActionable courseBooleanActionable
	) {
		return new BooleanBinding() {
			{
				super.bind(selectedRows);
			}

			@Override
			protected boolean computeValue() {
				if (selectedRows.size() > 1) {
					return true;
				}
				if (selectedRows.size() == 0) {
					return true;
				}
				Course c = selectedRows.get(0);
				return courseBooleanActionable.courseBooleanAction(c);
			}
		};
	}

	public void subscribeButtonOnAction(ActionEvent actionEvent) throws URISyntaxException, ExecutionException, InterruptedException {
		ObservableList<Course> selectedRows = coursesTable.getSelectionModel().getSelectedItems();
		Course courseTobeSubscribed = selectedRows.get(0);

		CompletableFuture<String> future = service
			.getSession()
			.getRestClient()
			.subscribe(service.getUserId(), courseTobeSubscribed.getId());
		String response = future.get();
		logger.info("Subscribe response - " + response);
		courseStatus.setText(response);

		service.getCourseHandler().addCourseToSubscribeList(courseTobeSubscribed);
		coursesTable.getSelectionModel().clearSelection();
	}

	public void unsubscribeButtonOnAction(ActionEvent actionEvent) throws ExecutionException, InterruptedException, URISyntaxException {
		ObservableList<Course> selectedRows = coursesTable.getSelectionModel().getSelectedItems();
		Course courseTobeUnsubscribed = selectedRows.get(0);

		CompletableFuture<String> future = service
			.getSession()
			.getRestClient()
			.unsubscribe(service.getUserId(), courseTobeUnsubscribed.getId());
		String response = future.get();
		logger.info("Unsubscribe response - " + response);
		courseStatus.setText(response);

		service.getCourseHandler().removeCourseFromSubscribedList(courseTobeUnsubscribed);
		coursesTable.getSelectionModel().clearSelection();
	}
}
