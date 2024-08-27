/*
= BOJ 1759. 암호 만들기

=특이 사항
각 암호마다 오름차순 정렬, 모음 1개 자음 2개 이상 포함 

=로직
1. 초기 세팅
	1-1. 암호의 길이, 사용 가능성이 있는 알파벳의 개수 입력
	1-2. 알파벳의 개수만큼 알파벳 입력
2. 주어진 알파벳을 이용해 조합을 구한다.
	2-1. 암호의 길이만큼 선택했다면 종료
		2-1-1. 모음의 길이 1개 이상이고 자음의 길이가 2개 이상이라면 출력 
	2-2. 모든 알파벳을 탐색했다면 종료
	2-3. 다음 알파벳 탐색
3, 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int ALPHA_SIZE = 26;
	static final int MIN_VOWEL_SIZE = 1, MIN_CONSONANT_SIZE = 2;
	static final int VOWEL_BIT = 0b00_0001_0000_0100_0001_0001_0001;
	static final int CONSONANT_BIT = 0b11_1110_1111_1011_1110_1110_1110;
	static int pwSize, candidateSize;
	static int candidateBit;
	
	public static void findPassword(int candidateIdx, int pwCount, int pwBit) {
		//2-1. 암호의 길이만큼 선택했다면 종료
		if (pwCount == pwSize) {
			//2-1-1. 모음의 길이 1개 이상이고 자음의 길이가 2개 이상이라면 출력
			int vowelSize = Integer.bitCount(pwBit & VOWEL_BIT);
			int consonantSize = Integer.bitCount(pwBit & CONSONANT_BIT);
			
			if (vowelSize >= MIN_VOWEL_SIZE && consonantSize >= MIN_CONSONANT_SIZE) {
				for (int idx = 0; idx < ALPHA_SIZE; idx++) {
					if ((pwBit & 1 << idx) == 0)
						continue;
					
					output.append((char) (idx + 'a'));
				}
				output.append("\n");
			}
			
			return;
		}
		
		//2-2. 모든 알파벳을 탐색했다면 종료
		if (candidateIdx == ALPHA_SIZE)
			return;
		
		//2-3. 다음 알파벳 탐색
		int next = candidateIdx;
		while (next < ALPHA_SIZE && (candidateBit & 1 << ++next) == 0);
		
		findPassword(next, pwCount + 1, pwBit | 1 << candidateIdx);
		findPassword(next, pwCount, pwBit);
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 주어진 알파벳을 이용해 조합을 구한다.
		int start = -1;
		while ((candidateBit & 1 << ++start) == 0);
		findPassword(start, 0, 0);
		
		//3. 출력
		System.out.println(output);
	}
	
	public static void init() throws IOException {
		//1-1. 행과 열의 크기 입력
		st = new StringTokenizer(input.readLine());
		pwSize = Integer.parseInt(st.nextToken());
		candidateSize = Integer.parseInt(st.nextToken());
		
		//1-2. 행의 크기만큼 보드 정보 입력
		String line = input.readLine();
		for (int idx = 0; idx < candidateSize; idx++)
			candidateBit |= 1 << (line.charAt(idx * 2) - 'a');
	}
}
