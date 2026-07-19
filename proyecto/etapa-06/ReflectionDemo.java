import java.lang.reflect.Method;

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        Class<?> clase = CatalogoService.class;

        System.out.println("Nombre de la clase: " + clase.getSimpleName());

        if (clase.isAnnotationPresent(Revisado.class)) {
            Revisado anotacion = clase.getAnnotation(Revisado.class);
            System.out.println("Revisado por: " + anotacion.por() + " el " + anotacion.fecha());
        }

        for (Method metodo : clase.getDeclaredMethods()) {
            System.out.println("Metodo encontrado: " + metodo.getName());
        }

        Object instancia = clase.getDeclaredConstructor().newInstance();
        Method metodoListar = clase.getMethod("listar");
        metodoListar.invoke(instancia);
    }
}
