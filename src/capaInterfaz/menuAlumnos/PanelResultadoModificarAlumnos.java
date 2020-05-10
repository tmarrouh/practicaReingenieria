
package capaInterfaz.menuAlumnos;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.ficheros.ActionListenerGuardarTxtAlumnos;
import capaInterfaz.listados.ListadoAlumno;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.Alumno;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel donde se muestra el resultado de una consulta
 *  para la modificación de alumnos.
 *
 * @author Confiencial
 */
public class PanelResultadoModificarAlumnos extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_MODIFICAR_ALUMNOS = 33;
    private static final int GENERAR_TXT = 3411; // ¿VALE EL MISMO QUE PARA CONSULTA?
    private static final int GUARDAR_MODIFICACIONES_ALUMNOS = 3311;
    private static final long serialVersionUID = 1L;

    private int x = 20,y = 140;
    private final int INCREMENTOX=140;
    private final int INCREMENTOY=30;

    private ArrayList<JTextField> arraynombre = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayapellidos = new ArrayList<JTextField>();
    private ArrayList<JTextField> arraydni = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayn_mat = new ArrayList<JTextField>();
    private ArrayList<JTextField> arraygrupo_clase = new ArrayList<JTextField>();

    private List<ListadoAlumno> resultado_consulta = new ArrayList<ListadoAlumno>();
    private JButtonOp botonSaveChanges, botonBack;
    private JLabel no_resul = new JLabel();

    /** Crea e inicializa un nuevo PanelResultadoModificarAlumnos.
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoModificarAlumnos(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);
        this.setPreferredSize(new Dimension(ancho,alto));

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {

        JLabel ruta = new JLabel("MENU PRINCIPAL>Alumnos>Modificar>Resultado de la búsqueda");
        ruta.setBounds(20,20,400,70);

        JLabel n_mat = new JLabel("Nº matrícula");
        n_mat.setBounds(20,80,100,70);

        JLabel dni = new JLabel("DNI");
        dni.setBounds(160,80,100,70);

        JLabel nombre = new JLabel("Nombre");
        nombre.setBounds(300,80,100,70);

        JLabel apellidos = new JLabel("Apellidos");
        apellidos.setBounds(440,80,100,70);

        JLabel grupo_clase = new JLabel("Grupo de clase");
        grupo_clase.setBounds(580,80,100,70);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        botonSaveChanges = new JButtonOp("Guardar",GUARDAR_MODIFICACIONES_ALUMNOS);
        botonSaveChanges.setBounds(500, 500, 100, 30);
        botonSaveChanges.addActionListener(this);

        botonBack = new JButtonOp("Atrás",MENU_MODIFICAR_ALUMNOS);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);


        this.add(ruta);
        this.add(n_mat);
        this.add(dni);
        this.add(nombre);
        this.add(apellidos);
        this.add(grupo_clase);
        this.add(botonHome);
        this.add(botonSaveChanges);
        this.add(botonBack);
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel resultado modificar de alumnos
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
            case MENU_MODIFICAR_ALUMNOS:
                this.resetear();
                PanelMenuModificarAlumnos pSubmenuModificarAlumnos = new PanelMenuModificarAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuModificarAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case GUARDAR_MODIFICACIONES_ALUMNOS:
                try{
                    int tam = resultado_consulta.size();
                    String dni_antiguo[] = new String[tam];
                    for (int i=0; i< tam; i++){
                        dni_antiguo[i] = resultado_consulta.get(i).getDNI();
                    }
                    resultado_consulta.clear();
                    ListadoAlumno alumnos;
                    Alumno alumno = new Alumno();
                    for (int i = 0; i < tam; i++){
                        alumnos = new ListadoAlumno(this.arrayn_mat.get(i).getText(),
                                                   this.arraydni.get(i).getText(),
                                                   this.arraynombre.get(i).getText(),
                                                   this.arrayapellidos.get(i).getText(),
                                                   this.arraygrupo_clase.get(i).getText());

                        resultado_consulta.add(alumnos);
                    }
                    alumno.actualizarAlumnos(resultado_consulta, dni_antiguo);
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
            this.add(no_resul);
        } else {

            resultado_consulta = resultado;
            System.out.println("Tamaño 1: " + resultado.size());
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
            System.out.println("Tamaño 2: " + resultado.size());
            this.modificarBoton(resultado.size());
            this.aumentarTamanio(resultado.size());
        }
    } // fin del método mostrarResultados

    /** Método que añade una fila al resultado de la consulta.
     *
     * @param n_mat matrícula del alumno
     * @param dni dni del alumno
     * @param nombre nombre del alumno
     * @param apellidos apellidos del alumno
     * @param grupo_clase grupo de clase del alumno
     */
    private void aniadir(int numreg, String n_mat, String dni, String nombre,
                         String apellidos, String grupo_clase) {

        JTextField aux = new JTextField(n_mat);
	aux.setBounds(x, y+ (INCREMENTOY*numreg), 140, 20);
	arrayn_mat.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(dni);
	aux.setBounds(x + (INCREMENTOX), y+ (INCREMENTOY*numreg), 140, 20);
        arraydni.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(nombre));
	aux.setBounds(x + (INCREMENTOX*2), y+ (INCREMENTOY*numreg), 140, 20);
        arraynombre.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(apellidos));
	aux.setBounds(x + (INCREMENTOX*3), y+ (INCREMENTOY*numreg), 140, 20);
        arrayapellidos.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(String.valueOf(grupo_clase));
	aux.setBounds(x + (INCREMENTOX*4), y+ (INCREMENTOY*numreg), 140, 20);
        arraygrupo_clase.add(numreg, aux);
	this.add(aux);
    } // fin del método aniadir


    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     */
    public void resetear(){
        for (int i=0;i<arraynombre.size();i++){
            this.remove(arraynombre.get(i));
            this.remove(arrayapellidos.get(i));
            this.remove(arrayn_mat.get(i));
            this.remove(arraydni.get(i));
            this.remove(arraygrupo_clase.get(i));
        }
        arraynombre.clear();
        arrayapellidos.clear();
        arrayn_mat.clear();
        arraydni.clear();
        arraygrupo_clase.clear();

        resultado_consulta.clear();

        this.setLayout(null);
        this.setBounds(0,0,800,600);
        this.setPreferredSize(new Dimension(800,600));

        no_resul.setText(" ");
        botonSaveChanges.setBounds(500, 500, 100, 30);
        botonBack.setBounds(640, 500, 100, 30);
    } // fin del método resetear

    /** Método que recoloca los botones "Guardar" y "Cancelar" en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void modificarBoton(int num_filas) {
        if ((y+(INCREMENTOY*num_filas) > 500)) {
            botonSaveChanges.setLocation(500, y+(INCREMENTOY*num_filas));
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

    public ArrayList<JTextField> getArrayGrupoClase() {
        return this.arraygrupo_clase;
    }

} // fin de la clase PanelResultadoModificarAlumnos