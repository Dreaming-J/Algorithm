import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*
#백준 15683번 감시
NxM 크기의 사무실에 K개의 CCTV가 설치되어 있고, CCTV는 5가지 종류가 있다.
	1번. 한 방향
	2번. 평행하는 두 방향
	3번. 수직인 두 방향
	4번. 세 방향
	5번. 네 방향
CCTV는 감시할 수 있는 방향에 있는 모든 칸을 벽(6)을 만날 때까지 감시할 수 있다.
CCTV는 CCTV를 통과해 감시할 수 있다.
CCTV가 감시할 수 없는 사각지대의 최고 크기를 구해라.

#입력
첫째 줄: N, M
	N = 사무실의 세로 크기
	M = 사무실의 가로 크기
	(1 <= N, M <= 8)
둘째 줄부터 N개의 줄: 사무실 각 칸의 정보
	0 = 빈칸
	1~5 = CCTV
	6 = 벽
	-> CCTV의 최대 개수는 8개를 넘지 않음
#출력
사각 지대의 최소 크기

#CCTV의 설치 방법
1, 3, 4번은 4개
2번은 2개
5번은 1개

#로직
0. 사무실 초기화, CCTV 위치 파악
1. 각각의  CCTV의 방향을 임의로 결정
2. CCTV의 방향에 맞춰 벽을 만날때까지 영역(#) 표시
3. 임의의 방향으로 모든 CCTV를 설치했다면 사각지대 계산
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int officeRow, officeCol;
    static int[][] office;
    static List<CCTV> cctv = new ArrayList<>();
    static int minBlindSpot = Integer.MAX_VALUE;

    public static class CCTV {
        int row;
        int col;
        int[][] directions;

        public CCTV(int row, int col, int type) {
            this.row = row;
            this.col = col;
            setDirections(type);
        }

        private void setDirections(int type) {
            switch (type) {
                case 1:
                    directions = new int[][]{
                            {0, 1, 0, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 1},
                            {1, 0, 0, 0}};
                    break;
                case 2:
                    directions = new int[][]{
                            {0, 1, 0, 1},
                            {1, 0, 1, 0}};
                    break;
                case 3:
                    directions = new int[][]{
                            {1, 1, 0, 0},
                            {0, 1, 1, 0},
                            {0, 0, 1, 1},
                            {1, 0, 0, 1}};
                    break;
                case 4:
                    directions = new int[][]{
                            {1, 1, 0, 1},
                            {1, 1, 1, 0},
                            {0, 1, 1, 1},
                            {1, 0, 1, 1}};
                    break;
                case 5:
                    directions = new int[][]{{1, 1, 1, 1}};
                    break;
            }
        }
    }

    //2. CCTV의 방향에 맞춰 벽을 만날때까지 영역(#) 표시
    public static int[][] monitor(CCTV cctv, int[] direction, int[][] office) {
        int[][] newOffice = new int[officeRow][officeCol];
        for (int row = 0; row < officeRow; row++) {
            newOffice[row] = Arrays.copyOf(office[row], officeCol);
        }

        for (int dirIdx = 0; dirIdx < direction.length; dirIdx++) {
            if (direction[dirIdx] == 0)
                continue;
            int nr = cctv.row + deltas[dirIdx][0];
            int nc = cctv.col + deltas[dirIdx][1];
            while (canMove(nr, nc, newOffice)) {
                if (newOffice[nr][nc] == 0)
                    newOffice[nr][nc] = -1;
                nr += deltas[dirIdx][0];
                nc += deltas[dirIdx][1];
            }
        }
        
        return newOffice;
    }

    public static boolean canMove(int row, int col, int[][] office) {
        return row >= 0 && row < officeRow && col >= 0 && col < officeCol && office[row][col] != 6;
    }

    public static int calBlindSpot(int[][] office) {
        return Arrays.stream(office)
                .map(row -> (int) Arrays.stream(row)
                        .filter(cell -> cell == 0)
                        .count())
                .mapToInt(i -> i)
                .sum();
    }
    
    public static void dfs(int depth, int[] curdirection, int[][] curOffice) {
        //3. 임의의 방향으로 모든 CCTV를 설치했다면 사각지대 계산
    	if (depth == cctv.size()) {
    		minBlindSpot = Math.min(minBlindSpot, calBlindSpot(curOffice));
    		return;
    	}

        //1. 각각의  CCTV의 방향을 임의로 결정
    	CCTV curCCTV = cctv.get(depth);
    	for (int[] direction : curCCTV.directions) {
    		dfs(depth + 1, direction, monitor(curCCTV, direction, curOffice));
    	}
    }

    public static void main(String[] args) throws IOException {
        //입력
        st = new StringTokenizer(br.readLine().trim());
        officeRow = Integer.parseInt(st.nextToken());
        officeCol = Integer.parseInt(st.nextToken());

        //0. 사무실 초기화, CCTV 위치 파악
        office = new int[officeRow][officeCol];
        for (int row = 0; row < officeRow; row++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int col = 0; col < officeCol; col++) {
                int cell = Integer.parseInt(st.nextToken());
                if (cell != 0 && cell != 6)
                    cctv.add(new CCTV(row, col, cell));
                office[row][col] = cell;
            }
        }

        //로직
        dfs(0, new int[] {0, 0, 0, 0}, office);
        
        System.out.println(minBlindSpot);
    }
}
