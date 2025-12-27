import java.util.Scanner;

public class odev {
    static String[] urunAdlari = {"Pro Maç Topu", "Krampon", "Milli Forma", "Kaleci Eldiveni", "Tozluk"};
    static double[] urunFiyatlari = {1200.0, 3500.0, 900.0, 600.0, 150.0};
    static int[] urunStoklari = {10, 5, 20, 8, 50}; 

    static String[] sepetUrunleri = new String[10];
    static double[] sepetBirimFiyatlari = new double[10];
    static int[] sepetAdetleri = new int[10]; 
    static int sepettekiCesitSayisi = 0; 

    static Scanner tarayici = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        boolean devamEdilsinMi = true;

        System.out.println("--- SPORTLAB STOKLU SATIÞ SÝSTEMÝ ---");

        while (devamEdilsinMi) {
            System.out.println("\n1. Ürünleri ve Stoklarý Listele");
            System.out.println("2. Satýn Al");
            System.out.println("3. Sepeti Göster");
            System.out.println("4. Sepetten Ürün Çýkar"); 
            System.out.println("0. Çýkýþ");
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
                devamEdilsinMi = false;
                System.out.println("Çýkýþ yapýldý.");
            } else {
                System.out.println("Hatalý seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    public static void urunleriListele() {
        System.out.println("\n--- ÜRÜN LÝSTESÝ ---");
        for (int i = 0; i < urunAdlari.length; i++) {
    
            String stokDurumu;
            if (urunStoklari[i] > 0) {
                stokDurumu = urunStoklari[i] + " adet";
            } else {
                stokDurumu = "TÜKENDÝ";
            }
            
            System.out.println((i + 1) + "- " + urunAdlari[i] + 
                               " (" + urunFiyatlari[i] + " TL) - Stok: " + stokDurumu);
        }
    }

    public static void satinAl() {
        urunleriListele(); 
        System.out.print("\nSatýn almak istediðiniz ürün numarasýný girin: ");
        int numara = tarayici.nextInt();
        
        int index = numara - 1; 

        if (index >= 0 && index < urunAdlari.length) {
            
            if (urunStoklari[index] > 0) {
                
                System.out.print(urunAdlari[index] + " ürününden kaç adet almak istersiniz? ");
                int istenenAdet = tarayici.nextInt();

                if (istenenAdet > 0 && istenenAdet <= urunStoklari[index]) {
                    
                    
                    if (sepettekiCesitSayisi < sepetUrunleri.length) {
                        
                        sepetUrunleri[sepettekiCesitSayisi] = urunAdlari[index];
                        sepetBirimFiyatlari[sepettekiCesitSayisi] = urunFiyatlari[index];
                        sepetAdetleri[sepettekiCesitSayisi] = istenenAdet;
                        
                        urunStoklari[index] = urunStoklari[index] - istenenAdet;
                        
                        sepettekiCesitSayisi++; 
                        
                        System.out.println("Baþarýlý: " + istenenAdet + " adet " + urunAdlari[index] + " sepete eklendi.");
                    } else {
                        System.out.println("Sepetiniz tamamen doldu!");
                    }

                } else {
                    System.out.println("Hata: Yetersiz stok veya geçersiz sayý! (Mevcut Stok: " + urunStoklari[index] + ")");
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

        System.out.print("Seçiminiz (Ýptal için herhangi bir sayý giriniz): ");
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
                if (urunAdlari[i].equals(urunAdi)) {
                    urunStoklari[i] += cikarilacakAdet;
                    break;
                }
            }

            sepetAdetleri[sepetIndex] -= cikarilacakAdet;
            System.out.println(cikarilacakAdet + " adet " + urunAdi + " sepetten çýkarýldý ve stoða eklendi.");

            if (sepetAdetleri[sepetIndex] == 0) {
                for (int j = sepetIndex; j < sepettekiCesitSayisi - 1; j++) {
                    sepetUrunleri[j] = sepetUrunleri[j+1];
                    sepetAdetleri[j] = sepetAdetleri[j+1];
                    sepetBirimFiyatlari[j] = sepetBirimFiyatlari[j+1];
                }
                
                sepetUrunleri[sepettekiCesitSayisi - 1] = null;
                sepetAdetleri[sepettekiCesitSayisi - 1] = 0;
                sepetBirimFiyatlari[sepettekiCesitSayisi - 1] = 0;

                sepettekiCesitSayisi--; 
            }
        } else {
            System.out.println("Hatalý miktar girdiniz! (Mevcut: " + sepettekiAdet + ")");
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
                
                System.out.println("- " + sepetUrunleri[i] + 
                                   " | Adet: " + sepetAdetleri[i] + 
                                   " | Birim Fiyat: " + sepetBirimFiyatlari[i] + " TL" +
                                   " | Tutar: " + urunToplamTutar + " TL");
                
                genelToplam = genelToplam + urunToplamTutar;
            }
            
            System.out.println("---------------------");
            System.out.println("GENEL TOPLAM: " + genelToplam + " TL");
        }
    }
}