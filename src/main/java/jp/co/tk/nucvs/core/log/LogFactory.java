package jp.co.tk.nucvs.core.log;

import org.apache.commons.logging.Log;

public class LogFactory {
    public static Log getLogger(Class<?> cls){
        return org.apache.commons.logging.LogFactory.getLog(cls);
    }
}
