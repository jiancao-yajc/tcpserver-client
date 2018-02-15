package G8R.app;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import G8R.serialization.G8RMessage;
import G8R.serialization.G8RRequest;
import G8R.serialization.G8RResponse;
import G8R.serialization.MessageInput;
import G8R.serialization.MessageOutput;
import G8R.serialization.ValidationException;

public class ClientHandler implements Runnable {

	/**
	 * The socket connected to the client.
	 */
	private Socket clientSocket;

	private Logger logger; // Server logger
	private G8RRequest g8rRequest;
	private G8RResponse g8rResponse;
	private MessageOutput socketOut = null;
	private MessageInput socketIn = null;
	/**
	 * Creates a new ClientHandler thread for the socket provided.
	 * 
	 * @param clientSocket
	 *            the socket to the client.
	 */
	public ClientHandler(Socket clientSocket, Logger logger) {
		this.clientSocket = clientSocket;
		this.logger = logger;
		try {
			socketOut = new MessageOutput(clientSocket.getOutputStream());
			socketIn = new MessageInput(clientSocket.getInputStream());
			G8RMessage temp = G8RMessage.decode(socketIn);
			if (temp instanceof G8RRequest) {
				g8rRequest = (G8RRequest)temp;
				g8rResponse = new G8RResponse(g8rRequest.coo); 
			} else {
				throw new ValidationException("Message is other", "");
			}
		} catch ( IOException e) {
			
		} catch (ValidationException e) {
			
		} catch(Exception e) {
			
		}
		
	}

	/**
	 * The run method is invoked by the ExecutorService (thread pool).
	 */
	@Override
	public void run() {

	
	}
	/**
	 * close socket of client
	 */
	public void close() {
		try {
		
			if (clientSocket != null && !clientSocket.isClosed())
				clientSocket.close();
		} catch (IOException e) {
			System.err.println("client socket closed failed:");
			System.exit(1);
		}
	}
}
