package org.cms.client.ui;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cms.core.course.Course;

public class UIHelper {

	public static void bindFilterPropertyInTableView(
		ObservableList<Course> courses,
		TextField filterField,
		TableView<Course> coursesTable
	) {
		if (courses == null) {
			return;
		}

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

	private static boolean filterColumnsBasedOnPredicate(String filterText, Course course) {
		if (filterText == null || filterText.isEmpty()) {
			return true;
		}

		String filter = filterText.toLowerCase();
		if (course.getId().toLowerCase().contains(filter)) {
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

	public static void initCourseTableColumns(
		TableColumn<Course, String> courseIDColumn,
		TableColumn<Course, String> courseNameColumn,
		TableColumn<Course, String> courseDescColumn,
		TableColumn<Course, String> courseBranchColumn
	) {
		courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		courseBranchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));
		courseDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
	}
}
