class Solution:
    def totalNumbers(self, digits: list[int]) -> int:
        freq = [0] * 10

        for digit in digits:
            freq[digit] += 1
        
        count = 0

        for num in range(100, 1000):
            # Skip odd numbers
            if (num & 1) != 0:
                continue
            
            a = num // 100
            b = (num // 10) % 10
            c = num % 10

            used = [0] * 10
            used[a] += 1
            used[b] += 1
            used[c] += 1

            possible = True

            for d in range(10):
                if used[d] > freq[d]:
                    possible = False
                    break

            if possible:
                count += 1

        return count
