package org.opencompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by BJBPrudor on 20/10/2016.
 */
public class HTMLGenerator
{

    private static String deleteSpace(String aModifier)
    {
        return aModifier.replaceAll(" ", "");
    }

    public static Map<String,List<String>> GenerateFrom(Map<String,List<String>> features)
    {

        // balises au format nom, balise en html
        Map<String,List<String>> balises = new HashMap<String,List<String>>();
        int compteur = 0;

        for(Map.Entry<String,List<String>> feat : features.entrySet())
        {

            String name = feat.getKey();
            List<String> cla = feat.getValue();
            List<String> ba = new ArrayList<String>();
            for (String s : cla)
            {
                String balise = CreateBaliseFrom(s,name,compteur);
                ba.add(balise);
                compteur++;
            }
            balises.put(name,ba);

        }

        return balises;

    }

    //region Selection du type de balise

    private static String CreateBaliseFrom(String c, String name, int compteur)
    {

        OCType Otype;
        String result = "";
        try
        {
            Otype = OCType.valueOf(c);
            switch (Otype)
            {
                case ValueImpl:
                    result = CreateErrorInput(name,compteur);
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
                    result = CreateErrorInput(name,compteur);
                    break;
                case NotAvailableImpl:
                    result = CreateErrorInput(name,compteur);
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
     * Creer un balise de type texte
     * @param name
     * @return
     */
    public static String CreateTextInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Texte";
        return "<input type='text' class='form-control' id=\"" + id + "\" placeholder=" + plhold + " onblur='verifTexte(this)'>";
    }

    /**
     * Creer un balise de type number specialisé pour les int
     * @param name
     * @return
     */
    public static String CreateIntegerInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Entier ex: 1000";
        return "<input type='number' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" min='0' step='1'  onblur='verifEntier(this)'>";
    }

    /**
     * Creerr une balise de type number calibré pour les nombres reels
     * @param name
     * @return
     */
    public static String CreateRealInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Reel ex:1.52";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" onblur='verifReel(this)' >";
    }

    /**
     * Creer une balise de type number calibree pour des multiples ?
     * @param name
     * @return
     */
    public static String CreateMultipleInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String p = "^(\\d*,)*(\\d*)";
        String pl = "val1,val2,...,valN";
        return "<input type='text' class='form-control' id=\""+ id + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)' >";
    }

    /**
     * Creer une balise Partial ?
     * @return
     */
    public static String CreatePartialInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Partial";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" value='Partial' onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise de type texte avec un pattern specifique au versions
     * @return
     */
    public static String CreateVersionInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String p = "\\d\\.\\d\\.\\d";
        String pl = "Version ex : 1.2.11";
        return "<input type='text' class='form-control' id=\""+ id + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise Unit ?
     * @return
     */
    public static String CreateUnitInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Unité";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise Dimension ?
     * @return
     */
    public static String CreateDimensionInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Dimension ";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise Conditionnal ?
     * @return
     */
    public static String CreateConditionalInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String p = "$\\(\\w*\\)";
        String pl = "text(text)";
        return "<input type='text' class='form-control' id=\""+ id + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)' >";
    }

    /**
     * Creer une balise checkbox
     * @return
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
                "<label class='col-sm-4 col-form-label'>Sans réponse\n" +
                "   <input type='radio' name=\"bool" + id + "\" id=\""+ id +"\"  value=''>\n" +
                "</label>\n";
    }

    /**
     * Creer une balise date
     * @return
     */
    public static String CreateDateInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "Date ex: 12/10/2016";
        return "<input type='date' class='form-control' id=\""+ id + "\" placeholder="+ plhold +" onblur='verifDate(this)'>";
    }

    /**
     * Creer une balise erreur
     * @return
     */
    public static String CreateErrorInput(String name,int compteur)
    {
        String id = deleteSpace(name) + compteur;
        String plhold = "error";
        return "<input type='text' class='form-control' id=\""+ id +"\" placeholder="+ plhold +" text='error' onblur='verifTexte(this)'>";
    }

    //endregion

}
