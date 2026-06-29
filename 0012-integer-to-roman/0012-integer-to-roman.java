class Solution {
    public String intToRoman(int num) {

        // store all Roman values from largest to smallest
        // include subtractive forms (4, 9, 40, 90, 400, 90)
        int[] values = {1000, 900, 500, 400, 
            100, 90, 50, 40, 
            10, 9, 5, 4, 1
        };

        // corresponding Roman symbols
        String[] symbols = {"M", "CM", "D", "CD", 
            "C", "XC", "L", "XL", 
            "X", "IX", "V", "IV", "I"
        };

        // store final Roman numeral
        StringBuilder result = new StringBuilder();

        // iterate from largest to smallest value
        for (int i = 0; i < values.length; i++) {

            // keep using current Roman value until it no longer fits
            while (num >= values[i]) {

                //  append corresponding Roman symbol
                result.append(symbols[i]);

                // subtract current value from number
                num -= values[i];
            }
        }
        
        // convert StringBuilder to String
        return result.toString();
    }
}