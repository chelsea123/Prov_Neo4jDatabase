package com.prov.neo4j;

import javax.ws.rs.core.MediaType;

//import org.neo4j.cypher.javacompat.ExecutionEngine;
//import org.neo4j.cypher.javacompat.ExecutionResult;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Node;
//import org.neo4j.graphdb.Transaction;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class QueryDatabase
{

	public static void main(String[] args)
	{
		final String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
		//GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase( SERVER_ROOT_URI );
		//Transaction tx = db.beginTx();
		//try
		//{
		    //Node myNode = db.createNode();
		    //myNode.setProperty( "name", "my node" );
		    //tx.success();
		//}
		//finally
		//{
		    //tx.finish();
		//}
		//ExecutionEngine engine = new ExecutionEngine( db );
		//ExecutionResult result = engine.execute( "start n=node(*) return n" );
		//System.out.println(result);
		try {
			
			final String nodeUri = SERVER_ROOT_URI + "node/1358/";		 
			Client client = Client.create();
	 
			WebResource webResource = client.resource(nodeUri);
	 
			ClientResponse response = webResource
					.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
	 
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			
			String output = response.getEntity(String.class);
	 
			//System.out.println("Output from Server .... \n");
			System.out.println(output);
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
	 
	}

}
