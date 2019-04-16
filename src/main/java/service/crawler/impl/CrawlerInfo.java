package service.crawler.impl;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;

public class CrawlerInfo {

    private List<Map<Byte, Map<String, String>>> mapList = Lists.newArrayList();

    public void save(Map<Byte, Map<String, String>> map) {
        if(map != null){
            mapList.add(map);
        }
    }

    public List<Map<Byte, Map<String, String>>> getList() {
        return mapList;
    }
}
