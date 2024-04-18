package com.coursework.clinicregistration;

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
        if (!source.substring(0, 2).matches("^\\d2$")) return false;
        return source.substring(3).matches("^\\d2$");
    }

    public static boolean cFIO(String source){
        if (source == null) return false;
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
}
