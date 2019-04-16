package service.crawler;

import java.util.List;
import java.util.Map;

public interface ICrawlerService {

    List<Map<Byte,Map<String,String>>> startCrawler();

    /**
     * 根据深度进行爬取
     * @param depth
     * @return
     */
    List<Map<Byte,Map<String,String>>> startCrawler(Integer depth);
}
