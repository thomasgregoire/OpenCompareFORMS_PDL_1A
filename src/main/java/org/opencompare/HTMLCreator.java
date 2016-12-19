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
 * Cette classe a pour but de récupérer des données depuis Analyzer et HTMLGenerator afin de retourner le HTML et le Javascript
 * à charger dans le fichier HTML généré
 */

public class HTMLCreator {

    int compteur = 0;

    String script = "";

    private static String deleteSpace(String aModifier) {

        return aModifier.replaceAll(" ", "");

    }

    public static String recupID(String string) {
        String result = "";

        Pattern p = Pattern.compile("id=\".*\"");

        Matcher m = p.matcher(string);

        while (m.find()) {
            //System.out.println("groupe = " + m.group() + "\n") ;
            result = m.group();
        }

        result.replaceAll("id=", result);
        //result.substring(2, endIndex)
        result = result.substring(4, result.length() - 1);

        return result;
    }

    public static boolean exclude(String string) {
        return string.contains("(");
    }
    /**
     * Génère le code du fichier html à créer
     * @return String
     */
    public String HTMLString(Map<String, List<String>> map, Map<String, List<String>> donnees) {

        // Header du fichier html (Ne change jamais
        String texte = "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>HTML de base</title>\n" +
                "        <!-- Latest compiled and minified CSS -->\n" +
                "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
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
                "    <div class=\"modal-dialog\" ng-class=\"size ? 'modal-' + size : ''\" style=\"min-width: 75%; max-width: 90%;\">" +
                "       <div class=\"modal-content\" modal-transclude=\"\">" +
                "           <div class=\"modal-header ng-scope\">Ajouter un produit</div>" +
                "           <div class=\"modal-body\">" +
                "\n" +
                "        <form>\n";


        for (String FeatureBalise : map.keySet()) {
            System.out.println("------------------------------------");
            System.out.println(map.get(FeatureBalise));
            System.out.println(recupID(map.get(FeatureBalise).get(0)));


            if (map.get(FeatureBalise).size() == 1) {
                // Création des balises sans double types
                texte = texte + "<div class=\"col-sm-12 ui-widget\">\n" +
                        "                <label class=\"col-sm-3 col-form-label\" for = \"" + recupID(map.get(FeatureBalise).get(0)) + "\" >" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" + map.get(FeatureBalise).get(0) + "\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <br /><br />";

                if (!exclude(recupID(map.get(FeatureBalise).get(0)))) {
                    script = script + "<script>\n" +
                            "         $(function() {\n" +
                            "            var availableTutorials  =  " + donnees.get(FeatureBalise) + "\n" +
                            "            $( \"#" + recupID(map.get(FeatureBalise).get(0)) + "\").autocomplete({\n" +
                            "               source: availableTutorials\n" +
                            "            });\n" +
                            "         });\n" +
                            "      </script>\n";
                }

            } else {
                //Création des balises avec double types
                texte = texte +
                        "                <div class=\"col-sm-12 ui-widget\" id='" + compteur + " uno' style=\"display:inline\" >\n" +
                        "                <label class=\"col-sm-3 col-form-label\" for = \"" + recupID(map.get(FeatureBalise).get(0)) + "\" >" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" +
                        map.get(FeatureBalise).get(0) + "\n" +
                        "                </div>\n" +

                        "           <div class =\"col-sm-3\">\n" +
                        "                    <input type=\"button\" class=\"btn btn-primary ng-binding\" value=\"changer\" onClick=\"afficher_cacher('" + compteur + " uno', '" + compteur + " bis')\">\n" +
                        "                </div>\n" +

                        "                </div>\n" +


                        "                <div class=\"col-sm-12 ui-widget\" id='" + compteur + " bis' style=\"display:none\">\n" +
                        "                <label class=\"col-sm-3 col-form-label\" for = \"" + recupID(map.get(FeatureBalise).get(1)) + "\" >" + FeatureBalise + "</label>\n" +
                        "                <div class=\"col-sm-6\">\n" + map.get(FeatureBalise).get(1) + "\n" +
                        "                </div>\n" +

                        "           <div class =\"col-sm-3\">\n" +
                        "                    <input type=\"button\" class=\"btn btn-primary ng-binding\" value=\"changer\" onClick=\"afficher_cacher('" + compteur + " uno', '" + compteur + " bis')\">\n" +
                        "                </div>\n" +

                        "                </div>\n" +
                        "            <br /><br />\n";

                // Ajout du JS pour l'autocomplétion
                if (!exclude(recupID(map.get(FeatureBalise).get(0)))) {
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

        //Ajout des boutons de fin de pages et du JS de vérification et de changement de type)
        texte = texte +
                "           </div>" +
                "           <div class=\"modal-footer ng-scope\">" +
                "               <button type=\"button\" class=\"btn btn-default ng-binding\" data-dismiss=\"modal\">Annuler</button>" +
                "               <button type=\"button\" class=\"btn btn-success ng-binding\">Cr&eacute;er</button>" +
                "           </div>" +
                "        </form>\n" +
                "        </div>\n" +
                "        </div>\n" +
                "       <script type=\"text/javascript\">\n" +
                "function afficher_cacher(element1 , element2) {\n" +
                "   var obj = document.getElementById(element1);\n" +
                "   var obj2 = document.getElementById(element2);\n" +
                "   console.log(obj);\n" +
                "   console.log(obj2);\n" +
                "   obj.style.display = (obj.style.display == 'none' ? 'inline' : 'none');\n" +
                "   obj2.style.display = (obj2.style.display == 'none' ? 'inline' : 'none');\n" +
                "}\n" +
                "function surligne(champ, erreur)\n" +
                "{\n" +
                "   if(erreur)\n" +
                "       champ.style.backgroundColor = \"#d9534f\";\n" +
                "   else\n" +
                "       champ.style.backgroundColor = \"#5cb85c\";\n" +
                "}\n" +
                "function verifMail(champ)\n" +
                "{\n" +
                "   var regex = /^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$/;\n" +
                "   if(!regex.test(champ.value))\n" +
                "   {\n" +
                "       surligne(champ, true);\n" +
                "       return false;\n" +
                "   }\n" +
                "   else\n" +
                "   {\n" +
                "       surligne(champ, false);\n" +
                "       return true;\n" +
                "   }\n" +
                "}\n" +
                "function verifTexte(champ)\n" +
                "{\n" +
                "   if(champ.value.length >= 0 || champ.value==\"\")\n" +
                "   {\n" +
                "       surligne(champ, false);\n" +
                "       return false;\n" +
                "   }\n" +
                "   else\n" +
                "   {\n" +
                "       surligne(champ, true);\n" +
                "       return true;\n" +
                "   }\n" +
                "}\n" +
                "function verifDate(champ)\n" +
                "{\n" +
                "   var regex = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;\n" +
                "   if(!regex.test(champ.value))\n" +
                "   {\n" +
                "       surligne(champ, true);\n" +
                "       console.log(champ.value);\n" +
                "       return false;\n" +
                "    }\n" +
                "   else\n" +
                "   {\n" +
                "        surligne(champ, false);\n" +
                "        return true;\n" +
                "    }\n" +
                "}\n" +
                "function verifEntier(value){\n" +
                "    console.log(value.value);\n" +
                "    if(value.value == \"\" || !((parseFloat(value.value) == parseInt(value.value)) && !isNaN(value.value))){\n" +
                "        surligne(value, false);\n" +
                "        return true;\n" +
                "    }else {\n" +
                "        surligne(value, false);\n" +
                "        return false;\n" +
                "    }\n" +
                "}\n" +
                "function verifReel(value){\n" +
                "    var regex = /^[0-9]*[.|,]?[0-9]*$/;\n" +
                "    if((!regex.test(value.value))){\n" +
                "        surligne(value, true);\n" +
                "        return true;\n" +
                "    } else {\n" +
                "        surligne(value, false);\n" +
                "        return false;\n" +
                "    }\n" +
                "}\n" +
                "function checkboxF(value){\n" +
                "    console.log(value);\n" +
                "}\n" +
                "</script>\n" +
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



