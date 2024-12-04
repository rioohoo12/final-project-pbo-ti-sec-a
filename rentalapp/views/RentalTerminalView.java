package rentalapp.views;

import org.springframework.stereotype.Component;
import rentalapp.services.RentalService;
import rentalapp.entities.Rental;
import java.util.Scanner;

@Component
public class RentalTerminalView implements RentalView {
    private static Scanner scanner = new Scanner(System.in);
    private final RentalService rentalService;

    public RentalTerminalView(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Override
    public void run() {
        menuUtama();
    }

    public void menuUtama() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n--- RENTAL KENDARAAN ---");
                System.out.println("1. Daftar Kendaraan");
                System.out.println("2. Kendaraan Tersedia");
                System.out.println("3. Tambah Kendaraan");
                System.out.println("4. Sewa Kendaraan");
                System.out.println("5. Kembalikan Kendaraan");
                System.out.println("6. Hapus Kendaraan");
                System.out.println("7. Keluar");

                String pilihan = input("Pilih Menu");

                switch (pilihan) {
                    case "1": tampilkanSemuaKendaraan(); break;
                    case "2": tampilkanKendaraanTersedia(); break;
                    case "3": tambahKendaraan(); break;
                    case "4": sewaKendaraan(); break;
                    case "5": kembalikanKendaraan(); break;
                    case "6": hapusKendaraan(); break;
                    case "7":
                        System.out.println("Terima kasih telah menggunakan aplikasi.");
                        running = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    private void tampilkanSemuaKendaraan() {
        Rental[] kendaraan = rentalService.daftarKendaraan();
        if (kendaraan.length == 0) {
            System.out.println("Tidak ada kendaraan yang terdaftar.");
            return;
        }

        System.out.println("\n--- DAFTAR SEMUA KENDARAAN ---");
        for (int i = 0; i < kendaraan.length; i++) {
            Rental k = kendaraan[i];
            System.out.printf("%d. %s - %s (%s) - Rp%.2f %s\n",
                    i + 1, k.getJenisKendaraan(), k.getNamaKendaraan(),
                    k.getPlatNomor(), k.getHargaSewa(),
                    k.isSedangDisewa() ? "[Disewa]" : "[Tersedia]");
        }
    }

    private void tampilkanKendaraanTersedia() {
        Rental[] kendaraan = rentalService.kendaraanTersedia();
        if (kendaraan.length == 0) {
            System.out.println("Tidak ada kendaraan yang tersedia.");
            return;
        }

        System.out.println("\n--- KENDARAAN TERSEDIA ---");
        for (int i = 0; i < kendaraan.length; i++) {
            Rental k = kendaraan[i];
            System.out.printf("%d. %s - %s (%s) - Rp%.2f\n",
                    i + 1, k.getJenisKendaraan(), k.getNamaKendaraan(),
                    k.getPlatNomor(), k.getHargaSewa());
        }
    }

    private void tambahKendaraan() {
        try {
            System.out.println("\n--- MENAMBAH KENDARAAN ---");

            // Validasi jenis kendaraan
            String jenisKendaraan;
            while (true) {
                jenisKendaraan = input("Jenis Kendaraan (motor/mobil)").toLowerCase();
                if (jenisKendaraan.equals("motor") || jenisKendaraan.equals("mobil")) {
                    break;
                }
                System.out.println("Jenis kendaraan harus 'motor' atau 'mobil'.");
            }

            // Validasi nama kendaraan
            String namaKendaraan;
            while (true) {
                namaKendaraan = input("Nama Kendaraan");
                if (!namaKendaraan.trim().isEmpty()) {
                    break;
                }
                System.out.println("Nama kendaraan tidak boleh kosong.");
            }

            // Validasi plat nomor
            String platNomor;
            while (true) {
                platNomor = input("Plat Nomor");
                if (!platNomor.trim().isEmpty()) {
                    break;
                }
                System.out.println("Plat nomor tidak boleh kosong.");
            }

            // Validasi harga sewa
            double hargaSewa;
            while (true) {
                try {
                    hargaSewa = Double.parseDouble(input("Harga Sewa"));
                    if (hargaSewa > 0) {
                        break;
                    }
                    System.out.println("Harga sewa harus lebih dari 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Masukkan harga sewa dengan angka yang valid.");
                }
            }

            rentalService.tambahKendaraanBaru(jenisKendaraan, namaKendaraan, platNomor, hargaSewa);
            System.out.println("Kendaraan berhasil ditambahkan.");
        } catch (Exception e) {
            System.out.println("Gagal menambahkan kendaraan: " + e.getMessage());
        }
    }

    private void sewaKendaraan() {
        try {
            System.out.println("\n--- SEWA KENDARAAN ---");
            tampilkanKendaraanTersedia();

            // Validasi nomor kendaraan
            int nomor;
            while (true) {
                try {
                    nomor = Integer.parseInt(input("Nomor Kendaraan"));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Masukkan nomor kendaraan dengan angka yang valid.");
                }
            }

            // Validasi nama penyewa
            String namaPenyewa;
            while (true) {
                namaPenyewa = input("Nama Penyewa");
                if (!namaPenyewa.trim().isEmpty()) {
                    break;
                }
                System.out.println("Nama penyewa tidak boleh kosong.");
            }

            if (rentalService.sewaKendaraan(nomor, namaPenyewa)) {
                System.out.println("Kendaraan berhasil disewa.");
            } else {
                System.out.println("Gagal menyewa kendaraan. Pastikan nomor kendaraan valid dan tersedia.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menyewa kendaraan: " + e.getMessage());
        }
    }

    private void kembalikanKendaraan() {
        try {
            System.out.println("\n--- KEMBALIKAN KENDARAAN ---");

            // Validasi nomor kendaraan
            int nomor;
            while (true) {
                try {
                    nomor = Integer.parseInt(input("Nomor Kendaraan"));
                    break;
                } catch (NumberFormatException e) { System.out.println("Masukkan nomor kendaraan dengan angka yang valid.");
                }
            }

            if (rentalService.kembalikanKendaraan(nomor)) {
                System.out.println("Kendaraan berhasil dikembalikan.");
            } else {
                System.out.println("Gagal mengembalikan kendaraan. Pastikan nomor kendaraan valid dan sedang disewa.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat mengembalikan kendaraan: " + e.getMessage());
        }
    }

    private void hapusKendaraan() {
        try {
            System.out.println("\n--- HAPUS KENDARAAN ---");

            // Validasi nomor kendaraan
            int nomor;
            while (true) {
                try {
                    nomor = Integer.parseInt(input("Nomor Kendaraan"));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Masukkan nomor kendaraan dengan angka yang valid.");
                }
            }

            if (rentalService.hapusKendaraan(nomor)) {
                System.out.println("Kendaraan berhasil dihapus.");
            } else {
                System.out.println("Gagal menghapus kendaraan. Pastikan nomor kendaraan valid.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat menghapus kendaraan: " + e.getMessage());
        }
    }

    private String input(String info) {
        System.out.print(info + " : ");
        return scanner.nextLine();
    }
}