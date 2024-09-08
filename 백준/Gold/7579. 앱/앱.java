/*
= BOJ 7579. 앱

= 로직
1. 초기 세팅
	1-1. 앱의 개수, 확보할 메모리 입력
	1-2. 앱의 사용 메모리 입력
	1-3. 앱의 비활성화 비용 입력
	1-4. 변수 초기화
2. 최소 비용 탐색
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int INF = 100_001;
    static int appSize, targetMemory;
    static App[] apps;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 최소 비용 탐색
        for (App app : apps) {
            for (int memory = targetMemory; memory >= 1; memory--) {
                if (memory < app.memory) {
                    dp[memory] = Math.min(app.cost, dp[memory]);
                } 
                else {
                    dp[memory] = Math.min(dp[memory - app.memory] + app.cost, dp[memory]);
                }
            }
        }

        //3.출력
        System.out.println(dp[targetMemory]);
    }

    public static class App {
        int memory, cost;

        public App(int memory) {
            this.memory = memory;
        }
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 앱의 개수, 확보할 메모리 입력
        st = new StringTokenizer(input.readLine());
        appSize = Integer.parseInt(st.nextToken());
        targetMemory = Integer.parseInt(st.nextToken());

        //1-2. 앱의 사용 메모리 입력
        apps = new App[appSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < appSize; idx++) {
            apps[idx] = new App(Integer.parseInt(st.nextToken()));
        }

        //1-3. 앱의 비활성화 비용 입력
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < appSize; idx++) {
            apps[idx].cost = Integer.parseInt(st.nextToken());
        }

        //1-4. 변수 초기화
        dp = new int[targetMemory + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
    }
}
