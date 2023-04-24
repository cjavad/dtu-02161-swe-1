# Aktiviteten er nu ikke længere i nogen af medarbejdernes projekt-aktivitet tuples
# Aktiviteten eksisterer nu ikke længere i aktivitets DB'en for projektet

Feature: fjern_aktivitet_fra_projekt
  Background:
    Given projektet "Projekt 1" er oprettet med løbenummer 1 og årstal 2023
    And en medarbejder "aaaa" eksisterer og er tilknyttet projektet "Projekt 1"
    And en medarbejder "bbbb" eksisterer og er tilknyttet projektet "Projekt 1"
    And en aktivitet "Aktivitet 1" eksisterer på projektet med id'et "2023-01" med start datoen "01-2023" og slut datoen "10-2023"
    And medarbejderen "aaaa" er projektleder på projektet "Projekt 1"
    And medarbejderen "aaaa" er anført "Aktivitet 1" på "Projekt 1"
    And medarbejderen "bbbb" er anført "Aktivitet 1" på "Projekt 1"
    And projektet "Projekt 2" er oprettet med løbenummer 2 og årstal 2023
    And en aktivitet "Aktivitet 1" eksisterer på projektet med id'et "2023-02" med start datoen "01-2023" og slut datoen "10-2023"
    And en medarbejder "cccc" eksisterer og er tilknyttet projektet "Projekt 2"
    And medarbejderen "cccc" er anført "Aktivitet 1" på "Projekt 2"
    And projektet "Projekt 3" er oprettet med løbenummer 3 og årstal 2023
    And en aktivitet "Aktivitet 1" eksisterer på projektet med id'et "2023-03" med start datoen "01-2023" og slut datoen "10-2023"
    And en medarbejder "dddd" eksisterer og er tilknyttet projektet "Projekt 3"
    And medarbejderen "aaaa" er projektleder på projektet "Projekt 3"
    And medarbejderen "aaaa" er anført "Aktivitet 1" på "Projekt 3"
    And medarbejderen "dddd" er anført "Aktivitet 1" på "Projekt 3"

  Scenario:
    # Admin
    Given brugeren er logget ind som admin på "Projekt 1"
    When brugeren vælger at se aktiviteterne på projektet
    Then kan brugeren se "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    And medarbejderen "aaaa" har aktivitets tuplen ("2023-01", "Aktivitet 1")
    And medarbejderen "bbbb" har aktivitets tuplen ("2023-01", "Aktivitet 1")
    When brugeren vælger at fjerne "Aktivitet 1" fra projektet
    Then er der 0 aktiviteter på projektet
    And medarbejderen "aaaa" har ikke aktivitets tuplen ("2023-01", "Aktivitet 1")
    And medarbejderen "bbbb" har ikke aktivitets tuplen ("2023-01", "Aktivitet 1")

  Scenario:
    # Projektleder
    Given brugeren er logget ind "aaaa" på "Projekt 1"
    When brugeren vælger at se aktiviteterne på projektet
    Then kan brugeren se "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    And medarbejderen "aaaa" har aktivitets tuplen ("2023-01", "Aktivitet 1")
    And medarbejderen "bbbb" har aktivitets tuplen ("2023-01", "Aktivitet 1")
    When brugeren vælger at fjerne "Aktivitet 1" fra projektet
    Then er der 0 aktiviteter på projektet
    And medarbejderen "aaaa" har ikke aktivitets tuplen ("2023-01", "Aktivitet 1")
    And medarbejderen "bbbb" har ikke aktivitets tuplen ("2023-01", "Aktivitet 1")


  Scenario:
    # Ikke projekt leder uden projekt leder
    Given brugeren er logget ind som "cccc" på "Projekt 2"
    When brugeren vælger at se aktiviteterne på projektet
    Then kan brugeren se "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    And medarbejderen "cccc" har aktivitets tuplen ("2023-02", "Aktivitet 1")
    When brugeren vælger at fjerne "Aktivitet 1" fra projektet
    Then er der 0 aktiviteter på projektet
    And medarbejderen "cccc" har ikke aktivitets tuplen ("2023-02", "Aktivitet 1")


  Scenario:
    # Ikke projekt leder med projekt leder
    Given brugeren er logget ind "dddd" på "Projekt 3"
    When brugeren vælger at se aktiviteterne på projektet
    Then kan brugeren se "Aktivitet 1" med start datoen "01-2023" og slut datoen "10-2023"
    And medarbejderen "aaaa" har aktivitets tuplen ("2023-03", "Aktivitet 1")
    And medarbejderen "dddd" har aktivitets tuplen ("2023-03", "Aktivitet 1")
    When brugeren vælger at fjerne "Aktivitet 1" fra projektet
    Then er der 1 aktiviteter på projektet
    And brugeren ser en fejlbesked "du kan ikke aktiviteter fra projektet"
    And medarbejderen "aaaa" har aktivitets tuplen ("2023-03", "Aktivitet 1")
    And medarbejderen "dddd" har aktivitets tuplen ("2023-03", "Aktivitet 1")
