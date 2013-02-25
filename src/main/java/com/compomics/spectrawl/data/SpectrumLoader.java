package com.compomics.spectrawl.data;

/**
 * Created by IntelliJ IDEA. User: niels Date: 3/02/12 Time: 10:51 To change
 * this template use File | Settings | File Templates.
 */
public interface SpectrumLoader {

    /**
     * Set the do noise filtering boolean. The default value can be set in the
     * spectrawl.properties file.
     *
     * @param doNoiseFiltering the do noise filtering boolean
     */
    void setDoNoiseFiltering(boolean doNoiseFiltering);
}
