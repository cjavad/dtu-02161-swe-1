# LÆSMIG.md - GUI instruktioner.

Start programmet ved at køre `mvn javafx:run` i roden af projektet. Eller ved brug af tilsvarende 
kommando i en IDE for at starte HelloFX klassen.

Når du først starter programmet ville det være intenderet at først logge ind som admin bruger.

Det går man ved at trykke "Login" øverst til venstre. Og indtaste "admin".
Dette er den eneste indbyggede bruger i programmet til at starte med.

Herfra kan du oprette flere medarbejdere i højresiden af startskærmen ved at trykke "Tilføj medarbejder" her kan du tilføje unikke medarbejdere navne op til 4 karakterer lange.

Du kan trykke ind på den individuelle medarbejder og vælge at sætte deres ugentlige timer nederest på den side.
Det kan gøres enten som admin eller ved at logge ud, og logge ind igen med den ønskede medarbejdes initialer.

For at oprette et projekt skal man være logget ind som admin bruger og trykke "Tilføj projekt" i venstre side af startskærmen.

Man kan navigivere tilbage til startskærmen ved at trykke "Tilbage"

Efter du har oprettet et projekt ved et navn kan du tilføje et antal medarbejdere til markere den ønskede medarbejder og vælge at trykke på "<<" knappen.
For at fjerne anførelsen kan du trykke på ">>" knappen efter du har markeret den medarbejder der skal rykkes tilbages.

Herefter kan du sætte en projekt leder til projektet som kan fører flere handlinger, men det betyder også at den normale medarbejder ikke kan tilføje aktiviteter.

For at oprette en aktivitet kan du enten som admin, projektleder eller hvis ingen projektleder er anført, en normal medarbejder på projektet, oprette en aktivitet i højresiden af projekt siden.

Her kan du trykke på aktivitet for at komme til aktivitets siden hvor man kan sætte budgetteret tid, start og slut dato.

Ved at give aktiviteten et valid sæt af start og slut datoer ville medarbejder listen blive annoteret af 3 farver.

Grønne uanførte medarbejdere er medarbejderen med hele perioden af aktiviten ledig med mere end 0 timer om ugen.

Gule medarbejdere har nogle uger med mere end 0 timer

Og røde har ingen uger i perioden med timer til overs.

Man kan teste og manipulere denne funktionalitet ved at oprette flere projekter og aktiviteter, og give dem overlappende intervaller og budgetteret tid.

På denne måde kan man sammen med den ugentlige tid på medarbejden holde styr på hvem kan og ikke kan arbejde i perioden af aktiviteten intuitivt. 

Fra medarbejderens side kan man også se anførte aktiviter og tilknyttede projekter for hurtigt adgang.

## Noter.

- Programmet gemmer sit sidste interne state ved normal nedlukning i en fil kaldet `system.ser` som ville blive forsøgt at læst på opstart.