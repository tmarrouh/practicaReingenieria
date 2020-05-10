
package capaInterfaz.ficheros;

import capaInterfaz.menuAlumnos.PanelMenuAltaAlumnosDesdeFichero;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Clase que realiza la carga de un fichero de alumnos cuando el usuario
 *  lo solicita en el módulo "Alta masiva de alumnos".
 *
 * @author Confiencial
 */
public class ActionListenerCargarTxtAlumnos {

    private PanelMenuAltaAlumnosDesdeFichero panel;

    /** Crea el ActionListener e inicializa el panel asociado.
     *
     * @param panel panel asociado al ActionListener
     */
    public ActionListenerCargarTxtAlumnos(PanelMenuAltaAlumnosDesdeFichero panel){
            this.panel=panel;
    } // fin del constructor


    /** Método que captura el evento de carga de un fichero txt en el panel
     *  de alta masiva de alumnos y ejecuta el código correspondiente a la
     *  carga del fichero y la posterior alta de los alumnos.
     *
     * @param arg0 evento capturado.
     */
    public void actionPerformed(ActionEvent arg0) {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo TXT", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(panel);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Se ha elegido este archivo: " +
                chooser.getSelectedFile().getAbsolutePath());

            String fichero = chooser.getSelectedFile().getAbsolutePath();
            String extension = fichero.substring(fichero.length()-3, fichero.length());
            if (extension.equals("txt")){
                PanelMenuAltaAlumnosDesdeFichero.strFilePath = fichero;
            } else {
                System.out.println("El archivo elegido no es de texto");
            }
        }
    } // fin del método actionPerformed

} // fin de la clase ActionListenerCargarTxtAlumnos