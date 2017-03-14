/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cis304_project3;

/**
 *
 * @author Bryan
 */
public class CarFaxValidator {

    private static String errorMessage = "";

    public static String getVin(String aVin) {
        try {
            if (aVin.length() == 5) {
                for (int i = 0; i < 1; i++) {
                    char letter = aVin.charAt(i);
                    if (!Character.isLetter(letter)) {
                        errorMessage += "VIN must start with 1 letter.";
                    }
                }
                for (int i = 1; i < aVin.length(); i++) {
                    char number = aVin.charAt(i);
                    if (!Character.isDigit(number)) {
                        errorMessage += "VIN must end with 4 numbers.";
                    }
                }
            } else {
                errorMessage += "VIN must be 5 characters.";
            }
        } catch (Exception e) {
            errorMessage += "Invalid VIN input.";
        }

        return aVin;
    }

    public static String getMake(String aMake) {
        if (aMake.length() == 0) {
            errorMessage += "Make cannot be blank.";
        }
        return aMake;
    }

    public static String getModel(String aModel) {
        if (aModel.length() == 0) {
            errorMessage += "Model cannot be blank.";
        }
        return aModel;
    }

    public static int getYear(int aYear) {
        try {
            if (aYear <= 0) {
                errorMessage += "Year must be higher than 0.";
            }
        } catch (Exception e) {
            errorMessage += "Invalid year input.";
        }
        return aYear;
    }

    public static String getError() {
        return errorMessage;
    }

    public static void clearError() {
        errorMessage = "";
    }
}
