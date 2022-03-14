package fosalgo;

import java.util.Arrays;

public class GeneticsContainer2D {

    public static void main(String[] args) {
        //VARIABEL KONTAINER
        int pContainer = 20;//Panjang Kontaier
        int lContainer = 10;//Lebar Kontainer

        //DATA KOTAK / BARANG YANG AKAN DIMASUKKAN KE KONTAINER
        int[][] kotak = {
            {3, 5, 1, 8},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}  
            {4, 2, 1, 7},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}   
            {4, 6, 1, 6},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 4, 1, 5},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 5},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 4},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 4},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 3},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 2},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 2},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 1},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
            {4, 6, 1, 1},//{panjang, lebar, statusRotasi, nomorUrutTitikTujuanBarang}    
        };

        //VARIABEL ALGORITMA GENETIKA
        int numPopulasi = 20;//numPopulasi = banyaknya individu dalam satu populasi
        int numGenerasi = 10;//Banyaknya generasi = jumlah iterasi proses evolusi
        int numIndividuTerseleksi = 14;//Banyak individu terbaik yang akan terpilih pada proses SELEKSI Tournament
        double probMutasi = 0.5;//Probabilitas Mutasi

        //MEMPERSIAPKAN PROSES EVOLUSI GENETIKA
        //1. MEMBANGKITKAN POPULASI AWAL SECARA RANDOM (membangkitkan individu sebanyak numPopulasi)        
        Individu[] populasiAwal = new Individu[numPopulasi];
        double[][] fitnessAwal = new double[populasiAwal.length][2];
        for (int i = 0; i < numPopulasi; i++) {
            //id    : 1 | 2 | 3 | 4 | ... | 12
            //rotasi: 0 | 0 | 0 | 0 | ... | 0 
            //stsValid: 0 | 0 | 0 |       | 0 ---> 0 = tidak valid; 1 = valid
            int[][] chromosome = new int[kotak.length][3];//
            for (int j = 0; j < chromosome.length; j++) {
                chromosome[j][0] = 1 + j;//id barang
                chromosome[j][1] = randNolSatu();//rotasi
                chromosome[j][2] = 0;//status validasi
            }
            Individu indv = new Individu(chromosome);
            double fit = indv.hitungFitness(pContainer, lContainer, kotak);
            fitnessAwal[i][0] = fit;//save nilai fitness untuk disorting nanti
            fitnessAwal[i][1] = i;//save posisi individu yang nilai fitnessnya akan disorting nanti
            //System.out.println("\nIndividu-" + (1 + i));
            //System.out.print(indv);
            //System.out.println("Fitness: " + fit);
            //indv.cetakValidator();
            populasiAwal[i] = indv;
        }
        System.out.println("-----------------------------------------------------------------");

        //2. SORTING INDIVIDU BERDASARKAN NILAI FITNESSNYA
        //Sorting selection sort
        for (int i = 0; i < fitnessAwal.length - 1; i++) {
            int jMin = i;
            for (int j = i + 1; j < fitnessAwal.length; j++) {
                if (fitnessAwal[j][0] > fitnessAwal[jMin][0]) {//Sorting Descending
                    jMin = j;
                }
            }
            if (jMin != i) {
                //swap
                double temp0 = fitnessAwal[i][0];
                double temp1 = fitnessAwal[i][1];

                fitnessAwal[i][0] = fitnessAwal[jMin][0];
                fitnessAwal[i][1] = fitnessAwal[jMin][1];

                fitnessAwal[jMin][0] = temp0;
                fitnessAwal[jMin][1] = temp1;
            }
        }

        //REKONSTRUKSI POPULASI YANG SUDAH TERSORTING
        System.out.println("=================================================================");
        System.out.println("GENERASI-0");
        System.out.println("-----------------------------------------------------------------");
        Individu[] populasi = new Individu[numPopulasi];
        for (int i = 0; i < populasi.length; i++) {
            int index = (int) fitnessAwal[i][1];
            populasi[i] = populasiAwal[index];
            System.out.println("Individu-" + (1 + i));
            System.out.print(populasi[i]);
            System.out.println("Fitness: " + populasi[i].fitness);
            System.out.println();
        }

        //3. OPERASI ELITISM
        Individu individuElitism = populasi[0];
        double fitnessElitism = populasi[0].fitness;

        //4. BERSIAP MEMASUKI PROSES EVOLUSI
        for (int g = 1; g <= numGenerasi; g++) {
            Individu[] populasi_1 = new Individu[numPopulasi];

            //5. PROSES SELEKSI (menggunakan metode seleksi tournamen)
            for (int i = 0; i < numIndividuTerseleksi; i++) {
                populasi_1[i] = populasi[i];
            }

            //6. PROSES MEMBANGKITKAN INDIVIDU-INDIVIDU BARU SECARA RANDOM UNTUK MELENGKAPI POPULASI
            for (int i = numIndividuTerseleksi; i < numPopulasi; i++) {
                //BANGKITKAN INDIVIDU SECARA RANDOM
                int[][] chromosome = new int[kotak.length][3];//
                for (int j = 0; j < chromosome.length; j++) {
                    chromosome[j][0] = 1 + j;//id barang
                    chromosome[j][1] = randNolSatu();//rotasi
                    chromosome[j][2] = 0;//status validasi
                }
                Individu indv = new Individu(chromosome);
                populasi_1[i] = indv;
            }

            //7. PROSES MUTASI
            double[][] fitness_1 = new double[numPopulasi][2];
            for (int i = 0; i < populasi_1.length; i++) {
                double mr = Math.random();
                if (mr >= probMutasi) {
                    //JIKA mr lebih besar atau sama dengan probabilitas mutasi baru individu ini dimutasi
                    int numMutationPoint = randIntegerBetween(1, populasi_1[i].chromosome.length);
                    for (int m = 1; m < numMutationPoint; m++) {
                        int mutationPoint = randIntegerBetween(0, populasi_1[i].chromosome.length - 1);
                        int id = populasi_1[i].chromosome[mutationPoint][0];
                        int indexKotak = id - 1;
                        int statusRotasi = kotak[indexKotak][2];
                        if (statusRotasi == 1) {
                            int rotasi = populasi_1[i].chromosome[mutationPoint][1];
                            if (rotasi == 0) {
                                populasi_1[i].chromosome[mutationPoint][1] = 1;
                            } else {
                                populasi_1[i].chromosome[mutationPoint][1] = 0;
                            }
                        }
                    }
                }

                //HITUNG NILAI FITNESSS
                populasi_1[i].hitungFitness(pContainer, lContainer, kotak);
                fitness_1[i][0] = populasi_1[i].fitness;
                fitness_1[i][1] = i;

            }//END OF MUTASI

            //8. SORTING INDIVIDU BERDASARKAN NILAI FITNESS
            //Sorting selection sort
            for (int i = 0; i < fitness_1.length - 1; i++) {
                int jMin = i;
                for (int j = i + 1; j < fitness_1.length; j++) {
                    if (fitness_1[j][0] > fitness_1[jMin][0]) {//Sorting Descending
                        jMin = j;
                    }
                }
                if (jMin != i) {
                    //swap
                    double temp0 = fitness_1[i][0];
                    double temp1 = fitness_1[i][1];

                    fitness_1[i][0] = fitness_1[jMin][0];
                    fitness_1[i][1] = fitness_1[jMin][1];

                    fitness_1[jMin][0] = temp0;
                    fitness_1[jMin][1] = temp1;
                }
            }

            System.out.println("=================================================================");
            System.out.println("GENERASI-" + g + "");
            System.out.println("-----------------------------------------------------------------");

            //9. REKONSTRUKSI POPULASI
            populasi = new Individu[numPopulasi];
            for (int i = 0; i < populasi.length; i++) {
                int index = (int) fitness_1[i][1];
                populasi[i] = populasi_1[index];
                System.out.println("Individu-" + (1 + i));
                System.out.print(populasi[i]);
                System.out.println("Fitness: " + populasi[i].fitness);
                System.out.println();
            }

            //10. OPERASI ELITISM
            //EVALUASI NILAI FITNESS ELITISM
            if (populasi[0].fitness > fitnessElitism) {
                individuElitism = populasi[0];
                fitnessElitism = populasi[0].fitness;
            }

        }//END OF PROSES EVOLUSI
        
        //BEST INDIVIDU
        System.out.println("INDIVIDU TERBAIK");
        System.out.print(individuElitism);
        System.out.println("Fitness Terbaik: "+fitnessElitism);
        System.out.println("----------------------------------------");
        System.out.println("CONTAINER");
        individuElitism.cetakValidator();
        System.out.println("----------------------------------------");

    }//END OF MAIN()

    public static int randNolSatu() {
        double r = Math.random();
        if (r < 0.5) {
            return 0;
        } else {
            return 1;
        }
    }

    private static int randIntegerBetween(int min, int max) {
        if (min >= max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
