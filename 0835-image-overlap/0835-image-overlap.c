#include <stdlib.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))

int largestOverlap(int** img1, int img1Size, int* img1ColSize, int** img2, int img2Size, int* img2ColSize) {
    int n = img1Size;
    int size = 2 * n - 1;

    // Allocate memory for the 2D count array dynamically
    int** count = (int**)malloc(size * sizeof(int*));
    for (int i = 0; i < size; i++) {
        count[i] = (int*)calloc(size, sizeof(int));
    }
    
    int answer = 0;

    for (int r1 = 0; r1 < n; r1++) {
        for (int c1 = 0; c1 < n; c1++) {
            if (img1[r1][c1] == 0) continue;

            for (int r2 = 0; r2 < n; r2++) {
                for (int c2 = 0; c2 < n; c2++) {
                    if (img2[r2][c2] == 0) continue;

                    int dr = r2 - r1 + n - 1;
                    int dc = c2 - c1 + n - 1;

                    count[dr][dc]++;
                    answer = MAX(answer, count[dr][dc]);
                }
            }
        }
    }

    // Free allocated memory to prevent leaks
    for (int i = 0; i < size; i++) {
        free(count[i]);
    }
    free(count);

    return answer;
}
