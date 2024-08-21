/*
#SWEA 6109. 추억의 2048게임
  
0. 테스트 케이스 입력
1. 입력 및 초기화
	1-1. 격자의 크기, 방향 입력
	1-2. 격자의 크기만큼 격자 정보 입력
2. 입력된 방향이 오른쪽 방향이 되도록 반시계방향으로 격자 회전
	2-1. 회전 횟수만큼 돌았다면 종료
	2-2. 배열을 복사하여 따로 보관
	2-3. 90도만큼 반시계방향으로 회전
    2-4. 다음 회전으로 이동
3. 오른쪽 방향으로 2048 시뮬레이션 진행
	3-1. 각 행마다, 오른쪽에서 두번째 인덱스부터 0번 인덱스까지 탐색
	3-2. 비어있지 않고, 사용하지 않은 칸이라면 이동
		3-2-1. 현재 위치가 격자의 끝이라면, 현재 칸에 고정
		3-2-2. 다음 칸이 사용된 칸이라면, 현재 칸에 고정
		3-2-3. 다음 칸이 0이 아니고 값이 다르다면, 현재 칸에 고정
		3-2-4. 다음 칸과의 값이 같다면, 숫자가 2배 커지고 다음 칸에 고정 후 사용 처리
4. 원래 방향이 되도록 시계방향으로 격자 회전
5. 출력
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
  
class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static final int EMPTY = 0, ROTATE_COUNT = 4;
    static StringTokenizer st;
    static int testCase;
    static int mapSize;
    static String direction;
    static int[][] map;
    static boolean[][] usedMap;
    
    public static enum Direction {
    	RIGHT("right"),
    	DOWN("down"),
    	LEFT("left"),
    	UP("up");
    	
    	final String command;
    	
    	Direction(String command) {
    		this.command = command;
    	}
    	
    	public static int of(String command) {
    		for (Direction dir : values()) {
    			if (dir.command.equals(command))
    				return dir.ordinal();
    		}
    		
    		return -1;
    	}
    }

	//2. 입력된 방향이 오른쪽 방향이 되도록 반시계방향으로 격자 회전
    public static void rotateMap(int count) {
    	//2-1. 회전 횟수만큼 돌았다면 종료
    	if (count == 0)
    		return;
    	
    	//2-2. 배열을 복사하여 따로 보관
    	int[][] temp = new int[mapSize][mapSize];
    	for (int idx = 0; idx < mapSize; idx++)
    		temp[idx] = Arrays.copyOf(map[idx], mapSize);
    	
    	//2-3. 90도만큼 반시계방향으로 회전
    	for (int row = 0; row < mapSize; row++) {
    		for (int col = 0; col < mapSize; col++)
    			map[row][col] = temp[col][mapSize - row - 1];
    	}
    	
    	//2-4. 다음 회전으로 이동
    	rotateMap(count - 1);
    }

	//3. 왼쪽 방향으로 2048 시뮬레이션 진행
    public static void play2048() {
    	//3-1. 각 행마다, 오른쪽에서 두번째 인덱스부터 0번 인덱스까지 탐색
    	for (int row = 0; row < mapSize; row++) {
    		for (int col = mapSize - 2; col >= 0; col--) {
    			
    	    	//3-2. 비어있지 않고, 사용하지 않은 칸이라면 이동
    			if (map[row][col] != EMPTY && !usedMap[row][col]) {
        			moveCell(map[row][col], col, map[row], usedMap[row]);
    			}
    		}
    	}
    }
    
    public static void moveCell(int value, int pos, int[] line, boolean[] usedLine) {
    	line[pos] = EMPTY;
    	
    	while (true) {
    		//3-2-1. 현재 위치가 격자의 끝이라면, 현재 칸에 고정
    		if (pos == mapSize - 1) {
    			line[pos] = value;
    			return;
    		}
    		
    		//3-2-2. 다음 칸이 사용된 칸이라면, 현재 칸에 고정
        	if (usedLine[pos + 1]) {
        		line[pos] = value;
        		return;
        	}
        	
    		//3-2-3. 다음 칸이 0이 아니고 값이 다르다면, 현재 칸에 고정
        	if (line[pos + 1] != EMPTY && value != line[pos + 1]) {
        		line[pos] = value;
        		return;
        	}
        	
    		//3-2-4. 다음 칸과의 값이 같다면, 숫자가 2배 커지고 다음 칸에 고정 후 사용 처리
        	if (value == line[pos + 1]) {
        		line[pos + 1] = value * 2;
        		usedLine[pos + 1] = true;
        		return;
        	}
        	
        	pos++;
    	}
    }
  
    public static void main(String args[]) throws IOException {
        //0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());
        
        for(int tc = 1; tc <= testCase; tc++) {
        	//1. 입력 및 초기화
        	initTestCase();
        	
        	//2. 입력된 방향이 오른쪽 방향이 되도록 반시계방향으로 격자 회전
        	rotateMap(Direction.of(direction));
        	
        	//3. 오른쪽 방향으로 2048 시뮬레이션 진행
        	play2048();
        	
        	//4. 원래 방향이 되도록 시계방향으로 격자 회전
        	if (Direction.of(direction) != 0)
        		rotateMap(ROTATE_COUNT - Direction.of(direction));
        	
        	//5. 출력
        	output.append("#").append(tc).append("\n");
        	for (int[] line : map) {
        		for (int element : line)
        			output.append(element).append(" ");
        		output.append("\n");
        	}
        }
        System.out.println(output);
    }
    
    public static void initTestCase() throws IOException {
    	//1-1. 격자의 크기, 방향 입력
		st = new StringTokenizer(input.readLine().trim());
		mapSize = Integer.parseInt(st.nextToken());
		direction = st.nextToken();
    	
    	//1-2. 격자의 크기만큼 격자 정보 입력
    	map = new int[mapSize][mapSize];
    	for (int row = 0; row < mapSize; row++) {
    		st = new StringTokenizer(input.readLine().trim());
    		for (int col = 0; col < mapSize; col++)
    			map[row][col] = Integer.parseInt(st.nextToken());
    	}
    	
    	//1-3. 변수 초기화
    	usedMap = new boolean[mapSize][mapSize];
    }
}