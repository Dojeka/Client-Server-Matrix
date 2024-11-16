import java.io.*;
import java.net.*;
import java.lang.StringBuilder;

public class SThread extends Thread 
{
	private Object [][] RTable; // routing table
	private PrintWriter out, outTo, outServer; // writers (for writing back to the machine and to destination)

	private ObjectOutputStream to = null;
   private BufferedReader in, inServer; // reader (for reading from the machine connected to)
	private String inputLine, outputLine, destination, addr; // communication strings
	private Socket outSocket; // socket for communicating with a destination
	private int ind; // indext in the routing table

	// Constructor
	SThread(Object [][] Table, Socket toClient, int index) throws IOException
	{
		try {
			out = new PrintWriter(toClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
		}catch (IOException e){
			System.err.println("Failed to connect with client.");
			System.exit(1);
		}
			RTable = Table;
			addr = toClient.getInetAddress().getHostAddress();
			synchronized (RTable) {
				RTable[index][0] = addr; // IP addresses
				RTable[index][1] = toClient;
			}
			// sockets for communication
			ind = index;
	}
	
	// Run method (will run for each machine that connects to the ServerRouter)
	public void run()
	{
		try
		{
			// Initial sends/receives
			destination = in.readLine(); // initial read (the destination for writing)
			System.out.println("Received destination: "+ destination);

			int portNo = Integer.valueOf(in.readLine());
			System.out.println("Received port number: " + portNo);
			System.out.println("Checking for " + destination + " on port number: " + portNo);
			out.println("Connected to the server."); // confirmation of connection
		// waits 10 seconds to let the routing table fill with all machines' information
		try{
    		Thread.currentThread().sleep(10000);

	   }
		catch(InterruptedException ie){
		System.out.println("Thread interrupted");
		}


		// loops through the routing table to find the destination
		synchronized (RTable){
			for(int i = 0; i< 10; i++){
				if(destination.equals(RTable[i][0])){
					outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
					outTo = new PrintWriter(outSocket.getOutputStream(),true); // assigns a writer
					System.out.println("Found destination: " + destination);
					try{
						Socket socket = new Socket(String.valueOf(RTable[i][0]),portNo);
						inServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						outServer = new PrintWriter(socket.getOutputStream(),true);
						System.out.println("ServerRouter connected to Server: " + socket.getInetAddress().getHostAddress() + " on port " + portNo);
					}catch (IOException e){
						System.err.println("Failed to connect with server " + e.getMessage());
						System.exit(1);
					}
					break;
				}
			}
		}
		if(outSocket == null){
			System.err.println("Destination not found for routing.");
			out.println("Error: Destination not found.");
		}
		String clientIP = in.readLine();
		outServer.println("Connected with " + clientIP + " on port: " + portNo);
		outServer.flush();
		System.out.println("Sent Confirmation to Server.");

		int[][] matrixA = readMatrix(in);
		int[][] matrixB = readMatrix(in);
		System.out.println("Received matrices from Client.");

		sendMatrix(matrixA, outServer);
		sendMatrix(matrixB, outServer);
		System.out.println("Sent matrices to Server");

		int[][] result = readMatrix(inServer);
		System.out.println("Received matrix product from Server.");

		sendMatrix(result,out);
		System.out.println("Sent matrix product to Client.");

		 }// end try
			catch (IOException e) {
               System.err.println("Could not listen to socket." + e.getMessage());
               System.exit(1);
         }
	}
	public static int[][] readMatrix(BufferedReader in) throws IOException {
		System.out.println("Reading matrix...");
		String line;
		int size = Integer.parseInt(in.readLine()); // First line: matrix size
		int[][] matrix = new int[size][size];
		for (int i = 0; i < size; i++) {
			line = in.readLine();
			String[] values = line.split(" ");
			for (int j = 0; j < values.length; j++) {
				matrix[i][j] = Integer.parseInt(values[j]);
			}
		}
		return matrix;
	}

	public static int[][] multiply(int[][] matrixA, int[][] matrixB){
		int n = matrixA.length;
		int[][] result = new int[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				result[i][j] += matrixA[i][j] * matrixB[i][j];
			}
		}
		return result;
	}
	public static void sendMatrix(int[][] matrix, PrintWriter out) {
		out.println(matrix.length);
		for (int i = 0; i < matrix.length; i++) {
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < matrix[i].length; j++){
				line.append(matrix[i][j]).append(" ");
			}
			out.println(line.toString().trim());
		}
	}
}