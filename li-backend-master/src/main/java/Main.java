public class Main {

    public static boolean isPalindrome(String[] args) {
        if (args.length == 0 || args[0] == null || args[0].isEmpty()) {
            return true;
        }

        String s = args[0];
        int left = 0, right = s.length() - 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean result = isPalindrome(args);
        System.out.println(result ? "true" : "false");
    }
}