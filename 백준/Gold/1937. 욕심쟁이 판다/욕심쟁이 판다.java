import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int forestSize;
	static int[][] forest;
	static int[][] memo;
	static int maxMoveCount;

	public static void main(String[] args) throws IOException {
		init();

		for (int row = 0; row < forestSize; row++) {
			for (int col = 0; col < forestSize; col++) {
				maxMoveCount = Math.max(findRoutes(row, col, forest[row][col]), maxMoveCount);
			}
		}

		System.out.println(maxMoveCount);
	}

	static int findRoutes(int row, int col, int bambooCount) {
		if (memo[row][col] != -1) {
			return memo[row][col];
		}

		memo[row][col] = 1;

		for (int[] delta : DELTAS) {
			int nr = row + delta[0];
			int nc = col + delta[1];

			if (isOut(nr, nc))
				continue;

			if (forest[nr][nc] <= bambooCount)
				continue;

			memo[row][col] = Math.max(findRoutes(nr, nc, forest[nr][nc]) + 1, memo[row][col]);
		}

		return memo[row][col];
	}

	static void init() throws IOException {
		st = new StringTokenizer(input.readLine().trim());
		forestSize = Integer.parseInt(st.nextToken());

		forest = new int[forestSize][forestSize];
		for (int row = 0; row < forestSize; row++) {
			st = new StringTokenizer(input.readLine().trim());
			for (int col = 0; col < forestSize; col++) {
				forest[row][col] = Integer.parseInt(st.nextToken());
			}
		}

		memo = new int[forestSize][forestSize];
		for (int[] m : memo)
			Arrays.fill(m, -1);
	}

	static boolean isOut(int row, int col) {
		return row < 0 || row >= forestSize || col < 0 || col >= forestSize;
	}
}