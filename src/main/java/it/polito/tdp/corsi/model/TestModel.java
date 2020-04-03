package it.polito.tdp.corsi.model;

public class TestModel {

	//QUESTA CLASSE SERVE SOLO A FAR "GIRARE" il programma prima di connetterlo al controller per l'interfaccia
	public static void main(String[] args) {
		Model model = new Model();
		System.out.println(model.getCorsiByPeriodo(1));
		
		System.out.println(model.getIscrittiByPeriodo(2));
		

	}

}
