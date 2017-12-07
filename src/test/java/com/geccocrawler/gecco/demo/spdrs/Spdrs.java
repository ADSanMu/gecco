package com.geccocrawler.gecco.demo.spdrs;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
import com.geccocrawler.gecco.spider.HtmlBean;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;
import org.apache.commons.lang3.StringUtils;
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
@Gecco(matchUrl = "https://{country}.spdrs.com/{anything:.*}", pipelines = "commonPipeline")
public class Spdrs implements HtmlBean, Pipeline<Spdrs> {

    private static Log log = LogFactory.getLog(Spdrs.class);
    private static final long serialVersionUID = -8870768223740844229L;
    public static final String BASE_PATH = "C:\\Users\\a637182\\TestWorkSpace/spdrs";

    @Request
    private HttpRequest request;

    @Href
    @HtmlField(cssPath = "a[href][href!='#']")
    private List<String> hrefs;

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


    /**
     * @param args
     */
    public static void main(String[] args) {

        HttpGetRequest httpRequest = new HttpGetRequest("https://us.spdrs.com/en");
        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo.spdrs")
                .thread(1)
                .start(httpRequest)
                .start();
    }

    @Override
    public void process(Spdrs bean) {
        HttpRequest request = bean.getRequest();
        String path = request.getPath();
        String html = bean.getHtml();
        String folder = path.substring(0, path.lastIndexOf("/"));
        File folderFile = new File(BASE_PATH + folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        if (StringUtils.isBlank(fileName) && (!new File(BASE_PATH + folder, "index.html").exists())) {
            fileName = "index.html";
        }
        if (!fileName.matches(".*\\.(css|js|pdf|xls|seam).*")) {
            fileName += ".html";
        }
        File file = new File(BASE_PATH + folder, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            if (fileName.matches(".*\\.(css|js)(\\?version=\\d*)")) {
                html = bean.getJsCssContent();
            } else {
                URL netUrl = new URL(request.getUrl());
                String port = ":" + netUrl.getPort();
                if (netUrl.getPort() == -1) {
                    port = "";
                }
                String prefix = netUrl.getProtocol() + "://" + netUrl.getHost() + port;
                html = process(html, request.getUrl());
            }
            writer.write(html);
            writer.flush();
            log.info("succeeded download file: " + fileName);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private String process(String content, String url) {
        //process html page content
        Document document = Jsoup.parse(content, url);
        Elements elements = document.select("a[href][href!='#'],script[src],link[href]");
        for (Element element : elements) {
            String attrName;
            if (element.hasAttr("href")) {
                attrName = "href";
            } else if (element.hasAttr("src")) {
                attrName = "src";
            } else continue;
            String attrValue = element.attr(attrName);

            if (attrValue.matches("/\\w.*\\.(|js|css)(\\?version=\\d*)") || (!attrValue.matches(".*\\.(pdf|xls|xlsx|doc).*"))) {
                String prefix = "https://us.spdrs.com";
                DeriveSchedulerContext.into(prefix + attrValue);
                attrValue = BASE_PATH + attrValue.replaceAll("/", "\\\\");
                element.attr(attrName, attrValue);
            }
        }
        return document.html();
    }
}
