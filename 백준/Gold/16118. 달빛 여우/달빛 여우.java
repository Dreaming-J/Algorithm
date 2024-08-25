import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int stumpSize, pathSize;
    static Stump[] mountain;
    static double[] foxCost;
    static boolean[] foxVisit;
    static double[][] wolfCost;
    static boolean[][] wolfVisit;
    static int answer;

    public static class Stump {
        int num;
        double cost;
        int count;
        Stump next;

        public Stump(int num, double cost, int count) {
            this.num = num;
            this.cost = cost;
            this.count = count;
        }

        public Stump(int num, double cost, int count, Stump next) {
            this.num = num;
            this.cost = cost;
            this.count = count;
            this.next = next;
        }
    }

    public static void findFoxCost() {
        PriorityQueue<Stump> pq = new PriorityQueue<>(((o1, o2) -> (int) (o1.cost - o2.cost)));
        pq.add(new Stump(1, 0, 0));

        while (!pq.isEmpty()) {
            Stump cur = pq.poll();

            if (foxVisit[cur.num])
                continue;
            foxVisit[cur.num] = true;

            Stump next = mountain[cur.num];
            while (next != null) {
                double cost = foxCost[cur.num] + next.cost;

                if (cost < foxCost[next.num]) {
                    foxCost[next.num] = cost;
                    pq.add(new Stump(next.num, cost, cur.count + 1));
                }

                next = next.next;
            }
        }
    }

    public static void findWolfCost() {
        PriorityQueue<Stump> pq = new PriorityQueue<>(((o1, o2) -> (int) (o1.cost - o2.cost)));
        pq.add(new Stump(1, 0, 0));

        while (!pq.isEmpty()) {
            Stump cur = pq.poll();

            if (wolfVisit[cur.count][cur.num])
                continue;
            wolfVisit[cur.count][cur.num] = true;

            Stump next = mountain[cur.num];
            while (next != null) {
                double cost = wolfCost[(cur.count + 1) % 2][cur.num] + (cur.count == 0 ? next.cost / 2 : next.cost * 2);

                if (cost < wolfCost[cur.count][next.num]) {
                    wolfCost[cur.count][next.num] = cost;
                    pq.add(new Stump(next.num, cost, (cur.count + 1) % 2));
                }

                next = next.next;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        initTestCase();

        findFoxCost();
        findWolfCost();

        for (int idx = 1; idx <= stumpSize; idx++) {
            answer += foxCost[idx] < Math.min(wolfCost[0][idx], wolfCost[1][idx]) ? 1 : 0;
        }

        System.out.println(answer);
    }

    public static void initTestCase() throws IOException {
        st = new StringTokenizer(input.readLine());
        stumpSize = Integer.parseInt(st.nextToken());
        pathSize = Integer.parseInt(st.nextToken());

        mountain = new Stump[stumpSize + 1];
        for (int idx = 0; idx < pathSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int to = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            mountain[to] = new Stump(from, cost, 0, mountain[to]);
            mountain[from] = new Stump(to, cost, 0, mountain[from]);
        }

        foxCost = new double[stumpSize + 1];
        wolfCost = new double[2][stumpSize + 1];
        for (int idx = 1; idx <= stumpSize; idx++) {
            foxCost[idx] = idx == 1 ? 0 : Integer.MAX_VALUE;
            wolfCost[0][idx] = Integer.MAX_VALUE;
            wolfCost[1][idx] = idx == 1 ? 0 : Integer.MAX_VALUE;
        }

        foxVisit = new boolean[stumpSize + 1];
        wolfVisit = new boolean[2][stumpSize + 1];
    }
}