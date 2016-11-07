package org.opencompare;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mu-B on 20/10/2016.
 */
public class HTMLGenerator
{

    public HashMap<String,String> GenerateFrom(HashMap<String,Class> features)
    {

        // balises au format nom, balise en html
        HashMap<String,String> balises = new HashMap();

        for(Map.Entry<String,Class> feat : features.entrySet())
        {
            balises.put(feat.getKey(),CreateBaliseFrom(feat.getValue(),feat.getKey()));
        }

        return balises;
    }

    //region Selection du type de balise

    public String CreateBaliseFrom(Class c, String name)
    {

        String vType = c.getName();
        OCType Otype;
        String result = "";

        try
        {
            Otype = OCType.valueOf(vType);
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
    public static String CreateTextInput(String name)
    {
        return "<input type='text' name=" + name +">";
    }

    /**
     * Creer un balise de type number specialisé pour les int
     * @param name
     * @return
     */
    public static String CreateIntegerInput(String name){ return "<input type='number' name="+ name +" min='0' step='1'>"; }

    /**
     * Creerr une balise de type number calibré pour les nombres reels
     * @param name
     * @return
     */
    public static String CreateRealInput(String name) { return "<input type='number' name="+ name + ">"; }

    /**
     * Creer une balise de type number calibree pour des multiples ?
     * @param name
     * @return
     */
    public static String CreateMultipleInput(String name) { return "<input type='number' name="+ name +">"; }

    /**
     * Creer une balise Partial ?
     * @return
     */
    public static String CreatePartialInput(String name) { return "<input type='text' name="+ name +">"; }

    /**
     * Creer une balise de type texte avec un pattern specifique au versions
     * @return
     */
    public static String CreateVersionInput(String name) { return "<input type='text' name=" + name + " pattern='\\d\\.\\d\\.\\d'>"; }

    /**
     * Creer une balise Unit ?
     * @return
     */
    public static String CreateUnitInput(String name) { return "<input type='text' name="+ name +">"; }

    /**
     * Creer une balise Dimension ?
     * @return
     */
    public static String CreateDimensionInput(String name) { return "<input type='text' name="+ name +">"; }

    /**
     * Creer une balise Conditionnal ?
     * @return
     */
    public static String CreateConditionalInput(String name) { return "<input type='text' name="+ name +">"; }

    /**
     * Creer une balise checkbox
     * @return
     */
    public static String CreateCheckInput(String name) { return "<input type='checkbox' name="+ name +" value=''>"+name+"<br>"; }

    /**
     * Creer une balise date
     * @return
     */
    public static String CreateDateInput(String name) { return "<input type='date' name="+ name +">"; }

    /**
     * Creer une balise erreur
     * @return
     */
    public static String CreateErrorInput(String name) { return "<input type='text'' name="+name+" text='error'>"; }

    //endregion

}
