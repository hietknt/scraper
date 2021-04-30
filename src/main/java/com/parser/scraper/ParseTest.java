package com.parser.scraper;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParseTest {
    public static void main(String[] args) {

        String url = "https://buff.163.com/api/market/goods?game=csgo&page_num=";
        String pageNumber = "1";
        String pageSize = "&page_size=80";
        String pageTime = "&_" + new Date().getTime();

        try (final WebClient webClient = new WebClient()) {

            webClient.getOptions().setJavaScriptEnabled(false);
            CookieManager cookieManager = webClient.getCookieManager();
            cookieManager.setCookiesEnabled(true);

            String domain = "buff.163.com";
            List<Cookie> cookies = new ArrayList<>();
            cookies.add(new Cookie(domain, "csrf_token", "IjMyZjNmMTlhY2M2YjAxNjc0ZWFjM2RlZWI4MzRmMWQ2OGY4YmMwMWUi.EsVhZw.ZVIPZ1PMjX6aAtV4eD_p8dNK37Q"));
            cookies.add(new Cookie(domain, "Locale-Supported", "en"));
            cookies.add(new Cookie(domain, "session", "1-q-GZKszR4xwBXx2FA2qXRgOgH5Ua-1WulB_Gy0w8oYZd2043634051"));

            for (int i = 0; i < cookies.size(); i++){
                cookieManager.addCookie(cookies.get(i));
            }

            final Page page = webClient.getPage(url + pageNumber + pageSize + pageTime);

            System.out.println(page.getWebResponse().getContentAsString());
            System.out.println(url + pageNumber + pageSize + pageTime);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
