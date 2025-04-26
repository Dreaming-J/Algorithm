import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int rowSize, colSize;
	static int[][] map;
	static long[][] memo;
	static boolean[][] visited;

	public static void main(String[] args) throws IOException {
		init();

		System.out.println(findRoutes(0, 0, map[0][0]));
	}

	static long findRoutes(int row, int col, int height) {
		if (row == rowSize - 1 && col == colSize - 1) {
			return 1L;
		}

		if (memo[row][col] != -1) {
			return memo[row][col];
		}

		memo[row][col] = 0;

		for (int[] delta : DELTAS) {
			int nr = row + delta[0];
			int nc = col + delta[1];

			if (isOut(nr, nc))
				continue;

			if (visited[nr][nc])
				continue;

			if (map[nr][nc] >= height)
				continue;

			visited[nr][nc] = true;
			memo[row][col] += findRoutes(nr, nc, map[nr][nc]);
			visited[nr][nc] = false;
		}

		return memo[row][col];
	}

	static void init() throws IOException {
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());

		map = new int[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			st = new StringTokenizer(input.readLine());
			for (int col = 0; col < colSize; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}

		memo = new long[rowSize][colSize];
		for (long[] row : memo)
			Arrays.fill(row, -1L);

		visited = new boolean[rowSize][colSize];
		visited[0][0] = true;
	}

	static boolean isOut(int row, int col) {
		return row < 0 || row >= rowSize || col < 0 || col >= colSize;
	}
}