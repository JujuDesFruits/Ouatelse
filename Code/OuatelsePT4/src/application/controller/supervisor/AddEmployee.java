package application.controller.supervisor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.controller.Connection_db;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class AddEmployee {

	@FXML
	private Button cancel;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private TextField nameTf;
	
	@FXML
	private TextField firstNameTf;
	
	@FXML
	private TextField phoneNumberTf;
	
	@FXML
	private TextField emailTf;
	
	@FXML
	private TextField addressTf;
	
	@FXML
	private TextField cityTf;
	
	@FXML
	private TextField zipCodeTf;
	
	@FXML
	private MenuButton situationMb;
	
	@FXML
	private TextField loginTf;
	
	@FXML
	private PasswordField passwordTf;
	
	@FXML
	private PasswordField confirmTf;
	
	@FXML
	private MenuItem salesman;
	
	@FXML
	private MenuItem purchasingManager;
	
	@FXML
	private MenuItem shopManager;
	
	@FXML
	private Label error;
	
	@FXML
	private MenuButton shop_mb;
	
	private int choosenShop;
	
	@FXML
	private Pane salesman_pane;
	
	@FXML
	public void initialize(){
		Statement stmt = null;
		String query = "select * from Shop";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				MenuItem miShop = new MenuItem(res.getString("City") + " / " + res.getInt("Code_Shop"));
								
				EventHandler<ActionEvent> action = chooseShop();
				
				miShop.setOnAction(action);
				
				shop_mb.getItems().add(miShop);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		salesman_pane.setVisible(false);
	}
	
	public EventHandler<ActionEvent> chooseShop(){
		return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                
                shop_mb.setText(mItem.getText());
                
                String[] line = mItem.getText().split(" ");
                //line[2] = code_shop
                choosenShop = Integer.valueOf(line[3]);
            }
        };
		
	}
	
	public void cancel(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/supervisor/HomeSupervisor.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(){
		String situation = null;
		switch(situationMb.getText()){
		case "Vendeur":
			situation = "Salesman";
			break;
		case "Responsable des achats":
			situation ="PurchasingManager";
			break;
		case "Responsable Magasin":
			situation ="ShopManager";
			break;
		}
				
		//password verification
		if(validation()){
			
			Statement stmt = null;
			String queryShop=null;//only for salesman
			
			String queryPerson = "Insert into Person (LastName,FirstName,Address,City,Zip_Code,Phone) values ('"
					+ nameTf.getText()
					+ "','" + firstNameTf.getText()
					+ "','" + addressTf.getText()
					+ "','" + cityTf.getText()
					+ "','" + zipCodeTf.getText()
					+ "','" + phoneNumberTf.getText() + "');";
					
			String queySalaried = "Insert into Salaried values ((Select Code_Person From Person Where Person.LastName like '" + nameTf.getText()
					+ "' and Person.FirstName like '" + firstNameTf.getText() + "')"
					+ ",'"+ loginTf.getText() + "','"+ passwordTf.getText() + "');";
			
			String querySituation = "Insert into Occupy values ((Select Code_Person From Salaried Where Login like '"+ loginTf.getText() + "'),(Select Code_Situation From Situation Where Situation_Label like '" + situation + "'));";
			
			if(salesman_pane.isVisible()){
				queryShop =  "Insert into Is_Salaried values((Select Code_Person From Salaried Where Login like '"+ loginTf.getText() + "')," + choosenShop + ")";
			}
			
			try {
				stmt = Connection_db.getConnection().createStatement();
				stmt.executeUpdate(queryPerson);
				stmt.executeUpdate(queySalaried);
				stmt.executeUpdate(querySituation);
				
				if(salesman_pane.isVisible()){
					stmt.executeUpdate(queryShop);
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//does not cancel but goes back to the menu
			cancel();
		}
	}
	
	public boolean validation(){
		boolean validate = true;
		//name verification
	    if(nameTf.getText() == null || nameTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Nom ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //first name verification
	    else if(firstNameTf.getText() == null || firstNameTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Prénom ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //phone number verification
	    else if(phoneNumberTf.getText() == null || phoneNumberTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Téléphone ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //email verification
	    else if(emailTf.getText() == null || emailTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Email ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //address verification
	    else if(addressTf.getText() == null || addressTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Addresse ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //city verification
	    else if(cityTf.getText() == null || cityTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Ville ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //zip Code verification
	    else if(zipCodeTf.getText() == null || zipCodeTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Code Postal ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //zip Code verification
	    else if(zipCodeTf.getText() == null || zipCodeTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Code Postal ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //situation verification
	    else if(situationMb.getText().equals("POSTE")){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Veuillez sélectionner un poste");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //login verification
	    else if(loginTf.getText() == null || loginTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Login ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //password verification
	    else if(passwordTf.getText() == null || passwordTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Password ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //confirm password verification
	    else if(confirmTf.getText() == null || confirmTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Vous devez confirmer votre mot de passe");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //verification that the password and the confirmed password are the same 
	    else if(!passwordTf.getText().equals(confirmTf.getText())){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Les deux mots de passes entrés sont différents");
	        fail.showAndWait();
	        validate = false;
	    }
		
	    else {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setHeaderText("Succès");
	        alert.setContentText("Compte créé avec succès!");
	        alert.showAndWait();
	    }
		return validate;
	}
	
	
	public void enterPressed(ActionEvent ae){
		add();
	}
	
	public void changeSituationSalesman(){
		situationMb.setText("Vendeur");
		salesman_pane.setVisible(true);
	}
	
	public void changeSituationPurchasingManager(){
		situationMb.setText("Responsable des ventes");
		salesman_pane.setVisible(false);
	}
	
	public void changeSituationShopManager(){
		situationMb.setText("Responsable Magasin");
		salesman_pane.setVisible(false);
	}
}
