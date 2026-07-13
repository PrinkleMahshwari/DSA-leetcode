class Solution {

    public List<Integer> sequentialDigits(int low, int high) {

        List<Integer> result = new ArrayList<>();

        // build numbers directly via arithmetic instead of substring + parseInt,
        // avoids String slicing and parsing overhead entirely
        for (int length = 2; length <= 9; length++) {
            for (int start = 1; start + length - 1 <= 9; start++) {

                int num = 0;
                for (int d = start; d < start + length; d++) {
                    num = num * 10 + d;
                }

                if (num > high) break; // increasing start only grows num further, stop early

                if (num >= low) {
                    result.add(num);
                }
            }
        }

        // no sort needed: length ascending + start ascending already yields
        // globally increasing values, since any number with more digits is
        // always larger than one with fewer digits
        return result;
    }
}