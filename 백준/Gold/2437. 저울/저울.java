/*
= BOJ 14907. 프로젝트 스케줄링

= 로직
1. 초기 세팅
    1-1. 무게추 개수 입력
    1-2. 무게추의 무게 입력
    1-3. 변수 초기화
2. 병합 정렬
3. 측정 불가능한 무게 찾기
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int weightSize;
    static int[] weights, temp;
    static int measurableWeight;

    public static void merge(int left, int right) {
        int mid = (left + right) / 2;
        int leftIdx = left;
        int rightIdx = mid;

        for (int idx = left; idx < right; idx++) {
            if (leftIdx == mid)
                temp[idx] = weights[rightIdx++];

            else if (rightIdx == right)
                temp[idx] = weights[leftIdx++];

            else if (weights[leftIdx] <= weights[rightIdx])
                temp[idx] = weights[leftIdx++];

            else
                temp[idx] = weights[rightIdx++];
        }

        for (int idx = left; idx < right; idx++) {
            weights[idx] = temp[idx];
        }
    }

    public static void mergeSort(int left, int right) {
        if (left == right - 1)
            return;

        int mid = (left + right) / 2;
        mergeSort(left, mid);
        mergeSort(mid, right);

        merge(left, right);
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 병합 정렬
        mergeSort(0, weightSize);

        //3. 측정 불가능한 무게 찾기
        for (int weight : weights) {
            if (measurableWeight < weight)
                break;

            measurableWeight += weight;
        }

        //4. 출력
        System.out.print(measurableWeight);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 무게추 개수 입력
        weightSize = Integer.parseInt(input.readLine());

        //1-2. 무게추의 무게 입력
        weights = new int[weightSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < weightSize; idx++) {
            weights[idx] = Integer.parseInt(st.nextToken());
        }

        //1-3. 변수 초기화
        temp = new int[weightSize];
        measurableWeight = 1;
    }
}
