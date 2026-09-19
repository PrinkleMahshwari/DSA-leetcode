#include <stdbool.h>

#define MIN(a, b) ((a) < (b) ? (a) : (b))
#define MAX(a, b) ((a) > (b) ? (a) : (b))

bool checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
    int closestX = MAX(x1, MIN(xCenter, x2));
    int closestY = MAX(y1, MIN(yCenter, y2));

    int dx = closestX - xCenter;
    int dy = closestY - yCenter;

    return dx * dx + dy * dy <= radius * radius;
}
