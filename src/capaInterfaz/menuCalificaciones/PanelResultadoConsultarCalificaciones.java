
package capaInterfaz.menuCalificaciones;

import capaInterfaz.JButtonOp;
import capaInterfaz.ficheros.ActionListenerGuardarTxtCalificaciones;
import capaInterfaz.ficheros.ActionListenerGuardarCSVCalificaciones;
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

/** Clase que gestiona el panel donde se muestra el resultado de una consulta de
 *  calificaciones.
 *
 * @author Confiencial
 */
public class PanelResultadoConsultarCalificaciones extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_CONSULTAR_CALIFICACIONES = 12;
    private static final int GENERAR_TXT = 1211;
    private static final int GENERAR_CSV = 1212;
    private static final long serialVersionUID = 1L;

    private int x = 20,y = 140;
    private final int INCREMENTOX=140;
    private final int INCREMENTOY=30;

    private ArrayList<JTextField> arrayCurso = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayConvocatoria = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayGrupoClase = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayDniAlumno = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayAlumno = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_P1 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_P2 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_P3 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_P4 = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_Ex = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_Pr = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_EvC = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayEvC = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_Final = new ArrayList<JTextField>();

    private List<ListadoEvaluacion> resultado_consulta = new ArrayList<ListadoEvaluacion>();
    private JButtonOp botonOK, botonGenerarTxt, botonGenerarCSV, botonHome;
    private JLabel no_resul = new JLabel();


    /** Crea e inicializa un nuevo PanelResultadoConsultarCalificaciones
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoConsultarCalificaciones(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, 900, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {

        JLabel ruta = new JLabel("MENU PRINCIPAL>Calificaciones>Consultar>Resultado de la búsqueda");
        ruta.setBounds(20,20,500,70);

        JLabel curso = new JLabel("Curso");
        curso.setBounds(20,80,80,70);

        JLabel convocatoria = new JLabel("Convocatoria");
        convocatoria.setBounds(100,80,140,70);

        JLabel dni_alumno = new JLabel("DNI");
        dni_alumno.setBounds(240,80,80,70);

        JLabel nombre_y_apellidos_alumno = new JLabel("Nombre y apellidos");
        nombre_y_apellidos_alumno.setBounds(320,80,140,70);

        JLabel nota_p1 = new JLabel("P1");
        nota_p1.setBounds(460,80,40,70);

        JLabel nota_p2 = new JLabel("P2");
        nota_p2.setBounds(500,80,40,70);

        JLabel nota_p3 = new JLabel("P3");
        nota_p3.setBounds(540,80,40,70);

        JLabel nota_p4 = new JLabel("P4");
        nota_p4.setBounds(580,80,40,70);

        JLabel nota_Ex = new JLabel("Ex");
        nota_Ex.setBounds(620,80,40,70);

        JLabel nota_Pr = new JLabel("Pr");
        nota_Pr.setBounds(660,80,40,70);

        JLabel nota_EvC = new JLabel("Nota_EvC");
        nota_EvC.setBounds(700,80,60,70);

        JLabel EvC = new JLabel("EvC");
        EvC.setBounds(760,80,40,70);

        JLabel nota= new JLabel("Nota Final");
        nota.setBounds(800,80,80,70);

        ImageIcon img_generar_txt = new ImageIcon(getClass().getResource("/capaInterfaz/images/create_txt_icon.jpg"));
        botonGenerarTxt = new JButtonOp("",img_generar_txt,GENERAR_TXT);
        botonGenerarTxt.setBounds(600, 40, 110, 40);
        botonGenerarTxt.addActionListener(new ActionListenerGuardarTxtCalificaciones(this));

        ImageIcon img_generar_csv = new ImageIcon(getClass().getResource("/capaInterfaz/images/create_csv_icon.jpg"));        
        botonGenerarCSV = new JButtonOp("Generar CSV",img_generar_csv,GENERAR_CSV);
        botonGenerarCSV.setBounds(450, 40, 110, 40);
        botonGenerarCSV.addActionListener(new ActionListenerGuardarCSVCalificaciones(this));


        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        botonHome = new JButtonOp("",img_home, MENU_PRINCIPAL);
        botonHome.setBounds(770, 30, 80, 30);
        botonHome.addActionListener(this);

        botonOK = new JButtonOp("Aceptar",MENU_CONSULTAR_CALIFICACIONES);
        botonOK.setBounds(740, 500, 100, 30);
        botonOK.addActionListener(this);

        this.add(ruta);
        this.add(curso);
        this.add(convocatoria);
        this.add(dni_alumno);
        this.add(nombre_y_apellidos_alumno);
        this.add(nota_p1);
        this.add(nota_p2);
        this.add(nota_p3);
        this.add(nota_p4);
        this.add(nota_Ex);
        this.add(nota_Pr);
        this.add(nota_EvC);
        this.add(EvC);
        this.add(nota);
        this.add(botonGenerarTxt);
        this.add(botonGenerarCSV);
        this.add(botonHome);
        this.add(botonOK);
    } // fin del método cargarElementos

    /** Método que captura los eventos del panel resultado consultar calificaciones
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
            case MENU_CONSULTAR_CALIFICACIONES:
                this.resetear();
                PanelMenuConsultarCalificaciones pSubmenuConsultarCalificaciones = new PanelMenuConsultarCalificaciones(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuConsultarCalificaciones);
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
            botonHome.setBounds(700, 10, 80, 30);
            botonOK.setBounds(640, 500, 100, 30);
            botonGenerarTxt.setBounds(580, 40, 110, 40);
            botonGenerarCSV.setBounds(450, 40, 110, 40);
        } else {
            System.out.println(resultado.size());
            resultado_consulta = resultado;
            String curso, convocatoria, dni_alumno, nomyape_alumno,
                   nota_p1, nota_p2, nota_p3, nota_p4, nota_ex, nota_pr, nota_evc,
                   evc, nota_final;

            for (int i = 0; i < resultado.size(); i++){
                curso = resultado.get(i).getCurso();
                convocatoria = resultado.get(i).getConvocatoria();
                dni_alumno = resultado.get(i).getDNIAlumno();
                nomyape_alumno = (resultado.get(i).getNombreAlumno()+" "+resultado.get(i).getApellidosAlumno());
                nota_p1 = resultado.get(i).getNota_P1();
                nota_p2 = resultado.get(i).getNota_P2();
                nota_p3 = resultado.get(i).getNota_P3();
                nota_p4 = resultado.get(i).getNota_P4();
                nota_ex = resultado.get(i).getNota_Ex();
                nota_pr = resultado.get(i).getNota_Pr();
                nota_evc = resultado.get(i).getNota_Ev_Continua();
                nota_final = resultado.get(i).getNota_Final();
                evc = resultado.get(i).getEv_Continua();
                
		this.aniadir(i,curso, convocatoria, dni_alumno, nomyape_alumno,
                             nota_p1, nota_p2, nota_p3, nota_p4, nota_ex,
                             nota_pr, nota_evc, evc, nota_final);
                System.out.println(i);
                System.out.print(curso+" ");
                System.out.print(convocatoria+" ");
                System.out.print(dni_alumno+" ");
                System.out.print(nomyape_alumno+" ");
                System.out.print(nota_p1+" ");
                System.out.print(nota_p2+" ");
                System.out.print(nota_p3+" ");
                System.out.print(nota_p4+" ");
                System.out.print(nota_ex+" ");
                System.out.print(nota_pr+" ");
                System.out.print(nota_evc+" ");
                System.out.print(evc+" ");
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
     * @param nomyape_alumno Nombre y apellidos del alumno.
     * @param nota_p1 Nota del ejercicio 1 del examen.
     * @param nota_p2 Nota del ejercicio 2 del examen.
     * @param nota_p3 Nota del ejercicio 3 del examen.
     * @param nota_p4 Nota del ejercicio 4 del examen.
     * @param nota_ex Nota total del examen.
     * @param nota_pr Nota total de la práctica.
     * @param nota_evc Nota de evaluación continua.
     * @param evc indicador de evaluación continua. Puede tener los valores "SI" y "NO".
     * @param nota_final Nota global de la evaluación.
     */
    private void aniadir(int numreg, String curso, String  convocatoria,
                         String dni_alumno, String nomyape_alumno,
                         String nota_p1, String nota_p2, String nota_p3,
                         String nota_p4, String nota_ex, String nota_pr,
                         String nota_evc, String evc, String nota_final) {

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

     	aux = new JTextField(String.valueOf(nomyape_alumno));
	aux.setBounds(320, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        this.arrayAlumno.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_p1));
	aux.setBounds(460, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_P1.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_p2));
	aux.setBounds(500, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_P2.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_p3));
	aux.setBounds(540, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_P3.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_p4));
	aux.setBounds(580, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_P4.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_ex));
	aux.setBounds(620, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_Ex.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_pr));
	aux.setBounds(660, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_Pr.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_evc));
	aux.setBounds(700, y+ (INCREMENTOY*numreg), 60, 20);
	aux.setEditable(false);
        this.arrayNota_EvC.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(evc));
	aux.setBounds(760, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayEvC.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_final));
	aux.setBounds(800, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_Final.add(numreg, aux);
	this.add(aux);
    } // fin del método aniadir


    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     *
     * Quita todos los campos de texto de la interfaz y vacia los arraylist.
     */
    public void resetear(){
        for (int i=0;i<arrayCurso.size();i++){
            this.remove(arrayCurso.get(i));
            this.remove(arrayConvocatoria.get(i));
            this.remove(arrayDniAlumno.get(i));
            this.remove(arrayAlumno.get(i));
            this.remove(arrayNota_P1.get(i));
            this.remove(arrayNota_P2.get(i));
            this.remove(arrayNota_P3.get(i));
            this.remove(arrayNota_P4.get(i));
            this.remove(arrayNota_Ex.get(i));
            this.remove(arrayNota_Pr.get(i));
            this.remove(arrayNota_EvC.get(i));
            this.remove(arrayEvC.get(i));
            this.remove(arrayNota_Final.get(i));
        }

        arrayCurso.clear();
        arrayConvocatoria.clear();
        arrayDniAlumno.clear();
        arrayAlumno.clear();
        arrayNota_P1.clear();
        arrayNota_P2.clear();
        arrayNota_P3.clear();
        arrayNota_P4.clear();
        arrayNota_Ex.clear();
        arrayNota_Pr.clear();
        arrayNota_EvC.clear();
        arrayEvC.clear();
        arrayNota_Final.clear();

        resultado_consulta.clear();

        this.setLayout(null);
        this.setBounds(0,0,900,600);
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
            botonOK.setLocation(740, y+(INCREMENTOY*num_filas));
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


    public ArrayList<JTextField> getArrayCurso() {
        return this.arrayCurso;
    }

    public ArrayList<JTextField> getArrayConvocatoria() {
        return this.arrayConvocatoria;
    }

    public ArrayList<JTextField> getArrayDniAlumno() {
        return this.arrayDniAlumno;
    }

    public ArrayList<JTextField> getArrayAlumno() {
        return this.arrayAlumno;
    }

    public ArrayList<JTextField> getArrayNota_P1() {
        return this.arrayNota_P1;
    }

    public ArrayList<JTextField> getArrayNota_P2() {
        return this.arrayNota_P2;
    }

    public ArrayList<JTextField> getArrayNota_P3() {
        return this.arrayNota_P3;
    }

    public ArrayList<JTextField> getArrayNota_P4() {
        return this.arrayNota_P4;
    }

    public ArrayList<JTextField> getArrayNota_Ex() {
        return this.arrayNota_Ex;
    }

    public ArrayList<JTextField> getArrayNota_Pr() {
        return this.arrayNota_Pr;
    }

    public ArrayList<JTextField> getArrayNota_EvC() {
        return this.arrayNota_EvC;
    }

    public ArrayList<JTextField> getArrayEvC() {
        return this.arrayEvC;
    }

    public ArrayList<JTextField> getArrayNota_Final() {
        return this.arrayNota_Final;
    }

} // fin de la clase PanelResultadoConsultarCalificaciones