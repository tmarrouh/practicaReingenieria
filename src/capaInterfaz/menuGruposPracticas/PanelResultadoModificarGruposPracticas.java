
package capaInterfaz.menuGruposPracticas;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.listados.ListadoGrupoPractica;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.GrupoPractica;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel donde se muestra el resultado de una
 *  modificación de grupos de prácticas.
 *
 * @author Confiencial
 */
public class PanelResultadoModificarGruposPracticas extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_MODIFICAR_GRUPO_PRACTICAS = 23;
    private static final int GUARDAR_MODIFICACIONES_GRUPOS_PRACTICAS = 2311;
    private static final long serialVersionUID = 1L;

    private int y = 140;
    private final int INCREMENTOY=30;

    private ArrayList<JTextField> arrayTutor = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayDniAlumno1 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayAlumno1 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayDniAlumno2 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayAlumno2 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arraygrupo_practicas = new ArrayList<JTextField>();
    private ArrayList<JTextField> array_nota = new ArrayList<JTextField>();

    private List<ListadoGrupoPractica> resultado_consulta = new ArrayList<ListadoGrupoPractica>();
    private JButtonOp botonBack, botonSaveChanges;
    private JLabel no_resul = new JLabel();


    /** Crea e inicializa un nuevo PanelResultadoModificarGruposPracticas
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoModificarGruposPracticas(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {

        JLabel ruta = new JLabel("MENU PRINCIPAL>Grupos de prácticas>Modificar>Resultado de la búsqueda");
        ruta.setBounds(20,20,500,70);

        JLabel cod_grupo = new JLabel("Código");
        cod_grupo.setBounds(20,80,40,70);

        JLabel tutor = new JLabel("Tutor/a");
        tutor.setBounds(60,80,140,70);

        JLabel dni_alumno1 = new JLabel("DNI");
        dni_alumno1.setBounds(200,80,80,70);

        JLabel nombre_y_apellidos_alumno1 = new JLabel("Alumno 1");
        nombre_y_apellidos_alumno1.setBounds(280,80,180,70);

        JLabel dni_alumno2 = new JLabel("DNI");
        dni_alumno2.setBounds(460,80,80,70);

        JLabel nombre_y_apellidos_alumno2 = new JLabel("Alumno 2");
        nombre_y_apellidos_alumno2.setBounds(540,80,160,70);

        JLabel nota= new JLabel("Nota");
        nota.setBounds(700,80,40,70);


        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        botonSaveChanges = new JButtonOp("Guardar",GUARDAR_MODIFICACIONES_GRUPOS_PRACTICAS);
        botonSaveChanges.setBounds(500, 500, 100, 30);
        botonSaveChanges.addActionListener(this);

        botonBack = new JButtonOp("Atrás",MENU_MODIFICAR_GRUPO_PRACTICAS);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(cod_grupo);
        this.add(tutor);
        this.add(dni_alumno1);
        this.add(nombre_y_apellidos_alumno1);
        this.add(dni_alumno2);
        this.add(nombre_y_apellidos_alumno2);
        this.add(nota);
        this.add(botonSaveChanges);
        this.add(botonHome);
        this.add(botonBack);
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel resultado modificar grupos de prácticas
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
                this.resetear();
                PanelMenuPrincipal pSubmenuPrincipal = new PanelMenuPrincipal(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuPrincipal);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case GUARDAR_MODIFICACIONES_GRUPOS_PRACTICAS:
                try{
                    int tam = resultado_consulta.size();
                    resultado_consulta.clear();
                    List<GrupoPractica> resultado_modificaciones = new ArrayList<GrupoPractica>();
                    GrupoPractica grupo = new GrupoPractica();
                    for (int i = 0; i < tam; i++){

                        grupo = new GrupoPractica(this.arraygrupo_practicas.get(i).getText(),
                                                  this.arrayDniAlumno1.get(i).getText(),
                                                  this.arrayDniAlumno2.get(i).getText(),
                                                  this.array_nota.get(i).getText());
                        resultado_modificaciones.add(grupo);
                    }
                    grupo.actualizarGruposPracticas(resultado_modificaciones);

                    JDialogOperacionOK messageOK = new JDialogOperacionOK(
                                        FrameMenuPrincipal.getFramePrincipal(),
                                        "Se ha actualizado la Base de Datos", true);
                    messageOK.setVisible(true);
                } catch (RuntimeException e1) {
                    JDialogOperacionFail messageFail = new JDialogOperacionFail(
                                        FrameMenuPrincipal.getFramePrincipal(),
                                        "Ha ocurrido un error al actualizar la Base de Datos", true);
                    messageFail.setVisible(true);
                    System.out.println(e1.getMessage());
                }   
            case MENU_MODIFICAR_GRUPO_PRACTICAS:
                this.resetear();
                PanelMenuModificarGruposPracticas pSubmenuModificarGruposPracticas = new PanelMenuModificarGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuModificarGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed



    /** Método que muestra al usuario los resultados de la consulta realizada.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado estructura que contiene el resultado de la consulta.
     */
    public void mostrarResultados(List<ListadoGrupoPractica> resultado) {

        if (resultado.isEmpty()) {
            no_resul.setText("La consulta realizada no ha producido resultados");
            no_resul.setBounds(300,250,300,70);
            this.add(no_resul);
        } else {
            System.out.println(resultado.size());
            resultado_consulta = resultado;
            String cod_grupo, tutor, dni1, alumno1, dni2, alumno2, nota;
  
            for (int i = 0; i < resultado.size(); i++){
                cod_grupo = resultado.get(i).getCodGrupo();
                tutor = resultado.get(i).getTutorEnString();
                dni1 = resultado.get(i).getAlumno1().getDNI();
                alumno1 = resultado.get(i).getAlumno1EnString();
                if (resultado.get(i).getAlumno2() != null) {
                    dni2 = resultado.get(i).getAlumno2().getDNI();
                    alumno2 = resultado.get(i).getAlumno2EnString();
                } else {
                    dni2 = null;
                    alumno2 = null;
                }
                nota = resultado.get(i).getNota();
		this.aniadir(i,cod_grupo, tutor, dni1, alumno1, dni2,
                             alumno2, nota);
                System.out.println(i);
                System.out.print(cod_grupo+" ");
                System.out.print(dni1+" ");
                System.out.print(alumno1+" ");
                System.out.print(dni2+" ");
                System.out.print(alumno2+" ");
                System.out.print(nota+" ");
                System.out.println();
             }
            this.modificarBoton(resultado.size());
            this.aumentarTamanio(resultado.size());
        }
    } // fin del método mostrarResultados



    /** Método que añade una fila al resultado de la consulta.
     *
     * @param numreg nº de fila
     * @param codGrupo código del grupo de prácticas
     * @param tutor Nombre y apellidos del tutor del grupo
     * @param N_Mat_Alumno1 Nº de matrícula del alumno 1 del grupo
     * @param alumno1EnString Nombre y apellidos del alumno 1 del grupo
     * @param Dni_Alumno2 Nº de matrícula del alumno 2 del grupo
     * @param alumno2EnString Nombre y apellidos del alumno 2 del grupo
     * @param nota nota del grupo de prácticas
     */
    private void aniadir(int numreg, String codGrupo, String tutor,
                        String Dni_Alumno1, String alumno1EnString, String Dni_Alumno2,
                        String alumno2EnString, String nota) {

        JTextField aux = new JTextField(codGrupo);
	aux.setBounds(20, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
	this.arraygrupo_practicas.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(tutor);
	aux.setBounds(60, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        this.arrayTutor.add(numreg, aux);
	this.add(aux);

        aux = new JTextField(String.valueOf(Dni_Alumno1));
	aux.setBounds(200, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(true);
        this.arrayDniAlumno1.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(alumno1EnString));
	aux.setBounds(280, y+ (INCREMENTOY*numreg), 180, 20);
	aux.setEditable(false);
        this.arrayAlumno1.add(numreg, aux);
	this.add(aux);



        if (Dni_Alumno2 != null) {
            aux = new JTextField(Dni_Alumno2);
        } else {
            aux = new JTextField("");
        }
        aux.setBounds(460, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(true);
        this.arrayDniAlumno2.add(numreg, aux);
	this.add(aux);


        if (alumno2EnString != null) {
            aux = new JTextField(alumno2EnString);
        } else {
            aux = new JTextField("");
        }
        aux.setBounds(540, y+ (INCREMENTOY*numreg), 160, 20);
	aux.setEditable(false);
        this.arrayAlumno2.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(nota));
	aux.setBounds(700, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(true);
        this.array_nota.add(numreg, aux);
	this.add(aux);
    } // fin del método aniadir


    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     *
     * Quita todos los campos de texto de la interfaz y vacia los arraylist.
     */
    public void resetear(){
        for (int i=0;i<arraygrupo_practicas.size();i++){
            this.remove(arraygrupo_practicas.get(i));
            this.remove(arrayTutor.get(i));
            this.remove(arrayDniAlumno1.get(i));
            this.remove(arrayAlumno1.get(i));
            this.remove(arrayDniAlumno2.get(i));
            this.remove(arrayAlumno2.get(i));
            this.remove(array_nota.get(i));
        }

        arraygrupo_practicas.clear();
        arrayTutor.clear();
        arrayDniAlumno1.clear();
        arrayAlumno1.clear();
        arrayDniAlumno2.clear();
        arrayAlumno2.clear();
        array_nota.clear();

        resultado_consulta.clear();

        this.setLayout(null);
        this.setBounds(0,0,800,600);
        this.setPreferredSize(new Dimension(800,600));

        no_resul.setText(" ");
        botonBack.setBounds(640, 500, 100, 30);
    } // fin del método resetear


    /** Método que recoloca los botones "Borrar" y "Cancelar" en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void modificarBoton(int num_filas) {
        if ((y+(INCREMENTOY*num_filas) > 500)) {
            botonBack.setLocation(640, y+(INCREMENTOY*num_filas));
        }
    } // fin del método modificarBoton


    /** Método que redefine el tamaño del panel en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void aumentarTamanio(int num_filas) {
	this.setPreferredSize(new Dimension(800,y + INCREMENTOY*num_filas + 70));
	this.updateUI();
    } // fin del método aumentarTamanio

} // fin de la clase PanelResultadoModificarGruposPracticas