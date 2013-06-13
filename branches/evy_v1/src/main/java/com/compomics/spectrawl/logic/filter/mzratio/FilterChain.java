package com.compomics.spectrawl.logic.filter.mzratio;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 15/02/12
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public interface FilterChain<T> extends Filter<T> {

    public enum FilterChainType {
        AND(Boolean.TRUE), OR(Boolean.FALSE);

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
     * @param filter
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
     */
    void setFilters(List<Filter<T>> filters);
    
    /**
     * Reset the filter chain. This removes all filters.
     */
    void reset();
}
