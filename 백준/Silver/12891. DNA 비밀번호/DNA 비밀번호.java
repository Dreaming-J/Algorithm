/*
#백준 12891. DNA 비밀번호

1. DNA 문자열 길이와 부분문자열의 길이를 입력받는다.
2. DNA 문자열을 입력받는다.
3. 포함될 {‘A’, ‘C’, ‘G’, ‘T’}의 최소 개수를 입력받는다.
4. 사용된 개수의 누적합을 구한다.
    4-1. 이 때, 부분문자열의 길이에 맞춰 해당 길이만큼의 누적합만 보관한다.
5. 계산된 누적합을 통해 정답을 찾는다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int DNA_TYPE_SIZE = 4;
    static int dnaLength, subDnaLength;
    static String dna;
    static int[] minUsedDna; //인덱스: 0='A' 1='C' 2='G' 3='T'
    static int[] prefixSumDna;
    static int pwCount;

    public static int dnaToIdx(char dna) {
        switch (dna) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        pwCount = 0;

        //1. DNA 문자열 길이와 부분문자열의 길이를 입력받는다.
        st = new StringTokenizer(input.readLine().trim());
        dnaLength = Integer.parseInt(st.nextToken());
        subDnaLength = Integer.parseInt(st.nextToken());

        //2. DNA 문자열을 입력받는다.
        dna = input.readLine();

        //3. 포함될 {‘A’, ‘C’, ‘G’, ‘T’}의 최소 개수를 입력받는다.
        minUsedDna = new int[DNA_TYPE_SIZE];
        st = new StringTokenizer(input.readLine().trim());
        for (int idx = 0; idx < DNA_TYPE_SIZE; idx++) {
            minUsedDna[idx] = Integer.parseInt(st.nextToken());
        }

        //4. 사용된 개수의 누적합을 구한다.
        prefixSumDna = new int[DNA_TYPE_SIZE];
        for (int dnaIdx = 0; dnaIdx < dnaLength; dnaIdx++) {
            prefixSumDna[dnaToIdx(dna.charAt(dnaIdx))]++;
            //4-1. 이 때, 부분문자열의 길이에 맞춰 해당 길이만큼의 누적합만 보관한다.
            if (dnaIdx >= subDnaLength - 1) {
            	if (dnaIdx >= subDnaLength)
            		prefixSumDna[dnaToIdx(dna.charAt(dnaIdx - subDnaLength))]--;
            	
            	//5. 계산된 누적합을 통해 정답을 찾는다.
                boolean canUsePassword = true;
                for (int dnaTypeIdx = 0; dnaTypeIdx < DNA_TYPE_SIZE; dnaTypeIdx++) {
                    if (prefixSumDna[dnaTypeIdx] < minUsedDna[dnaTypeIdx]) {
                        canUsePassword = false;
                        break;
                    }
                }
                if (canUsePassword)
                    pwCount++;
            }
        }

        System.out.println(pwCount);
    }
}
