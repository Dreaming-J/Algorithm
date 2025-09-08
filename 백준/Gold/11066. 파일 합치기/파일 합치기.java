import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while(T-- > 0){
            int n = Integer.parseInt(br.readLine());
            int[] file = new int[n + 1];
            int[] sum = new int[n + 1];
            int[][] dp = new int[n + 1][n + 1];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                file[i] = Integer.parseInt(st.nextToken());
                sum[i] = sum[i - 1] + file[i];
            }
            for (int i = 1; i < n; i++)
                dp[i][i + 1] = file[i] + file[i + 1];
            for (int k = 2; k < n; k++){
                for (int i = 1; i <= n - k; i++){
                    int j = i + k;
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int p = i; p < j; p++)
                        dp[i][j] = Math.min(dp[i][j], dp[i][p] + dp[p + 1][j] + sum[j] - sum[i - 1]);
                }
            }
            sb.append(dp[1][n]).append("\n");
        }
        System.out.print(sb);
    }
}