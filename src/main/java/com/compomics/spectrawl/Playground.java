
package com.compomics.spectrawl;

import com.compomics.spectrawl.config.ApplicationContextProvider;
import com.compomics.spectrawl.logic.filter.impl.FixedCombMassDeltaFilter;
import com.compomics.spectrawl.logic.filter.impl.VariableCombMassDeltaFilter;
import com.compomics.spectrawl.model.SpectrumImpl;
import com.compomics.spectrawl.repository.MsLimsExperimentRepository;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Niels Hulstaert
 */
public class Playground {

    public static void main(String[] args) {
        //add hoc testing of filters
        ApplicationContext applicationContext = ApplicationContextProvider.getInstance().getApplicationContext();
        MsLimsExperimentRepository msLimsExperimentRepository = applicationContext.getBean("msLimsExperimentRepository", MsLimsExperimentRepository.class);
        msLimsExperimentRepository.setDoNoiseFiltering(true);
        
        SpectrumImpl spectrum = msLimsExperimentRepository.getSpectrum(1076L, 51548701);
        
        FixedCombMassDeltaFilter fixedCombMassDeltaFilter = applicationContext.getBean("fixedCombMassDeltaFilter", FixedCombMassDeltaFilter.class);
        
        fixedCombMassDeltaFilter.init(0.05, 7, 7, 129.04);
        boolean passesFilter = fixedCombMassDeltaFilter.passesFilter(spectrum, false); 
        System.out.println(passesFilter);
    }
    
}
