package hn.cch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);


    public static byte[] toBytes(Object object){
        byte[] bytes = null;

        String string = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
        logger.debug("Json String : " + string);
        bytes = StringUtil.toBytes(string);

        return bytes;
    }

    public static Object toObject(byte[] bytes, Class<? extends Object> clazz){
        Object object = null;

        String string = StringUtil.fromBytes(bytes);
        logger.debug("Json String : " + string);
        object = JSON.parseObject(string, clazz);

        return object;
    }


}
