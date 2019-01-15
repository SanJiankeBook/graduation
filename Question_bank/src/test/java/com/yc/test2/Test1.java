package com.yc.test2;
import java.io.BufferedInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.zip.CRC32;  
import java.util.zip.CheckedOutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
//使用org.apache.tools.zip这个就不会中文乱码  
import org.apache.tools.zip.ZipEntry;  
import org.apache.tools.zip.ZipOutputStream;  
  
//使用java.util.zip原生ZipOutputStream与ZipEntry会中文乱码  
  
//import java.util.zip.ZipOutputStream;  
//import java.util.zip.ZipEntry;  
  
/** 
 * 作者:Code菜鸟 
 * 博客:http://blog.csdn.net/qq969422014/article/category/2944339 
 * **/  
public class Test1   
{  
    static String filePath = "C:/Users/Administrator/Desktop/springmvc_book.sql";//需要压缩的文件夹完整路径  
    static String fileName = "springmvc_book.sql";//需要压缩的文件夹名  
    static String outPath = "C:/Users/Administrator/Desktop/Test.zip";//压缩完成后保存为Test.zip文件，名字随意  
      
    public static void main(String args[]) throws Exception  
    {  
        OutputStream is = new FileOutputStream(outPath);//创建Test.zip文件  
        CheckedOutputStream cos = new CheckedOutputStream(is, new CRC32());//检查输出流,采用CRC32算法，保证文件的一致性  
        ZipOutputStream zos = new ZipOutputStream(cos);//创建zip文件的输出流  
        zos.setEncoding("UTF-8");//设置编码，防止中文乱码  
        File file = new File(filePath);//需要压缩的文件或文件夹对象  
        ZipFile(zos,file);//压缩文件的具体实现函数  
        zos.close();  
        cos.close();  
        is.close();  
        System.out.println("压缩完成");  
    }  
      
    //递归，获取需要压缩的文件夹下面的所有子文件,然后创建对应目录与文件,对文件进行压缩  
    public static void ZipFile(ZipOutputStream zos,File file) throws Exception  
    {  
        if(file.isDirectory())  
        {  
            //创建压缩文件的目录结构  
            zos.putNextEntry(new ZipEntry(file.getPath().substring(file.getPath().indexOf(fileName))+File.separator));  
  
            for(File f : file.listFiles())  
            {  
                ZipFile(zos,f);  
            }  
        }  
        else  
        {  
            //打印输出正在压缩的文件  
            System.out.println("正在压缩文件:"+file.getName());  
            //创建压缩文件  
            zos.putNextEntry(new ZipEntry(file.getPath().substring(file.getPath().indexOf(fileName))));  
            //用字节方式读取源文件  
            InputStream is = new FileInputStream(file.getPath());  
            //创建一个缓存区  
            BufferedInputStream bis = new BufferedInputStream(is);  
            //字节数组,每次读取1024个字节  
            byte [] b = new byte[1024];  
            //循环读取，边读边写  
            while(bis.read(b)!=-1)  
            {  
                zos.write(b);//写入压缩文件  
            }  
            //关闭流  
            bis.close();  
            is.close();  
        }  
    }
   
}  