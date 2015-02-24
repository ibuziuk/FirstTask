/**
 * Created by HP on 10.02.2015.
 */
import java.io.*;
import java.util.*;

public class Juice {
    private ArrayList<String> namesOfJuices;

    public ArrayList<String> getNamesOfJuices() {
        return this.namesOfJuices;
    }

    public void setNamesOfJuices(ArrayList<String> juices) {
        this.namesOfJuices = juices;
    }

    @Override
    public String toString() {
        return (this.namesOfJuices.toString());
    }

    public LinkedHashSet<String> getListOfComponents() {
        LinkedHashSet<String> setOfComponents = new LinkedHashSet<String> ();
        for(int i = 0; i < this.namesOfJuices.size(); i++) {
            StringTokenizer st = new StringTokenizer(this.namesOfJuices.get(i), " ");
            while (st.hasMoreTokens()) {
                setOfComponents.add(st.nextToken());
            }
        }
        return setOfComponents;
    }
}