/*
= BOJ 1943. 동전 분배
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int TEST_CASE_SIZE = 3;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int coinSize;
    static Coin[] coins;
    static int targetCost;
    static int[] dp;

    static class Coin {
        int cost, count;

        public Coin(int cost, int count) {
            this.cost = cost;
            this.count = count;
        }
    }

    public static void main(String[] args) throws IOException {
        for (int tc = 0; tc < TEST_CASE_SIZE; tc++) {
            if (!init()) {
                output.append(0)
                        .append("\n");
                continue;
            }
            
            for (Coin coin : coins) {
                for (int count = 1; count <= coin.count; count++) {
                    for (int cost = targetCost; cost >= coin.cost * count; cost--) {
                        dp[cost] = Math.max(dp[cost - coin.cost], dp[cost]);
                    }
                }
            }            
            
            output.append(dp[targetCost])
                    .append("\n");
        }

        System.out.println(output);
    }

    private static boolean init() throws IOException {
        coinSize = Integer.parseInt(input.readLine());

        coins = new Coin[coinSize];
        targetCost = 0;
        for (int idx = 0; idx < coinSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            coins[idx] = new Coin(cost, count);
            targetCost += cost * count;
        }
        
        if (targetCost % 2 != 0)
            return false;
        
        targetCost /= 2;
        dp = new int[targetCost + 1];
        dp[0] = 1;
        
        return true;
    }
}