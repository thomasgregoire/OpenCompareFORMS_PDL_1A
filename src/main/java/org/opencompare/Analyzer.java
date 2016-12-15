package org.opencompare;

import org.opencompare.api.java.*;
import org.opencompare.api.java.io.ExportCell;
import org.opencompare.api.java.io.ExportMatrix;
import org.opencompare.api.java.io.ExportMatrixExporter;

import java.util.*;

/**
 * Created by MSI_Thomas on 20/10/2016.
 */
public class Analyzer {

    private final int POURCENTAGE = 7;

    public Map getTypeFeatures(ExportMatrix em, PCM pcm) {
        HashMap featureTypeContainer = new HashMap<String, ArrayList<String>>();
        Collection<String> nomFeature = new ArrayList<String>();
        String nom = "";
        for (int i = 0; i < em.getNumberOfColumns(); i++) {
            for (int j = 0; j < em.getNumberOfRows(); j++) {
                ExportCell c = em.getCell(j, i);
                if (c != null) {
                    if (c.isFeature()) {
                        nom = c.getContent();
                    }
                }
            }
            nomFeature.add(nom);
        }

        if (!featureTypeContainer.isEmpty()) {
            featureTypeContainer.clear();
        }
        System.out.println(pcm.getFeatures().size());
        for (Feature feature : pcm.getConcreteFeatures()) {
            if (nomFeature.contains(feature.getName())) {
                HashMap typeContainer = new HashMap<String, Integer>();
                List lesTypes = new ArrayList<String>();
                int i;
                for (Cell c : feature.getCells()) {
                    if (c.getInterpretation() != null) {
                        if (!typeContainer.containsKey(c.getInterpretation().getClass().getSimpleName())) {
                            typeContainer.put(c.getInterpretation().getClass().getSimpleName(), 1);
                        } else {
                            i = (Integer) typeContainer.get(c.getInterpretation().getClass().getSimpleName());
                            typeContainer.put(c.getInterpretation().getClass().getSimpleName(), i + 1);
                        }
                    }
                }
                typeContainer.remove("NotApplicableImpl");
                typeContainer.remove("NotAvailableImpl");
                typeContainer.remove("ValueImpl");
                if (!typeContainer.isEmpty()) {

                    String type = "";
                    String type2 = "";
                    int val = 0;
                    int val2 = 0;

                    Iterator it = typeContainer.entrySet().iterator();


                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        String key = (String) pair.getKey();
                        int value = (Integer) pair.getValue();

                        if (value > val) {
                            if (val > 0) {
                                type2 = type;
                                val2 = val;
                            }
                            type = key;
                            val = value;
                        } else if (value > val2) {
                            type2 = key;
                            val2 = value;
                        }


                    }

                    lesTypes.add(type);

                    double pourc = val2 / (typeContainer.size()) * 100;
                    if (pourc > POURCENTAGE) {
                        lesTypes.add(type2);
                    }
                    System.out.println(lesTypes.toString());
                    featureTypeContainer.put(feature.getName(), lesTypes);
                } else {
                    lesTypes.add("StringValueImpl");
                    featureTypeContainer.put(feature.getName(), lesTypes);
                }
            }
        }
        return featureTypeContainer;
    }

    public Map getContentFeatures(PCM pcm) {
        HashMap featureContentContainer = new HashMap();
        return featureContentContainer;
    }
}
