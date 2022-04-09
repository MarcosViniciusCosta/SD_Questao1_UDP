package Questao1_UDP;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Thread_receber_e_repassar_mensagem implements Runnable {
	


	DatagramSocket socket;
	final int NUMERO_PROCESSO;				// P1 - 1, P2 - 2, P3 - 3, P4 - 4
	//final int PORTA_PROCESSO;
	final int NUMERO_PROCESSO_POSTERIOR;
	Scanner leia = new Scanner(System.in);
	final int TAMANHO_BUFFER_RECEBIMENTO = 1024;
	final int PORTA_PROCESSO_POSTERIOR;
	
	
	public Thread_receber_e_repassar_mensagem(DatagramSocket socket,int numero_processo,
		int numero_processo_posterior, int porta_processo_posterior) {
		this.socket = socket;
		this.NUMERO_PROCESSO = numero_processo;
		this.NUMERO_PROCESSO_POSTERIOR = numero_processo_posterior;
		this.PORTA_PROCESSO_POSTERIOR = porta_processo_posterior;
		//this.PORTA_PROCESSO = porta_processo;
		//this.PORTA_PROCESSO_POSTERIOR = porta_processo_posterior;
		
		
		
	}
	
	@Override
	public void run() {
		
		//ficar sempre escutando
		while(true)
		{
						try {
							Thread.sleep(1000);
							// criando o buffer de recebimento de mensagens
						
							byte[] buffer_recebimento = new byte[TAMANHO_BUFFER_RECEBIMENTO];
						
							DatagramPacket datagrama_recebimento = 
							new DatagramPacket(buffer_recebimento, TAMANHO_BUFFER_RECEBIMENTO);
						
							//coloca os bytes recebidos no datagrama de recebimento
						
							socket.receive(datagrama_recebimento);
						
							
							// colocando os bytes recebidos no datagrama de recebimento no vetor de bytes
							buffer_recebimento = datagrama_recebimento.getData();
							
							String mensagem_recebida = new String(buffer_recebimento, java.nio.charset.StandardCharsets.UTF_8);
							
							
							System.out.println("Processo P"+ NUMERO_PROCESSO +" Recebeu a"
									+ " mensagem = " + mensagem_recebida);
							
							
							String conjunto_ids = retornar_ultimos_caracteres(mensagem_recebida);
							//System.out.println("Conjunto_ids = " + conjunto_ids);
							
							// só enviará a mensagem se o id do processo alvo não estiver 
							// incluido ainda após o arroba
							String id_proximo_processo = "" + (char)(NUMERO_PROCESSO_POSTERIOR + '0');  
							if(!conjunto_ids.contains(id_proximo_processo))
							{
								
								// colocando 1 no final da mensagem para dizer que passou por p1
								String resposta =  new String(buffer_recebimento, java.nio.charset.StandardCharsets.UTF_8);
								
								// Eliminando espaços inúteis
								resposta = resposta.trim();
								
								resposta+= Integer.toString(NUMERO_PROCESSO);
								
								System.out.println("Processo P"+ NUMERO_PROCESSO +" enviou a"
											+ " mensagem = " + resposta);		
										
										
								byte[] buffer_envio = resposta.getBytes(StandardCharsets.UTF_8);
								
								InetAddress localhost = InetAddress.getByName("localhost");
								
								
								
								DatagramPacket datagrama_envio = new DatagramPacket(buffer_envio,
										buffer_envio.length,
										localhost,
										PORTA_PROCESSO_POSTERIOR
										);
								
								
								
								socket.send(datagrama_envio);
								
								
								
							}else
							{
								System.out.println("Parou no processo P" + NUMERO_PROCESSO);
								
								// trabalhando com a resposta final
								String resposta =  new String(buffer_recebimento, java.nio.charset.StandardCharsets.UTF_8);
								
								// Eliminando espaços inúteis
								resposta = resposta.trim();
								
								resposta+= Integer.toString(NUMERO_PROCESSO);
								
								System.out.println("Mensagem final = " + resposta);		
							}
							
							
							
							
							
							
						} catch (IOException e) 
						{
							System.out.println("Erro do tipo IOException");
						} catch (InterruptedException e) {
							
							System.out.println("Erro do tipo InterruptedException");
						}
						
		}
		
	}
	
	String retornar_ultimos_caracteres(String entrada)
	{
		entrada = entrada.trim();
		//System.out.println("Entrada = " + entrada);
		String ultimos_caracteres = "";

	    int cont = entrada.length()-1;
	    boolean arroba_encontrado = false;
	    while(cont>=0 &&  (arroba_encontrado == false) && cont>= entrada.length()-4)
	    {
	    	
	        if(entrada.charAt(cont) == '@')
	        {
	            arroba_encontrado = true;
	        }else
	        {
	          ultimos_caracteres += entrada.charAt(cont);
	        }
	        
	        cont--;
	    }
	    //System.out.println("Ultimos_caracteres = " + ultimos_caracteres);
	    return ultimos_caracteres;
	}
		
}
