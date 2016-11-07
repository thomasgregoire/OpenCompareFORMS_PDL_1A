package org.opencompare;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by BJBPrudor on 07/11/2016.
 */
public class FormGenerator
{

    public static void main(String[] args) throws java.io.IOException
    {

        File pcmFile = new File("model/euro.pcm");
        PCMLoader loader = new KMFJSONLoader();
        List<PCMContainer> pcmContainers = loader.load(pcmFile);
        PCMContainer pcmc = pcmContainers.get(0);
        PCM pcm = pcmc.getPcm();

        Analyzer a= new Analyzer();
        a.findFeatures(pcm);
        Map<String,String> feats = a.getFeatureContainer();
        Map<String,String> beacon = HTMLGenerator.GenerateFrom(feats);

    }

}
