class Solution {
    int answer;
    public int solution(String begin, String target, String[] words) {
        answer = 0;
        boolean[] visited = new boolean[words.length];
        dfs(0, -1, begin, target, words, visited);
        return answer;
    }
    
    void dfs(int n, int i, String begin, String target, String[] words, boolean[] visited) {
        if (i != -1 && visited[i]) return;
        
        if (begin.equals(target)) {
            answer = n;
            return;
        }
        
        if (i != -1) visited[i] = true;
        
        for (int j = 0; j < words.length; j++) {
            if (canConvert(begin, words[j])) {
                dfs(n + 1, j, words[j], target, words, visited);
            }
        }
    }
    
    boolean canConvert(String word1, String word2) {
        int diff = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) diff++;
        }
        
        return diff == 1;
    }
}