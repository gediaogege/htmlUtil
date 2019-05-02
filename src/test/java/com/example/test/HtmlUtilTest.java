package com.example.test;

import com.example.test.util.HtmlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HtmlUtilTest {
    @Test
    public void test1() {
        HtmlUtil instance = HtmlUtil.getInstance();
        String html = instance.readHtml("/home/qww/data/workspace/test/test/src/main/resources/static/test.html");
        Document doc = Jsoup.parse(html);
        List<String> strings = instance.getLinks(doc);
        List<String> outLinks = instance.filterOutLinks(strings);
        System.out.println(outLinks.toString());


    }

    @Test
    public void filterLinks() {
        HtmlUtil instance = HtmlUtil.getInstance();
        String html = instance.readHtml("/home/qww/data/workspace/test/test/src/main/resources/static/test.html");
        Document doc = Jsoup.parse(html);
        List<String> strings = instance.getLinks(doc);
        //根据条件过滤
        List<String> links = instance.filterLinks(strings, true,".*\\d+.*");
        System.out.println(links.toString());
    }
}
