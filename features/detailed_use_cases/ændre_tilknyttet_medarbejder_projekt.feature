Feature: ændre tilknyttet medarbejder projekt
  Background:
    Given en medarbejder "aaaa" eksistere
    And en medarbejder "bbbb" eksistere
    And projektet "2023-01" eksistere
    And projektet "2023-02" eksistere
    And medarbejderen "aaaa" er tilknyttet projektet "2023-01"
    And medarbejderen "bbbb" er tilknyttet projektet "2023-01"
    And medarbejderen "aaaa" er projektleder for projektet "2023-01"
    And medarbejderen "bbbb" er tilknyttet projektet "2023-02"
    And en medarbejder "cccc" eksistere

    # Systemhuset (admin) tilknytter en medarbejder til et projekt
    Scenario:
      Given medarbejderen er logget ind som "admin" på "2023-01"
      When medarbejderen tilknytter bruger "cccc" til projektet "2023-01"
      Then medarbejderene "aaaa", "bbbb", "cccc" er tilknyttet projektet "2023-01"
      And medarbejderen "aaaa" er projektleder for projektet "2023-01"

    # Projektleder tilknytter en medarbejder til et projekt
    Scenario:
      Given medarbejderen er logget ind som "aaaa" på "2023-01"
      When medarbejderen tilknytter bruger "cccc" til projektet
      Then medarbejderene "aaaa", "bbbb", "cccc" er tilknyttet projektet "2023-01"
      And medarbejderen "aaaa" er projektleder for projektet "2023-01"

    # En medarbejder tilknytter en anden medarbejder til et projekt uden en projektleder
    Scenario:
      Given medarbejderen er logget ind som "bbbb" på "2023-02"
      When medarbejderen tilknytter bruger "cccc" til projektet
      Then medarbejderene "bbbb", "cccc" er tilknyttet projektet "2023-02"
      And der er ingen projektleder for projektet "2023-02"

    # En medarbejder tilknytter en anden medarbejder til et projekt med en projektleder
    Scenario:
      Given medarbejderen er logget ind som "bbbb" på "2023-01"
      When medarbejderen tilknytter bruger "cccc" til projektet
      Then medarbejderen bliver vist en fejlmeddelelse "Du har ikke rettigheder til at tilknytte en medarbejder til dette projekt"
      And medarbejderene "aaaa", "bbbb" er tilknyttet projektet "2023-01"
      And medarbejderen "aaaa" er projektleder for projektet "2023-01"
