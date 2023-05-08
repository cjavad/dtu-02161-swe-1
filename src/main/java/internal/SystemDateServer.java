package internal;

import java.io.Serializable;

public class SystemDateServer implements DateServer, Serializable {
	UgeDato dato;

	public SystemDateServer(UgeDato dato) {
		this.dato = dato;
	}

	public UgeDato getUgeDato() {
		return dato;
	}
}
