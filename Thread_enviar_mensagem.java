package Questao1_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Thread_enviar_mensagem implements Runnable {

	DatagramSocket socket;
	final int NUMERO_PROCESSO;				// P1 - 1, P2 - 2, P3 - 3, P4 - 4
	//final int PORTA_PROCESSO;
	final int PORTA_PROCESSO_POSTERIOR;
	Scanner leia = new Scanner(System.in);

	public Thread_enviar_mensagem(DatagramSocket socket,int numero_processo,
		int porta_processo_posterior) {
		this.socket = socket;
		this.NUMERO_PROCESSO = numero_processo;
		//this.PORTA_PROCESSO = porta_processo;
		this.PORTA_PROCESSO_POSTERIOR = porta_processo_posterior;
		
		
		
	}

	@Override
	public void run() {
		
		
			
			try {
			
					// definindo o endereço para localhost
					InetAddress localhost;
					localhost = InetAddress.getByName("localhost");
			
					//recebendo mensagem pelo teclado
					System.out.print("Digite algo: ");
			        Thread.sleep(2000);

					while(leia.hasNext())
					{
						// formatando mensagem para tipo "mensagem enviada pelo teclado" vira
						//"mensagem enviada pelo teclado@1" caso tenha sido enviada por P1
			
						String mensagem = leia.nextLine();
						mensagem+= ("@" + (char)(NUMERO_PROCESSO + '0'));
			
						System.out.println("Processo P"+ NUMERO_PROCESSO +" enviou a"
								+ " mensagem = " + mensagem);
			
						byte[] buffer_envio = mensagem.getBytes(StandardCharsets.UTF_8);
			
						DatagramPacket datagrama_envio = new DatagramPacket(buffer_envio,
								buffer_envio.length,
								localhost,
								PORTA_PROCESSO_POSTERIOR);
			
			
						socket.send(datagrama_envio);
						
					}
			}catch (UnknownHostException e1) 
			{
				
				System.out.println("Erro do tipo UnknownHostException");
			}
			catch (SocketException e2) 
			{
				
				System.out.println("Erro do tipo SocketException");
			}catch (IOException e) 
			{
				
				System.out.println("Erro de IO no envio de mensagem");
			} catch (InterruptedException e) {
				
				System.out.println("Erro de tipo InterruptedException");
			}
		
	}

}
