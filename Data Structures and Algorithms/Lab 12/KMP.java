package lab12dsa;

import java.util.LinkedList;

public class KMP implements IStringMatcher {


    @Override
    public LinkedList<Integer> validShifts(String pattern, String text) {
        int m = pattern.length();
        int n = text.length();
        LinkedList<Integer> found = new LinkedList<>();
        int lps[] = new int[m];
        int j = 0;
        longestPreSuffixArr(pattern, lps);

        int i = 0;
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == m) {
                found.add(i - j);
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }
        return found;

    }

    public void longestPreSuffixArr(String pattern, int lps[]) {
        int m = pattern.length();
        int len = 0;
        int i = 1;
        lps[0] = 0;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

}