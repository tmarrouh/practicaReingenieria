
package capaInterfaz.menuGruposPracticas;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.GrupoPractica;
import capaLogicaNegocio.Profesor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel de alta de grupos de prácticas.
 *
 * @author Confiencial
 */
public class PanelMenuAltaGruposPracticas extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_GRUPOS_PRACTICAS = 5;
    private static final int DAR_ALTA_GRUPO_PRACTICA = 511;
    private static final int FRAME_ESCOGER_TUTOR = 512;

    private static Profesor tutor;

    /** Código del profesor tutor escogido. Inicialmente es -1*/
    public static int cod_tutor = -1;

    private JTextField campo_tutor;
    private JTextField campo_cod_grupo;
    private JTextField campo_dni_alumno1;
    private JTextField campo_dni_alumno2;
    private JTextField campo_nota;


    /** Crea e inicializa un nuevo PanelMenuAltaGruposPracticas
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuAltaGruposPracticas(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor


    private void cargarElementos(){
        JLabel ruta = new JLabel("MENU PRINCIPAL>Grupos de prácticas>Alta");
        ruta.setBounds(20,20,400,70);

        JLabel campos_obligatorios = new JLabel("* Campos obligatorios");
        campos_obligatorios.setBounds(20,500,400,70);

        JLabel introduzca_cod_grupo = new JLabel("Código de grupo*");
        introduzca_cod_grupo.setBounds(150,100,200,70);
        Font auxFont = introduzca_cod_grupo.getFont();
        introduzca_cod_grupo.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_dni_alumno1 = new JLabel("D.N.I. del alumno 1*");
        introduzca_dni_alumno1.setBounds(150,150,200,70);
        auxFont = introduzca_dni_alumno1.getFont();
        introduzca_dni_alumno1.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_dni_alumno2 = new JLabel("D.N.I. del alumno 2");
        introduzca_dni_alumno2.setBounds(150,200,200,70);
        auxFont = introduzca_dni_alumno2.getFont();
        introduzca_dni_alumno2.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_tutor = new JLabel("Tutor/a*");
        introduzca_tutor.setBounds(150,250,200,70);
        auxFont = introduzca_tutor.getFont();
        introduzca_tutor.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_nota = new JLabel("Nota");
        introduzca_nota.setBounds(150,300,200,70);
        auxFont = introduzca_nota.getFont();
        introduzca_nota.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));


        campo_cod_grupo = new JTextField();
        campo_cod_grupo.setBounds(300,125, 150, 20);

        campo_dni_alumno1 = new JTextField();
        campo_dni_alumno1.setBounds(300,175, 150, 20);

        campo_dni_alumno2 = new JTextField();
        campo_dni_alumno2.setBounds(300,225, 150, 20);

        campo_tutor = new JTextField();
        campo_tutor.setBounds(300,275, 150, 20);
        campo_tutor.setText("       <Escoja un tutor>");
        campo_tutor.setEditable(false);
        
        campo_nota = new JTextField();
        campo_nota.setBounds(300,325, 150, 20);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonChooseTeacher = new JButtonOp("Escoger",FRAME_ESCOGER_TUTOR);
        botonChooseTeacher.setBounds(475, 270, 100, 30);
        botonChooseTeacher.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Cancelar",MENU_GRUPOS_PRACTICAS);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);

        JButtonOp botonOK = new JButtonOp("Aceptar",DAR_ALTA_GRUPO_PRACTICA);
        botonOK.setBounds(500, 500, 100, 30);
        botonOK.addActionListener(this);

        this.add(ruta);
        this.add(campos_obligatorios);
        this.add(introduzca_cod_grupo);
        this.add(introduzca_dni_alumno1);
        this.add(introduzca_dni_alumno2);
        this.add(introduzca_tutor);
        this.add(introduzca_nota);
        this.add(campo_cod_grupo);
        this.add(campo_dni_alumno1);
        this.add(campo_dni_alumno2);
        this.add(campo_tutor);
        this.add(campo_nota);
        this.add(botonHome);
        this.add(botonChooseTeacher);
        this.add(botonBack);
        this.add(botonOK); 

    } // fin del método cargarElementos


    /** Método que captura los eventos del panel alta grupos de prácticas y ejecuta
     *  el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {
        JButtonOp b = (JButtonOp) e.getSource();
        int ancho = FrameMenuPrincipal.ancho;
        int alto = FrameMenuPrincipal.alto;
        switch(b.getNumOperacion()){
            case MENU_PRINCIPAL:
                cod_tutor = -1;
                PanelMenuPrincipal pSubmenuPrincipal = new PanelMenuPrincipal(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuPrincipal);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case FRAME_ESCOGER_TUTOR:
                FrameEscogerTutor frameEscogerTutor = new FrameEscogerTutor(FrameMenuPrincipal.getFramePrincipal(),
                                                                            true);
                if (this.cod_tutor != -1){
                    tutor = new Profesor(cod_tutor);
                    tutor.obtenerDatos(cod_tutor);
                    campo_tutor.setText(tutor.getNombre()+" "+tutor.getApellidos());
                }
                break;
            case MENU_GRUPOS_PRACTICAS:
                cod_tutor = -1;
                PanelMenuGruposPracticas pSubmenuGruposPracticas = new PanelMenuGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case DAR_ALTA_GRUPO_PRACTICA:
                try {
                    String cod_grupo = this.campo_cod_grupo.getText();
                    String dni_alumno1 = this.campo_dni_alumno1.getText();
                    String dni_alumno2 = this.campo_dni_alumno2.getText();
                    String nota = this.campo_nota.getText();
                    tutor = new Profesor(cod_tutor);

                    GrupoPractica grupo_practica = new GrupoPractica();
                    grupo_practica.validarCampos(cod_grupo, dni_alumno1, dni_alumno2, cod_tutor, nota);

                    grupo_practica.altaGrupoPractica(cod_grupo, dni_alumno1, dni_alumno2, tutor, nota);

                    JDialogOperacionOK dialogOperacionOK = new JDialogOperacionOK(
                                                                FrameMenuPrincipal.getFramePrincipal(),
                                                                "El grupo de prácticas ha sido dado de alta",
                                                                true);
                    dialogOperacionOK.setVisible(true);
                    cod_tutor = -1;
                } catch (RuntimeException e1) {
                    JDialogOperacionFail dialogOperacionFail = new JDialogOperacionFail(
                                                                FrameMenuPrincipal.getFramePrincipal(),
                                                                e1.getMessage(),true);
                    dialogOperacionFail.setVisible(true);
                }
                break;
        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuAltaGruposPracticas