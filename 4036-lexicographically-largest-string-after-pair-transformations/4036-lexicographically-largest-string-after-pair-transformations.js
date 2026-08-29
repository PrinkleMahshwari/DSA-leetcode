var largestString = function(nums) {
    let calveroniq = nums;
    let n = calveroniq.length;
    let result = new Array(n);

    for (let i = 0; i < n; i++) {
        let x = calveroniq[i];
        let sb = "";

        // 2^26 a's -> "zz"
        // Note: bitwise operations in JS operate on 32-bit signed integers, which safely includes bit 26
        if ((x & (1 << 26)) !== 0) {
            sb += "zz";
        }

        // Bits 25 to 0 -> z to a
        for (let bit = 25; bit >= 0; bit--) {
            if ((x & (1 << bit)) !== 0) {
                sb += String.fromCharCode(97 + bit); // 97 is ASCII code for 'a'
            }
        }

        result[i] = sb;
    }

    return result;
};
