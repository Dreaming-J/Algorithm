/*
=SWEA 5653. [모의 SW 역량테스트] 줄기세포배양

0. 테스트 케이스를 입력받는다.
1. 초기 상태의 세로 크기, 가로 크기, 배양 시간을 입력받는다.
2. 여러 변수를 초기화한다.
3. 초기 넓이만큼 입력을 받는다.
	3-1. 각 셀의 값이 0이 아닌 경우,
		3-1-1. 비활성 상태의 cell을 저장한다.
		3-1-2. 살아 있는 셀의 개수를 하나 더한다.
4. 배양 시간만큼 반복을 돌며 살아있는 모든 줄기 세포를 검사한다.
	4-1. 해당 셀이 죽어 있다면 넘어간다.
	4-2. 현재 생명력을 1 감소시킨다.
	4-3. 활성화된 경우
		4-3-1.상하좌우에 번식한다.
		    4-3-1-1. 우선순위 큐를 이용하여 생명력이 가장 큰 녀석을 추가하고 살아 있는 셀의 개수를 하나 더한다.
		4-3-2. 현재 생명력이 0이 된 경우, 해당 셀은 죽고 살아 있는 셀의 개수를 하나 뺀다.
	4-4. 비활성 상태이고 현재 생명력이 0이 된 경우, 초기 생명력으로 다시 설정하고 활성 상태로 바꿔준다.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int MAX_MAP_SIZE = 600;
    static Cell[][] cellMap;
    static boolean[][] visit;
    static PriorityQueue<Cell> aliveCells;
    static PriorityQueue<Cell> newCells;
    static int startRowSize, startColSize, time;
    static int aliveCellCount;

    public static class Cell implements Comparable<Cell> {
        int row;
        int col;
        int initHp;
        int hp;
        boolean isAlive;
        boolean isActive;

        public Cell(int row, int col, int initHp) {
            this.row = row;
            this.col = col;
            this.initHp = initHp;
            this.hp = initHp;
            this.isAlive = true;
            this.isActive = false;
        }

        @Override
        public int compareTo(Cell o) {
            return -(this.initHp - o.initHp);
        }
    }

    public static void initTestCase() throws IOException {
        //1. 초기 상태의 세로 크기, 가로 크기, 배양 시간을 입력받는다.
        st = new StringTokenizer(input.readLine().trim());
        startRowSize = Integer.parseInt(st.nextToken());
        startColSize = Integer.parseInt(st.nextToken());
        time = Integer.parseInt(st.nextToken());
        
        //2. 여러 변수를 초기화한다.
        aliveCellCount = 0;
        cellMap = new Cell[MAX_MAP_SIZE + startRowSize][MAX_MAP_SIZE + startColSize];
        visit = new boolean[MAX_MAP_SIZE + startRowSize][MAX_MAP_SIZE + startColSize];
        aliveCells = new PriorityQueue<>();

        //3. 초기 넓이만큼 입력을 받는다.
        for (int row = 0; row < startRowSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < startColSize; col++) {
                int initHp = Integer.parseInt(st.nextToken());
                //3-1. 각 셀의 값이 0이 아닌 경우,
                if (initHp != 0) {
                    //3-1-1. 비활성 상태의 cell을 저장한다.
                	cellMap[MAX_MAP_SIZE / 2 + row][MAX_MAP_SIZE / 2+ col] = new Cell(MAX_MAP_SIZE / 2 + row, MAX_MAP_SIZE / 2 + col, initHp);
                	visit[MAX_MAP_SIZE / 2 + row][MAX_MAP_SIZE / 2+ col] = true;
                	aliveCells.add(cellMap[MAX_MAP_SIZE / 2 + row][MAX_MAP_SIZE / 2+ col]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스를 입력받는다.
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            initTestCase();

            //4. 배양 시간만큼 반복을 돌며 살아있는 모든 줄기 세포를 검사한다.
            for (int cur = 1; cur <= time; cur++) {
                newCells = new PriorityQueue<>();
                while (!aliveCells.isEmpty()) {
                	Cell cell = aliveCells.poll();
                	
                    //4-2. 현재 생명력을 1 감소시킨다.
                    cell.hp--;

                    //4-3. 활성화된 경우
                    if (cell.isActive) {
                        //4-3-1.상하좌우에 번식한다.
                        for (int[] delta : deltas) {
                        	int newRow = cell.row + delta[0];
                        	int newCol = cell.col + delta[1];
                            //4-3-1-1. 우선순위 큐를 이용하여 생명력이 가장 큰 녀석을 추가한다.
                        	if (!visit[newRow][newCol]) {
                            	cellMap[newRow][newCol] = new Cell(newRow,newCol, cell.initHp);
                                newCells.add(new Cell(newRow, newCol, cell.initHp));
                                visit[newRow][newCol] = true;
                        	}
                        }
                        //4-3-2. 현재 생명력이 0이 된 경우, 해당 셀은 죽고 살아 있는 셀의 개수를 하나 뺀다.
                        if (cell.hp == 0) {
                            cell.isAlive = false;
                            cell.isActive = false;
                        }
                    }
                    //4-4. 비활성 상태이고 현재 생명력이 0이 된 경우, 초기 생명력으로 다시 설정하고 활성 상태로 바꿔준다.
                    else {
                        if (cell.hp == 0) {
                            cell.hp = cell.initHp;
                            cell.isActive = true;
                        }
                    }
                    
                    //4-5. 아직 살아있다면 다시 살아 있는 셀 보관 큐에 저장한다.
                    if (cell.isAlive)
                    	newCells.add(cell);
                }

                //5. 우선순위 큐에 저장된 세포들을 추가하고 살아 있는 셀의 개수를 하나 더한다.
                while (!newCells.isEmpty()) {
                	aliveCells.add(newCells.poll());
                }
            }

            output.append("#").append(tc).append(" ").append(aliveCells.size()).append("\n");
        }
        System.out.println(output);
    }
}