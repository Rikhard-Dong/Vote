package io.ride.util;

import java.util.ArrayList;
import java.util.List;

public class PageHelper {
    private long nums;       // 总记录数
    private int pages;      // 总页数
    private int size;       // 每页数目
    private int first;      // 第一页
    private int last;       // 最后一页
    private int pre;        // 前一页
    private int next;       // 下一页
    private int curr;       // 当前页
    private List<Object> data;
    private List<Integer> pageList;

    public PageHelper(long nums, int size, int curr) {
        this.nums = nums;
        this.size = size;
        this.curr = curr;
        pages = (int) Math.ceil(nums * 1.0 / size);
        this.first = 1;
        this.last = pages;
        if (curr < 1) {
            curr = 1;
        }
        if (curr > pages) {
            curr = pages;
        }
        pre = curr - 1;
        next = curr + 1;
        if (pre < 1) {
            pre = 1;
        }
        if (next > pages) {
            next = pages;
        }

        data = new ArrayList<>();
        pageList = new ArrayList<>();
        setPageList();
    }

    private void setPageList() {
        if (pages < 7) {
            for (int i = 1; i <= pages; i++) {
                pageList.add(i);
            }
            return;
        }
        if (curr - 3 <= 0) {
            for (int i = 1; i <= 7; i++) {
                pageList.add(i);
            }
            return;
        }
        if (curr + 3 > pages) {
            for (int i = pages - 6; i <= pages; i++) {
                pageList.add(i);
            }
            return;
        }
        for (int i = curr - 3; i <= curr + 3; i++) {
            pageList.add(i);
        }
    }

    public List<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(List<Integer> pageList) {
        this.pageList = pageList;
    }

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }
}
