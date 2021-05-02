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
import org.cms.client.framework.session.Session;
import org.cms.client.ui.UIHelper;
import org.cms.core.course.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoursesViewController implements Initializable {

	public TableView<Course> coursesTable;
	public TableColumn<Course, String> courseIDColumn;
	public TableColumn<Course, String> courseNameColumn;
	public TableColumn<Course, String> courseDescColumn;
	public TableColumn<Course, String> courseBranchColumn;
	public Button subscribeButton;
	public TextField filterField;
	public Label subscribeCourseStatus;

	private ObservableList<Course> courses;
	private static final Session session = Session.getInstance();
	private static final Logger logger = LoggerFactory.getLogger(CoursesViewController.class);

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			CompletableFuture<List<Course>> futureCourseList = session.getRestClient().getAllCourses();
			List<Course> courseList = futureCourseList.get();
			courses = FXCollections.observableArrayList(courseList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		coursesTable.setItems(courses);
		UIHelper.initCourseTableColumns(courseIDColumn, courseNameColumn, courseDescColumn, courseBranchColumn);
		bindDisablePropertyForSubscribeButton();
		UIHelper.bindFilterPropertyInTableView(courses, filterField, coursesTable);
	}

	private void bindDisablePropertyForSubscribeButton() {
		ObservableList<Course> selectedRows = coursesTable.getSelectionModel().getSelectedItems();
		subscribeButton.disableProperty().bind(booleanBindingForTableRowSelection(selectedRows));
	}

	private BooleanBinding booleanBindingForTableRowSelection(ObservableList<Course> selectedRows) {
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
				return session.isCourseSubscribed(c);
			}
		};
	}

	public void subscribeButtonOnAction(ActionEvent actionEvent) throws URISyntaxException, ExecutionException, InterruptedException {
		ObservableList<Course> selectedRows = coursesTable.getSelectionModel().getSelectedItems();
		Course courseTobeSubscribed = selectedRows.get(0);

		CompletableFuture<String> future = session.getRestClient().subscribe(session.getUserId(), courseTobeSubscribed.getId());
		String response = future.get();
		logger.info("Subscribe response - " + response);
		subscribeCourseStatus.setText(response);

		session.addCourseToSubscribeList(courseTobeSubscribed);
		coursesTable.getSelectionModel().clearSelection();
	}
}
