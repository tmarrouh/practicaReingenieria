
package capaInterfaz;

import capaInterfaz.menuConfiguracion.JDialogMenuConfiguracion;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**  Clase que gestiona la barra de menú de la aplicación.
 *
 * @author Confiencial
 */
public class JBarraMenus extends JMenuBar implements ActionListener {

    /** Objeto que contiene la barra de menús del frame principal.
     *
     */
    public JMenuBar menuBar;

    private JMenu menuFichero;
    private JMenu menuHerramientas;
    private JMenu menuAyuda;

    private JMenuItem itemExit;
    private JMenuItem itemOptions;
    private JMenuItem itemHelp;
    private JMenuItem itemAbout;

    /** Constructor que inicializa la barra de menús del frame principal.
     *
     */
    public JBarraMenus() {
        menuBar = new JMenuBar();

        menuFichero = new JMenu("Fichero");
        menuHerramientas = new JMenu("Herramientas");
        menuAyuda = new JMenu("Ayuda");

        itemExit = new JMenuItem("Salir");
        itemExit.addActionListener(this);

        itemOptions = new JMenuItem("Opciones...");
        itemOptions.addActionListener(this);

        itemHelp = new JMenuItem("Ayuda");
        itemAbout = new JMenuItem("About...");
        itemHelp.addActionListener(this);
        itemAbout.addActionListener(this);

        menuFichero.add(itemExit);

        menuHerramientas.add(itemOptions);

        menuAyuda.add(itemHelp);
        menuAyuda.add(itemAbout);

        menuBar.add(menuFichero);
        menuBar.add(menuHerramientas);
        menuBar.add(menuAyuda);
        
        this.setVisible(true);
    } // fin del constructor

    /** Método que captura los eventos de la barra de menús y ejecuta
     *  el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed (ActionEvent e) {

        JMenuItem item = (JMenuItem) e.getSource();

        if (item == itemExit) {
            FrameMenuPrincipal.getFramePrincipal().dispose();
        }

        if (item == itemOptions) {
            JDialogMenuConfiguracion jSubMenuConfiguracion = new JDialogMenuConfiguracion(
                                                                FrameMenuPrincipal.getFramePrincipal(),
                                                                true);
            jSubMenuConfiguracion.setVisible(true);
        }

        if (item == itemHelp) {

            String tutorial = "tutorial/Tutorial.pdf";
            try {
                File file = new File(tutorial);
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                JDialogOperacionFail dialogOperacionFail = new JDialogOperacionFail(
                                                            FrameMenuPrincipal.getFramePrincipal(),
                                                            "Error al abrir el archivo",true);
                dialogOperacionFail.setVisible(true);
            }
        }

        if (item == itemAbout) {
            JDialogAboutBox jDialogAboutBox = new JDialogAboutBox(
                                                  FrameMenuPrincipal.getFramePrincipal(),
                                                  true);
            jDialogAboutBox.setVisible(true);
        }
    } // fin del método actionPerformed

} // fin de la clase JBarraMenus