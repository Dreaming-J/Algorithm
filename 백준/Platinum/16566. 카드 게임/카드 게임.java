/*
= BOJ 16566. 카드 게임
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int cardSize, handSize, roundSize;
    static int[] hands, enemyOrder;
    static int[] parents;

    public static void main(String[] args) throws IOException {
        init();

        for (int idx = 0; idx < roundSize; idx++) {
            int handIdx = searchHand(enemyOrder[idx]);

            output.append(hands[handIdx])
                    .append("\n");

            parents[handIdx] = handIdx + 1;
        }

        System.out.println(output);
    }

    private static int searchHand(int enemyHand) {
        int left = 0;
        int right = handSize;

        int mid;
        while (left < right) {
            mid = (left + right) / 2;

            if (hands[mid] > enemyHand)
                right = mid;
            else
                left = mid + 1;
        }

        return find(right);
    }

    private static int find(int element) {
        if (parents[element] == element)
            return element;

        return parents[element] = find(parents[element]);
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        cardSize = Integer.parseInt(st.nextToken());
        handSize = Integer.parseInt(st.nextToken());
        roundSize = Integer.parseInt(st.nextToken());

        hands = new int[handSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < handSize; idx++)
            hands[idx] = Integer.parseInt(st.nextToken());

        enemyOrder = new int[roundSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < roundSize; idx++)
            enemyOrder[idx] = Integer.parseInt(st.nextToken());

        Arrays.sort(hands);

        parents = new int[handSize];
        Arrays.setAll(parents, idx -> idx);
    }
}