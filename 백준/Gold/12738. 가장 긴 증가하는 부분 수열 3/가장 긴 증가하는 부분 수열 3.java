/*
= BOJ 12738. 가장 긴 증가하는 부분 수열 3
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int arraySize;
    static int[] array;
    static int lisSize;
    static Number[] lisArray;

    static class Number {
        int num, idx;

        public Number(int num, int idx) {
            this.num = num;
            this.idx = idx;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findLIS();

        System.out.println(lisSize);
    }

    private static void findLIS() {
        for (int idx = 1; idx <= arraySize; idx++) {
            int newIdx = lisArray[lisSize].num < array[idx] ? ++lisSize : binarySearch(1, lisSize, array[idx]);

            lisArray[newIdx] = new Number(array[idx], idx);
        }
    }

    private static int binarySearch(int left, int right, int target) {
        while (left < right) {
            int mid = (left + right) / 2;

            if (lisArray[mid].num < target)
                left = mid + 1;
            else
                right = mid;
        }

        return right;
    }

    private static void init() throws IOException {
        arraySize = Integer.parseInt(input.readLine());

        array = new int[arraySize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= arraySize; idx++)
            array[idx] = Integer.parseInt(st.nextToken());

        lisArray = new Number[arraySize + 1];
        lisArray[0] = new Number(Integer.MIN_VALUE, 0);
    }
}
