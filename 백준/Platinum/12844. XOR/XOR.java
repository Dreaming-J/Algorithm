import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final String XOR = "1", QUERY = "2";

	static BufferedReader input;
	static StringTokenizer tokenizer;
	static StringBuilder output;
	static int arraySize, querySize;
	static SegmentTree st;

	public static class SegmentTree {
		int[] array;
		int[] tree;
		int[] lazy;

		public SegmentTree(int size, int[] array) {
			this.array = array;
			this.tree = new int[size * 4];
			this.lazy = new int[size * 4];

			build(0, size - 1, 1);
		}

		public int build(int left, int right, int node) {
			if (left == right) {
				return tree[node] = array[left];
			}

			int mid = (left + right) / 2;
			return tree[node] = build(left, mid, node * 2)
				^ build(mid + 1, right, node * 2 + 1);
		}

		public int updateRange(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			if (lazyValue != 0) {
				if (rangeSize % 2 != 0)
					tree[node] ^= lazyValue;

				lazy[node] ^= lazyValue;
			}

			if (right < updateLeft || updateRight < left)
				return tree[node];

			if (updateLeft <= left && right <= updateRight) {
				lazy[node] ^= value;

				return rangeSize % 2 != 0 ? tree[node] ^= value : tree[node];
			}

			int mid = (left + right) / 2;

			lazyValue = lazy[node];
			lazy[node] = 0;

			return tree[node] = updateRange(updateLeft, updateRight, value, left, mid, node * 2, lazyValue)
				^ updateRange(updateLeft, updateRight, value, mid + 1, right, node * 2 + 1, lazyValue);
		}

		public int query(int queryLeft, int queryRight, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			if (lazyValue != 0) {
				if (rangeSize % 2 != 0)
					tree[node] ^= lazyValue;

				lazy[node] ^= lazyValue;
			}

			if (right < queryLeft || queryRight < left)
				return 0;

			if (queryLeft <= left && right <= queryRight)
				return tree[node];

			int mid = (left + right) / 2;

			lazyValue = lazy[node];
			lazy[node] = 0;

			return query(queryLeft, queryRight, left, mid, node * 2, lazyValue)
				^ query(queryLeft, queryRight, mid + 1, right, node * 2 + 1, lazyValue);
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		for (int queryIdx = 0; queryIdx < querySize; queryIdx++) {
			tokenizer = new StringTokenizer(input.readLine());

			String cmd = tokenizer.nextToken();
			int start = Integer.parseInt(tokenizer.nextToken());
			int end = Integer.parseInt(tokenizer.nextToken());

			switch (cmd) {
				case XOR:
					int value = Integer.parseInt(tokenizer.nextToken());
					st.updateRange(start, end, value, 0, arraySize - 1, 1, 0);
					break;

				case QUERY:
					output.append(st.query(start, end, 0, arraySize - 1, 1, 0))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	public static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		arraySize = Integer.parseInt(input.readLine());
		int[] array = new int[arraySize];

		tokenizer = new StringTokenizer(input.readLine());
		for (int idx = 0; idx < arraySize; idx++)
			array[idx] = Integer.parseInt(tokenizer.nextToken());

		st = new SegmentTree(arraySize, array);

		querySize = Integer.parseInt(input.readLine());
	}
}