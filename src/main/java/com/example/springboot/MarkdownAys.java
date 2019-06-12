package com.example.springboot;


import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/md")
public class MarkdownAys {

    private static final String HTML_FILE = ".html";
    private static final String MD_FILE = ".md";

    /**
     * md转html
     */
    @RequestMapping("/mdTohtml")
    private static String  MarkdownToHtml() throws Throwable {
        String html = "";
        InputStreamReader isr = new InputStreamReader(new FileInputStream("F:\\test.md"));
        char [] chars = new char[10240];
        while (isr.read(chars) != -1){
            html = new String(chars);
        }
        //通过PegDownProcessor，将markdown转换成html
        PegDownProcessor pdp  = new PegDownProcessor(Extensions.ALL_WITH_OPTIONALS);
        html = pdp.markdownToHtml(html);
        //生成Html文件
        htmlToFile(html);
        return  html;
    }

    /**
     * html转md
     * @return
     */
    @RequestMapping("/htmlTomd")
    private static String HtmlToMarkdown() throws IOException {
        //自定义选择====使用ramark将表转换为简化的纯文本表示
        Options options = Options.markdown();
//        options.tables = Options.Tables.REMOVE;
        //与markdown extensions的兼容性，使用Options该类创建Remark具有附加功能的转换器
        Remark multiMarkdownRemark = new Remark(Options.multiMarkdown());
        File file = new File("F://markDown.html");
        String  markDown = multiMarkdownRemark.convert(file);
        //生成Markdown文件
        markDownToFile(markDown);
        return markDown;
    }

    /**
     *由html格式文件转换成markdown格式文件，生成md文件
     * @param markDown
     * @throws IOException
     */
    private static void markDownToFile(String markDown) throws IOException {
        String filePath = "D:"+File.separator+"htmlTomd"+MD_FILE;
        File file = new File(filePath);
        writeFile(file,markDown);
    }

    /**
     * 写入文件
     * @param file
     * @param fileStr
     * @throws IOException
     */
    private static void writeFile(File file,String fileStr) throws IOException {
        OutputStream outputStream  = new FileOutputStream(file);
        byte[] b = fileStr.toString().getBytes();
        outputStream.write(b);
        outputStream.close();
    }

    /**
     * 由md格式转换成html格式的字符串，生成html文件。
     * @param html
     * @throws IOException
     */
    private static void htmlToFile(String html) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "\t\t<title></title>\n" +
                "\t</head>\n" +
                "\t<body>");
        //添加table 的边框
        if (html.contains("<table>"))
        {
           html = html.replaceAll("<table>","<table border=\"1\">");
        }
        sb.append(html);
        sb.append("</body>\n" +
                "</html>");
        //生成html文件
        String filePath  = "D:"+File.separator+"mdTohtml"+HTML_FILE;
        File file = new File(filePath);
        writeFile(file,sb.toString());
       /* OutputStream outputStream  = new FileOutputStream(file);
        byte[] b = sb.toString().getBytes();
        outputStream.write(b);
        outputStream.close();*/
    }

    /**
     * 根据html的字符串，获取到md文档中对应的值
     * @param html
     */
    public static  void getDataByHtml(String html){
        Document document = Jsoup.parse(html);
        Elements title = document.select("h2").get(0).select("a");
        //获取标题
        String titleName = title.get(0).text();
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
        System.out.println("华丽的分割线----------------------------------");
        //遍历list
        for (Map<Object,Object> m : mapList){
            for (Object v : m.keySet()){
                System.out.println(v+":"+m.get(v));
            }
        }
    }
}
