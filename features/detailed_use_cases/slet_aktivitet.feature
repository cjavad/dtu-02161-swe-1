Feature: slet_aktivitet
  Description: Slet aktiviteter.
  Actors: Softwarehuset A/S, Medarbejder, Projektleder

  Background:
    Given brugeren "aaaa" findes
    And brugeren "bbbb" findes
    And Medarbejderen er logget ind som Softwarehuset A/S
    And Medarbejderen opretter et projekt "2022-01"
    And Medarbejderen tilknytter brugeren "aaaa" som projektleder til "2022-01"
    And Medarbejderen opretter en aktivitet til projektet "Aktivitet 1" med start tidspunktet "19-2022" og slut tidspunktet "20-2022"
    And Medarbejderen opretter et projekt "2022-01" (Uden projektleder)
    And Medarbejderen opretter en aktivitet til projektet "Aktivitet 1" med start tidspunktet "19-2022" og  slut tidspunktet "20-2022"

  Scenario: Medarbejder prøver at slette en projekt (med projektleder)
    Given Medarbejderen er logget ind som "bbbb" på projektet "2022-01"
    When medarbejderen prøver at slette aktiviteten "Aktivitet 1"
    Then fejlbeskeden er givet "Du kan ikke ændre aktiviteter på dette projekt"
    And der er en aktivitet i projektet "Aktivitet 1"


  Scenario: Medarbejder prøver at slette et projekt (uden projektleder)
    Given en medarbejder er logget ind som "bbbb" på projektet "2022-02"
    When medarbejderen prøver at slette aktiviteten "Aktivitet 1"
    Then er der ingen aktiviteter i projektet

  Scenario: Projektleder prøver at slette en aktivitet
    Given en medarbejder er logget ind som "aaaa" på projektet "2022-01"
    When medarbejderen prøver at slette aktiviteten "Aktivitet 1"
    Then er der ingen aktiviteter i projektet