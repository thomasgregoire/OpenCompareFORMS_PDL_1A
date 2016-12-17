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

    public static Map<String,List<String>> GenerateFrom(Map<String,List<String>> features)
    {

        // balises au format nom, balise en html
        Map<String,List<String>> balises = new HashMap<String,List<String>>();

        for(Map.Entry<String,List<String>> feat : features.entrySet())
        {
            String name = feat.getKey();
            List<String> cla = feat.getValue();
            List<String> ba = new ArrayList<String>();
            for (String s : cla)
            {
                String balise = CreateBaliseFrom(s,name);
                ba.add(balise);
            }
            balises.put(name,ba);
        }

        return balises;
    }

    //region Selection du type de balise

    private static String CreateBaliseFrom(String c, String name)
    {

        OCType Otype;
        String result = "";
        try
        {
            Otype = OCType.valueOf(c);
            switch (Otype)
            {
                case ValueImpl:
                    result = CreateErrorInput(name);
                    break;
                case BooleanValueImpl:
                    result = CreateCheckInput(name);
                    break;
                case ConditionalImpl:
                    result = CreateConditionalInput(name);
                    break;
                case DateValueImpl:
                    result = CreateDateInput(name);
                    break;
                case DimensionImpl:
                    result = CreateDimensionInput(name);
                    break;
                case IntegerValueImpl:
                    result = CreateIntegerInput(name);
                    break;
                case MultipleImpl:
                    result = CreateMultipleInput(name);
                    break;
                case NotApplicableImpl:
                    result = CreateErrorInput(name);
                    break;
                case NotAvailableImpl:
                    result = CreateErrorInput(name);
                    break;
                case PartialImpl:
                    result = CreatePartialInput(name);
                    break;
                case RealValueImpl:
                    result = CreateRealInput(name);
                    break;
                case StringValueImpl:
                    result = CreateTextInput(name);
                    break;
                case UnitImpl:
                    result = CreateUnitInput(name);
                    break;
                case VersionImpl:
                    result = CreateVersionInput(name);
                    break;
            }
        }
        catch (Exception e)
        {
            result = CreateErrorInput(name);
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
    private static String CreateTextInput(String name) { return "<input type='text' class='form-control' name=" + name + ">"; }

    /**
     * Creer un balise de type number specialisé pour les int
     * @param name
     * @return
     */
    private static String CreateIntegerInput(String name){ return "<input type='number' class='form-control' name="+ name +" min='0' step='1'  onblur='verifEntier(this)'>"; }

    /**
     * Creerr une balise de type number calibré pour les nombres reels
     * @param name
     * @return
     */
    private static String CreateRealInput(String name) { return "<input type='text' class='form-control' name="+ name + " onblur='verifReel(this)' >"; }

    /**
     * Creer une balise de type number calibree pour des multiples ?
     * @param name
     * @return
     */
    private static String CreateMultipleInput(String name)
    {
        String p = "^(\\d*,)*(\\d*)";
        String pl = "val1,val2,...,valN";
        return "<input type='text' class='form-control' name="+ name + " pattern="+ p +" placeholder="+ pl +" >";
    }

    /**
     * Creer une balise Partial ?
     * @return
     */
    private static String CreatePartialInput(String name)
    {
        return "<input type='text' class='form-control' name="+ name +" value='Partial'>";
    }

    /**
     * Creer une balise de type texte avec un pattern specifique au versions
     * @return
     */
    private static String CreateVersionInput(String name)
    {
        String p = "\\d\\.\\d\\.\\d";
        String pl = "X.X.XX";
        return "<input type='text' class='form-control' name="+ name + " pattern="+ p +" placeholder="+ pl +" >";
    }

    /**
     * Creer une balise Unit ?
     * @return
     */
    private static String CreateUnitInput(String name) { return "<input type='text' class='form-control' name="+ name +">"; }

    /**
     * Creer une balise Dimension ?
     * @return
     */
    private static String CreateDimensionInput(String name) { return "<input type='text' class='form-control' name="+ name +">"; }

    /**
     * Creer une balise Conditionnal ?
     * @return
     */
    private static String CreateConditionalInput(String name)
    {
        String p = "$\\(\\w*\\)";
        String pl = "text(text)";
        return "<input type='text' class='form-control' name="+ name + " pattern="+ p +" placeholder="+ pl +" >";
    }

    /**
     * Creer une balise checkbox
     * @return
     */
    private static String CreateCheckInput(String name)
    {
        return "<label class='col-sm-2 col-form-label'>true\n" + "   <input type='radio' name="+ name +"  value='true'>\n" +
                "</label>\n" + "<label class='col-sm-2 col-form-label'>false\n" +
                "   <input type='radio' name="+ name +" value=\"false\">\n" + "</label>\n" +
                "<label class='col-sm-2 col-form-label'>Sans réponse\n" + "   <input type='radio' name="+ name +"  value=''>\n" +
                "</label>\n";
    }

    /**
     * Creer une balise date
     * @return
     */
    private static String CreateDateInput(String name) { return "<input type='date' class='form-control' name="+ name + " onblur='verifDate(this)'>"; }

    /**
     * Creer une balise erreur
     * @return
     */
    private static String CreateErrorInput(String name) { return "<input type='text' class='form-control' name="+ name +" text='error'>"; }

    //endregion

}
