package org.opencompare;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by BJBPrudor on 16/12/2016.
 */
public class HTMLGeneratorTest
{

    String f1,f2,f3,f4,f5; //features
    String t1,t2,t3,t4,t5; //types
    String b1,b2,b3,b4,b5; //balises
    Map<String,List<String>> map;

    @Before
    public void PrepareFeats()
    {
        f1 = "feata";
        f2 = "featb";
        f3 = "featc";
        f4 = "featd";
        f5 = "feate";
    }

    @Before
    public void PrepareType()
    {
        t1 = HTMLGenerator.OCType.StringValueImpl.name();
        t2 = HTMLGenerator.OCType.IntegerValueImpl.name();
        t3 = HTMLGenerator.OCType.BooleanValueImpl.name();
        t4 = HTMLGenerator.OCType.MultipleImpl.name();
        t5 = HTMLGenerator.OCType.ConditionalImpl.name();
    }

    @Before
    public void PrepareBalises()
    {
        b1 = HTMLGenerator.CreateTextInput("$f",-1);
        b2 = HTMLGenerator.CreateIntegerInput("$f",-1);
        b3 = HTMLGenerator.CreateCheckInput("$f",-1);
        b4 = HTMLGenerator.CreateMultipleInput("$f",-1);
        b5 = HTMLGenerator.CreateConditionalInput("$f",-1);
    }

    public void PrepareMap()
    {
        map = new HashMap<String, List<String>>();
        List<String> ls = new ArrayList<String>();

        ls.add(t1); ls.add(t3); map.put(f1,ls);

        ls = new ArrayList<String>();
        ls.add(t2); ls.add(t4); map.put(f2,ls);

        ls = new ArrayList<String>();
        ls.add(t4); ls.add(t5); map.put(f3,ls);

        ls = new ArrayList<String>();
        ls.add(t1); ls.add(t5); map.put(f4,ls);

        ls = new ArrayList<String>();
        ls.add(t2); ls.add(t3); map.put(f5,ls);


    }

    @Test
    public void TestGenerateFrom()
    {
        PrepareMap();
        Map<String,List<String>> res = HTMLGenerator.GenerateFrom(map);

        Object[] nf1;
        Object[] nf2;
        nf1 = res.keySet().toArray();
        nf2 = map.keySet().toArray();
        Assert.assertArrayEquals(nf1,nf2);

        List<String> l;

        l = res.get(f1);
        Assert.assertEquals(l.get(0),b1.replace("$f",f1).replace("-1","0"));
        Assert.assertEquals(l.get(1),b3.replace("$f",f1).replace("-1","1"));

        l = res.get(f2);
        Assert.assertEquals(l.get(0),b2.replace("$f",f2).replace("-1","2"));
        Assert.assertEquals(l.get(1),b4.replace("$f",f2).replace("-1","3"));

        l = res.get(f3);
        Assert.assertEquals(l.get(0),b4.replace("$f",f3).replace("-1","4"));
        Assert.assertEquals(l.get(1),b5.replace("$f",f3).replace("-1","5"));

        l = res.get(f4);
        Assert.assertEquals(l.get(0),b1.replace("$f",f4).replace("-1","6"));
        Assert.assertEquals(l.get(1),b5.replace("$f",f4).replace("-1","7"));

        l = res.get(f5);
        Assert.assertEquals(l.get(0),b2.replace("$f",f5).replace("-1","8"));
        Assert.assertEquals(l.get(1),b3.replace("$f",f5).replace("-1","9"));


    }



}
