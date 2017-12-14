//package com.geccocrawler.gecco.demo.spdrs;
//
//import com.geccocrawler.gecco.GeccoEngine;
//import com.geccocrawler.gecco.annotation.*;
//import com.geccocrawler.gecco.pipeline.Pipeline;
//import com.geccocrawler.gecco.request.HttpGetRequest;
//import com.geccocrawler.gecco.request.HttpRequest;
//import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;
//import com.geccocrawler.gecco.spider.HtmlBean;
//import com.geccocrawler.gecco.spider.SpiderThreadLocal;
//import com.google.common.collect.Sets;
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
//import java.io.UnsupportedEncodingException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Pattern;
//
//import static java.util.regex.Pattern.CASE_INSENSITIVE;
//
//@PipelineName("commonPipeline")
//@Gecco(matchUrl = "https://be.spdrs.com/{anything:.*}", pipelines = "commonPipeline")
//public class Spdrs implements HtmlBean, Pipeline<Spdrs> {
//
//    private static Log log = LogFactory.getLog(Spdrs.class);
//    private static final long serialVersionUID = -8870768223740844229L;
//    public static final String BASE_PATH = "C:\\Users\\a637182\\TestWorkSpace\\spdrs";
//    public static final Set<String> FINISHED_URLS = Sets.newConcurrentHashSet();
//
//    @Request
//    private HttpRequest request;
//
//    @Href
//    @HtmlField(cssPath = "a[href][href!='#']")
//    private List<String> hrefs;
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
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        String startedUrl = "https://be.spdrs.com";
//        FINISHED_URLS.add(startedUrl);
//        HttpGetRequest httpRequest = new HttpGetRequest(startedUrl);
//        httpRequest.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0");
//        httpRequest.addHeader("Cookie", "AKA_A2=1; AMCV_00CC7FBA5881402E0A495ECF%40AdobeOrg=2096510701%7CMCIDTS%7C17513%7CMCMID%7C23966795000124163571122757703669487032%7CMCAAMLH-1513319813%7C7%7CMCAAMB-1513647379%7C6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y%7CMCOPTOUT-1513049779s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C2.0.0; _mkto_trk=id:451-VAW-614&token:_mch-spdrs.com-1512715015994-24252; InitialURL=be.spdrs.com; TS016b0cfe=01098c54485a29d8d12fffc111dfbd5d9b7052aa7684485c77da1ad0b314772b59600b576fa11473aa467cd60b24bf8fff4813e2d4442aab10061829e6bf0ce12e5af71f9e0249c19db4e377f1f44227a4fc7bc1ee7113d0181a718d4ea505069a446b0c1694d6d192cd71955e6bf1b146f44f7c024445475102bff2e51f2e6f453199e6acb6a72bf89c58745870889b40df0ed887440bb142bc46892e9771e637b9b3f7a8546ead96261274d907cd0ce81f54944d92d727311eab117c3453c93141b2af5209aaff0645cf860b7345e01748b46f5fa209d90633d0f26e4ff9c9a9798f9f5d; AMCVS_00CC7FBA5881402E0A495ECF%40AdobeOrg=1; WT_FPC=id=329efe07-b4b4-489f-a5ef-d9b8406fc405:lv=1512997229865:ss=1512995780537; s_cc=true; s_sq=%5B%5BB%5D%5D; JSESSIONID=gCdIYG2mSp2NzzLBxANk-pl5AU2g7_ewlGqnj_GWZwEe1m3rJ0Ot!1606160682; org.jboss.seam.core.Locale=en; geoloc=be:en; role=prof; countryKey=bel; fundDomInKey=All; TS012b9124=01098c5448fb1cab91663c2e85f9320bf99fd117bb63b2d1de00140359a251eb52e7c1c303a19c030a4baa7219e3e54df3d9c507727e6998780e68c59bb8fccaee26ef7ea295d04b15ddc32f12a862195c6be0fddd6cad5b5a4aa8a97a192705bc8f8a9f74a0d1b4fb70e9671b28697339a3f0a14dc9e85683a37a242d59a11ad78d93e17977ba6abb3fe1a8ab45b3e807d39a4874dc1642133004fc37b53f29e18b8063ff5b48413de9bc5dfde89d24d2aa326ddc586c5bea4cf1b792b054fc7a11221b28dc271da3ccc0c6afe5939ba026d18dfaed32b6980300cbd245045af603dadf5194e9cb59c0592be4555e0969291cbe2889160693a85a910fa5ae9c8e0315af2be32570ed58d30e650a14668aa1271dc0; TS016d81e0=01098c54483cef72513b9c2b8c6155c655a13d9a5062a9809addc9298708db4813b4bb2ec36b106b5513bc3ee1c1f2326541475c348afa4999e0ffc6a79ceb301924d6338f58b217075fa83b9a3fe79803e18f6a0759b006c999ecada1630ba33bcdf6755c2fa050879a622081f25b1f64324d4e4527d67b913d67cf18c3f8384ea75106b23a291f5a9af7737da5ca9ed2af9fc108f786816f89c2fc81093eb7ebaeded50f011a0c06865ced8f588b8daaed2c7b4b96859efa65833cb2bc49a9e064d15107; clickOnLogin=true; SMSESSION=fsKav0aDsli2i6CsEYI7iSIR5kWU18VflV/nGbvgNJo47XMDahSIZ6cgP6d8pEf/X+R1k9URtWpaUIizzM0qzfAFZgeDn+1mbxZcO0VBbrg33e1REDkftTbqNaIMCCf4WPj9UCm4/Ai2uVUf4zH5hoHCuJGYFoExeeKfWoDGzn4RnB/MU1rfZYiO5HtaiJ8u+jU8Vk6fOm2OVu6RVI0ylUw594S2Y4iS7EasMho1lgQyYBXRdm0Fv0epdEmbkHXe4OA4mo2PTxhRQA/Fo5RysTQJx8+E/Ku6FTceHnsni/TbGQ3SGgb+LH+bsLw8e09EHnqH3GJDYgwILx84xe3tiYL54xSRmHKyhObRKoAqRlikKL1OVonuQDRq1N1/+kKq7IBWbQnv42JEqGbDSQUuzhaCHb223w2VCmqZofOtuqiS/vV8/m0Z9sRh6WrVKkOu59s89TISKit2ImDPQepNyyemWHtYww4SrQNjjgZuK20SwCFACBDlS1vPH1HU+O32RZq82vPcdfKvsrrMgZaaG7yx3lJDwNpiAjNIgmzKH3/mvd5TDkJ3D6sXYLuH+MQlzrtiPF0Z2rpANAEOe7NTJQwGZstAZdRPtk+LqnOZYUwHADyHSkfN5o0yeEVNlHImKIGYCpHZAFsTINLwZlXTK6O+cKiPmaSxQ9FlgAHeMj1+5dOdwUfNdRn67rBzIz+Q/obsudJmLRG5J3sJdSkkMCwiyeihkvJsz/982/YJXM9mP80NWtpqu4/OFsViDz7FRnT89Q7rYFWLlb24isx/vnWuUE3j169N7nFrxXYu0UvBX86zunb+a7dlyk49NWtVedl9KL/rPM2iTEj8oSAyjThH9GOMQXyM4Anw8gxt8fzeHKUMpDxe/uhYUv9W0ih/eHlrCf7uSPkxpds6U+qpW9NJR8tOOJLqSPCt7vW7tiIrpTL8e7Av9kn/UWXI9cdsBAQ2Pgk6EfWN0VfmwDleBxXsyPqQpmxd5trM0iZ1S06+o+aePc76KUXzfrXW1h6GftMlo1an+mRznx74dC4ZCf6gCvp11w/ukdBarCrLa4nITz2ftuvgYktakMqnz85cM/K4ouVcsnokU49WqYiT5laorJGwhANv; loginId=a109048; loginName=jialong+hu; disclaimer=accepted; AKA_A2=1");
//        httpRequest.addHeader("Upgrade-Insecure-Requests", "1");
//        httpRequest.addCookie("AKA_A2", "1");
//        httpRequest.addCookie("AMCV_00CC7FBA5881402E0A495ECF@AdobeOrg", "2096510701|MCIDTS|17513|MCMID|23966795000124163571122757703669487032|MCAAMLH-1513319813|7|MCAAMB-1513647379|6G1ynYcLPuiQxYZrsz_pkqfLG9yMXBpb2zX5dvJdYQJzPXImdj0y|MCOPTOUT-1513049779s|NONE|MCAID|NONE|vVersion|2.0.0");
//        httpRequest.addCookie("_mkto_trk", "id:451-VAW-614&token:_mch-spdrs.com-1512715015994-24252");
//        httpRequest.addCookie("InitialURL", "be.spdrs.com");
//        httpRequest.addCookie("TS016b0cfe", "01098c54485a29d8d12fffc111dfbd5d9b7052aa7684485c77da1ad0b314772b59600b576fa11473aa467cd60b24bf8fff4813e2d4442aab10061829e6bf0ce12e5af71f9e0249c19db4e377f1f44227a4fc7bc1ee7113d0181a718d4ea505069a446b0c1694d6d192cd71955e6bf1b146f44f7c024445475102bff2e51f2e6f453199e6acb6a72bf89c58745870889b40df0ed887440bb142bc46892e9771e637b9b3f7a8546ead96261274d907cd0ce81f54944d92d727311eab117c3453c93141b2af5209aaff0645cf860b7345e01748b46f5fa209d90633d0f26e4ff9c9a9798f9f5d");
//        httpRequest.addCookie("AMCVS_00CC7FBA5881402E0A495ECF@AdobeOrg", "1");
//        httpRequest.addCookie("WT_FPC", "id=329efe07-b4b4-489f-a5ef-d9b8406fc405:lv=1512997229865:ss=1512995780537");
//        httpRequest.addCookie("s_cc", "true");
//        httpRequest.addCookie("s_sq", "[[B]]");
//        httpRequest.addCookie("JSESSIONID", "gCdIYG2mSp2NzzLBxANk-pl5AU2g7_ewlGqnj_GWZwEe1m3rJ0Ot!1606160682");
//        httpRequest.addCookie("org.jboss.seam.core.Locale", "en");
//        httpRequest.addCookie("geoloc", "be:en");
//        httpRequest.addCookie("role", "prof");
//        httpRequest.addCookie("countryKey", "bel");
//        httpRequest.addCookie("fundDomInKey", "All");
//        httpRequest.addCookie("TS012b9124", "01098c5448fb1cab91663c2e85f9320bf99fd117bb63b2d1de00140359a251eb52e7c1c303a19c030a4baa7219e3e54df3d9c507727e6998780e68c59bb8fccaee26ef7ea295d04b15ddc32f12a862195c6be0fddd6cad5b5a4aa8a97a192705bc8f8a9f74a0d1b4fb70e9671b28697339a3f0a14dc9e85683a37a242d59a11ad78d93e17977ba6abb3fe1a8ab45b3e807d39a4874dc1642133004fc37b53f29e18b8063ff5b48413de9bc5dfde89d24d2aa326ddc586c5bea4cf1b792b054fc7a11221b28dc271da3ccc0c6afe5939ba026d18dfaed32b6980300cbd245045af603dadf5194e9cb59c0592be4555e0969291cbe2889160693a85a910fa5ae9c8e0315af2be32570ed58d30e650a14668aa1271dc0");
//        httpRequest.addCookie("TS016d81e0", "01098c54483cef72513b9c2b8c6155c655a13d9a5062a9809addc9298708db4813b4bb2ec36b106b5513bc3ee1c1f2326541475c348afa4999e0ffc6a79ceb301924d6338f58b217075fa83b9a3fe79803e18f6a0759b006c999ecada1630ba33bcdf6755c2fa050879a622081f25b1f64324d4e4527d67b913d67cf18c3f8384ea75106b23a291f5a9af7737da5ca9ed2af9fc108f786816f89c2fc81093eb7ebaeded50f011a0c06865ced8f588b8daaed2c7b4b96859efa65833cb2bc49a9e064d15107");
//        httpRequest.addCookie("clickOnLogin", "true");
//        httpRequest.addCookie("SMSESSION", "fsKav0aDsli2i6CsEYI7iSIR5kWU18VflV/nGbvgNJo47XMDahSIZ6cgP6d8pEf/X+R1k9URtWpaUIizzM0qzfAFZgeDn+1mbxZcO0VBbrg33e1REDkftTbqNaIMCCf4WPj9UCm4/Ai2uVUf4zH5hoHCuJGYFoExeeKfWoDGzn4RnB/MU1rfZYiO5HtaiJ8u+jU8Vk6fOm2OVu6RVI0ylUw594S2Y4iS7EasMho1lgQyYBXRdm0Fv0epdEmbkHXe4OA4mo2PTxhRQA/Fo5RysTQJx8+E/Ku6FTceHnsni/TbGQ3SGgb+LH+bsLw8e09EHnqH3GJDYgwILx84xe3tiYL54xSRmHKyhObRKoAqRlikKL1OVonuQDRq1N1/+kKq7IBWbQnv42JEqGbDSQUuzhaCHb223w2VCmqZofOtuqiS/vV8/m0Z9sRh6WrVKkOu59s89TISKit2ImDPQepNyyemWHtYww4SrQNjjgZuK20SwCFACBDlS1vPH1HU+O32RZq82vPcdfKvsrrMgZaaG7yx3lJDwNpiAjNIgmzKH3/mvd5TDkJ3D6sXYLuH+MQlzrtiPF0Z2rpANAEOe7NTJQwGZstAZdRPtk+LqnOZYUwHADyHSkfN5o0yeEVNlHImKIGYCpHZAFsTINLwZlXTK6O+cKiPmaSxQ9FlgAHeMj1+5dOdwUfNdRn67rBzIz+Q/obsudJmLRG5J3sJdSkkMCwiyeihkvJsz/982/YJXM9mP80NWtpqu4/OFsViDz7FRnT89Q7rYFWLlb24isx/vnWuUE3j169N7nFrxXYu0UvBX86zunb+a7dlyk49NWtVedl9KL/rPM2iTEj8oSAyjThH9GOMQXyM4Anw8gxt8fzeHKUMpDxe/uhYUv9W0ih/eHlrCf7uSPkxpds6U+qpW9NJR8tOOJLqSPCt7vW7tiIrpTL8e7Av9kn/UWXI9cdsBAQ2Pgk6EfWN0VfmwDleBxXsyPqQpmxd5trM0iZ1S06+o+aePc76KUXzfrXW1h6GftMlo1an+mRznx74dC4ZCf6gCvp11w/ukdBarCrLa4nITz2ftuvgYktakMqnz85cM/K4ouVcsnokU49WqYiT5laorJGwhANv");
//        httpRequest.addCookie("loginId", "a109048");
//        httpRequest.addCookie("loginName", "jialong+hu");
//        httpRequest.addCookie("disclaimer", "accepted");
//        GeccoEngine.create()
//                .classpath("com.geccocrawler.gecco.demo.spdrs")
//                .thread(1)
//                .interval(2000)
//                .start(httpRequest)
//                .start();
//    }
//
//    @Override
//    public void process(Spdrs bean) {
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
//        if (!fileName.matches(".*\\.(css|js|seam|html|cvs|csv).*")) {
//            fileName += ".html";
//        }
//        File file = new File(BASE_PATH + folder, fileName);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            if (fileName.matches(".*\\.(css|js).*")) {
//                html = bean.getJsCssContent();
//            } else {
//                URL netUrl = new URL(request.getUrl());
//                String port = ":" + netUrl.getPort();
//                if (netUrl.getPort() == -1) {
//                    port = "";
//                }
//                String prefix = netUrl.getProtocol() + "://" + netUrl.getHost() + port;
//                html = process(html, request);
//            }
//            writer.write(html);
//            writer.flush();
//            log.info("succeeded download file: " + fileName);
//        } catch (Exception e) {
//            log.error(e);
//        }
//    }
//
//    private String process(String content, HttpRequest request) throws MalformedURLException {
//        String url = request.getUrl();
//        URL netUrl = new URL(url);
//        String prefix = netUrl.getProtocol() + "://" + netUrl.getHost();
//        if (netUrl.getPort() != -1) prefix += ":" + netUrl.getPort();
//        //process html page content
//        Document document = Jsoup.parse(content, url);
//        Elements elements = document.select("a[href][href!='#'],script[src][type='text/javascript'],link[href][rel='stylesheet']");
//        for (Element element : elements) {
//            String attrName;
//            if (element.hasAttr("href")) {
//                attrName = "href";
//            } else if (element.hasAttr("src")) {
//                attrName = "src";
//            } else continue;
//            String attrValue = element.attr(attrName);
//            Pattern pattern = Pattern.compile("/.*\\.(pdf|xls|xlsx|doc|svg|zip|cvs|ico).*", CASE_INSENSITIVE);
//            if (attrValue.matches("/\\w.*\\.(js|css).*") || (!pattern.matcher(attrValue).matches())) {
//                if (attrValue.startsWith("/") && (!attrValue.startsWith("//"))) {
//                    if (FINISHED_URLS.add(attrValue)) {
//                        DeriveSchedulerContext.into(prefix + attrValue);
//                    }
//                    //if it's html file ,should add .html
//                    if (!attrValue.matches("/\\w.*\\.(js|css).*")) {
//                        attrValue = BASE_PATH + "/en" + attrValue.replaceAll("/", "\\\\") + ".html";
//                    } else {
//                        attrValue = BASE_PATH + attrValue.replaceAll("/", "\\\\");
//                    }
//                    element.attr(attrName, attrValue);
//                }
//
//            }
//        }
//        return document.html();
//    }
//}
