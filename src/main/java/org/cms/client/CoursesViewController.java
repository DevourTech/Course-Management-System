package org.cms.client;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cms.client.framework.session.Session;
import org.cms.core.course.Course;

public class CoursesViewController implements Initializable {

	public TableView<Course> coursesTable;
	public TableColumn<Course, Integer> courseIDColumn;
	public TableColumn<Course, String> courseNameColumn;
	public TableColumn<Course, String> courseDescColumn;
	public TableColumn<Course, String> courseBranchColumn;
	public Button subscribeButton;
	public TextField filterField;

	private ObservableList<Course> courses, studentCourses;
	private static final Session session = Session.getInstance();

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

		courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		courseBranchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
		courseDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		bindDisablePropertyForSubscribeButton();
		bindFilterPropertyInTableView();
	}

	private void bindFilterPropertyInTableView() {
		FilteredList<Course> filteredList = new FilteredList<>(courses, b -> true);
		filterField
			.textProperty()
			.addListener(
				(observable, oldValue, newValue) -> {
					filteredList.setPredicate(course -> filterColumnsBasedOnPredicate(newValue, course));
				}
			);
		SortedList<Course> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(coursesTable.comparatorProperty());
		coursesTable.setItems(sortedList);
	}

	private boolean filterColumnsBasedOnPredicate(String filterText, Course course) {
		if (filterText == null || filterText.isEmpty()) {
			return true;
		}

		String filter = filterText.toLowerCase();
		if (course.getId().contains(filter)) {
			return true;
		}

		if (course.getName().toLowerCase().contains(filter)) {
			return true;
		}

		if (course.getBranch().toLowerCase().contains(filter)) {
			return true;
		}

		return course.getDescription().toLowerCase().contains(filter);
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
					return false;
				}
				Course c = selectedRows.get(0);
				return courses.contains(c);
			}
		};
	}
}
