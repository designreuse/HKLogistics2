package com.hk.logistics.constants;

public enum EnumHolidayConstants {
	
	Gandhi_Jayanti("02/10/2014"),
    Dussehra("03/10/2014"),
    Muharram("04/11/2014"),
    Guru_Nanak_Jayanti("06/11/2014");

    private String holidayDate;

    EnumHolidayConstants(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayDate() {
        return holidayDate;
    }
}
