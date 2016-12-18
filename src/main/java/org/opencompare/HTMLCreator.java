package org.opencompare;

import org.opencompare.api.java.PCM;
import org.opencompare.api.java.io.ExportMatrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jules on 07/11/2016.
 */

public class HTMLCreator {

    int compteur = 0;

    String script = "";

    private static String deleteSpace(String aModifier){

        return aModifier.replaceAll(" ", "");

    }

    public static String recupID(String string){
        String result="";

        Pattern p = Pattern.compile("id=\".*\"");

        Matcher m = p.matcher(string);

        while (m.find()) {
            //System.out.println("groupe = " + m.group() + "\n") ;
            result= m.group();
        }

        result.replaceAll("id=", result);
        //result.substring(2, endIndex)
        result = result.substring(4, result.length()-1);

        return result;
    }

    public static boolean exclude(String string){
        return string.contains("(");
    }

    public String HTMLString(Map<String, List<String>> map, Map<String,List<String>> donnees) {

        System.out.println(donnees.size());




        String texte = "<html>\n" +
                "    <head>\n" +
                "        <title>HTML de base</title>\n" +
                "        <!-- Latest compiled and minified CSS -->\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
                "        <link rel=\"stylesheet\" href=\"formulaire.css\">\n" +
                "\n" +
                "      <link href = \"https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css\"\n" +
                "         rel = \"stylesheet\">\n" +
                "      <script src = \"https://code.jquery.com/jquery-1.10.2.js\"></script>\n" +
                "      <script src = \"https://code.jquery.com/ui/1.10.4/jquery-ui.js\"></script>" +
                "\n" +
                "\n" +
                "\n" +
                "    </head>\n" +
                "    <body>\n" +
                "    <h1>HTML crée depuis java</h1>\n" +
                "\n" +
                "        <form>\n";





        for (String FeatureBalise : map.keySet()) {
            System.out.println("------------------------------------");
            System.out.println(map.get(FeatureBalise));
            System.out.println(recupID(map.get(FeatureBalise).get(0)));



            if (map.get(FeatureBalise).size() == 1) {

                texte = texte + "<div class=\"col-sm-12 ui-widget\">\n" +
                        "                <label class=\"col-sm-1 col-form-label\" for = \"" + recupID(map.get(FeatureBalise).get(0)) + "\" >" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" + map.get(FeatureBalise).get(0) + "\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <br /><br />";

                if(!exclude(recupID(map.get(FeatureBalise).get(0)))) {
                    script = script + "<script>\n" +
                            "         $(function() {\n" +
                            "            var availableTutorials  =  " + donnees.get(FeatureBalise) + "\n" +
                            "            $( \"#" + recupID(map.get(FeatureBalise).get(0)) + "\").autocomplete({\n" +
                            "               source: availableTutorials\n" +
                            "            });\n" +
                            "         });\n" +
                            "      </script>\n";
                }

            } else{

                texte = texte +
                        "                <div class=\"col-sm-12 ui-widget\" id='" + compteur + " uno' style=\"display:inline\" >\n" +
                        "                <label class=\"col-sm-1 col-form-label\" for = \"" + recupID(map.get(FeatureBalise).get(0)) + "\" >" + FeatureBalise + "</label>\n" +
                            "                <div class=\"col-sm-6\">\n" +
                                                map.get(FeatureBalise).get(0) + "\n" +
                            "                </div>\n" +

                        "           <div class =\"col-sm-1\">\n" +
                        "                    <input type=\"button\" class=\"btn btn-lg btn-primary btn-sm\" value=\"changer\" onClick=\"afficher_cacher('" + compteur + " uno', '" + compteur + " bis')\">\n" +
                        "                </div>\n" +

                        "                </div>\n"+


                        "                <div class=\"col-sm-12 ui-widget\" id='" + compteur + " bis' style=\"display:none\">\n" +
                        "                <label class=\"col-sm-1 col-form-label\" for = \"" + recupID(map.get(FeatureBalise).get(1)) + "\" >" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" + map.get(FeatureBalise).get(1) + "\n" +
                        "                </div>\n" +

                        "           <div class =\"col-sm-1\">\n" +
                        "                    <input type=\"button\" class=\"btn btn-lg btn-primary btn-sm\" value=\"changer\" onClick=\"afficher_cacher('" + compteur + " uno', '" + compteur + " bis')\">\n" +
                        "                </div>\n" +

                        "                </div>\n" +
                        "            <br /><br />\n";

                if(!exclude(recupID(map.get(FeatureBalise).get(0)))) {
                    script = script +
                            "<script>\n" +
                            "         $(function() {\n" +
                            "            var availableTutorials  =  " + donnees.get(FeatureBalise) + "\n" +
                            "            $( \"#" + recupID(map.get(FeatureBalise).get(0)) + "\").autocomplete({\n" +
                            "               source: availableTutorials\n" +
                            "            });\n" +
                            "         });\n" +
                            "      </script>\n" +

                            "<script>\n" +
                            "         $(function() {\n" +
                            "            var availableTutorials  =  " + donnees.get(FeatureBalise) + "\n" +
                            "            $( \"#" + recupID(map.get(FeatureBalise).get(1)) + "\").autocomplete({\n" +
                            "               source: availableTutorials\n" +
                            "            });\n" +
                            "         });\n" +
                            "      </script>\n";


                }

            }
            //System.out.println(script);
            compteur++;
        }







        texte = texte +
                "           <div class=\"col-sm-12\">\n" +
                "            <input type=\"button\" class=\"btn btn-lg btn-primary\" value=\"Valider\">\n" +
                "           </div>" +
                "            <br /><br />\n" +
                "        </form>" +
                "       <script src=\"verif.js\" type=\"text/javascript\"></script>\n" +
                "       <script src=\"bouton.js\" type=\"text/javascript\"></script>\n" +
                        script +
                "    </body>\n" +
                "\n" +
                "</html>";

        return texte;
    }

    public void insertTexte(String text, String path) throws Exception {

        try {
            BufferedWriter out = new
                    BufferedWriter(new FileWriter(path));
            out.write(text);
            out.close();
            System.out.println
                    ("File created successfully");
        } catch (IOException e) {
        }

    }

}



