import java.util.*;

public class CountDistinctPalindromicSubsequences {

    public static int solution(String str) {
        char[] chars = str.toCharArray();
        int len = str.length();
        int x = 0;
        while(x < len) {  // If characters don't match A, C,G or T, then return -1
            if ((chars[x] == 'A') || (chars[x] == 'C') || (chars[x] == 'G') || (chars[x] == 'T'))
                x++;
            else
                return -1;
        }
        if(len == 0)  // If string is null, then return 0
            return 0;

        long[][] dp = new long[len][len]; // Declare DP
        long mod = 1_000_000_007;  // To handle negative values

        HashMap<Character, Integer> map = new HashMap<>();

        int[] next = new int[len];  // Array to store the indexes of the next occurrence of each character
        for(int i=len-1; i>=0; i--) {
            if(!map.containsKey(str.charAt(i)))
                next[i] = -1;
            else
                next[i] = map.get(str.charAt(i));
            map.put(str.charAt(i), i);
        }

        int[] prev = new int[len];  // Array to store the indexes of the previous occurrence of each character
        for(int i=0; i<len; i++) {
            if(!map.containsKey(str.charAt(i)))
                prev[i] = -1;
            else
                prev[i] = map.get(str.charAt(i));
            map.put(str.charAt(i), i);
        }

        for(int g=0; g<len; g++) {  // Gap Strategy 2D array, row declaration
            for(int i=0, j=g; j<len; i++, j++) {
                if(g == 0)  // If gap is 0
                    dp[i][j] = 1;
                else if (g == 1)  // If gap is 1
                    dp[i][j] = 2;
                else {  // If gap is more than 1
                    char startChar = str.charAt(i);
                    char endChar = str.charAt(j);
                    if(startChar != endChar)
                        dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1];
                    else {  // If startChar == endChar, now we are checking if that same character is present anywhere inside or not.
                        int nextIndexFromStart = next[i];
                        int prevIndexFromEnd = prev[j];
                        if(nextIndexFromStart > prevIndexFromEnd)  // no occurrence in between
                            dp[i][j] = 2 * dp[i+1][j-1] + 2;
                        else if (nextIndexFromStart == prevIndexFromEnd) // only one occurrence in between
                            dp[i][j] = 2 * dp[i+1][j-1] + 1;
                        else {  // More than one occurrence in between
                            dp[i][j] = 2 * dp[i+1][j-1] - dp[nextIndexFromStart + 1][prevIndexFromEnd - 1];
                        }
                    }
                }
                dp[i][j] = (dp[i][j] + mod) % mod;
            }
        }
        return (int) dp[0][str.length() - 1];
    }

    public static void main (String[] args) {
        // Scanner in = new Scanner(System.in);
        String str = "ACGTGA";
        System.out.println(solution(str));
    }

}
