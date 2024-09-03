/*
= BOJ 1600. 말이 되고픈 원숭이

= 로직
1. 초기 세팅
	1-1. 말 능력 개수 입력
	1-2. 판의 가로, 세로 길이 입력
	1-3. 판의 정보 입력
	1-4. 변수 초기화
2. 최소 이동 횟수 계산
	2-1. 도착점에 도착했다면 탐색 종료
	2-2. 말의 능력 사용 횟수가 남았다면 말처럼 이동
	2-3. 일반적인 방향으로 이동
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static final int[][] HORSE_DELTA = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
    static final int OBSTACLE = 1;
    static int horseMoveCount, rowSize, colSize;
    static int[][] board;
    static int[][][] records;
    static int minMoveCount;
    
    public static void findMinMove(int startRow, int startCol, int endRow, int endCol) {
    	PriorityQueue<Point> pq = new PriorityQueue<>();
    	
        records[0][startRow][startCol] = 0;
    	pq.add(new Point(startRow, startCol, 0, 0));
    	
    	while (!pq.isEmpty()) {
    		Point cur = pq.poll();
    		
    		//2-1. 도착점에 도착했다면 탐색 종료
    		if (cur.row == endRow && cur.col == endCol) {
    			minMoveCount = cur.moveCount;
    			return;
    		}
    		
    		//2-2. 말의 능력 사용 횟수가 남았다면 말처럼 이동
    		if (cur.horseMoveCount < horseMoveCount) {
    			for (int[] horseDelta : HORSE_DELTA) {
    				int nr = cur.row + horseDelta[0];
    				int nc = cur.col + horseDelta[1];
    				
    				if (!canGo(nr, nc))
    					continue;
    				
    				if (board[nr][nc] == OBSTACLE)
    					continue;
    				
    				if (cur.moveCount + 1 < records[cur.horseMoveCount + 1][nr][nc]) {
    					records[cur.horseMoveCount + 1][nr][nc] = cur.moveCount + 1;
        				pq.add(new Point(nr, nc, cur.horseMoveCount + 1, cur.moveCount + 1));
    				}
    			}
    		}
    		
    		//2-3. 일반적인 방향으로 이동
			for (int[] delta : DELTA) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];
				
				if (!canGo(nr, nc))
					continue;
				
				if (board[nr][nc] == OBSTACLE)
					continue;
				
				if (cur.moveCount + 1 < records[cur.horseMoveCount][nr][nc]) {
					records[cur.horseMoveCount][nr][nc] = cur.moveCount + 1;
					pq.add(new Point(nr, nc, cur.horseMoveCount, cur.moveCount + 1));
				}
				
			}
    	}
    }

    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 최소 이동 횟수 계산
    	findMinMove(0, 0, rowSize - 1, colSize - 1);
    	
    	//3. 출력
        System.out.println(minMoveCount);
    }
    
	//1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 말 능력 개수 입력
    	horseMoveCount = Integer.parseInt(input.readLine());
    	
    	//1-2. 판의 가로, 세로 길이 입력
    	st = new StringTokenizer(input.readLine());
    	colSize = Integer.parseInt(st.nextToken());
    	rowSize = Integer.parseInt(st.nextToken());
    	
    	//1-3. 판의 정보 입력
    	board = new int[rowSize][colSize];
    	for (int row = 0; row < rowSize; row++) {
            st = new StringTokenizer(input.readLine());
            for (int col = 0; col < colSize; col++) {
            	board[row][col] = Integer.parseInt(st.nextToken());
            }
        }
    	
    	//1-4. 변수 초기화
        minMoveCount = -1;
        records = new int[horseMoveCount + 1][rowSize][colSize];
        for (int[][] record : records) {
        	for (int[] r : record)
        		Arrays.fill(r, Integer.MAX_VALUE);
        }
    }

    public static class Point implements Comparable<Point> {
        int row;
        int col;
        int horseMoveCount;
        int moveCount;
        
		public Point(int row, int col, int horseMoveCount, int moveCount) {
			this.row = row;
			this.col = col;
			this.horseMoveCount = horseMoveCount;
			this.moveCount = moveCount;
		}
		
		@Override
		public int compareTo(Point o) {
			if (moveCount == o.moveCount)
				return Integer.compare(horseMoveCount, o.horseMoveCount);
			
			return Integer.compare(moveCount, o.moveCount);
		}
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }
}