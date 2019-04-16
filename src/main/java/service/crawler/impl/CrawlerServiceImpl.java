package service.crawler.impl;


import com.google.common.collect.Lists;
import constant.CrawlerConstant;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import service.crawler.ICrawlerService;

@Slf4j
@Service("crawlerService")
public class CrawlerServiceImpl implements ICrawlerService {

    @Override
    public List<Map<Byte, Map<String, String>>> startCrawler() {
        int numberOfCrawlers = CrawlerConstant.NUMBER_OF_CRAWLERS;

        String targetUrl = CrawlerConstant.TARGET_URL;
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CrawlerConstant.CRAWLER_STORAGE_FOLDER);
        config.setPolitenessDelay(CrawlerConstant.POLITENESS_DELAY);
        config.setMaxDepthOfCrawling(CrawlerConstant.MAX_DEPTH_OF_CRAWLING);
        config.setMaxPagesToFetch(CrawlerConstant.MAX_PAGES_TO_FETCH);
        config.setSocketTimeout(CrawlerConstant.SOCKET_TIME_OUT);
        config.setIncludeBinaryContentInCrawling(CrawlerConstant.INCLUDE_BINARY_CONTENT_IN_CRAWLING);
        config.setResumableCrawling(CrawlerConstant.RESUMABLE_CRAWLING);

        return getDistrictList(config, numberOfCrawlers, targetUrl);
    }

    @Override
    public List<Map<Byte, Map<String, String>>> startCrawler(Integer depth) {
        int numberOfCrawlers = CrawlerConstant.NUMBER_OF_CRAWLERS;

        //爬取目标地址
        String targetUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/index.html";

        CrawlConfig config = new CrawlConfig();
        //爬虫文件存储的位置
        config.setCrawlStorageFolder(CrawlerConstant.CRAWLER_STORAGE_FOLDER);
        //请求等待时间
        config.setPolitenessDelay(CrawlerConstant.POLITENESS_DELAY);
        //爬取深度
        config.setMaxDepthOfCrawling(depth);
        //默认要爬的网页数量
        config.setMaxPagesToFetch(CrawlerConstant.MAX_PAGES_TO_FETCH);
        //设置超时时间
        config.setSocketTimeout(30000);
        //关闭爬取二进制内容（如图片，音频）
        config.setIncludeBinaryContentInCrawling(false);
        //意外终止是否需要恢复(启用会影响爬虫效率)
        config.setResumableCrawling(false);

        return getDistrictList(config, numberOfCrawlers, targetUrl);
    }

    /**
     * 爬取地址库信息
     * @param config
     * @param crawlers
     * @param targetUrl
     * @return
     */
    private List<Map<Byte, Map<String, String>>> getDistrictList(CrawlConfig config, Integer crawlers, String targetUrl){
        int numberOfCrawlers = crawlers;

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = null;
        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            log.error("Create CrawlerController Exception :{}", e.getMessage());
            throw new RuntimeException(ExceptionUtils.getStackTrace(e));
        }

        controller.addSeed(targetUrl);
        controller.start(BasicCrawler.class, numberOfCrawlers);

        List<Object> crawlersLocalData = controller.getCrawlersLocalData();
        List<Map<Byte, Map<String, String>>> maps = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(crawlersLocalData)) {
            for (Object localData : crawlersLocalData) {
                CrawlerInfo stat = (CrawlerInfo) localData;
                List<Map<Byte, Map<String, String>>> list = stat.getList();
                if (!CollectionUtils.isEmpty(list)) {
                    maps.addAll(list);
                }
            }
        }
        return maps;
    }

}
