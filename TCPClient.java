import java.net.Socket;

public class TCPClient {
    public static void main (String[] args) {

        final int PORT = 1234;
        final String HOST = "localhost";

        try {
            Socket socket = new Socket(HOST,PORT);
            System.out.println("Connected to: " + socket.getInetAddress());

            TCPClientThreadIn tcpClientThreadIn = new TCPClientThreadIn(socket);
            tcpClientThreadIn.start();

            TCPClientThreadOut tcpClientThreadOut = new TCPClientThreadOut(socket);
            tcpClientThreadOut.start();

            tcpClientThreadIn.join();
            tcpClientThreadOut.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
