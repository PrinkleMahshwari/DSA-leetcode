class Solution {

    public List<Integer> sequentialDigits(int low, int high) {

        List<Integer> result = new ArrayList<>();
        String digits = "123456789";

        // sliding window over "123456789": every window of length L is
        // automatically a valid sequential-digit number, no need to build
        // digit-by-digit or check validity separately
        for (int length = 2; length <= 9; length++) {
            for (int start = 0; start + length <= 9; start++) {

                int num = Integer.parseInt(digits.substring(start, start + length));

                // numbers grow with window start for a fixed length, so once
                // we exceed high there's no point checking longer starts here
                if (num > high) break;

                if (num >= low) {
                    result.add(num);
                }
            }
        }

        // window lengths were iterated smallest to largest, but within each
        // length results are naturally increasing too, so a single sort
        // fixes any cross-length ordering issues if lengths overlap ranges
        Collections.sort(result);

        return result;
    }
}