/**
 * @param {string} s
 * @param {string[][]} knowledge
 * @return {string}
 */
var evaluate = function(s, knowledge) {
    const map = new Map();

    // store key-value pairs
    for (let i = 0; i < knowledge.length; i++) {
        map.set(knowledge[i][0], knowledge[i][1]);
    }

    const result = [];
    let i = 0;

    while (i < s.length) {
        // normal character
        if (s[i] !== '(') {
            result.push(s[i]);
            i++;
            continue;
        }

        // find closing bracket
        let j = i + 1;
        while (s[j] !== ')') {
            j++;
        }

        // extract key
        const key = s.substring(i + 1, j);

        // replace with value or '?'
        result.push(map.has(key) ? map.get(key) : "?");

        // move after ')'
        i = j + 1;
    }

    return result.join('');
};
