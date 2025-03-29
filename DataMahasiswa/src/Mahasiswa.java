public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private int semester;
    private double ipk;


    public Mahasiswa(String nim, String nama, String jenisKelamin, int  semester, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.semester = semester;
        this.ipk = ipk;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setSemester(int semester) {this.semester = semester;}

    public void setIpk(double ipk) {this.ipk = ipk;}

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public int getSemester() { return  this.semester;}

    public double getIpk() { return this.ipk; }
}
