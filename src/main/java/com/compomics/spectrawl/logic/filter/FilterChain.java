package com.compomics.spectrawl.logic.filter;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 15/02/12
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 * @param <T>
 */
public interface FilterChain<T> extends Filter<T> {

    public enum FilterChainType {
        AND(true), OR(false);

        private boolean startBooleanValue;

        FilterChainType(boolean startBooleanValue) {
            this.startBooleanValue = startBooleanValue;
        }

        public boolean getStartBooleanValue(){
            return startBooleanValue;
        }
    }

    /**
     * Add a filter to the chain.
     *
     * @param filter
     */
    void addFilter(Filter<T> filter);
    
    /**
     * Set the filter chain type.
     * 
     * @param filterChainType
     */
    void setFilterChainType(FilterChainType filterChainType);
    
    /**
     * Get the filters of the chain
     *  
     * @return the list of filters
     */
    List<Filter<T>> getFilters();
    
    /**
     * Set the filters of the chain
     * @param filters
     */
    void setFilters(List<Filter<T>> filters);
    
    /**
     * Reset the filter chain. This removes all filters.
     */
    void reset();
}
