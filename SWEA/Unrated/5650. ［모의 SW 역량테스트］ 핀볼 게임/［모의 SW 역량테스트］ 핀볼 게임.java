/*
= SWEA 5650. [모의 SW 역량테스트] 핀볼 게임

= 로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
	1-1. 변수 초기화
	1-2. 게임판의 길이 입력
	1-3. 웜홀 저장용 임시 배열 초기화
	1-4. 게임판의 정보 입력
2. 핀볼 게임 시작
	2-1. 시작 위치 저장
	2-2. 이동
		2-2-1. 벽이라면 점수 증가 후, 방향 반대로 변경
		2-2-2. 블랙홀이라면 게임 종료
		2-2-3. 웜홀이라면 이동 방향 유지한채로 반대편 웜홀로 이동
		2-2-4. 블록이라면 점수 증가 후, 블록 모양에 맞춰 방향 변경
	2-3. 최대 점수 갱신
3. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //상하좌우
    static final int EMPTY = 0, BLACK_HOLE = -1, BLOCK = 1, WORM_HOLE = 6;
    static int boardSize;
    static int[][] board;
    static Map<Integer, Integer> wormHoles;
    static Ball ball;
    static int maxScore;
    
    //2. 핀볼 게임 시작
    public static void simulation() {
    	//2-1. 시작 위치 저장
    	Ball start = new Ball(ball.row, ball.col);
    	
    	int score = 0;
    	
    	//2-2. 이동
    	do {
    		//2-2-1. 벽이라면 점수 증가 후, 방향 반대로 변경
    		if (isOut(ball.row, ball.col)) {
    			score++;
    			ball.direction = Block.FIVE.direction[ball.direction];
    		}
    		
    		//2-2-2. 블랙홀이라면 게임 종료
    		else if (board[ball.row][ball.col] == BLACK_HOLE)
    			break;
    		
    		//2-2-3. 웜홀이라면 이동 방향 유지한채로 반대편 웜홀로 이동
    		else if (board[ball.row][ball.col] >= WORM_HOLE) {
    			int wormHole = wormHoles.get(ball.row * boardSize + ball.col);
    			ball.row = wormHole / boardSize;
    			ball.col = wormHole % boardSize;
    		}
    		
    		//2-2-4. 블록이라면 점수 증가 후, 블록 모양에 맞춰 방향 변경
    		else if (board[ball.row][ball.col] >= BLOCK) {
    			score++;
    			ball.direction = Block.of(board[ball.row][ball.col]).direction[ball.direction];
    		}
    		
    		ball.move();
    	} while (!start.equals(ball));
    	
    	//2-3. 최대 점수 갱신
    	maxScore = Math.max(score, maxScore);
    }
    
    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
        	init();
        	
        	//2. 핀볼 게임 시작
        	for (int row = 0; row < boardSize; row++) {
        		for (int col = 0; col < boardSize; col++) {
        			if (board[row][col] != EMPTY)
        				continue;
        			
        			for (int direction = 0; direction < DELTA.length; direction++) {
        	        	ball.set(row, col, direction);
        	        	simulation();
        			}
        		}
        	}
        	
        	//3. 출력
            output.append("#").append(tc).append(" ").append(maxScore).append("\n");
        }
        System.out.println(output);
    }
    
    public static boolean isOut(int row, int col) {
    	return row < 0 || row >= boardSize || col < 0 || col >= boardSize;
    }
    
    public static class Ball {
    	int row, col, direction;
    	
    	public Ball() {
    		
    	}
    	
    	public Ball(int row, int col) {
    		this.row = row;
    		this.col = col;
    	}

		public void set(int row, int col, int direction) {
			this.row = row;
			this.col = col;
			this.direction = direction;
		}
		
		public void move() {
			this.row += DELTA[direction][0];
			this.col += DELTA[direction][1];
		}

		@Override
		public boolean equals(Object obj) {
			Ball o = (Ball) obj;
			return row == o.row && col == o.col;
		}
    }
    
    public static enum Block {
    	ONE(1, new int[] {1, 3, 0, 2}),
    	TWO(2, new int[] {3, 0, 1, 2}),
    	THREE(3, new int[] {2, 0, 3, 1}),
    	FOUR(4, new int[] {1, 2, 3, 0}),
    	FIVE(5, new int[] {1, 0, 3, 2});
    	
    	private int idx;
    	private int[] direction;
    	
    	Block(int idx, int[] direction) {
    		this.idx = idx;
    		this.direction = direction;
    	}
    	
    	public static Block of(int idx) {
    		for (Block block : values()) {
    			if (block.idx == idx)
    				return block;
    		}
    		
    		return null;
    	}
    }

    //1. 초기 세팅
    static final int MAX_WORM_HOLE_SIZE = 5;
    public static void init() throws IOException {
    	//1-1. 변수 초기화
    	wormHoles = new HashMap<>();
    	ball = new Ball();
    	maxScore = 0;
    	
    	//1-2. 게임판의 길이 입력
    	boardSize = Integer.parseInt(input.readLine().trim());
    	
    	//1-3. 웜홀 저장용 임시 배열 초기화
    	int[] tempWormHoles = new int[MAX_WORM_HOLE_SIZE];
    	Arrays.fill(tempWormHoles, -1);
    	
    	//1-4. 게임판의 정보 입력
    	board = new int[boardSize][boardSize];
    	for (int row = 0; row < boardSize; row++) {
    		st = new StringTokenizer(input.readLine().trim());
    		for (int col = 0; col < boardSize; col++) {
    			board[row][col] = Integer.parseInt(st.nextToken());
    			
    			if (board[row][col] >= WORM_HOLE) {
        			int wormHoleIdx = board[row][col] - WORM_HOLE;
        			int point = row * boardSize + col;
    				
    				//1-4-1. 처음 발견된 웜홀이라면, 임시 배열에 저장
    				if (tempWormHoles[wormHoleIdx] == -1)
    					tempWormHoles[wormHoleIdx] = point;
        			
        			//1-4-2. 반대쪽 웜홀의 정보가 있다면, 맵에 저장 
    				else {
    					wormHoles.put(tempWormHoles[wormHoleIdx], point);
    					wormHoles.put(point, tempWormHoles[wormHoleIdx]);
    				}
    			}
    		}
    	}
    }
}