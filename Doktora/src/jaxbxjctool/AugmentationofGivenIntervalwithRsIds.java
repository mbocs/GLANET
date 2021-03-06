/**
 * @author burcakotlu
 * @date Apr 3, 2014 
 * @time 12:04:41 PM
 * 
 * This class with return the list of RsIds in a given interval.
 */
package jaxbxjctool;

import generated.ESearchResult;
import generated.Id;
import generated.IdList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import ui.GlanetRunner;
/**
 * 
 */
public class AugmentationofGivenIntervalwithRsIds {
	
	private Unmarshaller unmarshaller;
	private static generated.ObjectFactory _fool_javac=null;
	private  XMLInputFactory xmlInputFactory=null;
	
	
	public AugmentationofGivenIntervalwithRsIds() throws Exception
    {
		this.xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
    	
		xmlInputFactory.setXMLResolver(new javax.xml.stream.XMLResolver()
        {
            	@Override
            	public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace)
                {
            		return new java.io.ByteArrayInputStream(new byte[0]);
                }
        });

		JAXBContext jaxbCtxt=JAXBContext.newInstance("generated");
		this.unmarshaller=jaxbCtxt.createUnmarshaller();

    }
	
	
	//Requires oneBased positions
	//Requires chrName without preceeding "chr" string 
	public List<String> getRsIdsInAGivenInterval(String chrNamewithoutPreceedingChr, int givenIntervalStartOneBased,int givenIntervalEndOneBased) throws Exception
    {
		List<String> rsIdList = new ArrayList<String>();
		
		//esearch default retmode is xml or it can be set to json
		//chrName is without "chr", ex: 1, X, Y, 17...
	    String eSearchString="http://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=snp&term="+ givenIntervalStartOneBased + ":" + givenIntervalEndOneBased + "[Base Position] AND "+ chrNamewithoutPreceedingChr +"[CHR] AND txid9606&usehistory=n";
                                
        XMLEventReader readerSearch= xmlInputFactory.createXMLEventReader(new StreamSource(eSearchString)); 
	
		while(readerSearch.hasNext())
        {
			XMLEvent evtSearch=readerSearch.peek();

			if(!evtSearch.isStartElement())
               {
				readerSearch.nextEvent();
				continue;
               }

			StartElement startSearch=evtSearch.asStartElement();
			String localNameSearch=startSearch.getName().getLocalPart();

			if(!localNameSearch.equals("eSearchResult"))
               {
				readerSearch.nextEvent();
				continue;
               }

			ESearchResult eSearchResult=unmarshaller.unmarshal(readerSearch, ESearchResult.class).getValue();
			IdList idList = (IdList) eSearchResult.getCountOrRetMaxOrRetStartOrQueryKeyOrWebEnvOrIdListOrTranslationSetOrTranslationStackOrQueryTranslationOrERROR().get(5);
			
			for(Id id: idList.getId()){
				rsIdList.add(id.getvalue());							
			}
			
			
       }//End of while
         
		readerSearch.close();
		
		return rsIdList;
    }
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AugmentationofGivenIntervalwithRsIds app = null;
		
		String chrName = "1";
		int startOneBased = 204924685;
		int endOneBased = 204924685;
		List<String> rsIdList = null;
		

		
		try {
			app = new AugmentationofGivenIntervalwithRsIds();
			rsIdList = app.getRsIdsInAGivenInterval(chrName, startOneBased, endOneBased);
			
			for(String rsId: rsIdList){
				GlanetRunner.appendLog(rsId);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
