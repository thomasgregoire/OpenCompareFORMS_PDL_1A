package org.opencompare;

import org.opencompare.api.java.*;

import java.util.HashMap;
import java.util.Iterator;
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
                HashMap typeContainer = new HashMap<String, Integer>();
                int i;
                for (Cell c: feature.getCells()) {
                    if (!typeContainer.containsKey(c.getInterpretation().getClass().getSimpleName())){
                        typeContainer.put(c.getInterpretation().getClass().getSimpleName(), 1);
                    } else {
                        i = (Integer)typeContainer.get(c.getInterpretation().getClass().getSimpleName());
                        typeContainer.put(c.getInterpretation().getClass().getSimpleName(), i + 1);
                    }
                }
                System.out.println(typeContainer.toString());

                Iterator it = typeContainer.entrySet().iterator();

                String type = "";
                int val = 0;

                while (it.hasNext()){
                    Map.Entry pair = (Map.Entry)it.next();
                    String key = (String)pair.getKey();
                    int value = (Integer)pair.getValue();

                    if (!key.equals("NotAvailableImpl") && !key.equals("NotApplicableImpl") && !key.equals("ValueImpl")){
                        if (type.equals("")){
                            type = key;
                            val = value;
                        }else{
                            if ((Integer)pair.getValue() > val){
                                type = key;
                                val = value;
                            }
                        }
                    }
                }

                if(type.equals("")){
                    featureContainer.put(feature.getName(), "StringValueImpl");
                }else{
                    featureContainer.put(feature.getName(), type);
                }
            }
    }

    public Map getFeatureContainer() {
        return featureContainer;
    }
}
