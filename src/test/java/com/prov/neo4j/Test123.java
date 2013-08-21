package com.prov.neo4j;

import java.io.IOException;

public class Test123 {

	public static void main (String [] args) throws IOException, Throwable
	{
		Neo4jUtilities n = new Neo4jUtilities();
		n.provnToNeo4j("F:\\workspace\\Prov_Neo4jDatabase\\src\\test\\resources\\prov\\testCase3.provn");
	}
}
