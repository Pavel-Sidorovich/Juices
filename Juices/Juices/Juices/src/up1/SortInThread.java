/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package up1;

import java.util.ArrayList;

/**
 *
 * @author zbook.by
 */
public class SortInThread implements Runnable {

    ArrayList <String> ing;

    @Override
    public void run() {
        ing.sort(new SortByCode());
    }

    SortInThread(ArrayList <String> ing) {
        this.ing = ing;
    }
}
