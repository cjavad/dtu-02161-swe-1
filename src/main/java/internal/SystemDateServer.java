package internal;

import java.io.Serializable;

public class SystemDateServer implements DateServer, Serializable {
	UgeDato ugeDato;

	public SystemDateServer(UgeDato ugeDato) {
		this.ugeDato = ugeDato;
	}

	public UgeDato getUgeDato() {
		return ugeDato;
	}
}
