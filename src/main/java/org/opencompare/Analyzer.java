package org.opencompare;

import org.opencompare.api.java.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MSI_Thomas on 20/10/2016.
 */
public class Analyzer {

    private HashMap featureContainer = new HashMap();

    public void findFeatures(PCM pcm){
        if (!featureContainer.isEmpty()){
            featureContainer.clear();
        }
        for (Feature feature : pcm.getConcreteFeatures()) {
            featureContainer.put(feature.getName(), feature.getCells().get(0).getInterpretation().getClass().toString());
        }
    }

    public Map getFeatureContainer() {
        return featureContainer;
    }
}
