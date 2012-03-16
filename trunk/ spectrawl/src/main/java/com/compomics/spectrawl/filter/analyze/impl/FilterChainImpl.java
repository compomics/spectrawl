package com.compomics.spectrawl.filter.analyze.impl;

import com.compomics.spectrawl.filter.analyze.Filter;
import com.compomics.spectrawl.filter.analyze.FilterChain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 16/02/12
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class FilterChainImpl<T> implements FilterChain<T> {

    private FilterChainType filterChainType;
    private List<Filter<T>> filters;

    /**
     * FilterChainImpl constructor.
     *
     * @param filterChainType the filter chain type: i.e. the logical operator
     *                        that is used to combine
     *                        the different filters of the chain (AND or OR)
     */
    public FilterChainImpl(FilterChainType filterChainType) {
        this.filterChainType = filterChainType;
        filters = new ArrayList<Filter<T>>();
    }

    @Override
    public boolean passesFilter(T t, boolean doInvert) {
        boolean passesFilterChain = filterChainType.getStartBooleanValue();
        for (Filter<T> filter : filters) {
            switch (filterChainType) {
                case AND:
                    passesFilterChain = passesFilterChain && filter.passesFilter(t, Boolean.FALSE);
                    break;
                case OR:
                    passesFilterChain = passesFilterChain || filter.passesFilter(t, Boolean.FALSE);
                    break;
            }
        }

        if (doInvert) {
            passesFilterChain = !passesFilterChain;
        }

        return passesFilterChain;
    }

    @Override
    public void addFilter(Filter<T> filter) {
        filters.add(filter);
    }
}
