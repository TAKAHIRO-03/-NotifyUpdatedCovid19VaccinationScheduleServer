package jp.co.tk.nucvs.core.log;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
class LoggerPostProcessor implements BeanPostProcessor {

    private static final Log log = LogFactory.getLogger(LoggerPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            Logger annotation = field.getAnnotation(Logger.class);
            if (annotation != null) {
                if (!Log.class.isAssignableFrom(field.getType())) {
                    log.error("Target of Logger annotation must be " + Log.class.getSimpleName() + " subclass");
                    continue;
                }
                field.setAccessible(true);
                try {
                    field.set(bean, LogFactory.getLogger(bean.getClass()));
                    log.debug("Logger inject Log to " + bean.getClass().getSimpleName());
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Logger injection failed", e);
                }

            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}