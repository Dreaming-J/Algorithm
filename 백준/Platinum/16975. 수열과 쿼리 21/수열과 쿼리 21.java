import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_UPDATE = "1", CMD_QUERY = "2";

	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer tokenizer;
	static int arraySize, cmdCount;
	static SegmentTree st;

	public static class SegmentTree {
		long[] array, tree, lazy;

		public SegmentTree(int size, long[] array) {
			this.array = array;
			this.tree = new long[size * 4];
			this.lazy = new long[size * 4];

			build(1, size, 1);
		}

		public long build(int left, int right, int node) {
			if (left == right)
				return tree[node] = array[left];

			int mid = (left + right) / 2;
			return tree[node] = build(left, mid, node * 2)
				+ build(mid + 1, right, node * 2 + 1);
		}

		public long update(int updateLeft, int updateRight, long value, int left, int right, int node, long lazyValue) {
			int rangeSize = right - left + 1;

			lazy[node] += lazyValue;
			tree[node] += lazyValue * rangeSize;

			if (right < updateLeft || updateRight < left)
				return tree[node];

			if (updateLeft <= left && right <= updateRight) {
				lazy[node] += value;
				return tree[node] += value * rangeSize;
			}

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node * 2, lazyValue)
				+ update(updateLeft, updateRight, value, mid + 1, right, node * 2 + 1, lazyValue);
		}

		public long query(int index, int left, int right, int node, long lazyValue) {
			int rangeSize = right - left + 1;

			lazy[node] += lazyValue;
			tree[node] += lazyValue * rangeSize;

			if (right < index || index < left)
				return 0L;

			if (left == right)
				return tree[node];

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return query(index, left, mid, node * 2, lazyValue)
				+ query(index, mid + 1, right, node * 2 + 1, lazyValue);
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		while (cmdCount-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());

			String cmd = tokenizer.nextToken();

			switch (cmd) {
				case CMD_UPDATE:
					int left = Integer.parseInt(tokenizer.nextToken());
					int right = Integer.parseInt(tokenizer.nextToken());
					long value = Long.parseLong(tokenizer.nextToken());

					st.update(left, right, value, 1, arraySize, 1, 0);
					break;
				case CMD_QUERY:
					int index = Integer.parseInt(tokenizer.nextToken());

					output.append(st.query(index, 1, arraySize, 1, 0))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	public static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		arraySize = Integer.parseInt(input.readLine());
		long[] array = new long[arraySize + 1];

		tokenizer = new StringTokenizer(input.readLine());
		for (int idx = 1; idx <= arraySize; idx++)
			array[idx] = Long.parseLong(tokenizer.nextToken());

		cmdCount = Integer.parseInt(input.readLine());

		st = new SegmentTree(arraySize, array);
	}
}