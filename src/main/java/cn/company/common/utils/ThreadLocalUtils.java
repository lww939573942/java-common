package cn.company.common.utils;

/**
 * Created by donaldhan on 2017/6/26.
 *
 * @author donaldhan
 */
public class ThreadLocalUtils {
    private final static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void setObject(Object object) {
        threadLocal.set(object);
    }

    public static Object getObject() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}