/*
#BOJ 2839. 설탕 배달

1. 설탕의 무게 입력
2. 설탕에 3씩 빼며  최소 봉지 개수를 계산한다.
	2-1. 현재 봉지가 5로 나누어 떨어진다면, 루프 종료
3. 무게가 0이 아니라면 봉지 개수를 -1로 설정한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int weight, bagCount;
	
	public static void main(String[] args) throws IOException {
		//1. 설탕의 무게 입력
		weight = Integer.parseInt(input.readLine());
		
		//2. 설탕에 3씩 빼며  최소 봉지 개수를 계산한다.
		while (weight > 0) {
			//2-1. 현재 봉지가 5로 나누어 떨어진다면, 루프 종료
			if (weight % 5 == 0) {
				bagCount += weight / 5;
				weight = 0;
				break;
			}
			
			weight -= 3;
			bagCount++;
		}
		
		//3. 무게가 0이 아니라면 봉지 개수를 -1로 설정한다.
		if (weight != 0)
			bagCount = -1;
		
		System.out.println(bagCount);
	}
}
