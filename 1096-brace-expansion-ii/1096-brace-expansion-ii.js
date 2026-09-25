/**
 * @param {string} expression
 * @return {string[]}
 */
var braceExpansionII = function(expression) {
    let index = 0;

    function parseExpression() {
        let result = parseTerm();

        while (index < expression.length && expression[index] === ',') {
            index++; // skip ','
            let next = parseTerm();
            for (let str of next) {
                result.add(str);
            }
        }
        return result;
    }

    function parseTerm() {
        let result = new Set([""]);

        while (index < expression.length && expression[index] !== '}' && expression[index] !== ',') {
            let next = parseFactor();
            result = combine(result, next);
        }
        return result;
    }

    function parseFactor() {
        if (expression[index] === '{') {
            index++; // skip '{'
            let result = parseExpression();
            index++; // skip '}'
            return result;
        }

        let result = new Set();
        result.add(expression[index]);
        index++;
        return result;
    }

    function combine(first, second) {
        let result = new Set();
        for (let a of first) {
            for (let b of second) {
                result.add(a + b);
            }
        }
        return result;
    }

    let resultSet = parseExpression();
    let answer = Array.from(resultSet);
    answer.sort();
    return answer;
};
