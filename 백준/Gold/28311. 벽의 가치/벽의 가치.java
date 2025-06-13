import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static final char WALL = 'W';
	static final int NOTHING = 0;

	static BufferedReader input;
	static StringTokenizer tokenizer;
	static StringBuilder output;

	static int testCases;
	static int rowSize, colSize, meepleSize, wallSize;
	static Point target;
	static int[][] board;
	static List<Point> meeplePoints, wallPoints;
	static int[][] meepleToWall;
	static int[] meepleToTarget, wallToTarget;
	static int[] wallScore;

	public static class Point {
		int row, col, moveCount;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public Point(int row, int col, int moveCount) {
			this(row, col);
			this.moveCount = moveCount;
		}

		public boolean isSame(Point point) {
			return this.row == point.row && this.col == point.col;
		}
	}

	public static void main(String[] args) throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();

		testCases = Integer.parseInt(input.readLine());

		for (int testCase = 0; testCase < testCases; testCase++) {
			init();

			findFromTarget();

			for (int meepleIdx = 1; meepleIdx <= meepleSize; meepleIdx++) {
				findWall(meepleIdx, meeplePoints.get(meepleIdx));

				for (int wallIdx = 1; wallIdx <= wallSize; wallIdx++) {
					if (wallToTarget[wallIdx] == 0 || meepleToWall[meepleIdx][wallIdx] == 0)
						continue;

					wallScore[wallIdx] += Math.max(meepleToTarget[meepleIdx] - (meepleToWall[meepleIdx][wallIdx] + wallToTarget[wallIdx]), 0);
				}
			}
            
			output.append(Arrays.stream(meepleToTarget).sum())
				.append(" ")
				.append(Arrays.stream(wallScore).sum())
				.append("\n");
		}

		System.out.println(output);
	}

	public static void findFromTarget() {
		Deque<Point> queue = new ArrayDeque<>();
		boolean[][] visited = new boolean[rowSize + 1][colSize + 1];

		queue.add(target);
		visited[target.row][target.col] = true;

		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			int row = cur.row;
			int col = cur.col;

			if (isMeeple(row, col)) {
				meepleToTarget[-board[row][col]] = cur.moveCount;
			}

			if (isWall(row, col)) {
				wallToTarget[board[row][col]] = cur.moveCount;
				continue;
			}

			for (int[] delta : DELTAS) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];

				if (isOut(nr, nc))
					continue;

				if (visited[nr][nc])
					continue;

				visited[nr][nc] = true;
				queue.add(new Point(nr, nc, cur.moveCount + 1));
			}
		}
	}

	public static void findWall(int meepleIdx, Point start) {
		Deque<Point> queue = new ArrayDeque<>();
		boolean[][] visited = new boolean[rowSize + 1][colSize + 1];

		queue.add(start);
		visited[start.row][start.col] = true;

		int wallCount = 0;

		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			int row = cur.row;
			int col = cur.col;

			if (isWall(row, col)) {
				meepleToWall[meepleIdx][board[row][col]] = cur.moveCount;

				if (++wallCount == wallSize)
					return;

				continue;
			}

			for (int[] delta : DELTAS) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];

				if (isOut(nr, nc))
					continue;

				if (visited[nr][nc])
					continue;

				visited[nr][nc] = true;
				queue.add(new Point(nr, nc, cur.moveCount + 1));
			}
		}
	}

	public static boolean isOut(int row, int col) {
		return row <= 0 || row > rowSize || col <= 0 || col > colSize;
	}

	public static boolean isMeeple(int row, int col) {
		return board[row][col] < NOTHING;
	}

	public static boolean isWall(int row, int col) {
		return board[row][col] > NOTHING;
	}

	public static void init() throws IOException {
		tokenizer = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(tokenizer.nextToken());
		colSize = Integer.parseInt(tokenizer.nextToken());
		meepleSize = Integer.parseInt(tokenizer.nextToken());
		wallSize = 0;

		int targetRow = Integer.parseInt(tokenizer.nextToken());
		int targetCol = Integer.parseInt(tokenizer.nextToken());
		target = new Point(targetRow, targetCol);

		board = new int[rowSize + 1][colSize + 1];

		meeplePoints = new ArrayList<>();
		meeplePoints.add(null);
		for (int idx = 1; idx <= meepleSize; idx++) {
			tokenizer = new StringTokenizer(input.readLine());
			int row = Integer.parseInt(tokenizer.nextToken());
			int col = Integer.parseInt(tokenizer.nextToken());

			meeplePoints.add(new Point(row, col));

			board[row][col] = -idx;
		}

		wallPoints = new ArrayList<>();
		wallPoints.add(null);
		for (int row = 1; row <= rowSize; row++) {
			String line = input.readLine();

			for (int col = 1; col <= colSize; col++) {
				if (line.charAt(col - 1) == WALL) {
					wallPoints.add(new Point(row, col));

					board[row][col] = ++wallSize;
				}
			}
		}

		meepleToWall = new int[meepleSize + 1][wallSize + 1];
		meepleToTarget = new int[meepleSize + 1];
		wallToTarget = new int[wallSize + 1];
		wallScore = new int[wallSize + 1];
	}
}