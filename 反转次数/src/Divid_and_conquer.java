import java.util.Scanner;

/*
 **从形式上讲，如果a[i]> a [j]和i <j，则两个元素a [i]和a [j]形成反转
 */
public class Divid_and_conquer {
    static int count(int[] arr){
        int times=0;
        for(int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
                if(i<j&&arr[i]>arr[j]){
                    times++;
                }
            }
        }
        return times;
    }
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入数组的长度：");
        int length=scanner.nextInt();
        int arr[]=new int[length];
        System.out.println("请依次为该数组赋值：");
        for(int i=0;i<length;i++){
            arr[i]=scanner.nextInt();
        }
        System.out.println("该数组如下：");
        for(int i=0;i<length;i++){
            System.out.print(arr[i]+"  ");
        }
        System.out.println();
        System.out.println("该数组的反转次数为："+count(arr));
    }
}
