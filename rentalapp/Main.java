package rentalapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import rentalapp.config.Database;
import rentalapp.views.RentalTerminalView;
import rentalapp.views.RentalView;


@ComponentScan(basePackages = "rentalapp")
public class Main {
    // Konfigurasi database


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        RentalView rentalView = context.getBean(RentalTerminalView.class);
        rentalView.run();
    }

    @Bean
    public Database database() {

        // Inisialisasi koneksi database
        Database database = new Database(
                "rental_kendaraan",  // nama database
                "root",              // username
                "",          // password
                "localhost",         // host
                "3306"               // port
        );
        database.setup();
        return database;
    }
}