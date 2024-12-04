package rentalapp.service;

import org.springframework.stereotype.Component;
import rentalapp.entities.Rental;
import rentalapp.repositories.RentalRepository;

@Component
public class RentalServiceImpl implements rentalapp.services.RentalService {
    private final RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Rental[] daftarKendaraan() {
        return rentalRepository.getAllKendaraan();
    }

    @Override
    public Rental[] kendaraanTersedia() {
        return rentalRepository.getKendaraanTersedia();
    }

    @Override
    public void tambahKendaraanBaru(String jenisKendaraan, String namaKendaraan,
                                    String platNomor, double hargaSewa) {
        Rental kendaraan = new Rental();
        kendaraan.setJenisKendaraan(jenisKendaraan);
        kendaraan.setNamaKendaraan(namaKendaraan);
        kendaraan.setPlatNomor(platNomor);
        kendaraan.setHargaSewa(hargaSewa);
        kendaraan.setSedangDisewa(false);
        rentalRepository.tambahKendaraan(kendaraan);
    }

    @Override
    public Boolean hapusKendaraan(Integer nomor) {
        return rentalRepository.hapusKendaraan(nomor);
    }

    @Override
    public Boolean sewaKendaraan(Integer nomor, String namaPenyewa) {
        return rentalRepository.sewaKendaraan(nomor, namaPenyewa);
    }

    @Override
    public Boolean kembalikanKendaraan(Integer nomor) {
        return rentalRepository.kembalikanKendaraan(nomor);
    }
}