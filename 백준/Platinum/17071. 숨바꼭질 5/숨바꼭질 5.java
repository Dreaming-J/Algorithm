import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int MAX_SIZE = 500_000;
    static int start, target;
    static int time;

    public static void findMinTime() {
        boolean[][] visited = new boolean[2][MAX_SIZE + 1];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int cur = queue.poll();

                if (cur == target) {
                    return;
                }

                if (visited[time % 2][cur])
                    continue;
                visited[time % 2][cur] = true;

                if (cur * 2 <= MAX_SIZE)
                    queue.add(cur * 2);

                if (cur + 1 <= MAX_SIZE)
                    queue.add(cur + 1);

                if (cur - 1 >= 0)
                    queue.add(cur - 1);
            }

            time++;
            if ((target += time) > MAX_SIZE)
                return;

            if (visited[time % 2][target])
                return;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findMinTime();

        System.out.println(target > MAX_SIZE ? -1 : time);
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        start = Integer.parseInt(st.nextToken());
        target = Integer.parseInt(st.nextToken());
    }
}

