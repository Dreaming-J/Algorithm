import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input;
    static StringBuilder output;
    static StringTokenizer st;
    static int testCase;
    static int buildingSize, sequnceSize;
    static int[] buildTime;
    static List<Integer>[] sequence;
    static int[] inDegree;
    static int[] reachedTime;
    static int targetBuilding;

    public static void topologicalSort() {
        Deque<Integer> queue = new ArrayDeque<>();

        for (int idx = 1; idx <= buildingSize; idx++) {
            if (inDegree[idx] == 0) {
                queue.add(idx);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            if (cur == targetBuilding) {
                return;
            }

            for (int next : sequence[cur]) {
                reachedTime[next] = Math.max(buildTime[cur] + reachedTime[cur], reachedTime[next]);

                if (--inDegree[next] == 0) {
                    queue.add(next);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new InputStreamReader(System.in));
        output = new StringBuilder();

        testCase = Integer.parseInt(input.readLine());
        for (int tc = 0; tc < testCase; tc++) {
            initTestCase();

            topologicalSort();

            output.append(reachedTime[targetBuilding] + buildTime[targetBuilding]).append("\n");
        }

        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        st = new StringTokenizer(input.readLine());
        buildingSize = Integer.parseInt(st.nextToken());
        sequnceSize = Integer.parseInt(st.nextToken());

        buildTime = new int[buildingSize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= buildingSize; idx++) {
            buildTime[idx] = Integer.parseInt(st.nextToken());
        }

        sequence = new ArrayList[buildingSize + 1];
        for (int idx = 1; idx <= buildingSize; idx++) {
            sequence[idx] = new ArrayList<>();
        }

        inDegree = new int[buildingSize + 1];
        for (int idx = 0; idx < sequnceSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            sequence[from].add(to);
            inDegree[to]++;
        }

        reachedTime = new int[buildingSize + 1];
        targetBuilding = Integer.parseInt(input.readLine());
    }
}