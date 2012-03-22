package com.compomics.spectrawl.filter.process;

import com.compomics.spectrawl.filter.process.NoiseThresholdFinder;
import com.compomics.spectrawl.filter.process.impl.WinsorNoiseThresholdFinder;
import org.apache.commons.lang.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: niels
 * Date: 28/10/11
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class WinsorNoiseThresholdFinderTest {

    private static double [] values;

    @BeforeClass
    public static void setUponce() throws Exception {
        File file = new File(WinsorNoiseThresholdFinderTest.class.getClassLoader().getResource("NoiseFilter_TestData.txt").getPath());

        BufferedReader br = new BufferedReader(new FileReader(file));

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
        NoiseThresholdFinder noiseTresholdFinder = new WinsorNoiseThresholdFinder(1.5, 2, 0.01);
        double threshold = noiseTresholdFinder.findNoiseThreshold(values);

        assertTrue(threshold < 105D);
    }

}