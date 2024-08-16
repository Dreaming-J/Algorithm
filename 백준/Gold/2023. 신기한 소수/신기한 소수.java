/*
#백준 2023. 신기한 소수

1. 신기한 소수의 자리 수를 입력받는다.
2. {2, 3, 5, 7}로 시작하는 신기한 소수를 중복 순열을 이용해 찾는다.
3. 해당 숫자가 소수인지 판단하여, 소수만 중복 순열을 순회하도록 한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static StringBuilder output = new StringBuilder();
    static int digits;
    static final int[] NthDigit = {2, 3, 5, 7};
    static final int[] canDigits = {1, 3, 7, 9};

    public static boolean isPrime(int number) {
        for (int num = 2; num <= Math.sqrt(number); num++) {
            if (number % num == 0)
                return false;
        }

        return true;
    }

    public static void findAmazingPrime(int depth, int number) {
        if (depth == digits) {
            output.append(number).append("\n");
        }

        for (int firstDigit = 1; firstDigit <= 9; firstDigit += 2) {
            int newNumber = number * 10 + firstDigit;
            //3. 해당 숫자가 소수인지 판단하여, 소수만 중복 순열을 순회하도록 한다.
            if (isPrime(newNumber))
                findAmazingPrime(depth + 1, newNumber);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        //1. 신기한 소수의 자리 수를 입력받는다.
        digits = Integer.parseInt(input.readLine());

        //2. {2, 3, 5, 7}로 시작하는 신기한 소수를 중복 순열을 이용해 찾는다.
        for (int num : NthDigit) {
            findAmazingPrime(1, num);
        }

        System.out.println(output.toString());
    }
}
