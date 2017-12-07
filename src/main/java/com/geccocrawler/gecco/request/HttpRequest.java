package com.geccocrawler.gecco.request;

import java.net.MalformedURLException;
import java.util.Map;

public interface HttpRequest extends Cloneable {

    String getUrl();

    String getPath();

    void setUrl(String url);

    void addParameter(String name, String value);

    void setParameters(Map<String, String> parameters);

    String getParameter(String name);

    Map<String, String> getParameters();

    void addHeader(String name, String value);

    Map<String, String> getHeaders();

    void clearHeader();

    void refer(String refer);

    String getCharset();

    void setCharset(String charset);

    HttpRequest subRequest(String url);

    Map<String, String> getCookies();

    void addCookie(String name, String value);

    String getCookie(String name);

    void clearCookie();

    long getPriority();

    void setPriority(long prio);
}
