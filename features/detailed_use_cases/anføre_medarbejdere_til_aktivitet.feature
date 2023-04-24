# En liste af ID'er af medarbejdere, der tilhører aktiviteten
# Når man anfører en medarbejder til aktiviteten, så tilføjer vi en aktivitetstuple til medarbejder objektet i medarbejder DB'en med samme ID, som det der er blevet anført

Feature:
  Background:
    Given projektet "Projekt 1" er oprettet med løbenummer 1 og årstal 2023
    And en medarbejder "aaaa" eksisterer og er tilknyttet projektet "Projekt 1"
    And en medarbejder "bbbb" eksisterer og er tilknyttet projektet "Projekt 1"
    And en aktivitet "Aktivitet 1" eksisterer på projektet med id'et "2023-01" med start datoen "01-2023" og slut datoen "10-2023"
    And medarbejderen "aaaa" er projektleder på projektet "Projekt 1"
    And projektet "Projekt 2" er oprettet med løbenummer 2 og årstal 2023
    And en aktivitet "Aktivitet 1" eksisterer på projektet med id'et "2023-02" med start datoen "01-2023" og slut datoen "10-2023"
    And en medarbejder "cccc" eksisterer og er tilknyttet projektet "Projekt 2"
    And projektet "Projekt 3" er oprettet med løbenummer 3 og årstal 2023
    And en aktivitet "Aktivitet 1" eksisterer på projektet med id'et "2023-03" med start datoen "01-2023" og slut datoen "10-2023"
    And en medarbejder "dddd" eksisterer og er tilknyttet projektet "Projekt 3"
    And medarbejderen "aaaa" er projektleder på projektet "Projekt 3"

  Scenario:
    # P1 + projektleder
    Given brugeren er logget ind som "aaaa" på projektet "Projekt 1"
    When brugeren vælger at se listen af aktiviteter
    Then ses aktiviten "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der 0 medarbejdere anført på den
    When brugeren anfører "bbbb" til "Aktivitet 1"
    Then er der 1 medarbejder anført "Aktivitet 1"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er medarbejderen "bbbb" anført "Aktivitet 1"

  Scenario:
    # P1 + ikke projektleder
    Given brugeren er logget ind som "bbbb" på projektet "Projekt 1"
    When brugeren vælger at se listen af aktiviteter
    Then ses aktiviten "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der 0 medarbejdere anført på den
    When brugeren anfører "bbbb" til "Aktivitet 1"
    Then er der 0 medarbejder anført "Aktivitet 1"
    And brugeren får en fejlbesked "du kan ikke anføre en medarbejder til en aktivitet"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der ingen anførte medarbejderer på "Aktivitet 1"

  Scenario:
    # P2 + ikke projektleder (fail)
    Given brugeren er logget ind som "cccc" på projektet "Projekt 2"
    When brugeren vælger at se listen af aktiviteter
    Then ses aktiviten "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der 0 medarbejdere anført på den
    When brugeren anfører "cccc" til "Aktivitet 1"
    Then er der 1 medarbejder anført "Aktivitet 1"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then medarbejderen "cccc" er anført på "Aktivitet 1"

  Scenario:
    # P2 + ikke projektleder (fail)
    Given brugeren er logget ind som "cccc" på projektet "Projekt 2"
    When brugeren vælger at se listen af aktiviteter
    Then ses aktiviten "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der 0 medarbejdere anført på den
    When brugeren anfører "bbbb" til "Aktivitet 1"
    Then er der 0 medarbejder anført "Aktivitet 1"
    And brugeren får en fejlbesked "du kan ikke anføre en medarbejder til en aktivitet som ikke er på projektet"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der ingen anførte medarbejderer på "Aktivitet 1"

  Scenario:
    # P3 + ikke medlem af projektet (fejl)
    Given brugeren er logget ind som "dddd" på projektet "Projekt 3"
    When brugeren vælger at se listen af aktiviteter
    Then ses aktiviten "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der 0 medarbejdere anført på den
    When brugeren anfører "aaaa" til "Aktivitet 1"
    Then er der 0 medarbejder anført "Aktivitet 1"
    And brugeren får en fejlbesked "du kan ikke anføre en medarbejder til en aktivitet"
    When brugeren vælger at se listen af anførte medarbejdere på "Aktivitet 1"
    Then er der ingen anførte medarbejderer på "Aktivitet 1"


