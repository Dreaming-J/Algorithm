/*
= BOJ 5670. 휴대폰 자판

= 특이사항
trie 자료구조
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static final int ALPHA_SIZE = 26;
    static int wordSize;
    static Node trie;
    static int pushCount;

    public static class Node {
        Node[] children;
        boolean endOfWord;
        int childCount;
        int pushCount;

        public Node() {
            children = new Node[ALPHA_SIZE];
        }
    }

    public static void insert(String word) {
        Node cur = trie;
        for (int idx = 0, end = word.length(); idx < end; idx++) {
            int c = word.charAt(idx) - 'a';
            if (cur.children[c] == null) {
                cur.children[c] = new Node();
                cur.childCount++;
            }

            cur = cur.children[c];
        }

        cur.endOfWord = true;
    }

    public static void count() {
        Deque<Node> queue = new ArrayDeque<>();
        queue.add(trie);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (cur.endOfWord)
                pushCount += cur.pushCount;

            int size = 0;
            for (int idx = 0; idx < ALPHA_SIZE; idx++) {
                if (size == cur.childCount)
                    break;

                Node next = cur.children[idx];

                if (next == null)
                    continue;

                next.pushCount = cur.pushCount;

                if (cur.childCount == 1) {
                    next.pushCount += cur.endOfWord || cur == trie ? 1 : 0;
                } else
                    next.pushCount++;

                size++;
                queue.add(next);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String line = null;
        while ((line = input.readLine()) != null) {
            //초기 세팅
            init();

            //단어 등록
            wordSize = Integer.parseInt(line);
            for (int idx = 0; idx < wordSize; idx++) {
                insert(input.readLine());
            }

            //입력 횟수 계산
            count();

            output.append(String.format("%.2f\n", (double) pushCount / wordSize));
        }

        System.out.println(output);
    }

    public static void init() {
        trie = new Node();
        pushCount = 0;
    }
}