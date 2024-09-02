import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input;
	static StringBuilder output;
	static StringTokenizer st;
	static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static final int EMPTY = 0, FROZEN = 1;
	static int rowSize, colSize, frozenSize;
	static int[][] map;
	static boolean[][] visited;
	static PriorityQueue<Integer> answer;
	
	public static void find(Point point) {
		Deque<Point> queue = new ArrayDeque<>();
		
		visited[point.row][point.col] = true;
		queue.add(point);
		
		int size = 0;
		
		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			
			size++;
			
			for (int[] delta : deltas) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];
				
				if (!canGo(nr, nc))
					continue;
				
				if (visited[nr][nc])
					continue;
				
				if (map[nr][nc] == FROZEN)
					continue;
				
				visited[nr][nc] = true;
				queue.add(new Point(nr, nc));
			}
		}
		
		answer.add(size);
	}
	
	public static void main(String[] args) throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new StringBuilder();
		
		init();

		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				if (visited[row][col])
					continue;
				
				if (map[row][col] == FROZEN)
					continue;
				
				find(new Point(row, col));
			}
		}
		
		output.append(answer.size()).append("\n");
		while (!answer.isEmpty()) {
			output.append(answer.poll()).append(" ");
		}
		
		System.out.println(output);
	}
	
	public static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
	
	public static void init() throws IOException {
		st = new StringTokenizer(input.readLine().trim());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		frozenSize = Integer.parseInt(st.nextToken());
		
		map = new int[rowSize][colSize];
		for (int idx = 0; idx < frozenSize; idx++) {
			st = new StringTokenizer(input.readLine().trim());
			int startCol = Integer.parseInt(st.nextToken());
			int startRow = Integer.parseInt(st.nextToken());
			int endCol = Integer.parseInt(st.nextToken());
			int endRow = Integer.parseInt(st.nextToken());
			
			for (int row = startRow; row < endRow; row++) {
				for (int col = startCol; col < endCol; col++) {
					map[row][col] = FROZEN;
				}
			}
		}
		
		visited = new boolean[rowSize][colSize];
		answer = new PriorityQueue<>();
	}
}
