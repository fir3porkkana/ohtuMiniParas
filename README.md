# Ohjelmistotuotannon miniprojekti

## Sovelluksen määrittely

- Tästä tulee sovellus, jonka avulla käyttäjä voi hallinnoida lukuvinkkejä
    - Lukuvinkkejä voidaan lisätä, selata, muokata ja poistaa
 - Lukuvinkkeihin voi sisällyttää sovelluksessa kuunneltavia äänikirjoja
    - Äänikirjan kuuntelu voidaan keskeyttää ja kuuntelua voidaan jatkaa keskeytetystä kohdasta
    - Äänikirjaan voidaan lisätä aikaleima, joka jää muistiin ja johon voidaan siirtyä myöhempänä ajankohtana
- Toteutuskieli: Java
- Käyttöliittymä: Paikallinen komentoriviltä suoritettava, graafinen (ks. kuva alla)

![](https://github.com/fir3porkkana/ohtuMiniParas/blob/master/Documentation/UI9-win.png)

[Äänikirjaesimerkki: Historian mp3-kurssi](https://yle.fi/aihe/artikkeli/2013/06/20/historian-mp3-kurssi)

[Kuvituskuva: Kuvia Suomesta](https://kuviasuomesta.fi/kategoria/kaupunki/)


## Asennusohjeet

Lataa uusin release [**Bookmarks_2.1**](https://github.com/fir3porkkana/ohtuMiniParas/releases/tag/2.1)

Suorita ladattu **jar** joko tuplaklikkaamalla tai komentoriviltä oikeassa hakemistossa komennolla

`java -jar Bookmarks_2.1.jar`


## Seuranta

[Product Backlog](https://docs.google.com/spreadsheets/d/1xw16uQBEmb93MxG8sn8DBW7L4hb3ol44io-by8Mnahs/edit?usp=sharing)

[Sprint Backlog](https://docs.google.com/spreadsheets/d/1xw16uQBEmb93MxG8sn8DBW7L4hb3ol44io-by8Mnahs/edit#gid=1851121910)

[Definition of Done](https://github.com/fir3porkkana/ohtuMiniParas/blob/master/Documentation/definitionOfDone.md)


## Testaus

Toimeksianto | Raportti 
-----------|----------
Konfiguraatio CircleCI:ssä | [![CircleCI](https://circleci.com/gh/fir3porkkana/ohtuMiniParas.svg?style=svg)](https://circleci.com/gh/fir3porkkana/ohtuMiniParas) 
Testikattavuus CodeCov:ssa (ei-rajattu) | [![codecov](https://codecov.io/gh/fir3porkkana/ohtuMiniParas/branch/master/graph/badge.svg)](https://codecov.io/gh/fir3porkkana/ohtuMiniParas)
