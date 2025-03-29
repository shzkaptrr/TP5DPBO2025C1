import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(400, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.decode("#FFD1DC"));

        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // variabel untuk menyimpan NIM yang dipilih
    private String selectedNim = "";
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTextField ipkField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JComboBox semesterComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel semesterLabel;
    private JLabel ipkLabel;

    // constructor
    public Menu() {
        // inisialisasi listmahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box jenis kelamin
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));
        // atur isi combo box semester
        String[] semesterData = {"", "1", "2", "3", "4", "5", "6", "7", "8"};
        semesterComboBox.setModel(new DefaultComboBoxModel(semesterData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // set model tabel
        mahasiswaTable.setModel(setTable());

        // tambahkan mouse listener untuk seleksi baris
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // ambil data dari baris yang dipilih
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedSemester = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();
                String selectedIpk = mahasiswaTable.getModel().getValueAt(selectedIndex, 5).toString();

                // simpan NIM yang dipilih
                Menu.this.selectedNim = selectedNim;

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                semesterComboBox.setSelectedItem(selectedSemester);
                ipkField.setText(selectedIpk);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "NAMA", "JENIS KELAMIN", "SEMESTER", "IPK"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[6];

                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("semester");
                row[5] = resultSet.getString("ipk");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public void insertData() {
        // Validasi input fields
        if (nimField.getText().trim().isEmpty() ||
                namaField.getText().trim().isEmpty() ||
                jenisKelaminComboBox.getSelectedIndex() == 0 ||
                semesterComboBox.getSelectedIndex() == 0 ||
                ipkField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null,
                    "Semua field harus diisi!\n" +
                            "Pastikan:\n" +
                            "- NIM tidak kosong\n" +
                            "- Nama tidak kosong\n" +
                            "- Jenis Kelamin dipilih\n" +
                            "- Semester dipilih\n" +
                            "- IPK tidak kosong",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // ambil value dari textfield dan combobox
            String nim = nimField.getText();
            String nama = namaField.getText();
            String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
            int semester = Integer.parseInt(semesterComboBox.getSelectedItem().toString());
            double ipk = Double.parseDouble(ipkField.getText());

            // Cek apakah NIM sudah ada di database
            String checkQuery = "SELECT COUNT(*) AS count FROM mahasiswa WHERE nim = '" + nim + "'";

            // Ubah bagian ini
            int count = 0;
            try (ResultSet resultSet = database.selectQuery(checkQuery)) {
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "NIM sudah ada di database", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return; // Hentikan proses insert
            }

            // Jika NIM belum ada, lakukan insert
            String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + semester + "', '" + ipk + "')";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Format IPK atau Semester salah!\n" +
                            "Pastikan:\n" +
                            "- IPK berupa angka desimal\n" +
                            "- Semester berupa angka",
                    "Kesalahan Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Terjadi kesalahan saat menyimpan data: " + e.getMessage(),
                    "Kesalahan Database",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public void updateData() {
        // Validasi input fields
        if (nimField.getText().trim().isEmpty() ||
                namaField.getText().trim().isEmpty() ||
                jenisKelaminComboBox.getSelectedIndex() == 0 ||
                semesterComboBox.getSelectedIndex() == 0 ||
                ipkField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null,
                    "Semua field harus diisi!\n" +
                            "Pastikan:\n" +
                            "- NIM tidak kosong\n" +
                            "- Nama tidak kosong\n" +
                            "- Jenis Kelamin dipilih\n" +
                            "- Semester dipilih\n" +
                            "- IPK tidak kosong",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // ambil data dari form
            String nim = nimField.getText();
            String nama = namaField.getText();
            String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
            // konversi tipe data semester dan ipk
            int semester = Integer.parseInt(semesterComboBox.getSelectedItem().toString());
            double ipk = Double.parseDouble(ipkField.getText());

            // Cek apakah NIM sudah digunakan oleh mahasiswa lain
            String checkQuery = "SELECT COUNT(*) AS count FROM mahasiswa WHERE nim = '" + nim + "' AND nim != '" + selectedNim + "'";

            int count = 0;
            try (ResultSet resultSet = database.selectQuery(checkQuery)) {
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "NIM sudah digunakan mahasiswa lain", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return; // Hentikan proses update
            }

            // Update data mahasiswa berdasarkan nim
            String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama + "', jenis_kelamin = '" + jenisKelamin + "', semester = '" + semester + "', ipk = '" + ipk + "' WHERE nim = '" + selectedNim + "'";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Format IPK atau Semester salah!\n" +
                            "Pastikan:\n" +
                            "- IPK berupa angka desimal\n" +
                            "- Semester berupa angka",
                    "Kesalahan Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Terjadi kesalahan saat mengubah data: " + e.getMessage(),
                    "Kesalahan Database",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void deleteData() {
        int triggerHapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (triggerHapus == JOptionPane.YES_OPTION) {
            // Hapus data mahasiswa berdasarkan NIM
            String sql = "DELETE FROM mahasiswa WHERE nim = '" + selectedNim + "'";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedIndex(0);
        semesterComboBox.setSelectedIndex(0);
        ipkField.setText("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
        selectedNim = "";
    }
}