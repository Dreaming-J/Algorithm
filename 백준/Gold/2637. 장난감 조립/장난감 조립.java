import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st = new StringTokenizer("");
    static int totalParts, totalRelations;
    static List<Relation>[] graph;
    static int[] inDegree;
    static int[][] totalPartsNeeded;

    static class Relation {
        int target, count;

        public Relation(int target, int count) {
            this.target = target;
            this.count = count;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        find();

        answer();
    }

    private static void find() {
        Deque<Relation> queue = new ArrayDeque<>();

        for (int idx = 1; idx < totalParts; idx++) {
            if (inDegree[idx] == 0) {
                totalPartsNeeded[idx][idx] = 1;
                queue.add(new Relation(idx, 0));
            }
        }

        while (!queue.isEmpty()) {
            Relation cur = queue.poll();

            for (Relation next : graph[cur.target]) {
                for (int idx = 1; idx < totalParts; idx++)
                    totalPartsNeeded[next.target][idx] += totalPartsNeeded[cur.target][idx] * next.count;

                if (--inDegree[next.target] == 0)
                    queue.add(next);
            }
        }
    }

    private static void init() throws IOException {
        totalParts = Integer.parseInt(input.readLine());

        totalRelations = Integer.parseInt(input.readLine());

        graph = new ArrayList[totalParts + 1];
        for (int idx = 1; idx <= totalParts; idx++)
            graph[idx] = new ArrayList<>();

        inDegree = new int[totalParts + 1];

        for (int idx = 0; idx < totalRelations; idx++) {
            st = new StringTokenizer(input.readLine());
            int target = Integer.parseInt(st.nextToken());
            int source = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            graph[source].add(new Relation(target, count));
            inDegree[target]++;
        }

        totalPartsNeeded = new int[totalParts + 1][totalParts];
    }

    private static void answer() {
        for (int idx = 1; idx < totalParts; idx++) {
            if (totalPartsNeeded[totalParts][idx] == 0)
                continue;

            output.append(idx)
                    .append(" ")
                    .append(totalPartsNeeded[totalParts][idx])
                    .append("\n");
        }

        System.out.println(output);
    }
}