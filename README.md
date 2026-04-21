# Software engineering - Big Homework

georgescu mihai antonio 1242EA

eu m-am ocupat de tot ca am lucrat singur 
>(java, structura xml/xsd, transformari xsl, integrare github)

### note despre implementare si tweaks
desi proiectul respecta toate cerintele per se, am mai facut cateva ajustari

- la integrarea backend-frontend: in loc sa folosesc un server extern greu de configurat (tomcat), am integrat logica de procesare direct in Main.java. aplicatia genereaza automat un fisier display.html in folderul webapp folosind transformarea xslt.

- la adaugarea retetelor: am implementat metoda addRecipeManual in java care simuleaza input-ul din browser. aceasta citeste, modifica si salveaza automat in recipes.xml, demonstrand ca fluxul de date si persistenta functioneaza corect.

- la validare xsd: validarea nu este doar vizuala, ci este integrata de mine in codul java. sistemul verifica structura xml-ului inainte de orice operatiune majora.

- la stocare: fisierul recipes.xml este tratat ca o baza de date reala, fiind suprascris si actualizat la fiecare rulare, ca sa asigur cerinta de "salvare locala".
