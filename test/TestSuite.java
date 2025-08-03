import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de pruebas que ejecuta todas las pruebas unitarias del proyecto
 */
@Suite
@SelectPackages({"model", "service", "controller"})
public class TestSuite {
    // Esta clase funciona como un punto de entrada para ejecutar todas las pruebas
    // La anotación @SelectPackages incluye automáticamente todas las clases de prueba
    // en los paquetes especificados
}