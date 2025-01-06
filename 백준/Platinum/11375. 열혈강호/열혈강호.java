/*
= BOJ 11375. 열혈강호
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int workerSize, taskSize;
    static List<Integer>[] taskGraph;
    static int[] taskOwners;
    static boolean[] visited;
    static int answer;

    public static void main(String[] args) throws IOException {
        init();

        for (int workerIdx = 1; workerIdx <= workerSize; workerIdx++) {
            Arrays.fill(visited, false);

            if (matchWorkers(workerIdx))
                answer++;
        }

        System.out.println(answer);
    }

    private static boolean matchWorkers(int workerIdx) {
        for (int taskIdx : taskGraph[workerIdx]) {
            if (visited[taskIdx])
                continue;
            visited[taskIdx] = true;

            if (taskOwners[taskIdx] == 0 || matchWorkers(taskOwners[taskIdx])) {
                taskOwners[taskIdx] = workerIdx;
                return true;
            }
        }

        return false;
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        workerSize = Integer.parseInt(st.nextToken());
        taskSize = Integer.parseInt(st.nextToken());

        taskGraph = new List[workerSize + 1];
        for (int workerIdx = 1; workerIdx <= workerSize; workerIdx++) {
            taskGraph[workerIdx] = new ArrayList<>();

            st = new StringTokenizer(input.readLine());
            int availableTaskSize = Integer.parseInt(st.nextToken());

            for (int taskIdx = 0; taskIdx < availableTaskSize; taskIdx++)
                taskGraph[workerIdx].add(Integer.parseInt(st.nextToken()));
        }

        taskOwners = new int[taskSize + 1];
        visited = new boolean[taskSize + 1];
    }
}