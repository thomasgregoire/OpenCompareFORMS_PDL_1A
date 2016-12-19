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
    private static String CreateTextInput(String name,int compteur) { return "<input type='text' class='form-control' id=\"" + deleteSpace(name) + compteur + "\" onblur='verifTexte(this)'>"; }

    /**
     * Creer un balise de type number specialisé pour les int
     * @param name
     * @return
     */
    private static String CreateIntegerInput(String name,int compteur){ return "<input type='number' class='form-control' id=\""+ deleteSpace(name) + compteur +"\" min='0' step='1'  onblur='verifEntier(this)'>"; }

    /**
     * Creerr une balise de type number calibré pour les nombres reels
     * @param name
     * @return
     */
    private static String CreateRealInput(String name,int compteur) { return "<input type='text' class='form-control' id=\""+ deleteSpace(name) + compteur +"\" onblur='verifReel(this)' >"; }

    /**
     * Creer une balise de type number calibree pour des multiples ?
     * @param name
     * @return
     */
    private static String CreateMultipleInput(String name,int compteur)
    {
        String p = "^(\\d*,)*(\\d*)";
        String pl = "val1,val2,...,valN";
        return "<input type='text' class='form-control' id=\""+ deleteSpace(name) + compteur + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)' >";
    }

    /**
     * Creer une balise Partial ?
     * @return
     */
    private static String CreatePartialInput(String name,int compteur)
    {
        return "<input type='text' class='form-control' id=\""+ deleteSpace(name) + compteur +"\" value='Partial' onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise de type texte avec un pattern specifique au versions
     * @return
     */
    private static String CreateVersionInput(String name,int compteur)
    {
        String p = "\\d\\.\\d\\.\\d";
        String pl = "X.X.XX";
        return "<input type='text' class='form-control' id=\""+ deleteSpace(name) + compteur + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)'>";
    }

    /**
     * Creer une balise Unit ?
     * @return
     */
    private static String CreateUnitInput(String name,int compteur) { return "<input type='text' class='form-control' id=\""+ deleteSpace(name) + compteur +"\" onblur='verifTexte(this)'>"; }

    /**
     * Creer une balise Dimension ?
     * @return
     */
    private static String CreateDimensionInput(String name,int compteur) { return "<input type='text' class='form-control' id=\""+ deleteSpace(name)+ compteur +"\" onblur='verifTexte(this)'>"; }

    /**
     * Creer une balise Conditionnal ?
     * @return
     */
    private static String CreateConditionalInput(String name,int compteur)
    {
        String p = "$\\(\\w*\\)";
        String pl = "text(text)";
        return "<input type='text' class='form-control' id=\""+ deleteSpace(name) + compteur + "\" pattern="+ p +" placeholder="+ pl +" onblur='verifTexte(this)' >";
    }

    /**
     * Creer une balise checkbox
     * @return
     */
    private static String CreateCheckInput(String name,int compteur)
    {
        return "<label class='col-sm-2 col-form-label'>true\n" +
                "   <input type='radio' id=\""+ deleteSpace(name) + compteur +"\"  value='true'>\n" +
                "</label>\n" +
                "<label class='col-sm-2 col-form-label'>false\n" +
                "   <input type='radio' id=\""+ deleteSpace(name) + compteur +"\" value=\"false\">\n" +
                "</label>\n" +
                "<label class='col-sm-2 col-form-label'>Sans réponse\n" +
                "   <input type='radio' id=\""+ deleteSpace(name) + compteur +"\"  value=''>\n" +
                "</label>\n";
    }

    /**
     * Creer une balise date
     * @return
     */
    private static String CreateDateInput(String name,int compteur) { return "<input type='date' class='form-control' id=\""+ deleteSpace(name) + compteur + "\" onblur='verifDate(this)'>"; }

    /**
     * Creer une balise erreur
     * @return
     */
    private static String CreateErrorInput(String name,int compteur) { return "<input type='text' class='form-control' id=\""+ deleteSpace(name) + compteur +"\" text='error' onblur='verifTexte(this)'>"; }

    //endregion

}
