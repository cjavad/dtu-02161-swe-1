Feature: ændre_tilknyttet_medarbejder_projekt
  Background:
    Given en medarbejder "aaaa" eksistere
    And en medarbejder "bbbb" eksistere
    And projektet "Projekt 1" eksistere
    And projektet "Projekt 2" eksistere
    And medarbejderen "aaaa" er tilknyttet projektet "Projekt 1"
    And medarbejderen "bbbb" er tilknyttet projektet "Projekt 1"
    And medarbejderen "aaaa" er projektleder for projektet "Projekt 1"
    And medarbejderen "bbbb" er tilknyttet projektet "Projekt 2"
    And medarbejderen "cccc" eksistere

    # Systemhuset (admin) tilknytter en medarbejder til et projekt
    Scenario:
      Given medarbejderen er logget ind som Systemhuset A/S på projektet "Projekt 1"
      When medarbejderen tilknytter bruger "cccc" til projektet "Projekt 1"
      Then medarbejderene "aaaa", "bbbb", "cccc" er tilknyttet projektet "Projekt 1"
      And medarbejderen "aaaa" er projektleder for projektet "Projekt 1"

    # Projektleder tilknytter en medarbejder til et projekt
    Scenario:
      Given medarbejderen er logget ind som "aaaa" på "Projekt 1"
      When medarbejderen tilknytter bruger "cccc" til projektet
      Then medarbejderene "aaaa", "bbbb", "cccc" er tilknyttet projektet "Projekt 1"
      And medarbejderen "aaaa" er projektleder for projektet "Projekt 1"

    # En medarbejder tilknytter en anden medarbejder til et projekt uden en projektleder
    Scenario:
      Given medarbejderen er logget ind som "bbbb" på "Projekt 2"
      When medarbejderen tilknytter bruger "cccc" til projektet
      Then medarbejderene "bbbb", "cccc" er tilknyttet projektet "Projekt 2"
      And der er ingen projektleder for projektet "Projekt 2"

    # En medarbejder tilknytter en anden medarbejder til et projekt med en projektleder
    Scenario:
      Given medarbejderen er logget ind som "bbbb" på "Projekt 1"
      When medarbejderen tilknytter bruger "cccc" til projektet
      Then medarbejderen bliver vist en fejlmeddelelse "Du har ikke rettigheder til at tilknytte en medarbejder til dette projekt"
      And medarbejderene "aaaa", "bbbb" er tilknyttet projektet "Projekt 1"
      And medarbejderen "aaaa" er projektleder for projektet "Projekt 1"
