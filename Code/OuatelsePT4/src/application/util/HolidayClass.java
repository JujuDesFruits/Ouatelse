package application.util;

import javax.validation.constraints.NotNull;

public class HolidayClass {





	@NotNull
	private int code_holiday;
	@NotNull
	private String beginDate;
	@NotNull
	private String endDate;
	private String strDuration;

	@NotNull
	private int code_person;


	
	public HolidayClass(String beginDate, String endDate, String strDuration){
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.strDuration=strDuration;

	}
	
	public int getCode_holiday() {
		return code_holiday;
	}



	public void setCode_holiday(int code_holiday) {
		this.code_holiday = code_holiday;
	}



	public String getBeginDate() {
		return beginDate;
	}



	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public int getCode_person() {
		return code_person;
	}



	public void setCode_person(int code_person) {
		this.code_person = code_person;
	}

	public String getStrDuration() {
		return strDuration;
	}

	public void setStrduration(String strDuration) {
		this.strDuration = strDuration;
	}
}
