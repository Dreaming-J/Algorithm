/*
= BOJ 12865. 평범한 배낭

= 로직
1. 초기 세팅
    1-1. 물품의 수와 제한 무게 입력
    1-2. 물건의 무게와 가치 입력
    1-3. 변수 초기화
2. 최소 비용 계산
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int itemSize, limitWeight;
    static Item[] items;
    static int[] bestValue;

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 최소 비용 계산
        for (Item item : items) {
            for (int weight = limitWeight; weight >= item.weight; weight--) {
                bestValue[weight] = Math.max(bestValue[weight - item.weight] + item.value, bestValue[weight]);
            }
        }

        //3. 출력
        System.out.println(bestValue[limitWeight]);
    }

    public static class Item {
        int weight, value;

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    public static void init() throws IOException {
        //1-1. 물품의 수와 제한 무게 입력
        st = new StringTokenizer(input.readLine());
        itemSize = Integer.parseInt(st.nextToken());
        limitWeight = Integer.parseInt(st.nextToken());

        //1-2. 물건의 무게와 가치 입력
        items = new Item[itemSize];
        for (int idx = 0; idx < itemSize; idx++) {
            st = new StringTokenizer(input.readLine());
            items[idx] = new Item(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        //1-3. 변수 초기화
        bestValue = new int[limitWeight + 1];
    }
}
