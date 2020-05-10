
package capaInterfaz.ficheros;

import capaInterfaz.MenuHistoricoAlumnos.PanelResultadoConsultarHistoricoAlumnos;
import capaInterfaz.listados.ListadoAlumno;
import capaInterfaz.menuAlumnos.PanelMenuAltaAlumnosDesdeFichero;
import capaInterfaz.menuAlumnos.PanelResultadoConsultarAlumnos;
import capaInterfaz.menuCalificaciones.PanelResultadoConsultarCalificaciones;
import capaInterfaz.menuGruposPracticas.PanelResultadoConsultaGruposPracticas;
import capaInterfaz.menuProfesores.PanelResultadoConsultarProfesores;
import capaLogicaNegocio.Alumno;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

/** Clase que realiza las cargas y generaciones de ficheros solicitadas en el sistema.
 *
 * @author Confiencial
 */
public class ManejadorFichero {

    private BufferedReader lector = null;
    private BufferedWriter escritor = null;

    /** Crea un nuevo ManejadorFichero.
     *
     */
    public ManejadorFichero(){
    } // fin del constructor


    /** Método que realiza la carga de un fichero txt y comprueba que tenga el
     *  formato correcto para el alta de alumnos. Si todo se realiza correctamente,
     *  se darán de alta a los alumnos. Si ocurre algún error, se lanzará
     *  una excepción.
     *
     * @param strFilePath ruta donde está alojado el fichero.
     * @param panel panel desde el cual se solicita la carga.
     */
    public void Carga(String strFilePath,PanelMenuAltaAlumnosDesdeFichero panel){
        try{
            String separador = this.obtenerCaracterSeparador(strFilePath);
            FileInputStream fis = new FileInputStream(strFilePath);
            InputStreamReader is = new InputStreamReader(fis, "ISO-8859-1");
            lector = new BufferedReader(is);

            String dni, nombre, apellidos, n_mat, grupo_clase;
            String cadena = new String();
            String array[] = new String[5];
            List<ListadoAlumno> alumnos = new ArrayList<ListadoAlumno>();

            cadena = lector.readLine();
            do{               
                if (cadena != null){
                    array = cadena.split(separador);

                    grupo_clase = array[0];
                    n_mat = array[1];
                    dni = array[2];
                    apellidos = array[3];
                    nombre = array[4];
                    alumnos.add(new ListadoAlumno(n_mat, dni,
                                                  apellidos, nombre,
                                                  grupo_clase));
                }
                cadena = lector.readLine();
            }while (cadena!=null);

            Alumno alumno = new Alumno();
            alumno.altaMasivaAlumnos(alumnos);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Archivo no encontrado");
	} catch (IOException e1) {
            System.out.println(e1.getMessage());
            e1.printStackTrace();
            throw new RuntimeException("Error al intentar acceder al archivo");
	} catch (RuntimeException e2) {
            System.out.println(e2.getMessage());
            throw new RuntimeException(e2.getMessage());
        }
    } // fin del método Carga
	


    /** Método que obtiene el carácter separador del fichero. Si no lo encuentra,
     * o si ocurre algún error durante la ejecución del método, lanzará una excepción.
     *
     * @param strFilePath ruta donde está alojado el fichero.
     *
     * @return el carácter separador
     */
    private String obtenerCaracterSeparador(String strFilePath)  {
         try {
            FileInputStream fin = new FileInputStream(strFilePath);
            String separador = null;
            int aux;
            boolean formato_valido = true;
            do{
                aux = fin.read();
                System.out.println("Char : " + (char) aux);
                if ((this.noEsLetra((char) aux)) && (this.noEsNumero((char) aux))) {

                    switch ((char) aux) {
                       case '\t':
                                return "\t";
                       case '#':
                                return "#";
                       case '.':
                                return ".";
                       case ':':
                                return ":";
                       case '@':
                                return "@";
                       case '|':
                                return "|";
                       default:
                                throw new RuntimeException("El fichero no tiene el formato esperado");
                    }
                }
            } while ((separador == null) && (formato_valido) && (aux != -1));

            fin.close();
            throw new RuntimeException("El fichero no tiene el formato esperado");
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        } // fin del método obtenerCaracterSeparador



        /** Método que realiza la escritura del fichero CSV de calificaciones de alumnos
         *  en la ruta indicada por el usuario desde el panel de Consulta de alumnos.
         *
         * @param fichero contiene la ruta del fichero.
         * @param separador caracter separador de campos elegido por el usuario.
         * @param panel contiene el panel de Consulta de calificaciones.
         */
        public void GuardarFicheroCSVConsultaCalificaciones(String fichero, char separador,
                                               PanelResultadoConsultarCalificaciones panel) {
                try {
                        escritor = new BufferedWriter(new  OutputStreamWriter(new FileOutputStream(fichero),"UTF8"));
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Archivo no encontrado.");
                } catch (IOException e) {
                        throw new RuntimeException("Error en la escritura del fichero.");
                }

                String cadena = "dni,calificaciones";

                ArrayList<JTextField> arrayDniAlumno = panel.getArrayDniAlumno();
                ArrayList<JTextField> arrayNota_Final = panel.getArrayNota_Final();

                try {
                    escritor.write(cadena);
                    for (int i=0;i<arrayDniAlumno.size();i++){
                        escritor.newLine();
                        cadena= arrayDniAlumno.get(i).getText()+separador+
                                arrayNota_Final.get(i).getText();
                        escritor.write(cadena);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                try {
                    escritor.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }
        } // fin del método GuardarFicheroCSVConsultaCalificaciones



        /** Método que realiza la escritura del fichero de calificaciones de alumnos
         *  en la ruta indicada por el usuario desde el panel de Consulta de alumnos.
         *
         * @param fichero contiene la ruta del fichero.
         * @param separador caracter separador de campos elegido por el usuario.
         * @param panel contiene el panel de Consulta de calificaciones.
         */
        public void GuardarFicheroConsultaCalificaciones(String fichero,
                                                         char separador,
                            PanelResultadoConsultarCalificaciones panel) {

                try {
                    escritor = new BufferedWriter(new FileWriter(fichero));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Archivo no encontrado");
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                String cadena = new String();
                ArrayList<JTextField> arrayCurso = panel.getArrayCurso();
                ArrayList<JTextField> arrayConvocatoria = panel.getArrayConvocatoria();
                ArrayList<JTextField> arrayDniAlumno = panel.getArrayDniAlumno();
                ArrayList<JTextField> arrayAlumno = panel.getArrayAlumno();
                ArrayList<JTextField> arrayNota_P1 = panel.getArrayNota_P1();
                ArrayList<JTextField> arrayNota_P2 = panel.getArrayNota_P2();
                ArrayList<JTextField> arrayNota_P3 = panel.getArrayNota_P3();
                ArrayList<JTextField> arrayNota_P4 = panel.getArrayNota_P4();
                ArrayList<JTextField> arrayNota_Ex = panel.getArrayNota_Ex();
                ArrayList<JTextField> arrayNota_Pr = panel.getArrayNota_Pr();
                ArrayList<JTextField> arrayNota_EvC = panel.getArrayNota_EvC();
                ArrayList<JTextField> arrayEvC = panel.getArrayEvC();
                ArrayList<JTextField> arrayNota_Final = panel.getArrayNota_Final();

                try {
                    for (int i=0;i<arrayCurso.size();i++){
                        if (i!=0)
                            escritor.newLine();
                        cadena= arrayCurso.get(i).getText()+separador+
                                arrayConvocatoria.get(i).getText()+separador+
                                arrayDniAlumno.get(i).getText()+separador+
                                arrayAlumno.get(i).getText()+separador+
                                arrayNota_P1.get(i).getText()+separador+
                                arrayNota_P2.get(i).getText()+separador+
                                arrayNota_P3.get(i).getText()+separador+
                                arrayNota_P4.get(i).getText()+separador+
                                arrayNota_Ex.get(i).getText()+separador+
                                arrayNota_Pr.get(i).getText()+separador+
                                arrayNota_EvC.get(i).getText()+separador+
                                arrayEvC.get(i).getText()+separador+
                                arrayNota_Final.get(i).getText();
                        escritor.write(cadena);
                    }
                } catch (IOException e) {
                     throw new RuntimeException("Error en la escritura del fichero.");
                }

                try {
                     escritor.close();
                } catch (IOException e) {
                     throw new RuntimeException("Error en la escritura del fichero.");
                }
        } // fin del método GuardarFicheroConsultaCalificaciones




        /** Método que realiza la escritura del fichero de alumnos en la ruta
         *  indicada por el usuario desde el panel de Consulta de alumnos.
         *
         * @param fichero contiene la ruta del fichero.
         * @param separador caracter separador de campos elegido por el usuario.
         * @param panel contiene el panel de Consulta de alumnos.
         */
        public void GuardarFicheroConsultaAlumnos(String fichero,
                                                  char separador,
                            PanelResultadoConsultarAlumnos panel){

                try {
                        escritor = new BufferedWriter(new FileWriter(fichero));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Archivo no encontrado.");
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                String cadena = new String();
                ArrayList<JTextField> nombres = panel.getArrayNombres();
                ArrayList<JTextField> apellidos = panel.getArrayApellidos();
                ArrayList<JTextField> dni = panel.getArrayDNI();
                ArrayList<JTextField> n_mat = panel.getArrayN_Mat();
                ArrayList<JTextField> grupo_clase = panel.getArrayGrupoClase();

                try {
                    for (int i=0;i<nombres.size();i++){
                        if (i!=0)
                            escritor.newLine();
                        cadena= n_mat.get(i).getText()+separador+
                                dni.get(i).getText()+separador+
                                nombres.get(i).getText()+separador+
                                apellidos.get(i).getText()+separador+
                                grupo_clase.get(i).getText();
                        escritor.write(cadena);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                try {
                    escritor.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }
        } // fin del método GuardarFicheroConsultaAlumnos



        /** Método que realiza la escritura del fichero de profesores en la ruta
         *  indicada por el usuario desde el panel de Consulta de profesores.
         *
         * @param fichero contiene la ruta del fichero.
         * @param separador caracter separador de campos elegido por el usuario.
         * @param panel contiene el panel de Consulta de profesores.
         */
        public void GuardarFicheroConsultaProfesores(String fichero,
                                                     char separador,
                            PanelResultadoConsultarProfesores panel){

                try {
                    escritor = new BufferedWriter(new FileWriter(fichero));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Archivo no encontrado.");
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                String cadena = new String();
                ArrayList<JTextField> nombres = panel.getArrayNombres();
                ArrayList<JTextField> apellidos = panel.getArrayApellidos();
                ArrayList<JTextField> grupo1 = panel.getArrayGrupoClase1();
                ArrayList<JTextField> grupo2 = panel.getArrayGrupoClase2();


                try {
                    for (int i=0;i<nombres.size();i++){
                        if (i!=0)
                            escritor.newLine();
                        cadena= nombres.get(i).getText()+separador+
                                apellidos.get(i).getText()+separador+
                                grupo1.get(i).getText()+separador+
                                grupo2.get(i).getText();
                        escritor.write(cadena);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                try {
                    escritor.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }
        } // fin del método GuardarFicheroConsultaProfesores


        /** Método que realiza la escritura del fichero de grupos de prácticas
         *  en la ruta indicada por el usuario desde el panel de Consulta de
         *  grupos de prácticas.
         *
         * @param fichero contiene la ruta del fichero.
         * @param separador caracter separador de campos elegido por el usuario.
         * @param panel contiene el panel de Consulta de grupos de prácticas.
         */
        public void GuardarFicheroConsultaGruposPracticas(String fichero,
                                                          char separador,
                                    PanelResultadoConsultaGruposPracticas panel){

                try {
                    escritor = new BufferedWriter(new FileWriter(fichero));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Archivo no encontrado.");
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                String cadena = new String();
                ArrayList<JTextField> grupos = panel.getArrayGruposPracticas();
                ArrayList<JTextField> tutores = panel.getArrayTutores();
                ArrayList<JTextField> dni_alumno1 = panel.getArrayDniAlumno1();
                ArrayList<JTextField> dni_alumno2 = panel.getArrayDniAlumno2();
                ArrayList<JTextField> alumno1 = panel.getArrayNombreYApellidosAlumno1();
                ArrayList<JTextField> alumno2 = panel.getArrayNombreYApellidosAlumno2();
                ArrayList<JTextField> notas = panel.getArrayNotas();

                try {
                    String dni2 = null;
                    String nomyapealumno2 = null;
                    for (int i=0;i<grupos.size();i++){
                        if (i!=0)
                            escritor.newLine();
                        if (dni_alumno2.get(i).getText() == null) {
                            dni2 = "";
                            nomyapealumno2 = "";

                        } else {
                            dni2 = dni_alumno2.get(i).getText();
                            nomyapealumno2 = alumno2.get(i).getText();
                        }
                        cadena= grupos.get(i).getText()+separador+
                                tutores.get(i).getText()+separador+
                                dni_alumno1.get(i).getText()+separador+
                                alumno1.get(i).getText()+separador+
                                dni_alumno2.get(i).getText()+separador+
                                alumno2.get(i).getText()+separador+
                                notas.get(i).getText();
                        escritor.write(cadena);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                try {
                    escritor.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }
        } // fin del método GuardarFicheroConsultaGruposPracticas



        /** Método que realiza la escritura del fichero del histórico de alumno
         *  en la ruta indicada por el usuario desde el panel de Consulta de
         *  histórico de alumnos.
         *
         * @param fichero contiene la ruta del fichero.
         * @param separador caracter separador de campos elegido por el usuario.
         * @param panel contiene el panel de Consulta de histórico de alumnos.
         */
        public void GuardarFicheroConsultaHistoricoAlumnos(String fichero, char separador,
                                             PanelResultadoConsultarHistoricoAlumnos panel) {

                try {
                    escritor = new BufferedWriter(new FileWriter(fichero));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Archivo no encontrado.");
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }

                String cadena = new String();
                ArrayList<JTextField> curso = panel.getArrayCurso();
                ArrayList<JTextField> convocatoria = panel.getArrayConvocatoria();
                ArrayList<JTextField> dni_alumno = panel.getArrayDniAlumno();
                ArrayList<JTextField> n_mat_alumno = panel.getArrayNMatAlumno();
                ArrayList<JTextField> alumno = panel.getArrayAlumno();
                ArrayList<JTextField> nota_ex= panel.getArrayNota_Ex();
                ArrayList<JTextField> nota_pr = panel.getArrayNota_Pr();
                ArrayList<JTextField> nota_final = panel.getArrayNota_Final();

                try {
                    for (int i=0;i<curso.size();i++){
                        if (i!=0)
                            escritor.newLine();

                        cadena= curso.get(i).getText()+separador+
                                convocatoria.get(i).getText()+separador+
                                dni_alumno.get(i).getText()+separador+
                                n_mat_alumno.get(i).getText()+separador+
                                alumno.get(i).getText()+separador+
                                nota_ex.get(i).getText()+separador+
                                nota_pr.get(i).getText()+separador+
                                nota_final.get(i).getText();
                        escritor.write(cadena);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }
                try {
                    escritor.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error en la escritura del fichero.");
                }
        } // fin del método GuardarFicheroConsultaHistoricoAlumnos


        /** Método que comprueba si un carácter recibido por parámetro es letra.
         *
         * @param caracter carácter que se quiere comprobar.
         *
         * @return TRUE si el carácter NO es una letra.
         *         FALSE en caso contrario.
         */
        private boolean noEsLetra (char caracter) {
            return (!((caracter >= 'A') && (caracter <= 'Z')) ||
                      ((caracter >= 'a') && (caracter <= 'z'))
                      
                      );
        } // fin del método noEsLetra


        /** Método que comprueba si un carácter recibido por parámetro es número.
         *
         * @param caracter carácter que se quiere comprobar.
         *
         * @return TRUE si el carácter NO es un número.
         *         FALSE en caso contrario.
         */
        private boolean noEsNumero (char caracter) {
            return (!((caracter >= '0') && (caracter <= '9')));
        } // fin del método noEsNumero
 
} // fin de la clase ManejadorFichero