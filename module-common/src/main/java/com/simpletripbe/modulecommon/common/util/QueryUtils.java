package com.simpletripbe.modulecommon.common.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import java.util.stream.Collectors;

public class QueryUtils {
    private QueryUtils() {
    }

    public static List<OrderSpecifier> toOrderSpecifiers(Path<?> path, Sort sort) {
        return sort.stream()
                .map(it -> new OrderSpecifier(
                        toOrder(it),
                        Expressions.path(Object.class, path, it.getProperty())
                ))
                .collect(Collectors.toList());
    }

    private static Order toOrder(Sort.Order sortOrder) {
        return sortOrder.getDirection().isAscending() ? Order.ASC : Order.DESC;
    }

    public static <T> Page<T> toPage(QueryResults<T> queryResults, Pageable pageable) {
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }


}

