package com.compomics.spectrawl.logic.filter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 15/02/12
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public interface Filter<T> {

    /**
     * Returns the boolean result of the filter applied on t
     *
     * @param t the entity to be filtered
     * @param doInvert the invert the filter boolean
     * @return the boolean result of the filtering
     */
    boolean passesFilter(T t, boolean doInvert);

}
