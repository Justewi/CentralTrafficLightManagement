package annuaire;

import java.net.*;
import java.io.*;
import java.util.*;

//** Classe principale du serveur, gère les infos globales **
public class ServeurTCP
{
	public static int serverPort = 9278;
	
	//** Methode : la première méthode exécutée, elle attend les connections **
	public static void main(String args[])
	{
		ServeurTCP serveurTCP = new ServeurTCP(); // instance de la classe principale
		try
		{
			Integer port;
			if(args.length<=0) port=new Integer(serverPort); // si pas d'argument : port par défaut
			else port = new Integer(args[0]); // sinon il s'agit du numéro de port passé en argument

			ServerSocket ss = new ServerSocket(port.intValue()); // ouverture d'un socket serveur sur port
			System.out.println("Demarrage du serveur sur le port : "+port.toString());
			while (true) // attente en boucle de connexion (bloquant sur ss.accept)
			{
				new ThreadClient(ss.accept(),serveurTCP); // un client se connecte, un nouveau thread client est lancé
			}
		}
		catch (Exception e) { }
	}

}
