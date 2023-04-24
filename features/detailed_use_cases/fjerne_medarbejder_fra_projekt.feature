# De bliver fjernet fra alle aktiviteter, der tilhører projektets aktivitets DB
# Alle refferencer til medarbejderen slettes fra projektets medarbejder liste
# Skal være admin

Feature: fjern_medarbejder_fra_projekt
  Actor: Admin

  Background:
    Given projektet "Projekt 1" er oprettet med løbenummer 1 og årstal 2023
    And en medarbejder "aaaa" eksisterer og er tilknyttet projektet "Projekt 1"
    And en medarbejder "bbbb" eksisterer og er tilknyttet projektet "Projekt 1"
    And medarbejderen "aaaa" er projektleder på projektet "Projekt 1"
    And projektet "Projekt 2" er oprettet med løbenummer 2 og årstal 2023
    And en medarbejder "cccc" eksisterer og er tilknyttet projektet "Projekt 2"
    And en medarbejder "dddd" eksisterer og er tilknyttet projektet "Projekt 2"

  Scenario:
    Given brugeren er logget ind som "admin" på "Projekt 1"
    When brugeren vælger at se medarbejderer på projektet
    Then er "aaaa" og "bbbb" vist i medarbejderlisten
    When brugeren vælger at fjerne "bbbb" fra projektet
    Then er der 1 bruger tilbage i medarbejderlisten

  Scenario:
    Given brugeren er logget ind som "aaaa" på "Projekt 1"
    When brugeren vælger at se medarbejderer på projektet
    Then er "aaaa" og "bbbb" vist i medarbejderlisten
    When brugeren vælger at fjerne "bbbb" fra projektet
    Then er der 1 bruger tilbage i medarbejderlisten

  Scenario:
    Given brugeren er logget ind som "bbbb" på "Projekt 1"
    When brugeren vælger at se medarbejderer på projektet
    Then er "aaaa" og "bbbb" vist i medarbejderlisten
    When brugeren vælger at fjerne "bbbb" fra projektet
    Then er der 2 bruger tilbage i medarbejderlisten
    And brugeren får en fejlbesked "du kan ikke fjerne medarbejder fra projektet"

  Scenario:
    Given brugeren er logget ind som "cccc" på "Projekt 2"
    When brugeren vælger at se medarbejderer på projektet
    Then er "cccc" og "dddd" vist i medarbejderlisten
    When brugeren vælger at fjerne "dddd" fra projektet
    Then er der 1 bruger tilbage i medarbejderlisten