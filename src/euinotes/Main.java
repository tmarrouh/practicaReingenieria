
package euinotes;

import capaDatos.DataAccessObject;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.menuConfiguracion.JDialogMenuSeleccionarBDDPrimeraVez;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/** Clase Main del programa. Es la clase que inicia la aplicación.
 *
 * @author Confiencial
 */
public class Main {

    /** Método Main. Recupera los parámetros de acceso a la Base de Datos del
     *  fichero "config.txt" del sistema y lanza la aplicación.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream("config/config.txt");
            InputStreamReader is = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader lector = new BufferedReader(is);
            System.out.println();
            String url, user, password;
            url = lector.readLine();
            user = lector.readLine();
            password = lector.readLine();
            lector.close();
            DataAccessObject.URL = url;
            DataAccessObject.USER = user;
            DataAccessObject.PASS = password;
            FrameMenuPrincipal.getFramePrincipal().iniciar();
        } catch (FileNotFoundException e) {
            JDialogOperacionFail fail = new JDialogOperacionFail(
                                FrameMenuPrincipal.getFramePrincipal(),
                                e.getMessage(),
                                true);
            fail.setVisible(true);
        } catch (IOException e1) {
            JDialogOperacionFail fail = new JDialogOperacionFail(
                                FrameMenuPrincipal.getFramePrincipal(),
                                e1.getMessage(),
                                true);
            fail.setVisible(true);
            JDialogMenuSeleccionarBDDPrimeraVez jDialogMenuSeleccionarBDDPrimeraVez = new JDialogMenuSeleccionarBDDPrimeraVez(new Frame(),true);
            jDialogMenuSeleccionarBDDPrimeraVez.setVisible(true);
            FrameMenuPrincipal.getFramePrincipal().iniciar();
        } catch (RuntimeException e2) {
            JDialogMenuSeleccionarBDDPrimeraVez jDialogMenuSeleccionarBDDPrimeraVez = new JDialogMenuSeleccionarBDDPrimeraVez(new Frame(),true);
            jDialogMenuSeleccionarBDDPrimeraVez.setVisible(true);
            FrameMenuPrincipal.getFramePrincipal().iniciar();
        } 
    } // fin del método Main

} // fin de la clase Main