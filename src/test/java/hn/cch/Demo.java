package hn.cch;


import hn.cch.util.DateUtil;
import hn.cch.util.FtpUtil;
import hn.cch.util.HttpUtil;
import org.apache.commons.net.ftp.FTPClient;
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

        // String http = "http://192.168.10.1:8888/emp/1";
        // String get = HttpUtil.sentGet(http);
        // System.out.println(get);

    }

    @Test
    public void testFtp()
    {
        FTPClient ftpClient = FtpUtil.openFtp("10.44.4.7", 21, "secret_jiangsu", "123456");
        FtpUtil.changePath(ftpClient, "/");
        FtpUtil.treeFile(ftpClient, "third");
    }

    @Test
    public void testDate()
    {
        assert DateUtil.syncTime(DateUtil.toDate("yyyy-MM-dd HH:mm:ss", "2020-07-10 01:30:00"));
    }

}
