# Janji
Saya Shizuka Maulia Putri NIM 2308744 mengerjakan Tugas Praktikum 5 dalam mata kuliah DPBO untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program
![image](https://github.com/user-attachments/assets/1a8ff67b-e61f-499c-a4de-8c69c08518c5)

Dalam tampilan form dalam Intellij ini berisi Data Mahasiswa, dimana judul utama form ini adalah “Data Mahasiswa”. Lalu dibagian bawahnya terdapat form untuk input atau saat untuk meng-update data. Isi form diantaranya ada nim, nama, jenis kelamin(pilihan laki-laki dan perempuan), semester(antara 1-8), dan juga ipk. Dibagian kanan form juga terdapat button add dan cancel ketika pertma kali dijalankan. Namum saat di select salah satu kolom tabelnya maka form akan berisi data dari kolom tersebut dan button berubah menjadi button update, cancel, delete. Lalu di bawahnya terdapat tabel yang berisi data mahasiswa dengan value nim, nama, jenis kelamin, semesterm dan ipk.


# Penjelasan alur
Saat awal program dijalankan, program akan mengambil data dari database db_mahasiswa dari mysql dan menampilkannya dalam tabel. 
Jika user ingin menambahkan data mahasiswa, maka user harus mengisi formulir yang tersedia dengan NIM, Nama, Jenis Kelamin, Semester, dan IPK, lalu klik tombol “add”. Pastikan semua field terisi dan tidak ada nim ganda, jika ada field yang tidak terisi atau ada nim yang duplikat maka program akan mengeluarkan pesan  error yang meminta user untuk melengkapi semua data sebelum melanjutkan atau pesan error bahwa NIM sudah digunakan. Setelah semua field diisi, user dapat menekan tombol "Add". Jika sudah berhasil insert data maka program akan mengeluarkan pesan "Data Berhasil Ditambahkan".


Untuk mengupdate data, user harus memilih salah satu baris dalam tabel yang ingin diperbarui. Setelah baris dipilih, data tersebut akan otomatis diisi ke dalam formulir. Pastikan semua field terisi dan tidak ada nim ganda, jika ada field yang tidak terisi atau ada nim yang duplikat maka program akan mengeluarkan pesan  error yang meminta user untuk melengkapi semua data sebelum melanjutkan atau pesan error bahwa NIM sudah digunakan. Setelah semua field diisi, user dapat menekan tombol "Update". Jika sudah berhasil insert data maka program akan mengeluarkan pesan "Data Berhasil Diubah".


Jika user ingin menghapus data mahasiswa,maka user harus memilih baris dalam tabel yang ingin dihapus. Setelah memilih baris, user dapat menekan tombol "Delete". Program akan menampilkan konfirmasi dengan pesan "Apakah Anda yakin ingin menghapus data ini?". Jika user menekan "Yes", sistem akan menghapus data dari database, memperbarui tabel, mengosongkan formulir, dan menampilkan pesan "Data Berhasil Dihapus".

"# TP5DPBO2025C1" 
