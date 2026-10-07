/**
 * @param {string} s
 * @return {string[]}
 */
var removeInvalidParentheses = function(s) {
    const result = [];
    
    const remove = (str, start, checkStart, open, close) => {
        let balance = 0;
        
        for (let i = checkStart; i < str.length; i++) {
            const ch = str[i];
            if (ch === open) balance++;
            else if (ch === close) balance--;
            
            // Found an invalid closing parenthesis
            if (balance < 0) {
                for (let j = start; j <= i; j++) {
                    // Skip duplicate removals
                    if (j > start && str[j] === str[j - 1]) {
                        continue;
                    }
                    
                    // Remove this closing parenthesis
                    if (str[j] === close) {
                        const next = str.substring(0, j) + str.substring(j + 1);
                        remove(next, j, i, open, close);
                    }
                }
                return;
            }
        }
        
        // No invalid closing parentheses remain.
        // Reverse and check the opposite direction.
        const reversed = str.split('').reverse().join('');
        if (open === '(') {
            remove(reversed, 0, 0, ')', '(');
        } else {
            result.push(reversed);
        }
    };
    
    remove(s, 0, 0, '(', ')');
    return result;
};
