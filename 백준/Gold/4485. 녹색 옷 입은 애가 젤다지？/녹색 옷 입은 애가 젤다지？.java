import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int problemIdx;
    static int caveSize;
    static int[][] cave;
    static int[][] minCost;
    static boolean[][] visit;

    public static class Cell {
        int row;
        int col;
        int cost;

        public Cell(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }
    }

    public static void findMinCost() {
        PriorityQueue<Cell> pq = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        pq.add(new Cell(0, 0, cave[0][0]));

        while (!pq.isEmpty()) {
            Cell cur = pq.poll();

            if (visit[cur.row][cur.col])
                continue;
            visit[cur.row][cur.col] = true;

            for (int[] delta : deltas) {
                int nr = cur.row + delta[0];
                int nc = cur.col + delta[1];

                if (!canGo(nr, nc))
                    continue;

                if (cur.cost + cave[nr][nc] < minCost[nr][nc]) {
                    minCost[nr][nc] = cur.cost + cave[nr][nc];
                    pq.add(new Cell(nr, nc, minCost[nr][nc]));
                }
            }
        }
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < caveSize && col >= 0 && col < caveSize;
    }

    public static void main(String[] args) throws IOException {
        problemIdx = 1;

        while (true) {
            caveSize = Integer.parseInt(input.readLine());
            if (caveSize == 0)
                break;

            cave = new int[caveSize][caveSize];
            for (int row = 0; row < caveSize; row++) {
                st = new StringTokenizer(input.readLine());
                for (int col = 0; col < caveSize; col++) {
                    cave[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            visit = new boolean[caveSize][caveSize];
            minCost = new int[caveSize][caveSize];
            for (int[] row : minCost) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
            minCost[0][0] = 0;

            findMinCost();

            output.append("Problem ").append(problemIdx++).append(": ").append(minCost[caveSize - 1][caveSize - 1])
                    .append("\n");
        }

        System.out.println(output);
    }
}