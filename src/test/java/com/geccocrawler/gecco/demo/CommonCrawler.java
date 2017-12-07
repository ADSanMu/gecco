//package com.geccocrawler.gecco.demo;
//
//import com.geccocrawler.gecco.GeccoEngine;
//import com.geccocrawler.gecco.annotation.*;
//import com.geccocrawler.gecco.demo.down.TestDown;
//import com.geccocrawler.gecco.downloader.AfterDownload;
//import com.geccocrawler.gecco.pipeline.Pipeline;
//import com.geccocrawler.gecco.request.HttpGetRequest;
//import com.geccocrawler.gecco.request.HttpRequest;
//import com.geccocrawler.gecco.response.HttpResponse;
//import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
//import com.geccocrawler.gecco.scheduler.SchedulerContext;
//import com.geccocrawler.gecco.spider.HtmlBean;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.net.URL;
//import java.util.List;
//
//@PipelineName("commonPipeline")
//@Gecco(matchUrl = "http://localhost:4502/{anything:.*}", pipelines = "commonPipeline")
//public class CommonCrawler implements HtmlBean, Pipeline<CommonCrawler> {
//
//    private static Log log = LogFactory.getLog(CommonCrawler.class);
//    private static final long serialVersionUID = -8870768223740844229L;
//    public static final String BASE_PATH = "C:\\Users\\a637182\\TestWorkSpace";
//
//    @Request
//    private HttpRequest request;
//
//
//    @Href(click = true)
//    @HtmlField(cssPath = "a[href!='#'][href!=''][href!=' ']")
//    private List<String> hrefs;
//
//    @Attr("src")
//    @HtmlField(cssPath = "script[src^='/']")
//    private List<String> js;
//
//    @Attr("href")
//    @HtmlField(cssPath = "link[href^='/']")
//    private List<String> css;
//
//    @Html
//    @HtmlField(cssPath = "html")
//    private String html;
//
//    @JsCss
//    private String jsCssContent;
//
//    public String getJsCssContent() {
//        return jsCssContent;
//    }
//
//    public void setJsCssContent(String jsCssContent) {
//        this.jsCssContent = jsCssContent;
//    }
//
//    public List<String> getJs() {
//        return js;
//    }
//
//    public void setJs(List<String> js) {
//        this.js = js;
//    }
//
//    public List<String> getCss() {
//        return css;
//    }
//
//    public void setCss(List<String> css) {
//        this.css = css;
//    }
//
//    public String getHtml() {
//        return html;
//    }
//
//    public void setHtml(String html) {
//        this.html = html;
//    }
//
//    public HttpRequest getRequest() {
//        return request;
//    }
//
//    public void setRequest(HttpRequest request) {
//        this.request = request;
//    }
//
//    public List<String> getHrefs() {
//        return hrefs;
//    }
//
//    public void setHrefs(List<String> hrefs) {
//        this.hrefs = hrefs;
//    }
//
//    public static void main(String[] args) {
//
//        HttpGetRequest httpRequest = new HttpGetRequest("http://localhost:4502/content/ssmp/us/en/professional/index.html");
//        httpRequest.addCookie("login-token", "c294542f-1dfb-4eb9-bf7c-9b9ffc11bd89:4ee966b2-f474-4227-b971-09a0f6a1d183_747209f9c1fd3a8b:crx.default");
//        GeccoEngine.create()
//                .classpath("com.geccocrawler.gecco.demo")
//                .thread(3)
//                .start(httpRequest)
//                .interval(2000)
//                .start();
//    }
//
//    @Override
//    public void process(CommonCrawler bean) {
//        HttpRequest request = bean.getRequest();
//        String path = request.getPath();
//        String html = bean.getHtml();
//        String folder = path.substring(0, path.lastIndexOf("/"));
//        File folderFile = new File(BASE_PATH + folder);
//        if (!folderFile.exists()) {
//            folderFile.mkdirs();
//        }
//        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
//        if (StringUtils.isBlank(fileName) && (!new File(BASE_PATH + folder, "index.html").exists())) {
//            fileName = "index.html";
//        }
//        if (!fileName.matches(".*\\.(html|js|css)$")) {
//            log.info("ignore file:" + path);
//            return;
//        }
//        File file = new File(BASE_PATH + folder, fileName);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            if (fileName.endsWith(".html")) {
//                URL netUrl = new URL(request.getUrl());
//                String prefix = netUrl.getProtocol() + "://" + netUrl.getHost() + ":" + netUrl.getPort();
//                for (String j : bean.getJs()) {
//                    if (j.matches("/(app|etc).*\\.js$")) {
//                        DeriveSchedulerContext.into(prefix + j);
//                    }
//                }
//                for (String j : bean.getCss()) {
//                    if (j.matches("/(app|etc).*\\.css$")) {
//                        DeriveSchedulerContext.into(prefix + j);
//                    }
//                }
//                html = process(html, request.getUrl());
//            } else if (fileName.matches(".*\\.(css|js)$")) {
//                html = bean.getJsCssContent();
//            } else return;
//            writer.write(html);
//            writer.flush();
//            log.info("succeeded download file: " + fileName);
//        } catch (Exception e) {
//            log.error(e);
//        }
//    }
//
//    private String process(String content, String url) {
//        //process html page content
//        Document document = Jsoup.parse(content, url);
//        Elements elements = document.select("a[href][href!='#'],script[src^='/etc'],link[href^='/etc']");
//        for (Element element : elements) {
//            String attrName;
//            if (element.hasAttr("href")) {
//                attrName = "href";
//            } else if (element.hasAttr("src")) {
//                attrName = "src";
//            } else continue;
//            String attrValue = element.attr(attrName);
//            if (attrValue.matches("/etc/.*")) {
//                attrValue = BASE_PATH + attrValue.replaceAll("/", "\\\\");
//                element.attr(attrName, attrValue);
//            }
//        }
//        return document.html();
//    }
//}
