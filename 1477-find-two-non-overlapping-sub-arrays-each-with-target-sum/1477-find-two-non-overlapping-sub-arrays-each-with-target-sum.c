#include <stdlib.h>

#define MIN(a, b) ((a) < (b) ? (a) : (b))

int minSumOfLengths(int* arr, int arrSize, int target) {
    int n = arrSize;
    int INF = n + 1;

    int* best = (int*)malloc((n + 1) * sizeof(int));
    for (int i = 0; i <= n; i++) {
        best[i] = INF;
    }

    int left = 0;
    int sum = 0;
    int shortest = INF;
    int answer = INF;

    for (int right = 0; right < n; right++) {
        sum += arr[right];

        while (sum > target) {
            sum -= arr[left++];
        }

        if (sum == target) {
            int length = right - left + 1;

            if (best[left] != INF) {
                answer = MIN(answer, best[left] + length);
            }
            shortest = MIN(shortest, length);
        }
        best[right + 1] = shortest;
    }

    free(best);
    return answer == INF ? -1 : answer;
}
