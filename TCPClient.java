   import java.io.*;
   import java.net.*;
   import java.util.Random;
    public class TCPClient {
       public static void main(String[] args) throws IOException {
           //Generate matrix with random numbers
           Random rand = new Random();
           int N = 2;
           int[][] matrixOne = new int[N][N];
           int[][] matrixTwo = new int[N][N];
           for (int i = 0; i < N; i++) {
               for (int j = 0; j < N; j++) {
                   matrixOne[i][j] = rand.nextInt();
                   matrixTwo[i][j] = rand.nextInt();
               }
           }

           // Variables for setting up connection and communication
           //Socket socket = null; socket to connect with ServerRouter
           PrintWriter write = null;
           ObjectOutputStream out = null; // for writing to ServerRouter
           ObjectInputStream in = null; // for reading form ServerRouter

           //Fetching addresses and IP
           InetAddress addr = InetAddress.getLocalHost();
           String host = addr.getHostAddress(); // Client machine's IP
           String routerName = "localhost"; // ServerRouter host name
           int SockNum = 5555; // port number

           // Tries to connect to the ServerRouter
           int[][] result = new int[N][N];
           try (Socket socket = new Socket(routerName, SockNum)) {
               out = new ObjectOutputStream(socket.getOutputStream());
               in = new ObjectInputStream(socket.getInputStream());
               write = new PrintWriter(socket.getOutputStream());

               System.out.println("Connected to the Server.");

               String fromServer; // messages received from ServerRouter
               //String fromUser; messages sent to ServerRouter
               String address = "10.5.2.109"; // destination IP (Server)
               long t0, t1, t;

               // Communication process (initial sends/receives)
               write.println(address);// initial send (IP of the destination Server)
               fromServer = in.readLine();//initial receive from router (verification of connection)
               System.out.println("ServerRouter: " + fromServer);
               write.println(host); // Client sends the IP of its machine as initial send
               t0 = System.currentTimeMillis();

               //Sending Matrices for multiplication
               out.writeObject(matrixOne);
               out.writeObject(matrixTwo);
               out.flush();
               System.out.println("Sent out Matrices");

               // Take in the result
               result = (int[][]) in.readObject();

               t1 = System.currentTimeMillis();
               t = t1 - t0;
               System.out.println("Cycle time: " + t);

           } catch (UnknownHostException e) {
               System.err.println("Don't know about router: " + routerName);
               System.exit(1);
           } catch (IOException e) {
               System.err.println("Couldn't get I/O for the connection to: " + routerName);
               System.exit(1);
           } catch (ClassNotFoundException e) {
               System.out.println("Couldn't retrieve result matrix");
               System.exit(1);
           }

           System.out.println("First matrix:");
           for (int i = 0; i < matrixOne.length; i++) {
               for (int j = 0; j < matrixOne[i].length; j++) {
                   System.out.println(matrixOne[i][j] + " ");
               }
               System.out.println();
           }

           System.out.println("Second matrix:");
           for (int i = 0; i < matrixTwo.length; i++) {
               for (int j = 0; j < matrixTwo[i].length; j++) {
                   System.out.println(matrixTwo[i][j] + " ");
               }
               System.out.println();
           }

           System.out.println("Results:");
           for (int i = 0; i < result.length; i++) {
               for (int j = 0; j < result[i].length; j++) {
                   System.out.println(result[i][j] + " ");
               }
               System.out.println();
           }

           // closing connections
           out.close();
           in.close();
           write.close();
       }
   }
