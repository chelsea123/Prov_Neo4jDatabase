package com.prov.neo4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CreateGraph {
	private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data/";

    public static void main(String[] args) throws URISyntaxException{
		CreateGraph.checkDatabaseIsRunning();
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
    
    
    public static void addMetadataToProperty( URI relationshipUri,
            Map<String,String> property) throws URISyntaxException
    {
        URI propertyUri = new URI( relationshipUri.toString() + "/properties" );
        String entity = toJsonNameValuePairCollection( property );
        WebResource resource = Client.create().resource( propertyUri );
        ClientResponse response = resource.accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity( entity )
                .put( ClientResponse.class );

        System.out.println( String.format(
                "PUT [%s] to [%s], status code [%d]", entity, propertyUri,
                response.getStatus() ) );
        response.close();
    }

    private static String toJsonNameValuePairCollection(Map<String,String> property){

		Set<String> keys=property.keySet();
		Iterator<String> iter=keys.iterator();
		String outPut="{ ";
		while(iter.hasNext()){
			String key=iter.next();
			String value=property.get(key);

			outPut+="\""+key+"\" : \""+value+"\"";
			if(iter.hasNext()){
				outPut+=", ";
			}
		}
		outPut+="}";

		return outPut;

	}

    public static URI createNode()
    {
        final String nodeUri = SERVER_ROOT_URI + "node";
        WebResource resource = Client.create().resource( nodeUri );
        ClientResponse response = resource.accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity( "{}" )
                .post( ClientResponse.class );

        final URI location = response.getLocation();
        
        System.out.println( String.format("POST to [%s], status code [%d], location header [%s]",
                nodeUri, response.getStatus(), location.toString() ) );
        response.close();
        return location;
    }
    
    
    public static void addNodeToIndex( String key, String value, URI nodeUri){
    	final String indexNodeUri = SERVER_ROOT_URI + "index/node/index" ;
    	WebResource resource = Client.create().resource(indexNodeUri);
    	String indexNodeJson = "{\"key\" : \"" + key + "\", \"value\" : \"" + value + "\", \"uri\" : \"" + nodeUri + "\" }";

    	ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON).entity(indexNodeJson)
			.post(ClientResponse.class);

        System.out.println( String.format("POST [%s] to [%s], status code [%d], returned data: " 
        + System.getProperty( "line.separator" ) + "%s", indexNodeJson, indexNodeUri, response.getStatus(),
		response.getEntity( String.class ) ));
        response.close();
   }
    

    public static URI addRelationship( URI startNode, URI endNode,
            String relationshipType)
            throws URISyntaxException
    {   	
        URI fromUri = new URI( startNode.toString() + "/relationships" );
        String relationshipJson = generateJsonRelationship( endNode,
                relationshipType);

        WebResource resource = Client.create()
                .resource( fromUri );
        ClientResponse response = resource.accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity( relationshipJson )
                .post( ClientResponse.class );

        final URI location = response.getLocation();
        System.out.println( String.format(
                "POST to [%s], status code [%d], location header [%s]",
                fromUri, response.getStatus(), location.toString() ) );

        response.close();
        return location;
    }


    public static String generateJsonRelationship( URI endNode, String relationshipType, String... jsonAttributes )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "{ \"to\" : \"" );
        sb.append( endNode.toString() );
        sb.append( "\", " );

        sb.append( "\"type\" : \"" );
        sb.append( relationshipType );
        if ( jsonAttributes == null || jsonAttributes.length < 1 )
        {
            sb.append( "\"" );
        }
        else
        {
            sb.append( "\", \"data\" : " );
            for ( int i = 0; i < jsonAttributes.length; i++ )
            {
                sb.append( jsonAttributes[i] );
                if ( i < jsonAttributes.length - 1 )
                {
                    sb.append( ", " );
                }
            }
        }

        sb.append( " }" );
        return sb.toString();
    }
    

    public static void addProperty( URI nodeUri, String propertyName,
            String string )
    {
    	//System.out.println("propertyname              	"+propertyName);
        String propertyUri = nodeUri.toString() + "/properties/" + propertyName;
        WebResource resource = Client.create().resource( propertyUri );
        ClientResponse response = resource
        		.accept( MediaType.APPLICATION_JSON )
        		.type( MediaType.APPLICATION_JSON )
        		.entity( "\"" + string + "\"" )
        		.put( ClientResponse.class );
        System.out.println( String.format( "PUT to [%s], status code [%d]", propertyUri, response.getStatus() ) );
        response.close();
    }
    
    
    public static String getProperty( URI nodeUri, String propertyName)
    {
    	 String propertyUri = nodeUri.toString() + "/properties/" + propertyName;
    	 WebResource resource = Client.create().resource( propertyUri );
    	 String response = resource.get(String.class);
    	 return response;
    	
    	
    }
    

    public static void checkDatabaseIsRunning()
    {
        WebResource resource = Client.create().resource( SERVER_ROOT_URI );
        ClientResponse response = resource.get( ClientResponse.class );

        System.out.println( String.format( "GET on [%s], status code [%d]", SERVER_ROOT_URI, response.getStatus() ) );
        response.close();
    }
    
    
    public static void checkStartNode(URI startNode){
    	QName id=null;
    	String id1 = "" + id + "";
    	try
		{
			URI startUri = new URI( startNode.toString());
			if(startUri.toString().isEmpty()){
				URI firstNode = CreateGraph.createNode();
				CreateGraph.addNodeToIndex("id", id1, firstNode);
				CreateGraph.addProperty(firstNode, "type", " ");
				CreateGraph.addProperty(firstNode, "id", id1);
			}
		}
		catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public static void checkEndNode(URI endNode){
    	QName id = null;
    	String id1 = "" + id + "";
    	//URI endUri = new URI( endNode.toString());
		if(endNode.toString()==null){
			URI firstNode = CreateGraph.createNode();
			CreateGraph.addNodeToIndex("id", id1, firstNode);
			CreateGraph.addProperty(firstNode, "type", " ");
			CreateGraph.addProperty(firstNode, "id", id1);
		}
    }



}
