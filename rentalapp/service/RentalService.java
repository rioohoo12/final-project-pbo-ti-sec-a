package rentalapp.services;

import rentalapp.entities.Rental;

public interface RentalService {
    Rental[] daftarKendaraan();
    Rental[] kendaraanTersedia();
    void tambahKendaraanBaru(String jenisKendaraan, String namaKendaraan, String platNomor, double hargaSewa);
    Boolean hapusKendaraan(Integer nomor);
    Boolean sewaKendaraan(Integer nomor, String namaPenyewa);
    Boolean kembalikanKendaraan(Integer nomor);
}