import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final String REVERSE = "0", QUERY = "1";

	static BufferedReader input;
	static StringTokenizer tokenizer;
	static StringBuilder output;
	static int switchSize, taskSize;
	static SegmentTree st;

	public static class SegmentTree {
		int[] sum;
		int[] lazy;

		public SegmentTree(int size) {
			sum = new int[size * 4];
			lazy = new int[size * 4];
		}

		public int updateRange(int updateLeft, int updateRight, int left, int right, int node, int lazyValue) {
			if (lazyValue > 0) {
				if (lazyValue % 2 != 0)
					sum[node] = (right - left + 1) - sum[node];

				lazy[node] += lazyValue;
			}

			if (right < updateLeft || updateRight < left)
				return sum[node];

			if (updateLeft <= left && right <= updateRight) {
				lazy[node]++;
				return sum[node] = (right - left + 1) - sum[node];
			}

			int mid = (left + right) / 2;

			lazyValue = lazy[node];
			lazy[node] = 0;

			return sum[node] = updateRange(updateLeft, updateRight, left, mid, node * 2, lazyValue)
				+ updateRange(updateLeft, updateRight, mid + 1, right, node * 2 + 1, lazyValue);
		}

		public int query(int queryLeft, int queryRight, int left, int right, int node, int lazyValue) {
			if (lazyValue > 0) {
				if (lazyValue % 2 != 0)
					sum[node] = (right - left + 1) - sum[node];

				lazy[node] += lazyValue;
			}

			if (right < queryLeft || queryRight < left)
				return 0;

			if (queryLeft <= left && right <= queryRight)
				return sum[node];

			int mid = (left + right) / 2;

			lazyValue = lazy[node];
			lazy[node] = 0;

			return query(queryLeft, queryRight, left, mid, node * 2, lazyValue)
				+ query(queryLeft, queryRight, mid + 1, right, node * 2 + 1, lazyValue);
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		for (int taskIdx = 0; taskIdx < taskSize; taskIdx++) {
			tokenizer = new StringTokenizer(input.readLine());

			String cmd = tokenizer.nextToken();
			int start = Integer.parseInt(tokenizer.nextToken());
			int end = Integer.parseInt(tokenizer.nextToken());

			switch (cmd) {
				case REVERSE:
					st.updateRange(start, end, 1, switchSize, 1, 0);
					break;
				case QUERY:
					output.append(st.query(start, end, 1, switchSize, 1, 0))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	public static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		tokenizer = new StringTokenizer(input.readLine());
		switchSize = Integer.parseInt(tokenizer.nextToken());
		taskSize = Integer.parseInt(tokenizer.nextToken());

		st = new SegmentTree(switchSize);
	}
}