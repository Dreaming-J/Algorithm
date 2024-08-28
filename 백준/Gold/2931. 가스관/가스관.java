import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //상하좌우 델타 배열
	static final char HOME = 'M', GOAL = 'Z', EMPTY = '.';
	static int rowSize, colSize;
	static char[][] map;
	static boolean hasHomeBlock, hasGoalBlock;
	static Position home;
	static Position goal;
	static Position current;
	static char answerBlock;
	
	public static enum Block {
		VERTICAL('|', new int[] {0, 1, -1, -1}, 0b0011),
		HORIZON('-', new int[] {-1, -1, 2, 3}, 0b1100),
		PLUS('+', new int[] {0, 1, 2, 3}, 0b1111),
		BLOCK1('1', new int[] {3, -1, 1, -1}, 0b1010),
		BLOCK2('2', new int[] {-1, 3, 0, -1}, 0b1001),
		BLOCK3('3', new int[] {-1, 2, -1, 0}, 0b0101),
		BLOCK4('4', new int[] {2, -1, -1, 1}, 0b0110);
		
		final char block;		//블록의 입력값
		final int[] direction;	//해당 블록이 갈 수 있는 방향
		final int openBit; 		//상하좌우로 통행 가능한 칸을 알려줌
		
		private Block(char block, int[] direction, int openBit) {
			this.block = block;
			this.direction = direction;
			this.openBit = openBit;
		}
		
		//블록의 입력값과 들어온 방향으로 다음 방향을 찾아주는 메서드
		public static int ofBlockAndDirection(char block, int idx) {
			for (Block b : values()) {
				if (b.block == block) {
					return b.direction[idx];
				}
			}
			
			return -1;
		}
		
		//정답을 찾을 때, 해당 블록의 주변에 통행 가능한 칸을 기반으로 정답인 블록을 찾아주는 메서드
		public static char ofBit(int openBit) {
			for (Block b : values()) {
				if (b.openBit == openBit) {
					return b.block;
				}
			}
			
			return EMPTY;
		}
	}
	
	public static class Position {
		int row;
		int col;
		int direction;
		
		public Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public Position(int row, int col, int direction) {
			this.row = row;
			this.col = col;
			this.direction = direction;
		}
	}
	
	public static void findStartPositionAndDirection() {
		//집에서 출발하는 방향 찾기
		for (int idx = 0; idx < 4; idx++) {
			int nr = home.row + deltas[idx][0];
			int nc = home.col + deltas[idx][1];
			
			if (canGo(nr, nc) && map[nr][nc] != EMPTY && Block.ofBlockAndDirection(map[nr][nc], idx) != -1) {
				hasHomeBlock = true;
				current = new Position(nr, nc, idx);
				break;
			}
		}
		
		//집에서 출발하는 방향을 못찾았다면, 유치원에서 출발하는 방향 찾기
		for (int idx = 0; idx < 4; idx++) {
			int nr = goal.row + deltas[idx][0];
			int nc = goal.col + deltas[idx][1];
			
			if (canGo(nr, nc) && map[nr][nc] != EMPTY && Block.ofBlockAndDirection(map[nr][nc], idx) != -1) {
				hasGoalBlock = true;
				if (current == null) {
					current = new Position(nr, nc, idx);
					break;
				}
			}
		}
	}
	
	public static void findEraseBlockPosition() {
		while (map[current.row][current.col] != EMPTY) {
			int direction = Block.ofBlockAndDirection(map[current.row][current.col], current.direction);

			current.row += deltas[direction][0];
			current.col += deltas[direction][1];
			current.direction = direction;
		}
	}
	
	public static void chooseBlock() {
		int openBit = 0b0;
		
		for (int idx = 0; idx < 4; idx++) {
			int nr = current.row + deltas[idx][0];
			int nc = current.col + deltas[idx][1];
			
			if (canGo(nr, nc) && map[nr][nc] != EMPTY) {
				if (map[nr][nc] == HOME && !hasHomeBlock)
					openBit |= 1 << idx;
				if (map[nr][nc] == GOAL && !hasGoalBlock)
					openBit |= 1 << idx;
				if (Block.ofBlockAndDirection(map[nr][nc], idx) != -1)
					openBit |= 1 << idx;
			}
		}
		
		answerBlock = Block.ofBit(openBit);
	}
	
	public static void main(String[] args) throws IOException {
		initTestCase();
		
		//첫 시작 방향 찾기
		findStartPositionAndDirection();
		
		//지워진 블록 찾기
		findEraseBlockPosition();
		
		//지워진 블록 주변 통행해야 하는 위치 판단
		chooseBlock();
		
		output.append(current.row + 1).append(" ")
		.append(current.col + 1).append(" ")
		.append(answerBlock).append("\n");
		System.out.println(output);
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
	
	public static void initTestCase() throws IOException {
		st = new StringTokenizer(input.readLine().trim());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		map = new char[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			String line = input.readLine().trim();
			for (int col = 0; col < colSize; col++) {
				map[row][col] = line.charAt(col);
				
				if (map[row][col] == HOME)
					home = new Position(row, col);
				
				if (map[row][col] == GOAL)
					goal = new Position(row, col);
			}
		}
	}
}
