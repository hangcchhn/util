package hn.cch;

import hn.cch.util.PrivateKeyUtil;
import hn.cch.util.PublicKeyUtil;
import hn.cch.util.BytesUtil;
import hn.cch.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {


        // String prikPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\test-sile.p12";
        // String prikPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\ca\\certs\\ca.p12";
        // String prikPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\ca\\certs\\client.p12";
        String prikPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\ca\\certs\\server.p12";

        // String passWord = "123456";
        String passWord = "chench";


        String jsonText = "{\"code\":\"SUCCESS\",\"content\":{\"test0\":\"0\",\"test1\":\"1\",\"test2\":\"2\",\"test3\":\"3\",\"test4\":\"4\",\"test5\":\"5\",\"test6\":\"6\",\"test7\":\"7\",\"test8\":\"8\",\"test9\":\"9\"},\"info\":\"测试成功\"}";


        String signData = PrivateKeyUtil.sign(prikPath, passWord, jsonText);

        logger.info("sign ： " + signData );

        // String pubkPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\test-sile.cer";
        // String pubkPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\ca\\certs\\ca.cer";
        // String pubkPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\ca\\certs\\client.cer";
        String pubkPath = "C:\\Users\\chench\\Desktop\\Temp\\ca\\ca\\certs\\server.cer";

        logger.info("verify ： " + PublicKeyUtil.verify(pubkPath, signData, jsonText));





    }

}
