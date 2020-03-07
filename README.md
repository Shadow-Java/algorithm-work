# :pencil2: algorithm-work
update time:2020/3/7</br>
Auto created by [algorithm-work](https://github.com/liyuanbo1997/algorithm-work)

KEYWORDS:`反转`、`回文`、`哈夫曼`、`压缩与解压缩`</br>
## 章节总览</br>
| # | Title | Topic |
|:---:|:---:|:---:|
| 1 | [反转次数](./反转次数) | a[i]>a[j]和i<j,则a[i]和a[j]形成反转 |
| 2 |[回文分区](./Dynamic_program) |  给定一个字符串，如果分区的每个子字符串都是回文，则字符串的分区是回文分区 |
| 3 | [HuffmanCode](./HuffmanCode) | 实现压缩和解压缩功能的软件

 <h1 align="center">反转次数</h1></br>
 ## 实验要求和目的
    实验目的：1.掌握分治算法的基本思想（分-治-合）、技巧和效率分析方法。
          2.熟练掌握用递归设计分治算法的基本步骤（基准与递归方程）。 
          3.学会利用分治算法解决实际问题。 
    实验要求：采用分治法，实现输入一个数组，计算出数组的反转次数（即逆序个数）
## 实验环境（实验设备）
    硬件： 笔记本电脑
    软件：IntelliJ IDEA 
    语言实现：Java
## 实验原理和内容
+ 实验原理</br>
&emsp;分治法思想：分治法，即分而治之也。将一个难以解决的大问题划分为一些规模较小的子问题，分别求解各个子问题，然后合并子问题的解得到原问题的解。</br>
&emsp;在一个排列中，如果一对数的前后位置与大小顺序相反，即前面的数大于后面的数，那么它们就称为一个逆序。一个排列中逆序的总数就称为这个排列的逆序数。如2431中，21，43，41，31是逆序。分治法求逆序数相当于在归并排序的过程中加上相应的逆序数的数目。而归并排序的基本算法原理是：将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序，即将原序列先两两分割到最小单位，再将分割后的序列不断两两组合，排列成有序的集合，也称为二路归并。</br>
&ensp;以下为一个例子：</br>
&ensp;设有数列{6，202，100，301，38，8，1}</br>
&ensp;初始状态：6,202,100,301,38,8,1</br>
&emsp;第一次归并后：{6,202},{100,301},{8,38},{1}，比较次数：3；</br>
&emsp;第二次归并后：{6,100,202,301}，{1,8,38}，比较次数：4；</br>
&emsp;第三次归并后：{1,6,8,38,100,202,301},比较次数：4；</br>
&ensp;总的比较次数为：3+4+4=11；
&ensp;逆序数为14；

+ 实验内容</br>
①总体思路
&emsp;统计一个数组的反转次数时，先将一个数组采用迭代二分，统计两个数组的反转次数，并将两个数组变成有序数组，再统计整体数组的反转次数。</br>
<img align="center" src="https://picturestr.oss-cn-shanghai.aliyuncs.com/img/20200307221236.png"/></br>
&emsp;②基本流程</br>
1）将原数组划分为两个子数组</br>
2）对两个数组进行排序</br>
3）将两个数组有序的结合，合成一个有序数组，计算反转次数
&emsp;③具体函数实现</br>
算法的主要内容分为“分”和“治”两部分。</br>
&emsp;&ensp;1）分即把未排序的数组采用迭代的方式二分。
```Java
void MergeSort(int start,int end) {
		if(start<end) {
			int middle=(start+end)/2;
			MergeSort(start,middle);
			MergeSort(middle+1,end);
			Merge(start,middle,end);
		}
	}
```
&ensp;&emsp;2）其中Merge函数是“治”部分的函数。
治就是把二分后的数组不断按照有序的方式组合，合并成一个有序的数组。</br>
下面介绍Merge函数：</br>
&ensp;start到middle为第一部分，middle+1到end为第二部分，Merge函数就是把这两部分变为有序的数组。

```Java
void Merge(int start,int middle,int end) {
		int ptr0=0;
		//指向第一段开始
		int ptr1=start;
		//指向第二段开始
		int ptr2=middle+1;
		
				while(ptr1<=middle && ptr2<=end) {
			if(this.Origin[ptr1]<this.Origin[ptr2]) {
				temp[ptr0++]=Origin[ptr1++];
			}
			else {
				temp[ptr0++]=Origin[ptr2++];
				Res=Res+middle-ptr1+1;//记录逆序数
			}
		}
		while(ptr1<=middle) {
			temp[ptr0++]=Origin[ptr1++];
		}
		while(ptr2<=end) {
			temp[ptr0++]=Origin[ptr2++];
		}
		//将排序后的结果返回
		for(int i=0;i<end-start+1;i++) {
			Origin[start+i]=temp[i];
		}	
	}
  ```
  
&emsp;Ptr0为指向temp的指针，用来存放排序后的数据，ptr1为第一段数据的指针，ptr2为第二段数据的指针。第一个while循环用来比较第一段和第二段的数据的大小，有序的数据放入temp数组中，剩下的两个while循环用来将两段数据中末尾未放入temp数组中。至此temp数组中放的就是本次Merge函数将两段数据整合成一段有序数据的结果。
# 复杂性分析
&ensp;该程序时间复杂度为：O(n)= $\log$ (n)</br>
&ensp;空间复杂度分析：int arr[]=new int[length]分配空间，其他不分配空间</br>
&ensp;则空间复杂度：O(n)=n
