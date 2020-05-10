
package capaInterfaz.menuAlumnos;

import capaInterfaz.ficheros.ActionListenerCargarTxtAlumnos;
import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.ficheros.ManejadorFichero;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel de alta de alumnos desde fichero.
 *
 * @author Confiencial
 */
public class PanelMenuAltaAlumnosDesdeFichero extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_ALTA_ALUMNOS = 31;
    private static final int FRAME_SELECCIONAR_FICHERO = 3121;
    private static final int REALIZAR_ALTA_MASIVA = 3122;

    private JTextField campo_seleccionar_fichero;

    /** Ruta completa del fichero que se va a cargar.*/
    public static String strFilePath;

    /** Crea e inicializa un nuevo PanelMenuAltaAlumnosDesdeFichero
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuAltaAlumnosDesdeFichero(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor



    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Alumnos>Alta>Alta desde fichero");
        ruta.setBounds(20,20,400,70);

        campo_seleccionar_fichero = new JTextField();
        campo_seleccionar_fichero.setBounds(200,200, 300, 20);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonSearch = new JButtonOp("Examinar...",FRAME_SELECCIONAR_FICHERO);
        botonSearch.setBounds(550, 195, 100, 30);
        botonSearch.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Cancelar",MENU_ALTA_ALUMNOS);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);

        JButtonOp botonOK = new JButtonOp("Aceptar",REALIZAR_ALTA_MASIVA);
        botonOK.setBounds(500, 500, 100, 30);
        botonOK.addActionListener(this);

        this.add(ruta);
        this.add(campo_seleccionar_fichero);
        this.add(botonSearch);
        this.add(botonHome);
        this.add(botonBack);
        this.add(botonOK);

    } // fin del método cargarElementos



    /** Método que captura los eventos del panel alta de alumnos desde fichero
     *  y ejecuta el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {
        JButtonOp b = (JButtonOp) e.getSource();
        int ancho = FrameMenuPrincipal.ancho;
        int alto = FrameMenuPrincipal.alto;
        switch(b.getNumOperacion()){
            case MENU_PRINCIPAL:
                PanelMenuPrincipal pSubmenuPrincipal = new PanelMenuPrincipal(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuPrincipal);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_ALTA_ALUMNOS:
                PanelMenuAltaAlumnos pSubmenuAltaAlumnos = new PanelMenuAltaAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAltaAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case FRAME_SELECCIONAR_FICHERO:
                try {
                   new ActionListenerCargarTxtAlumnos(this).actionPerformed(e);
                   campo_seleccionar_fichero.setText(strFilePath);
                } catch (RuntimeException e1) {
                   JDialogOperacionFail dialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                              e1.getMessage(), true);
                   dialogFail.setVisible(true);
                }
                
                break;
            case REALIZAR_ALTA_MASIVA:
                try {
                    System.out.println("lol");
                    if (strFilePath != null) {
                        ManejadorFichero mf = new ManejadorFichero();
                        mf.Carga(strFilePath,this);
                        JDialogOperacionOK dialogOK = new JDialogOperacionOK(FrameMenuPrincipal.getFramePrincipal(),
                                                                             "Los alumnos se han dado de alta",
                                                                             true);
                        dialogOK.setVisible(true);
                        strFilePath = null;
                    } else {
                       JDialogOperacionFail dialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                                  "Escoja un fichero de texto",
                                                                                  true);
                       dialogFail.setVisible(true);
                    }
                    campo_seleccionar_fichero.setText("");
                } catch (RuntimeException e1) {
                    campo_seleccionar_fichero.setText("");
                    JDialogOperacionFail dialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               e1.getMessage(),
                                                                               true);
                    dialogFail.setVisible(true);
                }
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuAltaAlumnosDesdeFichero