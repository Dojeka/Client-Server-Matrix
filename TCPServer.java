   import java.io.*;
   import java.net.*;
public class TCPServer {
    public static void main(String[] args) throws IOException {
        //Variables for setting up connection and communication
        ServerSocket serverSocket = null;
        Socket clientSocket = null; // socket to connect with ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        PrintWriter out = null;

		String routerName = "127.0.0.1"; // ServerRouter host name

        int sockNum = 5556; // port number
			
			// Tries to connect to the ServerRouter
        try {
            serverSocket = new ServerSocket(sockNum);
            System.out.println("Server is listening on port: " + sockNum);


                //Accept connection
                clientSocket = serverSocket.accept();
                System.out.println("Connected to Router: " + clientSocket.getInetAddress().getHostAddress());

                //communication Streams
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(),true);

        }
        catch(UnknownHostException e){
            System.err.println("Don't know about router: " + routerName);
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("Couldn't get I/O for the connection due to: " + e.getMessage());
            System.exit(1);
        }


      	// Variables for message passing
        String fromClient; // messages received from ServerRouter
			
        //(initial sends/receives)
        fromClient = in.readLine();// initial receive from router (verification of connection)
        System.out.println("This line runs");
        System.out.println("ServerRouter: " + fromClient);

        while(true){
            try {
                int[][] matrixA = SThread.readMatrix(in);
                int[][] matrixB = SThread.readMatrix(in);
                System.out.println("Received matrices.");

                int[][] results = SThread.multiply(matrixA,matrixB);
                System.out.println("Matrix multiplication complete.");

                SThread.sendMatrix(results,out);
                System.out.println("Results sent.");
            }
            catch (IOException e){
                System.err.println("Error reading matrices");
                System.exit(1);
                break;
            }
        }

        // closing connections
        clientSocket.close();
        out.close();
        in.close();
    }
}

