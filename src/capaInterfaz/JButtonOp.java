
package capaInterfaz;


import javax.swing.ImageIcon;
import javax.swing.JButton;

/** Método que gestiona el contenido (texto e imágenes) de los botones de la
 *  aplicación.
 *
 * @author Confiencial
 */
public class JButtonOp extends JButton {

    
    private int NumOperacion;
   
    /** Asigna un texto al botón.
     * 
     * @param label texto del botón.
     */
    public JButtonOp(String label){
        super(label);
    } // fin del constructor


    /** Asigna un texto y un nº de operación al botón.
     *
     * @param label texto del botón.
     * @param numop nº de operación asociado.
     */
    public JButtonOp(String label,int numop){
        super(label);
        this.setNumOperacion(numop);
    } // fin del constructor

    /** Asigna un texto, imagen y un nº de operación al botón.
     *
     * @param label texto del botón.
     * @param img imagen del botón.
     * @param numop nº de operación asociado.
     */
    public JButtonOp(String label,ImageIcon img, int numop){
        super(label, img);
        this.setNumOperacion(numop);
    } // fin del constructor


    /** Método que asigna un nº de operación al botón.
     *
     * @param numop nº de operación.
     */
    public void setNumOperacion(int numop){
        this.NumOperacion=numop;
    } // fin del método setNumOperacion

    /** Método que devuelve el nº de operación asociado al botón.
     *
     * @return nº de operación asociado al botón.
     */
    public int getNumOperacion(){
        return NumOperacion;
    } // fin del método getNumOperacion

} // fin de la clase JBottonOp