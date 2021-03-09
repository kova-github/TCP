import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClientThreadOut extends Thread {

    private Socket socket;

    public TCPClientThreadOut(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        Scanner scanner = null;
        String message = " ";

        try{

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);

            while (socket.isConnected()) {

                message = scanner.nextLine();

                if (message.equals("@quit")) {
                    out.println(message);
                    return;
                }

                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
