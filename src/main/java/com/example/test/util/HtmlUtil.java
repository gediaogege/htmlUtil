package com.example.test.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlUtil {
    private static HtmlUtil htmlUtil = new HtmlUtil();
    private HtmlUtil(){}
    public static HtmlUtil getInstance(){
        return htmlUtil;
    }

    /**
     * 读html文件
     * @param fileName
     * @return
     */
    public String readHtml(String fileName){
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(fileName);
            byte[] bytes = new byte[1024];
            while (-1 != fis.read(bytes)) {
                sb.append(new String(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 获取网页的链接
     * @param doc 网页的Document对象
     * @return 链接集合
     */
    public List<String> getLinks(Document doc){
        List<String>links = new ArrayList<>();
        Elements href = doc.select("a");
        for (Element element : href) {
            links.add(element.attr("href"));
        }
        Elements iframe = doc.select("iframe");
        for (Element element : iframe) {
            links.add(element.attr("src"));
        }
        Elements link = doc.select("link");
        for (Element element : link) {
            links.add(element.attr("href"));
        }
        Elements script = doc.select("script");
        for (Element element : script) {
            links.add(element.attr("src"));
        }
      return links;
    }


    public List<String> filterOutLinks(List<String> strings) {
        List<String> outLinks = new ArrayList<>();
        for (String link : strings) {
            if (link.startsWith("http")||link.startsWith("https")){
                outLinks.add(link);
            }
        }
        return outLinks;
    }
}
