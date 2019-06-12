package com.example.markdown;

import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;

import java.io.File;
import java.io.IOException;

public class HtmlToMarkdown {

	//我添加的
    public static void main(String[] args) {
        //自定义选择====使用ramark将表转换为简化的纯文本表示
        Options options = Options.markdown();
//        options.tables = Options.Tables.REMOVE;
        //与markdown extensions的兼容性，使用Options该类创建Remark具有附加功能的转换器
        Remark multiMarkdownRemark = new Remark(Options.multiMarkdown());
        String html = "<h1>你好</h1><table>\n" +
                "    <thead>\n" +
                "       <tr>\n" +
                "         <th>&nbsp;</th>\n" +
                "         <th colspan=\"2\">Grouping</th>\n" +
                "       </tr>\n" +
                "       <tr>\n" +
                "         <th>First Header</th>\n" +
                "         <th>Second Header</th>\n" +
                "         <th>Third Header</th>\n" +
                "       </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "       <tr>\n" +
                "         <td>Content</td>\n" +
                "         <td colspan=\"2\" align=\"center\"><em>Long Cell</em></td>\n" +
                "       </tr>\n" +
                "       <tr>\n" +
                "         <td>Content</td>\n" +
                "         <td align=\"center\"><strong>Cell</strong></td>\n" +
                "         <td align=\"right\">Cell</td>\n" +
                "       </tr>\n" +
                "    </tbody>\n" +
                "    <tbody>\n" +
                "       <tr>\n" +
                "         <td>New Section</td>\n" +
                "         <td align=\"center\">More</td>\n" +
                "         <td align=\"right\">Data</td>\n" +
                "       </tr>\n" +
                "       <tr>\n" +
                "         <td>And more</td>\n" +
                "         <td colspan=\"2\" align=\"center\">And more</td>\n" +
                "       </tr>\n" +
                "    </tbody>\n" +
                "</table>";
        File file = new File("F://markDown.html");
        String markDown = null;
        try {
            markDown = multiMarkdownRemark.convert(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------");
        System.out.println(markDown);
    }
}
