/**
 * Created by HP on 10.02.2015.
 */
import java.io.*;
import java.util.*;

public class Juice {
    private ArrayList<String> components;

    public ArrayList<String> getComponents() {
        return this.components;
    }
    public void setComponents(ArrayList<String> components) {
        this.components = components;
    }
    public Juice creation() {
        Juice juice = new Juice();
        ArrayList<String> list = new ArrayList<String> ();
        try {
            BufferedReader buf = new BufferedReader(new FileReader("input5.in"));
            String bufString = "";
            while((bufString = buf.readLine()) != null) {
                list.add(bufString);
            }
            buf.close();
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
        juice.setComponents(list);
        return juice;
    }
    @Override
    public String toString() {
        return (this.components.toString());
    }
    public LinkedHashSet<String> getList() {
        LinkedHashSet<String> set = new LinkedHashSet<String> ();
        for(int i = 0;i < this.components.size();i++) {
            StringTokenizer st = new StringTokenizer(this.components.get(i), " ");
            while (st.hasMoreTokens())
                set.add(st.nextToken());
        }
        return set;
    }
    public void listMentioned() {
        if (!(this.components.isEmpty())) {
            LinkedHashSet<String> set = this.getList();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice1.out"));
                bw.write(set.toString());
                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice1.out"));
                bw.write("no elements!");
                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void sortList() {
        if (!(this.components.isEmpty()))  {
            LinkedHashSet<String> set = this.getList();
            ArrayList<String> list = new ArrayList<String>(set);
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice2.out"));
                bw.write(list.toString());
                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice2.out"));
                bw.write("no elements!");
                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public boolean isEntry(ArrayList<String> content,ArrayList<String> store) {
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < store.size(); j++)
            sb.append(store.get(j));
        for (int i = 0; i < content.size(); i++) {
            String check = content.get(i);
            if(sb.indexOf(check) == -1)
                return false;
        }
        return true;
    }
    public ArrayList<ArrayList<String>> delete (ArrayList<ArrayList<String>> list, int index) {
        ArrayList<ArrayList<String>> del = new ArrayList<ArrayList<String>>();
        for(int i = 0;i < list.size();i++)
            if(i != index)
                del.add(list.get(i));
        return del;
    }
    public ArrayList<ArrayList<String>> swap (ArrayList<ArrayList<String>> list, int firstIndex,int secondIndex) {
        ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < list.size(); i++) {
            if (i == firstIndex) {
                //for(int j = 0;j < list.get(secondIndex).size();j++)
                newList.add(i, list.get(secondIndex));
            }
            else
            if (i == secondIndex)
                newList.add(i, list.get(firstIndex));
            else
                newList.add(i, list.get(i));
        }
        return newList;
    }
    public void numberMin() {
        if (!(this.components.isEmpty()))  {
            LinkedHashSet<ArrayList<String>> newComponents = new LinkedHashSet<ArrayList<String>>();
            for(int i = 0;i < this.components.size();i++) {
                ArrayList<String> list = new ArrayList<String> ();
                StringTokenizer st = new StringTokenizer(this.components.get(i), " ");
                while (st.hasMoreTokens()) {
                    list.add(st.nextToken());
                }
                Collections.sort(list, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
                newComponents.add(list);
            }
            ArrayList<ArrayList<String>> components = new ArrayList<ArrayList<String>> ();
            for(ArrayList<String> a : newComponents) {
                components.add(a);
            }
            for(int i = components.size() - 1;i >= 0; i--)
                for (int j = 0; j < i; j++)
                    if (components.get(j).size() > components.get(j + 1).size()) {
                        components = swap(components, j, j + 1);
                    }
            int count;
            count = 0;
            for(int i = 0;i < components.size() - 1;i++) {
                count = i;
                for (int j = count + 1; j < components.size(); j++) {
                    if (isEntry(components.get(i), components.get(j)))
                        components = swap(components, ++count, j);
                }
                i = count + 1;
            }
            count = 0;
            for(int i = 0;i < components.size() - 1;i++)
                if(!(isEntry(components.get(i),components.get(i + 1))))
                    count++;
            count++;
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice3.out"));
                bw.write(Integer.toString(count));
                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("juice3.out"));
                bw.write("no elements!");
                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
