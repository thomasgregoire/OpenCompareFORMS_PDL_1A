package org.opencompare;

import org.junit.Before;
import org.junit.Test;
import org.opencompare.api.java.Cell;
import org.opencompare.api.java.Feature;
import org.opencompare.api.java.PCM;
import org.opencompare.api.java.PCMContainer;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.*;
import java.util.*;

/**
 * Created by BJBPrudor on 23/11/2016
 * Cette classe permet de recuperer les differents données de PCMs dans un dossier organisées par type
 * cela a permit de determiner la nature de plusieurs types
 */
public class OpCompTypeTest
{

    public static final File infolder = new File("pcms/");
    public static final String outfolder = "output/";
    public static Map<String,List<InfoType>> data = new HashMap<String, List<InfoType>>();
    public static final int maxReturn = 2000;

    @Test
    public void TestTypeOfPCM()
    {

        PrepareMap();

        int i = 0;
        for (File f : infolder.listFiles())
        {
            i++;
            PCM pcm = OpenPCM(f);
            if (pcm !=null) { ReadPCM(pcm); }
            System.out.println("File n°" + i + " has been read");
        }

        WriteFile(data);

    }

    @Before
    public void PrepareMap()
    {
        for (HTMLGenerator.OCType t : HTMLGenerator.OCType.values())
        {
            data.put(t.name(), new ArrayList<InfoType>());
        }
    }

    @Before
    public PCM OpenPCM(File f)
    {
        try
        {
            PCMLoader loader = new KMFJSONLoader();
            List<PCMContainer> pcmContainers = loader.load(f);
            PCMContainer pcmc = pcmContainers.get(0);
            return pcmc.getPcm();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            return null;
        }
    }

    public void ReadPCM(PCM pcm)
    {
        for (Feature feature : pcm.getConcreteFeatures())
        {

            if(feature.getCells().size() > 0)
            {

                for (Cell c : feature.getCells())
                {

                    String tp = "",fr = "",fe = "",va = "";
                    try
                    {
                        if(c.getInterpretation() != null)
                        {
                            tp = c.getInterpretation().getClass().getSimpleName();
                        }
                        fr = pcm.getName();
                        fe = feature.getName();
                        va = c.getContent();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.toString());
                        continue;
                    }

                    InfoType info = new InfoType(fr,fe,tp,va);

                    if (data.get(tp) == null)
                    {
                        data.put(tp,new ArrayList<InfoType>());
                    }
                    data.get(tp).add(info);

                }

            }

        }

    }

    public void WriteFile(Map m)
    {

        FileOutputStream fop = null;
        File out;
        String content = "";

        for (HTMLGenerator.OCType t : HTMLGenerator.OCType.values())
        {
            content = CreateFileContent(t.name());
            try
            {
                PrintWriter writer = new PrintWriter(outfolder + t.name() + ".txt", "UTF-8");
                writer.println(content);
                writer.close();
                System.out.println("File " + t.name() + " Done");
            }
            catch (IOException e)
            {
                System.out.println(e.toString());
            }
        }

    }

    public String CreateFileContent(String cat)
    {
        String res = "";
        List<InfoType> lif = data.get(cat);
        if(lif.size() > 0)
        {

            List<InfoType> clif;
            if(lif.size() < maxReturn)
            {
                clif = lif.subList(0,lif.size());
            }
            else
            {
                clif = lif.subList(0,maxReturn);
            }

            for(int i = 0; i < clif.size();i++)
            {
                res += clif.get(i).value + " from : " + clif.get(i).file + "\n";
            }

        }
        return  res;
    }

    class InfoType
    {

        public String file;
        public String feat;
        public String type;
        public String value;

        public InfoType(String f1, String f2, String t, String v)
        {
            file = f1;
            feat = f2;
            type = t;
            value = v;
        }

        @Override
        public String toString()
        {
            return "Type : " + type + "; Valeur : " + value + "; Provient de : " + file + " : feature : " + feat + " ;";
        }

    }

}
