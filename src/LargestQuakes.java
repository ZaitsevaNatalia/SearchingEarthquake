/**
 * Created by zajan on 31.01.2017.
 */
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;


public class LargestQuakes
{
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        //EarthQuakeParser xp = new EarthQuakeParser();
        LargestQuakes client = new LargestQuakes();

        //String source = "data/nov20quakedatasmall.atom";
        //ArrayList<QuakeEntry> list  = xp.read(source);
        //Location kiev = new Location(36.77, -98.06);

        //Collections.sort(list);
        /*for(QuakeEntry loc : list){
            System.out.println(loc);
        }*/
        //System.out.println("# quakes = "+list.size());

        //ArrayList<QuakeEntry> myList = client.filterByDistanceFrom(list, 20000.0, kiev);
        client.findLargestQuakes();

        /*for(QuakeEntry loc: list)
        {
            System.out.println(loc);
        }*/

        System.out.print("1");
    }
    public void findLargestQuakes()
    {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size());

        Integer index = indexOfLargest(list);
        System.out.println("The index location of the largest  magnitude earthquake is " + index);

        ArrayList<QuakeEntry> listLargest = getLargest(list, 5);
        //System.out.println(list.get(index));

        for (QuakeEntry element : listLargest)
        {
            System.out.println(element);
        }
    }

    public Integer indexOfLargest(ArrayList<QuakeEntry> data)
    {
        double maxMagnitude = 0;
        int answer = 0;
        for (int i = 0; i < data.size(); i ++)
        {
            if (data.get(i).getMagnitude() > maxMagnitude)
            {
                maxMagnitude = data.get(i).getMagnitude();
                answer = i;
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, Integer howMany)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        int size = quakeData.size();

        for (int i = 0; i < howMany; i ++)
        {
            int index = indexOfLargest(quakeData);
            answer.add(quakeData.get(index));
            quakeData.remove(index);

            if (i >= size - 1)
                return answer;
        }

        return answer;
    }
}
