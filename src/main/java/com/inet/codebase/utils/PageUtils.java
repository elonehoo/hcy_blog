package com.inet.codebase.utils;

public class PageUtils {
    //当前页得具体数据
    private Object resultList;
    //当前页码
    private Integer current;
    //总共有多少条
    private Integer totalCount;
    //当前页得条目数
    private Integer pageNavSize;

    public PageUtils(Object resultList, Integer current, Integer totalCount, Integer pageNavSize) {
        this.resultList = resultList;
        this.current = current;
        this.totalCount = totalCount;
        this.pageNavSize = pageNavSize;
    }

    public PageUtils() {
    }

    public Object getResultList() {
        return resultList;
    }

    public void setResultList(Object resultList) {
        this.resultList = resultList;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNavSize() {
        return pageNavSize;
    }

    public void setPageNavSize(Integer pageNavSize) {
        this.pageNavSize = pageNavSize;
    }

    @Override
    public String toString() {
        return "PageUtils{" +
                "resultList=" + resultList +
                ", current=" + current +
                ", totalCount=" + totalCount +
                ", pageNavSize=" + pageNavSize +
                '}';
    }
}
