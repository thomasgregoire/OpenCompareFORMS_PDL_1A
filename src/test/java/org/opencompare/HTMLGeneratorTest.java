package org.opencompare;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mu-B on 16/12/2016.
 */
public class HTMLGeneratorTest
{

    @Before
    public Map<String,List<String>> PrepareMap()
    {
        Map<String,List<String>> m = new HashMap<String, List<String>>();
        List<String> ls = new ArrayList<String>();
        ls.add("StringValueImpl");
        ls.add("RealImpl");
        m.put("feat1",ls);
        return m;
    }

    @Test
    public void TestGenerateFrom()
    {
        Map<String,List<String>> m = PrepareMap();
        Map<String,List<String>> res = HTMLGenerator.GenerateFrom(m);
        for (Map.Entry<String,List<String>> e: res.entrySet())
        {
            String s = e.getKey();
        }
    }

}
