/*
= BOJ 17135. 캐슬 디펜스

=특이사항
궁수는 사거리 이하인 적 중에서 가장 가까운 적을 공격하며, 가장 왼쪽에 있는 적을 공격한다.
궁수는 동시에 사격하기 때문에 동일한 적을 공격할 수도 있다.
몬스터를 내리지말고 궁수를 올리자.

=로직
1. 초기 세팅
	1-1. 판의 세로와 가로 그리고 궁수의 공격 거리 입력
	1-2. 판의 세로 크기만큼 판의 정보 입력
	1-3. 변수 초기화
2. 궁수의 위치 선정
	2-1. 3명의 궁수를 선택했다면 시뮬레이션 진행 후 종료
	2-2. 마지막 칸까지 탐색했다면 종료
	2-3. 다음 칸으로 이동
3. 캐슬 디펜스 시뮬레이션 진행
	3-1. 궁수가 사격할 적 탐색
	3-2. 궁수 사격 진행
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int MAX_ARCHER_SIZE = 3, EMPTY = 0, ENEMY = 1;
	static final int[][] deltas = {{0, -1}, {-1, 0}, {0, 1}};
	static int rowSize, colSize, attackRange;
	static int[][] originalBoard;
	static int[][] board;
	
	static int[] archersColIdx;
	
	static Point[] targetEnemy; //매 턴마다 쓰러뜨릴 적의 위치
	static int maxDefeatedEnemies;
	
	static Deque<Point> queue;
	static boolean[][] visited;

	public static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	//2. 궁수의 위치 선정
	public static void chooseArcherPosition(int colIdx, int archerCount) {
		//2-1. 3명의 궁수를 선택했다면 시뮬레이션 진행 후 종료
		if (archerCount == MAX_ARCHER_SIZE) {
			//3. 시뮬레이션 진행
			play();
			return;
		}
		
		//2-2. 마지막 칸까지 탐색했다면 종료
		if (colIdx == colSize)
			return;
		
		//2-3. 다음 칸으로 이동
		archersColIdx[archerCount] = colIdx;
		chooseArcherPosition(colIdx + 1, archerCount + 1);
		chooseArcherPosition(colIdx + 1, archerCount);
	}
	
	//3. 캐슬 디펜스 시뮬레이션 진행
	public static void play() {
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				board[row][col] = originalBoard[row][col];
			}
		}
		
		int defeatedEnemy = 0;
		
		for (int archerRow = rowSize; archerRow > 0; archerRow--) {
			//3-1. 궁수가 사격할 적 탐색
			for (int archerIdx = 0; archerIdx < MAX_ARCHER_SIZE; archerIdx++)
				findTargetEnemy(archerIdx, archerRow, archersColIdx[archerIdx]);
			
			//3-2. 궁수 사격 진행
			for (int archerIdx = 0; archerIdx < MAX_ARCHER_SIZE; archerIdx++) {
				//3-2-1. 타겟을 찾지 못했으면 패스
				if (targetEnemy[archerIdx] == null)
					continue;
				
				//3-2-2. 해당 위치의 타겟이 아직 남아있으면 공격
				Point enemyPoint = targetEnemy[archerIdx];
				if (board[enemyPoint.row][enemyPoint.col] == ENEMY) {
					board[enemyPoint.row][enemyPoint.col] = EMPTY;
					defeatedEnemy++;
				}
			}
		}
		
		maxDefeatedEnemies = Math.max(defeatedEnemy, maxDefeatedEnemies);
	}
	
	//3-1. 궁수가 사격할 적 탐색
	public static void findTargetEnemy(int archerIdx, int archerRow, int archerCol) {
		queue.clear();
		queue.add(new Point(archerRow - 1, archerCol));
		
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				visited[row][col] = false;
			}
		}
		visited[archerRow - 1][archerCol] = true;
		//처음으로 쓰러지지 않은 적을 찾았다면 탐색 종료
		if (board[archerRow - 1][archerCol] == ENEMY) {
			targetEnemy[archerIdx] = new Point(archerRow - 1, archerCol);
			return;
		}
		
		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			
			//다음 위치로 이동
			for (int[] delta : deltas) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];
				
				if (!canGo(nr, nc))
					continue;
				
				if (visited[nr][nc])
					continue;
				
				//궁수의 거리를 넘어갔을 경우 다음으로 이동
				if (calDistance(nr, nc, archerRow, archerCol) > attackRange)
					continue;

				//처음으로 쓰러지지 않은 적을 찾았다면 탐색 종료
				if (board[nr][nc] == ENEMY) {
					targetEnemy[archerIdx] = new Point(nr, nc);
					return;
				}
				
				visited[nr][nc] = true;
				queue.add(new Point(nr, nc));
			}
		}
		
		targetEnemy[archerIdx] = null;
	}
	
	public static int calDistance(int row1, int col1, int row2, int col2) {
		return Math.abs(row1 - row2) + Math.abs(col1 - col2);
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 궁수의 위치 선정
		chooseArcherPosition(0, 0);
		
		//4. 출력
		System.out.println(maxDefeatedEnemies);
	}
	
	public static void init() throws IOException {
		//1-1. 판의 세로와 가로 그리고 궁수의 공격 거리 입력
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		attackRange = Integer.parseInt(st.nextToken());
		
		//1-2. 판의 세로 크기만큼 판의 정보 입력
		originalBoard = new int[rowSize][colSize];
		board = new int[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			st = new StringTokenizer(input.readLine());
			for (int col = 0; col < colSize; col++) {
				originalBoard[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		//1-3. 변수 초기화
		archersColIdx = new int[MAX_ARCHER_SIZE];
		targetEnemy = new Point[MAX_ARCHER_SIZE];
		queue = new ArrayDeque<>();
		visited = new boolean[rowSize][colSize];
	}
}
