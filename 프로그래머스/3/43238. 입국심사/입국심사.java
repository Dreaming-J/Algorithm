class Solution {
    public long solution(int n, int[] times) {
        long minTime = 1L;
        long maxTime = 0L;
        
        for (int time : times)
            maxTime = Math.max(time, maxTime);
        maxTime *= n;
        
        while (minTime < maxTime) {
            long midTime = (minTime + maxTime) / 2;
            long possibleImmigrations = 0;
            
            for (int time : times)
                possibleImmigrations += midTime / time;
            
            if (possibleImmigrations >= n)
                maxTime = midTime;
            else
                minTime = midTime + 1;
        }
        
        return minTime;
    }
}