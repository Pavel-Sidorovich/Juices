/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package up1;

import java.util.Comparator;

/**
 *
 * @author zbook.by
 */
public class SortByPr implements Comparator<Juice> {

    @Override
    public int compare(Juice o1, Juice o2) {
        if (o1.prJuice() < o2.prJuice()) {
            return -1;
        }
        if (o1.prJuice() > o2.prJuice()) {
            return 1;
        }
        return 0;
    }

}
