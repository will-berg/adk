# Labb 2

## Spåra utförda operationer i matrisen
Operationerna som genomförs kan utläsas ur matrisen X-Y (som skapas när X ska omvandlas till Y) genom att börja i matrisens sista cell - alltså editeringsavståndet mellan X och Y - och se var värdet kommer ifrån. Om värdet kommer uppifrån motsvarar detta en "tilläggning" av bokstaven i Y framför bokstaven i X ovan. Om värdet kommer från vänster innebär det en "borttagning" av bokstaven i X. Kommer värdet diagonalt uppifrån motsvarar det ett "byte" från bokstaven i X till bokstaven i Y. Om bokstaven i X och Y är likadana kan detta också innebära att de är ekvivalenta och i detta fall har självklart ingen operation utförts. Fortsätt på detta vis tillbaks till startcellen.


**Vilken del av matriserna för "labd"-"blad" och "labs"-"blad" skiljer?**
Svar: sista raden endast

**Allmänt sett, vilken del av matriserna för Y-X och Z-X skiljer när orden Y och Z har samma första p bokstäver?**
Svar: De första *p* raderna kommer vara densamma i båda matriserna och vi kan börja på rad p + 1

Vi väljer det minsta valet utav våra operationer insert, delete och replace (vilka cellerna uppåt, till vänster och diagonalt representerar) och adderar 1 till det för att de två karaktärerna inte matchar vilket innebär att vi då behöver utföra en operation.
