package org.cms.client.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.cms.client.framework.service.Service;
import org.cms.client.framework.session.Session;
import org.cms.client.ui.UIHelper;
import org.cms.core.course.Course;

public class HomeController implements Initializable {

	public TableView<Course> coursesTable;
	public TableColumn<Course, String> courseIDColumn;
	public TableColumn<Course, String> courseNameColumn;
	public TableColumn<Course, String> courseDescColumn;
	public TableColumn<Course, String> courseBranchColumn;
	public TextField filterField;

	private ObservableList<Course> courses;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	public void populateCoursesTable() {
		final Service service = Service.getInstance();
		try {
			// handler - consumes session created event
			CompletableFuture<List<Course>> future = service.getSession().getRestClient().getCoursesForUser(service.getUserId());
			List<Course> list;
			if (future == null) {
				list = new ArrayList<>();
			} else {
				list = future.get();
			}
			courses = FXCollections.observableArrayList(list);
			service.getCourseHandler().setSubscribedCourses(courses);
			service.getCourseHandler().unlock();
		} catch (Exception e) {
			e.printStackTrace();
		}

		coursesTable.setItems(courses);
		UIHelper.initCourseTableColumns(courseIDColumn, courseNameColumn, courseDescColumn, courseBranchColumn);
		UIHelper.bindFilterPropertyInTableView(courses, filterField, coursesTable);
	}
}
