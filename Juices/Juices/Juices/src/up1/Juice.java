/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package up1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zbook.by
 */
public class Juice {

    private List<String> ingred = new ArrayList<>();
    private int count;
    private int prJuice;

    Juice() {
        ingred = null;
        count = 0;
        prJuice = 0;
    }

    Juice(String ingred) {
        this.ingred.add(ingred);
        count++;
        prJuice = 0;
    }

    Juice(List<String> ingred) {
        this.ingred.addAll(ingred);
        this.count = ingred.size();
        prJuice = 0;
    }

    Juice(ArrayList<String> ingred, int count) {
        this.ingred.addAll(ingred);
        this.count = count;
        prJuice = 0;
    }

    boolean contains(Juice j) {
        return ingred.containsAll(j.ingred);
    }

    int count() {
        return count;
    }

    int prJuice() {
        return prJuice;
    }

    void prJuice(int i) {
        prJuice += i;
    }

    String ingred(int i) {
        return ingred.get(i);
    }

}
