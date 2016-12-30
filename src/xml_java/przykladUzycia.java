package xml_java;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class przykladUzycia {

	public static void main(String[] args) {
		MOJPARSER mp=null;
		try {
			mp = new MOJPARSER();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//przegladanie od zerowego do 10
		System.out.println(mp.returnElements(0, 10));
		//wyszukiwanie
		//System.out.println(mp.search("adobe", "name"));
	}

}
