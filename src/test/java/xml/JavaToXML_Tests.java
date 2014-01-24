/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;



import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import tools.Utils;

/**
 *
 * @author Loïc
 */
public class JavaToXML_Tests {

    public JavaToXML_Tests() {
        test01();

    }
    
    private void test01(){
        try {
            String pathEcriture = "C:\\NetBeansProjects\\CodeTester\\code php\\niv 2\\resultatXML.xml";
            String pathLecture = "C:\\NetBeansProjects\\CodeTester\\code php\\niv 2\\boucles for imbriquées avec conditions.php";
            Utils.buildXML(pathEcriture, pathLecture);
        } catch (TransformerException ex) {
            Logger.getLogger(JavaToXML_Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
