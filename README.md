#### Bu proje iki test modulü içermektedir.
- Beymen Web Otomasyon Projesi
- Trello Api Otomasyon Projesi 


## Beymen Web Otomasyon Projesi
### Proje kurulumu
- Java Kurulumu: Bilgisayarınıza Java JDK kurulu değilse, Oracle'nin resmi web sitesinden Java JDK'yi indirip kurun.
- Maven Kurulumu: Maven, bağımlılık yönetimi ve proje yönetimi için kullanılır. Apache Maven'in resmi web sitesinden Maven'i indirip kurun.
- Projeniyi GitHub üzerinden güncel halini indirmeniz gerekir.
- Projenizi geliştirebilmek veya içeriğini görüntülemek için bir IDE seçmeniz gerekecek. Örneğin, IntelliJ IDEA gibi popüler Java IDE'lerinden birini seçebilirsiniz.
- POM.xml dosyasının içeriğini kontrol edin.
- Dosyasının Düzenlenmesi: Maven projesi oluşturduktan sonra pom.xml dosyasını açarak, Selenium WebDriver, JUnit ve Log4J gibi bağımlılıkları güncel hallerini ekleyin.
- Google Chorome üzerinden test gerçekleştirilir.



            Web otomasyon testini koşturmak için terminalden aşağıdaki komutu çalıştırın 
```
mvn -Dtest=Beymen test
```
```
mvn test
```

  (Maven, projeleri yönetmek ve derlemek için POM dosyasını (pom.xml) kullanır.
Bu dosya, projenin, bağımlılıklarının ve derleme sürecinin bilgilerini içerir. 
Herhangi bir Maven komutunu çalıştırmadan önce, POM dosyasının bulunduğu doğru dizinde olduğunuzdan emin olun.)
  

### Proje Amacı              

1. Beymen.com sitesini açar ve ana sayfanın açıldığını kontrol eder.
2. Açılan cokie notificationları kapatır.
3. Excel dosyasından alınan kelimelerle arama yapar, sonuçları kontrol eder.
4. Rastgele bir ürün seçer, ürün bilgisi ve fiyatını bir txt dosyasına yazar.
5. Seçilen ürünü sepete ekler ve sepete gider.
6. Ödeme sayfasında ki fiyat ile Ürün fiyatını karşılaştırıp doğruluk kontrolü yapar.
7. Sepetteki ürün adedini artırır ve doğruluğunu kontrol eder.
8. Üründen sadece bir tane varsa siler ve 2 önceki sayfaya döner.
9. 4'üncü adımdan itibaren testi tekrarlar.
10. Ürünü sepetten siler ve sepetin boş olduğunu kontrol eder.

Bu proje, ana dizin yapısında `main` ve `test` klasörleri içerir. `main` klasörü altında Java kaynak kodları, `resources` altında test verileri bulunurken, `test` klasörü altında test sınıfları ve yardımcı sınıflar yer alır. Proje, Beymen web otomasyonunu gerçekleştirmek için geliştirilmiştir. Başarılar dilerim!
`tests` Sınıfı altında tüm test senaryoları. `Utils` altında Excelden okuma için yardımcı clas bulunur. 

`pages` içeri ise 
- CartPage
- HomePage
- ProductPage

`CartPage` sınıfında kullanılan metodlar:

1. `parsePrice` //Toplam fiyatta sadece miktarı(sayı) tutar
3. `isStockAvailable`// Stock kontrolü 1'den fazla ürün var mı 
4. `emptyCart`     // Sepeti boşalt
5. `isCartEmpty`     // Sepetin boş olup olmadığını kontrol eder

`HomePage` sınıfında kullanılan bazı metodlar:

1. handleCookiesAndPopups → Çerez ve Popup'ları kapatır.
2. clickOnRandomProduct → rastgele Urun Sec
6. clearSearchBox → Arama Kutusunu Temizler

`ProductPage` içerisinde ürünün değerlerini tutar ve istenilen değerleri dönmesi için yazılan metodlar bulunur.

### Kullanım

1. Projenin ana sınıfı olan `Main.java` dosyasını çalıştırarak otomasyonu başlatın.
2. Otomasyon, FullTest adım adım www.beymen.com üzerinde belirtilen senaryoyu uygulayacaktır.
3. Excel dosyasındaki kelimeleri değiştirmek istiyorsanız, `test-data.xlsx` dosyasını güncelleyin.
4. Sonuçları `product.txt` dosyasında bulabilir ve kontrol edebilirsiniz.


Projenin düzgün çalışabilmesi için gerekli bağımlılıkları `pom.xml` dosyasından kontrol edin. 


### Trello Api Otomasyon Projesi

Bu proje, Trello üzerinde board, kart ve liste işlemlerini otomatikleştirmek amacıyla geliştirilmiştir.

1. **Ana Sınıflar**
    - `Main.java`: Projenin ana sınıfı, Trello işlemlerini başlatır.
    - `TrelloApi.java`: Trello API ile etkileşim sağlayan sınıf.
    - `TrelloTest.java`: Trello işlemlerini test eden JUnit test sınıfı.

2. **Yapılandırma Dosyası**
    - `config.properties`: Proje yapılandırma ayarlarını içerir. Trello API anahtarları (key,token) ve diğer konfigürasyonlar burada bulunur.

3. **Test ve Util Sınıfları**
    - `TrelloTest.java`: Trello işlemlerini test eden JUnit test sınıfı.
    - `Config.java`: Konfigürasyonda Api Key ve Token bilgisi çağıran  yöneten yardımcı sınıf.

Trello otomasyon testini koşturmak için terminalden aşağıdaki komutu çalıştırın
```
mvn test
```
## Senaryo Adımları

1. Trello test senaryoları metodları  `TrelloTest.java`'yı çalıştır ve içinde çağrılır.
2. Trello üzerinde yeni bir board oluşturmak için `TrelloTest.java` sınıfının `createBoard` metodunu kullanın.
2. Oluşturulan board'a iki kart eklemek için `TrelloTest.java` sınıfının `addCardsToList` metodunu kullanın.
4. Oluşturulan kartlardan bir tanesini rastgele güncellemek için `TrelloTest.java` sınıfının `updateRandomCard` metodunu kullanın.
5. Oluşturulan kartları silmek için `TrelloTest.java` sınıfının `deleteCards` metodunu kullanın.
5. Oluşturulan board'u silmek için `TrelloTest.java` sınıfının `deleteBoard` metodunu kullanın.

## Notlar
- Proje çalıştırılmadan önce `config.properties` dosyasındaki Trello API anahtarlarını güncelleyin.


