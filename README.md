# 🕸️ GhostNetFishing – Geisternetze melden und verwalten

Ein webbasiertes System zur Erfassung, Verwaltung und Bergung von Geisternetzen.

## 🚀 Funktionsübersicht

- **Geisternetz melden**
    - Anonyme oder personalisierte Meldung von Netzfunden mit Ort, Größe und optional Koordinaten.

- **Dashboard für Bergende**
    - Übersicht über zugewiesene Netze.
    - Statusänderung (z. B. *Geborgen*, *Nicht auffindbar*).
    - Möglichkeit, offene Netze zur Bergung zu übernehmen.

- **Statusverlauf & Historie**
    - Lückenlose Dokumentation aller Statusänderungen mit Zeitstempel und Person.

- **Berechtigungslogik**
    - Nur eingeloggte Nutzer können Netze übernehmen oder als *nicht auffindbar* markieren.
    - Anonyme Nutzer können Geisternetze melden oder als verschollen markieren (mit Kontaktdaten).

## 🧱 Technologie-Stack

- **Backend:** Jakarta EE 10 (JPA, CDI, JSF), Hibernate
- **Frontend:** PrimeFaces 13, Facelets
- **Datenbank:** PostgreSQL / H2 (abhängig von Konfiguration)
- **Build:** Maven
- **Server:** WildFly 30 / Payara / TomEE (kompatibel)

## 📁 Projektstruktur

