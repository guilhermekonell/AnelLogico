package anel;

import java.util.ArrayList;
import java.util.Random;

public class Util {

	public static ArrayList<Processo> processos;
	public static Object lock;
	public static int ID = 0;

	/*
	 * M�todo respons�vel por criar um processo na lista de processos.
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
	 * M�todo respons�vel por disparar uma requisi�ao com um processo aleat�rio ao
	 * coordenador. Se o coordenador n�o responder, � iniciado uma nova elei�ao.
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
	 * M�todo respons�vel por realizar a elei�ao de um novo coordenador.
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
		Logger.log("[Processo " + novoCoordenador.getID() + "] � o novo coordenador");
	}

	/*
	 * M�todo respons�vel por inativar o coordenador.
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
	 * M�todo respons�vel por inativar um processo aleat�rio.
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
