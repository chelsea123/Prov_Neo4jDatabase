package com.prov.neo4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.openprovenance.prov.xml.ActedOnBehalfOf;
import org.openprovenance.prov.xml.Activity;
import org.openprovenance.prov.xml.Agent;
import org.openprovenance.prov.xml.AlternateOf;
import org.openprovenance.prov.xml.Attribute;
import org.openprovenance.prov.xml.DerivedByInsertionFrom;
import org.openprovenance.prov.xml.DerivedByRemovalFrom;
import org.openprovenance.prov.xml.DictionaryMembership;
import org.openprovenance.prov.xml.Document;
import org.openprovenance.prov.xml.Entity;
import org.openprovenance.prov.xml.HadMember;
import org.openprovenance.prov.xml.KeyQNamePair;
import org.openprovenance.prov.xml.MentionOf;
import org.openprovenance.prov.xml.ModelConstructor;
import org.openprovenance.prov.xml.NamedBundle;
import org.openprovenance.prov.xml.SpecializationOf;
import org.openprovenance.prov.xml.Statement;
import org.openprovenance.prov.xml.Used;
import org.openprovenance.prov.xml.WasAssociatedWith;
import org.openprovenance.prov.xml.WasAttributedTo;
import org.openprovenance.prov.xml.WasDerivedFrom;
import org.openprovenance.prov.xml.WasEndedBy;
import org.openprovenance.prov.xml.WasGeneratedBy;
import org.openprovenance.prov.xml.WasInfluencedBy;
import org.openprovenance.prov.xml.WasInformedBy;
import org.openprovenance.prov.xml.WasInvalidatedBy;
import org.openprovenance.prov.xml.WasStartedBy;

public class ProvConstructor implements ModelConstructor
{
	private Map<String, URI> nodeMap;
	
	public ProvConstructor()
	{
		nodeMap = new HashMap<String, URI>();
	}

	public Object optional(Object str)
	{
		return ((str == null) ? "nil" : str);
	}

	public ActedOnBehalfOf newActedOnBehalfOf(QName id, QName ag2, QName ag1,
			QName a, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String agent2 = "" + ag2 + "";
		String agent1 = "" + ag1 + "";
		String activity = "" + a + "";		 
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("agent2", agent2);
		property.put("agent1", agent1);
		property.put("activity", activity);
		property.put("type", "actedOnBehalfOf");
		String attributes = "" + attrs + "";
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for(k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					break;
				}
			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(agent2), nodeMap.get(agent1),
					"actedOnBehalfOf");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	public Activity newActivity(QName id, XMLGregorianCalendar st,
			XMLGregorianCalendar et, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String startTime = "" + optional(st) + "";
		String endTime = "" + optional(et) + "";
		URI firstNode = CreateGraph.createNode();
		CreateGraph.addNodeToIndex("id", id1, firstNode);
		CreateGraph.addProperty(firstNode, "type", "activity");
		CreateGraph.addProperty(firstNode, "id", id1);
		CreateGraph.addProperty(firstNode, "starttime", startTime);
		CreateGraph.addProperty(firstNode, "endtime", endTime);
		String attributes = "" + attrs + "";
		nodeMap.put(id1, firstNode);
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					CreateGraph.addProperty(firstNode, v1, v2);
					v1 = null;
				}
				catch (Exception e)
				{
					break;
				}

			}
			break;
		}
		return null;
	}
	

	public Agent newAgent(QName id, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		URI firstNode = CreateGraph.createNode();
		CreateGraph.addNodeToIndex("id", id1, firstNode);
		CreateGraph.addProperty(firstNode, "type", "agent");
		CreateGraph.addProperty(firstNode, "id", id1);
		String attributes = "" + attrs + "";
		//String v = attributes.replaceAll("\\:", "\\_");
		nodeMap.put(id1, firstNode);
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					CreateGraph.addProperty(firstNode, v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					break;
				}

			}
			break;
		}
		return null;
	}

	public AlternateOf newAlternateOf(QName alt1, QName alt2)
	{
		String alternate2 = "" + alt2 + "";
		String alternate1 = "" + alt1 + "";
		Map<String, String> property = new HashMap<String, String>();
		property.put("alternate2", alternate2);
		property.put("alternate1", alternate1);
		property.put("type", "alternateOf");
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(alternate2),
					nodeMap.get(alternate1), "alternateOf");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	public Document newDocument(Hashtable<String, String> arg0,
			Collection<Statement> arg1, Collection<NamedBundle> arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Entity newEntity(QName id, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String attributes = "" + attrs + "";
		URI firstNode = CreateGraph.createNode();
		CreateGraph.addNodeToIndex("id", id1, firstNode);
		CreateGraph.addProperty(firstNode, "type", "entity");
		CreateGraph.addProperty(firstNode, "id", id1);	
		String v = attributes.replaceAll("\\:", "\\_");
		nodeMap.put(id1, firstNode);
		while (!v.isEmpty())
		{
			String[] array = v.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					CreateGraph.addProperty(firstNode, v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					break;
				}
			}
			break;

		}
		return null;
	}

	public HadMember newHadMember(QName c, Collection<QName> e)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public MentionOf newMentionOf(QName e2, QName e1, QName b)
	{
		String entity2 = "" + e2 + "";
		String entity1 = "" + e1 + "";
		String u = "" + b + "";
		Map<String, String> property = new HashMap<String, String>();
		property.put("entity2", entity2);
		property.put("entity1", entity1);
		property.put("b", u);
		property.put("type", "mentionOf");
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(entity2), nodeMap.get(u),
					"mentionOf");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public NamedBundle newNamedBundle(QName arg0,
			Hashtable<String, String> arg1, Collection<Statement> arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public SpecializationOf newSpecializationOf(QName infra, QName supra)
	{
		String specificEntity = "" + infra + "";
		String generalEntity = "" + supra + "";
		Map<String, String> property = new HashMap<String, String>();
		property.put("specificEntity", specificEntity);
		property.put("generalEntity", generalEntity);
		property.put("type", "speciallizationOf");
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(specificEntity),
					nodeMap.get(generalEntity), "speciallizationOf");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public Used newUsed(QName id, QName a, QName en, XMLGregorianCalendar t,
			Collection<Attribute> attrs)
	{
		
		String id1 = "" + id + "";
		String time = "" + t + "";
		String activity = "" + a + "";
		String entity = "" + en + "";
		String attributes = "" + attrs + "";
		while (id1 != null)
		{
			//URI firstNode = CreateGraph.createNode();
			//CreateGraph.addProperty(firstNode, "type", "used");
			//CreateGraph.addProperty(firstNode, "id", id1);
			//CreateGraph.addProperty(firstNode, "activity", activity);
			//CreateGraph.addProperty(firstNode, "entity", entity);
			//CreateGraph.addProperty(firstNode, "time", time);
			//nodeMap.put(id1, firstNode);
			break;
		}
		
		if(activity.isEmpty()){
			URI firstNode = CreateGraph.createNode();
			CreateGraph.addNodeToIndex("id", " ", firstNode);
			CreateGraph.addProperty(firstNode, "type", "activity");
			activity = "activity";
			
		}
		
		else if(entity.isEmpty()){
			URI firstNode = CreateGraph.createNode();
			CreateGraph.addNodeToIndex("id", " ", firstNode);
			CreateGraph.addProperty(firstNode, "type", "entity");
			entity = "entity";
		}
		
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("time", time);
		property.put("activity", activity);
		property.put("entity", entity);
		property.put("type", "used");		
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (Exception e)
				{
					break;
				}

			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(activity), nodeMap.get(entity),
					"used");	
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	public WasAssociatedWith newWasAssociatedWith(QName id, QName a, QName ag,
			QName pl, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String publicationPolicy = "" + pl + "";
		String activity = "" + a + "";
		String agent = "" + ag + "";
		String attributes = "" + attrs + "";
		while (id1 != null)
		{
			break;
		}
		
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("activity", activity);
		property.put("agent", agent);
		property.put("publicationPolicy", publicationPolicy);
		property.put("type", "wasAssociatedWith");		
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (Exception e)
				{
					break;
				}

			}
			break;
		}

		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(activity), nodeMap.get(agent),
					"wasAssociatedWith");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public WasAttributedTo newWasAttributedTo(QName id, QName en, QName ag,
			Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String entity = "" + en + "";
		String agent = "" + ag + "";
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("entity", entity);
		property.put("agent", agent);
		property.put("type", "wasAttributedTo");
		String attributes = "" + attrs + "";
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("=|,");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (Exception e)
				{
					break;
				}
			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(entity), nodeMap.get(agent),
					"wasAttributedTo");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public WasDerivedFrom newWasDerivedFrom(QName id, QName e2, QName e1,
			QName a, QName g2, QName u1, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String entity2 = "" + e2 + "";
		String entity1 = "" + e1 + "";
		String activity = "" + a + "";
		String generation = "" + g2 + "";
		String usage = "" + u1 + "";
		
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("entity2", entity2);
		property.put("entity1", entity1);
		property.put("activity", activity);
		property.put("generation", generation);
		property.put("usage", usage);
		property.put("type", "wasDerivedFrom");
		String attributes = "" + attrs + "";
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (Exception e)
				{
					break;
				}

			}
			break;

		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(entity2), nodeMap.get(entity1),
					"wasDerivedFrom");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	public WasEndedBy newWasEndedBy(QName id, QName a2, QName en, QName a1,
			XMLGregorianCalendar t, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String time = "" + t + "";
		String activity2 = "" + a2 + "";
		String entity = "" + en + "";
		String activity1 = "" + a1 + "";
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("time", time);
		property.put("activity2", activity2);
		property.put("entity", entity);		
		property.put("activity1", activity1);
		property.put("type", "wasEndedBy");
		String attributes = "" + attrs + "";
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (Exception e)
				{
					break;
				}

			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(activity2), nodeMap.get(entity),
					"wasEndedBy");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public WasGeneratedBy newWasGeneratedBy(QName id, QName en, QName a,
			XMLGregorianCalendar t, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String time = "" + t + "";
		String entity = "" + en + "";
		String activity = "" + a + "";

		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("time", time);
		property.put("entity", entity);
		property.put("activity", activity);
		property.put("type", "wasGeneratedBy");
		String attributes = "" + attrs + "";
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch (Exception e)
				{
					break;
				}

			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(entity),
					nodeMap.get(activity), "wasGeneratedBy");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

		return null;
	}
	

	public WasInfluencedBy newWasInfluencedBy(QName id, QName e2, QName e1,
			Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String influencee = "" + e2 + "";
		String influencer = "" + e1 + "";
		
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("influencee", influencee);
		property.put("influencer", influencer);
		property.put("type", "wasInfluencedBy");
		String attributes = "" + attrs + "";
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				String v1 = wordList.get(k);
				String v2 = wordList.get(k + 1);
				//String v3 = v2.replaceAll("\"|'", "");
				property.put(v1, v2);
				v1 = null;
				v2 = null;
			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(influencee), nodeMap.get(influencer),
					"wasInfluencedBy");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public WasInformedBy newWasInformedBy(QName id, QName a2, QName a1,
			Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String activity2 = "" + a2 + "";
		String activity1 = "" + a1 + "";
		String attributes = "" + attrs + "";
		while (id1 != null)
		{
			break;
		}
		Map<String, String> property = new HashMap<String, String>();
		property.put("id", id1);
		property.put("activity2", activity2);
		property.put("activity1", activity1);
		property.put("type", "wasInformedBy");
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch(Exception e)
				{
					break;
				}
				
			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(activity2), nodeMap.get(activity1),
					"wasInformedBy");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	public WasInvalidatedBy newWasInvalidatedBy(QName id, QName en, QName a,
			XMLGregorianCalendar t, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String time = "" + t + "";
		String entity = "" + en + "";
		String activity = "" + a + "";
		while (!activity.isEmpty())
		{
			Map<String, String> property = new HashMap<String, String>();
			property.put("id", id1);
			property.put("entity", entity);
			property.put("activity", activity);
			property.put("time", time);
			property.put("type", "wasInvalidateBy");
			String attributes = "" + attrs + "";
			while (!attributes.isEmpty())
			{
				String[] array = attributes.split("='");
				List<String> wordList = Arrays.asList(array);
				int i = array.length;
				int k = 0;
				for (k = 0; k < i; k = k + 2)
				{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				break;
			}
			try
			{
				URI relationshipUri = CreateGraph.addRelationship(
						nodeMap.get(entity),
						nodeMap.get(activity), "wasInvalidateBy");
				CreateGraph.addMetadataToProperty(relationshipUri, property);
			}
			catch (URISyntaxException e)
			{
				e.printStackTrace();
			}
			break;
		}
		return null;
	}

	
	public WasStartedBy newWasStartedBy(QName id, QName a2, QName en, QName a1,
			XMLGregorianCalendar t, Collection<Attribute> attrs)
	{
		String id1 = "" + id + "";
		String time = "" + t + "";
		String activity2 = "" + a2 + "";
		String entity = "" + en + "";
		String activity1 = "" + a1 + "";
		while (id1 != null)
		{
			break;
		}
		Map<String, String> property = new HashMap<String, String>();
		property.put("time", time);
		property.put("id", id1);
		property.put("entity", entity);
		property.put("activity2", activity2);
		property.put("activity1", activity1);
		property.put("type", "wasStartedBy");
		String attributes = "" + attrs + "";
		while (!attributes.isEmpty())
		{
			String[] array = attributes.split("='");
			List<String> wordList = Arrays.asList(array);
			int i = array.length;
			int k = 0;
			for (k = 0; k < i; k = k + 2)
			{
				try{
					String v1 = wordList.get(k);
					String v2 = wordList.get(k + 1);
					//String v3 = v2.replaceAll("\"|'", "");
					property.put(v1, v2);
					v1 = null;
					v2 = null;
				}
				catch(Exception e){
					break;
				}
				
			}
			break;
		}
		try
		{
			URI relationshipUri = CreateGraph.addRelationship(
					nodeMap.get(activity2), nodeMap.get(entity),
					"wasStartedBy");
			CreateGraph.addMetadataToProperty(relationshipUri, property);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public void startBundle(QName arg0, Hashtable<String, String> arg1)
	{
		// TODO Auto-generated method stub

	}

	public void startDocument(Hashtable<String, String> arg0)
	{
		// TODO Auto-generated method stub

	}

	public DerivedByInsertionFrom newDerivedByInsertionFrom(QName arg0,
			QName arg1, QName arg2, List<KeyQNamePair> arg3,
			Collection<Attribute> arg4)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public DerivedByRemovalFrom newDerivedByRemovalFrom(QName arg0, QName arg1,
			QName arg2, List<Object> arg3, Collection<Attribute> arg4)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public DictionaryMembership newDictionaryMembership(QName arg0,
			List<KeyQNamePair> arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

}