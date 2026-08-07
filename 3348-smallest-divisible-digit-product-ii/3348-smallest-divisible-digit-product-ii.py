class Solution:
    def smallestNumber(self, num: str, t: int) -> str:
        # Explicit initialization to safeguard against text-stripping bugs
        PRIMES = list()
        PRIMES.append(2)
        PRIMES.append(3)
        PRIMES.append(5)
        PRIMES.append(7)

        def getMinDigitsNeeded(f2, f3, f5, f7):
            cnt9 = f3 // 2; f3 %= 2
            cnt8 = f2 // 3; f2 %= 3
            cnt6 = 0
            if f2 > 0 and f3 > 0: cnt6 = 1; f2 -= 1; f3 -= 1
            cnt4 = f2 // 2; f2 %= 2
            return f7 + f5 + cnt9 + cnt8 + cnt6 + cnt4 + f3 + f2

        def fillSmallestSuffix(buffer, startIdx, endIdx, f2, f3, f5, f7):
            idx = endIdx
            cnt9 = f3 // 2; f3 %= 2; 
            for _ in range(cnt9): buffer[idx] = '9'; idx -= 1
            cnt8 = f2 // 3; f2 %= 3; 
            for _ in range(cnt8): buffer[idx] = '8'; idx -= 1
            for _ in range(f7): buffer[idx] = '7'; idx -= 1
            if f2 > 0 and f3 > 0: buffer[idx] = '6'; idx -= 1; f2 -= 1; f3 -= 1
            for _ in range(f5): buffer[idx] = '5'; idx -= 1
            cnt4 = f2 // 2; f2 %= 2; 
            for _ in range(cnt4): buffer[idx] = '4'; idx -= 1
            if f3 > 0: buffer[idx] = '3'; idx -= 1
            if f2 > 0: buffer[idx] = '2'; idx -= 1
            while idx >= startIdx: buffer[idx] = '1'; idx -= 1

        def subtractDigit(f_arr, d):
            if d == 2: f_arr[0] = max(0, f_arr[0] - 1)
            elif d == 3: f_arr[1] = max(0, f_arr[1] - 1)
            elif d == 4: f_arr[0] = max(0, f_arr[0] - 2)
            elif d == 5: f_arr[2] = max(0, f_arr[2] - 1)
            elif d == 6: f_arr[0] = max(0, f_arr[0] - 1); f_arr[1] = max(0, f_arr[1] - 1)
            elif d == 7: f_arr[3] = max(0, f_arr[3] - 1)
            elif d == 8: f_arr[0] = max(0, f_arr[0] - 3)
            elif d == 9: f_arr[1] = max(0, f_arr[1] - 2)

        req = list()
        for _ in range(4):
            req.append(0)
            
        for i in range(4):
            while t % PRIMES[i] == 0: 
                req[i] += 1
                t //= PRIMES[i]
        if t > 1: return "-1"

        n = len(num)
        s = list(num)
        
        prefixStates = list()
        for _ in range(n + 1):
            temp_state = list()
            for _ in range(4):
                temp_state.append(0)
            prefixStates.append(temp_state)
            
        prefixStates[0] = list(req)

        validLen = 0
        for i in range(n):
            if s[i] == '0': break
            prefixStates[i + 1] = list(prefixStates[i])
            subtractDigit(prefixStates[i + 1], int(s[i]))
            validLen += 1

        if validLen == n and all(x == 0 for x in prefixStates[n]): return num

        for pos in range(min(n - 1, validLen), -1, -1):
            baseReq = prefixStates[pos]
            currentDigit = int(s[pos])
            for d in range(currentDigit + 1, 10):
                f2, f3, f5, f7 = baseReq[0], baseReq[1], baseReq[2], baseReq[3]
                if d in (2, 6, 4, 8): f2 = max(0, f2 - (1 if d == 2 else 1 if d == 6 else 2 if d == 4 else 3))
                if d in (3, 6, 9): f3 = max(0, f3 - (1 if d == 3 else 1 if d == 6 else 2))
                if d == 5: f5 = max(0, f5 - 1)
                if d == 7: f7 = max(0, f7 - 1)

                remLen = n - 1 - pos
                if getMinDigitsNeeded(f2, f3, f5, f7) <= remLen:
                    ans = [''] * n
                    ans[:pos] = s[:pos]
                    ans[pos] = str(d)
                    fillSmallestSuffix(ans, pos + 1, n - 1, f2, f3, f5, f7)
                    return "".join(ans)

        minDigitsNeeded = getMinDigitsNeeded(req[0], req[1], req[2], req[3])
        targetLen = max(n + 1, minDigitsNeeded)
        ans = [''] * targetLen
        fillSmallestSuffix(ans, 0, targetLen - 1, req[0], req[1], req[2], req[3])
        return "".join(ans)
