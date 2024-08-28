/*
= BOJ 12904. A와 B

=특이사항
S에서 T가 아닌 T에서 S가 가능한지 판단

= 로직
1. 초기 세팅
    1-1. 타겟 문자열 입력
    1-2. 시작 문자열 입력
2. 타겟 문자열의 길이가 같을 때까지 역연산
    2-1. 마지막 글자가 B라면, B 삭제 후 뒤집기
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static String dest;
    static StringBuilder src;

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        initInput();

        //2. 타겟 문자열의 길이가 같을 때까지 역연산
        while (src.length() > dest.length()) {
            char lastChar = src.charAt(src.length() - 1);
            src.deleteCharAt(src.length() - 1);

            //2-1. 마지막 글자가 B라면, B 삭제 후 뒤집기
            if (lastChar == 'B')
                src.reverse();
        }

        //3. 출력
        System.out.println(src.toString().equals(dest) ? 1 : 0);
    }

    public static void initInput() throws IOException {
        //1-1. 타겟 문자열 입력
        dest = input.readLine();

        //1-2. 시작 문자열 입력
        src = new StringBuilder(input.readLine());
    }
}