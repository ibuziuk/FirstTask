/**
 * Created by HP on 10.02.2015.
 */
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Juice juice = new Juice();
        juice = juice.creation();
        juice.listMentioned();
        final Juice finalJuice = juice;
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                finalJuice.sortList();
            }
        });
        myThread.start();
        juice.numberMin();
    }
}
