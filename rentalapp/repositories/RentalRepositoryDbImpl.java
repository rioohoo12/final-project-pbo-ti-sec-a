package rentalapp.repositories;

import org.springframework.stereotype.Component;
import rentalapp.config.Database;
import rentalapp.entities.Rental;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class RentalRepositoryDbImpl implements RentalRepository {
    private final Database database;

    public RentalRepositoryDbImpl(Database database) {
        this.database = database;
    }

    @Override
    public Rental[] getAllKendaraan() {
        List<Rental> kendaraanList = new ArrayList<>();
        String query = "SELECT * FROM kendaraan";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rental kendaraan = new Rental();
                kendaraan.setId(rs.getInt("id"));
                kendaraan.setJenisKendaraan(rs.getString("jenis_kendaraan"));
                kendaraan.setNamaKendaraan(rs.getString("nama_kendaraan"));
                kendaraan.setPlatNomor(rs.getString("plat_nomor"));
                kendaraan.setHargaSewa(rs.getDouble("harga_sewa"));
                kendaraan.setSedangDisewa(rs.getBoolean("sedang_disewa"));
                kendaraanList.add(kendaraan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kendaraanList.toArray(new Rental[0]);
    }

    @Override
    public Rental[] getKendaraanTersedia() {
        List<Rental> kendaraanList = new ArrayList<>();
        String query = "SELECT * FROM kendaraan WHERE sedang_disewa = false";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rental kendaraan = new Rental();
                kendaraan.setId(rs.getInt("id"));
                kendaraan.setJenisKendaraan(rs.getString("jenis_kendaraan"));
                kendaraan.setNamaKendaraan(rs.getString("nama_kendaraan"));
                kendaraan.setPlatNomor(rs.getString("plat_nomor"));
                kendaraan.setHargaSewa(rs.getDouble("harga_sewa"));
                kendaraan.setSedangDisewa(false);
                kendaraanList.add(kendaraan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kendaraanList.toArray(new Rental[0]);
    }

    @Override
    public void tambahKendaraan(Rental kendaraan) {
        String query = "INSERT INTO kendaraan " +
                "(jenis_kendaraan, nama_kendaraan, plat_nomor, harga_sewa, sedang_disewa) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, kendaraan.getJenisKendaraan());
            stmt.setString(2, kendaraan.getNamaKendaraan());
            stmt.setString(3, kendaraan.getPlatNomor());
            stmt.setDouble(4, kendaraan.getHargaSewa());
            stmt.setBoolean(5, false);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean hapusKendaraan(Integer id) {
        String query = "DELETE FROM kendaraan WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean sewaKendaraan(Integer id, String namaPenyewa) {
        String query = "UPDATE kendaraan SET sedang_disewa = true, penyewa = ? WHERE id = ? AND sedang_disewa = false";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, namaPenyewa);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean kembalikanKendaraan(Integer id) {
        String query = "UPDATE kendaraan SET sedang_disewa = false, penyewa = NULL WHERE id = ? AND sedang_disewa = true";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}