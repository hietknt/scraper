package com.parser.scraper.service.parse.steam.buff163;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.parser.scraper.service.parse.steam.Parse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParseBuff163 implements Parse {

    private final Buff163Properties buff163Properties;

    @Override
    public ArrayDeque parse(String id) {

        String url = "https://buff.163.com/api/market/goods?game=csgo&page_num=";
        String pageNumber = "2";
        String pageSize = "&page_size=80";
        String pageTime = "&_" + new Date().getTime();

        try (final WebClient webClient = new WebClient()) {

            webClient.getOptions().setJavaScriptEnabled(false);
            CookieManager cookieManager = webClient.getCookieManager();
            cookieManager.setCookiesEnabled(true);

            String domain = "buff.163.com";
            List<Cookie> cookies = new ArrayList<>();
            cookies.add(new Cookie(domain, "csrf_token", buff163Properties.csrfToken));
            cookies.add(new Cookie(domain, "Locale-Supported", buff163Properties.localeSupported));
            cookies.add(new Cookie(domain, "session", buff163Properties.session));

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


        return null;
    }
}
