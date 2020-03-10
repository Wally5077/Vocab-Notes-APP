package com.home.englishnote;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {


    public static void main(String[] args) {
        passwordFormat();
    }


    private static void passwordFormat() {
        String s = "!#$%&'()*+,./:;<=>?@^_`{|}~-";
        String PASSWORD_PATTEN = "[a-zA-Z_0-create_own_dictionary_pen]{1,}";
        String p = "[ \\t\\r\\n\\v\\f]{1,}";
//        char[] chars = s.toCharArray();
//        for (char aChar : chars) {
//            String c = String.valueOf(aChar);
//            Matcher matcher = Pattern.compile(PASSWORD_Pattern).matcher(c);
//            System.out.println(c + " " + matcher.matches());
//        }
        Scanner scanner = new Scanner(System.in);
        do {
            s = scanner.nextLine();
            Matcher matcher = Pattern.compile(p).matcher(s);
            System.out.println(s + " " + matcher.matches());
        } while (!s.isEmpty());
    }
}
