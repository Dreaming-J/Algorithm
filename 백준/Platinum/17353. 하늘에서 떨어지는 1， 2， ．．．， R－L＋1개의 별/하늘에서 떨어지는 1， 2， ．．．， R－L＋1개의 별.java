import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_UPDATE = "1", CMD_QUERY = "2";

	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer tokenizer;
	static int starSize, commandSize;
	static int[] starCount;
	static SegmentTree st;

	public static class SegmentTree {
		long[] tree, lazy;

		public SegmentTree(int size) {
			this.tree = new long[size * 4];
			this.lazy = new long[size * 4];
		}

		public long update(int updateLeft, int updateRight, long value, int left, int right, int node, long lazyValue) {
			int rangeSize = right - left + 1;

			lazy[node] += lazyValue;
			tree[node] += rangeSize * lazyValue;

			if (right < updateLeft || updateRight < left)
				return tree[node];

			if (updateLeft <= left && right <= updateRight) {
				lazy[node] += value;
				return tree[node] += rangeSize * value;
			}

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node << 1, lazyValue)
				+ update(updateLeft, updateRight, value, mid + 1, right, node << 1 | 1, lazyValue);
		}

		public long query(int queryLeft, int queryRight, int left, int right, int node, long lazyValue) {
			int rangeSize = right - left + 1;

			lazy[node] += lazyValue;
			tree[node] += rangeSize * lazyValue;

			if (right < queryLeft || queryRight < left)
				return 0;

			if (queryLeft <= left && right <= queryRight)
				return tree[node];

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return query(queryLeft, queryRight, left, mid, node << 1, lazyValue)
				+ query(queryLeft, queryRight, mid + 1, right, node << 1 | 1, lazyValue);
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		while (commandSize-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());

			String command = tokenizer.nextToken();

			switch (command) {
				case CMD_UPDATE:
					int left = Integer.parseInt(tokenizer.nextToken());
					int right = Integer.parseInt(tokenizer.nextToken());
					st.update(left, right, 1, 1, starSize, 1, 0);
					st.update(right + 1, right + 1, -(right - left + 1), 1, starSize, 1, 0);
					break;
				case CMD_QUERY:
					int idx = Integer.parseInt(tokenizer.nextToken());
					output.append(starCount[idx] + st.query(1, idx, 1, starSize, 1, 0))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		starSize = Integer.parseInt(input.readLine());

		starCount = new int[starSize + 1];

		tokenizer = new StringTokenizer(input.readLine());
		for (int idx = 1; idx <= starSize; idx++)
			starCount[idx] = Integer.parseInt(tokenizer.nextToken());

		commandSize = Integer.parseInt(input.readLine());

		st = new SegmentTree(starSize);
	}
}