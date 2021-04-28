package andre.rodrigues;

public class Questao {
	private String[][] questao= new String[5][5];
	private String pergunta, respostaA,respostaB,respostaC, respostaCorreta;
	
	public Questao(int numeroQuestao) {
		preencherArray();
		this.pergunta=questao[numeroQuestao][0];
		this.respostaA=questao[numeroQuestao][1];
		this.respostaB=questao[numeroQuestao][2];
		this.respostaC=questao[numeroQuestao][3];
		this.respostaCorreta=questao[numeroQuestao][4];

	}
	
	public String[][] getQuestao() {
		return questao;
	}

	public void setQuestao(String[][] questao) {
		this.questao = questao;
	}

	public String getPergunta(int numeroQuestao) {
		Questao questao = new Questao(numeroQuestao);
		return questao.pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getRespostaA(int numeroQuestao) {
		Questao questao = new Questao(numeroQuestao);
		return questao.respostaA;
	}

	public void setRespostaA(String respostaA) {
		this.respostaA = respostaA;
	}

	public String getRespostaB(int numeroQuestao) {
		Questao questao = new Questao(numeroQuestao);
		
		return questao.respostaB;
	}

	public void setRespostaB(String respostaB) {
		this.respostaB = respostaB;
	}

	public String getRespostaC(int numeroQuestao) {
		Questao questao = new Questao(numeroQuestao);
		return questao.respostaC;
	}

	public void setRespostaC(String respostaC) {
		this.respostaC = respostaC;
	}

	public String getRespostaCorreta(int numeroQuestao) {
		Questao questao = new Questao(numeroQuestao);
		return questao.respostaCorreta;
	}

	public void setRespostaCorreta(String respostaCorreta) {
		this.respostaCorreta = respostaCorreta;
	}
	
	//mudar para funçao, onde recebe int com posiçao da questao e devolve String[5] --> c pergunta, Respsota A, B, C e Correta
	private void preencherArray() {
		questao[0][0]="O que é o libGDX";
		questao[0][1]="Ferramenta de automação de compilação (Build Tool), utilizada em projetos de aplicações Java";
		questao[0][2]="Ambiente de desenvolvimento integrado (IDE) que permite codificar Java";
		questao[0][3]="Framework de desenvolvimento de jogos Java, multiplataforma e baseado em OpenGL";
		questao[0][4]="Framework de desenvolvimento de jogos Java, multiplataforma e baseado em OpenGL";
		
		questao[1][0]="Qual a licença de utilização do libGDX?";
		questao[1][1]="Apache 2.0, ou seja, o seu desenvolvimento é mantido pela comunidade";
		questao[1][2]="Java Research License que permite desenvolvimento gratuito para fins académicos";
		questao[1][3]="Software proprietário, cuja utilização requer pagamento de licença";
		questao[1][4]="Apache 2.0, ou seja, o seu desenvolvimento é mantido pela comunidade";
		
		questao[2][0]="A classe Texture representa:";
		questao[2][1]="Uma classe especial de SpriteBatch";
		questao[2][2]="Um caminho para um ficheiro de imagem (PNG,JPEG,GIF)";
		questao[2][3]="Uma imagem carregada que é persistida na Video RAM (VRAM)";
		questao[2][4]="Uma imagem carregada que é persistida na Video RAM (VRAM)";
	
		
		questao[3][0]="libGDX trata de forma diferente instâncias Music e instâncias Sound?";
		questao[3][1]="Sim, regra geral, se o audio for superior a 10 segundos deve usar-se uma instância Music, caso contrário, uma instância Sound";
		questao[3][2]="Não, desde que seja um ficheiro aúdio compatível não há diferenças de processamento entre Music e Sound";
		questao[3][3]="Não, as instâncias servem apenas para organização e gestão do projeto ";
		questao[3][4]="Sim, regra geral, se o audio for superior a 10 segundos deve usar-se uma instância Music, caso contrário, uma instância Sound";

		questao[4][0]="A classe SpriteBatch é:";
		questao[4][1]="Uma classe que especifica se a dimensão será bidimensional (XY) ou tridimensional (XYZ)";
		questao[4][2]="Uma classe especial que desenha imagens 2D, permitindo carregar um único ficheiro de imagem, que pode conter diversas texturas distintas";
		questao[4][3]="Uma classe que reconhece quais foram os inputs do utilizador";
		questao[4][4]="Uma classe especial que desenha imagens 2D, permitindo carregar um único ficheiro de imagem, que pode conter diversas texturas distintas";
		
	};
	
	
}
