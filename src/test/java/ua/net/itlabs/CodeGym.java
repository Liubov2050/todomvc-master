package ua.net.itlabs;

/**
 * Created by liubovbarzakova on 10/19/15.
 */
public class CodeGym {
    public static void main(String[] args) {
        String input = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(check(input));
    }


    public static boolean check(String input) {
       boolean match = input.matches("\\w{1,2}^+[a-zA-Z].+");
        return match;
    }



    //Method returned int = 123 from char[] = {'1', '2', '3'}
    public static int join(char[] input){
        int i = Integer.parseInt(new String(input));
        return i;
    }
}
