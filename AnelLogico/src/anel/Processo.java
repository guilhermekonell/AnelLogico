package anel;

public class Processo {

	private final int ID;
	private boolean coordenador;

	/*
	 * Classe que representa o processo.
	 */
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

	/*
	 * M�todo respons�vel por fazer uma requisi�ao ao coordenador para verificar se
	 * esta ativo.
	 */
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
