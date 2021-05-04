package com.main.app.util;

public class JmbgValidatorUtil {

    public static boolean validateJmbg(String jmbg) {

        boolean isValidDayInMonth = false;
        boolean isValidControlNumber = false;

        if(jmbg.length() != 13){
            return false;
        }

        String dayString = jmbg.substring(0, 2);
        boolean isDayInteger = isInteger(dayString);
        int day = Integer.parseInt(dayString);

        String monthString = jmbg.substring(2, 4);
        boolean isMonthInteger = isInteger(monthString);
        int month = Integer.parseInt(monthString);

        String yearString = jmbg.substring(4, 7);
        boolean isYearInteger = isInteger(yearString);
        int year = Integer.parseInt(yearString);

        String regionCodeString = jmbg.substring(7, 9);
        boolean isRegionCodeInteger = isInteger(regionCodeString);

        String uniqueNumberString = jmbg.substring(9, 12);
        boolean isUniqueNumberInteger = isInteger(uniqueNumberString);

        String controlNumberString = jmbg.substring(12, 13);
        boolean isControlNumberInteger = isInteger(controlNumberString);
        int controlNumber = Integer.parseInt(controlNumberString);

        if(isDayInteger && isMonthInteger && isYearInteger && isRegionCodeInteger && isUniqueNumberInteger &&
        isControlNumberInteger && 1 <= Integer.parseInt(regionCodeString) && Integer.parseInt(regionCodeString) <= 96){

            isValidDayInMonth = validateDayInMonth(year, month, day);
            isValidControlNumber =  validateControlNumber(jmbg, controlNumber);
        }

        return isValidDayInMonth && isValidControlNumber;
    }

    /*
     * Static bool method - checks if a year is actually a leap year.
     * Every year that is exactly divisible by four is a leap year,
     * except for years that are exactly divisible by 100,
     * but these centurial years are leap years if they are exactly divisible by 400.
     * For example, the years 1700, 1800, and 1900 are not leap years, but the years 1600 and 2000 are.
     */
    private static boolean isLeapYear(int year){
        boolean leapYear = false;

        if( ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
            leapYear = true;
        }

        return leapYear;
    }

    /*
     * Static bool method - checks if day and month in JMBG are well typed in
     * Regarding, checks if day fits in range of days that typed-in month has
     */
    private static boolean validateDayInMonth(int year, int month, int day){

        boolean isDayValid = false;

        if(1 <= month && month <= 12){
            switch (month)
            {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(1 <= day && day <= 31){
                        isDayValid = true;
                        break;
                    }
                    isDayValid = false;
                    break;
                case 2:
                    if(isLeapYear(year)){
                        if(1 <= day && day <= 29){
                            isDayValid = true;
                            break;
                        }
                    }
                    else{
                        if(1 <= day && day <= 28){
                            isDayValid = true;
                            break;
                        }
                    }
                    isDayValid = false;
                    break;

                case 4:
                case 6:
                case 9:
                case 11:
                    if (1 <= day && day <= 30)
                    {
                        isDayValid = true;
                        break;
                    }
                    isDayValid = false;
                    break;
            }
        }

        return isDayValid;
    }

    /*
     * Static bool method - checks if last number in JMBG (K) is well typed in
     * Regarding, checks if control number fits in formula how it's primarly calculated
     */
    private static boolean validateControlNumber(String jmbg, int controlNumber){

        String digit1String = jmbg.substring(0, 1);
        int digit1 = Integer.parseInt(digit1String);

        String digit2String = jmbg.substring(1, 2);
        int digit2 = Integer.parseInt(digit2String);

        String digit3String = jmbg.substring(2, 3);
        int digit3 = Integer.parseInt(digit3String);

        String digit4String = jmbg.substring(3, 4);
        int digit4 = Integer.parseInt(digit4String);

        String digit5String = jmbg.substring(4, 5);
        int digit5 = Integer.parseInt(digit5String);

        String digit6String = jmbg.substring(5, 6);
        int digit6 = Integer.parseInt(digit6String);

        String digit7String = jmbg.substring(6, 7);
        int digit7 = Integer.parseInt(digit7String);

        String digit8String = jmbg.substring(7, 8);
        int digit8 = Integer.parseInt(digit8String);

        String digit9String = jmbg.substring(8, 9);
        int digit9 = Integer.parseInt(digit9String);

        String digit10String = jmbg.substring(9, 10);
        int digit10 = Integer.parseInt(digit10String);

        String digit11String = jmbg.substring(10, 11);
        int digit11 = Integer.parseInt(digit11String);

        String digit12String = jmbg.substring(11, 12);
        int digit12 = Integer.parseInt(digit12String);

        int calculatedControlNumber = 11 - ((7 * (digit1 + digit7) + 6 * (digit2 + digit8) + 5 * (digit3 + digit9) +
                                            4 * (digit4 + digit10) + 3 * (digit5 + digit11) + 2 * (digit6 + digit12)) % 11);

        if(calculatedControlNumber > 9){
            calculatedControlNumber = 0;
        }

        return controlNumber == calculatedControlNumber;
    }


    private static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
