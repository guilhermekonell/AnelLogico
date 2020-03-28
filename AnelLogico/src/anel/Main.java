package anel;

/*
 * Especificação:
 * 	▪ a cada 30segundos um novo processo deve ser criado
 * 	▪ a cada 25segundos um processo fazer uma requisiçãopara o coordenador
 * 	▪ a cada 100 segundos o coordenador fica inativo
 * 	▪ a cada 80 segundos um processo da lista de processos fica inativo 
 * 	▪ dois processos não podem ter o mesmo ID
 * 	▪ dois processos de eleição não podem acontecer simultaneamente
 */
public class Main {

	private final static int TEMPO_CRIACAO = 30000;
	private final static int TEMPO_REQUISICAO = 25000;
	private final static int TEMPO_COORDENADOR_INATIVADO = 100000;
	private final static int TEMPO_PROCESSO_INATIVADO = 80000;

	public static void main(String[] args) {
		Util.criaProcesso(TEMPO_CRIACAO);
		Util.realizaRequisicao(TEMPO_REQUISICAO);
		Util.inativaCoordenador(TEMPO_COORDENADOR_INATIVADO);
		Util.inativaProcesso(TEMPO_PROCESSO_INATIVADO);
	}

}