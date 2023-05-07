package internal;

public class SystemDateServer implements DateServer {
	UgeDato ugeDato;

	public SystemDateServer(UgeDato ugeDato) {
		this.ugeDato = ugeDato;
	}

	public UgeDato getUgeDato() {
		return ugeDato;
	}
}
