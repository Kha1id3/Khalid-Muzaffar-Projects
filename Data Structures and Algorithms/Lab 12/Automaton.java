package lab12dsa;

import java.util.LinkedList;

public class Automaton implements IStringMatcher {

    public int limit = 256;

    @Override
    public LinkedList<Integer> validShifts(String pattern, String text) {
        int m = pattern.length();
        int n = text.length();
        LinkedList<Integer> found = new LinkedList<>();
        int[][] arr = new int[m + 1][limit];
        char[] txt = text.toCharArray();

        for (int i = 0; i <= m; ++i) {
            for (int j = 0; j < limit; ++j) {
                arr[i][j] = nextState(pattern, i, j);
            }
        }

        int k = 0;
        for (int i = 0; i < n; i++) {
            k = arr[k][txt[i]];
            if (k == m) {
                found.add(i - m + 1);
            }
        }
        return found;
    }

    public int nextState(String pattern, int s1, int s2) {
        int m = pattern.length();
        char[] pat = pattern.toCharArray();

        if (s1 < m && s2 == pat[s1])
            return s1 + 1;

        int i;
        for (int next = s1; next > 0; next--) {
            if (pat[next - 1] == s2) {
                for (i = 0; i < next - 1; i++)
                    if (pat[i] != pat[s1 - next + 1 + i])
                        break;
                if (i == next - 1)
                    return next;
            }
        }
        return 0;
    }
}
