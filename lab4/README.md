# Labb 4

* Samma person kan spela flera roller, men samma roll kan endast innehas av en person
* Varje scen måste ha minst 2 roller
* Varje skådespelare får bara ha en roll per scen
* Varje roll måste förekomma i minst en scen
* p1 och p2 får minst en roll var och de rollerna ska åtminstone delta i en scen var
  * p1 och p2 får inte spela i samma scen

Rollbesättningsproblemet är att avgöra ifall alla roller kan besättas med de skådespelare som finns till hands.
Givet i indata är manuset, antalet skådespelare, antalet roller samt antalet scener. Utifrån denna information ska det avgöras om en rollbesättning kan göras som uppfyller villkoren listade ovan.

Själva uppgiften är att implementera en reduktion:
* Visa att rollbesättningsproblemet är NP-svårt genom att reducera ett känt NP-fullständigt problem
* Karpreduktion ska användas, dvs. reduktionsprogrammet ska ta en instans av ett A-problem och som utdata generera en instans av ett B-problem.

Frågor:
* Varför ligger problemet i NP?

  Vi behöver visa att det går att verifiera på polynomisk tid (med avseende på längden av indata som är 3 + n\*(k+1) +  s\*(n+1)). Om vi vill verifiera en lösning så kan vi först gå igenom rollbesättningen i probleminstansen och kolla så att varje roll endast ges till en skådespelare: tidskomplexitet O(n\*(k+1)) eftersom vi måste gå igenom n rader med högst k + 1 siffor på varje rad. Sedan går vi igenom alla scener i probleminstansen och kollar så att alla krav i uppgiftslydelsen uppfylls, alltså att varje skådespelare bara har en roll i en scen, varje roll förekommer i minst en scen, p1 och p2 är med i minst en scen var men aldrig med i samma scen som varandra: tidskomplexitet O(s\*(n+1)) eftersom vi måste gå igenom s rader med högst n + 1 siffror på varje rad. Tidskomplexiteten för att verifiera en lösning blir alltså O(n\*(k+1) + s*(n+1)) vilket är polynomiskt med avseende på längden av hela indatan.


* Vad är komplexiteten för reduktionen?

  createNodesAndEdges() är polynomisk (linjär) med avseende på v och e. Tidskomplexitet: O(v + e)

  possibleActors() är polynomisk (kvadratisk) med avseende på v. Tidskomplexitet O(v^2)

  output() är polynomisk med avseende på v och m. Tidskomplexitet O(v\*m)

  Så sammanfattningsvis så blir tidskomplexiteten för hela reduktionen polynomisk.

**Bevis för att reduktionen är korrekt:**

Den minsta möjliga ja-instansen för graffärgningsproblemet är:  
1 (en nod)  
0 (inga kanter)  
1 (en färg)  
Den minsta möjliga ja-instansen för rollbesättningsproblemet är:
3 (3 roller)  
2 (2 scener)  
3 (3 skådespelare)  
1 1  
1 2  
1 3  
2 1 3  
2 2 3   
Följande bild representerar exempel på ja-instanser för respektive problem:
![](./ja_instans.png)

Följande ekvivalenser mellan parametrarna från graffärgningsproblemet och rollbesättningsproblemet kändes mest naturlig.   
färger	<=> skådespelare  
nod 	<=> roll  
kant mellan nod a och nod b <=> a och b är i samma scen  
k (skådespelare som kan besätta rollerna) <=> m (antalet färger i grafen)  

Utifrån detta blev det uppenbart att vi var tvungna att justera grafen (inputen till graffärgningsproblemet) enligt bilden. Därmed behövde vi i det här fallet lägga till 2 noder per befintlig nod ifrån inputgrafen samt lägga till en kant från den befintliga noden till de två nya noderna. Vi behövde även öka antalet färger med 2. Vi kan se de nya noderna som nya roller som endast kan besättas av divorna p1 och p2. På så sätt säkerställer vi att divorna alltid medverkar i minst en roll var, och att de inte spelar mot varandra eftersom de nya noderna aldrig kommer att ha kanter mellan sig. Eftrsom både p1 och p2 måste besätta minst en av de nya rollerna så kommer det alltså krävas exakt 2 nya färger. Vi kan utifrån det här slutleda att om vi har en ja-instans som input så kommer vi även få en ja-instans efter dessa ändringar av grafen, eftersom de nya färgerna (skådespelarna p1 och p2) inte har använts innan och därmed kan avändas för att färga alla de nya noderna. Vi använder inte de nya färgerna för att färga befintliga noder. Därför kommer också en nej-instans att ge en nej-instans efter ändringarna på grafen, eftersom vi inte lägger till några nya färger att färga befintliga noder med.

 Den här metoden säkerställer också att oavsett vilken input till graffärningsproblemet som vi får, så kommer vår output till rollbesättningsproblemet alltid att bli minst lika stor som den minimala ja-instansen för rollbesättningsproblemet.



Vi väljer att reducera problemet \*graffärgning\* till \*rollbesättningsproblemet* och visar därmed att man kan lösa graffärgning med rollbesättningsproblemet.
* Detta kommer innebära att vi ska skriva en reduktionsalgoritm som tar indata till graffärgningsproblemet och spottar ut ekvivalent (som har samma ja/nej svar) indata till rollbesättningsproblemet.


**Graffärgning**
*Indata:* En oriktad graf och ett antal färger m. Isolerade hörn och dubbelkanter kan förekomma, inte öglor.

*Fråga:* Kan hörnen i grafen färgas med högst m färger så att inga grannar har samma färg?

*Indataformat:*
Rad ett: tal V (antal hörn)
Rad två: tal E (antal kanter)
Rad tre: mål m (max antal färger)
En rad för varje kant (E stycken) med kantens ändpunkter (hörnen numreras från 1 till V)

## Representation
roller (n)

scener (s)

skådespelare (k)

n rader av möjliga skådespelare för roller

s rader av rollerna i varje scen

```java
// färger 										<=> skådespelare
// nod.role 									<=> roll
// kant mellan nod a och nod b 					<=> a.role och b.role är i samma scen
// k (skådespelare som kan besätta rollerna) 	<=> m (antalet färger i grafen)

// Behövs för utdata:
// roller (n)
// scener (s)
// skådespelare (k)
// n rader av möjliga skådespelare för roller
// s rader av rollerna i varje scen
```
