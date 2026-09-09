#define MIN(a, b) ((a) < (b) ? (a) : (b))

long long countCommas(long long n) {
    long long total = 0;
    long long start = 1000;
    long long commas = 1;

    while (start <= n) {
        long long end = MIN(n, start * 1000 - 1);

        long long count = end - start + 1;
        total += count * commas;

        start *= 1000;
        commas++;
    }

    return total;
}
