import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
#백준 2636번 치즈
NxM 크기의 판에 치즈가 놓여있다.
판의 가장자리에는 치즈가 놓이지 않으며 치즈에는 하나 이상의 구멍이 있을 수 있다.
치즈를 공기 중에 놓으면 공기와 접촉된 칸은 한 시간이 지나면 녹아 없어진다.
치즈의 구멍 속에는 공기가 없지만 구멍이 열리면 구멍 속으로 공기가 들어간다.
공기에 치즈가 모두 녹아 없어지는 데 걸리는 시간과 모두 녹기 한 시간 전에 남아있는 치즈조각의 개수를 구해라.

#입력
첫째 줄: N, M
	N = 사무실의 세로 크기
	M = 사무실의 가로 크기
	(N, M <= 100)
둘째 줄부터 N개의 줄: 판의 정보
	0 = 빈칸
	1 = 치즈
#출력
첫째 줄: 치즈가 모두 녹아서 없어지는 데 걸리는 시간
둘째 줄: 모두 녹기 한 시간 전에 남아있는 치즈조각의 개수

#로직
1. 확정 공기 칸인 (0,0)부터 bfs로 탐색
2. 탐색 중 공기라면 큐에 삽입
3. 치즈라면 치즈카운트 증가 후, 큐에 삽입하지 않는다
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int AIR = 0, CHEESE = 1;
    static int boardRow, boardCol;
    static int[][] board;
    static int time = 0, lastCheese = 0, totalCheese = 0;

    public static class Position {
        int row;
        int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row <= boardRow - 1 && col >= 0 && col <= boardCol - 1;
    }

    public static void findMeltingCheese() {
        //1. 확정 공기 칸인 (0,0)부터 bfs로 탐색
        Queue<Position> bfs = new LinkedList<>();
        bfs.offer(new Position(0, 0));
        boolean[][] visited = new boolean[boardRow][boardCol];
        visited[0][0] = true;

        while (!bfs.isEmpty()) {
            Position cur = bfs.poll();

            //2. 탐색 중 공기라면 큐에 삽입
            if (board[cur.row][cur.col] == AIR) {
                for (int[] delta : deltas) {
                    int nr = cur.row + delta[0];
                    int nc = cur.col + delta[1];
                    if (canGo(nr, nc) && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        bfs.offer(new Position(nr, nc));
                    }
                }
            }

            //3. 치즈라면 총치즈개수 감소 후, 큐에 삽입하지 않는다
            if (board[cur.row][cur.col] == CHEESE) {
                totalCheese--;
                board[cur.row][cur.col] = AIR;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //입력
        st = new StringTokenizer(br.readLine().trim());
        boardRow = Integer.parseInt(st.nextToken());
        boardCol = Integer.parseInt(st.nextToken());

        board = new int[boardRow][boardCol];
        for (int row = 0; row < boardRow; row++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int col = 0; col < boardCol; col++) {
                board[row][col] = Integer.parseInt(st.nextToken());
                if (board[row][col] == CHEESE)
                    totalCheese++;
            }
        }

        //로직
        while (totalCheese != 0) {
            time++;
            lastCheese = totalCheese;
            findMeltingCheese();
        }

        //출력
        System.out.println(time);
        System.out.println(lastCheese);
    }
}
