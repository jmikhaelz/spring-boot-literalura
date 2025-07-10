package mx.aluras.literatura.gutendex.cli;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mx.aluras.literatura.gutendex.client.GutendexApiClient;
import mx.aluras.literatura.gutendex.model.BookResponse;
import mx.aluras.literatura.gutendex.service.GutendexApiService;

@Component
public class MenuInteractivo implements CommandLineRunner {
    // VARIABLE LOCALES
    private Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(MenuInteractivo.class.getName());

    @Override
    public void run(String... args) throws Exception {
        if (Arrays.asList(args).contains("--cli")) {
            BarraCargaUtil.mostrar(25, 8);
            mostrarMenu();
            System.out.println("¡Finalizado exitosamente el programa!");
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n|\t\t BOOK PLANET \t\t|");
            System.out.println("\t0. Salir");
            System.out.println("\t1. Buscar libro por Titulo en Gutendex");
            System.out.println("\t2. Buscar autor registrado");
            System.out.println("\t3. Buscar libro registrado");
            System.out.println("\t4. Buscar Idioma registrado");
            System.out.println("\t5. Buscar autores por fecha de nacimiento/fallecimiento registrados");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarPorGutendex();
                    break;
                case 2:
                    buscarPorGutendex();
                    break;
                case 3:
                    buscarPorGutendex();
                    break;
                case 4:
                    buscarPorIdioma();
                    break;
                case 5:
                    buscarPorFechaAutor();
                    break;
                case 0:
                    System.out.print("¿Seguro que quieres salir? [Ingrese Y/ Si no de ENTER]: ");
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    if (!(respuesta.equals("y")))
                        opcion = -1;
                    break;
                default:
            }
        } while (opcion != 0);
    }

    private void buscarPorFechaAutor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorFechaAutor'");
    }

    private void buscarPorIdioma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorIdioma'");
    }

    private void buscarPorGutendex() {
        while (true) {
            System.out.print("\n\t🔎 Ingrese el nombre del libro: ");
            String busqueda = scanner.nextLine().toLowerCase();
            if (obtenerDatoLibro(busqueda)) {
                System.out.println("✅ Datos Obtenidos del Libro: " + busqueda.toUpperCase());
                break;
            } else {
                BarraCargaUtil.mostrar(25, 8);

                logger.warning("❌ Sin datos obtenidos del Libro: " + busqueda.toUpperCase());
                System.out.print("¿Quieres seguir con otra búsqueda? [Ingrese Y/ Si no de ENTER]: ");
                var respuesta = scanner.nextLine().trim().toLowerCase();
                if (!(respuesta.equals("y")))
                    break;
            }
        }
    }

    private boolean obtenerDatoLibro(String busqueda) {
        try {
            URI uri = URI.create("https://gutendex.com/books/?search="
                    + URLEncoder.encode(busqueda.toLowerCase(), StandardCharsets.UTF_8));
            System.out.println(uri.toString());
            var resquest = new GutendexApiClient().getJSONToString(uri);
            BookResponse bookCache = new GutendexApiService().getAbout(resquest, BookResponse.class);
            /*
             * Mostrar todas las repuestas
             * bookCache.resultados().stream().forEach(
             * b -> System.out.println(b.toString()));
             * Mostrar la primera respuesta
             */
            BarraCargaUtil.mostrar(25, 8);
            System.out.println(
                    bookCache.resultados().get(0).toString());
            return true;
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return false;
    }
}