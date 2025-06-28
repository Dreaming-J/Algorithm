import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_UPDATE = "1", CMD_QUERY = "2", CMD_REVERSE = "3";
	static final int DIR_FORWARD = 0, DIR_REVERSE = 1;

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
		long[][] tree;
		long[] lazy;
		int direction;

		public SegmentTree(int size) {
			this.tree = new long[2][size * 4];
			this.lazy = new long[size * 4];
		}

		public void update(int updateLeft, int updateRight, long value) {
			if (isForward())
				update(updateLeft, updateRight, value, 1, employeeSize, 1, 0, DIR_FORWARD);
			else
				update(updateLeft, updateLeft, value, 1, employeeSize, 1, 0, DIR_REVERSE);
		}

		private long update(int updateLeft, int updateRight, long value, int left, int right, int node, long lazyValue, int direction) {
			//레이지 전처리
			if (direction == DIR_FORWARD) {
				lazy[node] += lazyValue;
				tree[direction][node] += lazyValue * (right - left + 1);
			}

			//범위를 벗어날 경우
			if (right < updateLeft || updateRight < left)
				return tree[direction][node];

			//범위를 포함할 경우
			if (updateLeft <= left && right <= updateRight) {
				if (direction == DIR_FORWARD)
					lazy[node] += value;

				return tree[direction][node] += value * (right - left + 1);
			}

			//레이지 후처리
			if (direction == DIR_FORWARD) {
				lazyValue = lazy[node];
				lazy[node] = 0;
			}

			//범위가 겹치는 경우
			int mid = (left + right) / 2;
			return tree[direction][node] = update(updateLeft, updateRight, value, left, mid, node << 1, lazyValue, direction)
				+ update(updateLeft, updateRight, value, mid + 1, right, node << 1 | 1, lazyValue, direction);
		}

		public long query(int queryLeft, int queryRight, int left, int right, int node, long lazyValue, int direction) {
			//레이지 전처리
			if (direction == DIR_FORWARD) {
				lazy[node] += lazyValue;
				tree[direction][node] += lazyValue * (right - left + 1);
			}

			//범위를 벗어난 경우
			if (right < queryLeft || queryRight < left)
				return 0;

			//범위를 포함할 경우
			if (queryLeft <= left && right <= queryRight)
				return tree[direction][node];

			//레이지 후처리
			if (direction == DIR_FORWARD) {
				lazyValue = lazy[node];
				lazy[node] = 0;
			}

			//범위가 겹치는 경우
			int mid = (left + right) / 2;
			return query(queryLeft, queryRight, left, mid, node << 1, lazyValue, direction)
				+ query(queryLeft, queryRight, mid + 1, right, node << 1 | 1, lazyValue, direction);
		}

		public void reverse() {
			direction = (direction + 1) % 2;
		}

		private boolean isForward() {
			return direction % 2 == 0;
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		company.eulerPathTechnique(1);

		while (commandSize-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());

			String command = tokenizer.nextToken();
			int id;
			int left = 0;
			int right = 0;

			if (!Objects.equals(command, CMD_REVERSE)) {
				id = Integer.parseInt(tokenizer.nextToken());
				left = company.in[id];
				right = company.out[id];
			}

			switch (command) {
				case CMD_UPDATE:
					long value = Long.parseLong(tokenizer.nextToken());
					st.update(left, right, value);
					break;
				case CMD_QUERY:
					output.append(st.query(left, left, 1, employeeSize, 1, 0, DIR_FORWARD)
							+ st.query(left, right, 1, employeeSize, 1, 0, DIR_REVERSE))
						.append("\n");
					break;
				case CMD_REVERSE:
					st.reverse();
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