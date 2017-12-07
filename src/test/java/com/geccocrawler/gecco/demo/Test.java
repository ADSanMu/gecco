package com.geccocrawler.gecco.demo;

/**
 * @author a637182
 * @email Jiacheng_Deng@ssga.com
 * @date 12/7/2017
 */
public class Test {

    public static void main(String[] args) {
        String a = "/site-content/scripts/spdr_new.css?version=1";
        System.out.println(a.matches(".*\\.(html|js|css)(\\?version=\\d*)$"));
    }
}
