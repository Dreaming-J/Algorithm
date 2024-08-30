/*
SWEA 1767. [SW Test 샘플문제] 프로세서 연결하기

=특이 사항
전선 교차 안됨
가장 많은 Core 연결했을 때, 최소 전선 길이

=로직
0. 초기 세팅
1. 테스트 케이스 입력
2. 테스트 케이스별 초기 세팅
	2-1. 변수 초기화
	2-2. 멕시노스의 크기 입력
	2-3. 멕시노스 배열 정보 입력
	    2-3-1. 가장자리가 아닌 코어는 따로 보관한다.
3. 부분집합을 응용해 모든 경우의 수를 파악한다.
    3-1. 현재 남은 코어를 모두 설치해도 최대 코어보다 작은 경우 탐색 포기
    3-2. 모든 코어를 탐색했다면 탐색을 종료
        3-2-1. 설치한 코어의 개수가 최대 코어와 동일하다면, 최소 전선의 개수 업데이트
        3-2-2. 설치한 코어의 개수가 최대 코어 초과이면, 최대 코어 개수 및 최소 전선의 개수 업데이트
    3-3. 사방으로 전선 설치
        3-3-1. 해당 방향으로 설치 가능한지 판단
            3-3-1-1. 가능하다면 전선 설치
            3-3-1-2. 전선 설치한 상태로 다음 코어 선택
            3-3-1-3. 재귀가 끝나면, 설치한 전선 회수
    3-4. 해당 코어 설치 안함
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //상하좌우 델타 배열
    static final int EMPTY = 0, CORE = 1, WIRE = 2;
    static int testCase;
    static int mapSize;
    static int[][] map;
    static Core[] cores; //가장자리에 없는 코어 관리
    static int coreSize; //가장자리에 없는 코어의 개수
    static int maxCore, minWire;

    public static void findMinWiresWithMaxCore(int coreIdx, int coreCount, int wireCount) {
        //3-1. 현재 남은 코어를 모두 설치해도 최대 코어보다 작은 경우 탐색 포기
        if (coreCount + (coreSize - coreIdx) < maxCore) {
            return;
        }

        //3-2. 모든 코어를 탐색했다면 탐색을 종료
        if (coreIdx == coreSize) {
            //3-2-1. 설치한 코어의 개수가 최대 코어와 동일하다면, 최소 전선의 개수 업데이트
            if (coreCount == maxCore) {
                minWire = Math.min(wireCount, minWire);
            }

            //3-2-2. 설치한 코어의 개수가 최대 코어 초과이면, 최대 코어 개수 및 최소 전선의 개수 업데이트
            if (coreCount > maxCore) {
                maxCore = coreCount;
                minWire = wireCount;
            }

            return;
        }

        //3-3. 사방으로 전선 설치
        for (int[] delta : deltas) {
            //3-3-1. 해당 방향으로 설치 가능한지 판단
            if (canInstallWire(coreIdx, delta)) {
                //3-3-1-1. 가능하다면 전선 설치
                int installWireCount = installWire(coreIdx, delta, true);
                //3-3-1-2. 전선 설치한 상태로 다음 코어 선택
                findMinWiresWithMaxCore(coreIdx + 1, coreCount + 1, wireCount + installWireCount);
                //3-3-1-3. 재귀가 끝나면, 설치한 전선 회수
                installWire(coreIdx, delta, false);
            }
        }
        //3-4. 해당 코어 설치 안함
        findMinWiresWithMaxCore(coreIdx + 1, coreCount, wireCount);
    }

    public static boolean canInstallWire(int coreIdx, int[] delta) {
        int nr = cores[coreIdx].row + delta[0];
        int nc = cores[coreIdx].col + delta[1];

        for (; canGo(nr, nc); nr += delta[0], nc += delta[1]) {
            if (map[nr][nc] != EMPTY)
                return false;
        }

        return true;
    }

    public static int installWire(int coreIdx, int[] delta, boolean installOrUninstall) {
        int installWireCount = 0;

        int install = installOrUninstall ? WIRE : -WIRE;
        int nr = cores[coreIdx].row + delta[0];
        int nc = cores[coreIdx].col + delta[1];

        for (; canGo(nr, nc); nr += delta[0], nc += delta[1]) {
            map[nr][nc] += install;
            installWireCount++;
        }

        return installWireCount;
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < mapSize && col >= 0 && col < mapSize;
    }

    public static void main(String[] args) throws IOException {
        //0. 초기 세팅
        init();

        //1. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
        	//2. 테스트 케이스별 초기 세팅
            initTestCase();

            //3. 부분집합을 응용해 모든 경우의 수를 파악한다.
            findMinWiresWithMaxCore(0, 0, 0);

            //4. 출력
            output.append("#").append(tc).append(" ").append(minWire).append("\n");
        }
        System.out.println(output);
    }

    public static class Core {
        int row;
        int col;

        public Core() {
        }

        public Core(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void init() {
        cores = new Core[12];
        for (int idx = 0; idx < cores.length; idx++) {
            cores[idx] = new Core();
        }
    }

    public static void initTestCase() throws IOException {
        //2-1. 변수 초기화
        coreSize = 0;
        maxCore = Integer.MIN_VALUE;
        minWire = Integer.MAX_VALUE;

        //2-2. 멕시노스의 크기 입력
        mapSize = Integer.parseInt(input.readLine());

        //2-3. 멕시노스 배열 정보 입력
        map = new int[mapSize][mapSize];
        for (int row = 0; row < mapSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < mapSize; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());

                //2-3-1. 가장자리가 아닌 코어는 따로 보관한다.
                if (map[row][col] == CORE && !isBorder(row, col)) {
                    cores[coreSize].row = row;
                    cores[coreSize++].col = col;
                }
            }
        }
    }

    public static boolean isBorder(int row, int col) {
        return row == 0 || row == mapSize - 1 || col == 0 || col == mapSize - 1;
    }
}