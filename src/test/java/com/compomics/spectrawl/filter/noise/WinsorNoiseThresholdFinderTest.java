package com.compomics.spectrawl.filter.noise;

import com.compomics.spectrawl.logic.filter.noise.NoiseThresholdFinder;
import com.compomics.spectrawl.logic.filter.noise.impl.WinsorNoiseThresholdFinder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 28/10/11
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springXMLConfig.xml")
public class WinsorNoiseThresholdFinderTest {

    @Autowired
    private NoiseThresholdFinder noiseThresholdFinder;
    private static double [] values;

    @BeforeClass
    public static void setUponce() throws Exception {
        Resource resource = new ClassPathResource("NoiseFilter_TestData.txt");

        BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));

        String line = null;
        List valuesList = new ArrayList<Double>();
        while( (line = br.readLine()) != null ){
            valuesList.add(Double.parseDouble(line));
        }
        values = ArrayUtils.toPrimitive((Double[])valuesList.toArray(new Double[valuesList.size()]));

        br.close();
    }

    @Test
    public void testWinsorNoiseTresholdFinder(){        
        double threshold = noiseThresholdFinder.findNoiseThreshold(values);

        assertTrue(threshold < 105D);
    }

}
