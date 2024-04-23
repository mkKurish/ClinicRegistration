package com.coursework.clinicregistration;

import java.util.Scanner;

public class Utils {
    public static boolean cLetters5_50(String source) {
        if (source == null) return false;
        return source.length() >= 5 && source.length() <= 50;
    }

    public static boolean cLettersAndDot(String source) {
        if (source == null) return false;
        return source.matches("^[A-za-z. ]+$");
    }

    public static boolean cFourDigits(int source){
        return source <= 9999 && source >= 1000;
    }

    public static boolean cThreeDigits(int source){
        return source <= 999 && source >= 100;
    }

    public static boolean cRegNum(String source){
        if (source == null) return false;
        if (source.length() != 9) return false;
        if (source.charAt(2) != '-') return false;
        if (!source.substring(0, 2).matches("^\\d{2}$")) return false;
        return source.substring(3).matches("^\\d{6}$");
    }

    public static boolean cFIO(String source){
        if (source == null) return false;
        if (source.length() > 25) return false;
        return source.matches("^[A-Z][a-z]+ [A-Z]\\.[A-Z]\\.$");
    }

    public static boolean cDate(String source){
        if (source == null) return false;
        return source.matches("^(((0[1-9]|[12][0-9]|3[01])[- /.](0[13578]|1[02])|(0[1-9]|[12][0-9]|30)[- /.](0[469]|11)|(0[1-9]|1\\d|2[0-8])[- /.]02)[- /.]\\d{4}|29[- /.]02[- /.](\\d{2}(0[48]|[2468][048]|[13579][26])|([02468][048]|[1359][26])00))$");
    }

    public static boolean cTime(String source){
        if (source == null) return false;
        return source.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$");
    }

    public static String stringInput(String welcome){
        System.out.print(welcome);
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) return sc.nextLine();
        while (!sc.hasNextLine()){
            System.out.println("Неверный ввод!");
            System.out.print(welcome);
        }
        return sc.nextLine();
    }

    public static int intInput(String welcome){
        System.out.print(welcome);
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) return sc.nextInt();
        while (!sc.hasNextInt()){
            sc.nextLine();
            System.out.println("Неверный ввод числа!");
            System.out.print(welcome);
        }
        return sc.nextInt();
    }

    public static int timeDiff(String time1, String time2){
        int t1 = 0, t2 = 0;
        t1 += Integer.parseInt(time1.substring(0, 2)) * 60;
        t1 += Integer.parseInt(time1.substring(3));
        t2 += Integer.parseInt(time2.substring(0, 2)) * 60;
        t2 += Integer.parseInt(time2.substring(3));
        return t2 - t1;
    }
}
