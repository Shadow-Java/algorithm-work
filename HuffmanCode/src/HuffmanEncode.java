import java.io.*;
import java.util.*;

public class HuffmanEncode {
    private static int[] charWeight=new int[256];
    private static String[][] huffSting;//存储字符和对应得权重
    private static Tree huffmanTree=new Tree();
    private static String[] codes = new String[256];
    private static String huffmancode="";

    public static void acquireWeight(String str){//获取字符权重
        for(int i=0;i<str.length();i++){
            char ch=str.charAt(i);
            charWeight[ch]++;
        }
        int length=0;
        int min=1;
        for(int i=0;i<256;i++){
            if(charWeight[i]!=0){
                char now=(char)i;
                length++;
                System.out.print(now+":"+charWeight[i]+"    ");
            }
        }
        huffSting=new String[2][length];
        for(int i=0;i<length;i++){
            huffSting[0][i]="";
            huffSting[1][i]="";
        }

        int m=0;
        for(int i=0;i<256;i++){
            if(charWeight[i]!=0){
                char now=(char)i;
                huffSting[0][m]=now+"";
                huffSting[1][m]=charWeight[i]+"";
                m++;
            }
        }
    }
    public static void CreatHuffmanTree(String[][] huffSting){//生成哈夫曼二叉树
        Comparator<Tree> cmp = new Comparator<Tree>() {
            public int compare(Tree a, Tree b) { //这里是小根堆
                if (a.root.weight == b.root.weight) return a.root.weight - b.root.weight; //第二键值做参考
                return a.root.weight - b.root.weight; //第一键值做参考
            }
        };

        Queue<Tree> q1=new PriorityQueue<Tree>(cmp);//优先数队列，一直更新排序
        for(int i=0;i<huffSting[0].length;i++){
            q1.add(new Tree(Integer.parseInt(huffSting[1][i]),huffSting[0][i]));
        }
        while (q1.size()>1){
            Tree tree1=q1.peek();
            q1.poll();
            Tree tree2=q1.peek();
            q1.poll();
            Tree newTree=new Tree(tree1,tree2);
            q1.add(newTree);
        }
        huffmanTree=q1.peek();
    }
    public static void setCode(String str){//得到哈夫曼编码
        for(int i=0;i<str.length();i++){
            String temp=huffmancode;
            System.out.print(codes[str.charAt(i)]);
            huffmancode=temp+codes[str.charAt(i)];
        }

    }

    public static void printHuffmanCode(Tree.Node huffmanTree,String code){//打印哈夫曼编码
        if(huffmanTree.rchild==null&&huffmanTree.lchild==null){
            System.out.print(huffmanTree.element+":"+code+"  ");
            codes[huffmanTree.element.charAt(0)] =code;
        }
        if(huffmanTree.lchild!=null)printHuffmanCode(huffmanTree.lchild,code+"0");
        if(huffmanTree.rchild!=null)printHuffmanCode(huffmanTree.rchild,code+"1");
    }
    public static void Huffmandecode(String pathname){//哈夫曼解码

        Date a = new Date();
        String str="";//解码得编码
        try{
            String str1="";//源目标文件字符
            File file=new File("./"+pathname);
            FileInputStream InputStream=new FileInputStream(file);
            int r;
            while ((r = InputStream.read())!= -1){
                str1=str1+(char)r+"";
            }
            System.out.println("---------------------------------------------------------------------压缩文本文件内容为：---------------------------------------------------------------------");
            System.out.println(str1);
            int firstchar=(int)str1.charAt(0);//第一个字符表示数字有多少个字符，如4 2835
            int index=Integer.parseInt(str1.substring(1,1+firstchar));//哈夫曼编码的长度
            for(int i=1+firstchar;i<str1.length();i++){//转换成二进制的code
                if(Integer.toBinaryString(str1.charAt(i)).length()==8){
                    str=str+Integer.toBinaryString(str1.charAt(i));
                }else{
                    String tempStrw=""+Integer.toBinaryString(str1.charAt(i));
                    for(int j=0;j<(8-Integer.toBinaryString(str1.charAt(i)).length());j++){
                        tempStrw='0'+tempStrw;
                    }
                    str=str+tempStrw;
                }
            }
            str=str.substring(0,index);//截取huffmancode
            System.out.println("---------------------------------------------------------------------解码后的哈夫曼编码内容为：---------------------------------------------------------------------");
            System.out.println(str);
            int z=0;
            String alphaVariety="";//字符种类数
            if(index%8==0){
                z=index/8+1+firstchar;
                alphaVariety=str1.substring(z,z+1);
                str1=str1.substring(z+1,str1.length());
            }else{
                z=index/8+2+firstchar;
                alphaVariety=str1.substring(z,z+1);
                str1=str1.substring(z+1,str1.length());
            }
            String[][] HuffmanStr=new String[2][(int)alphaVariety.charAt(0)];//存字符和权重
            int mx=0;int mx1=0;char tempCar=0;
            for(int i=0;i<str1.length();){
                HuffmanStr[0][mx++]=str1.charAt(i)+"";
                tempCar=str1.charAt(i+1);
                HuffmanStr[1][mx1++]=str1.substring(i+2,i+2+(int)tempCar);
                i=i+2+(int)tempCar;
            }
            CreatHuffmanTree(HuffmanStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------------------------------解码后内容为：---------------------------------------------------------------------");
        Tree.Node temp=huffmanTree.root;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='0'){
                if(temp.lchild!=null){
                    temp=temp.lchild;
                }
            }else if(str.charAt(i)=='1'){
                if(temp.rchild!=null){
                    temp=temp.rchild;
                }
            }
            if(temp.rchild==null&&temp.lchild==null){
                System.out.print(temp.element);
                temp=huffmanTree.root;
            }
        }
        Date b = new Date();
        System.out.println("\n---------------------------------------------------------------------压缩解码用时为："+(b.getTime()-a.getTime())+"ms"+"---------------------------------------------------------------------");
    }
    public static void comprssFile(String pathname) throws IOException {//压缩
        File file=new File("./"+pathname);//源文件
        FileInputStream input = new FileInputStream(file);
        int r;String str="";
        while ((r = input.read()) != -1){
            str=str+(char)r+"";
        }
        System.out.println("---------------------------------------------------------------------获得txt文本内容：---------------------------------------------------------------------");
        System.out.println(str);
        for(int i=0;i<256;i++){
            charWeight[i]=0;
            codes[i]="";
        }
        System.out.println("---------------------------------------------------------------------获得权重：---------------------------------------------------------------------");
        acquireWeight(str);
        CreatHuffmanTree(huffSting);
        System.out.println("\n---------------------------------------------------------------------每个字符的编码如下：---------------------------------------------------------------------");
        printHuffmanCode(huffmanTree.root,"");
        System.out.println("\n---------------------------------------------------------------------哈夫曼编码如下：---------------------------------------------------------------------");
        setCode(str);
        fileStorge(pathname);
    }
    public static void fileStorge(String pathname) throws IOException{//将文件写入zip文件中
       String zipPathname=pathname.substring(0,pathname.lastIndexOf("."));
        File file1=new File("./"+zipPathname+".zip");//目标文件
        FileOutputStream output=new FileOutputStream(file1);
        char ch=0;String str="";int m=huffmancode.length()/8;
        String number=""+huffmancode.length();
        output.write((char)number.length());
        output.write(number.getBytes());//首先写入有多少个字符
        for(int i=1;i<=m*8;i++){
            str=str+huffmancode.charAt(i-1);
            if((i%8==0)&&(i/8)<=m){
                ch=(char)Integer.parseInt(str,2);
                output.write(ch);
                str="";
            }
        }
        String endStr="";
        if(huffmancode.length()%8!=0){
            endStr=""+huffmancode.substring(m*8,huffmancode.length());
            int n=huffmancode.length()%8;//最后剩余多少个字符
            for(int j=0;j<(8-n);j++){
                endStr=endStr+"1";
            }
            ch=(char)Integer.parseInt(endStr,2);
            output.write(ch);
        }


        /*将哈夫曼树存入zip文件   分三部分 ①字符 ②权重字节数 ③权重 */
        output.write((char)huffSting[0].length);//写入字符种类数
        for(int i=0;i<huffSting[0].length;i++){
            output.write(huffSting[0][i].charAt(0));//char型
            output.write((char)huffSting[1][i].length());//往后读几个字节
            output.write(huffSting[1][i].getBytes());
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException{//输入模式一 压缩，输入模式二 解码
        System.out.println("※☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆Huffman压缩与解压缩软件☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆※");
        System.out.println("☆        ※                       ※           ※                       ※        ☆");
        System.out.println("☆        ※                       ※           ※                       ※        ☆");
        System.out.println("☆          ※※※※※※※※※※※※※【1.压缩  】※※※※※※※※※※※※※          ☆ ");
        System.out.println("☆          ※※※※※※※※※※※※※【2.解压缩】※※※※※※※※※※※※※           ☆");
        System.out.println("☆          ※※※※※※※※※※※※※请在下面输入你需要得操作※※※※※※※           ☆");
        System.out.println("☆        ※                       ※           ※                       ※        ☆");
        System.out.println("☆        ※                       ※           ※                       ※        ☆");
        System.out.println("☆                                                                  ※ 作者：黎远波  ☆");
        System.out.println("☆                                                                  ※ 学号：19216228 ☆");
        System.out.println("※☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆※");
        Scanner sc=new Scanner(System.in);
        Scanner Stringsc=new Scanner(System.in);
        int x=0;
        String pathname="";
        for(;;){
            System.out.println("---------------------------------------------------------------------输入你需要操作的文件名---------------------------------------------------------------------");
            pathname=Stringsc.nextLine();//源文件名
            System.out.println("---------------------------------------------------------------------选择操作  1、压缩文件  2、解压文件---------------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------------你得操作是：---------------------------------------------------------------------");
            x=sc.nextInt();
            if(x==1){
                if(!pathname.endsWith(".txt")){
                    System.out.println("-------------------------------------------------------文件名不适合压缩，格式错误,请重新输入（.txt文件）！-------------------------------------------------------");
                    continue;
                }
                System.out.println("---------------------------------------------------------------------现在压缩目标文件---------------------------------------------------------------------");
                comprssFile(pathname);
            }else if(x==2){
                if(!pathname.endsWith(".zip")){
                    System.out.println("-------------------------------------------------------文件名不适合解压，格式错误,请重新输入（.zip文件）！-------------------------------------------------------");
                    continue;
                }
                System.out.println("---------------------------------------------------------------------现在解压目标文件---------------------------------------------------------------------");
                Huffmandecode(pathname);
            }else{
                System.out.println("---------------------------------------------------------------------不好意思，没有你想要的操作请重新输入!---------------------------------------------------------------------");
            }
        }


    }
}


class Tree{//哈夫曼树
    Node root;
    public Tree(){
    }
    public Tree(int weight,String element){
        root=new Node(weight,element);
    }
    public Tree(Tree tree0,Tree tree1){//两个子树链接成一个树
        this.root=new Node();//空根节点
        this.root.lchild=tree0.root;
        this.root.rchild=tree1.root;
        this.root.weight=tree0.root.weight+tree1.root.weight;
    }
    class Node{
        String element;//该结点字符
        Node lchild;//左子树
        Node rchild;//右子树
        int weight;//该字符的权重
        String code="";//该字符的编码
        public Node(){
        }
        public Node(int weight,String element){
            this.weight=weight;
            this.element=element;
        }
    }
}

