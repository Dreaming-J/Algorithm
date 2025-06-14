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
		int[] array, tree, lazy;

		public SegmentTree(int size, int[] array) {
			this.array = array;
			this.tree = new int[size * 4];
			this.lazy = new int[size * 4];

			build(0, size - 1, 1);
		}

		public int build(int left, int right, int node) {
			if (left == right)
				return tree[node] = array[left];

			int mid = (left + right) / 2;
			return tree[node] = build(left, mid, node * 2)
				^ build(mid + 1, right, node * 2 + 1);
		}

		public int update(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			if (lazyValue != 0) {
				lazy[node] ^= lazyValue;

				if (isOdd(rangeSize))
					tree[node] ^= lazyValue;
			}

			if (right < updateLeft || updateRight < left)
				return 0;

			if (updateLeft <= left && right <= updateRight) {
				lazy[node] ^= value;

				return isOdd(rangeSize) ? tree[node] ^= value : tree[node];
			}

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node * 2, lazyValue)
				^ update(updateLeft, updateRight, value, mid + 1, right, node * 2 + 1, lazyValue);
		}

		public int query(int index, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			if (lazyValue != 0) {
				lazy[node] ^= lazyValue;

				if (isOdd(rangeSize))
					tree[node] ^= lazyValue;
			}

			if (right < index || index < left)
				return 0;

			if (left == right)
				return tree[node];

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return query(index, left, mid, node * 2, lazyValue)
				^ query(index, mid + 1, right, node * 2 + 1, lazyValue);
		}

		public boolean isOdd(int number) {
			return number % 2 != 0;
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
					int value = Integer.parseInt(tokenizer.nextToken());

					st.update(left, right, value, 0, arraySize - 1, 1, 0);

					break;
				case CMD_QUERY:
					int index = Integer.parseInt(tokenizer.nextToken());

					output.append(st.query(index, 0, arraySize - 1, 1, 0))
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

		cmdCount = Integer.parseInt(input.readLine());
	}
}