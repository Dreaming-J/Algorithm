# [Platinum III] 회사 문화 4 - 14288 

[문제 링크](https://www.acmicpc.net/problem/14288) 

### 성능 요약

메모리: 55848 KB, 시간: 600 ms

### 분류

자료 구조, 트리, 세그먼트 트리, 느리게 갱신되는 세그먼트 트리, 오일러 경로 테크닉, 차분 배열 트릭

### 제출 일자

2025년 6월 28일 23:10:47

### 문제 설명

<p>영선회사에는 매우 좋은 문화가 있는데, 바로 상사가 직속 부하를 칭찬하면 그 부하가 부하의 직속 부하를 연쇄적으로 칭찬하는 내리 칭찬이 있다. 즉, 상사가 한 직속 부하를 칭찬하면 그 부하의 모든 부하들이 칭찬을 받는다.</p>

<p>이러한 내리 칭찬은 회사에 가장 큰 장점이 되었고, 사장 영선이는 이 장점을 이용하기 위하여 근무 시간 도중 도중 칭찬의 방향을 바꿀 것이다. 가장 처음에는 부하 직원 방향이다.</p>

<p>칭찬에 대한 정보는 실시간으로 주어진다.</p>

<p>입력으로 아래와 같은 쿼리가 주어질 것이다.</p>

<ul>
	<li><code>1 i w</code>: i번째 직원이 직속 상사로부터 w만큼 칭찬을 받는다. 만약, 칭찬의 방향이 상사 방향이라면, i번째 직원이 직속 부하 중 한 명으로부터 w만큼 칭찬을 받는다. (1 ≤ i ≤ n, 1 ≤ w ≤ 1,000)</li>
	<li><code>2 i</code>: i번째 직원이 칭찬을 받은 정도를 출력한다.</li>
	<li><code>3</code>: 칭찬의 방향이 부하 직원 방향이면 상사 방향으로, 상사 방향이면 부하 직원 방향으로 바꾼다.</li>
</ul>

<p>직속 상사와 직속 부하관계에 대해 주어지고, 쿼리가 주어졌을 때 2번 쿼리에 따라 출력하시오.</p>

### 입력 

 <p>첫째 줄에는 회사의 직원 수 n명, 쿼리의 개수 m이 주어진다. 직원은 1번부터 n번까지 번호가 매겨져 있다. (2 ≤ n, m ≤ 100,000)</p>

<p>둘째 줄에는 직원 n명의 직속 상사의 번호가 주어진다. 직속 상사의 번호는 자신의 번호보다 작으며, 최종적으로 1번이 사장이다. 1번의 경우, 상사가 없으므로 -1이 입력된다.</p>

<p>다음 m줄에는 쿼리가 한 줄에 하나씩 주어진다.</p>

### 출력 

 <p>2번 쿼리가 주어질 때마다, 알맞게 출력하시오.</p>

