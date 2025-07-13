import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

	static BufferedReader input;
	static StringTokenizer tokenizer;
	static int blockSize;
	static Block[] blocks;
	static SegmentTree st;

    public static class Block {
        int start, end;

        public Block(int length, int index) {
            this.start = index;
            this.end = length + index - 1;
        }
    }

	public static class SegmentTree {
		int[] tree, lazy;

		public SegmentTree(int size) {
			this.tree = new int[size * 4];
			this.lazy = new int[size * 4];
		}

        public int update(int updateLeft, int updateRight, int value, int left, int right, int node, int lazyValue) {
            // 레이지 전처리
            lazy[node] = Math.max(lazyValue, lazy[node]);
            tree[node] = Math.max(lazyValue, tree[node]);

            // 범위를 벗어났을 때
            if (right < updateLeft || updateRight < left)
                return tree[node];

            // 범위를 포함할 때
            if (updateLeft <= left && right <= updateRight) {
                lazy[node] = Math.max(value, lazy[node]);
                return tree[node] = Math.max(value, tree[node]);
            }

            // 레이지 후처리
            lazyValue = lazy[node];
            lazy[node] = 0;

            // 범위가 겹쳤을 때
            int mid = (left + right) / 2;
            return tree[node] = Math.max(update(updateLeft, updateRight, value, left, mid, node << 1, lazyValue), update(updateLeft, updateRight, value, mid + 1, right, node << 1 | 1, lazyValue));
        }

        public int query(int queryLeft, int queryRight, int left, int right, int node, int lazyValue) {
            // 레이지 전처리
            lazy[node] = Math.max(lazyValue, lazy[node]);
            tree[node] = Math.max(lazyValue, tree[node]);

            // 범위를 벗어났을 때
            if (right < queryLeft || queryRight < left)
                return 0;
            
            // 범위를 포함할 때
            if (queryLeft <= left && right <= queryRight)
                return tree[node];

            // 레이지 후처리
            lazyValue = lazy[node];
            lazy[node] = 0;

            // 범위가 겹쳤을 때
            int mid = (left + right) / 2;
            return Math.max(query(queryLeft, queryRight, left, mid, node << 1, lazyValue), query(queryLeft, queryRight, mid + 1, right, node << 1 | 1, lazyValue));
        }
	}

	public static void main(String[] args) throws IOException {
		init();

        int maxSize = compress();

        st = new SegmentTree(maxSize);

        for (Block block : blocks) {
            int height = st.query(block.start, block.end, 1, maxSize, 1, 0);

            st.update(block.start, block.end, height + 1, 1, maxSize, 1, 0);
        }

        System.out.println(st.query(1, maxSize, 1, maxSize, 1, 0));
	}

    static int compress() {
        int index = 0;

        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (Block block : blocks) {
            if (!map.containsKey(block.start))
                map.put(block.start, 0);

            if (!map.containsKey(block.end))
                map.put(block.end, 0);
        }

        for (int key : map.keySet())
            map.put(key, ++index);

        for (Block block : blocks) {
            block.start = map.get(block.start);
            block.end = map.get(block.end);
        }

        return index;
    }

	static void init() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));

		blockSize = Integer.parseInt(input.readLine());
        blocks = new Block[blockSize];

        for (int idx = 0; idx < blockSize; idx++) {
            tokenizer = new StringTokenizer(input.readLine());
            int length = Integer.parseInt(tokenizer.nextToken());
            int index = Integer.parseInt(tokenizer.nextToken());

            blocks[idx] = new Block(length, index);
        }
	}
}