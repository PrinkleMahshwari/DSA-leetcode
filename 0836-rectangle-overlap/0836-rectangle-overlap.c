#include <stdbool.h>

#define MIN(a, b) ((a) < (b) ? (a) : (b))
#define MAX(a, b) ((a) > (b) ? (a) : (b))

bool isRectangleOverlap(int* rec1, int rec1Size, int* rec2, int rec2Size) {
    int overlapWidth = MIN(rec1[2], rec2[2]) - MAX(rec1[0], rec2[0]);
    int overlapHeight = MIN(rec1[3], rec2[3]) - MAX(rec1[1], rec2[1]);

    return overlapWidth > 0 && overlapHeight > 0;
}
