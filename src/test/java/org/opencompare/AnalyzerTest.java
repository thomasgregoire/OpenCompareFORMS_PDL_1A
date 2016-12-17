package org.opencompare;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.PCMMetadata;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.ExportMatrix;
import org.opencompare.api.java.io.ExportMatrixExporter;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static junit.framework.TestCase.*;

/**
 * Created by MSI_Thomas on 21/11/2016.
 */
public class AnalyzerTest {

    public Map<String, ArrayList<String>> features;
    public Map<String, ArrayList<String>> contents;

    @Before
    public void loadPCM() throws IOException {
        File pcmFile = new File("pcms/Comparison_of_BitTorrent_sites_0.pcm");
        PCMLoader loader = new KMFJSONLoader();
        List<PCMContainer> pcmContainers = loader.load(pcmFile);
        PCMContainer pcmc = pcmContainers.get(0);
        ExportMatrixExporter eme = new ExportMatrixExporter();
        ExportMatrix em = eme.export(pcmc);
        PCM pcm = pcmc.getPcm();
        Analyzer a = new Analyzer();
        features = a.getTypeFeatures(em, pcm);
        contents =  a.getContentFeatures(em, pcm);
    }

    @Test
    public void testAnalyzerTypes() {
        assertNotNull(features);
        Iterator it = features.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList value = (ArrayList) pair.getValue();
            assertFalse(value.isEmpty());
            assertFalse(value.contains("NotAvailableImpl"));
            assertFalse(value.contains("NotApplicableImpl"));
            assertFalse(value.contains("ValueImpl"));
            assertTrue(value.size() <= 2 && value.size() > 0);
        }
    }

    @Test
    public void testAnalyzerContent() {
        assertNotNull(contents);
        assertNotNull(features);
        assertEquals(contents.size(), features.size());
        Iterator it = features.entrySet().iterator();
        Iterator it2 = contents.entrySet().iterator();
        while (it.hasNext() && it2.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            Map.Entry pair2 = (Map.Entry) it2.next();
            assertEquals(pair.getKey(), pair2.getKey());
        }
    }

}
