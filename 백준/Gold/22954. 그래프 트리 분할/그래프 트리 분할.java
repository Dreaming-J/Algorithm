/*
= BOJ 22954. 그래프 트리 분할

= 로직
1. 초기 세팅
    1-1. 정점의 개수, 간선의 개수 입력
    1-2. 간선의 정보 입력
    1-3. 변수 초기화
2. 방문하지 않은 정점으로부터 컴포넌트 탐색
    2-1. 정점의 개수가 1개라면 분할 불가능
    2-2. 컴포넌트 개수 찾기
        2-2-1. 방문한 정점이라면 패스
        2-2-2. 컴포넌트의 개수가 2개를 넘어간다면 분할 불가능
        2-2-3. MST 탐색
    2-3. 컴포넌트의 개수가 1개라면 분할
3. 출력
    3-1. 컴포넌트가 2개가 아니거나, 두 컴포넌트의 정점의 크기가 같다면 -1 출력
    3-2. 2개로 분할할 수 있다면 출력
        3-2-1. 두 트리의 크기 출력
        3-2-2. 각 트리의 노드 번호 출력
        3-2-3. 각 트리의 간선 번호 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int TREE_SIZE = 2;
    static int vertexSize, edgeSize;
    static int componentCount;
    static boolean[] visited;
    static List<Node>[] graph;
    static Tree[] trees;

    //2. 방문하지 않은 정점으로부터 컴포넌트 탐색
    public static void findComponent() {
        //2-1. 정점의 개수가 1개라면 분할 불가능
        if (vertexSize == 1)
            return;

        //2-2. 컴포넌트 개수 찾기
        for (int vertex = 1; vertex <= vertexSize; vertex++) {
            //2-2-1. 방문한 정점이라면 패스
            if (visited[vertex])
                continue;

            //2-2-2. 컴포넌트의 개수가 2개를 넘어간다면 분할 불가능
            if (++componentCount > TREE_SIZE) {
                return;
            }

            //2-2-3. MST 탐색
            findMST(vertex);
        }

        //2-3. 컴포넌트의 개수가 1개라면 분할
        if (componentCount == TREE_SIZE - 1) {
            trees[componentCount + 1].pushNode(trees[componentCount].pop());
            componentCount++;
        }
    }

    //2-2-3. MST 탐색
    public static void findMST(int vertex) {
        Deque<Integer> queue = new ArrayDeque<>();

        visited[vertex] = true;
        trees[componentCount].pushNode(vertex);
        queue.add(vertex);

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (Node next : graph[cur]) {
                //방문했다면 패스
                if (visited[next.nodeNum])
                    continue;

                visited[next.nodeNum] = true;
                trees[componentCount].pushNode(next.nodeNum);
                trees[componentCount].pushEdge(next.edgeNum);
                queue.add(next.nodeNum);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 방문하지 않은 정점부터 MST 탐색
        findComponent();

        //3. 출력
        //3-1. 컴포넌트가 2개가 아니거나, 두 컴포넌트의 정점의 크기가 같다면 -1 출력
        if (componentCount != TREE_SIZE || trees[1].size == trees[2].size)
            output.append(-1);
            //3-2. 2개로 분할할 수 있다면 출력
        else {
            //3-2-1. 두 트리의 크기 출력
            output.append(trees[1].nodes.size())
                    .append(" ")
                    .append(trees[2].nodes.size())
                    .append("\n");

            for (int idx = 1; idx <= TREE_SIZE; idx++) {
                Deque<Integer> nodes = trees[idx].nodes;
                Deque<Integer> edges = trees[idx].edges;

                //3-2-2. 각 트리의 노드 번호 출력
                while (!nodes.isEmpty()) {
                    output.append(nodes.pop())
                            .append(" ");
                }
                output.append("\n");

                //3-2-3. 각 트리의 간선 번호 출력
                while (!edges.isEmpty()) {
                    output.append(edges.pop())
                            .append(" ");
                }
                output.append("\n");
            }
        }

        System.out.println(output);
    }

    public static class Node {
        int nodeNum, edgeNum;

        public Node(int nodeNum, int edgeNum) {
            this.nodeNum = nodeNum;
            this.edgeNum = edgeNum;
        }
    }

    public static class Tree {
        Deque<Integer> nodes, edges;
        int size;

        public Tree() {
            this.nodes = new ArrayDeque<>();
            this.edges = new ArrayDeque<>();
        }

        public void pushNode(int node) {
            nodes.push(node);
            size++;
        }

        public void pushEdge(int edge) {
            edges.push(edge);
        }

        public int pop() {
            edges.pop();
            size--;
            return nodes.pop();
        }
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 정점의 개수, 간선의 개수 입력
        st = new StringTokenizer(input.readLine());
        vertexSize = Integer.parseInt(st.nextToken());
        edgeSize = Integer.parseInt(st.nextToken());

        //1-2. 간선의 정보 입력
        graph = new ArrayList[vertexSize + 1];
        for (int idx = 1; idx <= vertexSize; idx++) {
            graph[idx] = new ArrayList<>();
        }

        for (int idx = 1; idx <= edgeSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, idx));
            graph[to].add(new Node(from, idx));
        }

        //1-3. 변수 초기화
        visited = new boolean[vertexSize + 1];

        trees = new Tree[TREE_SIZE + 1];
        for (int idx = 1; idx <= TREE_SIZE; idx++) {
            trees[idx] = new Tree();
        }
    }
}
