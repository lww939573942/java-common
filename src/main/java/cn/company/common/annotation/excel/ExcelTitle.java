package cn.company.common.annotation.excel;

import java.lang.annotation.*;

/**
 * @author linjunpeng
 * @date 2018/8/31
 * 注解处理输出excel
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelTitle {
    /**
     * 表格头名
     */
    String titleName() default "";

    /**
     * 是否输出到表格 默认输出
     */
    boolean isInput() default true;

    /**
     * 默认值为空
     *
     * @return
     */
    String defaultValue() default "";
}
