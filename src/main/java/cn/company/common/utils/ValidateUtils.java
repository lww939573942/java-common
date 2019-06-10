package cn.company.common.utils;

import java.util.Collection;
import java.util.Map;

public class ValidateUtils {

    /**
     * boolean specifying by default whether or not it is okay for a String to be empty
     */
    public static final boolean defaultEmptyOK = true;

    /**
     * digit characters
     */
    public static final String digits = "0123456789";

    /**
     * hex digit characters
     */
    public static final String hexDigits = digits + "abcdefABCDEF";

    /**
     * whitespace characters
     */
    public static final String whitespace = " \t\n\r";

    /**
     * decimal point character differs by language and culture
     */
    public static final String decimalPointDelimiter = ".";

    /**
     * non-digit characters which are allowed in phone numbers
     */
    public static final String phoneNumberDelimiters = "()- ";

    /**
     * non-digit characters which are allowed in Social Security Numbers
     */
    public static final String SSNDelimiters = "- ";

    /**
     * U.S. Social Security Numbers have 9 digits. They are formatted as 123-45-6789.
     */
    public static final int digitsInSocialSecurityNumber = 9;

    /**
     * U.S. phone numbers have 10 digits. They are formatted as 123 456 7890 or(123) 456-7890.
     */
    public static final int digitsInUSPhoneNumber = 10;
    public static final int digitsInUSPhoneAreaCode = 3;
    public static final int digitsInUSPhoneMainNumber = 7;

    /**
     * non-digit characters which are allowed in ZIP Codes
     */
    public static final String ZipCodeDelimiters = "-";

    /**
     * U.S. ZIP codes have 5 or 9 digits. They are formatted as 12345 or 12345-6789.
     */
    public static final int digitsInZipCode1 = 5;

    /**
     * U.S. ZIP codes have 5 or 9 digits. They are formatted as 12345 or 12345-6789.
     */
    public static final int digitsInZipCode2 = 9;

    /**
     * non-digit characters which are allowed in credit card numbers
     */
    public static final String creditCardDelimiters = " -";

    /**
     * An array of ints representing the number of days in each month of the year.
     * Note: February varies depending on the year
     */
    protected static final int[] daysInMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public static boolean areEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        } else {
            return obj.equals(obj2);
        }
    }

    /**
     * Check whether an object is empty, will see if it is a String, Map, Collection, etc.
     */
    public static boolean isEmpty(Object o) {
        return isValidEmpty(o);
    }

    /**
     * Check whether an object is NOT empty, will see if it is a String, Map, Collection, etc.
     */
    public static boolean isNotEmpty(Object o) {
        return !isValidEmpty(o);
    }

    /**
     * Check whether IsEmpty o is empty.
     */
    public static boolean isEmpty(IsEmpty o) {
        return o == null || o.isEmpty();
    }

    /**
     * Check whether IsEmpty o is NOT empty.
     */
    public static boolean isNotEmpty(IsEmpty o) {
        return o != null && !o.isEmpty();
    }

    /**
     * Check whether string s is empty.
     */
    public static boolean isEmpty(String s) {
        return (s == null) || s.length() == 0;
    }

    /**
     * Check whether collection c is empty.
     */
    public static <E> boolean isEmpty(Collection<E> c) {
        return (c == null) || c.isEmpty();
    }

    /**
     * Check whether map m is empty.
     */
    public static <K, E> boolean isEmpty(Map<K, E> m) {
        return (m == null) || m.isEmpty();
    }

    /**
     * Check whether charsequence c is empty.
     */
    public static <E> boolean isEmpty(CharSequence c) {
        return (c == null) || c.length() == 0;
    }

    /**
     * Check whether string s is NOT empty.
     */
    public static boolean isNotEmpty(String s) {
        return (s != null) && s.length() > 0;
    }

    /**
     * Check whether collection c is NOT empty.
     */
    public static <E> boolean isNotEmpty(Collection<E> c) {
        return (c != null) && !c.isEmpty();
    }

    /**
     * Check whether charsequence c is NOT empty.
     */
    public static <E> boolean isNotEmpty(CharSequence c) {
        return ((c != null) && (c.length() > 0));
    }

    public static boolean isString(Object obj) {
        return ((obj != null) && (obj instanceof String));
    }

    /**
     * Returns true if string s is empty or whitespace characters only.
     */
    public static boolean isWhitespace(String s) {
        // Is s empty?
        if (isEmpty(s)) {
            return true;
        }

        // Search through string's characters one by one
        // until we find a non-whitespace character.
        // When we do, return false; if we don't, return true.
        for (int i = 0; i < s.length(); i++) {
            // Check that current character isn't whitespace.
            char c = s.charAt(i);

            if (whitespace.indexOf(c) == -1) {
                return false;
            }
        }
        // All characters are whitespace.
        return true;
    }

    /**
     * Removes all characters which appear in string bag from string s.
     */
    public static String stripCharsInBag(String s, String bag) {
        int i;
        StringBuilder stringBuilder = new StringBuilder("");

        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (bag.indexOf(c) == -1) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Removes all characters which do NOT appear in string bag from string s.
     */
    public static String stripCharsNotInBag(String s, String bag) {
        int i;
        StringBuilder stringBuilder = new StringBuilder("");

        // Search through string's characters one by one.
        // If character is in bag, append to returnString.
        for (i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (bag.indexOf(c) != -1) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Removes all whitespace characters from s.
     * Member whitespace(see above) defines which characters are considered whitespace.
     */
    public static String stripWhitespace(String s) {
        return stripCharsInBag(s, whitespace);
    }

    /**
     * Returns true if single character c(actually a string) is contained within string s.
     */
    public static boolean charInString(char c, String s) {
        return (s.indexOf(c) != -1);
        // for(int i = 0; i < s.length; i++) {
        // if (s.charAt(i) == c) return true;
        // }
        // return false;
    }

    /**
     * Returns true if character c is an English letter (A .. Z, a..z).
     * <p>
     * NOTE: Need i18n version to support European characters.
     * This could be tricky due to different character
     * sets and orderings for various languages and platforms.
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     * Returns true if character c is a digit (0 .. 9).
     */
    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    /**
     * Returns true if character c is a letter or digit.
     */
    public static boolean isLetterOrDigit(char c) {
        return Character.isLetterOrDigit(c);
    }

    /**
     * Returns true if character c is a letter or digit.
     */
    public static boolean isHexDigit(char c) {
        return hexDigits.indexOf(c) >= 0;
    }

    /**
     * Returns true if all characters in string s are numbers.
     * <p>
     * Accepts non-signed integers only. Does not accept floating
     * point, exponential notation, etc.
     */
    public static boolean isInteger(String s) {
        if (isEmpty(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (!isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if all characters are numbers;
     * first character is allowed to be + or - as well.
     * <p>
     * Does not accept floating point, exponential notation, etc.
     */
    public static boolean isSignedInteger(String s) {
        if (isEmpty(s)) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if all characters are numbers;
     * first character is allowed to be + or - as well.
     * <p>
     * Does not accept floating point, exponential notation, etc.
     */
    public static boolean isSignedLong(String s) {
        if (isEmpty(s)) {
            return false;
        }
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if string s is an integer > 0. NOTE: using the Java Long object for greatest precision
     */
    public static boolean isPositiveInteger(String s) {
        if (isEmpty(s)) {
            return false;
        }

        try {
            long temp = Long.parseLong(s);

            if (temp > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if string s is an integer >= 0.
     */
    public static boolean isNonnegativeInteger(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        try {
            int temp = Integer.parseInt(s);

            if (temp >= 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if string s is an integer < 0.
     */
    public static boolean isNegativeInteger(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        try {
            int temp = Integer.parseInt(s);

            if (temp < 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if string s is an integer <= 0.
     */
    public static boolean isNonpositiveInteger(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        try {
            int temp = Integer.parseInt(s);

            if (temp <= 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * True if string s is an unsigned floating point(real) number.
     * <p>
     * Also returns true for unsigned integers. If you wish
     * to distinguish between integers and floating point numbers,
     * first call isInteger, then call isFloat.
     * <p>
     * Does not accept exponential notation.
     */
    public static boolean isFloat(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        boolean seenDecimalPoint = false;

        if (s.startsWith(decimalPointDelimiter)) {
            return false;
        }

        // Search through string's characters one by one
        // until we find a non-numeric character.
        // When we do, return false; if we don't, return true.
        for (int i = 0; i < s.length(); i++) {
            // Check that current character is number.
            char c = s.charAt(i);

            if (c == decimalPointDelimiter.charAt(0)) {
                if (!seenDecimalPoint) {
                    seenDecimalPoint = true;
                } else {
                    return false;
                }
            } else {
                if (!isDigit(c)) {
                    return false;
                }
            }
        }
        // All characters are numbers.
        return true;
    }

    /**
     * General routine for testing whether a string is a float.
     */
    public static boolean isFloat(String s, boolean allowNegative, boolean allowPositive, int minDecimal, int maxDecimal) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        try {
            float temp = Float.parseFloat(s);
            if (!allowNegative && temp < 0) {
                return false;
            }
            if (!allowPositive && temp > 0) {
                return false;
            }
            int decimalPoint = s.indexOf(".");
            if (decimalPoint == -1) {
                if (minDecimal > 0) {
                    return false;
                }
                return true;
            }
            // 1.2345; length=6; point=1; num=4
            int numDecimals = s.length() - decimalPoint - 1;
            if (minDecimal >= 0 && numDecimals < minDecimal) {
                return false;
            }
            if (maxDecimal >= 0 && numDecimals > maxDecimal) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * General routine for testing whether a string is a double.
     */
    public static boolean isDouble(String s, boolean allowNegative, boolean allowPositive, int minDecimal, int maxDecimal) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        try {
            double temp = Double.parseDouble(s);
            if (!allowNegative && temp < 0) {
                return false;
            }
            if (!allowPositive && temp > 0) {
                return false;
            }
            int decimalPoint = s.indexOf(".");
            if (decimalPoint == -1) {
                if (minDecimal > 0) {
                    return false;
                }
                return true;
            }
            // 1.2345; length=6; point=1; num=4
            int numDecimals = s.length() - decimalPoint - 1;
            if (minDecimal >= 0 && numDecimals < minDecimal) {
                return false;
            }
            if (maxDecimal >= 0 && numDecimals > maxDecimal) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * True if string s is a signed or unsigned floating point
     * (real) number. First character is allowed to be + or -.
     * <p>
     * Also returns true for unsigned integers. If you wish
     * to distinguish between integers and floating point numbers,
     * first call isSignedInteger, then call isSignedFloat.
     */
    public static boolean isSignedFloat(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        try {
            Float.parseFloat(s);
            return true;
        } catch (Exception e) {
            return false;
        }

        //The old way:
        // int startPos = 0;
        // if (isSignedFloat.arguments.length > 1) secondArg = isSignedFloat.arguments[1];
        // skip leading + or -
        // if ((s.charAt(0) == "-") ||(s.charAt(0) == "+")) startPos = 1;
        // return(isFloat(s.substring(startPos, s.length), secondArg))
    }

    /**
     * True if string s is a signed or unsigned floating point
     * (real) number. First character is allowed to be + or -.
     * <p>
     * Also returns true for unsigned integers. If you wish
     * to distinguish between integers and floating point numbers,
     * first call isSignedInteger, then call isSignedFloat.
     */
    public static boolean isSignedDouble(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if string s is letters only.
     * <p>
     * NOTE: This should handle i18n version to support European characters, etc.
     * since it now uses Character.isLetter()
     */
    public static boolean isAlphabetic(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        // Search through string's characters one by one
        // until we find a non-alphabetic character.
        // When we do, return false; if we don't, return true.
        for (int i = 0; i < s.length(); i++) {
            // Check that current character is letter.
            char c = s.charAt(i);

            if (!isLetter(c)) {
                return false;
            }
        }

        // All characters are letters.
        return true;
    }

    /**
     * Returns true if string s is English letters (A .. Z, a..z) and numbers only.
     * <p>
     * NOTE: Need i18n version to support European characters.
     * This could be tricky due to different character
     * sets and orderings for various languages and platforms.
     */
    public static boolean isAlphanumeric(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        // Search through string's characters one by one
        // until we find a non-alphanumeric character.
        // When we do, return false; if we don't, return true.
        for (int i = 0; i < s.length(); i++) {
            // Check that current character is number or letter.
            char c = s.charAt(i);

            if (!isLetterOrDigit(c)) {
                return false;
            }
        }

        // All characters are numbers or letters.
        return true;
    }

    /* ================== METHODS TO CHECK VARIOUS FIELDS. ==================== */

    /**
     * isSSN returns true if string s is a valid U.S. Social Security Number.  Must be 9 digits.
     */
    public static boolean isSSN(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        String normalizedSSN = stripCharsInBag(s, SSNDelimiters);

        return (isInteger(normalizedSSN) && normalizedSSN.length() == digitsInSocialSecurityNumber);
    }

    /**
     * isUSPhoneNumber returns true if string s is a valid U.S. Phone Number.  Must be 10 digits.
     */
    public static boolean isUSPhoneNumber(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        String normalizedPhone = stripCharsInBag(s, phoneNumberDelimiters);

        return (isInteger(normalizedPhone) && normalizedPhone.length() == digitsInUSPhoneNumber);
    }

    /**
     * isUSPhoneAreaCode returns true if string s is a valid U.S. Phone Area Code.  Must be 3 digits.
     */
    public static boolean isUSPhoneAreaCode(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        String normalizedPhone = stripCharsInBag(s, phoneNumberDelimiters);

        return (isInteger(normalizedPhone) && normalizedPhone.length() == digitsInUSPhoneAreaCode);
    }

    /**
     * isUSPhoneMainNumber returns true if string s is a valid U.S. Phone Main Number.  Must be 7 digits.
     */
    public static boolean isUSPhoneMainNumber(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        String normalizedPhone = stripCharsInBag(s, phoneNumberDelimiters);

        return (isInteger(normalizedPhone) && normalizedPhone.length() == digitsInUSPhoneMainNumber);
    }

    /**
     * isInternationalPhoneNumber returns true if string s is a valid
     * international phone number.  Must be digits only; any length OK.
     * May be prefixed by + character.
     */
    public static boolean isInternationalPhoneNumber(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        String normalizedPhone = stripCharsInBag(s, phoneNumberDelimiters);

        return isPositiveInteger(normalizedPhone);
    }

    /**
     * isZIPCode returns true if string s is a valid U.S. ZIP code.  Must be 5 or 9 digits only.
     */
    public static boolean isZipCode(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        String normalizedZip = stripCharsInBag(s, ZipCodeDelimiters);

        return (isInteger(normalizedZip) && ((normalizedZip.length() == digitsInZipCode1) || (normalizedZip.length() == digitsInZipCode2)));
    }

    /**
     * Returns true if string s is a valid contiguous U.S. Zip code.  Must be 5 or 9 digits only.
     */
    public static boolean isContiguousZipCode(String s) {
        boolean retval = false;
        if (isZipCode(s)) {
            if (isEmpty(s)) {
                retval = defaultEmptyOK;
            } else {
                String normalizedZip = s.substring(0, 5);
                int iZip = Integer.parseInt(normalizedZip);
                if ((iZip >= 96701 && iZip <= 96898) || (iZip >= 99501 && iZip <= 99950)) {
                    retval = false;
                } else {
                    retval = true;
                }
            }
        }
        return retval;
    }

    /**
     * isUrl returns true if the string contains ://
     *
     * @param s String to validate
     *
     * @return true if s contains ://
     */
    public static boolean isUrl(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        if (s.indexOf("://") != -1) {
            return true;
        }

        return false;
    }

    /**
     * isYear returns true if string s is a valid
     * Year number.  Must be 2 or 4 digits only.
     * <p>
     * For Year 2000 compliance, you are advised
     * to use 4-digit year numbers everywhere.
     */
    public static boolean isYear(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        if (!isNonnegativeInteger(s)) {
            return false;
        }
        return ((s.length() == 2) || (s.length() == 4));
    }

    /**
     * isIntegerInRange returns true if string s is an integer
     * within the range of integer arguments a and b, inclusive.
     */
    public static boolean isIntegerInRange(String s, int a, int b) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        // Catch non-integer strings to avoid creating a NaN below,
        // which isn't available on JavaScript 1.0 for Windows.
        if (!isSignedInteger(s)) {
            return false;
        }
        // Now, explicitly change the type to integer via parseInt
        // so that the comparison code below will work both on
        // JavaScript 1.2(which typechecks in equality comparisons)
        // and JavaScript 1.1 and before(which doesn't).
        int num = Integer.parseInt(s);

        return ((num >= a) && (num <= b));
    }

    /**
     * isMonth returns true if string s is a valid month number between 1 and 12.
     */
    public static boolean isMonth(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        return isIntegerInRange(s, 1, 12);
    }

    /**
     * isDay returns true if string s is a valid day number between 1 and 31.
     */
    public static boolean isDay(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        return isIntegerInRange(s, 1, 31);
    }

    /**
     * Given integer argument year, returns number of days in February of that year.
     */
    public static int daysInFebruary(int year) {
        // February has 29 days in any year evenly divisible by four,
        // EXCEPT for centurial years which are not also divisible by 400.
        return (((year % 4 == 0) && ((!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28);
    }

    /**
     * isHour returns true if string s is a valid number between 0 and 23.
     */
    public static boolean isHour(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        return isIntegerInRange(s, 0, 23);
    }

    /**
     * isMinute returns true if string s is a valid number between 0 and 59.
     */
    public static boolean isMinute(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        return isIntegerInRange(s, 0, 59);
    }

    /**
     * isSecond returns true if string s is a valid number between 0 and 59.
     */
    public static boolean isSecond(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }
        return isIntegerInRange(s, 0, 59);
    }

    /**
     * isDate returns true if string arguments year, month, and day form a valid date.
     */
    public static boolean isDate(String year, String month, String day) {
        // catch invalid years(not 2- or 4-digit) and invalid months and days.
        if (!(isYear(year) && isMonth(month) && isDay(day))) {
            return false;
        }

        int intYear = Integer.parseInt(year);
        int intMonth = Integer.parseInt(month);
        int intDay = Integer.parseInt(day);

        // catch invalid days, except for February
        if (intDay > daysInMonth[intMonth - 1]) {
            return false;
        }
        if ((intMonth == 2) && (intDay > daysInFebruary(intYear))) {
            return false;
        }
        return true;
    }

    /**
     * isDate returns true if string argument date forms a valid date.
     */
    public static boolean isDate(String date) {
        if (isEmpty(date)) {
            return defaultEmptyOK;
        }
        String month;
        String day;
        String year;

        int dateSlash1 = date.indexOf("/");
        int dateSlash2 = date.lastIndexOf("/");

        if (dateSlash1 <= 0 || dateSlash1 == dateSlash2) {
            return false;
        }
        month = date.substring(0, dateSlash1);
        day = date.substring(dateSlash1 + 1, dateSlash2);
        year = date.substring(dateSlash2 + 1);

        return isDate(year, month, day);
    }


    /**
     * isTime returns true if string arguments hour, minute, and second form a valid time.
     */
    public static boolean isTime(String hour, String minute, String second) {
        // catch invalid years(not 2- or 4-digit) and invalid months and days.
        if (isHour(hour) && isMinute(minute) && isSecond(second)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * isTime returns true if string argument time forms a valid time.
     */
    public static boolean isTime(String time) {
        if (isEmpty(time)) {
            return defaultEmptyOK;
        }

        String hour;
        String minute;
        String second;

        int timeColon1 = time.indexOf(":");
        int timeColon2 = time.lastIndexOf(":");

        if (timeColon1 <= 0) {
            return false;
        }
        hour = time.substring(0, timeColon1);
        if (timeColon1 == timeColon2) {
            minute = time.substring(timeColon1 + 1);
            second = "0";
        } else {
            minute = time.substring(timeColon1 + 1, timeColon2);
            second = time.substring(timeColon2 + 1);
        }
        return isTime(hour, minute, second);
    }

    /**
     * Check to see if a card number is a valid ValueLink Gift Card
     *
     * @param stPassed a string representing a valuelink gift card
     *
     * @return true, if the number passed simple checks
     */
    public static boolean isValueLinkCard(String stPassed) {
        if (isEmpty(stPassed)) {
            return defaultEmptyOK;
        }
        String st = stripCharsInBag(stPassed, creditCardDelimiters);
        if (st.length() == 16 && (st.startsWith("7") || st.startsWith("6"))) {
            return true;
        }
        return false;
    }

    /**
     * Check to see if a card number is a valid OFB Gift Card (Certifiicate)
     *
     * @param stPassed a string representing a gift card
     *
     * @return tru, if the number passed simple checks
     */
    public static boolean isOFBGiftCard(String stPassed) {
        if (isEmpty(stPassed)) {
            return defaultEmptyOK;
        }
        String st = stripCharsInBag(stPassed, creditCardDelimiters);
        if (st.length() == 15 && sumIsMod10(getLuhnSum(st))) {
            return true;
        }
        return false;
    }

    /**
     * Check to see if a card number is a supported Gift Card
     *
     * @param stPassed a string representing a gift card
     *
     * @return true, if the number passed simple checks
     */
    public static boolean isGiftCard(String stPassed) {
        if (isOFBGiftCard(stPassed)) {
            return true;
        } else if (isValueLinkCard(stPassed)) {
            return true;
        }
        return false;
    }

    public static int getLuhnSum(String stPassed) {
        stPassed = stPassed.replaceAll("\\D", ""); // nuke any non-digit characters

        int len = stPassed.length();
        int sum = 0;
        int mul = 1;
        for (int i = len - 1; i >= 0; i--) {
            int digit = Character.digit(stPassed.charAt(i), 10);
            digit *= (mul == 1) ? mul++ : mul--;
            sum += (digit >= 10) ? (digit % 10) + 1 : digit;
        }

        return sum;
    }

    public static int getLuhnCheckDigit(String stPassed) {
        int sum = getLuhnSum(stPassed);
        int mod = ((sum / 10 + 1) * 10 - sum) % 10;
        return (10 - mod);
    }

    public static boolean sumIsMod10(int sum) {
        return ((sum % 10) == 0);
    }

    /**
     * Checks credit card number with Luhn Mod-10 test
     *
     * @param stPassed a string representing a credit card number
     *
     * @return true, if the credit card number passes the Luhn Mod-10 test, false otherwise
     */
    public static boolean isCreditCard(String stPassed) {
        if (isEmpty(stPassed)) {
            return defaultEmptyOK;
        }
        String st = stripCharsInBag(stPassed, creditCardDelimiters);

        if (!isInteger(st)) {
            return false;
        }

        // encoding only works on cars with less the 19 digits
        if (st.length() > 19) {
            return false;
        }
        return sumIsMod10(getLuhnSum(st));
    }

    /**
     * Checks to see if the cc number is a valid Visa number
     *
     * @param cc a string representing a credit card number; Sample number: 4111 1111 1111 1111(16 digits)
     *
     * @return true, if the credit card number is a valid VISA number, false otherwise
     */
    public static boolean isVisa(String cc) {
        if (((cc.length() == 16) || (cc.length() == 13)) && ("4".equals(cc.substring(0, 1)))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid Master Card number
     *
     * @param cc a string representing a credit card number; Sample number: 5500 0000 0000 0004(16 digits)
     *
     * @return true, if the credit card number is a valid MasterCard  number, false otherwise
     */
    public static boolean isMasterCard(String cc) {
        if(cc == null){
            return false;
        }
        int firstdig = Integer.parseInt(cc.substring(0, 1));
        int seconddig = Integer.parseInt(cc.substring(1, 2));

        if ((cc.length() == 16) && (firstdig == 5) && ((seconddig >= 1) && (seconddig <= 5))) {
            return isCreditCard(cc);
        }
        return false;

    }

    /**
     * Checks to see if the cc number is a valid American Express number
     *
     * @param cc - a string representing a credit card number; Sample number: 340000000000009(15 digits)
     *
     * @return true, if the credit card number is a valid American Express number, false otherwise
     */
    public static boolean isAmericanExpress(String cc) {
        int firstdig = Integer.parseInt(cc.substring(0, 1));
        int seconddig = Integer.parseInt(cc.substring(1, 2));

        if ((cc.length() == 15) && (firstdig == 3) && ((seconddig == 4) || (seconddig == 7))) {
            return isCreditCard(cc);
        }
        return false;

    }

    /**
     * Checks to see if the cc number is a valid Diners Club number
     *
     * @param cc - a string representing a credit card number; Sample number: 30000000000004(14 digits)
     *
     * @return true, if the credit card number is a valid Diner's Club number, false otherwise
     */
    public static boolean isDinersClub(String cc) {
        int firstdig = Integer.parseInt(cc.substring(0, 1));
        int seconddig = Integer.parseInt(cc.substring(1, 2));

        if ((cc.length() == 14) && (firstdig == 3) && ((seconddig == 0) || (seconddig == 6) || (seconddig == 8))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid Carte Blanche number
     *
     * @param cc - a string representing a credit card number; Sample number: 30000000000004(14 digits)
     *
     * @return true, if the credit card number is a valid Carte Blanche number, false otherwise
     */
    public static boolean isCarteBlanche(String cc) {
        return isDinersClub(cc);
    }

    /**
     * Checks to see if the cc number is a valid Discover number
     *
     * @param cc - a string representing a credit card number; Sample number: 6011000000000004(16 digits)
     *
     * @return true, if the credit card number is a valid Discover card number, false otherwise
     */
    public static boolean isDiscover(String cc) {
        String first4digs = cc.substring(0, 4);

        if ((cc.length() == 16) && ("6011".equals(first4digs))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid EnRoute number
     *
     * @param cc - a string representing a credit card number; Sample number: 201400000000009(15 digits)
     *
     * @return true, if the credit card number is a valid enRoute card number, false, otherwise
     */
    public static boolean isEnRoute(String cc) {
        String first4digs = cc.substring(0, 4);

        if ((cc.length() == 15) && ("2014".equals(first4digs) || "2149".equals(first4digs))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid JCB number
     *
     * @param cc - a string representing a credit card number; Sample number: 3088000000000009(16 digits)
     *
     * @return true, if the credit card number is a valid JCB card number, false otherwise
     */
    public static boolean isJCB(String cc) {
        String first4digs = cc.substring(0, 4);

        if ((cc.length() == 16) &&
                ("3088".equals(first4digs) ||
                        "3096".equals(first4digs) ||
                        "3112".equals(first4digs) ||
                        "3158".equals(first4digs) ||
                        "3337".equals(first4digs) ||
                        "3528".equals(first4digs))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid Switch number
     *
     * @param cc - a string representing a credit card number; Sample number: 6331100000000096(16 digits)
     *
     * @return true, if the credit card number is a valid Switch card number, false otherwise
     */
    public static boolean isswitch(String cc) {
        String first4digs = cc.substring(0, 4);
        String first6digs = cc.substring(0, 6);

        if (((cc.length() == 16) || (cc.length() == 18) || (cc.length() == 19)) &&
                ("4903".equals(first4digs) ||
                        "4905".equals(first4digs) ||
                        "4911".equals(first4digs) ||
                        "4936".equals(first4digs) ||
                        "564182".equals(first6digs) ||
                        "633110".equals(first6digs) ||
                        "6333".equals(first4digs) ||
                        "6759".equals(first4digs))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid Solo number
     *
     * @param cc - a string representing a credit card number; Sample number: 6331100000000096 (16 digits)
     *
     * @return true, if the credit card number is a valid Solo card number, false otherwise
     */
    public static boolean isSolo(String cc) {
        String first4digs = cc.substring(0, 4);
        String first2digs = cc.substring(0, 2);
        if (((cc.length() == 16) || (cc.length() == 18) || (cc.length() == 19)) &&
                ("63".equals(first2digs) || "6767".equals(first4digs))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid Visa Electron number
     *
     * @param cc - a string representing a credit card number; Sample number: 4175000000000001(16 digits)
     *
     * @return true, if the credit card number is a valid Visa Electron card number, false otherwise
     */
    public static boolean isVisaElectron(String cc) {
        String first6digs = cc.substring(0, 6);
        String first4digs = cc.substring(0, 4);

        if ((cc.length() == 16) &&
                ("417500".equals(first6digs) ||
                        "4917".equals(first4digs) ||
                        "4913".equals(first4digs) ||
                        "4508".equals(first4digs) ||
                        "4844".equals(first4digs) ||
                        "4027".equals(first4digs))) {
            return isCreditCard(cc);
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid number for any accepted credit card
     *
     * @param ccPassed - a string representing a credit card number
     *
     * @return true, if the credit card number is any valid credit card number for any of the accepted card types, false otherwise
     */
    public static boolean isAnyCard(String ccPassed) {
        if (isEmpty(ccPassed)) {
            return defaultEmptyOK;
        }

        String cc = stripCharsInBag(ccPassed, creditCardDelimiters);

        if (!isCreditCard(cc)) {
            return false;
        }
        if (isMasterCard(cc) || isVisa(cc) || isAmericanExpress(cc) || isDinersClub(cc) ||
                isDiscover(cc) || isEnRoute(cc) || isJCB(cc) || isSolo(cc) || isswitch(cc) || isVisaElectron(cc)) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the cc number is a valid number for any accepted credit card, and return the name of that type
     *
     * @param ccPassed - a string representing a credit card number
     *
     * @return true, if the credit card number is any valid credit card number for any of the accepted card types, false otherwise
     */
    public static String getCardType(String ccPassed) {
        if (isEmpty(ccPassed)) {
            return "Unknown";
        }
        String cc = stripCharsInBag(ccPassed, creditCardDelimiters);

        if (!isCreditCard(cc)) {
            return "Unknown";
        }

        if (isMasterCard(cc)) {
            return "CCT_MASTERCARD";
        }
        if (isVisa(cc)) {
            return "CCT_VISA";
        }
        if (isAmericanExpress(cc)) {
            return "CCT_AMERICANEXPRESS";
        }
        if (isDinersClub(cc)) {
            return "CCT_DINERSCLUB";
        }
        if (isDiscover(cc)) {
            return "CCT_DISCOVER";
        }
        if (isEnRoute(cc)) {
            return "CCT_ENROUTE";
        }
        if (isJCB(cc)) {
            return "CCT_JCB";
        }
        if (isSolo(cc)) {
            return "CCT_SOLO";
        }
        if (isswitch(cc)) {
            return "CCT_SWITCH";
        }
        if (isVisaElectron(cc)) {
            return "CCT_VISAELECTRON";
        }

        return "Unknown";
    }

    /**
     * Checks to see if the cc number is a valid number for the specified type
     *
     * @param cardType - a string representing the credit card type
     * @param cardNumberPassed - a string representing a credit card number
     *
     * @return true, if the credit card number is valid for the particular credit card type given in "cardType", false otherwise
     */
    public static boolean isCardMatch(String cardType, String cardNumberPassed) {
        if (isEmpty(cardType)) {
            return defaultEmptyOK;
        }
        if (isEmpty(cardNumberPassed)) {
            return defaultEmptyOK;
        }
        String cardNumber = stripCharsInBag(cardNumberPassed, creditCardDelimiters);

        if (("CCT_VISA".equalsIgnoreCase(cardType)) && (isVisa(cardNumber))) {
            return true;
        }
        if (("CCT_MASTERCARD".equalsIgnoreCase(cardType)) && (isMasterCard(cardNumber))) {
            return true;
        }
        if ((("CCT_AMERICANEXPRESS".equalsIgnoreCase(cardType)) || ("CCT_AMEX".equalsIgnoreCase(cardType))) && (isAmericanExpress(cardNumber))) {
            return true;
        }
        if (("CCT_DISCOVER".equalsIgnoreCase(cardType)) && (isDiscover(cardNumber))) {
            return true;
        }
        if (("CCT_JCB".equalsIgnoreCase(cardType)) && (isJCB(cardNumber))) {
            return true;
        }
        if ((("CCT_DINERSCLUB".equalsIgnoreCase(cardType)) || ("CCT_DINERS".equalsIgnoreCase(cardType))) && (isDinersClub(cardNumber))) {
            return true;
        }
        if (("CCT_CARTEBLANCHE".equalsIgnoreCase(cardType)) && (isCarteBlanche(cardNumber))) {
            return true;
        }
        if (("CCT_ENROUTE".equalsIgnoreCase(cardType)) && (isEnRoute(cardNumber))) {
            return true;
        }
        if (("CCT_SOLO".equalsIgnoreCase(cardType)) && (isSolo(cardNumber))) {
            return true;
        }
        if (("CCT_SWITCH".equalsIgnoreCase(cardType)) && (isswitch(cardNumber))) {
            return true;
        }
        if (("CCT_VISAELECTRON".equalsIgnoreCase(cardType)) && (isVisaElectron(cardNumber))) {
            return true;
        }
        return false;
    }

    private static boolean isValidEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).length() == 0;
        }
        if (value instanceof Collection) {
            return ((Collection<? extends Object>) value).size() == 0;
        }
        if (value instanceof Map) {
            return ((Map<? extends Object, ? extends Object>) value).size() == 0;
        }
        if (value instanceof CharSequence) {
            return ((CharSequence) value).length() == 0;
        }
        if (value instanceof IsEmpty) {
            return ((IsEmpty) value).isEmpty();
        }
        if (value instanceof Boolean) {
            return false;
        }
        if (value instanceof Number) {
            return false;
        }
        if (value instanceof Character) {
            return false;
        }
        if (value instanceof java.util.Date) {
            return false;
        }

        return false;
    }
}
