/**
 * @param {string} s
 * @return {boolean}
 */
var isValid = function(s) {
    const stack = new Array(s.length);
    let top = -1;

    for (let i = 0; i < s.length; i++) {
        const ch = s[i];

        // opening brackets
        if (ch === '(' || ch === '[' || ch === '{') {
            stack[++top] = ch;
        } 
        // closing bracket
        else {
            if (top === -1) return false;

            const open = stack[top];

            if ((ch === ')' && open !== '(') || 
                (ch === ']' && open !== '[') || 
                (ch === '}' && open !== '{')) {
                return false;
            }

            top--;
        }
    }

    return top === -1;
};
