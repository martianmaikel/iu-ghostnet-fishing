# 🕸️ GhostNetFishing – Geisternetze melden und verwalten

Ein webbasiertes System zur Erfassung, Verwaltung und Bergung von Geisternetzen.

## 🚀 Funktionsübersicht

- **Geisternetz melden**
    - Anonyme oder personalisierte Meldung von Netzfunden mit Ort, Größe und optional Koordinaten.
  
- **Startseite Gäste**
    - Übersicht aller Netze 
    - Möglichkeit diese als verloren zu melden (nicht anonym)

- **Dashboard für Bergende nach Login (und Registrierung)**
    - Übersicht über (mir) zugewiesene Netze.
    - Statusänderung (*Geborgen*, *Verloren* usw.).
    - Möglichkeit, offene Netze zur Bergung zu übernehmen.

- **Statusverlauf & Historie**
    - Lückenlose Dokumentation aller Statusänderungen mit Zeitstempel und Person.

- **Berechtigungslogik**
    - Nur eingeloggte Nutzer können Netze übernehmen markieren.
    - Nur Nutzer, die ein Netz übernommen haben können, abgesehen von verschollen melden, Statusänderungen am Netz vornehmen
    - Anonyme Nutzer können Geisternetze melden oder als verschollen markieren (mit Kontaktdaten).

## 🧱 Technologie-Stack

- **Backend:** Jakarta EE 10 (JPA, CDI, JSF), Hibernate
- **Frontend:** JSF 4 (Jakarta Faces), PrimeFaces 13 (Jakarta-Version), Facelets
- **Datenbank:** MySql / H2 (im Server konfigurieren)
- **Build:** Maven
- **Server:** WildFly 30 (getestet) / Payara, TomEE (kompatibel)

## Starthilfe

Beim (lokalen) deployen der Applikation wird die Datenbank bereits mit Daten gefüllt (s.h. Datei src/main/java/com/example/ghostnetfishing/setup/Data.java).
Der Login kann für 2 Personen/Gruppen ausgeführt werden.
1. taucher@nord.de + test123
2. freedive@baltic.org + test321


