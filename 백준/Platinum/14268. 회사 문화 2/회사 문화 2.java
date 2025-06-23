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
	static int employeeSize, commandSize;
	static Graph company;
	static SegmentTree st;

	public static class Graph {
		List<Integer>[] graph;
		int[] in, out;

		public Graph(int size) {
			this.graph = new ArrayList[size + 1];
			for (int idx = 1; idx <= size; idx++)
				this.graph[idx] = new ArrayList<>();

			this.in = new int[size + 1];
			this.out = new int[size + 1];
		}

		int order;

		public void eulerPathTechnique(int id) {
			in[id] = ++order;

			for (int nextId : this.graph[id])
				eulerPathTechnique(nextId);

			out[id] = order;
		}
	}

	public static class SegmentTree {
		int[] array;
		int[] tree;
		int[] lazy;

		public SegmentTree(int size) {
			this.array = new int[size + 1];
			this.tree = new int[size * 4];
			this.lazy = new int[size * 4];
		}

		public int update(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			//레이지 전처리
			propagation(node, lazyValue, rangeSize);

			//범위를 벗어나면
			if (right < updateLeft || updateRight < left)
				return tree[node];

			//범위를 포함하면
			if (updateLeft <= left && right <= updateRight) {
				propagation(node, value, rangeSize);
				return tree[node];
			}

			//레이지 후처리
			lazyValue = lazy[node];
			lazy[node] = 0;

			//범위를 걸치면
			int mid = (left + right) / 2;
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node * 2, lazyValue)
				+ update(updateLeft, updateRight, value, mid + 1, right, node * 2 + 1, lazyValue);
		}

		public int query(int index, int left, int right, int node, int lazyValue) {
			int rangeSize = right - left + 1;

			//레이지 전처리
			propagation(node, lazyValue, rangeSize);

			//범위를 벗어나면
			if (right < index || index < left)
				return 0;

			//범위를 포함하면
			if (left == right)
				return tree[node];

			//레이지 후처리
			lazyValue = lazy[node];
			lazy[node] = 0;

			//범위를 걸치면
			int mid = (left + right) / 2;
			return query(index, left, mid, node * 2, lazyValue)
				+ query(index, mid + 1, right, node * 2 + 1, lazyValue);
		}

		private void propagation(int node, int value, int size) {
			lazy[node] += value;
			tree[node] += value * size;
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		company.eulerPathTechnique(1);

		while (commandSize-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());
			String command = tokenizer.nextToken();
			int employeeId = Integer.parseInt(tokenizer.nextToken());

			int left = company.in[employeeId];
			int right = company.out[employeeId];

			switch (command) {
				case CMD_UPDATE:
					int amount = Integer.parseInt(tokenizer.nextToken());
					st.update(left, right, amount, 1, employeeSize, 1, 0);
					break;
				case CMD_QUERY:
					output.append(st.query(left, 1, employeeSize, 1, 0))
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

		company = new Graph(employeeSize);

		tokenizer = new StringTokenizer(input.readLine());
		tokenizer.nextToken();
		for (int employeeId = 2; employeeId <= employeeSize; employeeId++) {
			int supervisorId = Integer.parseInt(tokenizer.nextToken());
			company.graph[supervisorId].add(employeeId);
		}

		st = new SegmentTree(employeeSize);
	}
}