package annuaire;


import java.net.*;

import gestionpattern.Pattern;

import java.io.*;

//** Classe associée à chaque client (contrôleurs) **
//** Il y aura autant d'instances de cette classe que de clients connectés **
class ThreadClient implements Runnable
{
	private Thread _t; // contiendra le thread du client
	private Socket _s; // recevra le socket liant au client
	private PrintWriter _out; // pour gestion du flux de sortie
	private BufferedReader _in; // pour gestion du flux d'entrée
	private ServeurTCP _serveur; // pour utilisation des méthodes de la classe principale


	ThreadClient(Socket s, ServeurTCP serveur) // le param s est donnée dans BlablaServ par ss.accept()
	{
		_serveur=serveur; // passage de local en global (pour gestion dans les autres méthodes)
		_s=s; // passage de local en global
		try
		{
			// fabrication d'une variable permettant l'utilisation du flux de sortie avec des string
			_out = new PrintWriter(_s.getOutputStream());
			// fabrication d'une variable permettant l'utilisation du flux d'entrée avec des string
			_in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
		}
		catch (IOException e){ }

		_t = new Thread(this); // instanciation du thread
		_t.start(); // demarrage du thread, la fonction run() est ici lancée
	}


	public void run()
	{
		System.out.println("Un nouveau controleur s'est connecte");
		try
		{
			if (_out != null) // sécurité, l'élément ne doit pas être vide
			{
				System.out.println("Envoi du pattern");
				Pattern p = new Pattern();
				int taille = p.getDescription().getBytes().length;
				// ecriture du texte passé en paramètre (et concaténation d'une string de fin de chaine si besoin)
				_out.print(taille);
				_out.print(p.getDescription()+'\u0000');
				_out.flush(); // envoi dans le flux de sortie
			}
		}
		catch (Exception e){ 
			System.out.println("Le controleur s'est deconnecte");
		}
		finally // finally se produira le plus souvent lors de la deconnexion du client
		{
			try
			{
				// on indique à la console la deconnexion du client
				_s.close(); // fermeture du socket si il ne l'a pas déjà été (à cause de l'exception levée plus haut)
			}
			catch (IOException e){ }
		}
	}
}