import java.util.Scanner;

/*分治法
**给定一个字符串，如果分区的每个子字符串都是回文，则字符串的分区是回文分区
* 动态规划 弄个表example一步一推出来
 */
public class dynamic_program {

    static int  minSplit_str(String s){//最少分割次数
        if(s.length()==0||s==null){ //0~i的最少分割次数
            return 0;
        }
        int[] dp=new int[s.length()];
        dp[0]=0;
        for (int i = 1; i <s.length(); i++) {
            dp[i]=Palindrome(s.substring(0,i+1))?0:i;
            for (int j = i; j >=1 ; j--) {
                if (Palindrome(s.substring(j,i+1))){
                    dp[i]=Math.min(dp[i],dp[j-1]+1);
                }
            }
        }
        return dp[s.length()-1];
    }

    static boolean Palindrome(String str1){//判断分割的字符是否为回
        int begin=0;int end=str1.length()-1;
        while (begin<end)
        if(str1.charAt(begin++) != str1.charAt(end--)) return false;
        return true;
    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入一个随机字符串：");
        String str=scanner.nextLine();
        System.out.println("该字符串的最少分割次数为："+minSplit_str(str));
    }
}
