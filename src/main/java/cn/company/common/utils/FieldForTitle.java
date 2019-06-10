package cn.company.common.utils;

import java.lang.reflect.Field;

/**
 * 属性、标题、宽度
 */
public class FieldForTitle {
    private Field field;
    private String title;
    private int width;


    public FieldForTitle(Field field) {
        super();
        this.field = field;
    }


    public FieldForTitle(Field field, String title, int width) {
        super();
        this.field = field;
        this.title = title;
        this.width = width;
    }


    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
