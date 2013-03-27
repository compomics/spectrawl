package com.compomics.spectrawl.logic.filter.mzratio;

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
     * Adds a filter to the chain
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
}
