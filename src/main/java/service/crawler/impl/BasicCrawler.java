package service.crawler.impl;


import com.google.common.collect.Maps;
import com.rometools.utils.Strings;
import constant.CrawlerConstant;
import constant.DistrictConstant.DistrictLevel;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class BasicCrawler extends WebCrawler {

    CrawlerInfo crawlerInfo;

    public BasicCrawler() {
        this.crawlerInfo = new CrawlerInfo();
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (CrawlerConstant.FILE_ENDING_EXCLUSION_PATTERN.matcher(href).matches()) {
            return false;
        }
        return href.startsWith(CrawlerConstant.START_URL);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        log.info("URL: " + url);
        parseData(page);
    }

    private void parseData(Page page) {
        Map<String, String> map = Maps.newHashMap();
        byte[] data = page.getContentData();
        String html = null;
        try {
            html = new String(data,CrawlerConstant.PARSE_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error("parse service.crawler page exception ：{}",e.getMessage());
            e.printStackTrace();
        }

        if (!Strings.isEmpty(html)) {

            //解析html数据
            Document doc = Jsoup.parse(html);
            Elements elements = doc.getElementsByTag("a");
            if (elements.size() > 0) {
                elements.remove(elements.size() - 1);
            }
            Elements provincetr = doc.getElementsByClass("provincetr");

            if (provincetr.size() > 0) {
                for (Element element : elements) {
                    map.put(element.text(), element.text());
                }
            }

            Elements citytr = doc.getElementsByClass("citytr");
            Elements countytr = doc.getElementsByClass("countytr");
            Elements towntr = doc.getElementsByClass("towntr");
            Elements villagetr = doc.getElementsByClass("villagetr");

            if (citytr.size() > 0 || countytr.size() > 0 || towntr.size() > 0) {
                for (int i = 0; i < elements.size(); i++) {
                    Element code = elements.get(i);
                    Element value = elements.get(i + 1);
                    String districtCode = code.text();
                    String districtName = value.text();
                    map.put(districtCode.substring(0, districtCode.length() - 3), districtName);
                    i++;
                }
            }

            try {
                if (!org.springframework.util.CollectionUtils.isEmpty(map)) {
                    Byte level;
                    if (provincetr.size() > 0) {
                        level = DistrictLevel.PROVINCE_LEVEL.byteValue();
                    } else if (citytr.size() > 0) {
                        level = DistrictLevel.CITY_LEVEL.byteValue();
                    } else if (countytr.size() > 0) {
                        level = DistrictLevel.DISTRICT_LEVEL.byteValue();
                    } else {
                        level = DistrictLevel.STREET_LEVEL.byteValue();
                    }
                    Map<Byte, Map<String, String>> districtMap = Maps.newHashMap();
                    districtMap.put(level, map);
                    crawlerInfo.save(districtMap);
                }
            } catch (RuntimeException e) {
                log.error("Storing failed", e);
            }

        }
    }

    @Override
    public Object getMyLocalData() {
        return crawlerInfo;
    }

}
