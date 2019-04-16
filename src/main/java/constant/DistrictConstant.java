package constant;

public class DistrictConstant {

    /**
     * 过滤街道特殊字符
     */
    public static final String[] STREET_FILTER_WORDS = {"办事处"};

    public interface RetrofitParam {

        int CONNECT_TIMEOUT = 150;
        int WRITE_TIMEOUT = 150;
        int READ_TIMEOUT = 150;
    }

    public interface DistrictLevel {


        /**
         * 国家
         */
        Integer COUNTRY_LEVEL = 0;

        /**
         * 省级地址常量
         */
        Integer PROVINCE_LEVEL = 1;

        /**
         * 市级级地址常量
         */
        Integer CITY_LEVEL = 2;

        /**
         * 区/县级地址常量
         */
        Integer DISTRICT_LEVEL = 3;

        /**
         * 街道地址层级
         */
        Integer STREET_LEVEL = 4;

        /**
         * 中国编码
         */
        String CHINA_CODE = "100000";

        /**
         * 市辖区
         */
        String DIRECT_CITY = "市辖区";

        /**
         * 省直辖县级行政区
         */
        String DIRECT_COUNTY = "省直辖县级行政区";

        /**
         * 自治区直辖县级行政区划
         */
        String BUR_DIRECT_COUNTY = "自治区直辖县级行政区划";

        /**
         * 际享仓版本
         */
        Integer GSTORE_VERSION = 3;

        /**
         * 重庆地址编码
         */
        String CHONG_QING_SUB_CODE = "5002";
    }

    public interface ListSize {

        /**
         * list集合分片大小
         */
        Integer LIST_SIZE = 2000;
    }
}
