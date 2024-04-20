package com.coursework.clinicregistration.Subjects;

import com.coursework.clinicregistration.Utils;

public class Patient {
    // format: MM-NNNNNN
    private String regNum;
    // format: 5-50 letters, only [A-z] and [.]
    private String fio;
    // format: YYYY
    private int birth;
    // format: 5-50 letters
    private String address;
    // format: 5-50 letters
    private String workplace;

    public Patient() {
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

    public String getFio() {
        return fio;
    }

    public boolean setFio(String fio) {
        if (Utils.cLetters5_50(fio) && Utils.cLettersAndDot(fio)) {
            this.fio = fio;
            return true;
        }
        return false;
    }

    public int getBirth() {
        return birth;
    }

    public boolean setBirth(int birth) {
        if (Utils.cFourDigits(birth)) {
            this.birth = birth;
            return true;
        }
        return false;
    }

    public String getAddress() {
        return address;
    }

    public boolean setAddress(String address) {
        if (Utils.cLetters5_50(address)) {
            this.address = address;
            return true;
        }
        return false;
    }

    public String getWorkplace() {
        return workplace;
    }

    public boolean setWorkplace(String workplace) {
        if (Utils.cLetters5_50(address)) {
            this.workplace = workplace;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        answer.append("regNum: ").append(regNum)
                .append("; patient: ").append(fio)
                .append("; birth: ").append(birth)
                .append("; address: ").append(address)
                .append("; workplace: ").append(workplace);
        return answer.toString();
    }

    public boolean equals(Patient pat) {
        if (this.birth != pat.birth) return false;
        if (!this.fio.equals(pat.fio)) return false;
        if (!this.address.equals(pat.address)) return false;
        if (!this.regNum.equals(pat.regNum)) return false;
        return this.workplace.equals(pat.workplace);
    }
}
