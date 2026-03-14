import java.util.HashMap;
import java.util.Map;

public class MinimumSubstring {
    //Minimum Window Substring: Find the smallest substring that contains all characters of a target
    // string by expanding the window to include characters and shrinking it to maintain validity.
    public static void main(String[] args) {
        String s = "ADOBECODEBAANC";
        String t = "AABC";
        System.out.println(minWindow(s, t)); // Output: "BANC"
    }

    private static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        String result = "";
        int l = 0;
        Map<Character, Integer> targetCharCount = new HashMap<>();
        for(Character c: t.toCharArray()){
            targetCharCount.put(c, targetCharCount.getOrDefault(c, 0) + 1);
        }
        for(int r = 0;r < s.length(); r++){
            Character c = s.charAt(r);
            // If the current character is part of the target, decrease its count in the map
            if(targetCharCount.containsKey(c)){
                targetCharCount.put(c, targetCharCount.get(c) - 1);
            }
            //check if the current window is valid (contains all characters of t)
            while(isValid(targetCharCount)) {
                // Update the result if the current window is smaller than the previously found minimum
                if (result.isEmpty() || r - l + 1 < result.length()) {
                    result = s.substring(l, r + 1);
                }
                // Try to shrink the window from the left
                Character leftChar = s.charAt(l);
                if (targetCharCount.containsKey(leftChar)) {
                    targetCharCount.put(leftChar, targetCharCount.get(leftChar) + 1);
                }
                l++; // Move the left pointer to shrink the window

            }
        }
        return result;

    }

    private static boolean isValid(Map<Character, Integer> targetCharCount) {
        for(Integer count: targetCharCount.values()){
            if(count > 0){
                return false;
            }
        }
        return true;
    }



    public String minWindowMoreOptimal(String s, String t) {

        Map<Character, Integer> target = new HashMap<>();
        for(char c : t.toCharArray())
            target.put(c, target.getOrDefault(c,0)+1);

        int left = 0;
        int right = 0;

        int formed = 0;
        int required = target.size();

        Map<Character, Integer> window = new HashMap<>();

        int minLen = Integer.MAX_VALUE;
        int start = 0;

        while(right < s.length()){

            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c,0)+1);

            if(target.containsKey(c) &&
                    window.get(c).intValue() == target.get(c).intValue())
                formed++;

            while(left <= right && formed == required){

                if(right-left+1 < minLen){
                    minLen = right-left+1;
                    start = left;
                }

                char l = s.charAt(left);
                window.put(l, window.get(l)-1);

                if(target.containsKey(l) &&
                        window.get(l) < target.get(l))
                    formed--;

                left++;
            }

            right++;
        }

        return minLen == Integer.MAX_VALUE ?
                "" : s.substring(start,start+minLen);
    }
}
