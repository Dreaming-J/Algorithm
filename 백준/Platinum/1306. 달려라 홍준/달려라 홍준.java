import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringJoiner output = new StringJoiner(" ");
    static StringTokenizer st;
    static int[] array = new int[1_000_001];
    static int[] rangeSum = new int[4_000_005];
    static int n, m;

    public static int init(int left, int right, int node) {
        if (left == right)
            return rangeSum[node] = array[left];

        int mid = (left + right) / 2;
        return rangeSum[node] = Math.max(init(left, mid, node * 2), init(mid + 1, right, node * 2 + 1));
    }

    public static int query(int queryLeft, int queryRight, int left, int right, int node) {
        if (queryLeft > right || queryRight < left)
            return 0;

        if (queryLeft <= left && right <= queryRight)
            return rangeSum[node];

        int mid = (left + right) / 2;
        return Math.max(query(queryLeft, queryRight, left, mid, node * 2),
                query(queryLeft, queryRight, mid + 1, right, node * 2 + 1));
    }

    public static void main(String[] args) throws IOException {
        initInput();

        init(1, n, 1);

        for (int idx = m; idx <= n - (m - 1); idx++) {
            int maxBright = query(idx - (m - 1), idx + (m - 1), 1, n, 1);
            output.add(String.valueOf(maxBright));
        }

        System.out.println(output);
    }

    public static void initInput() throws IOException {
        st = new StringTokenizer(input.readLine().trim());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(input.readLine().trim());
        for (int i = 1; i <= n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
    }
}
