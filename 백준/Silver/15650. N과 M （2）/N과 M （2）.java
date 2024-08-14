import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
=백준 15650. N과 M (2)

1. 숫자 n과 뽑을 개수 r을 입력받는다.
2. r개의 배열을 초기화한다.
3. nPr수열을 구한다.
	3-1. 모든 개수를 선택했다면, 출력 후 종료한다.
	3-2. 수열은 전의 값보다 큰 위치에서 시작해야 한다.
 */
public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int maxNum, count;
	static int[] selectElements;

	public static void permutation(int selectCount, int startIdx) {
		//3-1. 모든 개수를 선택했다면, 출력 후 종료한다.
		if (selectCount == count) {
			for (int idx = 0; idx < count; idx++)
				output.append(selectElements[idx]).append(" ");
			output.append("\n");
			return;
		}
		
		//3-2. 수열은 전의 값보다 큰 위치에서 시작해야 한다.
		for (int num = startIdx; num <= maxNum; num++) {
			selectElements[selectCount] = num;
			permutation(selectCount + 1, num + 1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 숫자 n과 뽑을 개수 r을 입력받는다.
		st = new StringTokenizer(input.readLine());
		maxNum = Integer.parseInt(st.nextToken());
		count = Integer.parseInt(st.nextToken());
		
		//2. r개의 배열을 초기화한다.
		selectElements = new int[count];
		
		//3. nPr수열을 구한다.
		permutation(0, 1);
		
		System.out.println(output);
	}
}
