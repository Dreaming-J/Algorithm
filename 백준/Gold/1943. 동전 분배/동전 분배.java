/*
= BOJ 1943. 동전 분배

= 로직
1. 초기 세팅
    1-1. 동전의 종류 입력
    1-2. 동전의 금액과 개수 입력
    1-3. 목표 비용 계산
    1-4. 변수 초기화
2. 분배 가능 여부 탐색
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int TEST_CASE = 3;
    static int coinSize, targetCost;
    static boolean isInteger;
    static Coin[] coins;
    static int[] dp;

    public static void findTargetCost() {
        if (!isInteger) {
            return;
        }

        for (Coin coin : coins) {
            for (int count = 1; count <= coin.count; count++) {
                for (int cost = targetCost; cost >= coin.cost * count; cost--) {
                    dp[cost] = Math.max(dp[cost - coin.cost], dp[cost]);

                    if (dp[targetCost] == 1)
                        return;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        for (int tc = 0; tc < TEST_CASE; tc++) {
            //1. 초기 세팅
            init();

            //2. 분배 가능 여부 탐색
            findTargetCost();

            //3.출력
            output.append(dp[targetCost]).append("\n");
        }
        System.out.print(output);
    }

    public static class Coin {
        int cost, count;

        public Coin(int cost, int count) {
            this.cost = cost;
            this.count = count;
        }
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 동전의 종류 입력
        coinSize = Integer.parseInt(input.readLine());

        //1-2. 동전의 금액과 개수 입력
        coins = new Coin[coinSize];
        double totalCost = 0;
        for (int idx = 0; idx < coinSize; idx++) {
            st = new StringTokenizer(input.readLine());
            coins[idx] = new Coin(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            totalCost += coins[idx].cost * coins[idx].count;
        }

        //1-3. 목표 비용 계산
        targetCost = (int) totalCost / 2;
        isInteger = totalCost == targetCost * 2;

        //1-4. 변수 초기화
        dp = new int[targetCost + 1];
        dp[0] = 1;
    }
}
