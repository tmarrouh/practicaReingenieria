
package capaInterfaz.menuGruposPracticas;

import capaInterfaz.JButtonOp;
import capaInterfaz.JDialogOperacionFail;
import capaInterfaz.listados.ListadoGrupoPractica;
import capaInterfaz.menuPrincipal.FrameMenuPrincipal;
import capaInterfaz.menuPrincipal.PanelMenuPrincipal;
import capaLogicaNegocio.GrupoPractica;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/** Clase que gestiona el panel de baja de grupos de prácticas.
 *
 * @author Confiencial
 */
public class PanelMenuBajaGruposPracticas extends JPanel implements ActionListener{

    private static final int MENU_PRINCIPAL = 1;
    private static final int MENU_GRUPOS_PRACTICAS = 5;
    private static final int LISTADO_BAJA_GRUPO_PRACTICAS = 522;

    private JTextField campo_codigo_grupo;


    /** Crea e inicializa un nuevo PanelMenuBajaGruposPracticas
     *
     * @param ancho nº de pixels de anchura del panel.
     * @param alto nº de pixels de altura del panel.
     */
    public PanelMenuBajaGruposPracticas(int ancho,int alto){
        this.setLayout(null);
        this.setBounds(0,0, ancho, alto);

        this.cargarElementos();
    } // fin del constructor

    private void cargarElementos() {
        JLabel ruta = new JLabel("MENU PRINCIPAL>Grupos de prácticas>Baja");
        ruta.setBounds(20,20,400,70);

        JLabel introduzca_cod_grupo = new JLabel("Código de grupo");
        introduzca_cod_grupo.setBounds(150,200,200,70);
        Font auxFont = introduzca_cod_grupo.getFont();
        introduzca_cod_grupo.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 15));

        campo_codigo_grupo = new JTextField();
        campo_codigo_grupo.setBounds(300,225, 150, 20);

        JButtonOp botonSearch = new JButtonOp("Buscar",LISTADO_BAJA_GRUPO_PRACTICAS);
        botonSearch.setBounds(475, 225, 100, 30);
        botonSearch.addActionListener(this);

        ImageIcon img_home = new ImageIcon(getClass().getResource("/capaInterfaz/images/home_icon.jpg"));
        JButtonOp botonHome = new JButtonOp("",
                                            img_home,
                                            MENU_PRINCIPAL);
        botonHome.setBounds(670, 30, 80, 30);
        botonHome.addActionListener(this);

        JButtonOp botonBack = new JButtonOp("Atrás",MENU_GRUPOS_PRACTICAS);
        botonBack.setBounds(670, 500, 80, 30);
        botonBack.addActionListener(this);

        this.add(ruta);
        this.add(introduzca_cod_grupo);
        this.add(campo_codigo_grupo);
        this.add(botonSearch);
        this.add(botonHome);
        this.add(botonBack);
    } // fin del método cargarElementos


    /** Método que captura los eventos del panel baja grupos de prácticas y ejecuta
     *  el código correspondiente a cada evento.
     *
     * @param e evento capturado.
     */
    public void actionPerformed(ActionEvent e) {
        JButtonOp b = (JButtonOp) e.getSource();
        int ancho = FrameMenuPrincipal.ancho;
        int alto = FrameMenuPrincipal.alto;
        switch(b.getNumOperacion()){
            case MENU_PRINCIPAL:
                PanelMenuPrincipal pSubmenuPrincipal = new PanelMenuPrincipal(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuPrincipal);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case MENU_GRUPOS_PRACTICAS:
                PanelMenuGruposPracticas pSubmenuGruposPracticas = new PanelMenuGruposPracticas(ancho,alto);
                FrameMenuPrincipal.getFramePrincipal().setContentPane(pSubmenuGruposPracticas);
                FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                break;
            case LISTADO_BAJA_GRUPO_PRACTICAS:
                String cod_grupo = campo_codigo_grupo.getText();
                int cod;
                try{
                    List<ListadoGrupoPractica> resultado;
                    GrupoPractica grupo;
                    if (!this.estaVacio(cod_grupo)) {
                        cod = Integer.parseInt(cod_grupo);
                        grupo = new GrupoPractica(cod);
                    } else {
                        grupo = new GrupoPractica();
                    }
                    resultado = grupo.consultarGrupoPractica(null);


                    PanelResultadoBajaGrupoPracticas pResultadoBajaGrupoPracticas = new PanelResultadoBajaGrupoPracticas(ancho,alto);
                    JScrollPane pResultadoBajaAlumnosConScroll = new JScrollPane(pResultadoBajaGrupoPracticas);
                    pResultadoBajaAlumnosConScroll.setHorizontalScrollBar(null);
                    FrameMenuPrincipal.getFramePrincipal().setContentPane(pResultadoBajaAlumnosConScroll);
                    pResultadoBajaGrupoPracticas.mostrarResultados(resultado);
                    FrameMenuPrincipal.getFramePrincipal().setVisible(true);
                } catch (NumberFormatException e1) {
                    JDialogOperacionFail jDialogFail = new JDialogOperacionFail(
                                                           FrameMenuPrincipal.getFramePrincipal(),
                                                           "Introduzca un valor numérico",true);
                    jDialogFail.setVisible(true);
                } catch (RuntimeException e2) {
                    JDialogOperacionFail jDialogFail = new JDialogOperacionFail(
                                                           FrameMenuPrincipal.getFramePrincipal(),
                                                           e2.getMessage(),true);
                    jDialogFail.setVisible(true);
                }


                break;
        }
    } // fin del método actionPerformed



    /** Método que comprueba si una cadena está vacía. Se considerará vacía
     * si su valor es null o "".
     *
     * @param cadena cadena de la que se quiere comprobar si está vacía.
     *
     * @return TRUE si la cadena está vacía. FALSE en caso contrario.
     */
    private boolean estaVacio(String cadena) {
	return ((cadena == null) || ("".equals(cadena)));
    } // fin del método estaVacio

} // fin de la clase PanelMenuBajaGruposPracticas