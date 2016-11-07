package org.opencompare;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BJBPrudor on 20/10/2016.
 */
public class HTMLGenerator
{

    public static Map<String,String> GenerateFrom(Map<String,String> features)
    {

        // balises au format nom, balise en html
        Map<String,String> balises = new HashMap();

        for(Map.Entry<String,String> feat : features.entrySet())
        {
            String name = feat.getKey();
            String cla = feat.getValue();
            String balise = CreateBaliseFrom(cla,name);
            balises.put(name,balise);
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

    private enum OCType
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
    private static String CreateTextInput(String name) { return "<input type='text' class='balise' name=" + name + ">"; }

    /**
     * Creer un balise de type number specialisé pour les int
     * @param name
     * @return
     */
    private static String CreateIntegerInput(String name){ return "<input type='number' class='balise' name="+ name +" min='0' step='1'>"; }

    /**
     * Creerr une balise de type number calibré pour les nombres reels
     * @param name
     * @return
     */
    private static String CreateRealInput(String name) { return "<input type='number' class='balise' name="+ name + ">"; }

    /**
     * Creer une balise de type number calibree pour des multiples ?
     * @param name
     * @return
     */
    private static String CreateMultipleInput(String name) { return "<input type='number' class='balise' name="+ name +">"; }

    /**
     * Creer une balise Partial ?
     * @return
     */
    private static String CreatePartialInput(String name) { return "<input type='text' class='balise' name="+ name +">"; }

    /**
     * Creer une balise de type texte avec un pattern specifique au versions
     * @return
     */
    private static String CreateVersionInput(String name) { return "<input type='text' class='balise' name=" + name + " pattern='\\d\\.\\d\\.\\d'>"; }

    /**
     * Creer une balise Unit ?
     * @return
     */
    private static String CreateUnitInput(String name) { return "<input type='text' class='balise' name="+ name +">"; }

    /**
     * Creer une balise Dimension ?
     * @return
     */
    private static String CreateDimensionInput(String name) { return "<input type='text' class='balise' name="+ name +">"; }

    /**
     * Creer une balise Conditionnal ?
     * @return
     */
    private static String CreateConditionalInput(String name) { return "<input type='text' class='balise' name="+ name +">"; }

    /**
     * Creer une balise checkbox
     * @return
     */
    private static String CreateCheckInput(String name) { return "<input type='checkbox' class='balise' name="+ name +" value=''>"+ name +"<br>"; }

    /**
     * Creer une balise date
     * @return
     */
    private static String CreateDateInput(String name) { return "<input type='date' class='balise' name="+ name +">"; }

    /**
     * Creer une balise erreur
     * @return
     */
    private static String CreateErrorInput(String name) { return "<input type='text' class='balise' name="+ name +" text='error'>"; }

    //endregion

}
