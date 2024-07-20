import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            int V = Integer.parseInt(br.readLine());
            
            int oldNo = 0;
            int newNo = 1;
            int answer = 1;
            while(V >= newNo){
                int temp = oldNo;
                oldNo = newNo;
                newNo += temp + 1;
                answer++;
            }
            System.out.println(--answer);
        }
    }
}