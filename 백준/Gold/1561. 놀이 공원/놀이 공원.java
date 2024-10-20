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
    static int peopleSize, attractionSize;
    static long maxTime;
    static long[] attractions;

    public static long binarySearch(long left, long right) {
        while (left < right) {
            long mid = (left + right) / 2;

            if (peopleCount(mid) < peopleSize)
                left = mid + 1;
            else
                right = mid;
        }

        return left;
    }

    public static long peopleCount(long time) {
        long cnt = attractionSize;

        for (int idx = 1; idx <= attractionSize; idx++)
            cnt += time / attractions[idx];

        return cnt;
    }

    public static int findLastAttraction(long time) {
        if (time == 1)
            return peopleSize;

        int attractionidx = 1;
        for (long cnt = peopleSize - peopleCount(time - 1); attractionidx <= attractionSize; attractionidx++) {
            cnt -= time % attractions[attractionidx] == 0 ? 1 : 0;

            if (cnt == 0)
                break;
        }

        return attractionidx;
    }

    public static void main(String[] args) throws IOException {
        init();

        long lastTime = binarySearch(1, maxTime);

        System.out.println(findLastAttraction(lastTime));
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        peopleSize = Integer.parseInt(st.nextToken());
        attractionSize = Integer.parseInt(st.nextToken());

        attractions = new long[attractionSize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= attractionSize; idx++) {
            attractions[idx] = Long.parseLong(st.nextToken());
            maxTime = Math.max(attractions[idx], maxTime);
        }

        maxTime *= peopleSize;
    }
}
