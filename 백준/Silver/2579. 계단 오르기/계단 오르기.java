import java.io.*;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int stairSize;
    static int[] scores;
    static int[][] dp;
    
    public static void main(String[] args) throws IOException {
        init();
        
        dp[0][1] = scores[1];
        for (int idx = 2; idx <= stairSize; idx++) {
            dp[1][idx] = dp[0][idx - 1] + scores[idx];
            dp[0][idx] = Math.max(dp[0][idx - 2], dp[1][idx - 2]) + scores[idx];
        }
        
        System.out.println(Math.max(dp[0][stairSize], dp[1][stairSize]));
    }
    
    public static void init() throws IOException {
        stairSize = Integer.parseInt(input.readLine());
        
        scores = new int[stairSize + 1];
        for (int idx = 1; idx <= stairSize; idx++) {
            scores[idx] = Integer.parseInt(input.readLine());
        }
        
        dp = new int[2][stairSize + 1];
    }
}