/*
= BOJ 12851. 숨바꼭질 2

= 로직
1. 초기 세팅
    1-1. 시작 위치, 도착 위치 입력
    1-2. 변수 초기화
2. 최소 시간 탐색
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int MIN_POSITON = 0, MAX_POSITION = 100_000;
    static int start, end;
    static int minTime, numberOfCase;

    //2. 최소 시간 탐색
    public static void findMinTime() {
        int[] visited = new int[MAX_POSITION + 1];
        Arrays.fill(visited, Integer.MAX_VALUE);

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        int time = 0;
        for (; !queue.isEmpty(); time++) {
            int size = queue.size();

            //시간 별로 탐색 진행
            while (size-- > 0) {
                int cur = queue.poll();

                //목표에 도달했다면 최소 시간 갱신 및 경우의 수 추가
                if (cur == end) {
                    minTime = time;
                    numberOfCase++;
                }

                if (time > visited[cur])
                    continue;
                visited[cur] = time;

                //1칸 뒤로 이동
                if (cur - 1 >= MIN_POSITON) {
                    queue.add(cur - 1);
                }

                //1칸 앞으로 이동
                if (cur + 1 <= MAX_POSITION) {
                    queue.add(cur + 1);
                }

                //2배 앞으로 이동
                if (cur * 2 <= MAX_POSITION) {
                    queue.add(cur * 2);
                }
            }

            if (minTime != Integer.MAX_VALUE)
                return;
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 최소 시간 탐색
        findMinTime();

        //3.출력
        System.out.printf("%d\n%d", minTime, numberOfCase);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 시작 위치, 도착 위치 입력
        st = new StringTokenizer(input.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        //1-2. 변수 초기화
        minTime = Integer.MAX_VALUE;
    }
}
