# Skal være admin

Feature:
  Actor: Admin

  Scenario:
    Given brugeren er logget ind som admin
    When brugeren trykker på en knap "lav projekt" og opretter et projekt med titlen "Projekt 1"
    # Temporal aspekt skal afklares af denne test?
    Then kan brugeren se projektet "Projekt 1" på listen over projekter med løbenummer 1 og årstal 2023

  Scenario:
    Given brugeren er logget ind som "aaaa"
    When brugeren trykker på en knap "lav projekt" og opretter et projekt med titlen "Projekt 1"
    # Temporal aspekt skal afklares af denne test?
    Then kan brugeren se en tom liste over projekter
    And kan brugeren se en fejlmeddelelse "Du kan ikke oprette et projekt, da du ikke er admin"


