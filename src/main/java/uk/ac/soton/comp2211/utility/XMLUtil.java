package uk.ac.soton.comp2211.utility;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.ac.soton.comp2211.airport.Airport;

public class XMLUtil {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("airports.xml");
        Airport obj1 = (Airport) context.getBean("Airport1");
        Airport obj2 = (Airport) context.getBean("Airport2");
        obj1.getName();
        obj1.getRunways();
        obj2.getName();
        obj2.getRunways();  
        }
}
