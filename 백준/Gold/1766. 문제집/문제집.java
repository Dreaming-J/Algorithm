/*
= BOJ 1766. 문제집

= 특이사항
위상 정렬
문제의 수는 1번부터 시작
최대한 문제 번호가 낮은 순으로 풀이 -> 우선순위 큐
모든 문제 풀 수 있도록 보장

= 로직
1. 초기 세팅
    1-1. 문제의 수, 선수 문제의 수 입력
    1-2. 선수 문제의 수만큼 선수 문제 정보 입력
    1-3. 변수 초기화
2. 위상 정렬
    2-1. 진입차수가 0인 문제 찾기
    2-2. 탐색 시작
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input;
    static StringTokenizer st;
    static int problemSize, preProblemSize;
    static List<Integer>[] problems;
    static int[] inDegreeCount;
    static StringJoiner sequence;

    private static void topologicalSort() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        //2-1. 진입차수가 0인 문제 찾기
        for (int idx = 1; idx <= problemSize; idx++) {
            if (inDegreeCount[idx] == 0)
                pq.add(idx);
        }

        //2-2. 탐색 시작
        for (int idx = 0; idx < problemSize; idx++) {
            int num = pq.poll();

            sequence.add(String.valueOf(num));

            for (int next : problems[num]) {
                if (--inDegreeCount[next] == 0)
                    pq.add(next);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new InputStreamReader(System.in));

        //1. 초기 세팅
        init();

        //2. 위상 정렬
        topologicalSort();

        System.out.println(sequence);
    }

    private static void init() throws IOException {
        //1-1. 문제의 수, 선수 문제의 수 입력
        st = new StringTokenizer(input.readLine());
        problemSize = Integer.parseInt(st.nextToken());
        preProblemSize = Integer.parseInt(st.nextToken());

        //1-2. 선수 문제의 수만큼 선수 문제 정보 입력
        problems = new ArrayList[problemSize + 1];
        for (int idx = 1; idx <= problemSize; idx++) {
            problems[idx] = new ArrayList<>();
        }
        inDegreeCount = new int[problemSize + 1];

        for (int idx = 0; idx < preProblemSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            problems[from].add(to);
            inDegreeCount[to]++;
        }

        //1-3. 변수 초기화
        sequence = new StringJoiner(" ");
    }
}
