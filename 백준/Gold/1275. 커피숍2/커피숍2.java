import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int NUMBER_SIZE = 100_001;
    static int[] array = new int[NUMBER_SIZE];
    static long[] rangeSum = new long[4 * NUMBER_SIZE];
    static int n, turnSize;

    public static long init(int left, int right, int node) {
        if (left == right)
            return rangeSum[node] = array[left];

        int mid = (left + right) / 2;
        return rangeSum[node] = init(left, mid, node * 2) + init(mid + 1, right, node * 2 + 1);
    }

    public static long query(int queryLeft, int queryRight, int left, int right, int node) {
        if (queryLeft > right || queryRight < left)
            return 0;

        if (queryLeft <= left && right <= queryRight)
            return rangeSum[node];

        int mid = (left + right) / 2;
        return query(queryLeft, queryRight, left, mid, node * 2) +
                query(queryLeft, queryRight, mid + 1, right, node * 2 + 1);
    }

    public static long update(int index, int value, int left, int right, int node) {
        if (index < left || index > right)
            return rangeSum[node];

        if (left == right)
            return rangeSum[node] = array[index] = value;

        int mid = (left + right) / 2;
        return rangeSum[node] =
                update(index, value, left, mid, node * 2) + update(index, value, mid + 1, right, node * 2 + 1);
    }

    public static void main(String[] args) throws IOException {
        initInput();

        init(1, n, 1);

        for (int turn = 0; turn < turnSize; turn++) {
            st = new StringTokenizer(input.readLine().trim());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            output.append(query(Math.min(x, y), Math.max(x, y), 1, n, 1));
            if (turn != turnSize - 1)
                output.append("\n");

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            update(a, b, 1, n, 1);
        }

        System.out.print(output);
    }

    public static void initInput() throws IOException {
        st = new StringTokenizer(input.readLine().trim());
        n = Integer.parseInt(st.nextToken());
        turnSize = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(input.readLine().trim());
        for (int i = 1; i <= n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
    }
}
