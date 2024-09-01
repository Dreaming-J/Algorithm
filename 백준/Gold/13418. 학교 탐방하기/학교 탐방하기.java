import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int ENTRANCE = 0, WORST = 0, BEST = 1;
    static int buildingSize, roadSize;
    static List<Building>[] buildings;
    static boolean[][] visited;
    static int[] fatigue;

    public static void findRoute(int state) {
        PriorityQueue<Building> queue = new PriorityQueue<>(
                (o1, o2) -> Integer.compare(o1.incline, o2.incline) * (state == BEST ? 1 : -1));

        queue.add(new Building(0, 0));

        int cnt = 0;
        while (!queue.isEmpty()) {
            Building cur = queue.poll();

            if (visited[state][cur.num])
                continue;
            visited[state][cur.num] = true;

            fatigue[state] += cur.incline;

            if (++cnt == buildingSize + 1)
                return;

            queue.addAll(buildings[cur.num]);
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findRoute(WORST);
        findRoute(BEST);

        System.out.println(fatigue[WORST] * fatigue[WORST] - fatigue[BEST] * fatigue[BEST]);
    }

    public static class Building {
        int num;
        int incline;

        public Building(int num, int incline) {
            this.num = num;
            this.incline = incline;
        }
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        buildingSize = Integer.parseInt(st.nextToken());
        roadSize = Integer.parseInt(st.nextToken());

        buildings = new ArrayList[buildingSize + 1];
        for (int idx = 0; idx <= buildingSize; idx++) {
            buildings[idx] = new ArrayList<>();
        }

        for (int idx = 0; idx <= roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int incline = Integer.parseInt(st.nextToken());

            buildings[from].add(new Building(to, 1 - incline));
            buildings[to].add(new Building(from, 1 - incline));
        }

        visited = new boolean[2][buildingSize + 1];
        fatigue = new int[2];
    }
}
