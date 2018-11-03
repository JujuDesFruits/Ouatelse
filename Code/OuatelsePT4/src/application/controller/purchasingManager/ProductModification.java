package application.controller.purchasingManager;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ProductModification {


	@FXML
	private Button cancel;
	
	@FXML
	private AnchorPane background;
	
	public void cancel(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/purchasingManager/PurchasingManager.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
