package com.geccocrawler.gecco.demo;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
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

    @Href
    @HtmlField(cssPath = "a")
    private List<String> contributors;


    /**
     * "; WT_FPC=id=6049ce07-7997-4578-b747-c433e5ed4512:lv=1512434053365:ss=1512432721153; s_cc=true; _mkto_trk=id:451-VAW-614&token:_mch-spdrs.com-1512479725160-77807"
     *
     * @param args
     */

    public static void main(String[] args) {
        HttpGetRequest httpRequest = new HttpGetRequest();
        httpRequest.addCookie("Global_URL","global.spdrs.com");
        httpRequest.addCookie("countryKey","uk");
        httpRequest.addCookie("fundDomInKey","All");
        httpRequest.addCookie("TS016b0cfe","01098c5448fb0e09f57f00c349e9c32e9b325b20f21b71602174d3c2a9aa9fd3e76765af6fb54b8304012fc1ea5c56c04aa10ab5b83abae602fca3a1f480b2888c893f96dacf3c97dfe9a539a5bdcaa86eb127c60259c7770bf3fdd049ff9639cbad77eab1f1fb7d131ad0d74c146390ef6e7a21a571e7e7103f349a33c34b7289639f0783");
        httpRequest.addCookie("TS01d5ee7b","01098c54481440e383cb47c1971e749f130abd17c53ffb898df06178983d3d2aa32f646a41d2075b39fe8bc51c92a289b2e8d06584");
        httpRequest.addCookie("InitialURL","uk.spdrs.com");
        httpRequest.addCookie("AMCVS_00CC7FBA5881402E0A495ECF%40AdobeOrg","1");
        httpRequest.addCookie("AMCV_00CC7FBA5881402E0A495ECF%40AdobeOrg","2096510701%7CMCIDTS%7C17506%7CMCMID%7C69154673269861499120113527763257769376%7CMCAAMLH-1513084524%7C11%7CMCAAMB-1513084524%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1512486924s%7CNONE%7CMCAID%7CNONE%7CMCSYNCSOP%7C411-17513%7CvVersion%7C2.0.0");
        httpRequest.addCookie("org.jboss.seam.core.Locale","en");
        httpRequest.addCookie("TS012b9124","01098c5448b5a1026ef8d1d0664ccf892e90e5cf04003308e7d85aa48e0c1bde5fb0ed541af0e9a7114e31e68af71478186837a515cde4bf516e32595831573b50fa0679e19eafeaeb591a0bde183024914a72c0f6");
        httpRequest.addCookie("Global_URL","global.spdrs.com");
        httpRequest.addCookie("Global_URL","global.spdrs.com");
        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo")
                //开始抓取的页面地址
                .start("https://uk.spdrs.com/en/professional")
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
