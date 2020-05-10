
package capaLogicaNegocio;

import capaDatos.ControladorCurso;
import capaDatos.DataAccessObject;

/** Clase que contiene la información del curso_actual.
 *  El atributo público curso_actual contendrá el curso_actual actual.
 *
 * @author Confiencial
 */
public class Curso {

    private static int curso_actual;
    private int cod_curso;
    private int actual = 0;


    /** Crea un nuevo curso.
     */
    public Curso () {
    } // fin del constructor


    /** Crea un nuevo curso e inicializa el atributo cod_curso.
     *
     * @param curso código del curso. Será el año de inicio.
     */
    public Curso (int curso){
        this.cod_curso = curso;
    } // fin del constructor


    /** Crea un nuevo curso e inicializa el atributo cod_curso.
     *
     * @param curso código del curso. Será el año de inicio.
     * @param actual indica si el curso es el que se cursa actualmente o no.
     */
    public Curso (int curso, int actual){
        this.cod_curso = curso;
        this.actual = actual;
    } // fin del constructor

    /** Método que, a través de la clase ControladorCurso, consulta en la
     * Base de Datos cual es el curso actual y lo asigna al atributo
     * estático curso_actual.
     */
    @SuppressWarnings("static-access")
    public void recuperarCursoActual() {
        ControladorCurso DAOCurso = new ControladorCurso ();
        this.curso_actual = DAOCurso.recuperarCursoActual ();
    } // fin del método recuperarCursoActual


    /** Método que cambia, en la Base de Datos, el curso actual.
     *  El nuevo curso será el año de inicio del actual, más uno.
     */
    public void cambioCurso() {
        ControladorCurso DAOCurso = new ControladorCurso();
        this.cod_curso = this.curso_actual+1;
        this.actual = 1;
        DAOCurso.desactivarCursoActual(this.curso_actual);
        DAOCurso.insertarEnTablaCurso(this);
        this.curso_actual = this.curso_actual+1;
    } // fin del método cambioCurso



    /** Método que realiza el BackUp del curso anterior. Elimina TODOS los registros
     *  del curso actual (evaluaciones, matrículas, tutorías, prácticas, exámenes,
     *  registros de imparticiones de clase, código del curso actual que va a dejar
     *  de serlo y grupos de prácticas en uso en el curso).
     *
     *  Posteriormente, recupera la información del curso anterior, y deja la
     *  Base de Datos y la aplicación en el último estado en el que se encontraba
     *  antes de cambiar de curso.
     */
    public void BackUpCurso() {
        DataAccessObject dataAccessObject = DataAccessObject.getDataAccessObjectConnected();
        try {
            Matricula matricula = new Matricula();
            matricula.eliminarMatriculasCursoActual(dataAccessObject);
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.eliminarEvaluacionesConvocatoriaActual(dataAccessObject);
            Tutoria tutorias = new Tutoria();
            tutorias.eliminarTutoriasConvocatoriaActual(dataAccessObject);
            Profesor profesor = new Profesor();
            profesor.eliminarImparticionesCursoActual(dataAccessObject);
            Examen examen = new Examen();
            examen.bajaCodExamenConvocatoriaActual(dataAccessObject);
            Practica practica = new Practica();
            practica.bajaCodPracticaConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en BackUpCurso");
        }

        Convocatoria convocatoria = new Convocatoria();
        convocatoria.cambioConvocatoria();
        convocatoria.recuperarConvocatoriaActual();
        this.cambioCursoAnterior();
        this.recuperarCursoActual();

        dataAccessObject = DataAccessObject.getDataAccessObjectConnected();

        try{
            GrupoPractica grupos = new GrupoPractica();
            Profesor profesores = new Profesor();
            grupos.reactivarGruposPracticasEnUsoEnConvocatoriaActual(dataAccessObject);
            profesores.reactivarProfesoresConRegistrosEnConvocatoriaActual(dataAccessObject);
            dataAccessObject.close();
        } catch (RuntimeException e) {
            dataAccessObject.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException("Problema en BackUpCurso 2");
        }
    } // fin del método BackUpCurso



    /** Método que elimina de la Base de Datos el curso actual y actualiza el
     *  campo actual al curso anterior.
     */
    public void cambioCursoAnterior() {
        ControladorCurso DAOCurso = new ControladorCurso();
        DAOCurso.eliminarCurso(this.curso_actual);
        DAOCurso.nuevoCursoActual(this.curso_actual-1);
    } // fin del método cambioCursoAnterior


    /** Método que devuelve el código del curso.
     *
     * @return código del curso.
     */
    public int getCurso () {
        return this.cod_curso;
    } // fin del método getCurso

    
    /** Método que indica si el objeto curso que llama al método es el actual.
     * 
     * @return 1 si es el curso actual.
     *         0 en caso contrario.
     */
    public int getActual () {
        return this.actual;
    } // fin del método getActual


    /** Método que devuelve el curso actual.
     * 
     * @return el año de inicio del curso actual.
     */
    public int getCursoActual() {
        return curso_actual;
    }

    /** Método que devuelve el curso actual en formato String.
     *  El formato será: año inicio "/" año fin.
     *  Ej.: 2010/2011
     *
     * @return curso actual en formato String.
     */
    public String getCursoActualEnString() {
        return (Integer.toString(curso_actual)+"/"+Integer.toString(curso_actual+1));
    } // fin del método getCursoActualEnString


    /** Método que devuelve el curso en formato String.
     *  El formato será: año inicio "/" año fin.
     *  Ej.: 2010/2011
     *
     * @return curso en formato String.
     */
    public String getCursoEnString() {
        return (Integer.toString(cod_curso)+"/"+Integer.toString(cod_curso+1));
    } // fin del método getCursoEnString

} // fin de la clase Curso