package org.example.推荐与去重;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BloomFilterGuavaTest {

    public static void main(String[] args) throws Exception{
        BloomFilter<String> filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.forName("utf-8")),
                1024,
                0.001);

        //导入数据到filter
        for(int i = 0; i < 100; i++ ) {
            filter.put(i+"");
        }

        //数据持久化到本地
        File f= new File("C:\\Users\\User\\Desktop\\bloom.txt");
        OutputStream out = null;
        out = new FileOutputStream(f);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();


        filter.writeTo(stream);
        String finalString = new String(stream.toByteArray());
        System.out.println(finalString);


        BloomFilter<String> filter2 = BloomFilter.create(
                Funnels.stringFunnel(Charset.forName("utf-8")),
                1024,
                0.001);

        //将之前持久化的数据加载到Filter
        File f2= new File("C:\\Users\\User\\Desktop\\bloom.txt");
        InputStream in = null;
        in = new ByteArrayInputStream(finalString.getBytes(Charset.forName("utf-8")));
        try {
            filter2 = BloomFilter.readFrom(in,Funnels.stringFunnel(Charset.forName("utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //测试验证
        for(int i = 0 ; i < 10; i++) {
            boolean result = filter2.mightContain(i+"");

            if(result) {
                System.out.println("i = " + i + " " + result);
            }
        }
    }
}
