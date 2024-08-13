/*
=SWEA 2001. 파리 퇴치

0. 테스트 케이스 개수를 입력 받는다.
	1. 배열의 크기 N과 파리채의 크기 M을 입력받는다.
	2. 배열을 입력받는다.
	3. 2차원 누적합을 계산한다.
		array[n][m] = array[n - 1][m] + array[n][m - 1] - array[n - 1][m - 1]
		3-1. 행이 0이 아니면, array[n - 1][m]를 더한다.
		3-2. 열이 0이 아니면, array[n][m - 1]를 더한다.
		3-3. 행과 열이 0이 아니면, array[n - 1][m - 1]를 뺀다.

	4. M크기의 누적합을 계산하여 최대값을 계산한다.
		array[i][j] - array[i - M][j] - array[i][j - M] + array[i - M][j - M]
		4-1. 행이 0이 아니면, array[i - M][j]를 뺀다.
		4-2. 열이 0이 아니면, array[i][j - M]를 뺀다.
		4-3. 행과 열이 0이 아니면, array[i - M][j - M]를 더한다.

	5. 구한 최대값을 출력한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int testCase;
    static int boardSize, flapperSize;
    static int[][] board; //누적합을 보관하는 배열
    static int maxCatchFly;

    public static void main(String[] args) throws IOException {
        testCase = Integer.parseInt(input.readLine());
        for (int tc = 1; tc <= testCase; tc++) {
            //1. 배열의 크기 N과 파리채의 크기 M을 입력받는다.
            st = new StringTokenizer(input.readLine().trim());
            boardSize = Integer.parseInt(st.nextToken());
            flapperSize = Integer.parseInt(st.nextToken());

            //2. 배열을 입력받는다.
            board = new int[boardSize][boardSize];
            for (int row = 0; row < boardSize; row++) {
                st = new StringTokenizer(input.readLine().trim());
                for (int col = 0; col < boardSize; col++) {
                    board[row][col] = Integer.parseInt(st.nextToken());
                    //3. 2차원 누적합을 계산한다.
                    //3-1. 행이 0이 아니면, array[n - 1][m]를 더한다.
                    if (row != 0)
                        board[row][col] += board[row - 1][col];
                    //3-2. 열이 0이 아니면, array[n][m - 1]를 더한다.
                    if (col != 0)
                        board[row][col] += board[row][col - 1];
                    //3-3. 행과 열이 0이 아니면, array[n - 1][m - 1]를 뺀다.
                    if (row != 0 && col != 0)
                        board[row][col] -= board[row - 1][col - 1];
                }
            }

            //4. M크기의 누적합을 계산하여 최대값을 계산한다.
            //array[i][j] - array[i - M][j] - array[i][j - M] + array[i - M][j - M]
            maxCatchFly = Integer.MIN_VALUE;
            for (int row = flapperSize - 1; row < boardSize; row++) {
                for (int col = flapperSize - 1; col < boardSize; col++) {
                    int targetRow = row - flapperSize;
                    int targetCol = col - flapperSize;

                    int prefixSum = board[row][col];
                    //4-1. 행이 0이 아니면, array[i - M][j]를 뺀다.
                    if (targetRow >= 0)
                        prefixSum -= board[targetRow][col];
                    //4-2. 열이 0이 아니면, array[i][j - M]를 뺀다.
                    if (targetCol >= 0)
                        prefixSum -= board[row][targetCol];
                    //4-3. 행과 열이 0이 아니면, array[i - M][j - M]를 더한다.
                    if (targetRow >= 0 && targetCol >= 0)
                        prefixSum += board[targetRow][targetCol];
                    if (prefixSum > maxCatchFly)
                        maxCatchFly = prefixSum;
                }
            }

            //5. 구한 최솟값을 출력한다.
            output.append("#").append(tc).append(" ").append(maxCatchFly).append("\n");
        }

        System.out.println(output);
    }
}
