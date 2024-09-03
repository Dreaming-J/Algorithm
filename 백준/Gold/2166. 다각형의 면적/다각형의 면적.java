/*
= BOJ 2166. 다각형의 면적

= 특이사항
신발끈 공식

= 로직
1. 초기 세팅
	1-1. 점의 개수 입력
	1-2. 점의 좌표 입력
2. 신발끈 공식을 이용해 넓이 계산
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int pointSize;
    static long[][] points;

    public static double findArea() {
        double area = 0L;

        for (int idx = 0; idx < pointSize; idx++) {
            area += points[idx][0] * points[(idx + 1) % pointSize][1];
            area -= points[(idx + 1) % pointSize][0] * points[idx][1];
        }

        return Math.abs(area) / 2;
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 신발끈 공식을 이용해 넓이 계산
        //3. 출력
        System.out.printf("%.1f", findArea());
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 점의 개수 입력
        pointSize = Integer.parseInt(input.readLine());

        //1-2. 점의 좌표 입력
        points = new long[pointSize + 1][2];
        for (int idx = 0; idx < pointSize; idx++) {
            st = new StringTokenizer(input.readLine());
            points[idx] = new long[]{Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())};
        }
    }
}
