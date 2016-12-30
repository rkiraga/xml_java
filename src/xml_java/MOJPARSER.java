package xml_java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.directory.SearchResult;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MOJPARSER extends DefaultHandler{
	
	String temp;
	
    private CpeItem cpeitem;
    private ArrayList<CpeItem> cpeItems = new ArrayList<CpeItem>();
    
    SAXParserFactory spfac;
    SAXParser sp;
    
   MOJPARSER() throws SAXException, IOException, ParserConfigurationException{
        spfac = SAXParserFactory.newInstance();
        sp = spfac.newSAXParser();
        System.out.println("Czytam plik..........");
        sp.parse("C:/Users/Radek2/Desktop/official-cpe-dictionary_v2.3.xml", this);
        this.read();
    }

    
    /** The main method sets things up for parsing */
    public static void main(String[] args) throws IOException, SAXException,ParserConfigurationException {
     
    	
    //&&& WPIEPRZE TO DO KONSTRUKTORA TO BEDZIE LATWIEJ WAM UZYWAC
    	
/*	           //Create a "parser factory" for creating SAX parsers
	           SAXParserFactory spfac = SAXParserFactory.newInstance();
	
	           //Now use the parser factory to create a SAXParser object
	           SAXParser sp = spfac.newSAXParser();
	
	           //Create an instance of this class; it defines all the handler methods
	           MOJPARSER handler = new MOJPARSER();
	
	           //Finally, tell the parser to parse the input and notify the handler
	           sp.parse("C:/Users/Radek2/Desktop/official-cpe-dictionary_v2.3.xml", handler);
	          
	          //TO MUSI BYC RAZ WYWOLANE (NA POCZATKU)
	           handler.read();
	           
	          // System.out.println(handler.returnElements(0,10));*/
    //&&&        
          //PRZYKLAD SZUKANIA
          //  ArrayList<CpeItem> lista = handler.search("Adobe", "name");
          // System.out.println(CpeItem.ArrayCpeItemToString(lista));
    }
    
    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
           temp = new String(buffer, start, length);
    } 
    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */ 
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
           temp = "";
           
           if (qName.equalsIgnoreCase("cpe-item")) {
                  cpeitem = new CpeItem();
                  cpeitem.name=attributes.getValue("name");
          //        System.out.println("1");
           }
           if (qName.equalsIgnoreCase("title")) {
        	   cpeitem.titleLang=attributes.getValue("xml:lang");
        	//   System.out.println("2");
           }
           if (qName.equalsIgnoreCase("title")) {
        	   cpeitem.titleLang=attributes.getValue("xml:lang");
           }
           if (qName.equalsIgnoreCase("cpe-23:cpe23-item")) {
        	   cpeitem.cpe23=attributes.getValue("name");
           }
          if (qName.equalsIgnoreCase("reference")) {
        	   cpeitem.addRef(attributes.getValue("href"),"");
        	 //  System.out.println("\nHALO\n"+ attributes.getValue("href"));
			}
    }
    
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

     if (qName.equalsIgnoreCase("cpe-item")) {
           // add it to the list
    	 cpeItems.add(cpeitem);
    	 //	 System.out.println("3");

     } else if (qName.equalsIgnoreCase("title")) {
    	 cpeitem.title=temp;
    	 //System.out.println("4");
     }else if (qName.equalsIgnoreCase("reference")) {
    	 cpeitem.references.get(cpeitem.references.size()-1).ref=temp;
     }

    }
    
    void read() {
        System.out.println("Liczba elementow: " + cpeItems.size()  + ".");
        
     //ODKUMENTUJ TRY CATCHA ZEBY DO PLIKU//   try{
           // PrintWriter writer = new PrintWriter("C:/Users/Radek2/Desktop/ladnyplik.xml", "UTF-8"); 
        	for(CpeItem i : cpeItems){
  //DO WYSWIETLENIA NA KONSOLI-> 	
            //	  System.out.println(i.toStringLadny());
            //	 writer.println(i.toString());
             }
             //writer.close();
             System.out.println("SKONCZYLEM.");
/*        } catch (IOException e) {
           // do something
        }*/
   
    }
    
    ArrayList<CpeItem> search(String query, String whatfield)
    {	
    	query = query.toLowerCase();
    	
    	int matchesCounter=0;
    	ArrayList<CpeItem> result = new ArrayList<CpeItem>();
    	
    	if(whatfield=="name"){
    		for(CpeItem i : cpeItems){
    			if(i.name.toLowerCase().contains(query)==true){
    				result.add(i);
    				matchesCounter++;
    			}
    		}
    	}else if(whatfield=="title"){
    		for(CpeItem i : cpeItems){
    			if(i.title.toLowerCase().contains(query)==true){
    				result.add(i);
    				matchesCounter++;
    			}
    		}
    	}else if(whatfield=="cpe23"){
    		for(CpeItem i : cpeItems){
    			if(i.cpe23.toLowerCase().contains(query)==true){
    				result.add(i);
    				matchesCounter++;
    			}
    		}
    	}else if(whatfield=="href"){
    		for(CpeItem i : cpeItems){
    			for(Ref j : i.references){
    				if(j.ref.toLowerCase().contains(query) ){
    					result.add(i);
    					matchesCounter++;
    				} 
    			}
    		}
    	}
    	else if(whatfield=="description"){
    		for(CpeItem i : cpeItems){
    			for(Ref j : i.references){
    				if(j.ref.toLowerCase().contains(query)){
    					result.add(i);
    					matchesCounter++;
    				} 
    			}
    		}
    	}
    	
    	//potem wykomentowac:
    	System.out.println("Znalezniono "+matchesCounter+" pasujacych elementow.");
    	
    	return result;
    }
  
    public String returnElements(int odKtorego, int ile){
    	String result="";
    		
    		for(int i=odKtorego; i<odKtorego+ile; i++){
    			result+=cpeItems.get(i).toString();
    		}
    	return result;
    }
}
