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

    public static void main(String[] args) throws Exception
    {

        File pcmFile = new File("pcms/euro.pcm");
        PCMLoader loader = new KMFJSONLoader();
        List<PCMContainer> pcmContainers = loader.load(pcmFile);
        PCMContainer pcmc = pcmContainers.get(0);
        PCM pcm = pcmc.getPcm();

        Analyzer a= new Analyzer();
        HTMLCreator creator = new HTMLCreator();

        Map<String,String> features = a.getTypeFeatures(pcm); // récupère les features

        Map<String,String> feats = a.getContentFeatures(pcm); //récupere les
        Map<String,String> beacon = HTMLGenerator.GenerateFrom(feats);

        String text = creator.HTMLString(features);
        creator.insertTexte(text);

    }

}
