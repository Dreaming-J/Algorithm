/*
= BOJ 20304. 비밀번호 제작

- 특이사항
XOR 연산의 특징: x ^ (2의 배수) -> 기존의 x와 1 비트 차이나는 숫자를 뽑아낼 수 있다.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    private static final int NOT_VISITED = -1;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int maxNumber, usedPasswordSize;
    static Deque<Integer> candidatePasswords;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(findMaxSafeDistance());
    }

    private static int findMaxSafeDistance() {
        int safeDistance = 0;
        
        while (!candidatePasswords.isEmpty()) {
            int size = candidatePasswords.size();

            while (size-- > 0) {
                int cur = candidatePasswords.poll();

                for (int idx = 0, end = Integer.SIZE - Integer.numberOfLeadingZeros(maxNumber); idx < end; idx++) {
                    int next = cur ^ 1 << idx;

                    if (next > maxNumber)
                        continue;

                    if (visited[next])
                        continue;

                    visited[next] = true;
                    candidatePasswords.add(next);
                }
            }
            
            safeDistance++;
        }

        return safeDistance - 1;
    }

    private static void init() throws IOException {
        maxNumber = Integer.parseInt(input.readLine());

        candidatePasswords = new ArrayDeque<>();
        visited = new boolean[maxNumber + 1];

        usedPasswordSize = Integer.parseInt(input.readLine());

        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < usedPasswordSize; idx++) {
            int num = Integer.parseInt(st.nextToken());
            candidatePasswords.add(num);
            visited[num] = true;
        }
    }
}