/*
= BOJ 1086. 박성원

- 참고사항
비트마스킹을 활용한 재귀
입력받는 숫자로 만들어지는 큰 정수 하나의 길이를 구한 후, 나머지를 기준으로 vsiited를 구분한다.
외판원 순회에서 사용한 비트마스킹 알고리즘을 사용할 예정
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Main {
    private static final int NOT_VISITED = -1;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int numberSize;
    static String[] numbers;
    static int denominator;
    static long[][] countMemo;
    static Map<String, int[]> restMemo;
    static long answerCount, totalCount;

    public static void main(String[] args) throws IOException {
        init();

        //총 경우의 수 계산
        totalCount = factorial(numberSize);

        //denominator로 나누어 떨어지는 경우의 수 계산
        answerCount = find(0, 0);

        //기약분수로 변경
        long gcd = calculateGCD(totalCount, answerCount);
        totalCount /= gcd;
        answerCount /= gcd;

        printAnswer();
    }

    private static long factorial(int num) {
        if (num == 1)
            return 1;

        return num * factorial(num - 1);
    }

    private static long find(int rest, int visitedBit) {
        //모든 곳을 방문했을 경우
        if (visitedBit == (1 << numberSize) - 1)
            return countMemo[rest][visitedBit] = rest == 0 ? 1L : 0L;

        //남은 숫자들의 경우의 수를 이미 알고 있다면, 해당 값 반환
        if (countMemo[rest][visitedBit] != NOT_VISITED)
            return countMemo[rest][visitedBit];

        //다음 숫자 확인
        countMemo[rest][visitedBit] = 0;
        for (int idx = 0; idx < numberSize; idx++) {
            if ((visitedBit & 1 << idx) != 0)
                continue;

            countMemo[rest][visitedBit] += find(calculateRest(rest, numbers[idx]), visitedBit | 1 << idx);
        }

        return countMemo[rest][visitedBit];
    }
    
    private static int calculateRest(int rest, String number) {
        if (!restMemo.containsKey(number)) {
            int[] rests = new int[denominator];
            Arrays.fill(rests, NOT_VISITED);
            restMemo.put(number, rests);
        }
        else if (restMemo.get(number)[rest] != NOT_VISITED)
            return restMemo.get(number)[rest];
        
        int newRest = rest;
        for (int idx = 0; idx < number.length(); idx++) {
            int num = number.charAt(idx) - '0';
            newRest = newRest * 10 + num;
            newRest %= denominator;
        }
        
        return restMemo.get(number)[rest] = newRest;
    }

    private static long calculateGCD(long num1, long num2) {
        if (num2 == 0)
            return num1;

        return calculateGCD(num2, num1 % num2);
    }

    private static void printAnswer() {
        if (answerCount == 0)
            System.out.println("0/1");
        else
            System.out.println(answerCount + "/" + totalCount);
    }

    private static void init() throws IOException {
        numberSize = Integer.parseInt(input.readLine());

        numbers = new String[numberSize];
        for (int idx = 0; idx < numberSize; idx++)
            numbers[idx] = input.readLine();

        denominator = Integer.parseInt(input.readLine());

        countMemo = new long[denominator][1 << numberSize];
        for (long[] rows : countMemo)
            Arrays.fill(rows, NOT_VISITED);
        
        restMemo = new HashMap<>();
    }
}