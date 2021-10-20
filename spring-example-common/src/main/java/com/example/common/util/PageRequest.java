package com.example.common.util;

import java.util.List;

import lombok.Value;

@Value
public class PageRequest {

    private final int size;

    private final int index;

    public int getIndex(long totalCount) {
        if (0 <= index) {
            return index;
        }
        return getPageCount(totalCount) + index;
    }

    public int getPageCount(long totalCount) {
        return (int) ((totalCount + size - 1) / size);
    }

    public long getOffset() {
        return (long) size * index;
    }

    public long getOffset(long totalCount) {
        return (long) size * getIndex(totalCount);
    }

    public int getCount(long totalCount) {
        return (int) Math.min(size, totalCount - getOffset(totalCount));
    }

    public <T> List<T> getContent(List<T> list) {
        int start = (int) getOffset(list.size());
        int count = getCount(list.size());
        return list.subList(start, start + count);
    }

}
