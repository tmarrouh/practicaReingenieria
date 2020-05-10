
package capaInterfaz.MenuHistoricoAlumnos;

import capaInterfaz.JButtonOp;
import capaInterfaz.ficheros.ActionListenerGuardarTxtHistoricoAlumnos;
import capaInterfaz.listados.ListadoEvaluacion;
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

/** Clase que gestiona el panel donde se muestra el resultado de una consulta del
 *  histórico de alumnos.
 *
 * @author Confiencial
 */
public class PanelResultadoConsultarHistoricoAlumnos extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_CONSULTAR_HISTORICO = 52;
    private static final int GENERAR_TXT = 5111;
    private static final long serialVersionUID = 1L;

    private int x = 20,y = 140;
    private final int INCREMENTOX=140;
    private final int INCREMENTOY=30;

    private ArrayList<JTextField> arrayCurso = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayConvocatoria = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayDniAlumno = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNMatAlumno = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayAlumno = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_Ex = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_Pr = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_Final = new ArrayList<JTextField>();

    private List<ListadoEvaluacion> resultado_consulta = new ArrayList<ListadoEvaluacion>();
    private JButtonOp botonOK, botonGenerarTxt;
    private JLabel no_resul = new JLabel();

    /** Crea e inicializa el panel correspondiente al resultado de la consulta
     *  realizada en el menú Consultar Histórico de Alumnos.
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoConsultarHistoricoAlumnos(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {

        JLabel ruta = new JLabel("MENU PRINCIPAL>Histórico de alumnos>Consultar>Resultado de la búsqueda");
        ruta.setBounds(20,20,500,70);

        JLabel curso = new JLabel("Curso");
        curso.setBounds(20,80,80,70);

        JLabel convocatoria = new JLabel("Convocatoria");
        convocatoria.setBounds(100,80,140,70);

        JLabel dni_alumno = new JLabel("DNI");
        dni_alumno.setBounds(240,80,80,70);

        JLabel n_mat = new JLabel("N Mat");
        n_mat.setBounds(320,80,80,70);

        JLabel nombre_y_apellidos_alumno = new JLabel("Nombre y apellidos");
        nombre_y_apellidos_alumno.setBounds(400,80,140,70);

        JLabel nota_ex = new JLabel("Teoría");
        nota_ex.setBounds(540,80,80,70);

        JLabel nota_pr = new JLabel("Práctica");
        nota_pr.setBounds(620,80,80,70);

        JLabel nota_final = new JLabel("Final");
        nota_final.setBounds(700,80,80,70);


        ImageIcon img_generar_txt = new ImageIcon(getClass().getResource("/capaInterfaz/images/create_txt_icon.jpg"));
        botonGenerarTxt = new JButtonOp("",img_generar_txt,GENERAR_TXT);
        botonGenerarTxt.setBounds(500, 40, 110, 40);
        botonGenerarTxt.addActionListener(new ActionListenerGuardarTxtHistoricoAlumnos(this));

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        botonOK = new JButtonOp("Aceptar",MENU_CONSULTAR_HISTORICO);
        botonOK.setBounds(640, 500, 100, 30);
        botonOK.addActionListener(this);

        this.add(ruta);
        this.add(curso);
        this.add(convocatoria);
        this.add(dni_alumno);
        this.add(n_mat);
        this.add(nombre_y_apellidos_alumno);
        this.add(nota_ex);
        this.add(nota_pr);
        this.add(nota_final);
        this.add(botonGenerarTxt);
        this.add(botonHome);
        this.add(botonOK);
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel del resultado de la consulta
     *  de histórico de alumnos y ejecuta el código correspondiente a cada evento.
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
            case MENU_CONSULTAR_HISTORICO:
                this.resetear();
                PanelMenuConsultarHistoricoAlumnos pSubmenuConsultarHistoricoAlumnos = new PanelMenuConsultarHistoricoAlumnos(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarHistoricoAlumnos);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
        }
    } // fin del método actionPerformed



    /** Método que muestra al usuario los resultados de la consulta realizada.
     *  Si ocurre algún error, lanzará una excepción.
     *
     * @param resultado estructura que contiene el resultado de la consulta.
     */
    public void mostrarResultados(List<ListadoEvaluacion> resultado) {

        if (resultado.isEmpty()) {
            no_resul.setText("La consulta realizada no ha producido resultados");
            no_resul.setBounds(300,250,300,70);
            this.add(no_resul);
        } else {
            System.out.println(resultado.size());
            resultado_consulta = resultado;
            String curso, convocatoria, dni_alumno, n_mat, nomyape_alumno,
                   nota_ex, nota_pr, nota_final;

            for (int i = 0; i < resultado.size(); i++){
                curso = resultado.get(i).getCurso();
                convocatoria = resultado.get(i).getConvocatoria();
                dni_alumno = resultado.get(i).getDNIAlumno();
                n_mat = resultado.get(i).getN_MatAlumno();
                nomyape_alumno = (resultado.get(i).getNombreAlumno()+" "+resultado.get(i).getApellidosAlumno());
                nota_ex = resultado.get(i).getNota_Ex();
                nota_pr = resultado.get(i).getNota_Pr();
                nota_final = resultado.get(i).getNota_Final();

		this.aniadir(i,curso, convocatoria, dni_alumno, n_mat,
                             nomyape_alumno, nota_ex, nota_pr, nota_final);
                System.out.println(i);
                System.out.print(curso+" ");
                System.out.print(convocatoria+" ");
                System.out.print(dni_alumno+" ");
                System.out.print(n_mat+" ");
                System.out.print(nomyape_alumno+" ");
                System.out.print(nota_ex+" ");
                System.out.print(nota_pr+" ");
                System.out.print(nota_final+" ");
                System.out.println();
             }
            this.modificarBoton(resultado.size());
            this.aumentarTamanio(resultado.size());
        }
    } // fin del método mostrarResultados




    /** Método que añade una fila al resultado de la consulta.
     *
     * @param numreg nº de fila
     * @param curso curso en el que se realizó la evaluación
     * @param convocatoria convocatoria en la que se realizó la evaluación
     * @param dni_alumno DNI del alumno.
     * @param n_mat Nº de matrícula del alumno.
     * @param nomyape_alumno Nombre y apellidos del alumno.
     * @param nota_ex Nota total del examen.
     * @param nota_pr Nota total de la práctica.
     * @param nota_final Nota global de la evaluación.
     */
    private void aniadir(int numreg, String curso, String  convocatoria,
                         String dni_alumno, String n_mat, String nomyape_alumno,
                         String nota_ex, String nota_pr, String nota_final) {

        JTextField aux = new JTextField(curso);
	aux.setBounds(20, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
	this.arrayCurso.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(convocatoria);
	aux.setBounds(100, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        this.arrayConvocatoria.add(numreg, aux);
	this.add(aux);

        aux = new JTextField(String.valueOf(dni_alumno));
	aux.setBounds(240, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
        this.arrayDniAlumno.add(numreg, aux);
	this.add(aux);

        aux = new JTextField(String.valueOf(n_mat));
	aux.setBounds(320, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
        this.arrayNMatAlumno.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nomyape_alumno));
	aux.setBounds(400, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        this.arrayAlumno.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_ex));
	aux.setBounds(540, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
        this.arrayNota_Ex.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_pr));
	aux.setBounds(620, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
        this.arrayNota_Pr.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_final));
	aux.setBounds(700, y+ (INCREMENTOY*numreg), 80, 20);
	aux.setEditable(false);
        this.arrayNota_Final.add(numreg, aux);
	this.add(aux);
    } // fin del método aniadir


    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     *
     *  Quita todos los campos de texto de la interfaz y vacia los arraylist.
     */
    public void resetear(){
        for (int i=0;i<arrayCurso.size();i++){
            this.remove(arrayCurso.get(i));
            this.remove(arrayConvocatoria.get(i));
            this.remove(arrayDniAlumno.get(i));
            this.remove(arrayNMatAlumno.get(i));
            this.remove(arrayAlumno.get(i));
            this.remove(arrayNota_Ex.get(i));
            this.remove(arrayNota_Pr.get(i));
            this.remove(arrayNota_Final.get(i));
        }

        arrayCurso.clear();
        arrayConvocatoria.clear();
        arrayDniAlumno.clear();
        arrayNMatAlumno.clear();
        arrayAlumno.clear();
        arrayNota_Ex.clear();
        arrayNota_Pr.clear();
        arrayNota_Final.clear();

        resultado_consulta.clear();

        this.setLayout(null);
        this.setBounds(0,0,800,600);
        this.setPreferredSize(new Dimension(800,600));

        no_resul.setText(" ");
        botonOK.setBounds(740, 500, 100, 30);
    } // fin del método resetear


    /** Método que recoloca los botones "Borrar" y "Cancelar" en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void modificarBoton(int num_filas) {
        if ((y+(INCREMENTOY*num_filas) > 500)) {
            botonOK.setLocation(640, y+(INCREMENTOY*num_filas));
        }
    } // fin del método modificarBoton


    /** Método que redefine el tamaño del panel en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void aumentarTamanio(int num_filas) {
	this.setPreferredSize(new Dimension(900,y + INCREMENTOY*num_filas + 70));
	this.updateUI();
    } // fin del método aumentarTamanio


    /** Método que devuelve el array que contiene los cursos de cada resultado
     *  de la consulta.
     *
     * @return atributo ArrayCurso que contiene los cursos de cada resultado
     *         de la consulta.
     */
    public ArrayList<JTextField> getArrayCurso() {
        return this.arrayCurso;
    } // fin del método getArrayCurso

    /** Método que devuelve el array que contiene las convocatorias de cada resultado
     *  de la consulta.
     *
     * @return atributo ArrayConvocatoria que contiene las convocatorias de cada resultado
     *         de la consulta.
     */
    public ArrayList<JTextField> getArrayConvocatoria() {
        return this.arrayConvocatoria;
    } // fin del método getArrayConvocatoria

    /** Método que devuelve el array que contiene los dnis de cada resultado
     *  de la consulta.
     *
     * @return atributo ArrayDniAlumno que contiene los dnis de cada resultado
     *         de la consulta.
     */
    public ArrayList<JTextField> getArrayDniAlumno() {
        return this.arrayDniAlumno;
    } // fin del método getArrayDniAlumno

    /** Método que devuelve el array que contiene los nºs de matrícula de cada
     *  resultado de la consulta.
     *
     * @return atributo ArrayNMatAlumno que contiene los cursos de cada resultado
     *         de la consulta.
     */
    public ArrayList<JTextField> getArrayNMatAlumno() {
        return this.arrayNMatAlumno;
    } // fin del método getArrayNMatAlumno

    /** Método que devuelve el array que contiene los alumnos de cada resultado
     *  de la consulta.
     *
     * @return atributo ArrayAlumno que contiene los cursos de cada resultado
     *         de la consulta.
     */
    public ArrayList<JTextField> getArrayAlumno() {
        return this.arrayAlumno;
    } // fin del método getArrayAlumno

    /** Método que devuelve el array que contiene las notas de teoría de cada
     *  resultado de la consulta.
     *
     * @return atributo ArrayNota_Ex que contiene las notas de teoría de cada
     *         resultado de la consulta.
     */
    public ArrayList<JTextField> getArrayNota_Ex() {
        return this.arrayNota_Ex;
    } // fin del método getArrayNota_Ex

    /** Método que devuelve el array que contiene las notas de prácticas de cada
     *  resultado de la consulta.
     *
     * @return atributo ArrayNota_Pr que contiene las notas de prácticas de cada
     *         resultado de la consulta.
     */
    public ArrayList<JTextField> getArrayNota_Pr() {
        return this.arrayNota_Pr;
    } // fin del método getArrayNota_Pr

    /** Método que devuelve el array que contiene las notas finales de cada
     *  resultado de la consulta.
     *
     * @return atributo ArrayNota_Final que contiene las notas finales de cada
     *         resultado de la consulta.
     */
    public ArrayList<JTextField> getArrayNota_Final() {
        return this.arrayNota_Final;
    } // fin del método getArrayNota_Final

} // fin de la clase PanelResultadoConsultarHistoricoAlumnos