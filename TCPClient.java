   import java.io.*;
   import java.net.*;
   import java.util.Random;
    public class TCPClient {
       public static void main(String[] args) throws IOException {
           //Generate matrix with random numbers
           int N = 2;
           int[][] matrixOne = generateMatrix(N);
           int[][] matrixTwo = generateMatrix(N);
           int[][] result = new int[N][N];


           // Variables for setting up connection and communication
           Socket socket = null; //socket to connect with ServerRouter
           PrintWriter out = null;
           BufferedReader in = null; // for reading form ServerRouter

           //Fetching addresses and IP
           InetAddress addr = InetAddress.getLocalHost();
           String host = addr.getHostAddress(); // Client machine's IP
           String routerName = "127.0.0.1";// ServerRouter host name
           int SockNum = 5555; // port number

           // Tries to connect to the ServerRouter

           try  {
               socket = new Socket(routerName, SockNum);
               //out = new ObjectOutputStream(socket.getOutputStream());
               in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               out = new PrintWriter(socket.getOutputStream(), true);
               System.out.println("Connected to the ServerRouter.");

           } catch (UnknownHostException e) {
               System.err.println("Don't know about router: " + routerName);
               System.exit(1);
           } catch (IOException e) {
               System.err.println("Couldn't get I/O for the connection to: " + routerName);
               System.exit(1);
           }

           String destinationIP = "127.0.0.1"; // destination IP (Server)
           int destinationPort = 5556;
           long t0, t1, t;
           String fromServer;

           // (initial sends/receives)
           out.println(destinationIP);// initial send (IP of the destination Server)
           out.flush();
           System.out.println("Sent host address.");

           out.println(destinationPort);
           System.out.println("Sent Destination port number.");

           fromServer = in.readLine();//initial receive from router (verification of connection)
           System.out.println("ServerRouter: " + fromServer);
           out.println(host); // Client sends the IP of its machine as initial send
           System.out.println("Sent client(myself) IP.");

           System.out.println("Starting timer.");
           t0 = System.currentTimeMillis();

           //Sending Matrices for multiplication
           SThread.sendMatrix(matrixOne, out);
           SThread.sendMatrix(matrixTwo, out);
           out.flush();
           System.out.println("Sent out Matrices");

           // Take in the result
           result = SThread.readMatrix(in);
           System.out.println("Received results.");

           System.out.println("Ending timer.");
           t1 = System.currentTimeMillis();
           t = t1 - t0;
           System.out.println("Cycle time: " + t);


           System.out.println("First matrix:");
           printMatrix(matrixOne);

           System.out.println("Second matrix:");
          printMatrix(matrixTwo);

           System.out.println("Results:");
           printMatrix(result);

           // closing connections
           out.close();
           in.close();
           socket.close();
       }
       private static int[][] generateMatrix(int N){
           Random rand = new Random();
           int [][] matrix = new int[N][N];
           for(int i = 0; i < matrix.length; i++){
               for(int j = 0; j < matrix[i].length; j++){
                   matrix[i][j] = rand.nextInt(10);
               }
           }
           return matrix;
       }
       private static void printMatrix(int[][] matrix){
           for (int i = 0; i < matrix.length; i++) {
               for (int j = 0; j < matrix[i].length; j++) {
                   System.out.print(matrix[i][j] + " ");
               }
               System.out.println();
           }
       }
   }
