package cn.company.common.bean;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nicholas on 2017/4/26.
 *
 * @author nicholas
 * <p>
 * 分页对象
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long totalElements;
    private Integer totalPages;
    private Integer numberOfElements;
    private Integer number;
    private Integer size;
    private List content;

    public Page(PageInfo<T> pageInfo, Integer number, Integer size) {
        this.number = number;
        this.size = size;
        this.content = pageInfo.getList();
        this.totalElements = pageInfo.getTotal();
        this.totalPages = pageInfo.getPages();
        this.numberOfElements = pageInfo.getEndRow();
    }

    public Page(List<T> list, Integer number, Integer size) {
        this.number = number;
        this.size = size;
        this.content = list;
        this.totalElements = ((com.github.pagehelper.Page) list).getTotal();
        this.totalPages = ((com.github.pagehelper.Page) list).getPages();
        this.numberOfElements = ((com.github.pagehelper.Page) list).getEndRow();
    }

    public Page(List<T> list, Integer number, Integer size, Long totalElements, Integer totalPages, Integer numberOfElements) {
        this.number = number;
        this.size = size;
        this.content = list;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List getContent() {
        return content;
    }

    public void setContent(List content) {
        this.content = content;
    }
}
