/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package up1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zbook.by
 */
public class UP1 {

    public static void main(String[] args) {
        try {
            Treatment tr = new Treatment();
            tr.read();
            tr.printToJuice1();
            tr.printToJuice2();
            tr.wash();
        } catch (IOException ex) {
            System.out.print("IOException");
        } catch (InterruptedException ex) {
            System.out.print("InterruptedException");
        }
    }
}
