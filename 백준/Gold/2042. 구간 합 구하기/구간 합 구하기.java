import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringJoiner output = new StringJoiner("\n");
    static StringTokenizer st;
    static long[] array = new long[1_000_050];
    static long[] rangeSum = new long[4_000_050];
    static int n, m, k;
    static long[][] command;

    public static long initST(int left, int right, int node) {
        if (left == right) {
            rangeSum[node] = array[left];
            return rangeSum[node];
        }

        int mid = (left + right) / 2;
        rangeSum[node] = initST(left, mid, node * 2) + initST(mid + 1, right, node * 2 + 1);
        return rangeSum[node];
    }

    public static long update(int index, long value, int left, int right, int node) {
        if (index < left || index > right)
            return rangeSum[node];

        if (left == right) {
            array[index] = value;
            rangeSum[node] = value;
            return value;
        }

        int mid = (left + right) / 2;
        rangeSum[node] =
                update(index, value, left, mid, node * 2) + update(index, value, mid + 1, right, node * 2 + 1);
        return rangeSum[node];
    }

    public static long query(int start, int end, int left, int right, int node) {
        if (right < start || end < left)
            return 0;

        if (start <= left && right <= end)
            return rangeSum[node];

        int mid = (left + right) / 2;
        return query(start, end, left, mid, node * 2) + query(start, end, mid + 1, right,
                        node * 2 + 1);
    }

    public static void main(String[] args) throws IOException {
        init();

        initST(1, n, 1);

        for (int i = 0; i < m + k; i++) {
            if (command[i][0] == 1) {
                update((int) command[i][1], command[i][2], 1, n, 1);
            }
            if (command[i][0] == 2) {
                output.add(String.valueOf(query((int) command[i][1], (int) command[i][2], 1, n, 1)));
            }
        }

        System.out.println(output);
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine().trim());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            array[i] = Long.parseLong(input.readLine().trim());
        }

        command = new long[m + k][3];
        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int j = 0; j < 3; j++) {
                command[i][j] = Long.parseLong(st.nextToken());
            }
        }
    }
}
