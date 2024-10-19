/*
= BOJ 17951. 흩날리는 시험지 속에서 내 평점이 느껴진거야
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int paperSize, groupSize, maxScore;
    static int[] paper;

    public static int binarySearch(int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;

            if (calGroupCnt(mid) >= groupSize)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return right;
    }

    public static int calGroupCnt(int threshold) {
        int groupCnt = 0;
        int score = 0;

        for (int idx = 0; idx < paperSize; idx++) {
            if ((score += paper[idx]) >= threshold) {
                groupCnt++;
                score = 0;
            }
        }

        return groupCnt;
    }

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(binarySearch(1, maxScore));
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        paperSize = Integer.parseInt(st.nextToken());
        groupSize = Integer.parseInt(st.nextToken());

        paper = new int[paperSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < paperSize; idx++)
            maxScore += paper[idx] = Integer.parseInt(st.nextToken());
    }
}
