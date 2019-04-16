package constant;

import java.util.regex.Pattern;

public class CrawlerConstant {

    public static final String CRAWLER_STORAGE_FOLDER = "/Users/g2/Downloads/Crawler4j/basic";
    public static final Integer POLITENESS_DELAY = 1000;
    public static final Integer MAX_DEPTH_OF_CRAWLING = 7;
    public static final Integer MAX_PAGES_TO_FETCH = 10000;
    public static final Integer SOCKET_TIME_OUT = 20000;
    public static final Boolean INCLUDE_BINARY_CONTENT_IN_CRAWLING = false;
    public static final Boolean RESUMABLE_CRAWLING = false;
    public static final Integer NUMBER_OF_CRAWLERS = 5;
    public static Pattern FILE_ENDING_EXCLUSION_PATTERN = Pattern.compile("\\d+\\.html'>(\\D+)</a>");
    public static final String START_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/";
    public static final String REGEX_RULE = "";
    public static final String PARSE_CHARSET = "GBK";
    public static final String TARGET_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/index.html";
}
