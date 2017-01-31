import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
//import edu.duke.*;

public class EarthQuakeClient
{
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        //EarthQuakeParser xp = new EarthQuakeParser();
        EarthQuakeClient client = new EarthQuakeClient();
        //String source = "data/2.5_week.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        //String source = "data/nov20quakedatasmall.atom";
        //ArrayList<QuakeEntry> list  = xp.read(source);
        //Location kiev = new Location(36.77, -98.06);

        //Collections.sort(list);
        /*for(QuakeEntry loc : list){
            System.out.println(loc);
        }*/
        //System.out.println("# quakes = "+list.size());

        //ArrayList<QuakeEntry> myList = client.filterByDistanceFrom(list, 20000.0, kiev);
        client.quakesByPhrase();

        /*for(QuakeEntry loc: list)
        {
            System.out.println(loc);
        }*/


    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData, double magMin)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

        for (QuakeEntry element: quakeData)
        {
            if (element.getMagnitude() > magMin)
                answer.add(element);
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

        for (QuakeEntry element : quakeData)
        {
            double distance = from.distanceTo(element.getLocation());
            if (distance < distMax)
                answer.add(element);
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

        for (QuakeEntry element: quakeData)
        {
            if (element.getDepth() > minDepth && element.getDepth() < maxDepth)
                answer.add(element);
        }

        return answer;
    }

    public void quakesOfDepth()
    {
        EarthQuakeParser parser = new EarthQuakeParser();
        String sourse = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(sourse);

        System.out.println("read data for " +list.size() + " quakes ");

        ArrayList<QuakeEntry> answer = filterByDepth(list, -10000, -5000);

        if (answer.size() > 0)
            System.out.println("Find quakes with depth between -10000.0 and -5000.0");
        else
            System.out.println("Don't find quakes with depth between -10000.0 and -5000.0");

        for (QuakeEntry element: answer)
            System.out.println(element);

        if (answer.size() > 0)
            System.out.println("Found " + answer.size() + " quakes that match that criteria");
    }

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

        for (QuakeEntry element : quakeData)
        {
            String title = element.getInfo();

            if (title.contains(phrase))
            {
                switch (where)
                {
                    case "start":
                        if (title.startsWith(phrase))
                            answer.add(element);
                        break;
                    case "end":
                        if (title.endsWith(phrase))
                            answer.add(element);
                        break;
                    case "any":
                        answer.add(element);
                        break;
                }
            }
        }

        return answer;
    }

    public void quakesByPhrase()
    {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        String where = "any";
        ArrayList<QuakeEntry> quakes = filterByPhrase(list, where, "Mojave");
        for (QuakeEntry element : quakes)
            System.out.println(element);

        if (quakes.size() > 0)
            System.out.println("Found " + quakes.size() + " quakes that match California at " + where);

    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                    qe.getLocation().getLatitude(),
                    qe.getLocation().getLongitude(),
                    qe.getMagnitude(),
                    qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        double magnitude = 5;                                          // Where can I take this number???
        ArrayList<QuakeEntry> bigQuakes = filterByMagnitude(list,magnitude);

        for (QuakeEntry element: bigQuakes)
        {
            System.out.println(element);                               // чи правильно виведе element???
        }

        System.out.println("Found " + bigQuakes.size() + " quakes that match that criteria");
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
         Location city =  new Location(38.17, -118.82);

        ArrayList<QuakeEntry> quakes = filterByDistanceFrom(list, 1000000, city);
        for (QuakeEntry element: quakes)
        {
            double distance = city.distanceTo(element.getLocation());
            System.out.println(distance + " " + element.getInfo());
        }

        if (quakes.size() > 0)
            System.out.println("Found " + quakes.size() + " quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }

}

