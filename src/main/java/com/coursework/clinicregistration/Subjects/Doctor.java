package com.coursework.clinicregistration.Subjects;

import com.coursework.clinicregistration.Utils;

public class Doctor implements Comparable {
    // format: up to 25 letters, surname + initials
    private String fio;
    // format: 5-50 letters
    private String post;
    // format: KKK
    private int cabNum;
    // format: 5-50 letters
    private String schedule;

    public Doctor() {
    }

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

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        answer.append("doctor: ").append(fio)
                .append("; post: ").append(post)
                .append("; cabNum: ").append(cabNum)
                .append("; schedule: ").append(schedule);
        return answer.toString();
    }

    public boolean equals(Doctor doc) {
        if (this.cabNum != doc.cabNum) return false;
        if (!this.fio.equals(doc.fio)) return false;
        if (!this.post.equals(doc.post)) return false;
        return this.schedule.equals(doc.schedule);
    }

    public static boolean trySetFio(String fio){
        return Utils.cFIO(fio);
    }

    public static boolean trySetPost(String post){
        return Utils.cLetters5_50(post);
    }

    @Override
    public int compareTo(Object o) {
        String doc2fio;
        try {
            doc2fio = ((Doctor) o).fio;
        } catch (ClassCastException e) {
            try {
                doc2fio = (String) o;
            } catch (ClassCastException e2) {
                System.out.println("Can't compare.");
                return 0;
            }
        }
        return this.fio.compareTo(doc2fio);
    }
}
