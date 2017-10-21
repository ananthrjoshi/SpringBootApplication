package service;

public class RegexParser {
	boolean isRegEx = true;
    String pattern = null;

    public static void main(String[] args) {
        RegexParser parser = new RegexParser();
        parser.checkParser();
    }

    public void checkParser() {
        String pattern = "a*b+c.";
        boolean isMatch = matchPat(pattern, "bc");
        System.out.println("Match found true -" + isMatch);
        isMatch = matchPat(pattern, "abc");
        System.out.println("Match found  true -" + isMatch);
        isMatch = matchPat(pattern, "aaabc");
        System.out.println("Match found true -" + isMatch);
        isMatch = matchPat(pattern, "bbbbbc");
        System.out.println("Match found true -" + isMatch);
    }

    /**
     * Check the value of character at given index if it's operator or not.
     * 
     * @param pat
     * @param idx
     * @return
     */
    private boolean isOperator(String pat, int idx) {
        if (idx >= pat.length())
            throw new IndexOutOfBoundsException();
        switch (pat.charAt(idx)) {
        case '*':
        case '.':
        case '+':
            return true;
        default:
            return false;
        }
    }

    /**
     * Check if Regex has *, + or .
     * 
     * @param pattern
     * @return
     */
    private boolean checkOperators(String pattern) {
        if (pattern == null)
            throw new NullPointerException("Invalid pattern");

        if (pattern.indexOf('+') > 0 || pattern.indexOf('*') > 0
                || pattern.indexOf('.') > 0)
            return true; // regex have operators
        return false;
    }

    /**
     * Normal String matching, since pattern does not have special operators
     * 
     * @param pattern
     * @param data
     * @return
     */
    private boolean isSame(String pattern, String data) {
        if (pattern == null || data == null)
            throw new NullPointerException("Invalid pattern or String");
        return data.contains(pattern);
    }

    /**
     * Matches pattern with data as per the rules
     * 
     * @param pattern
     * @param data
     * @return
     */
    public boolean matchPat(String pattern, String data) {

        if (pattern == null || data == null)
            throw new NullPointerException("Invalid pattern or String");

        int processedIdx = 0;

        if (!checkOperators(pattern)) {
            return isSame(pattern, data);
        }
        char[] pat = pattern.toCharArray();
        if (isOperator(pattern, 0))
            return false; // throw some exception here; invalid regex starting
                            // with operator
        for (int i = 0; i < pat.length - 1; i++) {
            if (i >= pat.length)
                break;
            if (!isOperator(pattern, i + 1)) {
                processedIdx = handleDot(pattern, i, data, processedIdx);
                if (processedIdx < 0)
                    return false;
            } else {
                switch (pat[i + 1]) {
                case '*':
                    processedIdx = handleStar(pattern, i, data, processedIdx);
                    i++; // Move index to next element
                    if (processedIdx < 0)
                        return false;
                    break;
                case '+':
                    processedIdx = handlePlus(pattern, i, data, processedIdx);
                    i++; // Move index to next element
                    if (processedIdx < 0)
                        return false;
                    break;
                case '.':
                    processedIdx = handleDot(pattern, i, data, processedIdx);
                    i++; // Move index to next element
                    if (processedIdx < 0)
                        return false;
                    break;
                default:
                    processedIdx = handleDot(pattern, i, data, processedIdx);
                    if (processedIdx < 0)
                        return false;
                    break;
                }
            }
        }

        return true;
    }

    /**
     * 
     * Checks for 0 or none character occurrences
     * 
     * if int = -1 match failed
     * 
     * @param data
     * @param processedIdx
     * @return
     */
    private int handleStar(String pattern, int patIdx, String data,
            int processedIdx) {
        int i = 0;
        boolean isFound = false;
        if (processedIdx >= data.length() || patIdx > pattern.length()) {
            return processedIdx;
        }
        for (i = processedIdx; i < data.length(); i++) {
            if (data.charAt(i) == pattern.charAt(patIdx)) {
                isFound = true;
                continue;
            } else {
                break;
            }
        }
        if (isFound)
            return i;
        return processedIdx;
    }

    /**
     * Checks for 1 or more character occurrences
     * 
     * 
     * @param pattern
     * @param patIdx
     * @param data
     * @param processedIdx
     * @return, if returns -1 means match failed
     */
    private int handlePlus(String pattern, int patIdx, String data,
            int processedIdx) {
        int i = 0;
        boolean leastOne = false;
        if (patIdx >= pattern.length())
            return -1; // Ran out of pattern now.

        if (processedIdx >= data.length()
                || data.charAt(processedIdx) != pattern.charAt(patIdx)) {
            return -1;
        }
        for (i = processedIdx; i < data.length(); i++) {
            if (data.charAt(i) == pattern.charAt(patIdx)) {
                if (!leastOne) // At least one match should happen
                    leastOne = true;
                continue;
            } else {
                break;
            }
        }
        if (leastOne)
            return i; // AT least one char should match

        return -1;
    }

    /**
     * Single character match
     * 
     * @param pattern
     * @param patIdx
     * @param data
     * @param processedIdx
     * @return
     */
    private int handleDot(String pattern, int patIdx, String data,
            int processedIdx) {
        if (patIdx >= pattern.length())
            return -1; // Ran out of pattern now.

        if (processedIdx >= data.length()
                || data.charAt(processedIdx++) != pattern.charAt(patIdx)) {
            return -1; // Ran out of String or char is not same
        }

        return processedIdx;
    }
}
