import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/*
백준 14499번 주사위 굴리기
NxM인 지도에 모든 면이 0인 주사위가 놓여져 있다.
주사위를 굴렸을 때,
    이동한 칸이 0이면, 주사위의 바닥면 수가 칸에 복사된다
    0이 아니면, 칸의 수가 주사위의 바닥면에 복사, 칸의 수는 0이 된다
주사위를 지도 바깥으로 이동시키는 명령은 무시

# 입력
    첫째 줄: N M x y K
        N, M = 지도의 세로, 가로 크기 (1 <= N, M <= 20)
        x, y = 주사위의 시작 좌표 (0 <= x <= N - 1, 0 <= y <= M - 1)
        K = 주사위 이동 명령의 개수 (1 <= K <= 1,000)
    둘째 줄부터 N개의 줄: 지도에 쓰여 있는 수 (10 미만의 자연수 || 0)
        북쪽부터 남쪽으로, 각 줄은 서쪽부터 동쪽으로
        주사위를 놓는 칸은 항상 0
    마지막 줄: 주사위 이동 명령 K개
        동쪽 == 1, 서쪽 == 2, 북쪽 == 3, 남쪽 == 4
# 출력
    이동할 때마다 주사위의 윗 면에 쓰여 있는 수 출력

# 주사위 이동
[상, 하, 좌, 우, 앞, 뒤]
[1, 2, 3, 4, 5, 6]
    -> 동쪽(1) 이동 시: [4, 3, 6, 1, 5, 2]
    -> 서쪽(2) 이동 시: [3, 4, 1, 6, 5, 2]
    -> 북쪽(3) 이동 시: [5, 2, 4, 3, 6, 1]
    -> 남쪽(4) 이동 시: [2, 5, 4, 3, 1, 6]

# 로직
    1. 주사위 이동
        1-1. 지도 바깥의 이동 명령은 무시
        1-2. 주사위 좌표, 방향 수정
    2. 값 변경
        2-1. 이동한 칸이 0이면 -> 주사위의 바닥면 수를 칸에 복사
        2-2. 이동한 칸이 0이 아니면 -> 칸의 수를 주사위의 바닥면에 복사되고, 칸의 수는 0으로 변경
    3. 이동 후, 주사위 윗면의 값을 출력
    4. 이동 명령이 남아있다면 1부터 반복
 */

public class Main {
    // dRow,dCol = {X, 동쪽, 서쪽, 북쪽, 남쪽}
    static final int[] dRow = {0, 0, 0, -1, 1};
    static final int[] dCol = {0, 1, -1, 0, 0};
    static final int EAST = 1, WEST = 2, NORTH = 3, SOUTH = 4;
    static final int TOP = 0, BOTTOM = 1, LEFT = 2, RIGHT = 3, FRONT = 4, BACK = 5;
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int boardRow, boardCol;
    static int diceRow, diceCol;
    static int commandCnt;
    static int[][] board;
    static Queue<Integer> commands = new LinkedList<>();
    static int[] dice = new int[6];

    public static void swap(int idx1, int idx2) {
        dice[idx1] += dice[idx2];
        dice[idx2] = dice[idx1] - dice[idx2];
        dice[idx1] -= dice[idx2];
    }

    public static boolean canMove(int command) {
        int moveRow = diceRow + dRow[command];
        int moveCol = diceCol + dCol[command];
        return moveRow >= 0 && moveRow <= boardRow - 1 && moveCol >= 0 && moveCol <= boardCol - 1;
    }

    public static void moveDice(int command) {
        diceRow += dRow[command];
        diceCol += dCol[command];

        switch (command) {
            case EAST:
                swap(TOP, BOTTOM);
                swap(TOP, LEFT);
                swap(BOTTOM, RIGHT);
                break;
            case WEST:
                swap(TOP, LEFT);
                swap(BOTTOM, RIGHT);
                swap(TOP, BOTTOM);
                break;
            case NORTH:
                swap(TOP, BOTTOM);
                swap(TOP, FRONT);
                swap(BOTTOM, BACK);
                break;
            case SOUTH:
                swap(TOP, FRONT);
                swap(BOTTOM, BACK);
                swap(TOP, BOTTOM);
                break;
            default:
                break;
        }
    }

    public static void main(String args[]) throws IOException {
        //입력
        st = new StringTokenizer(br.readLine());
        boardRow = Integer.parseInt(st.nextToken());
        boardCol = Integer.parseInt(st.nextToken());
        diceRow = Integer.parseInt(st.nextToken());
        diceCol = Integer.parseInt(st.nextToken());
        commandCnt = Integer.parseInt(st.nextToken());
        board = new int[boardRow][boardCol];
        for (int row = 0; row < boardRow; row++) {
            board[row] = Stream.of(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        st = new StringTokenizer(br.readLine());
        for (int cnt = 0; cnt < commandCnt; cnt++) {
            commands.offer(Integer.parseInt(st.nextToken()));
        }

        //로직
        //4. 이동 명령이 남아있다면 1부터 반복
        while (!commands.isEmpty()) {
            int command = commands.poll();

            //1. 주사위 이동
            //1-1. 지도 바깥의 이동 명령은 무시
            if (!canMove(command)) {
                continue;
            }
            //1-2. 주사위 좌표, 방향 수정
            moveDice(command);

            //2. 값 변경
            //2-1. 이동한 칸이 0이면 -> 주사위의 바닥면 수를 칸에 복사
            if (board[diceRow][diceCol] == 0) {
                board[diceRow][diceCol] = dice[BOTTOM];
            }
            //2-2. 이동한 칸이 0이 아니면 -> 칸의 수를 주사위의 바닥면에 복사하고, 칸의 수는 0으로 변경
            else {
                dice[BOTTOM] = board[diceRow][diceCol];
                board[diceRow][diceCol] = 0;
            }

            //3. 이동 후, 주사위 윗면의 값을 출력
            System.out.println(dice[TOP]);
        }
    }
}