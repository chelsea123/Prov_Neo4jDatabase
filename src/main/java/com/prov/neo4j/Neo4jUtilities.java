package com.prov.neo4j;

import org.openprovenance.prov.xml.ModelConstructor;
import org.openprovenance.prov.xml.ProvFactory;
import org.openprovenance.prov.notation.Utility;
import org.antlr.runtime.tree.CommonTree;
import org.openprovenance.prov.notation.TreeTraversal;

public class Neo4jUtilities extends Utility{
	
	private ModelConstructor model ;
	private ProvFactory factory ;
	private TreeTraversal traversal;
	
	public Neo4jUtilities()
	{
		model = (ModelConstructor) new ProvConstructor();
		factory =new ProvFactory();
		traversal = new TreeTraversal(model , factory);
	}


	public String provnToNeo4j(String file) throws java.io.IOException, Throwable {

        CommonTree tree = convertASNToTree(file);

        String s= (String) traversal.convert(tree);

        return s;

    }

}
