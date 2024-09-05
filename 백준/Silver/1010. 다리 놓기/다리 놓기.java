/*
= 백준 1010. 다리 놓기

1. 테스트 케이스의 개수 입력
2. 서쪽과 동쪽에 있는 사이트의 개수 입력
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
    static long[][] combinations = new long[30][30];

    public static long combi(int total, int subset) {
        if (combinations[total][subset] != 0)
            return combinations[total][subset];

        if (subset == 0 || total == subset)
            return combinations[total][subset] = 1;

        return combinations[total][subset] = combi(total - 1, subset - 1) + combi(total - 1, subset);
    }

    public static void main(String[] args) throws IOException {
        //1. 테스트 케이스의 개수 입력
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 0; tc < testCase; tc++) {
            //2. 서쪽과 동쪽에 있는 사이트의 개수 입력
            st = new StringTokenizer(input.readLine());
            int westSize = Integer.parseInt(st.nextToken());
            int eastSize = Integer.parseInt(st.nextToken());

            //3. 출력
            output.append(combi(eastSize, westSize)).append("\n");
        }

        System.out.println(output);
    }
}
