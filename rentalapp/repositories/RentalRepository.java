package rentalapp.repositories;

import rentalapp.entities.Rental;

public interface RentalRepository {
    Rental[] getAllKendaraan();
    Rental[] getKendaraanTersedia();
    void tambahKendaraan(Rental kendaraan);
    Boolean hapusKendaraan(Integer id);
    Boolean sewaKendaraan(Integer id, String namaPenyewa);
    Boolean kembalikanKendaraan(Integer id);
}