import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class odev {

    static String[] urunAdlari = new String[5];
    static double[] urunFiyatlari = new double[5];
    static int[] urunStoklari = new int[5];

    static String[] sepetUrunleri = new String[10];
    static double[] sepetBirimFiyatlari = new double[10];
    static int[] sepetAdetleri = new int[10];
    static int sepettekiCesitSayisi = 0;

    static Scanner tarayici = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        File dosya = new File("urunler.txt");

        if (!dosya.exists()) {
            FileWriter ilkYazici = new FileWriter(dosya);
            ilkYazici.write("Pro_Mac_Topu 1200,0 10\n");
            ilkYazici.write("Krampon 3500,0 5\n");
            ilkYazici.write("Milli_Forma 900,0 20\n");
            ilkYazici.write("Kaleci_Eldiveni 600,0 8\n");
            ilkYazici.write("Tozluk 150,0 50\n");
            ilkYazici.close();
            System.out.println("Bilgi: 'urunler.txt' bulunamadý, otomatik oluþturuldu.");
        }

        Scanner dosyaOkuyucu = new Scanner(dosya);
        int sayac = 0;
        
        while (dosyaOkuyucu.hasNext() && sayac < urunAdlari.length) {
            urunAdlari[sayac] = dosyaOkuyucu.next();
            urunFiyatlari[sayac] = dosyaOkuyucu.nextDouble();
            urunStoklari[sayac] = dosyaOkuyucu.nextInt();
            sayac++;
        }
        dosyaOkuyucu.close();

        boolean devamEdilsinMi = true;

        System.out.println("--- SPORTLAB STOKLU SATIÞ SÝSTEMÝ ---");

        while (devamEdilsinMi) {
            System.out.println("\n1. Ürünleri ve Stoklarý Listele");
            System.out.println("2. Satýn Al");
            System.out.println("3. Sepeti Göster");
            System.out.println("4. Sepetten Ürün Çýkar");
            System.out.println("0. Çýkýþ ve Fatura Yazdýr");
            System.out.print("Seçiminiz: ");

            int secim = tarayici.nextInt();

            if (secim == 1) {
                urunleriListele();
            } else if (secim == 2) {
                satinAl();
            } else if (secim == 3) {
                sepetiGoster();
            } else if (secim == 4) {
                sepettenCikar();
            } else if (secim == 0) {
                
                FileWriter yazici = new FileWriter("fatura.txt");
                
                yazici.write("--- SPORTLAB ALIÞVERÝÞ FÝÞÝ ---\n\n");
                
                double genelToplam = 0.0;
                if (sepettekiCesitSayisi > 0) {
                    for (int i = 0; i < sepettekiCesitSayisi; i++) {
                        double tutar = sepetBirimFiyatlari[i] * sepetAdetleri[i];
                        yazici.write(sepetUrunleri[i] + " x " + sepetAdetleri[i] + " Adet - Tutar: " + tutar + " TL\n");
                        genelToplam += tutar;
                    }
                    yazici.write("\n----------------------------\n");
                    yazici.write("ÖDENEN TOPLAM TUTAR: " + genelToplam + " TL\n");
                } else {
                    yazici.write("Alýþveriþ yapmadýnýz.\n");
                }
                
                yazici.close();
                
                devamEdilsinMi = false;
                System.out.println("Çýkýþ yapýldý. Faturanýz 'fatura.txt' dosyasýna yazdýrýldý.");
                
            } else {
                System.out.println("Hatalý seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    public static void urunleriListele() {
        System.out.println("\n--- ÜRÜN LÝSTESÝ ---");
        for (int i = 0; i < urunAdlari.length; i++) {
            if (urunAdlari[i] == null) continue; 
            
            String stokDurumu;
            if (urunStoklari[i] > 0) {
                stokDurumu = urunStoklari[i] + " adet";
            } else {
                stokDurumu = "TÜKENDÝ";
            }
            String guzelIsim = urunAdlari[i].replace("_", " ");
            System.out.println((i + 1) + "- " + guzelIsim + " (" + urunFiyatlari[i] + " TL) - Stok: " + stokDurumu);
        }
    }

    public static void satinAl() {
        urunleriListele();
        System.out.print("\nSatýn almak istediðiniz ürün numarasýný girin: ");
        int numara = tarayici.nextInt();
        int index = numara - 1;

        if (index >= 0 && index < urunAdlari.length && urunAdlari[index] != null) {
            if (urunStoklari[index] > 0) {
                String guzelIsim = urunAdlari[index].replace("_", " ");
                System.out.print(guzelIsim + " ürününden kaç adet almak istersiniz? ");
                int istenenAdet = tarayici.nextInt();

                if (istenenAdet > 0 && istenenAdet <= urunStoklari[index]) {
                    if (sepettekiCesitSayisi < sepetUrunleri.length) {
                        sepetUrunleri[sepettekiCesitSayisi] = guzelIsim;
                        sepetBirimFiyatlari[sepettekiCesitSayisi] = urunFiyatlari[index];
                        sepetAdetleri[sepettekiCesitSayisi] = istenenAdet;
                        urunStoklari[index] = urunStoklari[index] - istenenAdet;
                        sepettekiCesitSayisi++;
                        System.out.println("Baþarýlý: " + istenenAdet + " adet " + guzelIsim + " sepete eklendi.");
                    } else {
                        System.out.println("Sepetiniz tamamen doldu!");
                    }
                } else {
                    System.out.println("Hata: Yetersiz stok veya geçersiz sayý!");
                }
            } else {
                System.out.println("Üzgünüz, bu ürün stoklarýmýzda tükenmiþtir.");
            }
        } else {
            System.out.println("Geçersiz ürün numarasý!");
        }
    }

    public static void sepettenCikar() {
        if (sepettekiCesitSayisi == 0) {
            System.out.println("Sepetiniz boþ, çýkarýlacak ürün yok.");
            return;
        }

        System.out.println("\n--- ÇIKARILACAK ÜRÜNÜ SEÇÝN ---");
        for (int i = 0; i < sepettekiCesitSayisi; i++) {
            System.out.println((i + 1) + ". " + sepetUrunleri[i] + " (" + sepetAdetleri[i] + " adet)");
        }

        System.out.print("Seçiminiz (Ýptal için 0 giriniz): ");
        int secim = tarayici.nextInt();
        if (secim == 0) return;

        if (secim < 1 || secim > sepettekiCesitSayisi) {
            System.out.println("Geçersiz seçim!");
            return;
        }

        int sepetIndex = secim - 1;
        String urunAdi = sepetUrunleri[sepetIndex];
        int sepettekiAdet = sepetAdetleri[sepetIndex];

        System.out.print(urunAdi + " ürününden kaç adet çýkarmak istiyorsunuz? ");
        int cikarilacakAdet = tarayici.nextInt();

        if (cikarilacakAdet > 0 && cikarilacakAdet <= sepettekiAdet) {
            for (int i = 0; i < urunAdlari.length; i++) {
                if (urunAdlari[i] != null && urunAdlari[i].replace("_", " ").equals(urunAdi)) {
                    urunStoklari[i] += cikarilacakAdet;
                    break;
                }
            }
            sepetAdetleri[sepetIndex] -= cikarilacakAdet;
            System.out.println(cikarilacakAdet + " adet " + urunAdi + " sepetten çýkarýldý ve stoða eklendi.");

            if (sepetAdetleri[sepetIndex] == 0) {
                for (int j = sepetIndex; j < sepettekiCesitSayisi - 1; j++) {
                    sepetUrunleri[j] = sepetUrunleri[j + 1];
                    sepetAdetleri[j] = sepetAdetleri[j + 1];
                    sepetBirimFiyatlari[j] = sepetBirimFiyatlari[j + 1];
                }
                sepetUrunleri[sepettekiCesitSayisi - 1] = null;
                sepetAdetleri[sepettekiCesitSayisi - 1] = 0;
                sepetBirimFiyatlari[sepettekiCesitSayisi - 1] = 0;
                sepettekiCesitSayisi--;
            }
        } else {
            System.out.println("Hatalý miktar girdiniz!");
        }
    }

    public static void sepetiGoster() {
        System.out.println("\n--- SEPETÝNÝZ ---");
        if (sepettekiCesitSayisi == 0) {
            System.out.println("Sepetiniz boþ.");
        } else {
            double genelToplam = 0.0;
            for (int i = 0; i < sepettekiCesitSayisi; i++) {
                double urunToplamTutar = sepetBirimFiyatlari[i] * sepetAdetleri[i];
                System.out.println("- " + sepetUrunleri[i] + " | Adet: " + sepetAdetleri[i] + " | Birim Fiyat: " + sepetBirimFiyatlari[i] + " TL" + " | Tutar: " + urunToplamTutar + " TL");
                genelToplam = genelToplam + urunToplamTutar;
            }
            System.out.println("---------------------");
            System.out.println("GENEL TOPLAM: " + genelToplam + " TL");
        }
    }
}