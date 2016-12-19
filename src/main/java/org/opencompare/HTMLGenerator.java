package org.opencompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BJBPrudor on 20/10/2016.
 */
public class HTMLGenerator
{

    /**
     * Supprime les espaces dans le String en entrée
     * @param aModifier String à modifier
     * @return le String aModifier sans les espaces
     */
    private static String deleteSpace(String aModifier)
    {
        return aModifier.replaceAll(" ", "");
    }

    /**
     * Gènere une Map de balises HTML5 depuis une Map de types en entrée
     * @param features Map indexée par un String representant le nom de la Feature visée et contenant des listes de Types en String
     * @return Map indexée par un String représentant le nom de la Feature visée et contenant des listes de balises HTML5
     */
    public static Map<String,List<String>> GenerateFrom(Map<String,List<String>> features)
    {

        // Map de balises HTML5 indexé par un String (nom de la feature)
        Map<String,List<String>> balises = new HashMap<String,List<String>>();
        int compteur = 0; // compteur du nombre de balises existant

        // on parcours toutes les entrée du Map d'entrée features
        for(Map.Entry<String,List<String>> feat : features.entrySet())
        {

            String name = feat.getKey(); // on recupere la clé (le nom de la feature)
            List<String> cla = feat.getValue(); // on recupere les types associés
            List<String> ba = new ArrayList<String>();
            for (String s : cla) // pour chaque type
            {
                String balise = CreateBaliseFrom(s,name,compteur); // on creer la balise associé au type
                ba.add(balise); // on ajoute la balise a la liste de balise
                compteur++; // on incremente le compteur de balise
            }
            balises.put(name,ba); // on ahoute dans le Map de balise une entrée avec le nom de la Feature et la liste des balises generées

        }
        return balises;

    }

    //region Selection du type de balise

    /**
     * Creer une balise HTML5 avec les paramètres suivants
     * @param c Type de l'objet visé (Correspond a un type d'OpenCompare)
     * @param name Nom de la Feature concernée par la balise
     * @param compteur Numéro unique de la balise
     * @return Un String representant une balise HTML5
     */
    private static String CreateBaliseFrom(String c, String name, int compteur)
    {

        OCType Otype;
        String result = "";
        try
        {
            Otype = OCType.valueOf(c); // on recupere la valeur correspondante a c dans l'Enumerateur OCType
            switch (Otype) // on passe le type dans un switch
            {
                case ValueImpl:
                    result = CreateTextInput(name,compteur);
                    break;
                case BooleanValueImpl:
                    result = CreateCheckInput(name,compteur);
                    break;
                case ConditionalImpl:
                    result = CreateConditionalInput(name,compteur);
                    break;
                case DateValueImpl:
                    result = CreateDateInput(name,compteur);
                    break;
                case DimensionImpl:
                    result = CreateDimensionInput(name,compteur);
                    break;
                case IntegerValueImpl:
                    result = CreateIntegerInput(name,compteur);
                    break;
                case MultipleImpl:
                    result = CreateMultipleInput(name,compteur);
                    break;
                case NotApplicableImpl:
                    result = CreateTextInput(name,compteur);
                    break;
                case NotAvailableImpl:
                    result = CreateTextInput(name,compteur);
                    break;
                case PartialImpl:
                    result = CreatePartialInput(name,compteur);
                    break;
                case RealValueImpl:
                    result = CreateRealInput(name,compteur);
                    break;
                case StringValueImpl:
                    result = CreateTextInput(name,compteur);
                    break;
                case UnitImpl:
                    result = CreateUnitInput(name,compteur);
                    break;
                case VersionImpl:
                    result = CreateVersionInput(name,compteur);
                    break;
            }
        }
        catch (Exception e)
        {
            result = CreateErrorInput(name, compteur);
        }
        return result;
    }

    /**
     * Enumerateur contenant les differents type d'OpenCompare
     */
    public enum OCType
    {
        ValueImpl,
        BooleanValueImpl,
        ConditionalImpl,
        DateValueImpl,
        DimensionImpl,
        IntegerValueImpl,
        MultipleImpl,
        NotApplicableImpl,
        NotAvailableImpl,
        PartialImpl,
        RealValueImpl,
        StringValueImpl,
        UnitImpl,
        VersionImpl,
    };

    //endregion

    //region creation de balise

    /**
     * Creer une balise HTML5 de type input Text
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateTextInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Texte";
        return "<input type='text' class='form-control' id=\"" + id + "\" placeholder=" + plhold + " onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise HTML5 de type input number avec une contrainte d'Entier
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateIntegerInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Entier ex: 1000";
        return "<input type='number' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" min='0' step='1'  onblur='verifEntier(this)'>";
    }

    /**
     * Creer une balise HTML5 de type input number avec une contrainte de Réel
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateRealInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Reel ex:1.52";
        return "<input type='number' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" onblur='verifReel(this)' >";
    }

    /**
     * Creer une balise HTML5 de type input Text avec un Pattern qui verifie si l'entree correspond a un Multiple
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateMultipleInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String p = "^(\\d*,)*(\\d*)";
        String pl = "val1,val2,...,valN";
        return "<input type='text' class='form-control' id=\""+ id + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)' >";
    }

    /**
     * Creer une balise HTML5 de type input Text (avec comme valeur par Defaut : Partial)
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreatePartialInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Partial";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" value='Partial' onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise HTML5 de type input Text avec un Pattern qui verifie si l'entree correspond à un numero de Version
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateVersionInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String p = "\\d\\.\\d\\.\\d";
        String pl = "Version ex : 1.2.11";
        return "<input type='text' class='form-control' id=\""+ id + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise HTML5 de type input Text
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateUnitInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Unité";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise HTML5 de type input Text
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateDimensionInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Dimension ";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise HTML5 de type input Text avec un Pattern qui verifie si l'entrée correspond à un Conditional
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateConditionalInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String p = "$\\(\\w*\\)";
        String pl = "text(text)";
        return "<input type='text' class='form-control' id=\""+ id + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)' >";
    }

    /**
     * Creer une balise HTML5 contenant un ensemble de trois boutons Radio (Yes/No/N.A)
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateCheckInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        return "<label class='col-sm-4 col-form-label'>true\n" +
                "   <input type='radio' name=\"bool"+ id + "\"  id=\""+ id +"\"  value='true'>\n" +
                "</label>\n" +
                "<label class='col-sm-4 col-form-label'>false\n" +
                "   <input type='radio' name=\"bool" + id + "\" id=\""+ id +"\" value=\"false\">\n" +
                "</label>\n" +
                "<label class='col-sm-4 col-form-label'>Sans r&eacute;ponse\n" +
                "   <input type='radio' name=\"bool" + id + "\" id=\""+ id +"\"  value=''>\n" +
                "</label>\n";
    }

    /**
     * Creer une balise HTML5 de type input Date
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateDateInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Date ex: 12/10/2016";
        return "<input type='date' class='form-control' id=\""+ id + "\" placeholder="+ plhold +" onblur='verifDate(this)'>";
    }

    /**
     * Creer une balise d'erreur (n'est pas utilisée)
     * @param name Nom de la feature concernée (sert pour l'id)
     * @param compteur Numero unique de la balise (sert pour l'id)
     * @return un String represenant la balise crée
     */
    public static String CreateErrorInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "error";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" text='error' onblur='verifTexte(this)'>";
    }

    //endregion

}
