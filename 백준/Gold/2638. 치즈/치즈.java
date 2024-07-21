import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
#백준 2638번 치즈
NxM 크기의 판에 치즈가 놓여있다.
판의 가장자리에는 치즈가 놓이지 않으며 치즈에는 하나 이상의 구멍이 있을 수 있다.
치즈를 공기 중에 놓으면 공기와 2변 이상 접촉된 칸은 한 시간이 지나면 녹아 없어진다.
치즈의 구멍 속에는 공기가 없지만 구멍이 열리면 구멍 속으로 공기가 들어간다.
공기에 치즈가 모두 녹아 없어지는 데 걸리는 시간과 모두 녹기 한 시간 전에 남아있는 치즈조각의 개수를 구해라.

#입력
첫째 줄: N, M
	N = 보드의 세로 크기
	M = 보드의 가로 크기
	(5 <= N, M <= 100)
둘째 줄부터 N개의 줄: 판의 정보
	0 = 빈칸
	1 = 치즈
#출력
첫째 줄: 치즈가 모두 녹아서 없어지는 데 걸리는 시간
둘째 줄: 모두 녹기 한 시간 전에 남아있는 치즈조각의 개수

#로직
1. 치즈의 개수 파악
2. 치즈의 개수가 0이 될 때까지 반복
    2-1. 시간 증가

    ##bfs 탐색 로직
    0. visited 배열 초기화 -> int 배열로 방문 횟수를 저장
    1. 현재 위치로부터 4방향 중 갈 수 있는 곳 탐색
    2. 탐색 중 공기이고, 방문한 적이 없다면 -> 방문 처리 후 큐에 삽입
    3. 탐색 중 치즈라면,
        3-1. 방문 회수 증가
        3-2. 방문 회수가 2 이상이라면 녹이기
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int AIR = 0, CHEESE = 1;
    static final int NOT_VISITED = 0, MELTING_CONDITION = 2;
    static int boardRow, boardCol;
    static int[][] board;
    static int time = 0, totalCheese = 0;

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
        Queue<Position> bfs = new LinkedList<>();
        bfs.offer(new Position(0, 0));
        //0. visited 배열 초기화 -> int 배열로 방문 횟수를 저장
        int[][] visited = new int[boardRow][boardCol];
        visited[0][0] = 1;

        while (!bfs.isEmpty()) {
            Position cur = bfs.poll();

            //1. 현재 위치로부터 4방향 중 갈 수 있는 곳 탐색
            for (int[] delta : deltas) {
                int nr = cur.row + delta[0];
                int nc = cur.col + delta[1];
                if (!canGo(nr, nc))
                    continue;

                //2. 탐색 중 공기이고, 방문한 적이 없다면 -> 방문 처리 후 큐에 삽입
                if (board[nr][nc] == AIR && visited[nr][nc] == NOT_VISITED) {
                    visited[nr][nc]++;
                    bfs.offer(new Position(nr, nc));
                }

                //3. 탐색 중 치즈라면,
                if (board[nr][nc] == CHEESE) {
                    //3-1. 방문 회수 증가
                    //3-2. 방문 회수가 2 이상이라면 녹이기
                    if (++visited[nr][nc] >= MELTING_CONDITION) {
                        totalCheese--;
                        board[nr][nc] = AIR;
                    }
                }
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
                //1. 치즈의 개수 파악
                if (board[row][col] == CHEESE)
                    totalCheese++;
            }
        }

        //로직
        //2. 치즈의 개수가 0이 될 때까지 반복
        while (totalCheese != 0) {
            //2-1. 시간 증가
            time++;
            //2-2. 확정 공기 칸인 (0,0)부터 bfs로 탐색
            findMeltingCheese();
        }

        //출력
        System.out.println(time);
    }
}
