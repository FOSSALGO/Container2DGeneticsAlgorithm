package fosalgo;

public class Individu {

    public int[][] chromosome = null;
    public double fitness = -1;
    public int[][] validator = null;

    public Individu(int[][] chromosome) {
        if (chromosome != null) {
            this.chromosome = chromosome;//new int[chromosome.length][chromosome[0].length];
        }
    }

    public double hitungFitness(int panjangContainer, int lebarContainer, int[][] kotak) {
        validator = new int[panjangContainer][lebarContainer];
        int gen = 0;
        double f = 0;
        pencarian:
        for (int i = 0; i < validator.length; i++) {
            for (int j = 0; j < validator[i].length; j++) {
                if (validator[i][j] == 0 && gen < chromosome.length) {
                    int id = chromosome[gen][0];
                    int rotasi = chromosome[gen][1];

                    int indexKotak = id - 1;
                    //System.out.println(indexKotak);
                    int panjang = kotak[indexKotak][0];
                    int lebar = kotak[indexKotak][1];
                    int statusRotasi = kotak[indexKotak][2];
                    int nomorUrutTitikTujuanBarang = kotak[indexKotak][3];

                    //jika rotasi = 1
                    if (rotasi == 1) {
                        //swap(panjang,lebar)
                        int temp = panjang;
                        panjang = lebar;
                        lebar = temp;
                    }

                    //tentukan titik start dan end
                    int startX = i;
                    int endX = startX + panjang;
                    int startY = j;
                    int endY = startY + lebar;

                    //PERIKSA APAKAH RUANG KOSONG YANG TERSEDIA DAPAT MEMUAT KOTAK
                    boolean ruangMencukupi = true;
                    if (startX < 0 || startX >= validator.length || endX < 0 || endX > validator.length) {
                        ruangMencukupi = false;
                    } else if (startY < 0 || startY >= validator[0].length || endY < 0 || endY > validator[0].length) {
                        ruangMencukupi = false;
                    } else {
                        cariruang:
                        for (int m = startX; m < endX; m++) {
                            for (int n = startY; n < endY; n++) {
                                if (validator[m][n] != 0) {
                                    ruangMencukupi = false;
                                    break cariruang;
                                }
                            }
                        }
                    }//END OF PERIKSA APAKAH RUANG KOSONG YANG TERSEDIA DAPAT MEMUAT KOTAK

                    //MELAKUKAN PENGISIAN BARANG KE KONTAINER JIKA RUANG MENCUKUPI
                    if (ruangMencukupi) {
                        for (int m = startX; m < endX; m++) {
                            for (int n = startY; n < endY; n++) {
                                validator[m][n] = id;
                            }
                        }
                        //UPDATE STATUS VALID = 1;
                        chromosome[gen][2] = 1;
                        gen++;//Increment Index Kotak
                        if (gen >= chromosome.length) {
                            //PENCARIANPUN BERAKHIR KARNA GEN SUDAH HABIS
                            break pencarian;
                        }
                    } else {
                        //System.out.println("BLOCK");
                        f = f + 1;
                        validator[i][j] = -1;//MEMBUAT BLOCK untuk AREA YANG GAGAL DIISI
                    }//END OF MELAKUKAN PENGISIAN BARANG KE KONTAINER JIKA RUANG MENCUKUPI

                }
            }
        }//END OF FOR i

        //HITUNG FITNESS VALUE
        if (f == 0) {
            if(gen>0){
                this.fitness = 1;
            }else{
                this.fitness = 0;
            }
        } else {
            this.fitness = 1 / f;
        }

        return this.fitness;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        if (chromosome != null) {
            for (int j = 0; j < chromosome[0].length; j++) {
                for (int i = 0; i < chromosome.length; i++) {
                    result.append(chromosome[i][j] + ";");
                }
                result.append("\n");
            }
        }
        return result.toString();
    }
    
    public void cetakValidator(){
        if(validator!=null){
            for(int i=0;i<validator.length;i++){
                for(int j=0;j<validator[i].length;j++){
                    System.out.print(validator[i][j]+",");
                }
                System.out.println("");
            }
        }
    }

}
