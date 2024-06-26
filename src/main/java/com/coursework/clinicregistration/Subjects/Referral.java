package com.coursework.clinicregistration.Subjects;

import com.coursework.clinicregistration.Utils;

public class Referral implements Comparable {
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

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        answer.append("regNum: ").append(regNum)
                .append("; doctor: ").append(doctorFIO)
                .append("; date: ").append(date)
                .append("; time: ").append(time);
        return answer.toString();
    }

    public boolean equals(Referral ref) {
        if (!this.regNum.equals(ref.regNum)) return false;
        if (!this.doctorFIO.equals(ref.doctorFIO)) return false;
        if (!this.date.equals(ref.date)) return false;
        return this.time.equals(ref.time);
    }

    @Override
    public int compareTo(Object o) {
        String ref2regNum;
        try {
            ref2regNum = ((Referral) o).regNum;
        } catch (ClassCastException e) {
            try {
                ref2regNum = (String) o;
            } catch (ClassCastException e2) {
                System.out.println("Can't compare.");
                return 0;
            }
        }
        return this.regNum.compareTo(ref2regNum);
    }
}
