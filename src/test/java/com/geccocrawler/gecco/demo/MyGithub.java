package com.geccocrawler.gecco.demo;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Gecco(pipelines = "consolePipeline", timeout = 1000)
public class MyGithub implements HtmlBean {

    private static final long serialVersionUID = -7127412585200687225L;

//    @Request
//    private HttpRequest request;
//
//    @RequestParameter("user")
//    private String user;
//
//    @RequestParameter("project")
//    private String project;


    @Href(click = true)
    @HtmlField(cssPath = "a")
    private List<String> contributors;

    public static void main(String[] args) {
        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo")
                //开始抓取的页面地址
                .start("https://github.com/xtuhcy/gecco")
                .start("https://github.com/xtuhcy/gecco-spring")
                //开启几个爬虫线程,线程数量最好不要大于start request数量
                .thread(2)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(2000)
                //循环抓取
                .loop(false)
                //采用pc端userAgent
                .mobile(false)
                //是否开启debug模式，跟踪页面元素抽取
                .debug(false)
                //非阻塞方式运行
                .start();
    }


    public List<String> getContributors() {
        return contributors;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }
}
