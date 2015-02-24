/**
 * Created by HP on 10.02.2015.
 */
import java.io.*;
import java.util.*;

public class Work {
    private Juice juices;

    public Work(){
        juices = new Juice();
        juices = this.readFile();
    }

    public Juice readFile() {
        Juice juices = new Juice();
        ArrayList<String> listOfJuices = new ArrayList<String> ();
        try {
            BufferedReader buf = new BufferedReader(new FileReader("juice.in"));
            String bufString = "";
            while((bufString = buf.readLine()) != null) {
                listOfJuices.add(bufString);
            }
            buf.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        juices.setNamesOfJuices(listOfJuices);
        return juices;
    }

    public void listOfMentioned() {
        if (!(juices.getNamesOfJuices().isEmpty())) {
            LinkedHashSet<String> setOfComponents = juices.getListOfComponents();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice1.out"));
                bw.write(setOfComponents.toString());
                bw.close();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice1.out"));
                bw.write("no elements!");
                bw.close();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void sortListOfMentioned() {
        if (!(juices.getNamesOfJuices().isEmpty()))  {
            LinkedHashSet<String> setOfComponents = juices.getListOfComponents();
            ArrayList<String> listOfComponents = new ArrayList<String>(setOfComponents);
            Collections.sort(listOfComponents, new Comparator<String>() {

                @Override
                public int compare(String firstString, String secondString) {
                    return firstString.compareTo(secondString);
                }
            });
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice2.out"));
                bw.write(listOfComponents.toString());
                bw.close();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice2.out"));
                bw.write("no elements!");
                bw.close();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public boolean isEntry(ArrayList<String> content, ArrayList<String> store) {
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < store.size(); j++) {
            sb.append(store.get(j));
        }
        for (int i = 0; i < content.size(); i++) {
            String check = content.get(i);
            if(sb.indexOf(check) == -1) {
                return false;
            }
        }
        return true;
    }

        public ArrayList<ArrayList<String>> swap (ArrayList<ArrayList<String>> list, int firstIndex, int secondIndex) {
        ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < list.size(); i++) {
            if (i == firstIndex) {
                newList.add(i, list.get(secondIndex));
            }
            else {
                if (i == secondIndex) {
                    newList.add(i, list.get(firstIndex));
                }
                else {
                    newList.add(i, list.get(i));
                }
            }
        }
        return newList;
    }

    public void numberWashesMin() {
        if (!(juices.getNamesOfJuices().isEmpty()))  {
            LinkedHashSet<ArrayList<String>> newSetNamesOfJuices = new LinkedHashSet<ArrayList<String>>();
            for(int i = 0; i < juices.getNamesOfJuices().size(); i++) {
                ArrayList<String> listOfComponents = new ArrayList<String> ();
                StringTokenizer st = new StringTokenizer(juices.getNamesOfJuices().get(i), " ");
                while (st.hasMoreTokens()) {
                    listOfComponents.add(st.nextToken());
                }
                Collections.sort(listOfComponents, new Comparator<String>() {

                    @Override
                    public int compare(String firstString, String secondString) {
                        return firstString.compareTo(secondString);
                    }
                });
                newSetNamesOfJuices.add(listOfComponents);
            }
            ArrayList<ArrayList<String>> arrayListOfComponents = new ArrayList<ArrayList<String>> ();
            for(ArrayList<String> a : newSetNamesOfJuices) {
                arrayListOfComponents.add(a);
            }
            for(int i = arrayListOfComponents.size() - 1; i >= 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (arrayListOfComponents.get(j).size() > arrayListOfComponents.get(j + 1).size()) {
                        arrayListOfComponents = swap(arrayListOfComponents, j, j + 1);
                    }
                }
            }
            int count;
            for(int i = 0; i < arrayListOfComponents.size() - 1; i++) {
                count = i;
                for (int j = count + 1; j < arrayListOfComponents.size(); j++) {
                    if (isEntry(arrayListOfComponents.get(i), arrayListOfComponents.get(j))) {
                        arrayListOfComponents = swap(arrayListOfComponents, ++count, j);
                    }
                }
                i = count + 1;
            }
            count = 0;
            for(int i = 0; i < arrayListOfComponents.size() - 1; i++) {
                if (!(isEntry(arrayListOfComponents.get(i), arrayListOfComponents.get(i + 1)))) {
                    count++;
                }
            }
            count++;
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice3.out"));
                bw.write(Integer.toString(count));
                bw.close();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice3.out"));
                bw.write("no elements!");
                bw.close();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /*public ArrayList<ArrayList<String>> delete (ArrayList<ArrayList<String>> list, int index) {
        ArrayList<ArrayList<String>> del = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < list.size(); i++) {
            if (i != index) {
                del.add(list.get(i));
            }
        }
        return del;
    }*/
}