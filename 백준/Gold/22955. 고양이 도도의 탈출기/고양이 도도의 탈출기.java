/*
= BOJ 22955. 고양이 도도의 탈출기
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final String FAIL_ESCAPE = "dodo sad";
    static final char CAT = 'C', DOG = 'D', EXIT = 'E', FLOOR = 'F', LADDER = 'L', FALL = 'X';
    static final int LADDER_HP = 5, FALL_HP = 10, MOVE_HP = 1;
    static final int[] DELTAS = {-1, 1};
    static int rowSize, colSize;
    static char[][] room;
    static List<Cell>[] graph;
    static int startPos, endPos;
    static int[] totalDamage;

    static class Cell {
        int pos;
        int damage;

        public Cell(int pos, int damage) {
            this.pos = pos;
            this.damage = damage;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        makeGraph();

        int answer = findBestRoute();

        System.out.println(answer != 0 ? answer : FAIL_ESCAPE);
    }

    private static int findBestRoute() {
        PriorityQueue<Cell> pq = new PriorityQueue<>((o1, o2) -> o1.damage - o2.damage);

        pq.add(new Cell(startPos, 0));

        while (!pq.isEmpty()) {
            Cell cur = pq.poll();

            for (Cell next : graph[cur.pos]) {
                int damage = next.damage + cur.damage;

                if (totalDamage[next.pos] == 0 || damage < totalDamage[next.pos]) {
                    totalDamage[next.pos] = damage;
                    pq.add(new Cell(next.pos, damage));
                }
            }
        }

        return totalDamage[endPos];
    }

    private static int getIdxOfCoordinate(int row, int col) {
        return row * colSize + col;
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize || room[row][col] == DOG;
    }

    private static void makeGraph() {
        graph = new ArrayList[rowSize * colSize];
        for (int idx = 0; idx < rowSize * colSize; idx++) {
            graph[idx] = new ArrayList<>();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                switch (room[row][col]) {
                    case CAT:
                        startPos = getIdxOfCoordinate(row, col);
                        moveHorizontal(row, col);
                        break;
                    case DOG:
                        break;
                    case EXIT:
                        endPos = getIdxOfCoordinate(row, col);
                        break;
                    case FLOOR:
                        moveHorizontal(row, col);
                        break;
                    case LADDER:
                        moveHorizontal(row, col);
                        moveVertical(row, col);
                        break;
                    case FALL:
                        fall(row, col);
                        break;
                }
            }
        }
    }

    private static void moveHorizontal(int row, int col) {
        int curPos = getIdxOfCoordinate(row, col);

        for (int delta : DELTAS) {
            int newCol = col + delta;

            if (isOut(row, newCol))
                continue;

            graph[curPos].add(new Cell(getIdxOfCoordinate(row, newCol), MOVE_HP));
        }
    }

    private static void moveVertical(int row, int col) {
        int curPos = getIdxOfCoordinate(row, col);

        int newRow = row - 1;
        if (isOut(newRow, col) || room[newRow][col] == FALL)
            return;
        int newPos = getIdxOfCoordinate(newRow, col);

        graph[curPos].add(new Cell(newPos, LADDER_HP));
        graph[newPos].add(new Cell(curPos, LADDER_HP));
    }

    private static void fall(int row, int col) {
        int newRow = row;
        while (room[++newRow][col] == FALL) ;
        int newPos = getIdxOfCoordinate(newRow, col);

        graph[getIdxOfCoordinate(row, col)].add(new Cell(newPos, FALL_HP));
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        room = new char[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++)
                room[row][col] = line.charAt(col);
        }

        totalDamage = new int[rowSize * colSize];
    }
}
