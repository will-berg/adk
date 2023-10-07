# Labb 5
Du ska välja att implementera valfri heuristik som löser följande problem:

**konstruktionsproblemet**: *Vilka skådespelare ska ha vilka roller för att lösa rollbesättningsinstansen med så få skådespelare som möjligt?*

**Indataformatet** för rollbesättningsproblemet är detsamma som i labb 4. Divorna är 1 och 2:

*n* (roller)

*s* (scener)

*k* (skådespelare)

*n* rader, börjar med ett *tal som anger antalet efterföljande tal på raden*, följt av *de möjliga skådespelarnas nummer* (vilka skådespelare kan besätta vilka roller)

*s* rader, börjar med ett *tal som anger antalet efterföljande tal på raden*, följt av *tal som representerar vilka roller som är med i vilka scener*


**Utdataformat** för konstruktionsproblemet:

1. *a* (antal skådespelare som fått roller)
2. a rader, en rad för varje skådespelare (som fått roller) med *skådespelarens nummer*, *antalet roller skådespelaren tilldelats* samt *numren på dessa roller*

Problemet ska lösas enligt villkoren som specificerats för rollbesättningsproblemet, dvs. divorna måste vara med men får inte spela i samma scen, ingen roll får spelas av flera personer, och ingen skådespelare får spela mot sig själv i någon scen (inga monologer). Bättre heuristik (dvs. färre skådespelare) ger bättre betyg. Endast lösbara instanser kommer att ges som indata, men för att heuristiken i polynomisk tid säkert ska kunna hitta en lösning så är det tillåtet att använda högst *n-1* särskilda *superskådisar* med nummer *k+1, k+2, ...* Varje superskådis kan spela vilken roll som helst, men kan bara spela en enda roll.
