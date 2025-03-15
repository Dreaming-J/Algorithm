import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final String CMD_ADD = "1", CMD_QUERY = "2";

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st = new StringTokenizer("");
	static int totalDays, totalQueries;
	static long[] tree;

	public static void main(String[] args) throws IOException {
		init();

		for (int queryIdx = 0; queryIdx < totalQueries; queryIdx++) {
			st = new StringTokenizer(input.readLine());

			String cmd = st.nextToken();

			if (CMD_ADD.equals(cmd)) {
				int day = Integer.parseInt(st.nextToken());
				int amount = Integer.parseInt(st.nextToken());

				update(day, amount);
			}
			else if (CMD_QUERY.equals(cmd)) {
				int start = Integer.parseInt(st.nextToken());
				int end = Integer.parseInt(st.nextToken());
				long result = query(end) - query(start - 1);

				output.append(result)
					.append("\n");
			}
		}

		System.out.println(output);
	}

	private static void update(int idx, int value) {
		for (; idx < tree.length; idx += idx & -idx)
			tree[idx] += value;
	}

	private static long query(int idx) {
		long sum = 0;

		for (; idx > 0; idx -= idx & -idx)
			sum += tree[idx];

		return sum;
	}

	private static void init() throws IOException {
		st = new StringTokenizer(input.readLine());
		totalDays = Integer.parseInt(st.nextToken());
		totalQueries = Integer.parseInt(st.nextToken());

		tree = new long[totalDays + 1];
	}
}