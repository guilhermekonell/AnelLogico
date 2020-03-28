package anel;

import java.util.ArrayList;
import java.util.Random;

public class Util {

	public static ArrayList<Processo> processos;
	public static Object lock = new Object();

	public static void criaProcesso(int TEMPO_CRIACAO) {
		processos = new ArrayList<Processo>();

		new Thread(() -> {
			while (true) {
				synchronized (lock) {
					int ID;
					if (processos.isEmpty()) {
						ID = 1;
					} else {
						ID = processos.size() + 1;
					}

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
						realizaEleicao(p);
					}
				}
			}
		}).start();
	}

	private static void realizaEleicao(Processo p) {
		// Logger.log("[INFO] Elei�ao iniciada!");

		int IDNovoCoordenador = p.getID();
		for (Processo processo : processos) {
			if (processo.getID() > IDNovoCoordenador) {
				IDNovoCoordenador = processo.getID();
			}
		}

		// seta o coordenador e garante que nenhum outro tamb�m seja o coordenador
		for (Processo processo : processos) {
			if (processo.getID() == IDNovoCoordenador) {
				processo.setCoordenador(true);
				Logger.log("[Processo " + p.getID() + "] � o novo coordenador");
			} else {
				processo.setCoordenador(false);
			}
		}

		// Logger.log("[INFO] Elei�ao terminada!");
	}

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
						}
						break;
					}
				}
			}
		}).start();
	}

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