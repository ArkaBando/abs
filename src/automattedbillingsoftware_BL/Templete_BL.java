/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automattedbillingsoftware_BL;

import automatedbillingsoftware_modal.Templete;
import automatedbillingsoftware_DA.Templete_DA;

/**
 *
 * @author Arka
 */
public class Templete_BL {

    private Templete_DA tmpDA;

    public Templete_BL() {
        if (tmpDA == null) {
            tmpDA = new Templete_DA();
        }
    }

    public Templete addTemplete(Templete temp) {
        tmpDA.addTemplete(temp);
        return temp;
    }

    public Templete fetchTempleteByName(String name) {
        Templete temp = tmpDA.fetchTempleteByName(name);
        return temp;
    }

}
