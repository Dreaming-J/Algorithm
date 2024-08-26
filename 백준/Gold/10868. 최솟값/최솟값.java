import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int[] array;
    static long[] sum;
    static int arraySize, commandSize;

    public static long init(int left, int right, int node) {
        if (left == right)
            return sum[node] = array[left];

        int mid = (left + right) / 2;
        return sum[node] = Math.min(init(left, mid, node * 2), init(mid + 1, right, node * 2 + 1));
    }

    public static long query(int queryLeft, int queryRight, int left, int right, int node) {
        if (queryLeft > right || queryRight < left)
            return Long.MAX_VALUE;

        if (queryLeft <= left && right <= queryRight)
            return sum[node];

        int mid = (left + right) / 2;
        return Math.min(query(queryLeft, queryRight, left, mid, node * 2),
                query(queryLeft, queryRight, mid + 1, right, node * 2 + 1));
    }

    public static void main(String[] args) throws IOException {
        initInput();

        init(1, arraySize, 1);

        for (int idx = 0; idx < commandSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int queryLeft = Integer.parseInt(st.nextToken());
            int queryRight = Integer.parseInt(st.nextToken());

            output.append(query(queryLeft, queryRight, 1, arraySize, 1)).append("\n");
        }

        System.out.println(output);
    }

    public static void initInput() throws IOException {
        st = new StringTokenizer(input.readLine());
        arraySize = Integer.parseInt(st.nextToken());
        commandSize = Integer.parseInt(st.nextToken());

        array = new int[arraySize + 1];
        for (int idx = 1; idx <= arraySize; idx++) {
            array[idx] = Integer.parseInt(input.readLine());
        }

        sum = new long[4 * (arraySize + 1)];
    }
}
