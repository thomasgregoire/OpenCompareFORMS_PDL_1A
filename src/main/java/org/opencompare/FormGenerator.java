package org.opencompare;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.ExportMatrix;
import org.opencompare.api.java.io.ExportMatrixExporter;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by BJBPrudor on 07/11/2016.
 * Cette classe permet de tester l'application depuis un IDE
 */
public class FormGenerator
{

    public static void main(String[] args) throws Exception
    {

        String path = "test.html"; // chemin d'accès du fichier de sortie
        File pcmFile = new File("pcms/euro.pcm");
        PCMLoader loader = new KMFJSONLoader();
        List<PCMContainer> pcmContainers = loader.load(pcmFile);
        PCMContainer pcmc = pcmContainers.get(0); // on recupere le premier PCMContainer du fichier
        PCM pcm = pcmc.getPcm(); // on recupere le PCM

        Analyzer a= new Analyzer();
        HTMLCreator creator = new HTMLCreator();

        ExportMatrixExporter eme = new ExportMatrixExporter();
        ExportMatrix em = eme.export(pcmc);
        Map<String,List<String>> features = a.getTypeFeatures(em,pcm); // on analyse et recupere les Features et leur type

        Map<String,List<String>> feats = a.getContentFeatures(em,pcm); // on analyse et recupere les données des Features

        Map<String,List<String>> beacon = HTMLGenerator.GenerateFrom(features); // on creer les balises HTML5 à partir des types des Features

        String text = creator.HTMLString(beacon,feats); // on Creer le formulaire HTML5 à partir des balises et données des Features
        creator.insertTexte(text,path); // on enregistre le formulaire dans un fichier situé au chemin d'accès path

    }

}
