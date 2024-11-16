   import java.io.*;
   import java.net.*;
public class TCPServer {
    public static void main(String[] args) throws IOException {
        //Variables for setting up connection and communication
        ServerSocket serverSocket = null;
        Socket clientSocket = null; // socket to connect with ServerRouter
        ObjectOutputStream out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        PrintWriter write = null;

        InetAddress addr = InetAddress.getLocalHost();// Gets the address of the local host
		String host = addr.getHostAddress(); // Server machine's IP
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
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                write = new PrintWriter(clientSocket.getOutputStream());

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
        String fromServer; // messages sent to ServerRouter
        String fromClient; // messages received from ServerRouter
        String address ="127.0.0.1"; // destination IP (Client)
			
        //(initial sends/receives)
//        write.println(address);// initial send (IP of the destination Client)
        fromClient = in.readLine();// initial receive from router (verification of connection)
        System.out.println("ServerRouter: " + fromClient);

        while(true){
            try {
                int[][] matrixA = SThread.readMatrix(in);
                int[][] matrixB = SThread.readMatrix(in);
                System.out.println("Received matrices.");

                int[][] results = SThread.multiply(matrixA,matrixB);
                System.out.println("Matrix multiplication complete.");

                out.writeObject(results);
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
        write.close();
        out.close();
        in.close();
    }
}

