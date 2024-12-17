/*
= BOJ 9202. Boggle
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static final int BOARD_SIZE = 4, ALPHABET_SIZE = 26;
    static final int[][] DELTAS = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    static int dicSize, boardCount;
    static char[][][] boards;
    static Trie trie;
    static Answer answer;

    static class Trie {
        Node head;

        public Trie() {
            this.head = new Node(Character.MIN_VALUE);
        }

        public static class Node {
            char key;
            boolean endOfWord;
            String word;
            Node[] children;

            public Node(char key) {
                this.key = key;
                this.endOfWord = false;
                this.children = new Node[ALPHABET_SIZE];
            }
        }

        public void add(String word) {
            Node cur = head;

            for (char c : word.toCharArray()) {
                int idx = ctoi(c);

                if (cur.children[idx] == null)
                    cur.children[idx] = new Node(c);

                cur = cur.children[idx];
            }

            cur.endOfWord = true;
            cur.word = word;
        }
    }

    static class Answer {
        int score;
        String longestWord;
        Set<String> words;

        public Answer() {
            longestWord = "";
            words = new HashSet<>();
        }

        public void add(String word) {
            if (words.add(word)) {
                updateScore(word);
                updateLongestWord(word);
            }
        }

        private void updateScore(String word) {
            switch (word.length()) {
                case 3:
                case 4:
                    score += 1;
                    break;
                case 5:
                    score += 2;
                    break;
                case 6:
                    score += 3;
                    break;
                case 7:
                    score += 5;
                    break;
                case 8:
                    score += 11;
                    break;
            }
        }

        private void updateLongestWord(String word) {
            if (word.length() < longestWord.length())
                return;

            if (word.length() > longestWord.length()) {
                longestWord = word;
                return;
            }

            if (word.compareTo(longestWord) < 0)
                longestWord = word;
        }

        @Override
        public String toString() {
            return score + " " + longestWord + " " + words.size();
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        for (int boardIdx = 0; boardIdx < boardCount; boardIdx++) {
            answer = new Answer();

            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    int idx = ctoi(boards[boardIdx][row][col]);

                    if (trie.head.children[idx] == null)
                        continue;

                    int pos = getIdxOfCoordinate(row, col);
                    playBoggle(trie.head.children[idx], pos, 1 << pos, boardIdx);
                }
            }

            output.append(answer).append("\n");
        }

        System.out.println(output);
    }

    private static void playBoggle(Trie.Node cur, int pos, int visitedBit, int boardIdx) {
        int row = pos / BOARD_SIZE;
        int col = pos % BOARD_SIZE;

        if (cur.endOfWord)
            answer.add(cur.word);

        for (int[] delta : DELTAS) {
            int nextRow = row + delta[0];
            int nextCol = col + delta[1];

            if (isOut(nextRow, nextCol))
                continue;

            int nextPos = getIdxOfCoordinate(nextRow, nextCol);

            if (visited(nextPos, visitedBit))
                continue;

            int nextIdx = ctoi(boards[boardIdx][nextRow][nextCol]);

            if (cur.children[nextIdx] == null)
                continue;

            playBoggle(cur.children[nextIdx], nextPos, visitedBit | 1 << nextPos, boardIdx);
        }
    }

    private static void init() throws IOException {
        // 단어 입력
        dicSize = Integer.parseInt(input.readLine());

        trie = new Trie();
        for (int idx = 0; idx < dicSize; idx++)
            trie.add(input.readLine());

        input.readLine();

        //보드 입력
        boardCount = Integer.parseInt(input.readLine());
        boards = new char[boardCount][BOARD_SIZE][BOARD_SIZE];
        for (int idx = 0; idx < boardCount; idx++) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                String line = input.readLine();
                for (int col = 0; col < BOARD_SIZE; col++)
                    boards[idx][row][col] = line.charAt(col);
            }

            if (idx != boardCount - 1)
                input.readLine();
        }
    }

    private static int ctoi(char c) {
        return c - 'A';
    }

    private static int getIdxOfCoordinate(int row, int col) {
        return row * BOARD_SIZE + col;
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE;
    }

    private static boolean visited(int pos, int visitedBit) {
        return (visitedBit & 1 << pos) != 0;
    }
}
