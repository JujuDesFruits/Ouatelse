package application.controller.shopManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import application.controller.Connection_db;
import application.util.HolidayClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Holiday {

	@FXML
	AnchorPane background;

	@FXML
	DatePicker startDate;

	@FXML
	DatePicker endDate;


	@FXML
	private TableView<HolidayClass> tableHoliday;

	private final ObservableList<HolidayClass> dataHoliday = FXCollections.observableArrayList();

	@FXML
	private TableColumn start_date_column;
	@FXML
	private TableColumn end_date_column;
	@FXML
	private TableColumn duration_column;

	public void back(){

		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/shopManager/EmployeeDetails.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void initialize() throws ParseException{
		//launched when the fxml is initialized	
		startDate.setValue(LocalDate.now());
		list();
	}

	public void add(){
		
		if(validation()){
		//startDate recuperation
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate tmp = startDate.getValue();
		Instant instant = Instant.from(tmp.atStartOfDay(ZoneId.systemDefault()));
		Date dateTmp = Date.from(instant);
		String startDateString = formatter.format(dateTmp);

		//endDate recuperation
		tmp = endDate.getValue();
		instant = Instant.from(tmp.atStartOfDay(ZoneId.systemDefault()));
		dateTmp = Date.from(instant);
		String endDateString = formatter.format(dateTmp);
		
		if(validationDateComparator(startDateString, endDateString)){

			Statement stmt = null;
			String queryDate = "Insert into Holiday (Begin_Date,End_Date,Code_Person) values ('"
					+ startDateString
					+ "','" + endDateString
					+ "',"+getCodeEmployee()+");";

			try {
				stmt = Connection_db.getConnection().createStatement();
				stmt.executeUpdate(queryDate);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				list();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		}
	}




	private long duration(String date1str, String date2str) {
		//startDate recuperation
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date date1 = null;
		Date date2 = null;
		try {
			date1 = formatter.parse(date1str);
			date2 = formatter.parse(date2str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimeUnit tu = TimeUnit.DAYS;
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return tu.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	

	private boolean validation() {
        Alert fail= new Alert(AlertType.INFORMATION);
        fail.setHeaderText("Erreur");
        boolean validate = true;
        if(startDate.getValue() == null ){
	        fail.setContentText("Veuillez entrer une date de début");
	        fail.showAndWait();
	        validate = false;
		}else if(endDate.getValue() == null){
	        fail.setContentText("Veuillez entrer une date de fin");
	        fail.showAndWait();
	        validate = false;
		}
		return validate;
	}

	private boolean validationDateComparator(String startDateString, String endDateString) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = null; 
		Date date2 = null;
		boolean validate = true;
        Alert fail= new Alert(AlertType.INFORMATION);
        fail.setHeaderText("Erreur");
		try {
			date1 = formatter.parse(startDateString);
			date2 = formatter.parse(endDateString);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(date1.compareTo(date2) > 0 || date1.compareTo(date2) == 0){

	        fail.setContentText("La date de début doit être supérieure à celle de fin");
	        fail.showAndWait();
	        validate = false;
		}
		

		return validate;
	}

	public void list() throws ParseException{	

		//Removing of all elements of the table
		dataHoliday.clear();
		tableHoliday.setItems(dataHoliday);

		Statement stmt = null;
		String query = "select Begin_Date,End_Date from Holiday Where Code_Person = '"+getCodeEmployee()+"';";

		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				

				//definition of columns
				//inspired by : http://www.java2s.com/Code/Java/JavaFX/AddnewrowtoTableView.htm
				start_date_column.setCellValueFactory(new PropertyValueFactory<HolidayClass,String>("beginDate"));
				end_date_column.setCellValueFactory(new PropertyValueFactory<HolidayClass,String>("endDate"));
				duration_column.setCellValueFactory(new PropertyValueFactory<HolidayClass,String>("strDuration"));
				
				String date1 =res.getString("Begin_Date");
				String date2 =res.getString("End_Date");
				
				long duration = duration(date1,date2);
				String strDuration = Long.toString(duration) + " jour(s)";
				

				dataHoliday.add(new HolidayClass(res.getString("Begin_Date"),res.getString("End_Date"), strDuration ));
				tableHoliday.setItems(dataHoliday);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public int getCodeEmployee(){
		return EmployeeDetails.getCode_actual_employee();
	}
}
