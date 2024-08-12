/*
#백준 15649. N과 M (1)
1부터 N까지 자연수 중 중복 없이 M개를 고른 수열을 사전 순으로 출력해라.

#입력
첫째 줄: N M (1 <= M <= N <= 8)
#출력
한 줄에 하나씩 수열을 출력

#로직
nPr 순열 로직을 이용해 풀이
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder output = new StringBuilder();

    public static void permutation(int selectIdx, int[] selectElements, int elementUsedCheck, int maxNum, int count) {
        if (selectIdx == count) {
            for (int idx = 0; idx < count; idx++) {
                output.append(selectElements[idx]).append(" ");
            }
            output.append("\n");
            return;
        }

        for (int num = 1; num <= maxNum; num++) {
            //방문했다면, 패스
            if (((elementUsedCheck >> num) & 1) == 1)
                continue;

            //방문
            selectElements[selectIdx] = num;
            permutation(selectIdx + 1, selectElements, elementUsedCheck + (1 << num), maxNum, count);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine().trim());
        int maxNum = Integer.parseInt(st.nextToken());
        int count = Integer.parseInt(st.nextToken());

        permutation(0, new int[count], 0, maxNum, count);

        System.out.println(output.toString());
    }
}
