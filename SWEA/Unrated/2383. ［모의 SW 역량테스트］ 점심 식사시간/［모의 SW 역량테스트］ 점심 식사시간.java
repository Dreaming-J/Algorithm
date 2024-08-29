/*
=SWEA 2383. [모의 SW 역량테스트] 점심 식사시간

=특이사항
계단 2개 고정
계단에 도착하고 1분 뒤부터 내려가기 시작
시작한 시간 기준으로 계단 시간만큼 추가로 지나야 통과

=로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
	1-1. 방의 길이 입력
	1-2. 방의 길이만큼 방의 정보 입력
		1-2-1. 사람의 좌표 보관
		1-2-2. 계단의 좌표 보관
	1-3. 변수 초기화
2. 사람과 계단 사이의 거리 계산
3. 사람마다 계단 고르는 모든 조합 생성
4. 출력
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
    static final int MAX_PEOPLE_SIZE = 10, STAIR_SIZE = 2;
    static final int PERSON = 1, MIN_STAIR_LENGTH = 2;
    static final int FIRST_STAIR = 0, SECOND_STAIR = 1;
    static final int ARRIVAL = 0, MAX_STAIR_USED_SIZE = 3;
    static int testCase;
    static int roomSize;
    static Point[] people, stairs;
    static int peopleSize, stairSize;
    static int[][] distance;
    static int[] selectStair;
    static int minTime;

    public static class Point {
        int row;
        int col;
        int time;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Point(int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
    }

    public static class Person implements Comparable<Person> {
        int number;
        int time;
        int selectStair;
        int version;

        public Person(int number, int time, int selectStair) {
            this.number = number;
            this.time = time;
            this.selectStair = selectStair;
        }

        @Override
        public int compareTo(Person o) {
            return this.version == o.version ? this.time - o.time : this.version - o.version;
        }
    }

    public static void calDistance() {
        for (int peopleIdx = 0; peopleIdx < peopleSize; peopleIdx++) {
            for (int stairIdx = 0; stairIdx < stairSize; stairIdx++) {
                Point person = people[peopleIdx];
                Point stair = stairs[stairIdx];

                distance[peopleIdx][stairIdx] = Math.abs(person.row - stair.row) + Math.abs(person.col - stair.col);
            }
        }
    }

    public static void chooseStair(int personIdx) {
        if (personIdx == peopleSize) {
            minTime = Math.min(play(), minTime);
            return;
        }

        selectStair[personIdx] = FIRST_STAIR;
        chooseStair(personIdx + 1);
        selectStair[personIdx] = SECOND_STAIR;
        chooseStair(personIdx + 1);
    }

    public static int play() {
        int time = 0;
        int[] stairCount = new int[STAIR_SIZE];
        PriorityQueue<Person> queue = new PriorityQueue<>();

        for (int personIdx = 0; personIdx < peopleSize; personIdx++) {
            queue.add(new Person(personIdx, distance[personIdx][selectStair[personIdx]], selectStair[personIdx]));
        }

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                Person cur = queue.poll();

                //계단에 도착했을 경우
                if (cur.time == ARRIVAL) {
                    //해당 계단에 3명 이하로 이용 중이라면 탑승
                    if (stairCount[cur.selectStair] < MAX_STAIR_USED_SIZE) {
                        stairCount[cur.selectStair]++;
                    }

                    //아니라면 대기
                    else {
                        cur.time++;
                    }
                }

                cur.time--;

                //계단을 다 내려왔을 경우
                if (cur.time == -(stairs[cur.selectStair].time + 1)) {
                    stairCount[cur.selectStair]--;
                } else {
                    cur.version++;
                    queue.add(cur);
                }
            }

            time++;
        }

        return time;
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 개수 입력
        testCase = Integer.parseInt(input.readLine().trim());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();

            //2. 사람과 계단 사이의 거리 계산
            calDistance();

            //3. 사람마다 계단 고르는 모든 조합 생성
            chooseStair(0);

            //4. 출력
            output.append("#").append(tc).append(" ").append(minTime).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        //1-1. 방의 길이 입력
        roomSize = Integer.parseInt(input.readLine().trim());

        //1-2. 방의 길이만큼 방의 정보 입력
        people = new Point[MAX_PEOPLE_SIZE];
        stairs = new Point[STAIR_SIZE];
        peopleSize = 0;
        stairSize = 0;
        for (int row = 0; row < roomSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < roomSize; col++) {
                int cell = Integer.parseInt(st.nextToken());

                //1-2-1. 사람의 좌표 보관
                if (cell == PERSON)
                    people[peopleSize++] = new Point(row, col);

                //1-2-2. 계단의 좌표 보관
                if (cell >= MIN_STAIR_LENGTH) {
                    stairs[stairSize++] = new Point(row, col, cell);
                }
            }
        }

        //1-3. 변수 초기화
        distance = new int[peopleSize][stairSize];
        selectStair = new int[peopleSize];
        minTime = Integer.MAX_VALUE;
    }
}