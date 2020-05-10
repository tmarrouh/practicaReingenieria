
package capaInterfaz.menuCalificaciones;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.JDialogOperacionOK;
import capaInterfaz.listados.ListadoEvaluacion;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.Alumno;
import capaLogicaNegocio.Convocatoria;
import capaLogicaNegocio.Curso;
import capaLogicaNegocio.Evaluacion;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Clase que gestiona el panel donde se muestra el resultado de una modificación de
 *  calificaciones.
 *
 * @author Confiencial
 */
public class PanelResultadoModificarCalificaciones extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_MODIFICAR_CALIFICACIONES = 13;
    private static final int GUARDAR_MODIFICACIONES_CALIFICACIONES = 14;
    private static final long serialVersionUID = 1L;

    private int x = 20,y = 140;
    private final int INCREMENTOX=140;
    private final int INCREMENTOY=30;

    private ArrayList<Integer> arrayCodEvaluacion = new ArrayList<Integer>();
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
    private ArrayList<Boolean> arrayExConvalidado = new ArrayList<Boolean>();
    private ArrayList<Boolean> arrayPrConvalidada = new ArrayList<Boolean>();
    private ArrayList<JTextField> arrayNota_P1Antigua = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_P2Antigua = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_P3Antigua = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_P4Antigua = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayNota_PrAntigua = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayCodExamen = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayCodPractica = new ArrayList<JTextField>();

    private List<ListadoEvaluacion> resultado_consulta = new ArrayList<ListadoEvaluacion>();
    private JButtonOp botonOK,botonSaveChanges, botonHome;
    private JLabel no_resul = new JLabel();


    /** Crea e inicializa un nuevo PanelResultadoModificarCalificaciones
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelResultadoModificarCalificaciones(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, 900, alto);

        this.cargarElementos();
    }


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


        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        botonSaveChanges = new JButtonOp("Guardar",GUARDAR_MODIFICACIONES_CALIFICACIONES);
        botonSaveChanges.setBounds(600, 500, 100, 30);
        botonSaveChanges.addActionListener(this);

        botonOK = new JButtonOp("Atrás",MENU_MODIFICAR_CALIFICACIONES);
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
        this.add(botonSaveChanges);
        this.add(botonHome);
        this.add(botonOK);
    } // fin del método cargarElementos

    /** Método que captura los eventos del panel resultado modificar calificaciones
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
            case GUARDAR_MODIFICACIONES_CALIFICACIONES:
                try{
                    int tam = resultado_consulta.size();
                    Evaluacion evaluacion = new Evaluacion();
                    Curso curso = new Curso();
                    Convocatoria convocatoria = new Convocatoria();
                    List<Evaluacion> resultado_modificaciones = new ArrayList<Evaluacion>();
                    int cod_ev;
                    String dni_alumno, nota_p1, nota_p2, nota_p3, nota_p4, nota_pr,
                           ev_c, nota_evC, nota_p1ant, nota_p2ant, nota_p3ant, nota_p4ant, 
                           nota_prant, cod_ex, cod_pr;
                    boolean evC, ex_conv, pr_conv;
                    for (int i = 0; i < tam; i++){
                        cod_ev = this.arrayCodEvaluacion.get(i);
                        ex_conv = this.arrayExConvalidado.get(i);
                        pr_conv = this.arrayPrConvalidada.get(i);
                        cod_ex = this.arrayCodExamen.get(i).getText();
                        cod_pr = this.arrayCodPractica.get(i).getText();

                        nota_p1 = this.arrayNota_P1.get(i).getText();
                        nota_p2 = this.arrayNota_P2.get(i).getText();
                        nota_p3 = this.arrayNota_P3.get(i).getText();
                        nota_p4 = this.arrayNota_P4.get(i).getText();
                        nota_pr = this.arrayNota_Pr.get(i).getText();
                        nota_p1ant = this.arrayNota_P1Antigua.get(i).getText();
                        if (!nota_p1.equals(nota_p1ant)) {
                            ex_conv = false;
                            cod_ex = convocatoria.getConvocatoriaActual()+
                                     Integer.toString(curso.getCursoActual());
                        }


                        if (ex_conv) {         
                            nota_p2ant = this.arrayNota_P2Antigua.get(i).getText();
                            if (!nota_p2.equals(nota_p2ant)) {
                                ex_conv = false;
                                cod_ex = convocatoria.getConvocatoriaActual()+
                                         Integer.toString(curso.getCursoActual());
                            } else {
                                nota_p2 = nota_p2ant;
                            }
                        }


                        if (ex_conv) {   
                            nota_p3ant = this.arrayNota_P3Antigua.get(i).getText();
                            if (!nota_p3.equals(nota_p3ant)) {
                                ex_conv = false;
                                cod_ex = convocatoria.getConvocatoriaActual()+
                                         Integer.toString(curso.getCursoActual());
                            }
                        }

                        if (ex_conv) {       
                            nota_p4ant = this.arrayNota_P4Antigua.get(i).getText();
                            if (!nota_p4.equals(nota_p4ant)) {
                                ex_conv = false;
                                cod_ex = convocatoria.getConvocatoriaActual()+
                                         Integer.toString(curso.getCursoActual());
                            }
                        }

                        if (pr_conv) {
                            nota_prant = this.arrayNota_PrAntigua.get(i).getText();
                            if (!nota_pr.equals(nota_prant)) {
                                pr_conv = false;
                                cod_pr = convocatoria.getConvocatoriaActual()+
                                         Integer.toString(curso.getCursoActual());
                            }
                        }

                        ev_c = this.arrayEvC.get(i).getText();
                        if (ev_c.equals("SI") || (ev_c.equals("NO"))) {
                            evC = ev_c.equals("SI");
                            nota_evC = this.arrayNota_EvC.get(i).getText();
                            if (ex_conv){
                                ex_conv = false;
                                nota_p1 = "0";
                                nota_p2 = "0";
                                nota_p3 = "0";
                                nota_p4 = "0";
                            }

                        nota_pr = this.arrayNota_Pr.get(i).getText();
                        } else {
                            throw new RuntimeException();
                        }


                        dni_alumno = this.arrayDniAlumno.get(i).getText();
                        evaluacion = new Evaluacion(cod_ev, nota_p1, nota_p2,
                                                    nota_p3, nota_p4, nota_pr,
                                                    nota_evC, evC, ex_conv,
                                                    pr_conv, cod_ex, cod_pr);
                        resultado_modificaciones.add(evaluacion);
                    }

                    evaluacion.actualizarNotasEvaluaciones(resultado_modificaciones);

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



            case MENU_MODIFICAR_CALIFICACIONES:
                this.resetear();
                PanelMenuModificarCalificaciones pSubmenuModificarCalificaciones = new PanelMenuModificarCalificaciones(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuModificarCalificaciones);
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
            
            botonHome.setBounds(670, 30, 80, 30);
            botonOK.setBounds(640, 500, 100, 30);
            botonSaveChanges.setBounds(500, 500, 100, 30);
        } else {
            System.out.println(resultado.size());
            resultado_consulta = resultado;
            int cod_ev;
            String curso, convocatoria, dni_alumno, nomyape_alumno,
                   nota_p1, nota_p2, nota_p3, nota_p4, nota_ex, nota_pr, nota_evc,
                   evc, nota_final, cod_ex, cod_pr;
            boolean ex_conv, pr_conv;
            for (int i = 0; i < resultado.size(); i++){
                cod_ev = resultado.get(i).getCodEv();
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
                ex_conv = resultado.get(i).getEx_Convalidado();
                pr_conv = resultado.get(i).getPr_Convalidada();
                cod_ex = resultado.get(i).getCodExamen();
                cod_pr = resultado.get(i).getCodPractica();

		this.aniadir(i, cod_ev, curso, convocatoria, dni_alumno, nomyape_alumno,
                             nota_p1, nota_p2, nota_p3, nota_p4, nota_ex,
                             nota_pr, nota_evc, evc, nota_final, cod_ex, cod_pr,
                             ex_conv, pr_conv);
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
     * @param ex_conv indica si la nota del examen está convalidada de una convocatoria anterior.
     * @param pr_conv indica si la nota de la práctica está convalidada de una convocatoria anterior.
     * @param cod_ex Código del último examen aprobado. El código indica la convocatoria y el curso.
     * @param cod_pr Código de la última práctica aprobada. El código indica la convocatoria y el curso.
     */
    private void aniadir(int numreg, int cod_ev, String curso, String  convocatoria,
                         String dni_alumno, String nomyape_alumno,
                         String nota_p1, String nota_p2, String nota_p3,
                         String nota_p4, String nota_ex, String nota_pr,
                         String nota_evc, String evc, String nota_final,
                         String cod_ex, String cod_pr, boolean ex_conv,
                         boolean pr_conv) {

        arrayCodEvaluacion.add(numreg, cod_ev);

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
        this.arrayNota_P1.add(numreg, aux);
        this.arrayNota_P1Antigua.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_p2));
	aux.setBounds(500, y+ (INCREMENTOY*numreg), 40, 20);
        this.arrayNota_P2.add(numreg, aux);
        this.arrayNota_P2Antigua.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_p3));
	aux.setBounds(540, y+ (INCREMENTOY*numreg), 40, 20);
        this.arrayNota_P3.add(numreg, aux);
        this.arrayNota_P3Antigua.add(numreg, aux);
	this.add(aux);
 
     	aux = new JTextField(String.valueOf(nota_p4));
	aux.setBounds(580, y+ (INCREMENTOY*numreg), 40, 20);
        this.arrayNota_P4.add(numreg, aux);
        this.arrayNota_P4Antigua.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_ex));
	aux.setBounds(620, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_Ex.add(numreg, aux);
	this.add(aux);

        aux = new JTextField(String.valueOf(cod_ex));
        this.arrayCodExamen.add(numreg, aux);

     	aux = new JTextField(String.valueOf(nota_pr));
	aux.setBounds(660, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_Pr.add(numreg, aux);
        this.arrayNota_PrAntigua.add(numreg, aux);
	this.add(aux);

        aux = new JTextField(String.valueOf(cod_pr));
        this.arrayCodPractica.add(numreg, aux);

     	aux = new JTextField(String.valueOf(nota_evc));
	aux.setBounds(700, y+ (INCREMENTOY*numreg), 60, 20);
        this.arrayNota_EvC.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(evc));
	aux.setBounds(760, y+ (INCREMENTOY*numreg), 40, 20);
        this.arrayEvC.add(numreg, aux);
	this.add(aux);

     	aux = new JTextField(String.valueOf(nota_final));
	aux.setBounds(800, y+ (INCREMENTOY*numreg), 40, 20);
	aux.setEditable(false);
        this.arrayNota_Final.add(numreg, aux);
	this.add(aux);
        
        arrayExConvalidado.add(numreg, ex_conv);
        arrayPrConvalidada.add(numreg, pr_conv);
    } // fin del método aniadir


    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     *
     * Quita todos los campos de texto de la interfaz y vacia los arraylist.
     */
    public void resetear(){
        for (int i=0;i<arrayCurso.size();i++){
//            this.remove(arrayCodEvaluacion.get(i));
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
            this.remove(arrayNota_P1Antigua.get(i));
            this.remove(arrayNota_P2Antigua.get(i));
            this.remove(arrayNota_P3Antigua.get(i));
            this.remove(arrayNota_P4Antigua.get(i));
            this.remove(arrayNota_PrAntigua.get(i));
            this.remove(arrayNota_EvC.get(i));
            this.remove(arrayCodPractica.get(i));
            this.remove(arrayCodExamen.get(i));
        }

        arrayCodEvaluacion.clear();
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
        arrayExConvalidado.clear();
        arrayPrConvalidada.clear();
        arrayNota_P1Antigua.clear();
        arrayNota_P2Antigua.clear();
        arrayNota_P3Antigua.clear();
        arrayNota_P4Antigua.clear();
        arrayNota_PrAntigua.clear();
        arrayCodExamen.clear();
        arrayCodPractica.clear();
        resultado_consulta.clear();

        this.setLayout(null);
        this.setBounds(0,0,900,600);
        this.setPreferredSize(new Dimension(800,600));

        no_resul.setText(" ");
        botonOK.setBounds(740, 500, 100, 30);
        botonSaveChanges.setBounds(600, 500, 100, 30);
    } // fin del método resetear


    /** Método que recoloca los botones "Borrar" y "Cancelar" en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void modificarBoton(int num_filas) {
        if ((y+(INCREMENTOY*num_filas) > 500)) {
            botonOK.setLocation(740, y+(INCREMENTOY*num_filas));
            botonSaveChanges.setLocation(600, y+(INCREMENTOY*num_filas));
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

    public ArrayList<Integer> getArrayCodEv() {
        return this.arrayCodEvaluacion;
    }

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

    public ArrayList<Boolean> getArrayPr_Convalidada() {
        return this.arrayPrConvalidada;
    }

    public ArrayList<Boolean> getArrayEx_Convalidado() {
        return this.arrayExConvalidado;
    }

    public ArrayList<JTextField> getArrayNota_P1Antigua() {
        return this.arrayNota_P1Antigua;
    }

    public ArrayList<JTextField> getArrayNota_P2Antigua() {
        return this.arrayNota_P2Antigua;
    }

    public ArrayList<JTextField> getArrayNota_P3Antigua() {
        return this.arrayNota_P3Antigua;
    }

    public ArrayList<JTextField> getArrayNota_P4Antigua() {
        return this.arrayNota_P4Antigua;
    }

    public ArrayList<JTextField> getArrayNota_PrAntigua() {
        return this.arrayNota_PrAntigua;
    }

    public ArrayList<JTextField> getArrayCodExamen() {
        return this.arrayCodExamen;
    }

    public ArrayList<JTextField> getArrayCodPractica() {
        return this.arrayCodPractica;
    }

} // fin de la clase PanelResultadoModificarCalificaciones