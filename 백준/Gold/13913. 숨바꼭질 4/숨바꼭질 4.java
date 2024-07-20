import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {
    static final int MAX_SIZE = 100_000;
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int subinPos, brotherPos;
    static int time;
    static List<Integer> answer = new ArrayList<>();
    static boolean visited[] = new boolean[MAX_SIZE + 1];
    static int parent[] = new int[MAX_SIZE + 1];

    public static class Position {
        int time;
        int pos;

        public Position(int time, int pos) {
            this.time = time;
            this.pos = pos;
        }
    }

    public static void bfs(int start, int end) {
        Queue<Position> q = new LinkedList<>();
        q.offer(new Position(0, start));

        while (!q.isEmpty()) {
            Position cur = q.poll();

            if (cur.pos == end) {
                time = cur.time;
                return;
            }

            if (cur.pos * 2 <= MAX_SIZE && !visited[cur.pos * 2]) {
                visited[cur.pos * 2] = true;
                parent[cur.pos * 2] = cur.pos;
                q.add(new Position(cur.time + 1, cur.pos * 2));
            }
            if (cur.pos + 1 <= MAX_SIZE && !visited[cur.pos + 1]) {
                visited[cur.pos + 1] = true;
                parent[cur.pos + 1] = cur.pos;
                q.add(new Position(cur.time + 1, cur.pos + 1));
            }
            if (cur.pos - 1 >= 0 && !visited[cur.pos - 1]) {
                visited[cur.pos - 1] = true;
                parent[cur.pos - 1] = cur.pos;
                q.add(new Position(cur.time + 1, cur.pos - 1));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        subinPos = Integer.parseInt(st.nextToken());
        brotherPos = Integer.parseInt(st.nextToken());

        //최소 시간 계산
        bfs(subinPos, brotherPos);

        //흔적따라 경로 추적
        answer.add(brotherPos);
        for (int idx = brotherPos; idx != subinPos; idx = parent[idx]) {
            answer.add(parent[idx]);
        }
        Collections.reverse(answer);

        System.out.println(time);
        System.out.println(answer.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));
    }
}
