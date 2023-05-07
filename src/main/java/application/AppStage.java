package application;

public enum AppStage {
	Start,
	Projekt,
	Medarbejder,
	Aktivitet;

	public AppStage sidste() {
		switch (this) {
			case Start:
				return AppStage.Start;
			case Projekt:
				return AppStage.Start;
			case Medarbejder:
				return AppStage.Start;
			case Aktivitet:
				return AppStage.Projekt;
			default:
				throw new IndexOutOfBoundsException("how?!");
		}
	}
}
