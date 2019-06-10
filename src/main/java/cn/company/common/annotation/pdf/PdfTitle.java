package cn.company.common.annotation.pdf;

import java.lang.annotation.*;

/**
 * @Author lww
 * @date 2018/10/25
 * 注解处理输出PDF
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PdfTitle {
    /**
     * 表格头名
     */
    String titleName() default "";

    /**
     * 是否输出到表格 默认输出
     */
    boolean isInput() default true;
}
