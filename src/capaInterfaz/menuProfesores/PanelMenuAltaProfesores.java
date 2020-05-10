
package capaInterfaz.menuProfesores;


import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.Profesor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel del menú para alta de profesores.
 *
 * @author Confiencial
 */
public class PanelMenuAltaProfesores extends JPanel implements ActionListener{


    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_PROFESORES = 4;
    private static final int ALTA_PROFESOR = 41;

    private JTextField introduzca_nombre = new JTextField();
    private JTextField introduzca_apellidos = new JTextField();
    private JTextField introduzca_grupo_clase1 = new JTextField();
    private JTextField introduzca_grupo_clase2 = new JTextField();


    /** Crea e inicializa un nuevo PanelMenuAltaProfesores
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuAltaProfesores(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor


    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Profesores>Alta");
        ruta.setBounds(20,20,400,70);

        JLabel campos_obligatorios = new JLabel("* Campos obligatorios");
        campos_obligatorios.setBounds(20,500,400,70);

        JLabel filtro_nombre = new JLabel("Nombre*");
        filtro_nombre.setBounds(150,150,200,70);
        Font auxFont = filtro_nombre.getFont();
        filtro_nombre.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_apellidos = new JLabel("Apellidos*");
        filtro_apellidos.setBounds(150,200,200,70);
        auxFont = filtro_apellidos.getFont();
        filtro_apellidos.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_grupo_clase1 = new JLabel("Grupo de clase 1");
        filtro_grupo_clase1.setBounds(150,250,200,70);
        auxFont = filtro_grupo_clase1.getFont();
        filtro_grupo_clase1.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel filtro_grupo_clase2 = new JLabel("Grupo de clase 2");
        filtro_grupo_clase2.setBounds(150,300,200,70);
        auxFont = filtro_grupo_clase2.getFont();
        filtro_grupo_clase2.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        introduzca_nombre.setBounds(300,175, 150, 20);
        introduzca_apellidos.setBounds(300,225, 150, 20);
        introduzca_grupo_clase1.setBounds(300,275, 150, 20);
        introduzca_grupo_clase2.setBounds(300,325, 150, 20);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Cancelar",MENU_PROFESORES);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);

        JButtonOp botonOK = new JButtonOp("Aceptar",ALTA_PROFESOR);
        botonOK.setBounds(500, 500, 100, 30);
        botonOK.addActionListener(this);


        this.add(ruta);
        this.add(campos_obligatorios);
        this.add(filtro_nombre);
        this.add(filtro_apellidos);
        this.add(filtro_grupo_clase1);
        this.add(filtro_grupo_clase2);
        this.add(introduzca_nombre);
        this.add(introduzca_apellidos);
        this.add(introduzca_grupo_clase1);
        this.add(introduzca_grupo_clase2);
        this.add(botonHome);
        this.add(botonBack);
        this.add(botonOK);

    } // fin del método cargarElementos

    /** Método que captura los eventos del panel alta profesores
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
            case MENU_PROFESORES:
                PanelMenuProfesores pSubmenuProfesores = new PanelMenuProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case ALTA_PROFESOR:
               String nombre = introduzca_nombre.getText();
               String apellidos = introduzca_apellidos.getText();
               String grupo_clase1 = introduzca_grupo_clase1.getText();
               String grupo_clase2 = introduzca_grupo_clase2.getText();

               Profesor profesor = new Profesor (nombre, apellidos, grupo_clase1, grupo_clase2);
               try{
                   profesor.altaProfesor();
                   JDialogOperacionOK jDialogOK = new JDialogOperacionOK(FrameMenuPrincipal.getFramePrincipal(),
                                                                         "El profesor se ha dado de alta correctamente",true);
                   jDialogOK.setVisible(true);
                   PanelMenuProfesores pSubmenuProfesores2 = new PanelMenuProfesores(ancho,alto);
                   FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuProfesores2);
                   FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                } catch (RuntimeException e1) {
                   JDialogOperacionFail jDialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               e1.getMessage(),true);
                   jDialogFail.setVisible(true);
                }
                break;

        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuAltaProfesores