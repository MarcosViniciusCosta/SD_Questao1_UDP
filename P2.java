package Questao1_UDP;

import java.net.DatagramSocket;
import java.net.SocketException;


public class P2 {
	
	
	public static void main(String[] args) 
	{
		
		final int PORTA_ESCUTA_P2 = 4097;
		final int PORTA_ESCUTA_P3 = 4098;
		final int numero_processo = 2;
		final int numero_processo_posterior = 3;
		
		
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(PORTA_ESCUTA_P2);
			Thread enviar_mensagem = new Thread(new Thread_enviar_mensagem(socket, numero_processo, PORTA_ESCUTA_P3));
			Thread receber_e_repassar_mensagem = new Thread (new Thread_receber_e_repassar_mensagem
					(socket, numero_processo, numero_processo_posterior, PORTA_ESCUTA_P3) 
					);
			
			enviar_mensagem.start();
			receber_e_repassar_mensagem.start();
	        

			
		} 
		catch (SocketException e1) {
			
			System.out.println("Erro do tipo SocketException");
		}
		
		
		
		
		
		
	}
	
}
