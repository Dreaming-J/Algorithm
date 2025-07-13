import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_QUERY = "1", CMD_UPDATE = "2";

	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer tokenizer;
	static int vertexSize, commandSize;
	static Graph graph;
	static SegmentTree st;

	public static class Graph {
		List<Integer>[] graph;
		int[] originWeight, eulerWeight;
		int[] in, out;
		boolean[] visited;
		int order;

		public Graph(int size) {
			graph = new ArrayList[size + 1];

			for (int idx = 1; idx <= size; idx++)
				graph[idx] = new ArrayList<>();

			originWeight = new int[size + 1];
			eulerWeight = new int[size + 1];

			in = new int[size + 1];
			out = new int[size + 1];
			visited = new boolean[size + 1];
		}

		public void add(int from, int to) {
			graph[from].add(to);
			graph[to].add(from);
		}

		public void eulerPathTechnique(int idx) {
			in[idx] = ++order;
			eulerWeight[order] = originWeight[idx];

			visited[idx] = true;

			for (int next : graph[idx]) {
				if (visited[next])
					continue;

				eulerPathTechnique(next);
			}

			out[idx] = order;
		}
	}

	public static class SegmentTree {
		int[] tree, lazy;

		public SegmentTree(int size) {
			this.tree = new int[size * 4];
			this.lazy = new int[size * 4];
		}

		public int build(int left, int right, int node) {
			if (left == right)
				return tree[node] = graph.eulerWeight[left];

			int mid = (left + right) / 2;
			return tree[node] = build(left, mid, node << 1)
				^ build(mid + 1, right, node << 1 | 1);
		}

		public int update(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			lazy[node] ^= lazyValue;
			tree[node] ^= isOdd(rangeSize) ? lazyValue : 0;

			if (right < updateLeft || updateRight < left)
				return tree[node];

			if (updateLeft <= left && right <= updateRight) {
				lazy[node] ^= value;
				return tree[node] ^= isOdd(rangeSize) ? value : 0;
			}

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node << 1, lazyValue)
				^ update(updateLeft, updateRight, value, mid + 1, right, node << 1 | 1, lazyValue);
		}

		public int query(int queryLeft, int queryRight, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			lazy[node] ^= lazyValue;
			tree[node] ^= isOdd(rangeSize) ? lazyValue : 0;

			if (right < queryLeft || queryRight < left)
				return 0;

			if (queryLeft <= left && right <= queryRight)
				return tree[node];

			lazyValue = lazy[node];
			lazy[node] = 0;

			int mid = (left + right) / 2;
			return query(queryLeft, queryRight, left, mid, node << 1, lazyValue)
				^ query(queryLeft, queryRight, mid + 1, right, node << 1 | 1, lazyValue);
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		graph.eulerPathTechnique(1);

		st.build(1, vertexSize, 1);

		while (commandSize-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());

			String command = tokenizer.nextToken();
			int idx = Integer.parseInt(tokenizer.nextToken());

			int left = graph.in[idx];
			int right = graph.out[idx];

			switch (command) {
				case CMD_UPDATE:
					int value = Integer.parseInt(tokenizer.nextToken());
					st.update(left, right, value, 1, vertexSize, 1, 0);
					break;
				case CMD_QUERY:
					output.append(st.query(left, right, 1, vertexSize, 1, 0))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	static boolean isOdd(int number) {
		return number % 2 != 0;
	}

	static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		tokenizer = new StringTokenizer(input.readLine());
		vertexSize = Integer.parseInt(tokenizer.nextToken());
		commandSize = Integer.parseInt(tokenizer.nextToken());

		graph = new Graph(vertexSize);
		for (int idx = 0; idx < vertexSize - 1; idx++) {
			tokenizer = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokenizer.nextToken());
			int to = Integer.parseInt(tokenizer.nextToken());

			graph.add(from, to);
		}

		tokenizer = new StringTokenizer(input.readLine());
		for (int idx = 1; idx <= vertexSize; idx++)
			graph.originWeight[idx] = Integer.parseInt(tokenizer.nextToken());

		st = new SegmentTree(vertexSize);
	}
}