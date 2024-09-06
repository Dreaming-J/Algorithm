import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
 
public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int studentSize, compareSize;
    static List<Integer>[] order;
    static boolean[] visited;
    static int[] degrees;
    static int answer;
 
    //2. 키 순서 확인
    public static void checkOrder(int startIdx, int curIdx) {
        visited[curIdx] = true;
 
        for (int next : order[curIdx]) {
            if (visited[next])
                continue;
 
            degrees[startIdx]++;
            degrees[next]++;
            checkOrder(startIdx, next);
        }
    }
 
    public static void main(String[] args) throws IOException {
            //1. 초기 세팅
            initTestCase();

            //2. 키 순서 확인
            for (int studentIdx = 1; studentIdx <= studentSize; studentIdx++) {
                visited = new boolean[studentSize + 1];
                checkOrder(studentIdx, studentIdx);
            }

            //3. 내 뒤에 있는 학생수 + 내 앞의 학생수를 계산해 정답 개수 확인
            for (int studentDegree : degrees) {
                if (studentDegree == studentSize - 1)
                    answer++;
            }
            
        System.out.println(answer);
    }
 
    //1. 초기 세팅
    public static void initTestCase() throws IOException {
        //1-1. 학생의 수 입력
    	st = new StringTokenizer(input.readLine());
        studentSize = Integer.parseInt(st.nextToken());
 
        //1-2. 키 비교 횟수 입력
        compareSize = Integer.parseInt(st.nextToken());
        //1-3. 키 비교 결과 입력
        order = new ArrayList[studentSize + 1];
        for (int idx = 1; idx <= studentSize; idx++) {
            order[idx] = new ArrayList<>();
        }
 
        for (int compareIdx = 0; compareIdx < compareSize; compareIdx++) {
            st = new StringTokenizer(input.readLine().trim());
 
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
 
            order[from].add(to);
        }
 
        //1-4. 변수 초기화
        degrees = new int[studentSize + 1];
        answer = 0;
    }
}