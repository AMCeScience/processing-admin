package docking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.base.Joiner;

public class TestClass {
	public LinkedHashMap<Object, String> wheres = new LinkedHashMap<Object, String>();
	public String input = "test+something 2014+bla";
	
	@Test 
	public void a() {
		LinkedHashMap<Object, String> tempMap = new LinkedHashMap<Object, String>();
    	ArrayList<String> tempList = new ArrayList<String>();
    	
    	//String[] searchFields = {"p.ProjectName", "p.ProjectDescription", "p.ProjectOwner", "po.ProcessingDate"};
    	String concat = "bla";

    	String[] terms = input.split(" ");
    	
    	for (String term : terms) {
    		System.out.println("normal term: " + term);
    		
    		String[] andTerms = term.split(Pattern.quote("+"));
    		
    		LinkedHashMap<ArrayList<String>, String> andMap = new LinkedHashMap<ArrayList<String>, String>();
    		ArrayList<String> andList = new ArrayList<String>();
    		
    		if (andTerms.length > 1) {
	    		for (String andTerm : andTerms) {
	    			System.out.println("andterm: " + andTerm);
	    			
	    			if (andTerm.length() > 0) {
	    				System.out.println("writing 1");
	    				andList.add(getWhere(concat, "LIKE", "'%" + andTerm + "%'"));
	    			}
	    		}
	    		
	    		if (andList.size() > 0) {
	    			System.out.println("writing 2");
	    			andMap.put(andList, "AND");
	    			tempMap.put(andMap, "OR");
	    		}
	    		
	    		continue;
    		}
    		
    		
			if (term.length() > 0) {
				System.out.println("writing 3");
				tempList.add(getWhere(concat, "LIKE", "'%" + term + "%'"));
				tempMap.put(tempList, "OR");
			}
    	}
    	
        wheres.put(tempMap, "AND");
        
        System.out.println(setWhere(tempMap, true));
	}
	
	public String getWhere(String name, String modifier, String where) {
        return name + " " + modifier + " " + where;
    }

    public String getWhereOr(String a, String b) {
    	return a + " OR " + b;
    }
    
	@SuppressWarnings("unchecked")
	private String setWhere(LinkedHashMap<Object, String> where, boolean first) {
    	String whereSql = "";
    	
        for (Entry<Object, String> entry : where.entrySet()) {
        	if (!first) {
    			whereSql += " " + entry.getValue() + " ";
    		}
        	
        	if (entry.getKey() instanceof LinkedHashMap<?, ?>) {
        		whereSql += "(" + setWhere((LinkedHashMap<Object, String>) entry.getKey(), true) + ")";
        	} else if (entry.getKey() instanceof ArrayList<?>) {
        		Joiner commaJoiner = Joiner.on(" " + entry.getValue() + " ").skipNulls();
        		
        		whereSql += "(" + commaJoiner.join((ArrayList<String>) entry.getKey()) + ")";
        	} else {
        		whereSql += entry.getKey();
        	}
        	
        	first = false;
        }
        
        return whereSql;
    }
}