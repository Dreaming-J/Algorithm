/*
= BOJ 9466. 텀 프로젝트

= 로직
0. 테스트 케이스의 개수 입력
1. 초기 세팅
    1-1. 학생의 수 입력
    1-2. 선택한 학생의 번호 입력
    1-3. 변수 초기화
2. 팀 짜기
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int UNKOWN = 0, FAIL = -1;
    static int studentSize;
    static int[] chooseStudent;
    static boolean[] visited;
    static int[] makeTeam;

    public static int findProjectTeam(int studentIdx) {
        if (visited[studentIdx]) {
            if (makeTeam[studentIdx] == UNKOWN)
                return makeTeam[studentIdx] = studentIdx;
            else
                return FAIL;
        }

        visited[studentIdx] = true;

        int result = findProjectTeam(chooseStudent[studentIdx]);
        if (makeTeam[studentIdx] == UNKOWN)
            makeTeam[studentIdx] = result == studentIdx ? FAIL : result;

        return result == studentIdx ? FAIL : result;
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스의 개수 입력
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 0; tc < testCase; tc++) {
            //1. 초기 세팅
            init();

            //2. 팀 짜기
            for (int studentIdx = 1; studentIdx <= studentSize; studentIdx++) {
                if (makeTeam[studentIdx] != UNKOWN)
                    continue;

                findProjectTeam(studentIdx);
            }

            //3. 출력
            int count = 0;
            for (int studentIdx = 1; studentIdx <= studentSize; studentIdx++) {
                count += makeTeam[studentIdx] == FAIL ? 1 : 0;
            }
            output.append(count).append("\n");
        }
        System.out.print(output);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 학생의 수 입력
        studentSize = Integer.parseInt(input.readLine());

        //1-2. 선택한 학생의 번호 입력
        chooseStudent = new int[studentSize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= studentSize; idx++) {
            chooseStudent[idx] = Integer.parseInt(st.nextToken());
        }

        //1-3. 변수 초기화
        visited = new boolean[studentSize + 1];
        makeTeam = new int[studentSize + 1];
    }
}
