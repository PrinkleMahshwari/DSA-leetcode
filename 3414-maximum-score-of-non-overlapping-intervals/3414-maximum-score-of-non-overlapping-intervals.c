#include <stdlib.h>
#include <string.h>

// Struct for sorting tracking
struct IntervalNode {
    int left;
    int right;
    int weight;
    int original_idx;
};

// State representation
struct State {
    long long score;
    int ids[4];
    int len;
};

// Sort comparator
int compareIntervals(const void* x, const void* y) {
    int r1 = ((struct IntervalNode*)x)->right;
    int r2 = ((struct IntervalNode*)y)->right;
    if (r1 < r2) return -1;
    if (r1 > r2) return 1;
    return 0;
}

// Lexicographical helper
int compareIds(int* a, int a_len, int* b, int b_len) {
    int min_len = a_len < b_len ? a_len : b_len;
    for (int i = 0; i < min_len; i++) {
        if (a[i] != b[i]) return a[i] - b[i];
    }
    return a_len - b_len;
}

void addId(int* src, int src_len, int val, int* dest) {
    int i = 0, j = 0;
    while (i < src_len && src[i] < val) {
        dest[j++] = src[i++];
    }
    dest[j++] = val;
    while (i < src_len) {
        dest[j++] = src[i++];
    }
}

struct State betterState(struct State a, struct State b) {
    if (a.score != b.score) {
        return a.score > b.score ? a : b;
    }
    return compareIds(a.ids, a.len, b.ids, b.len) <= 0 ? a : b;
}

int* maximumWeight(int** intervals, int intervalsSize, int* intervalsColSize, int* returnSize) {
    int n = intervalsSize;

    struct IntervalNode* a = (struct IntervalNode*)malloc(n * sizeof(struct IntervalNode));
    for (int i = 0; i < n; i++) {
        a[i].left = intervals[i][0];
        a[i].right = intervals[i][1];
        a[i].weight = intervals[i][2];
        a[i].original_idx = i;
    }

    qsort(a, n, sizeof(struct IntervalNode), compareIntervals);

    int* prev = (int*)malloc(n * sizeof(int));
    for (int i = 0; i < n; i++) {
        int lo = 0, hi = i - 1, pos = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid].right < a[i].left) {
                pos = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        prev[i] = pos;
    }

    // Multi-dimensional DP arrays allocation on heap to avoid stack limits
    struct State** dp = (struct State**)malloc(5 * sizeof(struct State*));
    for (int k = 0; k <= 4; k++) {
        dp[k] = (struct State*)calloc((n + 1), sizeof(struct State));
    }

    for (int k = 1; k <= 4; k++) {
        for (int i = 1; i <= n; i++) {
            int cur = i - 1;
            struct State skip = dp[k][i - 1];
            struct State base = dp[k - 1][prev[cur] + 1];

            struct State take;
            take.score = base.score + a[cur].weight;
            take.len = base.len + 1;
            addId(base.ids, base.len, a[cur].original_idx, take.ids);

            dp[k][i] = betterState(skip, take);
        }
    }

    *returnSize = dp[4][n].len;
    int* result = (int*)malloc((*returnSize) * sizeof(int));
    memcpy(result, dp[4][n].ids, (*returnSize) * sizeof(int));

    // Cleanup memory allocations
    free(a);
    free(prev);
    for (int k = 0; k <= 4; k++) free(dp[k]);
    free(dp);

    return result;
}
