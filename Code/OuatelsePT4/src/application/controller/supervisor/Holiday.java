package application.controller.supervisor;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

public class Holiday {
	
	@FXML
	AnchorPane background;

	@FXML
	DatePicker startDate;

	@FXML
	DatePicker endDate;
	
	public void back(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/supervisor/EmployeeDetails.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
