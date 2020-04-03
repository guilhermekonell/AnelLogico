package anel;

import java.util.ArrayList;
import java.util.Random;

public class Util {

	public static ArrayList<Processo> processos;
	public static Object lock;
	public static int ID = 0;

	/*
	 * Método responsável por criar um processo na lista de processos.
	 */
	public static void criaProcesso(int TEMPO_CRIACAO) {
		processos = new ArrayList<Processo>();
		lock = new Object();

		new Thread(() -> {
			while (true) {
				synchronized (lock) {
					ID++;
					processos.add(new Processo(ID, false));

					Logger.log("[Processo " + ID + "] Processo criado");
				}

				try {
					Thread.sleep(TEMPO_CRIACAO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/*
	 * Método responsável por disparar uma requisiçao com um processo aleatório ao
	 * coordenador. Se o coordenador não responder, é iniciado uma nova eleiçao.
	 */
	public static void realizaRequisicao(int TEMPO_REQUISICAO) {
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(TEMPO_REQUISICAO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					int IDAleatorio = new Random().nextInt(processos.size());
					Processo p = processos.get(IDAleatorio);

					boolean existeCoordenador = p.realizarRequisicao();

					if (!existeCoordenador) {
						realizaEleicao();
					}
				}
			}
		}).start();
	}

	/*
	 * Método responsável por realizar a eleiçao de um novo coordenador.
	 */
	private static void realizaEleicao() {
		Processo novoCoordenador = null;
		int ID = 0;
		for (Processo processo : processos) {
			if (processo.getID() > ID) {
				novoCoordenador = processo;
			}
		}

		novoCoordenador.setCoordenador(true);
		Logger.log("[Processo " + novoCoordenador.getID() + "] É o novo coordenador");
	}

	/*
	 * Método responsável por inativar o coordenador.
	 */
	public static void inativaCoordenador(int TEMPO_COORDENADOR_INATIVADO) {
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(TEMPO_COORDENADOR_INATIVADO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					for (Processo p : processos) {
						if (p.isCoordenador()) {
							processos.remove(p);
							Logger.log("[Processo " + p.getID() + "] Coordenador inativado");
							break;
						}
					}
				}
			}
		}).start();
	}

	/*
	 * Método responsável por inativar um processo aleatório.
	 */
	public static void inativaProcesso(int TEMPO_PROCESSO_INATIVADO) {
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(TEMPO_PROCESSO_INATIVADO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					int IDAleatorio = new Random().nextInt(processos.size());
					Processo p = processos.get(IDAleatorio);
					processos.remove(p);
					Logger.log("[Processo " + p.getID() + "] Processo inativado");
				}
			}
		}).start();
	}

}
