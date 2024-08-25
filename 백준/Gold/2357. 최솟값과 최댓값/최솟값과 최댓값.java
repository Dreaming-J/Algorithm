import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MIN = 0, MAX = 1;
    static int numberSize, intervalSize;
    static long[] array;
    static long[][] sum;
    static int[][] intervals;

    public static long init(int left, int right, int node, int sumIdx) {
        if (left == right) {
            return sum[sumIdx][node] = array[left];
        }

        int mid = (left + right) / 2;
        long leftInit = init(left, mid, node * 2, sumIdx);
        long rightInit = init(mid + 1, right, node * 2 + 1, sumIdx);

        if (sumIdx == MAX)
            return sum[sumIdx][node] = Math.max(leftInit, rightInit);
        return sum[sumIdx][node] = Math.min(leftInit, rightInit);
    }

    public static long query(int queryLeft, int queryRight, int left, int right, int node, int sumIdx) {
        if (queryLeft > right || queryRight < left)
            return sumIdx == MAX ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (queryLeft <= left && right <= queryRight)
            return sum[sumIdx][node];

        int mid = (left + right) / 2;
        long leftQuery = query(queryLeft, queryRight, left, mid, node * 2, sumIdx);
        long rightQuery = query(queryLeft, queryRight, mid + 1, right, node * 2 + 1, sumIdx);

        if (sumIdx == MAX)
            return Math.max(leftQuery, rightQuery);
        return Math.min(leftQuery, rightQuery);
    }

    public static void main(String[] args) throws IOException {
        initInput();

        init(1, numberSize, 1, MIN);
        init(1, numberSize, 1, MAX);

        for (int idx = 0; idx < intervalSize; idx++) {
            output.append(query(intervals[idx][0], intervals[idx][1], 1, numberSize, 1, MIN)).append(" ");
            output.append(query(intervals[idx][0], intervals[idx][1], 1, numberSize, 1, MAX)).append("\n");
        }

        System.out.println(output);
    }

    public static void initInput() throws IOException {
        st = new StringTokenizer(input.readLine());
        numberSize = Integer.parseInt(st.nextToken());
        intervalSize = Integer.parseInt(st.nextToken());

        array = new long[numberSize + 1];
        for (int idx = 1; idx <= numberSize; idx++) {
            array[idx] = Integer.parseInt(input.readLine());
        }

        sum = new long[2][4 * (numberSize + 1)];

        intervals = new int[intervalSize][2];
        for (int intervalIdx = 0; intervalIdx < intervalSize; intervalIdx++) {
            st = new StringTokenizer(input.readLine());
            for (int idx = 0; idx < 2; idx++) {
                intervals[intervalIdx][idx] = Integer.parseInt(st.nextToken());
            }
        }
    }
}