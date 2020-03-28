package anel;

public class Processo {

	private final int ID;
	private boolean coordenador;

	public Processo(int ID, boolean coordenador) {
		super();
		this.ID = ID;
		this.coordenador = coordenador;
	}

	public int getID() {
		return ID;
	}

	public boolean isCoordenador() {
		return coordenador;
	}

	public void setCoordenador(boolean isCoordenador) {
		this.coordenador = isCoordenador;
	}

	public boolean realizarRequisicao() {
		for (Processo p : Util.processos) {
			if (p.isCoordenador()) {
				return p.requisicao();
			}
		}

		Logger.log("[Processo " + this.ID + "] Coodenador n�o encontrado");
		return false;
	}

	private boolean requisicao() {
		Logger.log("[Processo " + this.ID + "] Coordenador encontrado");
		return true;
	}

}
