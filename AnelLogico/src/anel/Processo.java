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
	 * Método responsável por fazer uma requisiçao ao coordenador para verificar se
	 * esta ativo.
	 */
	public boolean realizarRequisicao() {
		for (Processo p : Util.processos) {
			if (p.isCoordenador()) {
				return p.requisicao();
			}
		}

		Logger.log("[Processo " + this.ID + "] Coodenador não encontrado");
		return false;
	}

	private boolean requisicao() {
		Logger.log("[Processo " + this.ID + "] Coordenador encontrado");
		return true;
	}

}
