class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int answer = 0;
        
        int[][] dp = new int[m + 1][n + 1];
        dp[1][1] = 1;
        for (int[] puddle : puddles) {
            dp[puddle[0]][puddle[1]] = -1;
        }
        
        for (int i = 1; i < m + n + 1; i++) {
            for (int j = 0; j <= i; j++) {
                int r = i - j + 1;
                int c = j + 1;
                
                if (r > m || c > n) continue;
                if (dp[r][c] == -1) {
                    dp[r][c] = 0;
                    continue;
                }
                
                dp[r][c] = (dp[r - 1][c] + dp[r][c - 1]) % 1_000_000_007;
            }
        }
        
        return dp[m][n];
    }
}