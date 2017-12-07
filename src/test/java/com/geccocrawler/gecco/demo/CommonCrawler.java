package com.geccocrawler.gecco.demo;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.demo.down.TestDown;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.geccocrawler.gecco.spider.HtmlBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;

@PipelineName("commonPipeline")
@Gecco(matchUrl = "http://localhost:4502/{anything:.*}", pipelines = "commonPipeline")
public class CommonCrawler implements HtmlBean, Pipeline<CommonCrawler> {

    private static Log log = LogFactory.getLog(CommonCrawler.class);
    private static final long serialVersionUID = -8870768223740844229L;
    public static final String BASE_PATH = "C:\\Users\\a637182\\TestWorkSpace";

    @Request
    private HttpRequest request;


    @Href(click = true)
    @HtmlField(cssPath = "a[href!='#'][href!=''][href!=' ']")
    private List<String> hrefs;

    @Attr("src")
    @HtmlField(cssPath = "script[src^='/']")
    private List<String> js;

    @Attr("href")
    @HtmlField(cssPath = "link[href^='/']")
    private List<String> css;

    @Html
    @HtmlField(cssPath = "html")
    private String html;

    @JsCss
    private String jsCssContent;

    public String getJsCssContent() {
        return jsCssContent;
    }

    public void setJsCssContent(String jsCssContent) {
        this.jsCssContent = jsCssContent;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public List<String> getHrefs() {
        return hrefs;
    }

    public void setHrefs(List<String> hrefs) {
        this.hrefs = hrefs;
    }

    public static void main(String[] args) {
        HttpGetRequest httpRequest = new HttpGetRequest("http://localhost:4502/content/ssmp/us/en/professional/index.html");
        httpRequest.addCookie("login-token", "c294542f-1dfb-4eb9-bf7c-9b9ffc11bd89:cb0802f9-45a8-4cc9-8b68-3b65c67b5a9a_0a5891a01b193414:crx.default");
        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo")
                .thread(1)
                .start(httpRequest)
                .interval(2000)
                .start();
    }

    @Override
    public void process(CommonCrawler bean) {
        String url = bean.getRequest().getUrl();

        HttpRequest request = bean.getRequest();
        String html = bean.getHtml();
        String path = request.getPath();
        String folder = path.substring(0, path.lastIndexOf("/"));
        String htmlName = path.substring(path.lastIndexOf("/") + 1, path.length());
        File file = new File(BASE_PATH + folder, htmlName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            if (url.endsWith(".html")) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(html);
                writer.flush();
                writer.close();
                URL netUrl = new URL(url);
                String prefix = netUrl.getProtocol() + "://" + netUrl.getHost() + ":" + netUrl.getPort();
                for (String j : bean.getJs()) {
                    if (j.matches("/etc.*\\.js")) {
                        DeriveSchedulerContext.into(new HttpGetRequest(prefix + j));
                    }
                }
                for (String j : bean.getCss()) {
                    if (j.matches("/etc.*\\.css")) {
                        DeriveSchedulerContext.into(new HttpGetRequest(prefix + j));
                    }
                }
            } else if (url.endsWith(".js") || url.endsWith(".css")) {

            }

            log.info("downloaded page" + url);
        } catch (Exception ignored) {
            System.out.println(ignored);
        }
    }

}
