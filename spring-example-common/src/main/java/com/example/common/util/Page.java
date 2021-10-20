package com.example.common.util;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Page<T> {

    private final int size;

    private final int index;

    private final long totalCount;

    private final List<T> content;

    public Page(PageRequest request, long totalCount, List<T> content) {
        this(request.getSize(), request.getIndex(totalCount), totalCount, content);
    }

    public Page(PageRequest request, List<T> list) {
        this(request, list.size(), request.getContent(list));
    }

}
