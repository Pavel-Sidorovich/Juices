/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package up1;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author zbook.by
 */
public class Treatment {
    private final List <String> ingred  = new ArrayList <>();
    private final List <Juice> all  = new ArrayList <>();
    private final Set <String> ing = new LinkedHashSet <>();
    
    void read() throws FileNotFoundException{
            try (Scanner sc = new Scanner(new InputStreamReader(new FileInputStream("Juice.txt")))) {
                String str;
                while (sc.hasNext()) {
                    ingred.clear();
                StringTokenizer stk = new StringTokenizer(sc.nextLine(), " ");
                while (stk.hasMoreElements()) {
                    str = stk.nextElement().toString();
                    ingred.add(str);
                    ing.add(str);
                    }
                all.add(new Juice(ingred));
                }
            }
        }
    
    void printToJuice1() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("juice1.txt"))) {
            for (String str : ing) {
                writer.write(str + " ");
            }
        }
    }
    
    void printToJuice2() throws IOException, InterruptedException {
        SortInThread mThing;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("juice2.txt"))) {
            ArrayList<String> ingr = new ArrayList<>();
            ingr.addAll(ing);
            mThing = new SortInThread(ingr);
            Thread myThready = new Thread(mThing);
            myThready.start();
            myThready.join();
            for (String str : ingr) {
                writer.write(str + " ");
            }
        }
    }

    void wash() throws IOException {
        all.sort(new SortByCount());
        for (int i = 0; i < all.size(); i++) {
            Juice j = all.get(i);
            for (int k = i + 1; k < all.size(); k++) {
                Juice j1 = all.get(k);
                if (j1.contains(j)) {
                    all.get(k).prJuice(1);
                }
            }
        }
        Integer count = 0;
        all.sort(new SortByPr());
        while (!all.isEmpty()) {
            Juice j = all.remove(0);
            for (int i = 0; i < all.size(); i++) {
                Juice j1 = all.get(i);
                if (j1.contains(j) || j.contains(j1)) {
                    j = all.remove(i);
                    i--;
                }
            }
            count++;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("juice3.txt"))) {
            writer.write(count.toString());
        }
    }
}
