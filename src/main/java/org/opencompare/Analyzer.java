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
    private final int POURCENTAGE_CONTENT = 10;

    /***
     *
     * @param em
     * @param pcm
     * @return HashMap <String, ArrayList<String>>
     *  with the name of the features of pcm as key and a list of it's associated types as value
     */
    public Map getTypeFeatures(ExportMatrix em, PCM pcm) {
        HashMap featureTypeContainer = new HashMap<String, ArrayList<String>>();
        Collection<String> nomFeature = getNomFeature(em);
        String nom = "";

        if (!featureTypeContainer.isEmpty()) {
            featureTypeContainer.clear();
        }
        for (Feature feature : pcm.getConcreteFeatures()) {
            if (nomFeature.contains(feature.getName())) { //seul le plus bas niveau de chaque features est traité
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
                // les types non utiles sont ignorés
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
                    //Le type qui apparaît le plus est ajouté à la liste
                    lesTypes.add(type);

                    //Le deuxième type est ajouté si son purcentage d'apparition est assez élévé
                    double pourc = val2 / (typeContainer.size()) * 100;
                    if (pourc > POURCENTAGE) {
                        lesTypes.add(type2);
                    }
                    featureTypeContainer.put(feature.getName(), lesTypes);
                } else {
                    lesTypes.add("StringValueImpl");
                    featureTypeContainer.put(feature.getName(), lesTypes);
                }
            }
        }
        return featureTypeContainer;
    }

    /***
     *
     * @param em
     * @param pcm
     * @return HashMap <String, ArrayList<String>
     *     with the names of the features of pcm as key and a list of the associated cell contents as value.
     *
     *  This method will be used for the auto-completion of the generated form.
     */
    public Map getContentFeatures(ExportMatrix em, PCM pcm) {
        HashMap featureContentContainer = new HashMap<String, ArrayList<String>>();

        Collection<String> nomFeature = getNomFeature(em);
        if(!featureContentContainer.isEmpty()){
            featureContentContainer.clear();
        }
       for(Feature feature : pcm.getConcreteFeatures()){
           if(nomFeature.contains(feature.getName())){ //seul le plus bas niveau de chaque features est traité
               HashMap contentContainer = new HashMap<String, Integer>();
               List listContent = new ArrayList<String>();
               int i;
               for (Cell c : feature.getCells()) {
                   if (c.getContent() != null) {
                       if(!contentContainer.containsKey(c.getContent())){
                           contentContainer.put(c.getContent(), 1);
                       }else{
                           i = (Integer) contentContainer.get(c.getContent());
                           contentContainer.put(c.getContent(), i + 1);
                       }
                   }
               }
               if(!contentContainer.isEmpty()){
                   Iterator it = contentContainer.entrySet().iterator();
                   int size = contentContainer.size();
                   while (it.hasNext()) {
                       Map.Entry pair = (Map.Entry) it.next();
                       String key = (String) pair.getKey();
                       int value = (Integer) pair.getValue();
                       if(value/size * 100 >=POURCENTAGE_CONTENT){
                           listContent.add("\""+key+"\"");
                       }
                   }
               }
               featureContentContainer.put(feature.getName(),listContent);
           }
       }
        return featureContentContainer;
    }

    /***
     *
     * @param em
     * @return an ArrayList<String> of the names of the lowest level of the different features of em
     */
    private Collection<String> getNomFeature(ExportMatrix em){
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
        return nomFeature;
    }
}
