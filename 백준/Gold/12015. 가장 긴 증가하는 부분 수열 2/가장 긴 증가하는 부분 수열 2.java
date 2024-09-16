/*
= BOJ 12015. 가장 긴 증가하는 부분 수열 2

= 로직
1. 초기 세팅
	1-1. 수열의 크기
	1-2. 수열 입력
	1-3. 변수 초기화
2. 부분 수열 찾기
3. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int numberSize;
    static int[] numbers, lis;
    static int length;

    public static int binarySearch(int left, int right, int target) {
        while (left < right) {
            int mid = (left + right) / 2;

            if (lis[mid] < target)
                left = mid + 1;
            else {
                right = mid;
            }
        }

        return right;
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        initTestCase();

        //2. 부분 수열 찾기
        for (int idx = 1; idx <= numberSize; idx++) {
            if (lis[length] < numbers[idx])
                lis[++length] = numbers[idx];
            else
                lis[binarySearch(1, length, numbers[idx])] = numbers[idx];
        }

        //3. 출력
        System.out.print(length);
    }

    //1. 초기 세팅
    public static void initTestCase() throws IOException {
        //1-1. 수열의 크기
        numberSize = Integer.parseInt(input.readLine());

        //1-2. 수열 입력
        numbers = new int[numberSize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= numberSize; idx++) {
            numbers[idx] = Integer.parseInt(st.nextToken());
        }

        //1-3. 변수 초기화
        lis = new int[numberSize + 1];
        length = 0;
    }
}