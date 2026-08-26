/**
 * @param {string} s
 * @param {number} k
 * @return {string}
 */
var shortestBeautifulSubstring = function(s, k) {
    const ones = [];

    // store positions of all 1s
    for (let i = 0; i < s.length; i++) {
        if (s[i] === '1') {
            ones.push(i);
        }
    }

    // Not enough 1s
    if (ones.length < k) {
        return "";
    }

    let answer = "";

    // try every possible starting 1
    for (let i = 0; i + k - 1 < ones.length; i++) {
        const start = ones[i];
        const end = ones[i + k - 1];

        const candidate = s.substring(start, end + 1);

        if (answer === "" 
                || candidate.length < answer.length 
                || (candidate.length === answer.length && candidate < answer)) {
            answer = candidate;
        }
    }

    return answer;
};
