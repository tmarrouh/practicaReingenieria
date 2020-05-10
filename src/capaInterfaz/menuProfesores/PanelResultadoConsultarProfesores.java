
package capaInterfaz.menuProfesores;

import capaInterfaz.JButtonOp;
import capaInterfaz.ficheros.ActionListenerGuardarTxtProfesores;
import capaInterfaz.listados.ListadoProfesor;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel donde se muestra el resultado de una consulta de profesores.
 *
 * @author Confiencial
 */
public class PanelResultadoConsultarProfesores extends JPanel implements ActionListener{
    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_CONSULTAR_PROFESORES = 43;
    private static final int GENERAR_TXT = 431;

    private static final long serialVersionUID = 1L;

    private int x = 20,y = 140;
    private final int INCREMENTOX=140;
    private final int INCREMENTOY=30;

    private ArrayList<Integer> arrayCodProfesor = new ArrayList<Integer>();
    private ArrayList<JTextField> arrayNombre = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayApellidos = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayGrupoclase1 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayGrupoclase2 = new ArrayList<JTextField>();

    private List<ListadoProfesor> resultado_consulta = new ArrayList<ListadoProfesor>();
    private JButtonOp botonBack, botonGenerarTxt;
    private JLabel no_resul = new JLabel();


    /** Crea e inicializa un nuevo PanelResultadoConsultarProfesores
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoConsultarProfesores(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);
        this.setPreferredSize(new Dimension(ancho,alto));

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {

        JLabel ruta = new JLabel("MENU PRINCIPAL>Profesores>Consultar>Resultado de la búsqueda");
        ruta.setBounds(20,20,400,70);

        JLabel nombre = new JLabel("Nombre");
        nombre.setBounds(20,80,100,70);

        JLabel apellidos = new JLabel("Apellidos");
        apellidos.setBounds(160,80,100,70);

        JLabel grupo_clase1 = new JLabel("Grupo de clase 1");
        grupo_clase1.setBounds(300,80,100,70);

        JLabel grupo_clase2 = new JLabel("Grupo de clase 2");
        grupo_clase2.setBounds(440,80,100,70);


        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        ImageIcon img_generar_txt = new ImageIcon(getClass().getResource("/capaInterfaz/images/create_txt_icon.jpg"));
        botonGenerarTxt = new JButtonOp("",img_generar_txt,GENERAR_TXT);
        botonGenerarTxt.setBounds(500, 40, 100, 40);
        botonGenerarTxt.addActionListener(new ActionListenerGuardarTxtProfesores(this));

        botonBack = new JButtonOp("Aceptar",MENU_CONSULTAR_PROFESORES);
        botonBack.setBounds(640, 500, 100, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(nombre);
        this.add(apellidos);
        this.add(grupo_clase1);
        this.add(grupo_clase2);
        this.add(botonHome);
        this.add(botonGenerarTxt);
        this.add(botonBack);
    } // fin del método cargarElementos



    /** Método que captura los eventos del panel resultado consultar profesores
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
            case MENU_CONSULTAR_PROFESORES:
                this.resetear();
                PanelMenuConsultarProfesores pSubmenuConsultarProfesores = new PanelMenuConsultarProfesores(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarProfesores);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed


    /** Método que muestra al usuario los resultados de la consulta realizada.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado estructura que contiene el resultado de la consulta.
     */
    public void mostrarResultados(List<ListadoProfesor> resultado) {
        if (resultado.isEmpty()) {
            no_resul.setText("La consulta realizada no ha producido resultados");
            no_resul.setBounds(300,250,300,70);
            this.add(no_resul);
        } else {
            resultado_consulta = resultado;
            System.out.println(resultado.size());
            for (int i = 0; i < resultado.size(); i++){

                System.out.println(resultado.get(i).getNombre());
		this.aniadir(i,resultado.get(i).getCodProfesor(),
                             resultado.get(i).getNombre(),
                             resultado.get(i).getApellidos(),
                             resultado.get(i).getGrupoClase1(),
                             resultado.get(i).getGrupoClase2());
                System.out.println();
             }
            this.modificarBoton(resultado.size());
            this.aumentarTamanio(resultado.size());
        }
    } // fin del método mostrarResultados


    /** Método que añade una fila al resultado de la consulta.
     *
     * @param numreg nº de fila
     * @param cod Código del profesor
     * @param nombre nombre del profesor
     * @param apellidos apellidos del profesor
     * @param grupo_clase1 Primer grupo de clase que imparte. Puede ser null.
     * @param grupo_clase2 Segundo grupo de clase que imparte. Puede ser null.
     */
    private void aniadir(int numreg, int cod, String nombre, String apellidos,
                         String grupo_clase1, String grupo_clase2) {


        arrayCodProfesor.add(numreg, cod);

        JTextField aux = new JTextField(nombre);
	aux.setBounds(x, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
	arrayNombre.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(apellidos);
	aux.setBounds(x + (INCREMENTOX), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arrayApellidos.add(numreg, aux);
	this.add(aux);

	if (this.noEstaVacio(grupo_clase1)) {
            aux = new JTextField(String.valueOf(grupo_clase1));
        } else {
            aux = new JTextField(String.valueOf(""));
        }
	aux.setBounds(x + (INCREMENTOX*2), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arrayGrupoclase1.add(numreg, aux);
	this.add(aux);

	if (this.noEstaVacio(grupo_clase2)) {
            aux = new JTextField(String.valueOf(grupo_clase2));
        } else {
            aux = new JTextField(String.valueOf(""));
        }
	aux.setBounds(x + (INCREMENTOX*3), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arrayGrupoclase2.add(numreg, aux);
	this.add(aux);

    } // fin del método aniadir


    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     *
     * Quita todos los campos de texto de la interfaz y vacia los arraylist.
     */
    public void resetear(){
        for (int i=0;i<arrayNombre.size();i++){
            System.out.println(arrayNombre.size());
            System.out.println(arrayCodProfesor.size());
            this.remove(arrayNombre.get(i));
     //     this.remove(arrayCodProfesor.get(i));
            this.remove(arrayApellidos.get(i));
            this.remove(arrayGrupoclase1.get(i));
            this.remove(arrayGrupoclase2.get(i));
        }
        arrayCodProfesor.clear();
        arrayNombre.clear();
        arrayApellidos.clear();
        arrayGrupoclase1.clear();
        arrayGrupoclase2.clear();


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

    

    /** Método que comprueba si una cadena no está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si no está vacía.
     *
     * @return TRUE si la cadena no está vacía.
     *         FALSE en caso contrario.
     */
    private boolean noEstaVacio(String cadena) {
	return cadena != null && !"".equals(cadena);
    }

    public ArrayList<JTextField> getArrayNombres() {
        return this.arrayNombre;
    }

    public ArrayList<JTextField> getArrayApellidos() {
        return this.arrayApellidos;
    }


    public ArrayList<JTextField> getArrayGrupoClase1() {
        return this.arrayGrupoclase1;
    }


    public ArrayList<JTextField> getArrayGrupoClase2() {
        return this.arrayGrupoclase2;
    }

} // fin de la clase PanelResultadoConsultarProfesores