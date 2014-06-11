package com.compomics.spectrawl.logic.filter.impl;

import com.compomics.spectrawl.logic.filter.Filter;
import com.compomics.spectrawl.logic.filter.FilterChain;
import com.compomics.util.experiment.biology.AminoAcid;
import com.compomics.util.experiment.biology.NeutralLoss;
import com.compomics.util.experiment.biology.PTM;
import com.compomics.util.experiment.biology.ions.ReporterIon;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: niels Date: 16/02/12 Time: 14:02 To change
 * this template use File | Settings | File Templates.
 *
 * @param <T>
 */
public class FilterChainImpl<T> implements FilterChain<T> {

    //@TODO add a name to the chain? Like when a modification the modification name?
    private FilterChainType filterChainType;
    private List<Filter<T>> filters = new ArrayList<>();

    public FilterChainImpl() {
    }

    /**
     * FilterChainImpl constructor.
     *
     * @param filterChainType the filter chain type: i.e. the logical operator
     * that is used to combine the different filters of the chain (AND or OR)
     */
    public FilterChainImpl(FilterChainType filterChainType) {
        this();
        this.filterChainType = filterChainType;
    }

    public FilterChainType getFilterChainType() {
        return filterChainType;
    }

    @Override
    public void setFilterChainType(FilterChainType filterChainType) {
        this.filterChainType = filterChainType;
    }

    @Override
    public boolean passesFilter(T t, boolean doInvert) {
        boolean passesFilterChain = filterChainType.getStartBooleanValue();
        for (Filter<T> filter : filters) {
            switch (filterChainType) {
                case AND:
                    passesFilterChain = passesFilterChain && filter.passesFilter(t, false);
                    break;
                case OR:
                    passesFilterChain = passesFilterChain || filter.passesFilter(t, false);
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

    @Override
    public void reset() {
        filters.clear();
    }

    @Override
    public List<Filter<T>> getFilters() {
        return filters;
    }

    @Override
    public void setFilters(List<Filter<T>> filters) {
        this.filters = filters;
    }

    /**
     * Creates a filter chain based on a ptm
     *
     * @param ptm the ptm of interest
     * @return
     */
    public static FilterChainImpl getFilterChain(PTM ptm) {
        FilterChainImpl result = new FilterChainImpl(FilterChainType.OR);
        for (NeutralLoss neutralLoss : ptm.getNeutralLosses()) {
            result.addFilter(new BasicMassDeltaFilter(neutralLoss.mass, null)); // Not sure whether I got that right
        }
        for (ReporterIon reporterIon : ptm.getReporterIons()) {
            result.addFilter(new BasicMzFilter(reporterIon.getTheoreticMass(), new ArrayList<Double>())); //@TODO: add mzRatioFilterValues
        }
        return result;
    }

    /**
     * Creates a filter based on a peptide fragment
     *
     * @param peptideFragment the peptide fragment
     * @param accountedCharges the accounted charges
     * @param accountedPtms the accounted PTMs
     * @return the corresponding filter chain
     */
    public static FilterChainImpl getFilterChain(String peptideFragment, ArrayList<Integer> accountedCharges, ArrayList<PTM> accountedPtms) {
        FilterChainImpl result = new FilterChainImpl(FilterChainType.OR);
        AminoAcid aa;
        CombFilter combFilter;
        double massShift;
        for (int charge : accountedCharges) {
            combFilter = new CombFilter();
            for (int i = 0; i < peptideFragment.length(); i++) {
                aa = AminoAcid.getAminoAcid(peptideFragment.charAt(i));
                massShift = aa.monoisotopicMass;
                for (PTM ptm : accountedPtms) {
                    if (ptm.getResidues().contains(aa.name)) {
                        massShift += ptm.getMass();
                    }
                }
                combFilter.addTooth(massShift / charge);
            }
            result.addFilter(combFilter);
        }
        return result;
    }

    /**
     * Creates a filter based on a peptide fragment
     *
     * @param peptideFragment the peptide fragment
     * @param accountedCharges the accounted charges
     * @return the corresponding filter chain
     */
    public static FilterChainImpl getFilterChain(String peptideFragment, ArrayList<Integer> accountedCharges) {
        return getFilterChain(peptideFragment, accountedCharges, new ArrayList<PTM>());
    }

    /**
     * Creates a filter based on a peptide fragment
     *
     * @param peptideFragment the peptide fragment
     * @param accountedCharges the accounted charges
     * @param ptm the accounted PTM
     * @return the corresponding filter chain
     */
    public static FilterChainImpl getFilterChain(String peptideFragment, ArrayList<Integer> accountedCharges, PTM ptm) {
        ArrayList<PTM> ptms = new ArrayList<>();
        ptms.add(ptm);
        return getFilterChain(peptideFragment, accountedCharges, ptms);
    }
}
