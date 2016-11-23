package org.opencompare;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static junit.framework.TestCase.*;

/**
 * Created by MSI_Thomas on 21/11/2016.
 */
public class FormGeneratorTest {

    public Map<String, String> features;
    public PCM pcm;

    @Before
    public void loadPCM() throws IOException {
        File pcmFile = new File("pcms/example.pcm");
        PCMLoader loader = new KMFJSONLoader();
        pcm = loader.load(pcmFile).get(0).getPcm();
        Analyzer a = new Analyzer();
        features = a.getTypeFeatures(pcm);
        assertNotNull(features);
    }

    @Test
    public void testAnalyzerTypes() {
        Iterator it = features.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList value = (ArrayList) pair.getValue();
            assertFalse(value.contains("NotAvailableImpl"));
            assertFalse(value.contains("NotApplicableImpl"));
            assertFalse(value.contains("ValueImpl"));
        }
    }

    @Test
    public void testAnalyzerFeatures() {
        assertEquals(pcm.getConcreteFeatures().size(), features.size());
    }

}
