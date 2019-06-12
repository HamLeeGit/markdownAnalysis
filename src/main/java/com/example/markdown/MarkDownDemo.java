package com.example.markdown;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MarkDownDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("1111111111");
        //System.out.println(get());
        String html = get();
       // getDataByHtml(html);
    }

    public static String  get() throws Exception {
        String html = null;
//      FileReader fr = new FileReader("F:/test.md");  ==出现中文乱码
        InputStreamReader isr = new InputStreamReader(new FileInputStream("F:\\test.md"));
        char[] cbconf = new char[1024];
        while (isr.read(cbconf) != -1){
            html = new String(cbconf);
        }
        PegDownProcessor pdp = new PegDownProcessor(Extensions.ALL_WITH_OPTIONALS);
        html = pdp.markdownToHtml(html);
        htmlToFile(html);
        return  html;
    }

    public static void htmlToFile(String  html) throws  IOException{
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "\t\t<title></title>\n" +
                "\t</head>\n" +
                "\t<body>");
        html = html.replaceAll("<table>","<table border=\"1\">");
        System.out.println("2222222222222222222");
        System.out.println(html);
        sb.append(html);
        sb.append("</body>\n" +
                "</html>");
        //生成html文件
        File file = new File("D:"+File.separator+"MdToHtml.html");
        OutputStream  outputStream  = new FileOutputStream(file);
        byte[] b = sb.toString().getBytes();
        outputStream.write(b);
        outputStream.close();
    }


    public static  void getDataByHtml(String html){
        Document document = Jsoup.parse(html);
        Elements title = document.select("h3").get(0).select("a");
        //获取标题
        String titleName = title.get(0).text();
        System.out.println(titleName);
        //获取tbody中的行和列
        Elements rows = document.select("tbody").get(0).select("tr");
        //存放案列
        List<Map<Object,Object>> mapList = new ArrayList<Map<Object,Object>>();
        if (rows.size() == 0){
            System.out.println("没有结果");
        }else{
            for (int i = 0; i < rows.size(); i++) {
                Element row  = rows.get(i);
                Map<Object,Object> map = new HashMap<Object, Object>();
                map.put("al_name",row.select("td").get(0).text());
                map.put("condition",row.select("td").get(1).text());
                map.put("step",row.select("td").get(2).text());
                map.put("data",row.select("td").get(3).text());
                map.put("result",row.select("td").get(4).text());
                mapList.add(map);
            }
        }
        System.out.printf(mapList.toString());
        System.out.println("华丽的分割线---------------------------------");
        //遍历list
        for (Map<Object,Object> m : mapList){
            for (Object v : m.keySet()){
                System.out.println(v+":"+m.get(v));
            }
        }
    }
}
