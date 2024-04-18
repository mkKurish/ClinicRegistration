package com.coursework.clinicregistration.Subjects;

import com.coursework.clinicregistration.Utils;

public class Referral {
    // format: MM-NNNNNN
    private String regNum;
    // format: up to 25 letters, surname + initials
    private String doctorFIO;
    // format: DD.MM.YYYY
    private String date;
    // format: HH:MM
    private String time;

    public Referral() {
    }

    public String getRegNum() {
        return regNum;
    }

    public boolean setRegNum(String regNum) {
        if (Utils.cRegNum(regNum)) {
            this.regNum = regNum;
            return true;
        }
        return false;
    }

    public String getDoctorFIO() {
        return doctorFIO;
    }

    public boolean setDoctorFIO(String doctorFIO) {
        if (Utils.cFIO(doctorFIO)) {
            this.doctorFIO = doctorFIO;
            return true;
        }
        return false;
    }

    public String getDate() {
        return date;
    }

    public boolean setDate(String date) {
        if (Utils.cDate(date)) {
            this.date = date;
            return true;
        }
        return false;
    }

    public String getTime() {
        return time;
    }

    public boolean setTime(String time) {
        if (Utils.cTime(time)) {
            this.time = time;
            return true;
        }
        return false;
    }
}
