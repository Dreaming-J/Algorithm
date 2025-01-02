/*
= BOJ 17272. 리그 오브 레전설 (Large)

= 특이사항
조합의 수를 1,000,000,007로 나눈 나머지로 저장
싸움 시간은 10^18(천경)으로 단순 dp를 활용하게 되면 시간 초과
점화식을 이용해 행렬로 풀어나가야 하는 문제

= 점화식
F(n) = B * F(n - 1)
     = B^(N - M - 1) * F(M + 1)

= M이 3인 경우
B = [0 1 0 0; 0 0 1 0; 0 0 0 1; 0 1 0 1]
F(n) = [A(n-3); A(n-2); A(n-1); A(n)]

A(1) = A(2) = 1, A(3) = 2, A(4) = 3
F(4) = [A(1); A(2); A(3); A(4)]
     = [1; 1; 2; 3]
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static class Matrix {
        int rowSize, colSize;
        long[][] matrix;

        public Matrix(int rowSize, int colSize) {
            this.rowSize = rowSize;
            this.colSize = colSize;
            this.matrix = new long[rowSize][colSize];
        }
    }

    static final int MOD = 1_000_000_007;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static long combatTime;
    static int castingTime;
    static Matrix base, answer;

    public static void main(String[] args) throws IOException {
        init();

        if (combatTime < castingTime)
            System.out.println(1);
        else if (combatTime == castingTime)
            System.out.println(2);
        else {
            long power = combatTime - castingTime - 1;
            base = baseMatrixPower(power);
            answer = matrixMultiply(base, answer);

            System.out.println(answer.matrix[castingTime][0]);
        }
    }

    /*
    A^n * A^m = A^(n+m)이라는 특징과 2진수를 활용해 계산

    power를 2로 나누었을 때, 나머지가 존재한다면 해당 자리수를 사용한다는 의미이다.

    - 11을 2진수로 변환하는 법
        11 / 2 = 5...1 -> 2^0 = true
        5 / 2 = 2...1 -> 2^1 = true
        2 / 2 = 1...0 -> 2^2 = false
        1 / 2 = 0...1 -> 2^4 = true
        => 11 -> 1011_2

    위 방식을 행렬에 동일하게 적용할 수 있다.
    A^7 = A^4 * A^2 * A^1 -> A^7 = A^(111_2)
    A^11 = A^8 * A^2 * A^1 -> A^11 = A^(1011_2)
     */
    private static Matrix baseMatrixPower(long power) {
        Matrix result = makeUnitMatrix();

        while (power > 0) {
            //나머지가 존재한다면 해당 자리수에 해당하는 행렬 곱하기
            if (power % 2 == 1)
                result = matrixMultiply(result, base);

            //다음 자리수로 이동 및 기본 행렬 제곱
            base = matrixMultiply(base, base);
            power /= 2;
        }

        return result;
    }

    private static Matrix matrixMultiply(Matrix A, Matrix B) {
        Matrix result = new Matrix(A.rowSize, B.colSize);

        for (int rowA = 0; rowA < A.rowSize; rowA++) {
            for (int colB = 0; colB < B.colSize; colB++) {
                for (int idx = 0; idx < A.colSize; idx++) {
                    result.matrix[rowA][colB] += (A.matrix[rowA][idx] * B.matrix[idx][colB]) % MOD;
                    result.matrix[rowA][colB] %= MOD;
                }
            }
        }

        return result;
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        combatTime = Long.parseLong(st.nextToken());
        castingTime = Integer.parseInt(st.nextToken());

        base = makeBaseMatrix();

        answer = makeAnswerMatrix();
    }

    private static Matrix makeBaseMatrix() {
        Matrix result = new Matrix(castingTime + 1, castingTime + 1);

        for (int idx = 0; idx < castingTime; idx++)
            result.matrix[idx][idx + 1] = 1;

        result.matrix[castingTime][1] = result.matrix[castingTime][castingTime] = 1;

        return result;
    }

    private static Matrix makeAnswerMatrix() {
        Matrix result = new Matrix(castingTime + 1, 1);

        for (int idx = 0; idx < castingTime - 1; idx++)
            result.matrix[idx][0] = 1;

        result.matrix[castingTime - 1][0] = 2;
        result.matrix[castingTime][0] = 3;

        return result;
    }

    private static Matrix makeUnitMatrix() {
        Matrix result = new Matrix(castingTime + 1, castingTime + 1);

        for (int idx = 0; idx <= castingTime; idx++)
            result.matrix[idx][idx] = 1;

        return result;
    }
}
