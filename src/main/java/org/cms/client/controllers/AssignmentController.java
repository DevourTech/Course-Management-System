package org.cms.client.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.cms.client.framework.service.Service;
import org.cms.core.course.Course;
import org.cms.core.files.assignment.Assignment;

public class AssignmentController implements Initializable {

	public TableView<Assignment> assignmentsTable;
	public TableColumn<Assignment, String> sNoColumn;
	public TableColumn<Assignment, String> courseNameColumn;
	public TableColumn<Assignment, String> uploadedByColumn;
	public TableColumn<Assignment, String> dueDateColumn;
	private ObservableList<Assignment> assignmentList;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		assignmentList = FXCollections.observableArrayList();
		courseNameColumn.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getCourse().getName()));
		uploadedByColumn.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getInstructor().getName()));
		sNoColumn.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getId()));
		dueDateColumn.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getDueDate()));
		assignmentsTable.setItems(assignmentList);
	}

	public void populateAssignmentTable() {
		Service service = Service.getInstance();
		List<String> courseIds = service.getCourseHandler().getSubscribedCourses().stream().map(Course::getId).collect(Collectors.toList());

		try {
			CompletableFuture<List<Assignment>> future = service.getSession().getRestClient().getAssignmentsWithCourses(courseIds);
			List<Assignment> assignments = future.get();
			assignmentList.addAll(assignments);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Assignment> getAssignmentList() {
		return assignmentList;
	}

	public void uploadSolutionOnAction(ActionEvent actionEvent) {}
}
