# Paardenstalapplicatie "Blaze of Glory" - Eindopdracht Backend - Novi Hogeschool

---

Voor dit project is een installatiehandleiding geschreven, om dit project te kunnen gebruiken samen met het bijbehorende 
frontend project. Deze benopte handleiding beperkt zich tot het kunnen installeren van de backend om zo de frontend te kunnen 
gaan gebruiken. Voor de backendleerlijn is een uitgebreidere handleiding geschreven .De handleiding is geschreven 
voor gebruikers van __Windows__ . 



## Inhoudsopgave

---

1. [Inleiding](#1-inleiding)
2. [Benodigdheden](#2-benodigdheden)
3. [Installatie instructies](#3-installatie-instructies)


## 1. Inleiding

---

Deze paardenstalapplicatie is ontwikkeld als eindopdracht voor de Backend Leerlijn van Novi Hogeschool. Het betreft
een webapplicatie met informatie over een pensionstal voor paarden en een klant- en beheerdersomgeving. Hier kunnen
klanten online een abonnement op een stal aanvragen of annuleren en hun gegevens inkijken en wijzigen. De staleigenaar
kan abonnementsaanvragen- en annuleringsverzoeken inwilligen.

Deze backend, in Java ontwikkeld, is te gebruiken in combinatie met de voor de frontend leerlijn ontwikkelde frontend. 
[link](https://github.com/Pincodeert/eindopdracht-frontend-novi-paardenstal). Deze handleiding beperkt zich daarom tot 
de benodigdheden en installatie instructies die nodig zijn om deze backend te gebruiken in combinatie met de ontwikkelde 
frontend.


## 2. Benodigdheden

---

Om de software op een lokale computer te kunnen gebruiken is het nodig om onderstaande programma's/onderdelen te
installeren op een lokale computer. Sommige zijn al standaard aanwezig of worden standaard met een ander programma
geïnstalleerd. Het is belangrijk dat de onderdelen op dezelfde machine worden geinstalleerd als die waarop de frontend 
gaat draaien.

* __IntelliJ IDEA__

  Dit is een IDE voor de backend waarin de code ontwikkeld is. Deze applicatie is gemaakt in de gratis
  te downloaden Community Edition van IntelliJ.


* __JDK__

  JDK staat voor Java Development Kit. Java is de programmeertaal waarin de bijbehorende backend ontwikkeld is. Om Java
  op een pc of laptop te kunnen uitvoeren is het noodzakelijk om een Java Development Kit te downloaden. De backend maakt
  gebruik van versie JDK-17. Deze is gratis te downloaden en gebruiken en kent een long-term support.


* __Localhost__

  Ook de backend is niet gehost op een externe server en draait alleen op localhost op de eigen lokale computer. De
  backend maakt hiervoor gebruik van poort 8080.


* __PostgreSQL__

  Dit is een relationele database die nodig is om data te kunnen opslaan en ophalen en koppelingen tussen de data op te
  slaan. PostgreSQL kan ook gratis gedownload worden. Tijdens de installatie wordt eveneens de tool pgAdmin
  meegeïnstaleerd.


* __pgAdmin__

  Dit is een grafische gebruikersinterface voor de database. Deze tool wordt tijdens de installatie van PostgreSQL
  meegeïnstalleerd.


## 3. Installatie instructies

---

Deze applicatie kan gebruikt worden met een zelf ontwikkelde frontend. Belangrijk is dat zowel frontend als backend op 
dezelfde machine worden geinstalleerd en kunnen draaien.


1. Download en installeer IntelliJ via deze [link](https://www.jetbrains.com/idea/download/?section=windows). Kies voor
   de Community Edition. Kies bij `Create Associations` voor `.java` en `.pom`. Wanneer de installatie voltooid is, open
   IntelliJ.


2. Installeer de Java Development Kit. De paardenstalapplicatie maakt gebruik van versie JDK-17. Click in IntelliJ op
   `New Project` en kies in het dropdown-menu van de JDK voor `+Add SDK`. Kies in het menu dat zich nu opent voor
   `Download JDK` en vervolgens voor de openjdk versie 17 Amazon Coretto en click op `download` en vervolgens op `OK`.


3. Download en installeer PostgreSQL via deze [link](https://www.postgresql.org/download/). Kies de meest recente
    versie voor Windows. Open het gedownloade bestand en volg de stappen van de setup wizard. Selecteer de gewenste
    componenten om te selecteren. Selecteer in ieder geval de opties `PostgreSQL Server` en `pgAdmin4`. Vul
    de poort in waar de server naar moet luisteren. Deze staat standaard op port `5432`. Als laatste moet er nog een
    password worden gekozen om toegang te krijgen tot de database. __Let op!:__ dit password is straks in stap 12 ook
    nodig om in de application.properties in IntelliJ te worden aangepast.


4. Zet de database klaar, zodat straks PostgreSQL gebruikt kan worden in het project in IntelliJ. Open hiervoor de
    hierboven gesinstalleerde pgAdmin. Vul in het scherm dat nu openklapt het eerder opgegeven password in. Click
    in het linker menu op `>Databases`, kies `Create` en vervolgens rechts hiervan `Database...`. Vervolgens opent zich
    een scherm waarin de nieuwe database aangemaakt gaat worden. De `Owner` staat al ingesteld op `postgres`. Dit is ook
    de username die straks in stap 12 in de application.properties in IntelliJ geconfigureerd moet worden. Vul een naam
    in bij `Database`, bv. paardenstal. Ook deze database naam moet straks worden aangepast in het project in IntelliJ.
    Click vervolgens op `Save`.


5. Voeg de brondcode van de backend toe in IntelliJ. Indien de broncode niet beschikbaar is gesteld kan via deze
    [link](https://github.com/Pincodeert/eindopdracht-backend-novi-paardenstal) een ZIP-bestand gedownload worden door
    op de groene button `code` te clicken en vervolgens op `Download ZIP`. Pak vervolgens het (meegeleverde) ZIP-bestand
    uit en sla het op in een map IdeaProjects. Open Intellij en click op `Open`. Selecteer vervolgens het bestand
    `eindopdracht-backend-novi-paardenstal` en binnen deze map weer `pom.xml` en click vervolgens op `OK` en open het
    project. De paardenstalapplicatie opent zich nu on IntelliJ. Selecteer nu in de linker menu-balk de projectmap `src`
    en ga binnen de `main`-map naar de map `resources` en click vervolgens op `application.properties`. En maak de
    volgende aanpassingen in dit bestand (zie stap 10 en 11). Let op! GEEN spaties achter = teken.
    `spring.datasource.url=jdbc:postgresql://localhost:5432/plaats-hier-de-naam-van-de-database`
    `spring.datasource.username=plaats-hier-de-naam-van-de-owner`
    `spring.datasource.password=plaats-hier-het-gekozen-password`

### Om de applicatie te draaien

13. Run de backend code door in IntelliJ in de menubalk boven op de `groene pijl` te clicken of gebruik `Shift+F10`. De
    backend is succesvol opgestart wanneer een dergelijke melding verschijnt:
    `Started PaardenstalApplication in 4.449 seconds (JVM running for 4.815)`