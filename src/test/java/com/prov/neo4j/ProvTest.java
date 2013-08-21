package com.prov.neo4j;

import junit.framework.TestCase;

import com.prov.neo4j.Neo4jUtilities;

public class ProvTest extends TestCase{

	public void testNeo4jUpload() throws java.lang.Throwable {
		
		Neo4jUtilities n = new Neo4jUtilities();
    	n.provnToNeo4j("F:\\workspace\\Prov_Neo4jDatabase\\src\\test\\resources\\prov\\testCase3.provn");
    	
    }
}
