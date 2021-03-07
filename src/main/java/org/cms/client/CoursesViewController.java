package org.cms.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.cms.core.course.Course;

public class CoursesViewController implements Initializable {

	public TableView<Course> coursesTable;
	public TableColumn<Course, Integer> courseIDColumn;
	public TableColumn<Course, String> courseNameColumn;
	public TableColumn<Course, String> courseDescColumn;
	public TableColumn<Course, String> courseBranchColumn;
	public Button subscribeButton;

	private ObservableList<Course> courses;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		courses = FXCollections.observableArrayList();
		courses.add(new Course(1, "Networking", "Lmao"));
		courses.add(new Course(2, "OS", "Divya Sir"));
		courses.add(new Course(3, "DBMS", "Fatt gyi"));

		coursesTable.setItems(courses);

		courseIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		courseDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		coursesTable.getColumns().setAll(courseIDColumn, courseNameColumn, courseDescColumn);

		ObservableList<Course> selectedRows = coursesTable.getSelectionModel().getSelectedItems();
		subscribeButton
			.disableProperty()
			.bind(
				new BooleanBinding() {
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
						return (c.getId() % 2 == 1);
					}
				}
			);
	}
}
