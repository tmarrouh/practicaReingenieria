
package capaInterfaz.menuGruposPracticas;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.JDialogOperacionWarning;
import capaInterfaz.listados.ListadoGrupoPractica;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.GrupoPractica;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/** Clase que gestiona el panel donde se muestra el resultado de una baja de
 *  grupos de prácticas.
 *
 * @author Confiencial
 */
public class PanelResultadoBajaGrupoPracticas extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_BAJA_GRUPO_PRACTICAS = 22;
    private static final int BORRAR_GRUPO_PRACTICAS = 221;

    private static final long serialVersionUID = 1L;

    private int x = 20,y = 140;
    private final int INCREMENTOY=30;

    private ArrayList<JTextField> arrayTutor = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayAlumno1 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayDniAlumno1 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayAlumno2 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayDniAlumno2 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arraygrupo_practicas = new ArrayList<JTextField>();
    private ArrayList<JTextField> array_nota = new ArrayList<JTextField>();

    private ArrayList<JRadioButton> arrayradio = new ArrayList<JRadioButton>();

    private List<ListadoGrupoPractica> resultado_consulta = new ArrayList<ListadoGrupoPractica>();
    private JButtonOp botonDelete, botonBack;
    private JLabel no_resul = new JLabel();

    private ButtonGroup grupoRadios = new ButtonGroup();

    /** Crea e inicializa un nuevo PanelResultadoBajaGrupoPracticas
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoBajaGrupoPracticas(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);
        this.setPreferredSize(new Dimension(ancho,alto));

        this.cargarElementos();
    } // fin del constructor



    private void cargarElementos() {

        JLabel ruta = new JLabel("MENU PRINCIPAL>Grupos de prácticas>Baja>Resultado de la búsqueda");
        ruta.setBounds(20,20,500,70);

        JLabel cod_grupo = new JLabel("Código");
        cod_grupo.setBounds(40,80,40,70);

        JLabel tutor = new JLabel("Tutor/a");
        tutor.setBounds(80,80,140,70);

        JLabel dni_alumno1 = new JLabel("DNI");
        dni_alumno1.setBounds(220,80,80,70);

        JLabel nombre_y_apellidos_alumno1 = new JLabel("Alumno 1");
        nombre_y_apellidos_alumno1.setBounds(300,80,180,70);

        JLabel dni_alumno2 = new JLabel("DNI");
        dni_alumno2.setBounds(480,80,80,70);

        JLabel nombre_y_apellidos_alumno2 = new JLabel("Alumno 2");
        nombre_y_apellidos_alumno2.setBounds(560,80,160,70);

        JLabel nota= new JLabel("Nota");
        nota.setBounds(720,80,40,70);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        botonDelete = new JButtonOp("Borrar",BORRAR_GRUPO_PRACTICAS);
        botonDelete.setBounds(500, 500, 100, 30);
        botonDelete.addActionListener(this);

        botonBack = new JButtonOp("Cancelar",MENU_BAJA_GRUPO_PRACTICAS);
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
        this.add(botonHome);
        this.add(botonDelete);
        this.add(botonBack);

    } // fin del método cargarElementos


    /** Método que captura los eventos del panel resultado baja grupos de prácticas
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
            case MENU_BAJA_GRUPO_PRACTICAS:
                this.resetear();
                PanelMenuBajaGruposPracticas pSubmenuBajaGrupoPracticas = new PanelMenuBajaGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuBajaGrupoPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case BORRAR_GRUPO_PRACTICAS:
                int fila = this.filaSeleccionada();
                try {
                    if (fila != -1) {
                        JDialogOperacionWarning dialogWarning = new JDialogOperacionWarning(
                                                                    FrameMenuPrincipal.getFramePrincipal(),
                                                                    "Los alumnos se quedarán sin grupo",
                                                                    "¿Desea continuar?",
                                                                    true);
                        dialogWarning.setVisible(true);
                        if (dialogWarning.operacionOK){
                            String cod_grupo = this.arraygrupo_practicas.get(fila).getText();
                            String dni1 = this.arrayDniAlumno1.get(fila).getText();
                            String dni2 = this.arrayDniAlumno2.get(fila).getText();
                            int cod = Integer.parseInt(cod_grupo);
                            GrupoPractica grupo = new GrupoPractica(cod);
                            System.out.println("Grupo "+cod+" fila "+fila);
                            
                            System.out.println(" Hola 1");
                            grupo.bajaGrupoPractica(dni1, dni2);
                            System.out.println(" Hola 2");
                            this.BorrarFilaSeleccionada(fila);
                            JDialogOperacionOK ok = new JDialogOperacionOK(
                                                FrameMenuPrincipal.getFramePrincipal(),
                                                "El grupo ha sido dado de baja",
                                                true);
                            ok.setVisible(true);
                            fila = -1;
                        }
                    } else {
                        JDialogOperacionFail fail = new JDialogOperacionFail(
                                            FrameMenuPrincipal.getFramePrincipal(),
                                            "Seleccione una fila",true);
                        fail.setVisible(true);
                    }
                } catch (RuntimeException e1) {
                    JDialogOperacionFail fail = new JDialogOperacionFail(
                                                FrameMenuPrincipal.getFramePrincipal(),
                                                e1.getMessage(),
                                                true);
                    fail.setVisible(true);
                    fila = -1;
                    System.out.println(e1.getMessage());
                }
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
            this.botonDelete.setEnabled(false);
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
     * @param Dni_Alumno1 Nº de matrícula del alumno 1 del grupo
     * @param alumno1EnString Nombre y apellidos del alumno 1 del grupo
     * @param Dni_Alumno2 Nº de matrícula del alumno 2 del grupo
     * @param alumno2EnString Nombre y apellidos del alumno 2 del grupo
     * @param nota nota del grupo de prácticas
     */
    private void aniadir(int numreg, String codGrupo, String tutor, String Dni_Alumno1,
                        String alumno1EnString, String Dni_Alumno2, String alumno2EnString,
                        String nota) {

	JRadioButton radio = new JRadioButton();
	radio.setBounds(x, y+ (INCREMENTOY*numreg), 20, 20);
	grupoRadios.add(radio);
	arrayradio.add(radio);
	this.add(radio);

        JTextField aux = new JTextField(codGrupo);
	aux.setBounds(40, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
	this.arraygrupo_practicas.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(tutor);
	aux.setBounds(80, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        this.arrayTutor.add(numreg, aux);
	this.add(aux);

        aux = new JTextField(String.valueOf(Dni_Alumno1));
	aux.setBounds(220, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
        this.arrayDniAlumno1.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(alumno1EnString));
	aux.setBounds(300, y+ (INCREMENTOY*numreg), 180, 20);
	aux.setEditable(false);
        this.arrayAlumno1.add(numreg, aux);
	this.add(aux);

	

        if (Dni_Alumno2 != null) {
            aux = new JTextField(Dni_Alumno2);
        } else {
            aux = new JTextField("");
        }
        aux.setBounds(480, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
        this.arrayDniAlumno2.add(numreg, aux);
	this.add(aux);


        if (alumno2EnString != null) {
            aux = new JTextField(alumno2EnString);
        } else {
            aux = new JTextField("");
        }
        aux.setBounds(560, y+ (INCREMENTOY*numreg), 160, 20);
	aux.setEditable(false);
        this.arrayAlumno2.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(nota));
	aux.setBounds(720, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
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

            this.remove(arrayradio.get(i));
        }
        arraygrupo_practicas.clear();
        arrayTutor.clear();
        arrayDniAlumno1.clear();
        arrayAlumno1.clear();
        arrayDniAlumno2.clear();
        arrayAlumno2.clear();
        array_nota.clear();
        arrayradio.clear();

        resultado_consulta.clear();

        this.setLayout(null);
        this.setBounds(0,0,800,600);
        this.setPreferredSize(new Dimension(800,600));

        no_resul.setText(" ");
        botonDelete.setBounds(500, 500, 100, 30);
        botonBack.setBounds(640, 500, 100, 30);
        botonDelete.setEnabled(true);
    } // fin del método resetear


    /** Método que recoloca los botones "Borrar" y "Cancelar" en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void modificarBoton(int num_filas) {
        if ((y+(INCREMENTOY*num_filas) > 500)) {
            botonBack.setLocation(640, y+(INCREMENTOY*num_filas));
            botonDelete.setLocation(500, y+(INCREMENTOY*num_filas));
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

    
    /** Método que elimina del interfaz la fila seleccionada y
     *  actualiza la interfaz.
	 *
     * @param numreg nº de fila que se quiere borrar.
     */
    public void BorrarFilaSeleccionada(int numreg){
        this.remove(arraygrupo_practicas.get(numreg));
        arraygrupo_practicas.remove(numreg);
        this.remove(arrayTutor.get(numreg));
        arrayTutor.remove(numreg);
        this.remove(arrayDniAlumno1.get(numreg));
        arrayDniAlumno1.remove(numreg);
        this.remove(arrayAlumno1.get(numreg));
        arrayAlumno1.remove(numreg);
        this.remove(arrayDniAlumno2.get(numreg));
        arrayDniAlumno2.remove(numreg);
        this.remove(arrayAlumno2.get(numreg));
        arrayAlumno2.remove(numreg);
        this.remove(array_nota.get(numreg));
        array_nota.remove(numreg);
        grupoRadios.remove(arrayradio.get(numreg));
        this.remove(arrayradio.get(numreg));
        arrayradio.remove(numreg);

        Point punto;

        for (int i = numreg; i<arraygrupo_practicas.size();i++){

                punto = arraygrupo_practicas.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arraygrupo_practicas.get(i).setLocation(punto);

                punto = arrayTutor.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayTutor.get(i).setLocation(punto);

                punto = arrayDniAlumno1.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayDniAlumno1.get(i).setLocation(punto);

                punto = arrayAlumno1.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayAlumno1.get(i).setLocation(punto);

                punto = arrayDniAlumno2.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayDniAlumno2.get(i).setLocation(punto);

                punto = arrayAlumno2.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayAlumno2.get(i).setLocation(punto);

                punto = array_nota.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                array_nota.get(i).setLocation(punto);

                punto = arrayradio.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayradio.get(i).setLocation(punto);
        }
        punto = botonDelete.getLocation();
        punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
        botonDelete.setLocation(punto);

        punto = botonBack.getLocation();
        punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
        botonBack.setLocation(punto);

        this.aumentarTamanio(arraygrupo_practicas.size());
    } // fin del método BorrarFilaSeleccionada



    /** Método que devuelve el nº de fila seleccionada por el usuario.
     *
     * @return Devuelve el nº de fila seleccionada.
     *         Si el usuario no ha seleccionado ninguna fila, devolverá -1.
     */
    public int filaSeleccionada(){
	int i=0;

	while (i<arrayradio.size()){
           if (arrayradio.get(i).isSelected()) {
                return i;
            }
            i++;
	}
	return -1;
    } // fin del método filaSeleccionada

} // fin de la clase PanelResultadoBajaGrupoPracticas