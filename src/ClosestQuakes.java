
/**
 * Find N-closest quakes
 *
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class ClosestQuakes {



    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany)
    {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();

        TreeMap<Double, QuakeEntry> myList = new TreeMap<Double, QuakeEntry>();

        for (QuakeEntry element : quakeData)
        {
            double distance = current.distanceTo(element.getLocation());
            myList.put(distance, element);
        }

        int count = 0;
        for(Map.Entry<Double, QuakeEntry> e : myList.entrySet())
        {
            ret.add(e.getValue());
            count++;

            if (count >= quakeData.size())
                return ret;
            else if (count == howMany)
                return ret;
        }

        return ret;
    }

    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,3);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }

}

