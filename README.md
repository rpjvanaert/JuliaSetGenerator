# 2D-Computergraphics Eindopdracht
>Ralf van Aert

- Studentennummer: 2163995
- Studenten-mail: rpj.vanaert@student.avans.nl

### **Inleiding**
s

### **Mandelbrot Set Tab**
De Mandelbrot Set Tab kan de Mandelbrot Set Zooms renderen met een resolutie van 1920 bij 1080 pixels. Er is in te stellen wat het focus punt is, het punt in het midden van de render. Er kan gezoomt worden op de afbeelding, maar er wordt alleen maar gerendered naar de ingestelde variabelen in de TextFields.

Als je dus meer op een punt in wilt zoomen, met render, dan moet je de stepsize aanpassen. Stepsize is het verschil tussen 2 naast liggende pixels, het inverse van een zoom dus.

Naast al dat is er ook in te stellen wat de maximum hoeveelheid iteraties is per pixel en de Hue Cycle Speed. Hue Cycle Speed is de snelheid waarmee de kleur veranderd per iteratie.

Ten slot is er de mogelijkheid om de afbeelding op te slaan, de afbeelding wordt opgeslagen in het project folder genaamd "SaveFolder".
![MandelbrotSetTab](README-Images/mandelbrotsetExample.png)

### **Julia Set Tab**
De Julia Set Tab is een tab die Julia sets genereerd. Elke Julia Set heeft een eigen coördinaat vanwaar het uitgerekend is. Dit punt noemen we C. Punt C is complex en is in te stellen via C P. Real en C P. Imag TextFields. Naast dat werkt de Julia Set Tab precies hetzelfde als de Mandelbrot Set Tab.

De Julia Set Tab heeft dus ook een focus punt, stepsize, iteraties en Hue Cycle Speed. Deze tab kan dus ook een afbeelding opslaan, ook dit gebeurt in dezelfde folder.
![JuliaSetTab](README-Images/juliasetExample.png)

### **Other Fractals Tab**

![SierpinskiTriangleTab](README-Images/sierpinskiExample.png)
![TreeFractalTab](README-Images/treefractalExample.png)