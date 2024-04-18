package com.coursework.clinicregistration.Subjects;

import com.coursework.clinicregistration.Utils;

public class Doctor {
    // format: up to 25 letters, surname + initials
    private String fio;
    // format: 5-50 letters
    private String post;
    // format: KKK
    private int cabNum;
    // format: 5-50 letters
    private String schedule;

    public Doctor() {}

    public String getFio() {
        return fio;
    }

    public boolean setFio(String fio) {
        if (Utils.cFIO(fio)) {
            this.fio = fio;
            return true;
        }
        return false;
    }

    public String getPost() {
        return post;
    }

    public boolean setPost(String post) {
        if (Utils.cLetters5_50(post)) {
            this.post = post;
            return true;
        }
        return false;
    }

    public int getCabNum() {
        return cabNum;
    }

    public boolean setCabNum(int cabNum) {
        if (Utils.cThreeDigits(cabNum)) {
            this.cabNum = cabNum;
            return true;
        }
        return false;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean setSchedule(String schedule) {
        if (Utils.cLetters5_50(schedule)) {
            this.schedule = schedule;
            return true;
        }
        return false;
    }
}
