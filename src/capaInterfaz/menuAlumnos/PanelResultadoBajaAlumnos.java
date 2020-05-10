
package capaInterfaz.menuAlumnos;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.JDialogOperacionWarning;
import capaInterfaz.listados.ListadoAlumno;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.Alumno;
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

/** Clase que gestiona el panel donde se muestra el resultado de una baja de alumnos.
 *
 * @author Confiencial
 */
public class PanelResultadoBajaAlumnos extends JPanel implements ActionListener{


    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_BAJA_ALUMNOS = 32;
    private static final int BORRAR_ALUMNO = 321;

    private static final long serialVersionUID = 1L;

    private int x = 20,y = 140;
    private final int INCREMENTOX=140;
    private final int INCREMENTOY=30;

    private ArrayList<JTextField> arraynombre = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayapellidos = new ArrayList<JTextField>();
    private ArrayList<JTextField> arraydni = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayn_mat = new ArrayList<JTextField>();
    private ArrayList<JTextField> arraygrupo_clase = new ArrayList<JTextField>();
    private ArrayList<JRadioButton> arrayradio = new ArrayList<JRadioButton>();

    private List<ListadoAlumno> resultado_consulta = new ArrayList<ListadoAlumno>();
    private JButtonOp botonDelete, botonBack;
    private JLabel no_resul = new JLabel();

    private ButtonGroup grupoRadios = new ButtonGroup();


    /** Crea e inicializa un nuevo PanelResultadoBajaAlumnos.
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoBajaAlumnos(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);
        this.setPreferredSize(new Dimension(ancho,alto));

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {

        JLabel ruta = new JLabel("MENU PRINCIPAL>Alumnos>Baja>Resultado de la búsqueda");
        ruta.setBounds(20,20,400,70);

        JLabel n_mat = new JLabel("Nº matrícula");
        n_mat.setBounds(40,80,100,70);

        JLabel dni = new JLabel("DNI");
        dni.setBounds(180,80,100,70);

        JLabel nombre = new JLabel("Nombre");
        nombre.setBounds(320,80,100,70);

        JLabel apellidos = new JLabel("Apellidos");
        apellidos.setBounds(460,80,100,70);

        JLabel grupo_clase = new JLabel("Grupo de clase");
        grupo_clase.setBounds(600,80,100,70);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        botonDelete = new JButtonOp("Borrar",BORRAR_ALUMNO);
        botonDelete.setBounds(500, 500, 100, 30);
        botonDelete.addActionListener(this);

        botonBack = new JButtonOp("Cancelar",MENU_BAJA_ALUMNOS);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(n_mat);
        this.add(dni);
        this.add(nombre);
        this.add(apellidos);
        this.add(grupo_clase);
        this.add(botonHome);
        this.add(botonDelete);
        this.add(botonBack);
        
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel resultado baja de alumnos
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
            case MENU_BAJA_ALUMNOS:
                this.resetear();
                PanelMenuBajaAlumnos pSubmenuBajaAlumnos = new PanelMenuBajaAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuBajaAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case BORRAR_ALUMNO:
                int fila = this.filaSeleccionada();
                if (fila != -1) {
                    JDialogOperacionWarning dialogWarning = new JDialogOperacionWarning(FrameMenuPrincipal.getFramePrincipal(),
                                                                                        "Se perderán TODOS los datos del alumno",
                                                                                        "¿Desea continuar?",
                                                                                        true);
                    dialogWarning.setVisible(true);
                    if (dialogWarning.operacionOK){
                        String dni = this.arraydni.get(fila).getText();
                        String num_matricula = this.arrayn_mat.get(fila).getText();
                        Alumno alumno = new Alumno(num_matricula, dni);
                        try {
                            alumno.bajaAlumno();
                            this.BorrarFilaSeleccionada(fila);
                            JDialogOperacionOK ok = new JDialogOperacionOK(
                                                FrameMenuPrincipal.getFramePrincipal(),
                                                "El alumno ha sido dado de baja",
                                                true);
                            ok.setVisible(true);
                            fila = -1;
                        } catch (RuntimeException e1) {
                            JDialogOperacionFail fail = new JDialogOperacionFail(
                                                FrameMenuPrincipal.getFramePrincipal(),
                                                e1.getMessage(),
                                                true);
                            fail.setVisible(true);
                            fila = -1;
                            System.out.println(e1.getMessage());
                        }
                    }
                } else {
                    JDialogOperacionFail fail = new JDialogOperacionFail(
                                        FrameMenuPrincipal.getFramePrincipal(),
                                        "Seleccione una fila",true);
                    fail.setVisible(true);
                }
                break;
        }
    } // fin del método actionPerformed


    /** Método que muestra al usuario los resultados de la consulta realizada.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado estructura que contiene el resultado de la consulta.
     */
    public void mostrarResultados(List<ListadoAlumno> resultado) {
        if (resultado.isEmpty()) {
            no_resul.setText("La consulta realizada no ha producido resultados");
            no_resul.setBounds(300,250,300,70);
            this.botonDelete.setEnabled(false);
            this.add(no_resul);
        } else {
            resultado_consulta = resultado;

            for (int i = 0; i < resultado.size(); i++){
		this.aniadir(i,resultado.get(i).getNumMatricula(),
                            resultado.get(i).getDNI(),resultado.get(i).getNombre(),
                            resultado.get(i).getApellidos(), resultado.get(i).getGrupoClase());
                System.out.print(resultado.get(i).getNumMatricula()+" ");
                System.out.print(resultado.get(i).getDNI()+" ");
                System.out.print(resultado.get(i).getNombre()+" ");
                System.out.print(resultado.get(i).getApellidos()+" ");
                System.out.print(resultado.get(i).getGrupoClase());
                System.out.println();
             }
            this.modificarBoton(resultado.size());
            this.aumentarTamanio(resultado.size());
        }
    } // fin del método mostrarResultados

    
    /** Método que añade una fila al resultado de la consulta.
     *
     * @param numreg nº de fila
     * @param n_mat matrícula del alumno
     * @param dni dni del alumno
     * @param nombre nombre del alumno
     * @param apellidos apellidos del alumno
     * @param grupo_clase grupo de clase del alumno
     */
    private void aniadir(int numreg, String n_mat, String dni, String nombre,
                         String apellidos, String grupo_clase) {


	JRadioButton radio = new JRadioButton();
	radio.setBounds(x, y+ (INCREMENTOY*numreg), 20, 20);
	grupoRadios.add(radio);
	arrayradio.add(radio);
	this.add(radio);


        JTextField aux = new JTextField(n_mat);
	aux.setBounds(x + 20, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
	arrayn_mat.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(dni);
	aux.setBounds(x + 20 + (INCREMENTOX), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arraydni.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(nombre));
	aux.setBounds(x + 20 + (INCREMENTOX*2), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arraynombre.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(apellidos));
	aux.setBounds(x + 20 + (INCREMENTOX*3), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arrayapellidos.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(grupo_clase));
	aux.setBounds(x + 20 + (INCREMENTOX*4), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arraygrupo_clase.add(numreg, aux);
	this.add(aux);
    } // fin del método aniadir


    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     *
     * Quita todos los campos de texto de la interfaz y vacia los arraylist.
     */
    public void resetear(){
        for (int i=0;i<arraynombre.size();i++){
            this.remove(arraynombre.get(i));
            this.remove(arrayapellidos.get(i));
            this.remove(arrayn_mat.get(i));
            this.remove(arraydni.get(i));
            this.remove(arraygrupo_clase.get(i));
            this.remove(arrayradio.get(i));
        }
        arraynombre.clear();
        arrayapellidos.clear();
        arrayn_mat.clear();
        arraydni.clear();
        arraygrupo_clase.clear();
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

    
    public void BorrarFilaSeleccionada(int numreg){
        this.remove(arraynombre.get(numreg));
        arraynombre.remove(numreg);
        this.remove(arrayapellidos.get(numreg));
        arrayapellidos.remove(numreg);
        this.remove(arraydni.get(numreg));
        arraydni.remove(numreg);
        this.remove(arrayn_mat.get(numreg));
        arrayn_mat.remove(numreg);
        this.remove(arraygrupo_clase.get(numreg));
        arraygrupo_clase.remove(numreg);
        grupoRadios.remove(arrayradio.get(numreg));
        this.remove(arrayradio.get(numreg));
        arrayradio.remove(numreg);

        Point punto;

        for (int i = numreg; i<arraynombre.size();i++){

                punto = arraynombre.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arraynombre.get(i).setLocation(punto);

                punto = arrayapellidos.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayapellidos.get(i).setLocation(punto);

                punto = arraydni.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arraydni.get(i).setLocation(punto);

                punto = arrayn_mat.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arrayn_mat.get(i).setLocation(punto);

                punto = arraygrupo_clase.get(i).getLocation();
                punto.setLocation(punto.getX(), punto.getY()-INCREMENTOY);
                arraygrupo_clase.get(i).setLocation(punto);

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

        this.aumentarTamanio(arraynombre.size());
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


    public ArrayList<JTextField> getArrayNombres() {
        return this.arraynombre;
    }

    public ArrayList<JTextField> getArrayApellidos() {
        return this.arrayapellidos;
    }

    public ArrayList<JTextField> getArrayDNI() {
        return this.arraydni;
    }

    public ArrayList<JTextField> getArrayN_Mat() {
        return this.arrayn_mat;
    }

} // fin de la clase PanelResultadoBajaAlumnos