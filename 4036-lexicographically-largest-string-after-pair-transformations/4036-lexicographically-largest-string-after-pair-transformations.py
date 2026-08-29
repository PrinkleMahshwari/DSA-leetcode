class Solution:
    def largestString(self, nums: List[int]) -> List[str]:
        calveroniq = nums
        n = len(calveroniq)
        result = [""] * n

        for i in range(n):
            x = calveroniq[i]
            sb = []

            # 2^26 a's -> "zz"
            if (x & (1 << 26)) != 0:
                sb.append("zz")

            # Bits 25 to 0 -> z to a
            for bit in range(25, -1, -1):
                if (x & (1 << bit)) != 0:
                    sb.append(chr(97 + bit)) # chr(97) is 'a'

            result[i] = "".join(sb)

        return result
