/*
= BOJ 3067. Coins

= 특이 사항
dp

= 로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
    1-1. 동전의 가지 수 입력
    1-2. 동전의 금액 입력(오름차순)
    1-3. 만들어야 하는 금액 입력
    1-4. 변수 초기화
2. 경우의 수 계산
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
    static int testCase, coinSize, targetAmount;
    static int[] coins, dp;

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 개수 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 0; tc < testCase; tc++) {
            //1. 초기 세팅
            init();

            //2. 경우의 수 계산
            for (int coinIdx = 0; coinIdx < coinSize; coinIdx++) {
                for (int amount = coins[coinIdx]; amount <= targetAmount; amount++) {
                    dp[amount] += dp[amount - coins[coinIdx]];
                }
            }

            //3. 출력
            output.append(dp[targetAmount]).append("\n");
        }
        System.out.print(output);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 동전의 가지 수 입력
        coinSize = Integer.parseInt(input.readLine());

        //1-2. 동전의 금액 입력(오름차순)
        coins = new int[coinSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < coinSize; idx++) {
            coins[idx] = Integer.parseInt(st.nextToken());
        }

        //1-3. 만들어야 하는 금액 입력
        targetAmount = Integer.parseInt(input.readLine());

        //1-4. 변수 초기화
        dp = new int[targetAmount + 1];
        dp[0] = 1;
    }
}
