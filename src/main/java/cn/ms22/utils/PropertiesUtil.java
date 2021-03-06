package cn.ms22.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件工具
 * @author baopz
 */
public final class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private volatile static Properties prop;


    public static String getProperty(String config, String key, String superAddition) {
        if (prop == null) {
            synchronized (PropertiesUtil.class) {
                if (prop == null) {
                    prop = new Properties();
                    InputStream inputStream = null;
                    inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(config);
                    if (inputStream == null) {
                        logger.warn("没有发现配置文件。{}", config);
                        return null;
                    }
                    try {
                        prop.load(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error("读取文件未知错误.{}", e);
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.error("关闭文件出错");
                        }
                    }
                }
            }
        }
        String s;
        synchronized (PropertiesUtil.class) {
            s = prop.getProperty(key);
            if (superAddition != null) {
                s += superAddition;
            }
        }
        return s;
    }

    /**
     * 得到配置参数
     *
     * @param config
     * @param key
     * @return
     */
    public static String getProperty(String config, String key) {
        return getProperty(config, key, null);
    }
}
