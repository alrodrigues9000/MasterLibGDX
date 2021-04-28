package andre.rodrigues;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Main extends ApplicationAdapter {
	private Questao questao;
	private SpriteBatch batch;
	private BitmapFont font, pontuacao, esc, barraEspaco;
	private OrthographicCamera camera;
	private TextureRegion[] inicioJogo, perguntasTexturas, perguntasFont;
	private TextureAtlas packImagensAtlas;
	private Music backgroundMusica;
	private Sound respostaCerta, respostaErrada;
	private float tempoAnimacaoResposta, timePassed;
	private boolean executarAnimacaoRespostaA, executarAnimacaoRespostaB, executarAnimacaoRespostaC, fimAnimacao,
			menuInicial = true, jogoComecou, jogadorRespondeA, jogadorRespondeB, jogadorRespondeC, respostaCertaPlay,
			respostaErradaPlay, executarAnimacaoCorrecao, fimJogada;

	private int numeroPergunta = 0, contador, numeroRespostasCertas, numeroQuestao, numeroMaxQuestao = 19;
	// 1º Carregar assets e referências para os mesmos (ApplicationAdapter.create())

	@Override
	public void create() {
		batch = new SpriteBatch();

		// Criar texturas
		packImagensAtlas = new TextureAtlas("Images.atlas");

		inicioJogo = new TextureRegion[5];
		carregarTexturaInicioJogo();

		perguntasTexturas = new TextureRegion[7];
		carregarTexturaJogoInterface();

		perguntasFont = new TextureRegion[20];
		carregarTexturaPerguntasRespostasFont();

		// Carregar música e sons
		carregarSom();

		// Definir camara
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720); // garante que tem sempre a mesma resolução

		questao = new Questao(0);

		font = new BitmapFont();
		pontuacao = new BitmapFont();
		barraEspaco = new BitmapFont();
		esc = new BitmapFont();
	}

	@Override
	public void render() {
		limparTela();
		definirCamera();
		batch.begin();
		executarAnimacaoInicio();

		// começar jogo
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || jogoComecou == true) {
			if (menuInicial == true) {
				menuInicial = false;
				jogoComecou = true;
			}

			// desenhar background
			desenhaBackground();
			desenharPergunta();
			desenharRespostas();

			// Se jogador pressionar tecla
			jogadorPressionaA();
			jogadorPressionaB();
			jogadorPressionaC();
		}

		// desenhar pontuação jogador
		mostrarPontuacao();

		animacaoRespostaA(executarAnimacaoRespostaA);
		animacaoRespostaB(executarAnimacaoRespostaB);
		animacaoRespostaC(executarAnimacaoRespostaC);

		// condição para controlar/reset no contador de tempo de animação

		iniciarContadorTempoAnimacao();

		// mostrar correção da resposta do jogador
		iniciarAnimacaoCorrecaoResposta();

		// som resposta certa
		executarSomRespostaCorreta();
		executarSomRespostaErrada();

		fimJogada();
		// fim do lifecycle

		if (fimJogada == true && numeroQuestao >= 20) {
			jogoComecou = false;
			
			batch.draw(inicioJogo[2], 0, 0);
			batch.draw(inicioJogo[3],550,300);
			batch.draw(inicioJogo[4],600,200);
			barraEspaco.draw(batch, "Recomeçar:", 370,490);
			esc.draw(batch, "Sair:", 370,390);
			pontuacao.draw(batch, "Número respostas corretas: " + numeroRespostasCertas, 440, 100);
			pontuacao.getData().setScale(2);
			barraEspaco.getData().setScale(2);
			esc.getData().setScale(2);
			
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				numeroQuestao=0;
				jogoComecou=true;
				numeroPergunta=0;
				numeroRespostasCertas=0;
				}
		
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
				System.exit(0);
				}
		}
		batch.end();

	}

	private void definirCamera() {
		batch.setProjectionMatrix(camera.combined);
		camera.update();

	}

	private void limparTela() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	private void fimJogada() {
		if (executarAnimacaoCorrecao == true && tempoAnimacaoResposta > 1.5f) {
			numeroPergunta = numeroPergunta + 1;
			numeroQuestao = numeroQuestao + 4;
			executarAnimacaoCorrecao = false;
			jogadorRespondeA = false;
			jogadorRespondeB = false;
			jogadorRespondeC = false;
			fimAnimacao = false;
			respostaCertaPlay = false;
			respostaErradaPlay = false;
			this.contador = 0;
			tempoAnimacaoResposta = 0;

			executarAnimacaoRespostaA = false;
			jogadorRespondeA = false;

			executarAnimacaoRespostaB = false;
			jogadorRespondeB = false;

			executarAnimacaoRespostaC = false;
			jogadorRespondeC = false;
			fimJogada = true;
		}

	}

	private void executarSomRespostaErrada() {
		if (respostaErradaPlay == true) {
			contador += 1;
			if (contador == 1) {
				respostaErrada.play();
				tempoAnimacaoResposta = 0;
				executarAnimacaoCorrecao = true;
				// numeroQuestao=numeroQuestao+4;
				// respostaCertaPlay=false;
			}
		}

	}

	private void executarSomRespostaCorreta() {

		if (respostaCertaPlay == true) {
			contador += 1;
			if (contador == 1) {
				numeroRespostasCertas = numeroRespostasCertas + 1;
				respostaCerta.play();
				tempoAnimacaoResposta = 0;
				executarAnimacaoCorrecao = true;
				// numeroQuestao=numeroQuestao+4;
				// respostaCertaPlay=false;
			}
		}

	}

	private void iniciarAnimacaoCorrecaoResposta() {
		if (numeroQuestao <= numeroMaxQuestao) {
			if (tempoAnimacaoResposta > 2.0f) {
				executarAnimacaoRespostaA = false;
				executarAnimacaoRespostaB = false;
				executarAnimacaoRespostaC = false;
				tempoAnimacaoResposta = 0;
				fimAnimacao = true;
			}

			if (jogadorRespondeA == true && fimAnimacao == true) {
				if (questao.getRespostaCorreta(numeroPergunta).equals(questao.getRespostaA(numeroPergunta))) {
					batch.draw(perguntasTexturas[1], 0, 0);
					respostaCertaPlay = true;

				} else {
					batch.draw(perguntasTexturas[4], 0, 0);
					respostaErradaPlay = true;
				}
			}

			if (jogadorRespondeB == true && fimAnimacao == true) {
				if (questao.getRespostaCorreta(numeroPergunta).equals(questao.getRespostaB(numeroPergunta))) {
					batch.draw(perguntasTexturas[2], 0, 0);
					respostaCertaPlay = true;

				} else {
					batch.draw(perguntasTexturas[5], 0, 0);
					respostaErradaPlay = true;
				}

			}

			if (jogadorRespondeC == true && fimAnimacao == true) {
				if (questao.getRespostaCorreta(numeroPergunta).equals(questao.getRespostaC(numeroPergunta))) {
					batch.draw(perguntasTexturas[3], 0, 0);
					respostaCertaPlay = true;

				} else {
					batch.draw(perguntasTexturas[6], 0, 0);
					respostaErradaPlay = true;
				}
			}
		}

	}

	private void iniciarContadorTempoAnimacao() {
		if (jogadorRespondeA == true || jogadorRespondeB == true || jogadorRespondeC == true) {
			tempoAnimacaoResposta += Gdx.graphics.getDeltaTime();
			fimJogada = false;// começa jogada
		}
	}

	private void desenharRespostas() {
		if (numeroQuestao <= numeroMaxQuestao) {
			batch.draw(perguntasFont[numeroQuestao + 1], 0, 0);
			batch.draw(perguntasFont[numeroQuestao + 2], 0, 0);
			batch.draw(perguntasFont[numeroQuestao + 3], 0, 0);
		}
	}

	private void desenharPergunta() {
		if (numeroQuestao <= numeroMaxQuestao) {
			batch.draw(perguntasFont[numeroQuestao], 0, 0);
		}
	}

	private void desenhaBackground() {
		if (numeroQuestao <= numeroMaxQuestao) {
			batch.draw(perguntasTexturas[0], 0, 0);
		}
	}

	@Override
	public void dispose() {
		// todos os assets carregados deve ser disposed, para não ocupar espaço
		batch.dispose();
		backgroundMusica.dispose();
		respostaCerta.dispose();
		packImagensAtlas.dispose();
	}

	private void animacaoRespostaA(boolean executarAnimacao) {
		if (executarAnimacao == true) {
			batch.draw(perguntasTexturas[4], 0, 0);
			batch.draw(perguntasTexturas[1], 0, 0);
		}
	}

	private void animacaoRespostaB(boolean executarAnimacao) {
		if (executarAnimacao == true) {
			batch.draw(perguntasTexturas[5], 0, 0);
			batch.draw(perguntasTexturas[2], 0, 0);
		}
	}

	private void animacaoRespostaC(boolean executarAnimacao) {
		if (executarAnimacao == true) {
			batch.draw(perguntasTexturas[6], 0, 0);
			batch.draw(perguntasTexturas[3], 0, 0);
		}
	}

	private void executarAnimacaoInicio() {
		timePassed += Gdx.graphics.getDeltaTime();

		if ((int) timePassed % 2 == 0 && jogoComecou == false && numeroQuestao <= numeroMaxQuestao) {
			batch.draw(inicioJogo[0], 0, 0);
		} else if ((int) timePassed % 2 != 0 && jogoComecou == false && numeroQuestao <= numeroMaxQuestao) {
			batch.draw(inicioJogo[0], 0, 0);
			batch.draw(inicioJogo[1], 0, 0);
		}
	}

	private void jogadorPressionaA() {
		if (Gdx.input.isKeyPressed(Input.Keys.A) && jogadorRespondeB == false && jogadorRespondeC == false) {

			executarAnimacaoRespostaA = true;
			jogadorRespondeA = true;

			executarAnimacaoRespostaB = false;
			jogadorRespondeB = false;

			executarAnimacaoRespostaC = false;
			jogadorRespondeC = false;
		}
	}

	private void jogadorPressionaB() {
		if (Gdx.input.isKeyPressed(Input.Keys.B) && jogadorRespondeC == false && jogadorRespondeA == false) {

			executarAnimacaoRespostaA = false;
			jogadorRespondeA = false;

			executarAnimacaoRespostaB = true;
			jogadorRespondeB = true;

			executarAnimacaoRespostaC = false;
			jogadorRespondeC = false;

		}
	}

	private void jogadorPressionaC() {
		if (Gdx.input.isKeyPressed(Input.Keys.C) && jogadorRespondeB == false && jogadorRespondeA == false) {
			executarAnimacaoRespostaC = true;
			jogadorRespondeC = true;

			executarAnimacaoRespostaA = false;
			jogadorRespondeA = false;

			executarAnimacaoRespostaB = false;
			jogadorRespondeB = false;

		}
	}

	private void mostrarPontuacao() {
		if (jogoComecou == true) {
			font.draw(batch, "Pontos: " + numeroRespostasCertas, 50, 50);
		}
	}

	private void carregarTexturaPerguntasRespostasFont() {
		perguntasFont[0] = packImagensAtlas.findRegion("1pergunta");
		perguntasFont[1] = packImagensAtlas.findRegion("1respostaA");
		perguntasFont[2] = packImagensAtlas.findRegion("1respostaB");
		perguntasFont[3] = packImagensAtlas.findRegion("1respostaC");

		perguntasFont[4] = packImagensAtlas.findRegion("2Pergunta");
		perguntasFont[5] = packImagensAtlas.findRegion("2RespostaA");
		perguntasFont[6] = packImagensAtlas.findRegion("2RespostaB");
		perguntasFont[7] = packImagensAtlas.findRegion("2RespostaC");

		perguntasFont[8] = packImagensAtlas.findRegion("3Pergunta");
		perguntasFont[9] = packImagensAtlas.findRegion("3RespostaA");
		perguntasFont[10] = packImagensAtlas.findRegion("3RespostaB");
		perguntasFont[11] = packImagensAtlas.findRegion("3RespostaC");

		perguntasFont[12] = packImagensAtlas.findRegion("4Pergunta");
		perguntasFont[13] = packImagensAtlas.findRegion("4RespostaA");
		perguntasFont[14] = packImagensAtlas.findRegion("4RespostaB");
		perguntasFont[15] = packImagensAtlas.findRegion("4RespostaC");

		perguntasFont[16] = packImagensAtlas.findRegion("5Pergunta");
		perguntasFont[17] = packImagensAtlas.findRegion("5RespostaA");
		perguntasFont[18] = packImagensAtlas.findRegion("5RespostaB");
		perguntasFont[19] = packImagensAtlas.findRegion("5RespostaC");

	}

	private void carregarTexturaJogoInterface() {
		perguntasTexturas[0] = packImagensAtlas.findRegion("backgroundBlueAndPurple");
		perguntasTexturas[1] = packImagensAtlas.findRegion("respostaVerdeA");
		perguntasTexturas[2] = packImagensAtlas.findRegion("respostaVerdeB");
		perguntasTexturas[3] = packImagensAtlas.findRegion("respostaVerdeC");
		perguntasTexturas[4] = packImagensAtlas.findRegion("respostaVermelhaA");
		perguntasTexturas[5] = packImagensAtlas.findRegion("respostaVermelhaB");
		perguntasTexturas[6] = packImagensAtlas.findRegion("respostaVermelhaC");
	}

	private void carregarTexturaInicioJogo() {
		inicioJogo[0] = packImagensAtlas.findRegion("inicioImagem");
		inicioJogo[1] = packImagensAtlas.findRegion("PressionarEspacoInicio");
		inicioJogo[2] = packImagensAtlas.findRegion("backgroundAllgame");
		inicioJogo[3] = packImagensAtlas.findRegion("SpaceBar");
		inicioJogo[4] = packImagensAtlas.findRegion("ESC");

	}

	private void carregarSom() {
		backgroundMusica = Gdx.audio.newMusic(Gdx.files.internal("Mello C - Tedukedo.mp3"));
		respostaCerta = Gdx.audio.newSound(Gdx.files.internal("RespostaCerta.mp3"));
		respostaErrada = Gdx.audio.newSound(Gdx.files.internal("respostaErrada.wav"));
		backgroundMusica.setLooping(true);
		backgroundMusica.play();
	}

}
