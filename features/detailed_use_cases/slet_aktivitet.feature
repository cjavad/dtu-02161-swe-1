Feature: slet aktivitet
  Description: Slet aktiviteter.
  Actors: Softwarehuset A/S, Medarbejder, Projektleder

  Background:
    Given brugeren "aaaa" findes
    And brugeren "bbbb" findes
    And Medarbejderen er logget ind som Softwarehuset A/S
    And Medarbejderen opretter et projekt "2022-01"
    And Medarbejderen tilknytter brugeren "aaaa" som projektleder til "2022-01"
    And Medarbejderen opretter en aktivitet "Aktivitet 1" til projektet "2022-01" med start tidspunktet "19-2022" og slut tidspunktet "20-2022"
    And Medarbejderen opretter et projekt "2022-02" (Uden projektleder)
    And Medarbejderen opretter en aktivitet "Aktivitet 1" til projektet "2022-02" med start tidspunktet "19-2022" og slut tidspunktet "20-2022"

  Scenario: Medarbejder prøver at slette en aktivitet (med projektleder)
    Given Medarbejderen er logget ind som "bbbb" på projektet "2022-01"
    When medarbejderen prøver at slette aktiviteten "Aktivitet 1"
    Then fejlbeskeden er givet "du kan ikke aktiviteter fra projektet"
    And der er en aktivitet i projektet "2022-01"


  Scenario: Medarbejder prøver at slette et aktivitet (uden projektleder)
    Given en medarbejder er logget ind som "bbbb" på projektet "2022-02"
    When medarbejderen prøver at slette aktiviteten "Aktivitet 1"
    Then er der ingen aktiviteter i projektet

  Scenario: Projektleder prøver at slette en aktivitet
    Given en medarbejder er logget ind som "aaaa" på projektet "2022-01"
    When medarbejderen prøver at slette aktiviteten "Aktivitet 1"
    Then er der ingen aktiviteter i projektet