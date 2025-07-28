#include <iostream>
#include <string>
using namespace std;

const int DELTAS[4][2] = {{-1 ,0}, {1, 0}, {0, -1}, {0, 1}};
const int MAX_SIZE = 101;

int rowSize, colSize, hintSize;
char map[MAX_SIZE][MAX_SIZE];
string hint;
bool visited[MAX_SIZE][MAX_SIZE];
int maxK = -1;
pair<int, int> maxPoint;

void init()
{
    cin >> rowSize >> colSize >> hintSize;
    
    cin >> hint;
    
    for (int row = 1; row <= rowSize; row++)
    {
        for (int col = 1; col <= colSize; col++)
        {
            cin >> map[row][col];
        }
    }
}

bool is_out(int row, int col)
{
    return row <= 0 || row > rowSize || col <= 0 || col > colSize;
}

void dfs(int row, int col, int nextHintIdx, int k)
{
    visited[row][col] = true;
    
    if (nextHintIdx % hintSize == 0 && k > maxK)
    {
        maxK = k;
        maxPoint = {row, col};
    }
    
    for (auto& delta : DELTAS)
    {
        int nextRow = row + delta[0];
        int nextCol = col + delta[1];
        
        // 범위를 벗어나면
        if (is_out(nextRow, nextCol))
            continue;
            
        // 힌트의 다음 칸이 아니면
        if (map[nextRow][nextCol] != hint.at(nextHintIdx % hintSize))
            continue;
        
        // 방문했으면
        if (visited[nextRow][nextCol] && nextHintIdx % hintSize == 0)
        {
            cout << -1;
            exit(0);
        }
        
        dfs(nextRow, nextCol, nextHintIdx + 1, nextHintIdx % hintSize == 0 ? k + 1 : k);
    }
    
    visited[row][col] = false;
}

int main()
{
    init();
    
    dfs(1, 1, 1, 1);
    
    cout << maxK << '\n';
    if (maxK != -1) {
        cout << maxPoint.first << " " << maxPoint.second;
    }
    
    return 0;
}