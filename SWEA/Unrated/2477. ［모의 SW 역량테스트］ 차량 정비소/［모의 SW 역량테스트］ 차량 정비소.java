/*
= SWEA 2477. [모의 SW 역량테스트] 차량 정비소

= 특이사항
접수 창구, 정비 창구, 고객 모두 1번부터 시작

= 로직
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 접수 창구의 개수, 정비 창구의 개수, 고객의 수, 타겟 접수 창구 번호, 타겟 정비 창구 번호 입력
	1-2. 접수 창구의 접수 시간 입력
	1-3. 정비 창구의 정비 시간 입력
	1-4. 고객의 정비소 방문 시간 입력
2. 시뮬레이션
	3. 정비 완료 시뮬레이션
		3-1. 정비 중인 고객이 있고, 정비가 완료된 고객이 있다면 정비 완료 절차 진행
			3-1-1. 사용한 창구 비우기
			3-1-2. 찾고 있는 손님인지 판단
			3-1-3. 정비소에 남은 고객의 수 감소
	4. 정비 창구 이동 시뮬레이션
		4-1. 정비를 기다리는 고객이 있고, 비어있는 정비 창구가 있고, 정비를 기다리는 고객이 있다면 접수 진행
			4-1-1. 정비할 창구 번호 탐색
			4-1-2. 접수 진행
			4-1-3. 해당 창구 사용 처리
	5. 접수 완료 시뮬레이션
		5-1. 접수 중인 고객이 있고, 접수가 완료된 고객이 있다면 접수 완료 절차 진행
			5-1-1. 사용한 창구 비우기
			5-1-2. 정비 창구 대기 시작
	6. 접수 창구 이동 시뮬레이션
		6-1. 접수할 고객이 있고, 비어있는 접수 창구가 있고, 접수를 기다리는 고객이 있다면 접수 진행
			6-2-1. 접수할 창구 번호 탐색
			6-2-2. 접수 진행
			6-2-3. 해당 창구 사용 처리
7. 출력
*/
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
 
public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int receptionSize, repairSize, customerSize, targetReception, targetRepair;
    static Desk[] receptionDesks, repairDesks;
    static Deque<Customer> receptionWaiting;
    static PriorityQueue<Customer> repairWaiting;
    static PriorityQueue<Desk> receptionService, repairService;
    static int time, remainedCustomer, usedReceptionBit, usedRepairBit;
    static int answer;
    
    //3. 정비 완료 시뮬레이션
    public static void checkRepairComplete() {
    	//3-1. 정비 중인 고객이 있고, 정비가 완료된 고객이 있다면 정비 완료 절차 진행
    	while (!repairService.isEmpty() && repairService.peek().endTime == time) {
    		Customer customer = repairService.poll().customer;
    		
    		//3-1-1. 사용한 창구 비우기
    		usedRepairBit &= ~(1 << customer.usedRepairNum);
    		
    		//3-1-2. 찾고 있는 손님인지 판단
    		if (customer.usedReceptionNum == targetReception && customer.usedRepairNum == targetRepair)
    			answer += customer.number;
    		
    		//3-1-3. 정비소에 남은 고객의 수 감소
    		remainedCustomer--;
    	}
    }
    
    //4. 정비 창구 이동 시뮬레이션
    public static void moveToRepairDesk() {
    	//4-1. 정비를 기다리는 고객이 있고, 비어있는 정비 창구가 있고, 정비를 기다리는 고객이 있다면 접수 진행
    	while (!repairWaiting.isEmpty() && Integer.bitCount(usedRepairBit) < repairSize && repairWaiting.peek().time <= time) {
    		//4-1-1. 정비할 창구 번호 탐색
    		int repairIdx = 1;
    		while ((usedRepairBit & 1 << repairIdx) != 0)
    			repairIdx++;
    		
			//4-1-2. 접수 진행
    		if (repairIdx == repairSize + 1)
    			return;
    		
    		Customer customer = repairWaiting.poll();
    		customer.usedRepairNum = repairIdx;
    		
    		Desk repairDesk = repairDesks[repairIdx];
    		repairDesk.customer = customer;
    		repairDesk.endTime = time + repairDesk.elapsedTime;
    		
    		repairService.add(repairDesk);
    		
			//4-1-3. 해당 창구 사용 처리
    		usedRepairBit |= 1 << repairIdx;
    	}
    }
    
    //5. 접수 완료 시뮬레이션
    public static void checkReceptionComplete() {
    	//5-1. 접수 중인 고객이 있고, 접수가 완료된 고객이 있다면 접수 완료 절차 진행
    	while (!receptionService.isEmpty() && receptionService.peek().endTime == time) {
    		Customer customer = receptionService.poll().customer;
    		customer.time = time;
    		
    		//5-1-1. 사용한 창구 비우기
    		usedReceptionBit &= ~(1 << customer.usedReceptionNum);
    		
    		//5-1-2. 정비 창구 대기 시작
    		repairWaiting.add(customer);
    	}
		moveToRepairDesk();
    }
    
    //6. 접수 창구 이동 시뮬레이션
    public static void moveToReceptionDesk() {
    	//6-1. 접수할 고객이 있고, 비어있는 접수 창구가 있고, 접수를 기다리는 고객이 있다면 접수 진행
    	while (!receptionWaiting.isEmpty() && Integer.bitCount(usedReceptionBit) < receptionSize && receptionWaiting.peek().time <= time) {
    		//6-1-1. 접수할 창구 번호 탐색
    		int receptionIdx = 1;
    		while ((usedReceptionBit & 1 << receptionIdx) != 0)
    			receptionIdx++;
    		
			//6-1-2. 접수 진행
    		if (receptionIdx == receptionSize + 1)
    			return;
    		
    		Customer customer = receptionWaiting.poll();
    		customer.usedReceptionNum = receptionIdx;
    		
    		Desk receptionDesk = receptionDesks[receptionIdx];
    		receptionDesk.customer = customer;
    		receptionDesk.endTime = time + receptionDesk.elapsedTime;
    		
    		receptionService.add(receptionDesk);
    		
			//6-1-3. 해당 창구 사용 처리
    		usedReceptionBit |= 1 << receptionIdx;
    	}
    }
    
    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine().trim());
 
        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();
            
            //2. 시뮬레이션
            for (time = 0; remainedCustomer > 0; time++) {
            	//3. 정비 완료 시뮬레이션
            	checkRepairComplete();
            	
            	//4. 정비 창구 이동 시뮬레이션
            	moveToRepairDesk();
            	
            	//5. 접수 완료 시뮬레이션
            	checkReceptionComplete();
            	
            	//6. 접수 창구 이동 시뮬레이션
            	moveToReceptionDesk();
            }
            
            //7. 출력
            output.append("#").append(tc).append(" ").append(answer == 0 ? -1 : answer).append("\n");
        }
        System.out.println(output);
    }

    //두 명 이상의 고객들이 접수 창구에서 동시에 접수를 완료하고 정비 창구로 이동한 경우, 이용했던 접수 창구번호가 작은 고객이 우선한다
    public static class Customer implements Comparable<Customer> {
    	int number, time, usedReceptionNum, usedRepairNum;

		public Customer(int number, int time) {
			this.number = number;
			this.time = time;
		}
		
		@Override
		public int compareTo(Customer o) {
			return time == o.time ? usedReceptionNum - o.usedReceptionNum : time - o.time;
		}
    }
    
    public static class Desk implements Comparable<Desk> {
    	int elapsedTime;
    	Customer customer;
    	int endTime;
    	
		public Desk(int elapsedTime) {
			this.elapsedTime = elapsedTime;
		}
		
		@Override
		public int compareTo(Desk o) {
			return this.endTime - o.endTime;
		}
    }
 
    //1. 초기 세팅
    public static void initTestCase() throws IOException {
    	//1-1. 접수 창구의 개수, 정비 창구의 개수, 고객의 수, 타겟 접수 창구 번호, 타겟 정비 창구 번호 입력
    	st = new StringTokenizer(input.readLine().trim());
    	receptionSize = Integer.parseInt(st.nextToken());
    	repairSize = Integer.parseInt(st.nextToken());
    	customerSize = Integer.parseInt(st.nextToken());
    	targetReception = Integer.parseInt(st.nextToken());
    	targetRepair = Integer.parseInt(st.nextToken());
    	
        //1-2. 접수 창구의 접수 시간 입력
    	receptionDesks = new Desk[receptionSize + 1];
    	st = new StringTokenizer(input.readLine().trim());
    	for (int idx = 1; idx <= receptionSize; idx++)
    		receptionDesks[idx] = new Desk(Integer.parseInt(st.nextToken()));
    	
    	//1-3. 정비 창구의 정비 시간 입력
    	repairDesks = new Desk[repairSize + 1];
    	st = new StringTokenizer(input.readLine().trim());
    	for (int idx = 1; idx <= repairSize; idx++)
    		repairDesks[idx] = new Desk(Integer.parseInt(st.nextToken()));
    	
    	//1-4. 고객의 정비소 방문 시간 입력
    	receptionWaiting = new ArrayDeque<>();
        st = new StringTokenizer(input.readLine().trim());
    	for (int idx = 1; idx <= customerSize; idx++)
    		receptionWaiting.add(new Customer(idx, Integer.parseInt(st.nextToken())));
    	
    	//1-5. 변수 초기화
    	repairWaiting = new PriorityQueue<>();
    	receptionService = new PriorityQueue<>();
    	repairService = new PriorityQueue<>();
    	
    	remainedCustomer = customerSize;
    	usedReceptionBit = 0;
    	usedRepairBit = 0;
    	answer = 0;
    }
}