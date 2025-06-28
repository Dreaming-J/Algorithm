import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_UPDATE = "1", CMD_QUERY = "2", CMD_REVERSE = "3";

	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer tokenizer;
	static int employeeSize, commandSize;
	static Graph company;
	static SegmentTree st;

	public static class Graph {
		List<Integer>[] graph;
		int[] in, out;

		public Graph(int size) {
			graph = new ArrayList[size + 1];
			for (int idx = 1; idx <= size; idx++)
				graph[idx] = new ArrayList<>();

			in = new int[size + 1];
			out = new int[size + 1];
		}

		public void add(int from, int to) {
			graph[from].add(to);
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
		long[] tree;

		public SegmentTree(int size) {
			this.tree = new long[size * 4];
		}

		public long update(int index, long value, int left, int right, int node) {
			//범위를 벗어날 경우
			if (right < index || index < left)
				return tree[node];

			//범위를 포함할 경우
			if (left == right)
				return tree[node] += value;

			//범위가 겹치는 경우
			int mid = (left + right) / 2;
			return tree[node] = update(index, value, left, mid, node << 1)
				+ update(index, value, mid + 1, right, node << 1 | 1);
		}

		public long query(int queryLeft, int queryRight, int left, int right, int node) {
			//범위를 벗어난 경우
			if (right < queryLeft || queryRight < left)
				return 0;

			//범위를 포함할 경우
			if (queryLeft <= left && right <= queryRight)
				return tree[node];

			//범위가 겹치는 경우
			int mid = (left + right) / 2;
			return query(queryLeft, queryRight, left, mid, node << 1)
				+ query(queryLeft, queryRight, mid + 1, right, node << 1 | 1);
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
					long value = Long.parseLong(tokenizer.nextToken());
					st.update(left, value, 1, employeeSize, 1);
					break;
				case CMD_QUERY:
					output.append(st.query(left, right, 1, employeeSize, 1))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	public static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		tokenizer = new StringTokenizer(input.readLine());
		employeeSize = Integer.parseInt(tokenizer.nextToken());
		commandSize = Integer.parseInt(tokenizer.nextToken());

		company = new Graph(employeeSize + 1);

		tokenizer = new StringTokenizer(input.readLine());
		tokenizer.nextToken();
		for (int id = 2; id <= employeeSize; id++)
			company.add(Integer.parseInt(tokenizer.nextToken()), id);

		st = new SegmentTree(employeeSize + 1);
	}
}