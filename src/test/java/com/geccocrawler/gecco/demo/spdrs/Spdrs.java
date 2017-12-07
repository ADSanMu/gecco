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

    @Href(click = true)
    @HtmlField(cssPath = "a[href][href!='#']")
    private List<String> hrefs;

    @Attr("src")
    @HtmlField(cssPath = "script[src^='/'][type='text/javascript']")
    private List<String> js;

    @Attr("href")
    @HtmlField(cssPath = "link[href^='/'][rel='stylesheet']")
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


    /**
     * @param args
     */
    public static void main(String[] args) {

        HttpGetRequest httpRequest = new HttpGetRequest("https://us.spdrs.com/en/");
//        httpRequest.addHeader("Host", "us.spdrs.com");
//        httpRequest.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0");
//        httpRequest.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        httpRequest.addHeader("Accept-Language", "en-US,en;q=0.5");
//        httpRequest.addHeader("Accept-Encoding", "gzip, deflate, br");
//        httpRequest.addHeader("Cookie", "JSESSIONID=bcQv2gW4bHR-3neBp7rApr4BimcvtiKgZ19G_Gww3PjC2b10IzC3!428701573; TS016b0cfe=01098c5448539059ec855a7a2d4f9c7c9dcd82ec5892810a9ccba510421fd022b741eabe8806d5ee2729ba86107918d1f91bc6f340; InitialURL=us.spdrs.com; geoloc=us:en; role=def; disclaimer=accepted; AMCV_00CC7FBA5881402E0A495ECF%40AdobeOrg=2096510701%7CMCIDTS%7C17508%7CMCMID%7C06510195573592157954167922030574198676%7CMCAAMLH-1513236114%7C7%7CMCAAMB-1513236114%7C6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y%7CMCOPTOUT-1512638514s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C2.0.0; AMCVS_00CC7FBA5881402E0A495ECF%40AdobeOrg=1; WT_FPC=id=ea096266-45d7-4ee2-a3db-bdbe78ed9539:lv=1512584515618:ss=1512584515618; TS012b9124=01098c5448f99ddd795fc29851fa0cf93ef6dbb59f22290c39529d617ce00b082fddf1527b; s_cc=true; _mkto_trk=id:451-VAW-614&token:_mch-spdrs.com-1512631319816-84426; visitor_id42582=607970206; visitor_id42582-hash=b58bf1604d730366faf1ad98a2bf7ad36b7d1c6b90a70de5c354a957623f916a69ac9b4029f1cec476f1d02d60f0d5c93efd2b75");
//        httpRequest.addHeader("Connection", "keep-alive");
//        httpRequest.addHeader("Upgrade-Insecure-Requests", "1");
//        httpRequest.addHeader("Cache-Control", "max-age=0");
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
        if (!fileName.matches(".*\\.(css|js|pdf|xls|seam)$")) {
            fileName += ".html";
        }
        File file = new File(BASE_PATH + folder, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            if (fileName.matches(".*\\.(css|js)(\\?version=\\d*)$")) {
                html = bean.getJsCssContent();
            } else {
                URL netUrl = new URL(request.getUrl());
                String port = ":" + netUrl.getPort();
                if (netUrl.getPort() == -1) {
                    port = "";
                }
                String prefix = netUrl.getProtocol() + "://" + netUrl.getHost() + port;
                for (String j : bean.getJs()) {
                    if (j.matches(".*\\.(html|js|css)(\\?version=\\d*)$")) {
                        DeriveSchedulerContext.into(prefix + j);
                    }
                }
                for (String j : bean.getCss()) {
                    if (j.matches(".*\\.(html|js|css)(\\?version=\\d*)$")) {
                        DeriveSchedulerContext.into(prefix + j);
                    }
                }
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
            if (attrValue.matches(".*\\.(html|js|css)(\\?version=\\d*)$")) {
                attrValue = BASE_PATH + attrValue.replaceAll("/", "\\\\");
                element.attr(attrName, attrValue);
            }
        }
        return document.html();
    }
}
