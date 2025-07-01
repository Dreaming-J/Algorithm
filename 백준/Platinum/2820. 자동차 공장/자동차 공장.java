import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_UPDATE = "p", CMD_QUERY = "u";

	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer tokenizer;
	static int employeeSize, commandSize;
	static Graph company;
	static SegmentTree st;

	public static class Graph {
		List<Integer>[] graph;
		int[] originSalaries, eulerSalaries;
		int[] in, out;
		int order;

		public Graph(int size) {
			this.graph = new ArrayList[size + 1];
			for (int idx = 1; idx <= size; idx++)
				this.graph[idx] = new ArrayList<>();

			this.originSalaries = new int[size + 1];
			this.eulerSalaries = new int[size + 1];
			this.in = new int[size + 1];
			this.out = new int[size + 1];
		}

		public void addNode(int from, int to) {
			this.graph[from].add(to);
		}

		public void addSalary(int idx, int salary) {
			this.originSalaries[idx] = salary;
		}

		public void eulerPathTechnique(int idx) {
			this.in[idx] = ++order;
			this.eulerSalaries[order] = this.originSalaries[idx];

			for (int next : this.graph[idx])
				eulerPathTechnique(next);

			this.out[idx] = order;
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
				return tree[node] = company.eulerSalaries[left];

			int mid = (left + right) / 2;
			return tree[node] = build(left, mid, node << 1)
				+ build(mid + 1, right, node << 1 | 1);
		}

		public int update(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
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
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node << 1, lazyValue)
				+ update(updateLeft, updateRight, value, mid + 1, right, node << 1 | 1, lazyValue);
		}

		public int query(int queryLeft, int queryRight, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			lazy[node] += lazyValue;
			tree[node] += lazyValue * rangeSize;

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

		company.eulerPathTechnique(1);

		st.build(1, employeeSize, 1);

		while (commandSize-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());

			String command = tokenizer.nextToken();

			int id = Integer.parseInt(tokenizer.nextToken());
			int left = company.in[id];
			int right = company.out[id];

			switch (command) {
				case CMD_UPDATE:
					int value = Integer.parseInt(tokenizer.nextToken());

					if (left + 1 > right)
						continue;

					st.update(left + 1, right, value, 1, employeeSize, 1, 0);
					break;
				case CMD_QUERY:
					output.append(st.query(left, left, 1, employeeSize, 1, 0))
						.append("\n");
			}
		}

		System.out.println(output);
	}

	static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		tokenizer = new StringTokenizer(input.readLine());
		employeeSize = Integer.parseInt(tokenizer.nextToken());
		commandSize = Integer.parseInt(tokenizer.nextToken());

		company = new Graph(employeeSize);

		for (int idx = 1; idx <= employeeSize; idx++) {
			tokenizer = new StringTokenizer(input.readLine());

			company.addSalary(idx, Integer.parseInt(tokenizer.nextToken()));

			if (idx != 1)
				company.addNode(Integer.parseInt(tokenizer.nextToken()), idx);
		}

		st = new SegmentTree(employeeSize);
	}
}