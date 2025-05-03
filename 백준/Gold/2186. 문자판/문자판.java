import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int rowCount, colCount, moveCount;
	static char[][] grid;
	static char[] word;
	static int[][][] memo;
	static int answer;

	public static void main(String[] args) throws IOException {
		init();

		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				if (grid[row][col] != word[0])
					continue;

				answer += findRoute(row, col, 0);
			}
		}

		System.out.println(answer);
	}

	static int findRoute(int row, int col, int wordIdx) {
		if (wordIdx == word.length - 1)
			return 1;

		if (memo[row][col][wordIdx] != -1)
			return memo[row][col][wordIdx];

		int route = 0;

		for (int[] delta : DELTAS) {
			for (int move = 1; move <= moveCount; move++) {
				int nr = row + delta[0] * move;
				int nc = col + delta[1] * move;

				if (isOut(nr, nc))
					continue;

				if (grid[nr][nc] != word[wordIdx + 1])
					continue;

				route += findRoute(nr, nc, wordIdx + 1);	
			}
		}

		return memo[row][col][wordIdx] = route;
	}

	static boolean isOut(int row, int col) {
		return row < 0 || row >= rowCount || col < 0 || col >= colCount;
	}

	static void init() throws IOException {
		st = new StringTokenizer(input.readLine());
		rowCount = Integer.parseInt(st.nextToken());
		colCount = Integer.parseInt(st.nextToken());
		moveCount = Integer.parseInt(st.nextToken());

		grid = new char[rowCount][colCount];

		for (int row = 0; row < rowCount; row++) {
			String line = input.readLine();
			for (int col = 0; col < colCount; col++) {
				grid[row][col] = line.charAt(col);
			}
		}

		word = input.readLine().toCharArray();

		memo = new int[rowCount][colCount][word.length];
		for (int[][] grid : memo) {
			for (int[] row : grid)
				Arrays.fill(row, -1);
		}
	}
}