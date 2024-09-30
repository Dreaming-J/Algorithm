import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
= SWEA 3282. 0/1 Knapsack

= 로직
0. 테스트케이스 입력
1. 초기 세팅
	1-1. 물건의 개수, 가방의 부피 입력
	1-2. 물건의 부피와 가치 입력
	1-3. 변수 초기화
2. 각 무게 별 최대 가치 탐색
	2-1. 데이터 오염을 방지하기 위해 최대 무게부터 현재 아이템의 무게까지 역으로 탐색하며, 최대 가치 갱신
 */

public class Solution {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int itemSize, bagSize;
	static Item[] items;
	static int[] maxValue;
	
	public static void main(String[] args) throws IOException {
		//0. 테스트케이스 입력
		int testCase = Integer.parseInt(input.readLine());
		
		for (int tc = 1; tc <= testCase; tc++) {
			//1. 초기 세팅
			init();
			
			//2. 각 무게 별 최대 가치 탐색
			for (Item item : items) {
				//2-1. 데이터 오염을 방지하기 위해 최대 무게부터 현재 아이템의 무게까지 역으로 탐색하며, 최대 가치 갱신
				for (int curWeight = bagSize; curWeight >= item.weight; curWeight--) {
					maxValue[curWeight] = Math.max(item.value + maxValue[curWeight - item.weight], maxValue[curWeight]);
				}
			}
			
			//3. 출력
			output.append("#").append(tc).append(" ").append(maxValue[bagSize]).append("\n");
		}
		
		System.out.println(output);
	}
	
	public static class Item {
		int weight, value;

		public Item(int weight, int value) {
			this.weight = weight;
			this.value = value;
		}
	}
	
	//1. 초기 세팅
	public static void init() throws IOException {
		//1-1. 물건의 개수, 가방의 부피 입력
		st = new StringTokenizer(input.readLine());
		itemSize = Integer.parseInt(st.nextToken());
		bagSize = Integer.parseInt(st.nextToken());
		
		//1-2. 물건의 부피와 가치 입력
		items = new Item[itemSize];
		for (int idx = 0; idx < itemSize; idx++) {
			st = new StringTokenizer(input.readLine());
			items[idx] = new Item(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		//1-3. 변수 초기화
		maxValue = new int[bagSize + 1];
	}
}
