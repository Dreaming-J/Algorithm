/*
#BOJ 6987. 월드컵

1. 경기 결과 입력
2. 경기 결과 시뮬레이션
	2-1. 불가능한 경기 결과라면 탐색 종료
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringJoiner output = new StringJoiner(" ");
	static StringTokenizer st;
	static final int WIN = 0, DRAW = 1, LOSE = 2;
	static final int COUNTRY_SIZE = 6, RESULT_SIZE = 4;
	static int[][][] results;
	static int resultIdx;
	static boolean possible;
	
	public static void playLeague(int currentIdx, int versusIdx) {
		if (currentIdx == COUNTRY_SIZE - 1) {
			int lastCountry = 0;
			for (int result : results[resultIdx][currentIdx])
				lastCountry += result;
			
			if (lastCountry == 0)
				possible = true;
			
			return;
		}
		
		if (versusIdx < COUNTRY_SIZE - 1) {
			//이기는 경우
			if (results[resultIdx][currentIdx][WIN] - 1 >= 0 && results[resultIdx][versusIdx][LOSE] - 1 >= 0) {
				results[resultIdx][currentIdx][WIN]--;
				results[resultIdx][versusIdx][LOSE]--;
				
				playLeague(currentIdx, versusIdx + 1);
				
				results[resultIdx][currentIdx][WIN]++;
				results[resultIdx][versusIdx][LOSE]++;
			}
			
			//비기는 경우
			if (results[resultIdx][currentIdx][DRAW] - 1 >= 0 && results[resultIdx][versusIdx][DRAW] - 1 >= 0) {
				results[resultIdx][currentIdx][DRAW]--;
				results[resultIdx][versusIdx][DRAW]--;
				
				playLeague(currentIdx, versusIdx + 1);
				
				results[resultIdx][currentIdx][DRAW]++;
				results[resultIdx][versusIdx][DRAW]++;
			}
			
			//지는 경우
			if (results[resultIdx][currentIdx][LOSE] - 1 >= 0 && results[resultIdx][versusIdx][WIN] - 1 >= 0) {
				results[resultIdx][currentIdx][LOSE]--;
				results[resultIdx][versusIdx][WIN]--;
				
				playLeague(currentIdx, versusIdx + 1);
				
				results[resultIdx][currentIdx][LOSE]++;
				results[resultIdx][versusIdx][WIN]++;
			}
		}
		else if (versusIdx == COUNTRY_SIZE - 1) {
			//이기는 경우
			if (results[resultIdx][currentIdx][WIN] - 1 == 0 && results[resultIdx][versusIdx][LOSE] - 1 >= 0) {
				results[resultIdx][currentIdx][WIN]--;
				results[resultIdx][versusIdx][LOSE]--;
				
				playLeague(currentIdx, versusIdx + 1);
				
				results[resultIdx][currentIdx][WIN]++;
				results[resultIdx][versusIdx][LOSE]++;
			}
			
			//비기는 경우
			if (results[resultIdx][currentIdx][DRAW] - 1 == 0 && results[resultIdx][versusIdx][DRAW] - 1 >= 0) {
				results[resultIdx][currentIdx][DRAW]--;
				results[resultIdx][versusIdx][DRAW]--;
				
				playLeague(currentIdx, versusIdx + 1);
				
				results[resultIdx][currentIdx][DRAW]++;
				results[resultIdx][versusIdx][DRAW]++;
			}
			
			//지는 경우
			if (results[resultIdx][currentIdx][LOSE] - 1 == 0 && results[resultIdx][versusIdx][WIN] - 1 >= 0) {
				results[resultIdx][currentIdx][LOSE]--;
				results[resultIdx][versusIdx][WIN]--;
				
				playLeague(currentIdx, versusIdx + 1);
				
				results[resultIdx][currentIdx][LOSE]++;
				results[resultIdx][versusIdx][WIN]++;
			}
		}
		else {
			playLeague(currentIdx + 1, currentIdx + 2);
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 경기 결과 입력
		init();
		
		//2. 경기 결과 시뮬레이션
		for (resultIdx = 0; resultIdx < RESULT_SIZE; resultIdx++) {
			possible = false;
			
			playLeague(0, 1);
			
			output.add(String.valueOf(possible ? 1 : 0));
		}
		
		//3. 출력
		System.out.println(output);
	}
	
	public static void init() throws IOException {
		results = new int[RESULT_SIZE][COUNTRY_SIZE][3];
		
		for (int resultIdx = 0; resultIdx < RESULT_SIZE; resultIdx++) {
			st = new StringTokenizer(input.readLine().trim());
			for (int countryIdx = 0; countryIdx < COUNTRY_SIZE; countryIdx++) {
				for (int idx = 0; idx < 3; idx++) {
					results[resultIdx][countryIdx][idx] = Integer.parseInt(st.nextToken());
				}
			}
		}
	}
}
