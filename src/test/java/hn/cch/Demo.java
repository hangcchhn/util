package hn.cch;


import hn.cch.util.HttpUtil;
import org.junit.Test;

public class Demo {

    @Test
    public void testHttp()
    {


        // String http = "http://192.168.124.100:8808/http/api/test?type=main&user=XXXX&hash=XXXXXXXXXXXXXXXXX";
        // String data = "{\"content\":{\"key\":\"testKey\",\"value\":\"testValue\"},\"datetime\":null,\"function\":\"testFunction\"}";
        //
        // String post = HttpUtil.sentPost(http, data);
        // System.out.println(post);

        String http = "http://192.168.10.1:8888/emp/1";
        String get = HttpUtil.sentGet(http);
        System.out.println(get);

    }

}
