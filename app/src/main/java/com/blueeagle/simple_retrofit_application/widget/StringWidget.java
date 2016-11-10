package com.blueeagle.simple_retrofit_application.widget;

public class StringWidget {

    public static String toCapWords(String strInput) {
        if (strInput == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder(strInput);

        for (int i = 0; i < strInput.length(); i++) {
            if ((i == 0 || strInput.charAt(i - 1) == ' ')
                    && strInput.charAt(i) != ' ') {

                char c = strInput.charAt(i);
                if (c >= 97 && c <= 122) {
                    stringBuilder.setCharAt(i, (char) (c - 32));
                }
            }
        }

        return stringBuilder.toString();
    }

}
