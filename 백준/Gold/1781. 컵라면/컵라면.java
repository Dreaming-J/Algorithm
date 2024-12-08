/*
= BOJ 1781. 컵라면
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int assignmentSize;
    static PriorityQueue<Assignment> assignments;
    static int[] solved;
    static int score;

    public static class Assignment implements Comparable<Assignment> {
        int deadLine, score;

        public Assignment(int deadLine, int score) {
            this.deadLine = deadLine;
            this.score = score;
        }

        @Override
        public int compareTo(Assignment o) {
            return o.score - score;
        }
    }

    public static int find(int deadLine) {
        if (solved[deadLine] == deadLine)
            return deadLine;

        return solved[deadLine] = find(solved[deadLine]);
    }

    public static void main(String[] args) throws IOException {
        init();

        while (!assignments.isEmpty()) {
            Assignment assignment = assignments.poll();

            int solvedIdx = find(assignment.deadLine);
            if (solvedIdx > 0) {
                solved[solvedIdx] = solvedIdx - 1;
                score += assignment.score;
            }
        }

        System.out.println(score);
    }

    public static void init() throws IOException {
        assignmentSize = Integer.parseInt(input.readLine());

        assignments = new PriorityQueue<>();
        for (int idx = 0; idx < assignmentSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int deadLine = Integer.parseInt(st.nextToken());
            int score = Integer.parseInt(st.nextToken());

            assignments.add(new Assignment(deadLine, score));
        }

        solved = new int[assignmentSize + 1];
        Arrays.setAll(solved, idx -> idx);
    }
}
