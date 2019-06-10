package cn.company.common.utils;

import jodd.util.ClassUtil;

import java.lang.reflect.Field;

public final class FieldUtils {
    public static Field[] getDeclaredFields(Class<?> cl, boolean includeParent) {
        if (includeParent) {
            return ClassUtil.getSupportedFields(cl);
        } else {
            return cl.getDeclaredFields();
        }
    }
}