package cn.company.common.utils;

public abstract class ObjectUtils {
    public static boolean equals(Object obj1, Object obj2) {
        return org.springframework.util.ObjectUtils.nullSafeEquals(obj1, obj2);
    }
}
