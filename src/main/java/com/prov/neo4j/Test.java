package com.prov.neo4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import java.util.Map;


public class Test {

	public static void main(String[] args) throws URISyntaxException{
		CreateGraph.checkDatabaseIsRunning();
		ProvTest provTest = new ProvTest();
		try {
			provTest.testNeo4jUpload();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ProvConstructor pConstructor = new ProvConstructor();
        
		//Collection<Attribute> attrs;
		//pConstructor.newActivity("http://localhost:7474/db/data/node/1", 201306, 201307, attrs);

        URI firstActivity = CreateGraph.createNode();
        CreateGraph.addNodeToIndex( "id", "1", firstActivity);
        CreateGraph.addProperty(firstActivity, "type", "activity");
        CreateGraph.addProperty(firstActivity, "id", "1");
        CreateGraph.addProperty(firstActivity, "starttime", "201306");
        CreateGraph.addProperty(firstActivity, "endtime", "201307");
        
        URI secondActivity = CreateGraph.createNode();
        CreateGraph.addNodeToIndex( "id", "2", secondActivity);
        CreateGraph.addProperty(secondActivity, "type", "activity");
        CreateGraph.addProperty(secondActivity, "id", "2");
        CreateGraph.addProperty(secondActivity, "starttime", "20130620");
        CreateGraph.addProperty(secondActivity, "endtime", "20130701");
        
        URI firstEntity = CreateGraph.createNode();
        CreateGraph.addNodeToIndex( "id", "1", firstEntity);
        CreateGraph.addProperty(firstEntity, "type", "entity"); 
        CreateGraph.addProperty(firstEntity, "id", "1");

        URI firstAgent = CreateGraph.createNode();
        CreateGraph.addNodeToIndex( "id", "1", firstAgent);
        CreateGraph.addProperty(firstAgent, "type", "agent"); 
        CreateGraph.addProperty(firstAgent, "id", "1");
        
        URI relationshipUri1 = CreateGraph.addRelationship( firstActivity, firstEntity, "used");
        URI relationshipUri2 = CreateGraph.addRelationship( firstEntity, firstActivity, "WasGeneratedBy");
        URI relationshipUri3 = CreateGraph.addRelationship( firstActivity, firstActivity, "WasDrivedForm");
        URI relationshipUri4 = CreateGraph.addRelationship( firstActivity, firstActivity, "WasInformedBy");
        URI relationshipUri5 = CreateGraph.addRelationship( firstEntity, firstAgent, "WasAttributedTo");
        URI relationshipUri6 = CreateGraph.addRelationship( firstAgent, firstAgent, "ActedonHalfOf");
        URI relationshipUri7 = CreateGraph.addRelationship( firstActivity, firstAgent, "WasAssoiatedWith");
        
        Map<String,String> property=new HashMap<String,String>();

		//property.put("timestamp", "201306");
		//property.put("title", "table");

		CreateGraph.addMetadataToProperty(relationshipUri1,property);
		CreateGraph.addMetadataToProperty(relationshipUri2,property);
		CreateGraph.addMetadataToProperty(relationshipUri3,property);
		CreateGraph.addMetadataToProperty(relationshipUri4,property);
		CreateGraph.addMetadataToProperty(relationshipUri5,property);
		CreateGraph.addMetadataToProperty(relationshipUri6,property);
		CreateGraph.addMetadataToProperty(relationshipUri7,property);
		//Map<String,String> property=new HashMap<String,String>();
		//CreateGraph.addMetadataToProperty(relationshipUri,property);
		
	}

}
