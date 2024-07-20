import java.util.*;
import java.util.stream.*;
import java.io.*;

/*
백준 3190(G4): 뱀
NxN 보드 위에서 뱀이 기어다니며 사과를 먹는 문제
사과의 위치와 뱀의 이동경로가 주어질 때 게임이 몇 초에 끝나는지 계산
=초기설정
 뱀은 (0,0)위치에서 시작한다.
 뱀의 길이는 1이며, 오른쪽을 향한다.
=규칙
 1.뱀은 몸길이를 늘려 머리를 다음칸에 이동
 2.만약 벽 or 몸과 부딪히면, 게임 종료
 3.만약 이동칸에 사과가 있다면, 사과가 사라지고 몸길이를 줄이지 않는다.
 4.만약 이동칸에 사과가 없다면, 몸길이를 줄여 꼬리가 위치한 칸을 비워준다.
 */

public class Main {
	static final int[] dy = {0, 1, 0, -1};
	static final int[] dx = {1, 0, -1, 0};
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int boardSize;
	static int appleCnt;
	static int[][] board;
	static int snakePathCnt;
	static Queue<SnakePath> snakePathes = new LinkedList<>();
	static int curDir = 0;
	static int curTime = 0;
	static Position curPos = new Position(0, 0);
	static Queue<Position> snake = new LinkedList<>();
	
	public static enum Direction {
		LEFT, RIGHT;
	}
	
	// board의 좌표를 나타내는 클래스
	public static class Position {
		int row;
		int col;
		
		public Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (row != other.row)
				return false;
			if (col != other.col)
				return false;
			return true;
		}
	}
	
	// 뱀의 이동경로를 나타내는 클래스
	public static class SnakePath {
		int time;
		Direction direction;
		
		public SnakePath(String time, String direction) {
			this.time = Integer.parseInt(time);
			this.direction = direction.equals("L") ? Direction.LEFT : Direction.RIGHT;
		}
	}
	
	// 뱀의 이동을 위한 메서드
	public static void move() {
		curTime++;
		curPos = new Position(curPos.row + dy[curDir], curPos.col + dx[curDir]);
	}
	
	// 뱀의 경로 변경을 위한 메서드
	public static void nextDir(Direction direction) {
		curDir = direction == Direction.LEFT ? curDir - 1 : curDir + 1;
		
		if (curDir == -1) curDir = 3;
		if (curDir == 4) curDir = 0;
	}
	
	//2.만약 벽 or 몸과 부딪히면, 게임 종료
	public static boolean isGameOver() {
		//2-1. 벽과 부딪히면
		if (curPos.row < 0 || curPos.row >= boardSize) return true;
		if (curPos.col < 0 || curPos.col >= boardSize) return true;
		//2-2. 몸과 부딪히면
		if (snake.contains(curPos)) return true;
		
		return false;
	}
	
	public static boolean eatApple() {
		if (board[curPos.row][curPos.col] == 1) {
			board[curPos.row][curPos.col] = 0;
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		//입력 및 초기화
		boardSize = Integer.parseInt(br.readLine());
		board = new int[boardSize][boardSize];
		appleCnt = Integer.parseInt(br.readLine());
		for (int appleIdx = 0; appleIdx < appleCnt; appleIdx++) {
			st = new StringTokenizer(br.readLine());
			board[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = 1;
		}
		snakePathCnt = Integer.parseInt(br.readLine());
		for (int snakeDirIdx = 0; snakeDirIdx < snakePathCnt; snakeDirIdx++) {
			st = new StringTokenizer(br.readLine());
			snakePathes.offer(new SnakePath(st.nextToken(), st.nextToken()));
		}
		snake.offer(curPos);
		
		//메인 로직
		while (true) {
			move();
			
			if (isGameOver())
				break;
			snake.offer(curPos);
			
			if (!eatApple())
				snake.poll();
			
			if (!snakePathes.isEmpty() && snakePathes.peek().time == curTime) {
				SnakePath nextPath = snakePathes.poll();
				nextDir(nextPath.direction);
			}
		}
		
		System.out.println(curTime);
	}
}
