import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/*
#백준 14503번 로봇 청소기
NxM 크기의 방은 벽 또는 빈 칸으로 이루어진다.
청소기는 동서남북 중 바라보는 방향이 있다.
로봇 청소기는 다음과 같이 작동한다
    1. 현재 칸이 청소되지 않은 경우, 현재 칸 청소
    2. 현재 칸의 4방의 칸이 모두 청소된 경우,
        2-1. 바라보는 방향 유지한 채, 한 찬 후진 후 1번으로 이동
        2-2. 바라보는 방향 뒤 칸이 벽이라면 작동을 멈춘다
    3. 현재 칸의 4방 중 청소되지 않은 방이 있는 경우,
        3-1. 반시계 방향으로 회전
        3-2.바라보는 방향 앞 칸이 청소되지 않았다면 한칸 전진
        3-3. 1번으로 이동
로봇 청소기가 작동을 멈출 때까지 청소하는 칸의 개수를 출력해라.

#입력
첫째 줄: N, M
	N = 방의 세로 크기
	M = 방의 가로 크기
	(3 <= N, M <= 508)
둘째 줄: r, c, d
    r = 로봇 청소기의 세로 좌표
    c = 로봇 청소기의 가로 좌표
    d = 로봇 청소기가 바라보는 방향
        0 = 북, 1 = 동, 2 = 남, 3 = 서
셋째 줄부터 N개의 줄: 방 각 칸의 정보
    0 = 빈 칸
    1 = 벽
#출력
로봇 청소기가 작동을 멈출 때까지 청소하는 칸의 개수

#로직
1. 현재 칸이 청소되지 않은 경우, 현재 칸 청소
2. 현재 칸의 4방의 칸이 모두 청소된 경우,
    2-1. 바라보는 방향 유지한 채, 한 칸 후진 후 1번으로 이동
    2-2. 바라보는 방향 뒤 칸이 벽이라면 작동을 멈춘다
3. 현재 칸의 4방 중 청소되지 않은 방이 있는 경우,
    3-1. 반시계 방향으로 회전
    3-2. 바라보는 방향 앞 칸이 청소되지 않았다면 한 칸 전진
    3-3. 1번으로 이동
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int DIRTY = 0, WALL = 1, CLEANING = 2;
    static final int[][] deltas = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int roomRow, roomCol;
    static int[][] room;
    static int cleaingRoomCnt;
    static Robot robot;

    public static class Robot {
        int row;
        int col;
        int direction;

        public Robot(int row, int col, int direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
        
        public void run() {
            while (true) {
                //1. 현재 칸이 청소되지 않은 경우, 현재 칸 청소
                if (room[row][col] == DIRTY) {
                    room[row][col] = CLEANING;
                    cleaingRoomCnt++;
                }

                //현재 칸의 4방의 칸 청소 여부 판단
                boolean existDirty = false;
                for (int[] delta : deltas) {
                	if (room[row + delta[0]][col + delta[1]] == DIRTY) {
                		existDirty = true;
                		break;
                	}
                }
                
                //2. 현재 칸의 4방의 칸이 모두 청소된 경우,
                if (!existDirty) {
                    // 2-2. 바라보는 방향 뒤 칸이 벽이라면 작동을 멈춘다
                	if (room[row + getBackDelta()[0]][col + getBackDelta()[1]] == WALL) {
                		break;
                	}
                    // 2-1. 바라보는 방향 유지한 채, 한 칸 후진 후 1번으로 이동
                	robot.moveBack();
                	continue;
                }

                //3. 현재 칸의 4방 중 청소되지 않은 방이 있는 경우,
                while (true) {
                    // 3-1. 반시계 방향으로 회전
                	robot.changeDirection();
                    // 3-2.바라보는 방향 앞 칸이 청소되지 않았다면 한 칸 전진
                	if (room[robot.row + getDelta()[0]][robot.col + getDelta()[1]] == DIRTY) {
                		robot.moveFront();
                		break;
                	}
                }
            }
        }

        private void moveFront() {
            row += deltas[direction][0];
            col += deltas[direction][1];
        }

        private void moveBack() {
            row += getBackDelta()[0];
            col += getBackDelta()[1];
        }

        private void changeDirection() {
            direction -= 1;
            if (direction == -1)
                direction = 3;
        }
        
        private int[] getDelta() {
        	return deltas[direction];
        }

        private int[] getBackDelta() {
            return deltas[(direction + 2) % 4];
        }
    }

    public static void main(String[] args) throws IOException {
        //입력
        st = new StringTokenizer(br.readLine().trim());
        roomRow = Integer.parseInt(st.nextToken());
        roomCol = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        robot = new Robot(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()));
        room = new int[roomRow][roomCol];
        for (int row = 0; row < roomRow; row++) {
            room[row] = Stream.of(br.readLine().trim().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        //로직
        robot.run();

        //출력
        System.out.println(cleaingRoomCnt);
    }
}
