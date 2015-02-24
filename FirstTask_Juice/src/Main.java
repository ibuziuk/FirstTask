/**
 * Created by HP on 10.02.2015.
 */
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Work juices = new Work();
        juices.listOfMentioned();
        final Work finalJuice = juices;
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                finalJuice.sortListOfMentioned();
            }
        });
        myThread.start();
        juices.numberWashesMin();
    }
}