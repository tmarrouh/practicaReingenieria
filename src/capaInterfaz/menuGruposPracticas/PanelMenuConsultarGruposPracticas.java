
package capaInterfaz.menuGruposPracticas;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.listados.ListadoGrupoPractica;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.GrupoPractica;
import capaLogicaNegocio.Profesor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/** Clase que gestiona el panel del menú para consultar grupos de prácticas.
 *
 * @author Confiencial
 */
public class PanelMenuConsultarGruposPracticas extends JPanel implements ActionListener {

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_GRUPOS_PRACTICAS = 5;
    private static final int LISTADO_CONSULTAR_GRUPOS_PRACTICAS = 541;
    private static final int FRAME_ESCOGER_TUTOR = 512;

    /** Código del profesor tutor escogido. Inicialmente es -1*/
    public static int cod_tutor = -1;
    private static Profesor tutor = null;

    private JTextField campo_cod_grupo ;
    private JTextField campo_dni_alumno;
    private JTextField campo_tutor;


    /** Crea e inicializa un nuevo FrameEscogerTutor
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuConsultarGruposPracticas(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {
        JLabel ruta = new JLabel("MENU PRINCIPAL>Grupos de prácticas>Consultar");
        ruta.setBounds(20,20,400,70);

        JLabel introduzca_codigo_grupo = new JLabel("Código de grupo");
        introduzca_codigo_grupo.setBounds(150,150,200,70);
        Font auxFont = introduzca_codigo_grupo.getFont();
        introduzca_codigo_grupo.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_dni_alumno = new JLabel("D.N.I.");
        introduzca_dni_alumno.setBounds(150,200,200,70);
        auxFont = introduzca_dni_alumno.getFont();
        introduzca_dni_alumno.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        JLabel introduzca_tutor = new JLabel("Tutor/a");
        introduzca_tutor.setBounds(150,250,200,70);
        auxFont = introduzca_tutor.getFont();
        introduzca_tutor.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        campo_cod_grupo = new JTextField();
        campo_cod_grupo.setBounds(300,175, 150, 20);

        campo_dni_alumno = new JTextField();
        campo_dni_alumno.setBounds(300,225, 150, 20);

        campo_tutor = new JTextField();
        campo_tutor.setBounds(300,275, 150, 20);
        campo_tutor.setText("       <Escoja un tutor>");
        campo_tutor.setEditable(false);
        
        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonSearch = new JButtonOp("Buscar",LISTADO_CONSULTAR_GRUPOS_PRACTICAS);
        botonSearch.setBounds(475, 220, 100, 30);
        botonSearch.addActionListener(this);

        JButtonOp botonChooseTeacher = new JButtonOp("Escoger",FRAME_ESCOGER_TUTOR);
        botonChooseTeacher.setBounds(475, 270, 100, 30);
        botonChooseTeacher.addActionListener(this);
        
        JButtonOp botonBack = new JButtonOp("Atrás",MENU_GRUPOS_PRACTICAS);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(introduzca_codigo_grupo);
        this.add(introduzca_dni_alumno);
        this.add(introduzca_tutor);
        this.add(campo_cod_grupo);
        this.add(campo_dni_alumno);
        this.add(campo_tutor);
        this.add(botonHome);
        this.add(botonSearch);
        this.add(botonChooseTeacher);
        this.add(botonBack);
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel consultar grupos de prácticas y ejecuta
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
                PanelMenuPrincipal pSubmenuPrincipal = new PanelMenuPrincipal(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuPrincipal);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_GRUPOS_PRACTICAS:
                PanelMenuGruposPracticas pSubmenuGruposPracticas = new PanelMenuGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuGruposPracticas);
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
            case LISTADO_CONSULTAR_GRUPOS_PRACTICAS:
                try{
                    String cod_grupo = campo_cod_grupo.getText();
                    String dni = campo_dni_alumno.getText();
                    GrupoPractica grupo = new GrupoPractica(cod_grupo, this.tutor); 
                    
                    List<ListadoGrupoPractica> resultado = grupo.consultarGrupoPractica(dni);
                    
                    PanelResultadoConsultaGruposPracticas pResultadoConsultarGrupoPracticas = new PanelResultadoConsultaGruposPracticas(ancho,alto);
                    JScrollPane pResultadoConsultarGrupoPracticasConScroll = new JScrollPane(pResultadoConsultarGrupoPracticas);
                    pResultadoConsultarGrupoPracticasConScroll.setHorizontalScrollBar(null);
                    FrameMenuPrincipal.getFramePrincipal().setContentPane(pResultadoConsultarGrupoPracticasConScroll);
                    pResultadoConsultarGrupoPracticas.mostrarResultados(resultado);
                    FrameMenuPrincipal.getFramePrincipal().setVisible(true);                    
                } catch (NumberFormatException e1) {
                        JDialogOperacionFail fail = new JDialogOperacionFail(
                                            FrameMenuPrincipal.getFramePrincipal(),
                                            "Introduzca un código de grupo válido",true);
                        fail.setVisible(true);                    
                } catch (RuntimeException e2) {
                        JDialogOperacionFail fail = new JDialogOperacionFail(
                                            FrameMenuPrincipal.getFramePrincipal(),
                                            e2.getMessage(),true);
                        fail.setVisible(true);
                }


                break;

        }
    } // fin del método actionPerformed

} // fin de la clase PanelMenuConsultarGruposPracticas