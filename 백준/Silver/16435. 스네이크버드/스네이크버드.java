/*
=BOJ 16435. 스네이크버드

1. 과일의 개수, 스네이크버드의 초기 길이 입력
2. 과일의 개수만큼 과일의 높이 입력
3. 과일의 높이 정렬
4. 과일을 먹을 수 있는 만큼 먹기
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int fruitSize;
    static int snakeBird;
    static int[] fruits;
    
    public static void main(String[] args) throws IOException {
        //1. 과일의 개수, 스네이크버드의 초기 길이 입력
        st = new StringTokenizer(input.readLine().trim());
        fruitSize = Integer.parseInt(st.nextToken());
        snakeBird = Integer.parseInt(st.nextToken());
        
        //2. 과일의 개수만큼 과일의 높이 입력
        fruits = new int[fruitSize];
        st = new StringTokenizer(input.readLine().trim());
        for (int idx = 0; idx < fruitSize; idx++) {
            fruits[idx] = Integer.parseInt(st.nextToken());
        }
        
        //3. 과일의 높이 정렬
        Arrays.sort(fruits);
        
        //4. 과일을 먹을 수 있는 만큼 먹기
        for (int idx = 0; idx < fruitSize; idx++) {
            if (snakeBird < fruits[idx])
                break;
            snakeBird++;
        }

        System.out.println(snakeBird);
    }
}
