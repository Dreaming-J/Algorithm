/*
= BOJ 1655. 가운데를 말해요
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static int numberSize;
    static PriorityQueue<Integer> smallQ, bigQ;

    public static void main(String[] args) throws IOException {
        init();

        for (int idx = 0; idx < numberSize; idx++) {
            int num = Integer.parseInt(input.readLine());

            if (smallQ.isEmpty())
                smallQ.add(num);
            else if (bigQ.isEmpty()) {
                if (smallQ.peek() > num) {
                    bigQ.add(smallQ.poll());
                    smallQ.add(num);
                } else
                    bigQ.add(num);
            } else {
                smallQ.add(num);

                if (smallQ.peek() > bigQ.peek()) {
                    bigQ.add(smallQ.poll());
                    if (bigQ.size() > smallQ.size())
                        smallQ.add(bigQ.poll());
                }

                if (smallQ.size() > bigQ.size() + 1)
                    bigQ.add(smallQ.poll());
            }

            output.append(smallQ.peek())
                    .append("\n");
        }

        System.out.println(output);
    }

    public static void init() throws IOException {
        numberSize = Integer.parseInt(input.readLine());

        smallQ = new PriorityQueue<>(Collections.reverseOrder());
        bigQ = new PriorityQueue<>();
    }
}