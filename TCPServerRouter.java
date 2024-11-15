	import java.net.*;
   import java.io.*;

    public class TCPServerRouter {
       public static void main(String[] args) throws IOException {
         Socket clientSocket = null; // socket for the thread
         ServerSocket serverSocket = null; // server socket for accepting connections
         Object [][] RoutingTable = new Object [10][2]; // routing table
			int sockNum = 5555; // port number
			Boolean Running = true;
			int ind = 0; // index in the routing table


			//Accepting connections
         try {
            serverSocket = new ServerSocket(sockNum);
            System.out.println("ServerRouter is Listening on port: " + sockNum);
         } catch (IOException e) {
               System.err.println("Could not listen on port: " + sockNum + e.getMessage());
               System.exit(1);
            }

			// Creating threads with accepted connections
			while (Running)
			{
			try {
				clientSocket = serverSocket.accept();
				SThread t = new SThread(RoutingTable, clientSocket, ind); // creates a thread with a random port
				t.start(); // starts the thread
				ind++; // increments the index
            System.out.println("ServerRouter connected with Client: " + clientSocket.getInetAddress().getHostAddress());
         }
             catch (IOException e) {
               System.err.println("Client/Server failed to connect.");
               System.exit(1);
            }
			}//end while
			
			//closing connections
		   clientSocket.close();
         serverSocket.close();

      }
   }