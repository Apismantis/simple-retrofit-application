package com.blueeagle.simple_retrofit_application.widget;

public class StringWidget {

    /**
     * @param strInput: input string
     * @return A string has capitalized the first letter of each word
     */
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

    /**
     * @param strInput: input string
     * @return A string has capitalized the first letter of sentence
     */
    public static String toCapSentence(String strInput) {
        if (strInput == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder(strInput);
        char c = strInput.charAt(0);
        if (c >= 97 && c <= 122) {
            stringBuilder.setCharAt(0, (char) (c - 32));
        }

        return stringBuilder.toString();
    }
}
