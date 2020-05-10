package capaInterfaz.menuGruposPracticas;

import capaInterfaz.JButtonOp;
import capaInterfaz.listados.ListadoProfesor;
import capaLogicaNegocio.GrupoClase;
import capaLogicaNegocio.Profesor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/** Clase que gestiona el frame utilizado para escoger un tutor al dar de alta
 *  un grupo de prácticas.
 *
 * @author Confiencial
 */
public class FrameEscogerTutor extends javax.swing.JDialog implements ActionListener{
    private static final int ESCOGER_TUTOR = 1;
    private static final int CANCELAR = 2;

    private static final long serialVersionUID = 1L;

    private int x = 20,y = 100;
    private final int INCREMENTOX=140;
    private final int INCREMENTOY=30;

    private ArrayList<JTextField> arraynombre = new ArrayList<JTextField>();
    private ArrayList<JTextField> arrayapellidos = new ArrayList<JTextField>();
    private ArrayList<JTextField> arraycod = new ArrayList<JTextField>();
    private ArrayList<JRadioButton> arrayradio = new ArrayList<JRadioButton>();

    private List<ListadoProfesor> resultado_consulta = new ArrayList<ListadoProfesor>();
    private JButtonOp botonChoose, botonBack;
    private JLabel no_resul = new JLabel();

    private ButtonGroup grupoRadios = new ButtonGroup();

    private Profesor tutor;

    /** Crea e inicializa un nuevo FrameEscogerTutor
     *
     * @param parent frame padre.
     * @param modal indica si el frame es modal respecto al frame padre o no.
     */
    public FrameEscogerTutor (java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setLayout(null);
        this.setBounds(parent.getMousePosition().x,parent.getMousePosition().y - 200, 450, 500);
        this.setPreferredSize(new Dimension(450,100));
        cargarElementos();
        this.setVisible(true);
        dispose();
    } // fin del constructor

    private void cargarElementos() {

        JLabel escoja_tutor = new JLabel("Escoja un tutor de la lista:");
        escoja_tutor.setBounds(40,10,200,70);

        JLabel nombre = new JLabel("Nombre");
        nombre.setBounds(40,40,100,70);

        JLabel apellidos = new JLabel("Apellidos");
        apellidos.setBounds(180,40,100,70);

        botonChoose = new JButtonOp("Aceptar",ESCOGER_TUTOR);
        botonChoose.setBounds(150, 350, 100, 30);
        botonChoose.addActionListener(this);

        botonBack = new JButtonOp("Cancelar",CANCELAR);
        botonBack.setBounds(300, 350, 100, 30);
        botonBack.addActionListener(this);

        this.add(escoja_tutor);
        this.add(nombre);
        this.add(apellidos);
        this.add(botonChoose);
        this.add(botonBack);

        tutor = new Profesor();
        resultado_consulta = tutor.consultarProfesor(new GrupoClase(null));
        if (resultado_consulta.isEmpty()) {
            no_resul.setText("La consulta realizada no ha producido resultados");
            no_resul.setBounds(200,100,300,70);
            this.botonChoose.setEnabled(false);
            this.add(no_resul);
        } else {

            for (int i = 0; i < resultado_consulta.size(); i++){
		this.aniadir(i,resultado_consulta.get(i).getCodProfesor(),
                            resultado_consulta.get(i).getNombre(),
                            resultado_consulta.get(i).getApellidos());
                System.out.print(resultado_consulta.get(i).getCodProfesor()+" ");
                System.out.print(resultado_consulta.get(i).getNombre()+" ");
                System.out.print(resultado_consulta.get(i).getApellidos()+" ");
                System.out.println();
             }
            this.modificarBoton(resultado_consulta.size());
            this.aumentarTamanio(resultado_consulta.size());
        }
    } // fin del método cargarElementos


    /** Método que captura los eventos del frame escoger tutor y ejecuta
     *  el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {
        JButtonOp b = (JButtonOp) e.getSource();
        switch(b.getNumOperacion()){
            case ESCOGER_TUTOR:
                int fila = this.filaSeleccionada();
                if (fila != -1){
                    PanelMenuAltaGruposPracticas.cod_tutor = Integer.parseInt(arraycod.get(fila).getText());
                    PanelMenuConsultarGruposPracticas.cod_tutor = Integer.parseInt(arraycod.get(fila).getText());
                    PanelMenuModificarGruposPracticas.cod_tutor = Integer.parseInt(arraycod.get(fila).getText());
                    this.resetear();
                    dispose();
                }
                break;
            case CANCELAR:
                dispose();
                break;
        }
    } // fin del método actionPerformed


    /** Método que añade una fila al resultado de la consulta.
     *
     * @param numreg nº de fila
     * @param codProfesor
     * @param nombre nombre del profesor
     * @param apellidos apellidos del profesor
     */
    private void aniadir(int numreg, int codProfesor, String nombre, String apellidos) {

        JRadioButton radio = new JRadioButton();
	radio.setBounds(x, y+ (INCREMENTOY*numreg), 20, 20);
	grupoRadios.add(radio);
	arrayradio.add(radio);
	this.add(radio);

        JTextField aux = new JTextField(nombre);
	aux.setBounds(x + 20, y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
	arraynombre.add(numreg, aux);
	this.add(aux);

	aux = new JTextField(apellidos);
	aux.setBounds(x + 20 + (INCREMENTOX), y+ (INCREMENTOY*numreg), 140, 20);
	aux.setEditable(false);
        arrayapellidos.add(numreg, aux);
	this.add(aux);

        aux = new JTextField(Integer.toString(codProfesor));
        arraycod.add(numreg, aux);
    } // fin del método aniadir



    /** Método que recoloca los botones "Aceptar" y "Cancelar" en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void modificarBoton(int num_filas) {
        if ((y+(INCREMENTOY*num_filas) > 300)) {
            botonBack.setLocation(300, y+(INCREMENTOY*num_filas));
            botonChoose.setLocation(150, y+(INCREMENTOY*num_filas));
        }
    } // fin del método modificarBoton


    /** Método que redefine el tamaño del panel en función del
     *  nº de filas que se hayan obtenido en la consulta.
     *
     * @param num_filas nº de filas obtenidas como resultado de la consulta.
     */
    private void aumentarTamanio(int num_filas) {
	this.setPreferredSize(new Dimension(450,y + INCREMENTOY*num_filas + 70));
	// this.updateUI();
    } // fin del método aumentarTamanio


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



    /** Método que vacía las estructuras y reestablece el tamaño original
     *  del panel.
     *
     * Quita todos los campos de texto de la interfaz y vacia los arraylist.
     */
    public void resetear(){
        for (int i=0;i<arraynombre.size();i++){
            this.remove(arraynombre.get(i));
            this.remove(arrayapellidos.get(i));
            this.remove(arraycod.get(i));
            this.remove(arrayradio.get(i));
        }
        arraynombre.clear();
        arrayapellidos.clear();
        arraycod.clear();
        arrayradio.clear();

        resultado_consulta.clear();

        this.setLayout(null);
        this.setBounds(0,0,450,500);
        this.setPreferredSize(new Dimension(450,500));

        no_resul.setText(" ");
        botonChoose.setBounds(150, 300, 100, 30);
        botonBack.setBounds(300, 300, 100, 30);
        botonChoose.setEnabled(true);
    } // fin del método resetear


    public ArrayList<JTextField> getArrayNombres() {
        return this.arraynombre;
    }

    public ArrayList<JTextField> getArrayApellidos() {
        return this.arrayapellidos;
    }

    public ArrayList<JTextField> getArrayCodTutor() {
        return this.arraycod;
    }

} // fin de la clase FrameEscogerTutor