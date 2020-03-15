public class RecursionTest {

    public static void main(String[] args) {

    }

    public static long factorial(int n){
        if (n == 0){
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static long fibonacci(int n){
        if (n == 0){
            return 0;
        } else if (n == 1){
            return 1;
        } else{
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void checkIfPalindrome(String s){
        System.out.println(s + " is a palindrome: " + isPalindrome(s));
    }

    public static boolean isPalindrome(String s){
        if (s.length() <= 1){
            return true;
        } else if (s.charAt(0) != s.charAt(s.length() - 1)){
            return false;
        } else {
            return isPalindrome(s.substring(1, s.length() - 1));
        }
    }
}
