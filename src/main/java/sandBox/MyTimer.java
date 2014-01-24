/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandBox;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Loïc
 */
public class MyTimer implements ActionListener {

    private int timeMinute;
    private int timeSeconde;

    public MyTimer(int initMinute, int initSeconde) {
        super();
        this.timeMinute = initMinute;
        this.timeSeconde = initSeconde;
    }

    public void actionPerformed(ActionEvent e) {
        if (this.timeSeconde == 0) {
            this.timeMinute--;
            this.timeSeconde = 59;
        } else {
            this.timeSeconde--;
        }
        System.out.println(this.timeMinute + " : " + this.timeSeconde);
        if (this.timeSeconde == 0 && this.timeMinute == 00) {
            System.out.println("Fin de la boucle !");
        }
    }
}
