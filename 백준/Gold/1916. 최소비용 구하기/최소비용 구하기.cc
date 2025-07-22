#include <iostream>
#include <vector>
#include <queue>
using namespace std;

const int MAX_CITY_SIZE = 1001, INF = 1e9;

int citySize, busSize;
vector<pair<int, int>> graph[MAX_CITY_SIZE];
int src, dest;
int minCost[MAX_CITY_SIZE];

void init() {
    cin >> citySize;
    cin >> busSize;

    for (int idx = 1; idx <= busSize; idx++) {
        int from, to, cost;
        cin >> from >> to >> cost;

        graph[from].push_back({to, cost});
    }

    cin >> src >> dest;

    for (int idx = 1; idx <= citySize; idx++) {
        minCost[idx] = INF;
    }
    minCost[src] = 0;
}

void findMinCost() {
    priority_queue<pair<int, int>, vector<pair<int, int>>, less<pair<int, int>>> minHeap;

    minHeap.push({0, src});

    while (!minHeap.empty()) {
        pair<int, int> cur = minHeap.top();
        minHeap.pop();

        if (cur.first > minCost[cur.second])
            continue;
        
        for (auto& next : graph[cur.second]) {
            int cost = next.second + minCost[cur.second];
            
            if (cost < minCost[next.first]) {
                minCost[next.first] = cost;
                minHeap.push({cost, next.first});
            }
        }
    }
}

int main() {
    init();

    findMinCost();

    cout << minCost[dest];

    return 0;
}