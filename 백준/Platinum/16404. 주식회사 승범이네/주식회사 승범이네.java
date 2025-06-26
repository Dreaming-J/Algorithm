import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_UPDATE = "1", CMD_QUERY = "2";

	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer tokenizer;
	static int salesManSize, commandSize;
	static Graph company;
	static SegmentTree st;

	public static class Graph {
		List<Integer>[] graph;
		int[] in, out;

		public Graph(int size) {
			this.graph = new ArrayList[size + 1];
			for (int idx = 1; idx <= size; idx++)
				graph[idx] = new ArrayList<>();

			this.in = new int[size + 1];
			this.out = new int[size + 1];
		}

		public void add(int from, int to) {
			this.graph[from].add(to);
		}

		int order;

		public void eulerPathTechnique(int curId) {
			in[curId] = ++order;

			for (int nextId : graph[curId])
				eulerPathTechnique(nextId);

			out[curId] = order;
		}
	}

	public static class SegmentTree {
		int[] tree, lazy;

		public SegmentTree(int size) {
			this.tree = new int[size * 4];
			this.lazy = new int[size * 4];
		}

		public int update(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			//레이지 전처리
			lazy[node] += lazyValue;
			tree[node] += lazyValue * rangeSize;

			//범위를 벗어나면
			if (right < updateLeft || updateRight < left)
				return tree[node];

			//범위를 포함하면
			if (updateLeft <= left && right <= updateRight) {
				lazy[node] += value;
				return tree[node] += value * rangeSize;
			}

			//레이지 후처리
			lazyValue = lazy[node];
			lazy[node] = 0;

			//범위를 걸치면
			int mid = (left + right) / 2;
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node * 2, lazyValue)
				+ update(updateLeft, updateRight, value, mid + 1, right, node * 2 + 1, lazyValue);
		}

		public int query(int id, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			//레이지 전처리
			lazy[node] += lazyValue;
			tree[node] += lazyValue * rangeSize;

			//범위를 벗어나면
			if (right < id || id < left)
				return 0;

			//범위를 포함하면
			if (left == right)
				return tree[node];

			//레이지 후처리
			lazyValue = lazy[node];
			lazy[node] = 0;

			//범위를 걸치면
			int mid = (left + right) / 2;
			return query(id, left, mid, node * 2, lazyValue)
				+ query(id, mid + 1, right, node * 2 + 1, lazyValue);
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		company.eulerPathTechnique(1);

		while (commandSize-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());

			String command = tokenizer.nextToken();
			int id = Integer.parseInt(tokenizer.nextToken());
			int left = company.in[id];
			int right = company.out[id];

			switch (command) {
				case CMD_UPDATE:
					int amount = Integer.parseInt(tokenizer.nextToken());
					st.update(left, right, amount, 1, salesManSize, 1, 0);
					break;
				case CMD_QUERY:
					output.append(st.query(left, 1, salesManSize, 1, 0))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	public static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		tokenizer = new StringTokenizer(input.readLine());
		salesManSize = Integer.parseInt(tokenizer.nextToken());
		commandSize = Integer.parseInt(tokenizer.nextToken());

		company = new Graph(salesManSize);

		tokenizer = new StringTokenizer(input.readLine());
		tokenizer.nextToken();
		for (int to = 2; to <= salesManSize; to++) {
			int from = Integer.parseInt(tokenizer.nextToken());
			company.add(from, to);
		}

		st = new SegmentTree(salesManSize + 1);
	}
}