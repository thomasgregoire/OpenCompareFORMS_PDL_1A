package org.opencompare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * Created by Jules on 07/11/2016.
 */

public class HTMLCreator {

    public String HTMLString(Map<String, List<String>> map) {
        String texte = "<html>\n" +
                "    <head>\n" +
                "        <title>HTML de base</title>\n" +
                "        <!-- Latest compiled and minified CSS -->\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
                "        <link rel=\"stylesheet\" href=\"formulaire.css\">\n" +
                "\n" +
                "        <script src=\"https://code.jquery.com/jquery-3.1.0.min.js\" defer></script>\n" +
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

            if (map.get(FeatureBalise).size() == 1) {

                texte = texte + "<div class=\"col-sm-12\">\n" +
                        "                <label class=\"col-sm-1 col-form-label\">" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" + map.get(FeatureBalise).get(0) + "\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <br /><br />";

            } else{

                texte = texte + "<div class=\"col-sm-12\">\n" +
                        "                <label class=\"col-sm-1 col-form-label\">" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" + map.get(FeatureBalise).get(0) + "\n" +
                        "                </div>\n" +


                        "           <div class =\"col-sm-1\">\n" +
                        "                    <input type=\"button\" class=\"btn btn-lg btn-primary btn-sm\" value=\"alert\">\n" +
                        "                </div>" +
                        "            </div>\n" +
                        "            <br /><br />"+

                        "<div class=\"col-sm-12\">\n" +
                        "                <label class=\"col-sm-1 col-form-label\">" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" + map.get(FeatureBalise).get(1) + "\n" +
                        "                </div>\n" +


                        "           <div class =\"col-sm-1\">\n" +
                        "                    <input type=\"button\" class=\"btn btn-lg btn-primary btn-sm\" value=\"alert\">\n" +
                        "                </div>" +
                        "            </div>\n" +
                        "            <br /><br />";

            }
        }







        texte = texte +
                "           <div class=\"col-sm-12\">\n" +
                "            <input type=\"button\" class=\"btn btn-lg btn-primary\" value=\"Valider\">\n" +
                "           </div>" +
                "            <br /><br />\n" +
                "        </form>" +
                "       <script src=\"verif.js\" type=\"text/javascript\"></script>\n" +
                "    </body>\n" +
                "\n" +
                "</html>";

        return texte;
    }

    public void insertTexte(String text) throws Exception {

        try {
            BufferedWriter out = new
                    BufferedWriter(new FileWriter("test.html"));
            out.write(text);
            out.close();
            System.out.println
                    ("File created successfully");
        } catch (IOException e) {
        }

    }

}



