package application;

public enum AppStage {
	Start,
	Projekt,
	Medarbejder;

	public AppStage sidste() {
		switch (this) {
			case Start:
				return AppStage.Start;
			case Projekt:
				return AppStage.Start;
			case Medarbejder:
				return AppStage.Projekt;
			default:
				throw new IndexOutOfBoundsException("how?!");
		}
	}
}
