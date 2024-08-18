/*
= SWEA 2115. [모의 SW 역량테스트] 벌꿀채취

1. 테스트 케이스 입력
2. 벌통의 크기, 벌통의 개수, 꿀 채취 최대 양 입력
3. 벌통의 크기만큼 꿀의 정보 입력
4. 변수 초기화
5. 특정 좌표에서의 최대 수익을 미리 계산
    5-1. 꿀 선택량이 채취 최대 양보다 크다면 탐색 종료
    5-2. 벌통의 개수만큼 탐색했다면 탐색 종료
    5-3. 다음 부분집합 탐색
6. 두 일꾼의 좌표를 조합을 통해 선택
    6-1. 일꾼 2명 모두 작업 범위를 선택했다면, 탐색 종료
        6-1-1. 일꾼의 작업 범위가 겹친다면, 탐색 종료
        6-1-2. 2명의 작업 범위에서 얻을 수 있는 수익을 계산
    6-2. 범위를 벗어났다면 탐색 종료
    6-3. 다음 조합 선택
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int WORKER_COUNT = 2;
    static int testCase;
    static int hiveSize, boxSize, maxHoneyGetAmount;
    static int[][] hive;
    static int[][] maxAvenueMap;
    static int maxAvenue;
    static int[][] workerPosition;

    public static void initTestCase() throws IOException {
        //2. 벌통의 크기, 벌통의 개수, 꿀 채취 최대 양 입력
        st = new StringTokenizer(input.readLine().trim());
        hiveSize = Integer.parseInt(st.nextToken());
        boxSize = Integer.parseInt(st.nextToken()) - 1;
        maxHoneyGetAmount = Integer.parseInt(st.nextToken());

        //3. 벌통의 크기만큼 꿀의 정보 입력
        hive = new int[hiveSize][hiveSize];
        for (int row = 0; row < hiveSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < hiveSize; col++) {
                hive[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        //4. 변수 초기화
        maxAvenueMap = new int[hiveSize][hiveSize - boxSize];
        maxAvenue = Integer.MIN_VALUE;
        workerPosition = new int[WORKER_COUNT][2];
    }

    public static void findMaxAvenuePerCell(int selectIdx, int row, int col, int honeyGetCount, int avenue) {
        //5-1. 꿀 선택량이 채취 최대 양보다 크다면 탐색 종료
        if (honeyGetCount > maxHoneyGetAmount)
            return;

        //5-2. 벌통의 개수만큼 탐색했다면 탐색 종료
        if (selectIdx == boxSize + 1) {
            maxAvenueMap[row][col] = Math.max(avenue, maxAvenueMap[row][col]);
            return;
        }

        //5-3. 다음 부분집합 탐색
        findMaxAvenuePerCell(selectIdx + 1, row, col, honeyGetCount + hive[row][col + selectIdx],
                avenue + hive[row][col + selectIdx] * hive[row][col + selectIdx]);
        findMaxAvenuePerCell(selectIdx + 1, row, col, honeyGetCount, avenue);
    }

    public static void findWorkersPosition(int rowIdx, int colIdx, int workerIdx) {
        //6-1. 일꾼 2명 모두 작업 범위를 선택했다면, 탐색 종료
        if (workerIdx == 2) {
            //6-1-1. 일꾼의 작업 범위가 겹친다면, 탐색 종료
            if (workerPosition[0][0] == workerPosition[1][0]) {
                if (workerPosition[0][1] <= workerPosition[1][1]
                        && workerPosition[1][1] <= workerPosition[0][1] + boxSize)
                    return;
                if (workerPosition[1][1] <= workerPosition[0][1]
                        && workerPosition[0][1] <= workerPosition[1][1] + boxSize)
                    return;
            }

            //6-1-2. 2명의 작업 범위에서 얻을 수 있는 수익을 계산
            int curMaxAvenue = maxAvenueMap[workerPosition[0][0]][workerPosition[0][1]]
                    + maxAvenueMap[workerPosition[1][0]][workerPosition[1][1]];
            maxAvenue = Math.max(curMaxAvenue, maxAvenue);

            return;
        }

        //6-2. 범위를 벗어났다면 탐색 종료
        if (!canGo(rowIdx, colIdx))
            return;

        //6-3. 다음 조합 선택
        workerPosition[workerIdx][0] = rowIdx;
        workerPosition[workerIdx][1] = colIdx;

        int nextRow = rowIdx + (colIdx + 1 == hiveSize - boxSize ? 1 : 0);
        int nextCol = colIdx + 1 == hiveSize - boxSize ? 0 : colIdx + 1;
        findWorkersPosition(nextRow, nextCol, workerIdx + 1);
        findWorkersPosition(nextRow, nextCol, workerIdx);
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < hiveSize && col >= 0 && col < hiveSize;
    }

    public static void main(String[] args) throws IOException {
        //1. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            initTestCase();

            //5. 특정 좌표에서의 최대 수익을 미리 계산
            for (int row = 0; row < hiveSize; row++) {
                for (int col = 0; col < hiveSize - boxSize; col++) {
                    findMaxAvenuePerCell(0, row, col, 0, 0);
                }
            }

            //6. 두 일꾼의 좌표를 조합을 통해 선택
            findWorkersPosition(0, 0, 0);

            output.append("#").append(tc).append(" ").append(maxAvenue).append("\n");
        }
        System.out.println(output);
    }
}