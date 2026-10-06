/**
 * @param {string} s
 * @return {number}
 */
var minAddToMakeValid = function(s) {
    let open = 0;
    let additions = 0;
    
    for (let i = 0; i < s.length; i++) {
        let ch = s[i];
        if (ch === '(') {
            open++;
        } else {
            if (open > 0) {
                open--;
            } else {
                additions++;
            }
        }
    }
    
    return additions + open;
};
