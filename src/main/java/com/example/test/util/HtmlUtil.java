package com.example.test.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 获取符合条件的链接
     * @param links 网页的全部链接
     * @param isOutLinks  true：是获取外链接 ；false:是获取内链接
     * @param patterns 链接的过滤条件，不传表示不用过滤
     * @return 符合条件的链接集合
     */
    public List<String> filterLinks(List<String>links,boolean isOutLinks,String... patterns){
       List<String> result = new ArrayList<>();
       if(isOutLinks){
           result = filterOutLinks(links);
       }else {
           for (String link : links) {
               if (!link.startsWith("http")&&!link.startsWith("https")){
                   result.add(link);
               }
           }
       }
       if(patterns!=null&&patterns.length>0){
           Iterator<String> iterator = result.iterator();
           while (iterator.hasNext()){
               for (String reg : patterns) {
                   //只要是不符合传入正则表达式规则的都移除
                    if(!isPatternlink(iterator.next(),reg)){
                        iterator.remove();
                    }
               }
           }

       }
       return result;
    }

    /**
     * 通过正则表达式获取匹配的Link
     * @param link 目标链接
     * @param reg  正则表达式
     * @return true 匹配
     */
    public boolean isPatternlink(String link,String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(link);
        return matcher.matches();

    }
}
