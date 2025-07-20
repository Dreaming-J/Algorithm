#include <iostream>
#include <string>
using namespace std;

const int DELTAS[4][2] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
const int MAX_SIZE = 101, MAX_INT = 1e9;

int rowSize, colSize, hintSize;
char map[MAX_SIZE][MAX_SIZE];
string hint;
bool visited[MAX_SIZE][MAX_SIZE];
int maxK = -1;
pair<int, int> point;

void init() {
    cin >> rowSize >> colSize >> hintSize;
    cin >> hint;

    for (int row = 1; row <= rowSize; row++) {
        for (int col = 1; col <= colSize; col++) {
            cin >> map[row][col];
        }
    }
}

bool isOut(int row, int col) {
    return row <= 0 || row > rowSize || col <= 0 || col > colSize;
}

void dfs(pair<int, int> cur, int hintIdx, int k) {
    visited[cur.first][cur.second] = true;

    if (hintIdx == hintSize) {
        if (k > maxK) {
            maxK = k;
            point = cur;
        }

        hintIdx = 0;
    }

    for (auto& delta : DELTAS) {
        int nextRow = cur.first + delta[0];
        int nextCol = cur.second + delta[1];

        if (isOut(nextRow, nextCol) || map[nextRow][nextCol] != hint.at(hintIdx)) {
            continue;
        }

        if (visited[nextRow][nextCol]) {
            maxK = MAX_INT;
            return;
        }

        dfs({nextRow, nextCol}, hintIdx + 1, hintIdx == 0 ? k + 1 : k);

        visited[nextRow][nextCol] = false;
    }
}

int main() {
    init();

    dfs({1, 1}, 1, 1);

    if (maxK == MAX_INT) {
        cout << -1;
    }
    else {
        cout << maxK << '\n' << point.first << " " << point.second;
    }

    return 0;
}