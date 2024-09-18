/*
= BOJ 9328. 열쇠
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
    static final int ALPHA_SIZE = 26;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final char EMPTY = '.', WALL = '*', DOCUMENT = '$';
    static int rowSize, colSize;
    static char[][] building;
    static boolean[][] visited;
    static Deque<Integer> path;
    static Deque<Integer>[] doors;
    static int keyBit;
    static int maxDocument;

    //문서 훔치기 시작
    public static void startStealDocument() {
        while (!path.isEmpty()) {
            int curPosition = path.poll();
            int row = curPosition / colSize;
            int col = curPosition % colSize;
            char cur = building[row][col];

            //현재 위치가 문이고 열쇠를 가지고 있지 않다면, 해당 문 위치 저장 후 다음 위치로 바로 이동
            if (Character.isUpperCase(cur) && (keyBit & 1 << (cur - 'A')) == 0) {
                saveDoor(cur - 'A', row * colSize + col);
                continue;
            }

            //현재 위치 방문했다면 패스
            if (visited[row][col])
                continue;
            visited[row][col] = true;

            //현재 위치가 열쇠라면, 해당 열쇠 저장 후 해당 열쇠로 열 수 있는 문도 갈 수 있게 추가
            if (Character.isLowerCase(cur)) {
                int keyIdx = cur - 'a';
                keyBit |= 1 << (keyIdx);

                path.addAll(doors[keyIdx]);
            }

            //현재 위치가 문서라면, 훔치기
            if (cur == DOCUMENT)
                maxDocument++;

            //다음으로 이동
            for (int[] delta : DELTA) {
                int nr = row + delta[0];
                int nc = col + delta[1];

                //범위를 벗어났다면 패스
                if (!canGo(nr, nc))
                    continue;

                //벽이라면 패스
                if (building[nr][nc] == WALL)
                    continue;

                path.add(nr * colSize + nc);
            }
        }
    }

    public static void saveDoor(int doorIdx, int position) {
        doors[doorIdx].add(position);
    }

    public static void main(String[] args) throws IOException {
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 0; tc < testCase; tc++) {
            //초기 세팅
            init();

            //문서 훔치기 시작
            startStealDocument();

            //정답 출력
            output.append(maxDocument)
                    .append("\n");
        }
        System.out.println(output);
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    //초기 세팅
    public static void init() throws IOException {
        //빌딩의 크기 입력
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        //빌딩 정보 입력
        building = new char[rowSize][colSize];
        path = new ArrayDeque<>();
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++) {
                building[row][col] = line.charAt(col);

                //가장자리이고 벽이 아닌 위치는 다 경로에 추가
                if ((row == 0 || row == rowSize - 1 || col == 0 || col == colSize - 1) && building[row][col] != WALL) {
                    path.add(row * colSize + col);
                }
            }
        }

        //이미 가지고 있는 열쇠 정보 입력
        keyBit = 0;
        String line = input.readLine();
        if (!line.equals("0")) {
            for (int idx = 0; idx < line.length(); idx++)
                keyBit |= 1 << (line.charAt(idx) - 'a');
        }

        //변수 초기화
        visited = new boolean[rowSize][colSize];
        doors = new ArrayDeque[ALPHA_SIZE];
        for (int idx = 0; idx < ALPHA_SIZE; idx++)
            doors[idx] = new ArrayDeque<>();
        maxDocument = 0;
    }
}
