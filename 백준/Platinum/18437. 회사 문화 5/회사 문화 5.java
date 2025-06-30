import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static final String CMD_TURN_ON = "1", CMD_TURN_OFF = "2", CMD_QUERY = "3";

	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer tokenizer;
	static int employeeSize, commandSize;
	static Graph company;
	static SegmentTree st;

	public enum Status {
		NOTHING, TURN_ON, TURN_OFF;

		public static int toInt(Status status) {
			return TURN_ON.equals(status) ? 1 : 0;
		}
	}

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
		int[] tree;
		Status[] lazy;

		public SegmentTree(int size) {
			this.tree = new int[size * 4];
			this.lazy = new Status[size * 4];
		}

		public int update(int updateLeft, int updateRight, Status value, int left, int right, int node, Status lazyValue) {
			int rangeSize = right - left + 1;

			//레이지 전처리
			if (lazyValue != Status.NOTHING) {
				lazy[node] = lazyValue;
				tree[node] = Status.toInt(lazyValue) * rangeSize;
			}

			//범위를 벗어났을 때
			if (right < updateLeft || updateRight < left)
				return tree[node];

			//범위를 포함할 때
			if (updateLeft <= left && right <= updateRight) {
				lazy[node] = value;
				return tree[node] = Status.toInt(value) * rangeSize;
			}

			//레이지 후처리
			lazyValue = lazy[node];
			lazy[node] = Status.NOTHING;

			//범위를 걸쳤을 때
			int mid = (left + right) / 2;
			return tree[node] = update(updateLeft, updateRight, value, left, mid, node << 1, lazyValue)
				+ update(updateLeft, updateRight, value, mid + 1, right, node << 1 | 1, lazyValue);
		}

		public int query(int queryLeft, int queryRight, int left, int right, int node, Status lazyValue) {
			int rangeSize = right - left + 1;

			//레이지 전처리
			if (lazyValue != Status.NOTHING) {
				lazy[node] = lazyValue;
				tree[node] = Status.toInt(lazyValue) * rangeSize;
			}

			//범위를 벗어났을 때
			if (right < queryLeft || queryRight < left)
				return 0;

			//범위를 포함할 때
			if (queryLeft <= left && right <= queryRight)
				return tree[node];

			//레이지 후처리
			lazyValue = lazy[node];
			lazy[node] = Status.NOTHING;

			//범위를 걸쳤을 때
			int mid = (left + right) / 2;
			return query(queryLeft, queryRight, left, mid, node << 1, lazyValue)
				+ query(queryLeft, queryRight, mid + 1, right, node << 1 | 1, lazyValue);
		}
	}

	public static void main(String[] args) throws IOException {
		init();

		company.eulerPathTechnique(1);

		st.update(1, employeeSize, Status.TURN_ON, 1, employeeSize, 1, Status.NOTHING);

		while (commandSize-- > 0) {
			tokenizer = new StringTokenizer(input.readLine());

			String command = tokenizer.nextToken();
			int id = Integer.parseInt(tokenizer.nextToken());

			int left = company.in[id] + 1;
			int right = company.out[id];

			if (left > right) {
				if (CMD_QUERY.equals(command))
					output.append(0)
						.append("\n");
				continue;
			}

			switch (command) {
				case CMD_TURN_ON:
					st.update(left, right, Status.TURN_ON, 1, employeeSize, 1, Status.NOTHING);
					break;
				case CMD_TURN_OFF:
					st.update(left, right, Status.TURN_OFF, 1, employeeSize, 1, Status.NOTHING);
					break;
				case CMD_QUERY:
					output.append(st.query(left, right, 1, employeeSize, 1, Status.NOTHING))
						.append("\n");
					break;
			}
		}

		System.out.println(output);
	}

	public static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		employeeSize = Integer.parseInt(input.readLine());

		company = new Graph(employeeSize + 1);

		tokenizer = new StringTokenizer(input.readLine());
		tokenizer.nextToken();
		for (int id = 2; id <= employeeSize; id++)
			company.add(Integer.parseInt(tokenizer.nextToken()), id);

		commandSize = Integer.parseInt(input.readLine());

		st = new SegmentTree(employeeSize + 1);
	}
}