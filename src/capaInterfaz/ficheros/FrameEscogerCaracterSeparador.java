

package capaInterfaz.ficheros;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/** Clase que gestiona el frame utilizado para escoger un caracter separador como
 *  paso previo a la generación de un fichero solicitado por el usuario.
 *
 * @author Confiencial
 */
public class FrameEscogerCaracterSeparador extends JDialog implements ActionListener{

    private static final int GUARDAR_CARACTER_ESCOGIDO = 0;
    private JButtonOp botonOK;
    private ArrayList<JRadioButton> arrayradio = new ArrayList<JRadioButton>();
    private char arraycaracter[] = new char [6];
  //  private ArrayList<Char> arraycaracter = new ArrayList<Char>();
    private ButtonGroup grupoRadios = new ButtonGroup();

    /** Crea e inicializa un nuevo FrameEscogerCaracterSeparador.
     *
     * @param parent frame padre.
     * @param modal indica si el frame es modal respecto al frame padre o no.
     */
    public FrameEscogerCaracterSeparador(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setResizable(false);
        initComponents();
    } // fin del constructor

    private void initComponents() {
	this.setLayout(null);
	this.setBounds(0, 0, 400, 400);
	this.setPreferredSize(new Dimension(500,400));

        this.cargarElementos();
        this.setVisible(false);
    } // fin del constructor


    private void cargarElementos() {

        JLabel ruta = new JLabel("Elija el caracter separador de campos:");
        ruta.setBounds(20,20,400,70);

        JLabel tab = new JLabel("<tab>");
        tab.setBounds(100,80,100,70);
        arraycaracter[0] = '\t';

        JRadioButton radio_tab = new JRadioButton();
	radio_tab.setBounds(50,105,20,20);
	grupoRadios.add(radio_tab);
	arrayradio.add(radio_tab);

        JLabel almohadilla = new JLabel("#");
        almohadilla.setBounds(100,120,100,70);
        arraycaracter[1] ='#';

        JRadioButton radio_almohadilla = new JRadioButton();
	radio_almohadilla.setBounds(50,145,20,20);
	grupoRadios.add(radio_almohadilla);
	arrayradio.add(radio_almohadilla);

        JLabel punto = new JLabel(".");
        punto.setBounds(100,160,100,70);
        arraycaracter[2] = '.';

        JRadioButton radio_punto = new JRadioButton();
	radio_punto.setBounds(50,185,20,20);
	grupoRadios.add(radio_punto);
	arrayradio.add(radio_punto);

        JLabel dos_puntos = new JLabel(":");
        dos_puntos.setBounds(100,200,100,70);
        arraycaracter[3] = ':';

        JRadioButton radio_dos_puntos = new JRadioButton();
	radio_dos_puntos.setBounds(50,225,20,20);
	grupoRadios.add(radio_dos_puntos);
	arrayradio.add(radio_dos_puntos);

        JLabel arroba = new JLabel("@");
        arroba.setBounds(100,240,100,70);
        arraycaracter[4] = '@';

        JRadioButton radio_arroba = new JRadioButton();
	radio_arroba.setBounds(50,265,20,20);
	grupoRadios.add(radio_arroba);
	arrayradio.add(radio_arroba);

        JLabel barra = new JLabel("|");
        barra.setBounds(100,280,100,70);
        arraycaracter[5] = '|';

        JRadioButton radio_barra = new JRadioButton();
	radio_barra.setBounds(50,305,20,20);
	grupoRadios.add(radio_barra);
	arrayradio.add(radio_barra);

        botonOK = new JButtonOp("Aceptar",GUARDAR_CARACTER_ESCOGIDO);
        botonOK.setBounds(250, 300, 80, 30);
        botonOK.addActionListener(this);

        this.add(ruta);
        this.add(tab);
        this.add(almohadilla);
        this.add(radio_tab);
        this.add(radio_almohadilla);
        this.add(punto);
        this.add(radio_punto);
        this.add(dos_puntos);
        this.add(radio_dos_puntos);
        this.add(arroba);
        this.add(radio_arroba);
        this.add(barra);
        this.add(radio_barra);
        this.add(botonOK);
    } // fin del método cargarElementos


    /** Método que captura el evento al pulsar el botón "Aceptar" del frame
     *  y asigna el caracter separador escogido al ActionListener correspondiente..
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {     
        ActionListenerGuardarTxtAlumnos panel = new ActionListenerGuardarTxtAlumnos();
        ActionListenerGuardarTxtGrupoPracticas panel2 = new ActionListenerGuardarTxtGrupoPracticas();
        ActionListenerGuardarTxtProfesores panel3 = new ActionListenerGuardarTxtProfesores();
        ActionListenerGuardarTxtCalificaciones panel4 = new ActionListenerGuardarTxtCalificaciones();
        ActionListenerGuardarTxtHistoricoAlumnos panel5 = new ActionListenerGuardarTxtHistoricoAlumnos();
        int fila = this.filaSeleccionada();
        if (fila != -1) {
           panel.separador = this.arraycaracter[fila];
           panel2.separador = this.arraycaracter[fila];
           panel3.separador = this.arraycaracter[fila];
           panel4.separador = this.arraycaracter[fila];
           panel5.separador = this.arraycaracter[fila];
           this.dispose();
        } else {
            System.out.println("Escoja un caracter antes");
        }
    } // fin del método actionPerformed


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

} // fin de la clase FrameEscogerCaracterSeparador