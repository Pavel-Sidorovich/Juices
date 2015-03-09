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
public class SortByCount implements Comparator<Juice> {

    @Override
    public int compare(Juice o1, Juice o2) {
        if (o1.count() < o2.count()) {
            return -1;
        }
        if (o1.count() > o2.count()) {
            return 1;
        }
        return 0;
    }

}
