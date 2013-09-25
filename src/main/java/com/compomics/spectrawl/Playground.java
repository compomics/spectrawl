package com.compomics.spectrawl;

import com.compomics.spectrawl.config.ApplicationContextProvider;
import com.compomics.spectrawl.logic.filter.FilterChain;
import com.compomics.spectrawl.logic.filter.impl.BasicMassDeltaFilter;
import com.compomics.spectrawl.logic.filter.impl.FilterChainImpl;
import com.compomics.spectrawl.logic.filter.impl.FixedCombMassDeltaFilter;
import com.compomics.spectrawl.logic.filter.impl.VariableCombMassDeltaFilter;
import com.compomics.spectrawl.model.Experiment;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.spectrawl.repository.MsLimsExperimentRepository;
import com.compomics.spectrawl.service.MsLimsExperimentService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Niels Hulstaert
 */
public class Playground {

    public static void main(String[] args) {
        //add hoc testing of filters
        ApplicationContext applicationContext = ApplicationContextProvider.getInstance().getApplicationContext();
//        MsLimsExperimentRepository msLimsExperimentRepository = applicationContext.getBean("msLimsExperimentRepository", MsLimsExperimentRepository.class);
//        msLimsExperimentRepository.setDoNoiseFiltering(true);
//        
//        SpectrumImpl spectrum = msLimsExperimentRepository.getSpectrum(1076L, 51548701);
//        
//        FixedCombMassDeltaFilter fixedCombMassDeltaFilter = applicationContext.getBean("fixedCombMassDeltaFilter", FixedCombMassDeltaFilter.class);
//        
//        fixedCombMassDeltaFilter.init(0.05, 7, 7, 129.04);
//        boolean passesFilter = fixedCombMassDeltaFilter.passesFilter(spectrum, false); 
//        System.out.println(passesFilter);        

        //get main filter chain
        FilterChain<SpectrumImpl> spectrumFilterChain = applicationContext.getBean("filterChain", FilterChainImpl.class);
        //init filter chain        
        spectrumFilterChain.setFilterChainType(FilterChain.FilterChainType.AND);

        //for each mass delta filter value, make a new filter and add it to an "or" filter chain
        FilterChain<SpectrumImpl> orFilterChain = new FilterChainImpl<SpectrumImpl>();
        orFilterChain.setFilterChainType(FilterChain.FilterChainType.OR);
        
        List<Double> massDeltaFilterValues = new ArrayList<Double>();
        massDeltaFilterValues.add(67.042185);
        massDeltaFilterValues.add(83.0371);    
        
        for (double value : massDeltaFilterValues) {
            BasicMassDeltaFilter basicMassDeltaFilter = new BasicMassDeltaFilter();
            
            //set the intensity threshold, tweakable
            basicMassDeltaFilter.setIntensityThreshold(0.01);
            
            List<Double> singleFilterMassDeltaFilterValues = new ArrayList<Double>();
            singleFilterMassDeltaFilterValues.add(value);
            basicMassDeltaFilter.setMassDeltaFilterValues(singleFilterMassDeltaFilterValues);
            orFilterChain.addFilter(basicMassDeltaFilter);
        }
        
        //add "or" filter chain to the main filter chain       
        spectrumFilterChain.addFilter(orFilterChain);
        
        //get experiment bean
        MsLimsExperimentService msLimsExperimentService = applicationContext.getBean("msLimsExperimentService", MsLimsExperimentService.class);
        List<Long> spectrumIds = new ArrayList();
        spectrumIds.add(51548701L);
        spectrumIds.add(51548702L);
        //load spectra
        Experiment stubExperiment = msLimsExperimentService.loadExperiment(spectrumIds);
        
        System.out.println("test");
    }
}
