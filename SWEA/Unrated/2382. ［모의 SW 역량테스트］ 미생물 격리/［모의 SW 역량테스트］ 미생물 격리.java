/*
=SWEA 2382. [모의 SW 역량테스트] 미생물 격리

1. 테스트케이스 입력
	1-1. 구역의 길이, 격리 시간, 미생물 군집의 개수 입력
	1-2. 미생물 군집의 개수만큼 세로 위치, 가로 위치, 미생물 수, 이동 방향 입력
	1-3. 변수 초기화
2. 시뮬레이션 통해 시간에 따른 미생물 군집 이동 계산
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] deltas = {{0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //상하좌우 배열
    static int testCase;
    static int length, time, groupSize;
    static Germ[][] map;
    static PriorityQueue<Germ> germs;
    static int totalGermCount;

    public static class Germ implements Comparable<Germ> {
        int row;
        int col;
        int germCount;
        Direction direction;

        public Germ(int row, int col, int germCount, Direction direction) {
            this.row = row;
            this.col = col;
            this.germCount = germCount;
            this.direction = direction;
        }

        public void move() {
            this.row += deltas[direction.idx][0];
            this.col += deltas[direction.idx][1];

            if (isBorder(row, col)) {
                germCount /= 2;
                changeDirection();
            }
        }

        public void changeDirection() {
        	direction = Direction.values()[direction.reverseIdx];
        }

        @Override
        public int compareTo(Germ o) {
            return -(this.germCount - o.germCount);
        }
    }
    
    public static enum Direction {
    	UP(1, 1),
    	DOWN(2, 0),
    	LEFT(3, 3),
    	RIGHT(4, 2);
    	
    	final int idx;
    	final int reverseIdx;
		
    	private Direction(int idx, int reverse) {
			this.idx = idx;
			this.reverseIdx = reverse;
		}
    	
    	public static Direction of(int idx) {
    		for (Direction direction : values()) {
    			if (direction.idx == idx)
    				return direction;
    		}
    		
    		return null;
    	}
    }

    public static void simulation() {
        PriorityQueue<Germ> newGerms = new PriorityQueue<>();

        while (!germs.isEmpty()) {
            Germ germ = germs.poll();

            map[germ.row][germ.col] = null;
            germ.move();

            if (germ.germCount != 0) {
                newGerms.add(germ);
            }
        }

        while (!newGerms.isEmpty()) {
            Germ germ = newGerms.poll();

            if (map[germ.row][germ.col] == null) {
                map[germ.row][germ.col] = germ;
                germs.add(germ);
            } else {
                map[germ.row][germ.col].germCount += germ.germCount;
            }
        }
    }

    public static boolean isBorder(int row, int col) {
        return row == 0 || row == length - 1 || col == 0 || col == length - 1;
    }

    public static void main(String[] args) throws IOException {
        //1. 테스트케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            initTestCase();

            //2. 시뮬레이션 통해 시간에 따른 미생물 군집 이동 계산
            for (int cur = 0; cur < time; cur++) {
                simulation();
            }

            for (Germ germ : germs) {
                totalGermCount += germ.germCount;
            }

            output.append("#").append(tc).append(" ").append(totalGermCount).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        //1-1. 구역의 길이, 격리 시간, 미생물 군집의 개수 입력
        st = new StringTokenizer(input.readLine().trim());
        length = Integer.parseInt(st.nextToken());
        time = Integer.parseInt(st.nextToken());
        groupSize = Integer.parseInt(st.nextToken());

        //1-2. 미생물 군집의 개수만큼 세로 위치, 가로 위치, 미생물 수, 이동 방향 입력
        map = new Germ[length][length];
        germs = new PriorityQueue<>();
        for (int idx = 0; idx < groupSize; idx++) {
            st = new StringTokenizer(input.readLine().trim());
            Germ germ = new Germ(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()), Direction.of(Integer.parseInt(st.nextToken())));
            map[germ.row][germ.col] = germ;
            germs.add(germ);
        }

        //1-3. 변수 초기화
        totalGermCount = 0;
    }
}
