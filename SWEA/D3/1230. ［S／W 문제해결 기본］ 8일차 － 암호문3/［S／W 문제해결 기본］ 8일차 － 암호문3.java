/*
= SWEA 1230. [S/W 문제해결 기본] 8일차 - 암호문3

= 특이사항
테스트케이스 10개 고정

= 로직
1. 원본 암호문 뭉치의 개수 입력
2. 원본 암호문 뭉치 입력
3. 명령어의 개수 입력
    3-1. 삽입 명령
    3-2. 삭제 명령
    3-3. 추가 명령
4. 명렁어 입력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int Test_CASE = 10, PRINT_SIZE = 10;
    static int pwSize, cmdSize;
    static List<String> pwBunch;

    public static void insert() {
        int startIdx = Integer.parseInt(st.nextToken());
        int size = Integer.parseInt(st.nextToken());

        for (int idx = 0; idx < size; idx++) {
            pwBunch.add(idx + startIdx, st.nextToken());
        }
    }

    public static void delete() {
        int startIdx = Integer.parseInt(st.nextToken());
        int size = Integer.parseInt(st.nextToken());

        for (int idx = 0; idx < size; idx++) {
            pwBunch.remove(startIdx);
        }
    }

    public static void append() {
        int size = Integer.parseInt(st.nextToken());

        for (int idx = 0; idx < size; idx++) {
            pwBunch.add(st.nextToken());
        }
    }

    public static void main(String[] args) throws IOException {
        for (int tc = 1; tc <= Test_CASE; tc++) {
            //1. 원본 암호문 뭉치의 개수 입력
            pwSize = Integer.parseInt(input.readLine().trim());

            //2. 원본 암호문 뭉치 입력
            pwBunch = new LinkedList<>();
            st = new StringTokenizer(input.readLine().trim());
            for (int idx = 0; idx < pwSize; idx++) {
                pwBunch.add(st.nextToken());
            }

            //3. 명령어의 개수 입력
            cmdSize = Integer.parseInt(input.readLine().trim());
            st = new StringTokenizer(input.readLine().trim());
            for (int idx = 0; idx < cmdSize; idx++) {
                String cmd = st.nextToken();

                //3-1. 삽입 명령
                if (cmd.equals("I"))
                    insert();

                    //3-2. 삭제 명령
                else if (cmd.equals("D"))
                    delete();

                    //3-3. 추가 명령
                else if (cmd.equals("A"))
                    append();
            }

            //4. 출력
            output.append("#").append(tc).append(" ");
            for (int idx = 0; idx < PRINT_SIZE; idx++) {
                output.append(pwBunch.get(idx)).append(" ");
            }
            output.append("\n");
        }
        System.out.println(output);
    }
}
