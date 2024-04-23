package com.coursework.clinicregistration;

import com.coursework.clinicregistration.Algorithms.LinearSearch;
import com.coursework.clinicregistration.Algorithms.QuickSort;
import com.coursework.clinicregistration.DataStructures.DoubleLinkedList;
import com.coursework.clinicregistration.DataStructures.HashTableLinear;
import com.coursework.clinicregistration.DataStructures.TreeAVL;
import com.coursework.clinicregistration.Subjects.Doctor;
import com.coursework.clinicregistration.Subjects.Patient;
import com.coursework.clinicregistration.Subjects.Referral;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    static TreeAVL<Doctor> doctors;
    static HashTableLinear<Patient> patients;
    static DoubleLinkedList<Referral> referrals;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, UnsupportedEncodingException {
        doctors = new TreeAVL<>(Doctor.class, "fio");
        patients = new HashTableLinear<>(Patient.class, "regNum");
        referrals = new DoubleLinkedList<>(Referral.class);

        int mode = -1;
        while (mode != 1000) {
            mode = menu();
            switch (mode) {
                case 11:
                    System.out.println("Для регистрации больного необходимо ввести данные.");
                    Patient newPatient = new Patient();
                    while (true) {
                        String regNum = Utils.stringInput("Введите регистрационный номер пациента: ");
                        while (!newPatient.setRegNum(regNum)) {
                            System.out.println("Некорректно введен регистрационный номер!");
                            System.out.println("Формат номера: MM-NNNNNN");
                            regNum = Utils.stringInput("Введите регистрационный номер пациента: ");
                        }
                        if (patients.findByKey(regNum) == null) break;
                        System.out.println("База уже содержит пациента с таким регистрационным номером!");
                        System.out.println("Введите оригинальный номер!");
                    }
                    String newPfio = Utils.stringInput("Введите ФИО пациента: ");
                    while (!newPatient.setFio(newPfio)) {
                        System.out.println("Некорректно введено ФИО!");
                        System.out.println("Формат: 5-50 символов, допускаются только символы A-z и точка.");
                        newPfio = Utils.stringInput("Введите ФИО пациента: ");
                    }
                    int birthYear = Utils.intInput("Введите год рождения пациента: ");
                    while (!newPatient.setBirth(birthYear)) {
                        System.out.println("Некорректно введен год рождения!");
                        System.out.println("Формат: YYYY");
                        birthYear = Utils.intInput("Введите год рождения пациента: ");
                    }
                    String address = Utils.stringInput("Введите адрес регистрации: ");
                    while (!newPatient.setAddress(address)) {
                        System.out.println("Некорректно введен адрес регистрации ФИО!");
                        System.out.println("Формат: 5-50 символов, допускаются любые символы");
                        address = Utils.stringInput("Введите адрес регистрации пациента: ");
                    }
                    String workplace = Utils.stringInput("Введите место работы пациента: ");
                    while (!newPatient.setWorkplace(workplace)) {
                        System.out.println("Некорректно введено место работы!");
                        System.out.println("Формат: 5-50 символов, допускаются любые символы");
                        workplace = Utils.stringInput("Введите место работы пациента: ");
                    }
                    boolean patientResult = patients.add(newPatient);
                    if (patientResult) {
                        System.out.println(newPatient.toString());
                        System.out.println("Пациент был успешно добавлен!");
                    } else {
                        System.out.println("Возникла проблема, пациент не был добавлен.");
                    }
                    break;
                case 12:
                    System.out.println("Для удаления информации о пациенте");
                    System.out.println("необходимо ввести регистрационный номер.");
                    String regNum = Utils.stringInput("Введите регистрационный номер пациента: ");
                    while (!Patient.trySetRegNum(regNum)) {
                        System.out.println("Некорректно введен регистрационный номер!");
                        System.out.println("Формат номера: MM-NNNNNN");
                        regNum = Utils.stringInput("Введите регистрационный номер пациента: ");
                    }
                    boolean removedPatient = patients.remove(regNum);
                    if (removedPatient) {
                        System.out.println("Пациент был удален из базы.");
                        int deletedRefs = referrals.removeAllBy("regNum", regNum);
                        System.out.printf("Было удалено %d записей этого пациента к врачам.%n", deletedRefs);
                    } else {
                        System.out.println("Пациент не был удален.");
                    }
                    break;
                case 13:
                    System.out.println("Вся информация о пациентах:");
                    System.out.println(patients.toString());
                    break;
                case 14:
                    patients.clear();
                    System.out.println("Была произведена очистка базы данных пациентов.");
                    referrals.clear();
                    System.out.println("Также отменены все направления к врачам.");
                    break;
                case 15:
                    System.out.println("Для поиска информации о пациенте");
                    System.out.println("необходимо ввести регистрационный номер.");
                    String registration = Utils.stringInput("Введите регистрационный номер пациента: ");
                    while (!Patient.trySetRegNum(registration)) {
                        System.out.println("Некорректно введен регистрационный номер!");
                        System.out.println("Формат номера: MM-NNNNNN");
                        registration = Utils.stringInput("Введите регистрационный номер пациента: ");
                    }
                    Patient foundPatient = patients.findByKey(registration);
                    if (foundPatient == null) {
                        System.out.println("Пациент не был найден.");
                    } else {
                        System.out.println("Найден пациент:");
                        System.out.println(foundPatient.toString());
                        DoubleLinkedList<Referral> patientRefs = referrals.findBy("regNum", foundPatient.getRegNum());
                        Iterator<Referral> refIt = patientRefs.iterator();
                        if (refIt.hasNext())
                            System.out.print("Больной имеет направления к врачам: ");
                        while (refIt.hasNext()) {
                            System.out.print(refIt.next().getDoctorFIO());
                            if (refIt.hasNext()) System.out.print(", ");
                        }
                        System.out.print("\n");
                    }
                    break;
                case 16:
                    System.out.println("Для поиска информации о пациенте");
                    System.out.println("необходимо ввести ФИО.");
                    String patientFio = Utils.stringInput("Введите ФИО пациента: ");
                    while (!Patient.trySetFio(patientFio)) {
                        System.out.println("Некорректно введено ФИО!");
                        System.out.println("Формат: 5-50 символов, допускаются только символы A-z и точка.");
                        patientFio = Utils.stringInput("Введите ФИО пациента: ");
                    }
                    ArrayList<Patient> foundPatients = patients.findByOtherField("fio", patientFio);
                    if (foundPatients.isEmpty()) {
                        System.out.println("Пациент не был найден.");
                    } else {
                        System.out.println("Найдены пациенты:");
                        for (Patient pat : foundPatients) {
                            System.out.println(pat);
                        }
                    }
                    break;
                case 21:
                    System.out.println("Для регистрации врача необходимо ввести данные.");
                    Doctor newDoctor = new Doctor();
                    while (true) {
                        String newDocFio = Utils.stringInput("Введите ФИО врача: ");
                        while (!newDoctor.setFio(newDocFio)) {
                            System.out.println("Некорректно введено ФИО!");
                            System.out.println("Формат: не более 25 символов, фамилия и инициалы (Пример А.Б.)");
                            newDocFio = Utils.stringInput("Введите ФИО врача: ");
                        }
                        if (doctors.findByKeyValue(newDocFio) == null) break;
                        System.out.println("База уже содержит врача с таким ФИО!");
                        System.out.println("Введите оригинальное ФИО!");
                    }
                    String newDocPost = Utils.stringInput("Введите должность врача: ");
                    while (!newDoctor.setPost(newDocPost)) {
                        System.out.println("Некорректно введена должность!");
                        System.out.println("Формат: 5-50 символов");
                        newDocPost = Utils.stringInput("Введите должность врача: ");
                    }
                    int newDocCab = Utils.intInput("Введите кабинет врача: ");
                    while (!newDoctor.setCabNum(newDocCab)) {
                        System.out.println("Некорректно введен номер кабинета!");
                        System.out.println("Формат: YYY");
                        newDocCab = Utils.intInput("Введите кабинет врача: ");
                    }
                    String newDocSchedule = Utils.stringInput("Введите расписание врача: ");
                    while (!newDoctor.setSchedule(newDocSchedule)) {
                        System.out.println("Некорректно введено расписание!");
                        System.out.println("Формат: 5-50 символов");
                        newDocSchedule = Utils.stringInput("Введите расписание врача: ");
                    }
                    boolean doctorResult = doctors.add(newDoctor);
                    if (doctorResult) {
                        System.out.println(newDoctor.toString());
                        System.out.println("Доктор был успешно добавлен!");
                    } else {
                        System.out.println("Возникла проблема, доктор не был добавлен.");
                    }
                    break;
                case 22:
                    System.out.println("Для удаления информации о враче");
                    System.out.println("необходимо ввести ФИО.");
                    String docFio = Utils.stringInput("Введите ФИО врача: ");
                    while (!Doctor.trySetFio(docFio)) {
                        System.out.println("Некорректно введено ФИО!");
                        System.out.println("Формат: не более 25 символов, фамилия и инициалы (Пример А.Б.)");
                        docFio = Utils.stringInput("Введите ФИО врача: ");
                    }
                    boolean removedDoc = doctors.remove(docFio);
                    if (removedDoc) {
                        System.out.println("Врач был удален из базы.");
                        int deletedRefs = referrals.removeAllBy("doctorFIO", docFio);
                        System.out.printf("Было удалено %d записей пациентов к этому врачу.%n", deletedRefs);
                    } else {
                        System.out.println("Врач не был удален.");
                    }
                    break;
                case 23:
                    System.out.println("Вся информация о врачах:");
                    System.out.println(doctors.toString());
                    break;
                case 24:
                    doctors.clear();
                    System.out.println("Была произведена очистка базы данных врачей.");
                    referrals.clear();
                    System.out.println("Также отменены все направления к врачам.");
                    break;
                case 25:
                    System.out.println("Для поиска информации о враче");
                    System.out.println("необходимо ввести ФИО.");
                    String doctorFio = Utils.stringInput("Введите ФИО врача: ");
                    while (!Doctor.trySetFio(doctorFio)) {
                        System.out.println("Некорректно введено ФИО!");
                        System.out.println("Формат: не более 25 символов, фамилия и инициалы (Пример А.Б.)");
                        doctorFio = Utils.stringInput("Введите ФИО врача: ");
                    }
                    Doctor foundDoctor = doctors.findByKeyValue(doctorFio);
                    if (foundDoctor == null) {
                        System.out.println("Врач не был найден.");
                    } else {
                        System.out.println("Найден врач:");
                        System.out.println(foundDoctor.toString());
                        DoubleLinkedList<Referral> doctorRefs = referrals.findBy("doctorFIO", foundDoctor.getFio());
                        ArrayList<String> regNums = new ArrayList<>();
                        for (Referral doctorRef : doctorRefs) {
                            String rn = doctorRef.getRegNum();
                            if (!regNums.contains(rn)) regNums.add(rn);
                        }
                        if (!regNums.isEmpty())
                            System.out.println("К врачу записаны следующие больные:");
                        for (String rn : regNums) {
                            Patient docPatient = patients.findByKey(rn);
                            if (docPatient != null)
                                System.out.println(docPatient.getFio() + " " + docPatient.getRegNum());
                        }
                    }
                    break;
                case 26:
                    System.out.println("Для поиска информации о враче");
                    System.out.println("необходимо ввести фрагмент должности (или полностью должность).");
                    String doctorPost = Utils.stringInput("Введите должность врача: ");
                    ArrayList<Doctor> foundDoctors = doctors.findByStringField("post", doctorPost, new LinearSearch());
                    if (foundDoctors.isEmpty()) {
                        System.out.println("Врач не был найден.");
                    } else {
                        System.out.println("Найдены врачи:");
                        for (Doctor doc : foundDoctors) {
                            System.out.println(doc);
                        }
                    }
                    break;
                case 31:
                    System.out.println("Для регистрации выдачи направления пациенту");
                    System.out.println("Нужно выбрать пациента и врача.");
                    String patientRegistration;
                    Patient existingPatient;
                    while (true) {
                        patientRegistration = Utils.stringInput("Введите регистрационный номер пациента: ");
                        while (!Patient.trySetRegNum(patientRegistration)) {
                            System.out.println("Некорректно введен регистрационный номер!");
                            System.out.println("Формат номера: MM-NNNNNN");
                            patientRegistration = Utils.stringInput("Введите регистрационный номер пациента: ");
                        }
                        existingPatient = patients.findByKey(patientRegistration);
                        if (existingPatient != null) {
                            System.out.println("Пациент найден!");
                            break;
                        }
                        System.out.println("Пациент не был найден.");
                        System.out.println("Повторите поиск пациента.");
                    }
                    String existingDocFio;
                    while (true) {
                        existingDocFio = Utils.stringInput("Введите ФИО врача: ");
                        while (!Doctor.trySetFio(existingDocFio)) {
                            System.out.println("Некорректно введено ФИО!");
                            System.out.println("Формат: не более 25 символов, фамилия и инициалы (Пример А.Б.)");
                            existingDocFio = Utils.stringInput("Введите ФИО врача: ");
                        }
                        Doctor existingDoctor = doctors.findByKeyValue(existingDocFio);
                        if (existingDoctor != null) {
                            System.out.println("Врач найден!");
                            break;
                        }
                        System.out.println("Врач не был найден.");
                        System.out.println("Повторите поиск врача.");
                    }
                    Referral newReferral = new Referral();
                    newReferral.setRegNum(patientRegistration);
                    newReferral.setDoctorFIO(existingDocFio);
                    while (true) {
                        String newRefDate = Utils.stringInput("Введите дату записи: ");
                        while (!newReferral.setDate(newRefDate)) {
                            System.out.println("Некорректно введена дата!");
                            System.out.println("Формат: DD.MM.YYYY");
                            newRefDate = Utils.stringInput("Введите дату записи: ");
                        }
                        if (Integer.parseInt(newRefDate.substring(6)) < existingPatient.getBirth()) {
                            System.out.println("Пациент родился в " + existingPatient.getBirth() + " году.");
                            System.out.println("Выберите корректную дату.");
                            continue;
                        }
                        String newRefTime = Utils.stringInput("Введите время приема: ");
                        while (!newReferral.setTime(newRefTime)) {
                            System.out.println("Некорректно введено время!");
                            System.out.println("Формат: HH:MM");
                            newRefTime = Utils.stringInput("Введите время приема: ");
                        }
                        DoubleLinkedList<Referral> existingRefs = referrals.findBy("date", newRefDate);
                        Referral timeCollision = null;
                        for (Referral r : existingRefs) {
                            if ((r.getDoctorFIO().equals(newReferral.getDoctorFIO()) || r.getRegNum().equals(newReferral.getRegNum())) && Math.abs(Utils.timeDiff(r.getTime(), newRefTime)) < 15) {
                                timeCollision = r;
                            }
                        }
                        if (timeCollision == null) {
                            referrals.add(newReferral);
                            System.out.println("Запись к врачу успешно зарегистрирована.");
                            break;
                        }
                        System.out.println("База содержит запись с данным\nпациентом/врачом со временем приема, пересекающимся с указанным.");
                        System.out.println(timeCollision.getDate() + " " + timeCollision.getTime());
                        System.out.println("Необходимо выбрать другие дату/время записи (интервал >15мин).");
                    }
                    break;
                case 32:
                    boolean fullBreak = false;
                    System.out.println("Для возврата направления");
                    System.out.println("нужно выбрать существующее направление.");
                    DoubleLinkedList<Referral> existingPatientRefs = new DoubleLinkedList<>(Referral.class);
                    String refRegNum;
                    while (true) {
                        refRegNum = Utils.stringInput("Введите регистрационный номер пациента: ");
                        if (refRegNum.equals("quit")) {
                            fullBreak = true;
                            break;
                        }
                        while (!Patient.trySetRegNum(refRegNum)) {
                            System.out.println("Некорректно введен регистрационный номер!");
                            System.out.println("Формат номера: MM-NNNNNN");
                            refRegNum = Utils.stringInput("Введите регистрационный номер пациента: ");
                        }
                        existingPatientRefs = referrals.findBy("regNum", refRegNum);
                        if (existingPatientRefs.length > 0) {
                            System.out.println("Найдены следующие записи:");
                            System.out.println(existingPatientRefs.toString());
                            break;
                        }
                        System.out.println("Не было найдено записей с этим пациентом.");
                        System.out.println("Повторите поиск пациента. (quit для выхода)");
                    }
                    if (fullBreak) break;
                    DoubleLinkedList<Referral> existingDoctorRefs = new DoubleLinkedList<>(Referral.class);
                    String refDocFio;
                    while (true) {
                        refDocFio = Utils.stringInput("Введите ФИО врача: ");
                        if (refDocFio.equals("quit")) {
                            fullBreak = true;
                            break;
                        }
                        while (!Doctor.trySetFio(refDocFio)) {
                            System.out.println("Некорректно введено ФИО!");
                            System.out.println("Формат: не более 25 символов, фамилия и инициалы (Пример А.Б.)");
                            refDocFio = Utils.stringInput("Введите ФИО врача: ");
                        }
                        existingDoctorRefs = existingPatientRefs.findBy("doctorFIO", refDocFio);
                        if (existingDoctorRefs.length > 0) {
                            System.out.println("Найдены следующие записи:");
                            System.out.println(existingDoctorRefs.toString());
                            break;
                        }
                        System.out.println("Не было найдено записей с этим врачом.");
                        System.out.println("Повторите поиск врача. (quit для выхода)");
                    }
                    if (fullBreak) break;
                    DoubleLinkedList<Referral> existingDateTimeRefs = new DoubleLinkedList<>(Referral.class);
                    Referral existingRef = new Referral();
                    existingRef.setRegNum(refRegNum);
                    existingRef.setDoctorFIO(refDocFio);
                    while (true) {
                        String newRefDate = Utils.stringInput("Введите дату записи: ");
                        if (refDocFio.equals("quit")) {
                            fullBreak = true;
                            break;
                        }
                        while (!existingRef.setDate(newRefDate)) {
                            System.out.println("Некорректно введена дата!");
                            System.out.println("Формат: DD.MM.YYYY");
                            newRefDate = Utils.stringInput("Введите дату записи: ");
                        }
                        String newRefTime = Utils.stringInput("Введите время приема: ");
                        if (newRefTime.equals("quit")) {
                            fullBreak = true;
                            break;
                        }
                        while (!existingRef.setTime(newRefTime)) {
                            System.out.println("Некорректно введено время!");
                            System.out.println("Формат: HH:MM");
                            newRefTime = Utils.stringInput("Введите время приема: ");
                        }
                        existingDateTimeRefs = existingDoctorRefs.findBy("date", newRefDate).findBy("time", newRefTime);
                        if (existingDateTimeRefs.length == 1) {
                            System.out.println("Найдена следующая запись:");
                            System.out.println(existingDateTimeRefs.toString());
                            break;
                        }
                        System.out.println("Данный пациент не записан к данному врачу на эту дату и время.");
                        System.out.println("Необходимо выбрать другие дату/время из следующих:");
                        System.out.println(existingDoctorRefs.toString());
                        System.out.println("Повторите ввод (quit для выхода).");
                    }
                    if (fullBreak) break;
                    boolean deleteRefSuccess = referrals.remove(existingDateTimeRefs.getFirst());
                    if (deleteRefSuccess) {
                        System.out.println("Запись успешно удалена!");
                    } else {
                        System.out.println("Непредвиденная ошибка при удалении, запись не была удалена.");
                    }
                    break;
                case 33:
                    System.out.println("Вся информация о направлениях:");
                    System.out.println(referrals.toString());
                    break;
                case 34:
                    QuickSort qs = new QuickSort();
                    referrals = qs.sort(referrals, Referral.class);
                    System.out.println("Список направлений отсортирован.");
                    break;
                case 35:
                    referrals.clear();
                    System.out.println("Отменены все направления к врачам.");
                    break;
                case 90:
                    Doctor d1 = new Doctor();
                    d1.setFio("Postgres Q.L.");
                    d1.setPost("RDBMS");
                    d1.setCabNum(543);
                    d1.setSchedule("Monday Tuesday");

                    Doctor d2 = new Doctor();
                    d2.setFio("Mongo D.B.");
                    d2.setPost("file DBMS");
                    d2.setCabNum(111);
                    d2.setSchedule("Friday Tuesday");

                    Doctor d3 = new Doctor();
                    d3.setFio("Unknown X.X.");
                    d3.setPost("random post");
                    d3.setCabNum(109);
                    d3.setSchedule("Saturday Sunday");

                    Doctor d4 = new Doctor();
                    d4.setFio("Well D.N.");
                    d4.setPost("surgery");
                    d4.setCabNum(785);
                    d4.setSchedule("Saturday Tuesday Sunday");

                    Doctor d5 = new Doctor();
                    d5.setFio("Zero I.F.");
                    d5.setPost("service");
                    d5.setCabNum(641);
                    d5.setSchedule("Monday Tuesday Friday");

                    Patient p1 = new Patient();
                    p1.setFio("Someone O.B.");
                    p1.setAddress("Street");
                    p1.setBirth(2000);
                    p1.setRegNum("12-000000");
                    p1.setWorkplace("Home");

                    Patient p2 = new Patient();
                    p2.setFio("Another one A.");
                    p2.setAddress("Avenue");
                    p2.setBirth(1900);
                    p2.setRegNum("98-869536");
                    p2.setWorkplace("Office");

                    Patient p3 = new Patient();
                    p3.setFio("Brother of Someone");
                    p3.setAddress("Outside");
                    p3.setBirth(2002);
                    p3.setRegNum("10-100001");
                    p3.setWorkplace("House");

                    Patient p4 = new Patient();
                    p4.setFio("Random P.P.L.");
                    p4.setAddress("Building");
                    p4.setBirth(1980);
                    p4.setRegNum("77-368843");
                    p4.setWorkplace("Job");

                    doctors.add(d1);
                    doctors.add(d2);
                    doctors.add(d3);
                    doctors.add(d4);
                    doctors.add(d5);

                    patients.add(p1);
                    patients.add(p2);
                    patients.add(p3);
                    patients.add(p4);

                    System.out.println("Хеш пациента " + p1.getRegNum() + ": " + patients.findHashByKey(p1.getRegNum()));
                    System.out.println("Хеш пациента " + p2.getRegNum() + ": " + patients.findHashByKey(p2.getRegNum()));
                    System.out.println("Хеш пациента " + p3.getRegNum() + ": " + patients.findHashByKey(p3.getRegNum()));
                    System.out.println("Хеш пациента " + p4.getRegNum() + ": " + patients.findHashByKey(p4.getRegNum()));

                    System.out.println("Задан тестовый набор врачей и пациентов.");
                    break;
            }
        }
    }

    static int menu() {
        System.out.println("----------------------------------------------");
        System.out.println("11 - Регистрация больного");
        System.out.println("12 - Удаление больного");
        System.out.println("13 - Просмотр всех больных");
        System.out.println("14 - Очистка всех больных");
        System.out.println("15 - Поиск больного по регистрационному номеру");
        System.out.println("16 - Поиск больного по ФИО");
        System.out.println("21 ---- Добавление врача");
        System.out.println("22 ---- Удаление врача");
        System.out.println("23 ---- Просмотр всех врачей");
        System.out.println("24 ---- Очистка всех врачей");
        System.out.println("25 ---- Поиск врача по ФИО");
        System.out.println("26 ---- Поиск врача по должности");
        System.out.println("31 - Регистрация выдачи направления");
        System.out.println("32 - Регистрация возврата направления");
        System.out.println("33 - Просмотр всех направлений");
        System.out.println("34 - Сортировка списка направлений");
        System.out.println("35 - Очистка всех направлений");
        System.out.println("90 - Тестовый набор");
        System.out.println("1000 - Завершение работы");
        System.out.print("Выбор: ");
        Scanner sc = new Scanner(System.in);
        int value = -1;
        if (sc.hasNextInt()) value = sc.nextInt();
        while (!checkMenu(value)) {
            System.out.println("Ошибка при выборе пункта меню.");
            System.out.print("Повторите: ");
            sc.nextLine();
            if (sc.hasNextInt()) value = sc.nextInt();
        }
        return value;
    }

    static boolean checkMenu(int value) {
        return value >= 11 && value <= 16 || value >= 21 && value <= 26 || value >= 31 && value <= 35 || value == 90 || value == 1000;
    }

    static void testTree() throws NoSuchFieldException, IllegalAccessException {
        TreeAVL<Doctor> tr = new TreeAVL<>(Doctor.class, "fio");

        Doctor d1 = new Doctor();
        d1.setFio("Pentium G.X.");
        d1.setPost("LOR");
        d1.setCabNum(1233);
        d1.setSchedule("Monday Tuesday");

        Doctor d2 = new Doctor();
        d2.setFio("Selenium P.V.");
        d2.setPost("Allergic");
        d2.setCabNum(3516);
        d2.setSchedule("Friday Tuesday");

        Doctor d3 = new Doctor();
        d3.setFio("Unknown X.X.");
        d3.setPost("none");
        d3.setCabNum(1000);
        d3.setSchedule("Saturday Sunday");

        System.out.println(tr);
        tr.add(d1);
        System.out.println("========");
        System.out.println(tr);
        tr.add(d2);
        System.out.println("========");
        System.out.println(tr);
        tr.add(d3);
        System.out.println("========");
        System.out.println(tr);
        System.out.println("=-=-=-=-=-=-=-=");
        System.out.println(tr.findByKeyValue("Pentium G.X."));
        System.out.println(tr.findByStringField("schedule", "nday", new LinearSearch()));
        tr.remove("Selenium P.V.");
        System.out.println("========");
        System.out.println(tr);
        tr.clear();
    }

    static void testList() throws NoSuchFieldException {
        DoubleLinkedList<Referral> lst = new DoubleLinkedList<>(Referral.class);

        Referral r1 = new Referral();
        r1.setRegNum("11-111111");
        r1.setDate("12.02.2024");
        r1.setTime("12:30");
        r1.setDoctorFIO("Selenium P.V.");

        Referral r2 = new Referral();
        r2.setRegNum("33-123123");
        r2.setDate("05.12.2020");
        r2.setTime("15:00");
        r2.setDoctorFIO("Pentium G.X.");

        Referral r3 = new Referral();
        r3.setRegNum("28-197546");
        r3.setDate("24.07.2023");
        r3.setTime("09:45");
        r3.setDoctorFIO("Pentium G.X.");

        Referral r4 = new Referral();
        r4.setRegNum("73-717956");
        r4.setDate("20.01.2024");
        r4.setTime("14:15");
        r4.setDoctorFIO("Selenium P.V.");

        System.out.println(lst);
        System.out.println(lst.length);
        lst.add(r1);
        System.out.println(lst);
        System.out.println(lst.length);
        lst.add(r2);
        System.out.println(lst);
        System.out.println(lst.length);
        lst.add(r3);
        System.out.println(lst);
        System.out.println(lst.length);
        lst.add(r4);
        System.out.println(lst);
        System.out.println(lst.length);

        QuickSort qs = new QuickSort();
        lst = qs.sort(lst, Referral.class);
        System.out.println(lst);
        lst = qs.sort(lst, Referral.class);
        System.out.println(lst);

        try {
            DoubleLinkedList<Referral> results = lst.findBy("doctorFIO", "Pentium G.X.");
            System.out.println("Found:");
            for (Referral r : results) {
                if (r != null) {
                    System.out.println(r.toString());
                }
            }
            System.out.println("_________");
        } catch (Exception ignored) {
        }

        try {
            lst.removeFirstBy("regNum", "11-111111");
        } catch (Exception ignored) {
        }
        System.out.println(lst);
    }

    static void testHashTable() throws NoSuchFieldException, IllegalAccessException {
        Patient p1 = new Patient();
        p1.setFio("A.B.A");
        p1.setAddress("Street");
        p1.setBirth(2000);
        p1.setRegNum("12-124123");
        p1.setWorkplace("Home");

        Patient p2 = new Patient();
        p2.setFio("B.A.A");
        p2.setAddress("Avenue");
        p2.setBirth(1900);
        p2.setRegNum("98-869536");
        p2.setWorkplace("Office");

        Patient p3 = new Patient();
        p3.setFio("Brother of Someone");
        p3.setAddress("Outside");
        p3.setBirth(2002);
        p3.setRegNum("21-321543");
        p3.setWorkplace("House");

        Patient p4 = new Patient();
        p4.setFio("Random P.P.L.");
        p4.setAddress("Building");
        p4.setBirth(1980);
        p4.setRegNum("77-368843");
        p4.setWorkplace("Job");

        HashTableLinear<Patient> ht = new HashTableLinear<>(Patient.class, "regNum");
        System.out.println(ht);
        ht.add(p1);
        ht.add(p2);
        ht.add(p3);
        ht.add(p4);
        System.out.println("_____");
        System.out.println(ht);
        System.out.println(ht.findByKey("Random P.P.L."));
        System.out.println(ht.findByKey("B.A.X"));
        System.out.println(ht.findByKey("B.A.A"));
        System.out.println("----===----");
        System.out.println(ht.removeBySubject(p1));
        System.out.println(ht);
        System.out.println("_____");
        System.out.println(ht.remove("Brother of Someone"));
        System.out.println(ht);
        System.out.println("_____");
        System.out.println(ht.findByKey("Brother of Someone"));
    }
}
