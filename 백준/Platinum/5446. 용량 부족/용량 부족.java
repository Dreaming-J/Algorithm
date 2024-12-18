/*
= BOJ 5446. 용량 부족

- 특이사항
여러 개의 테스트 케이스가 주어짐
와일드카드는 '*'뿐이며 제일 끝에만 이용 가능, 사용하지 않는 것도 가능
문자열의 길이는 1이상 20이하이며, 파일명은 영문 대소문자, 숫자, 점(.)으로 이루어짐
모든 파일 이름은 서로 다름
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int deleteFileSize, preservedFileSize;
    static Trie trie;
    static int deleteCommandCount;

    enum State {
        DELETE, PRESERVED;
    }

    static class Trie {
        Node head;

        public Trie() {
            this.head = new Node();
        }

        public static class Node {
            char a;
            boolean shouldDelete;
            boolean shouldPreserve;
            Map<Character, Node> children;

            public Node() {
                children = new HashMap<>();
            }
        }

        public void add(String word, State state) {
            Node cur = head;

            if (state == State.PRESERVED)
                cur.shouldPreserve = true;

            for (char c : word.toCharArray()) {
                if (!cur.children.containsKey(c))
                    cur.children.put(c, new Node());

                cur = cur.children.get(c);
                cur.a = c;

                if (state == State.PRESERVED)
                    cur.shouldPreserve = true;
            }

            if (state == State.DELETE)
                cur.shouldDelete = true;
        }
    }

    public static void main(String[] args) throws IOException {
        int testCases = Integer.parseInt(input.readLine());

        while (testCases-- > 0) {
            init();

            calculateMinDeleteCommands(trie.head);

            output.append(deleteCommandCount)
                    .append("\n");
        }

        System.out.println(output);
    }

    private static void calculateMinDeleteCommands(Trie.Node cur) {
        //해당 문자열 이후에 보존할 파일이 없다면
        if (!cur.shouldPreserve) {
            deleteCommandCount++;
            return;
        }

        //삭제해야할 파일을 찾았다면
        if (cur.shouldDelete)
            deleteCommandCount++;

        //다음 문자열로 이동
        for (Trie.Node next : cur.children.values())
            calculateMinDeleteCommands(next);
    }

    private static void init() throws IOException {
        trie = new Trie();

        deleteFileSize = Integer.parseInt(input.readLine());
        for (int idx = 0; idx < deleteFileSize; idx++)
            trie.add(input.readLine(), State.DELETE);

        preservedFileSize = Integer.parseInt(input.readLine());
        for (int idx = 0; idx < preservedFileSize; idx++)
            trie.add(input.readLine(), State.PRESERVED);

        deleteCommandCount = 0;
    }
}
