# 🥷 Nihongo Kids – Japanisch lernen für Kinder

Eine einfache, bunte Lern-App für Kinder. Sie läuft komplett offline, hat keine Werbung und braucht kein Konto.

## Was kann die App?

| Bereich | Inhalt |
|---|---|
| **Start-Animation** | Eine Manga-Figur mit Katana zerschneidet nach ca. 3 Sekunden den Bildschirm. Antippen überspringt sie. |
| **📚 Zeichen-Ordner** | Alle 46 Hiragana und 46 Katakana plus die Zeichen mit Strichen ゛ ゜ (142 Zeichen). Zu jedem Zeichen gibt es die Aussprache, ein Bild mit Beispielwort, eine kleine Merkgeschichte und Sprachausgabe. |
| **✏️ Nachmalen** | Malfläche mit grauer Vorlage und Hilfskreuz. Mit dem Finger nachmalen, Farben und Strichstärke wählen, rückgängig, löschen. Mit 👻 lässt sich die Vorlage ausblenden. |
| **🔤 Übersetzer** | Deutsche Buchstaben werden in Katakana und Hiragana umgeschrieben. Die Silben stehen einzeln in Kästchen. Zusätzlich gibt es ein Mini-Wörterbuch Deutsch → Japanisch (z. B. „Hund“ → いぬ). |
| **🎮 Spiele** | Bild → Zeichen, Zeichen → Bild, Hör genau hin und Memory. Für jede richtige Antwort gibt es einen ⭐. |
| **🗣️ Wörter-Welt** | Hallo-Wörter, Zahlen, Farben, Tiere, Essen und Familie. Antippen spricht das Wort vor. |
| **💡 Tipps** | Schreib-Regeln, die drei Schriften und ein 10-Tage-Lernplan. |

Die Gestaltung folgt dem, was beim Japanischlernen für Kinder gut funktioniert: kurze tägliche Einheiten, Merkbilder (Mnemonics), Hören und Sprechen, Nachmalen von Hand und spielerisches Wiederholen.

## App installieren (Android)

1. Öffne im Repo den Bereich **Releases** und lade **NihongoKids.apk** aus „Nihongo Kids APK (neueste Version)“ herunter.
2. Öffne die Datei und erlaube die Installation aus dieser Quelle.
3. Für die Sprachausgabe muss auf dem Gerät eine japanische Stimme installiert sein: Einstellungen → Sprachausgabe → Japanisch.

Die APK wird automatisch von GitHub Actions gebaut (Datei `.github/workflows/build-apk.yml`). Unter **Actions → APK bauen → Run workflow** lässt sich ein neuer Build jederzeit starten.

## Als Web-App (ohne APK)

`index.html` ist die komplette App in einer einzigen Datei. Sie lässt sich im Browser öffnen und über „Zum Startbildschirm hinzufügen“ wie eine App installieren (`manifest.webmanifest`, `sw.js` und die Icons sind dabei). Mit GitHub Pages (Settings → Pages → Branch `main`) bekommst du eine Adresse, die auch auf iPhone und iPad funktioniert.

## Aufbau des Projekts

```
index.html            die ganze App (HTML, CSS, JavaScript)
manifest.webmanifest  Web-App-Beschreibung
sw.js                 Offline-Speicher
icons/                App-Icon (SVG und PNG)
android/              Android-Projekt (WebView mit Sprachausgabe), baut die APK
.github/workflows/    automatischer APK-Build
```

Die APK enthält dieselbe `index.html`; der Build kopiert sie vor dem Bauen nach `android/app/src/main/assets/`. Änderungen macht man deshalb nur in `index.html`.

## Datenschutz und Kindersicherheit

- Die Android-App hat **keine Internet-Berechtigung** und lädt nichts nach.
- Es gibt keine Werbung, keine Käufe, kein Tracking und keine Links nach draußen.
- Gespeichert werden nur die gesammelten Sterne auf dem Gerät.

## Grenzen

- Die Zeichen werden zum Nachmalen als Vorlage gezeigt. Animierte Strichfolgen (welcher Strich zuerst) gibt es noch nicht; die Tipps-Seite erklärt die Grundregeln.
- Der Übersetzer schreibt nach Gehör um. Er kennt keine Kanji und keine Grammatik.
