/*
#SWEA 19113. 식료품 가게
N개의 물건의 판매 가격과 25% 할인 가격을 모두 합쳐 오름차순 정렬을 하였을 때, 할인가격표를 구하여랴.
할인가격표도 오름차순으로 정렬하며, 물건의 판매 가격은 4의 배수로 할인가격표도 정수이다.

#입력
첫째 줄: 테스트 케이스의 수 T
    =각 테스트 케이스
    첫번째 줄: N (1 <= N <= 100)
    두번째 줄: 2N개의 정수, 오름차순 정렬 (1 ~ 10^9의 값)
#출력
"#Ti [오름차순으로 정렬된 물건의 할인가격 N개]"

#로직
시작 값은 언제나 할인 가격이며, 할인가격에 매칭되는 정가를 만나기 전에의 값은 모두 할인 가격이다.
이 점을 활용하여 queue를 이용하여 정가와 할인 가격을 매칭시킨다.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Solution {

    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        int testCases;
        int count;
        StringTokenizer mixedPrice;
        Deque<Long> queue;
        Long price;

        testCases = Integer.parseInt(input.readLine());
        for (int test_case = 1; test_case <= testCases; test_case++) {
            output.write("#" + test_case);

            //입력 및 초기화
            count = Integer.parseInt(input.readLine());
            mixedPrice = new StringTokenizer(input.readLine().trim());
            queue = new ArrayDeque<>();

            //로직
            for (int i = 0; i < count * 2; i++) {
                price = Long.parseLong(mixedPrice.nextToken());

                if (queue.isEmpty() || price < queue.peek() * 4 / 3) {
                    queue.add(price);
                    continue;
                }

                output.write(" " + queue.poll());
            }

            output.write("\n");
        }

        output.flush();
        output.close();
    }
}