
package capaInterfaz.menuAlumnos;


import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.Alumno;
import capaLogicaNegocio.GrupoClase;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel de alta individual de alumnos.
 *
 * @author Confiencial
 */
public class PanelMenuAltaAlumnosIndividual extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_ALTA_ALUMNOS = 31;
    private static final int ALTA_ALUMNO = 311;

    private JTextField campo_dni = new JTextField();
    private JTextField campo_nombre = new JTextField();
    private JTextField campo_apellidos = new JTextField();
    private JTextField campo_n_mat = new JTextField();
    private JTextField campo_grupo_clase = new JTextField();

    /** Crea e inicializa un nuevo PanelMenuAltaAlumnosIndividual
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuAltaAlumnosIndividual(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor



    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Alumnos>Alta>Alta individual");
        ruta.setBounds(20,20,400,70);

        JLabel campos_obligatorios = new JLabel("* Campos obligatorios");
        campos_obligatorios.setBounds(20,500,400,70);

        JLabel introduzca_dni = new JLabel("D.N.I.*");
        introduzca_dni.setBounds(150,100,200,70);
        Font auxFont = introduzca_dni.getFont();
        introduzca_dni.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_n_mat = new JLabel("Nº matrícula*");
        introduzca_n_mat.setBounds(150,150,200,70);
        auxFont = introduzca_n_mat.getFont();
        introduzca_n_mat.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_nombre = new JLabel("Nombre*");
        introduzca_nombre.setBounds(150,200,200,70);
        auxFont = introduzca_nombre.getFont();
        introduzca_nombre.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_apellidos = new JLabel("Apellidos*");
        introduzca_apellidos.setBounds(150,250,200,70);
        auxFont = introduzca_apellidos.getFont();
        introduzca_apellidos.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_grupo_clase = new JLabel("Grupo de clase*");
        introduzca_grupo_clase.setBounds(150,300,200,70);
        auxFont = introduzca_grupo_clase.getFont();
        introduzca_grupo_clase.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));


        campo_dni.setBounds(300,125, 150, 20);
        campo_n_mat.setBounds(300,175, 150, 20);
        campo_nombre.setBounds(300,225, 150, 20);
        campo_apellidos.setBounds(300,275, 150, 20);
        campo_grupo_clase.setBounds(300,325, 150, 20);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Cancelar",MENU_ALTA_ALUMNOS);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);

        JButtonOp botonOK = new JButtonOp("Aceptar",ALTA_ALUMNO);
        botonOK.setBounds(500, 500, 100, 30);
        botonOK.addActionListener(this);

        this.add(ruta);
        this.add(campos_obligatorios);
        this.add(introduzca_dni);
        this.add(introduzca_n_mat);
        this.add(introduzca_nombre);
        this.add(introduzca_apellidos);
        this.add(introduzca_grupo_clase);
        this.add(campo_dni);
        this.add(campo_n_mat);
        this.add(campo_nombre);
        this.add(campo_apellidos);
        this.add(campo_grupo_clase);
        this.add(botonHome);
        this.add(botonBack);
        this.add(botonOK);

    } // fin del método cargarElementos

    /** Método que captura los eventos del panel alta individual de alumnos
     *  y ejecuta el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {
        JButtonOp b = (JButtonOp) e.getSource();
        int ancho = FrameMenuPrincipal.ancho;
        int alto = FrameMenuPrincipal.alto;
        switch(b.getNumOperacion()){
            case ALTA_ALUMNO:
               GrupoClase grupo_clase = new GrupoClase(campo_grupo_clase.getText());

               Alumno alumno = new Alumno(campo_dni.getText(),
                                          campo_nombre.getText(),
                                          campo_apellidos.getText(),
                                          campo_n_mat.getText());
               try{
                   alumno.altaAlumno(grupo_clase);
                   JDialogOperacionOK jDialogOK = new JDialogOperacionOK(FrameMenuPrincipal.getFramePrincipal(),
                                                                         "El alumno se ha dado de alta correctamente",true);
                   jDialogOK.setVisible(true);
                   PanelMenuAltaAlumnos pSubmenuAltaAlumnos = new PanelMenuAltaAlumnos(ancho,alto);
                   FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuAltaAlumnos);
                   FrameMenuPrincipal.getFramePrincipal().setVisible(true);
               } catch (RuntimeException e1) {
                   JDialogOperacionFail jDialogFail = new JDialogOperacionFail(FrameMenuPrincipal.getFramePrincipal(),
                                                                               e1.getMessage(),true);
                   jDialogFail.setVisible(true);
               }
                break;
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
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuAltaAlumnosIndividual