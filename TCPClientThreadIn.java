import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClientThreadIn extends Thread {

    private Socket socket;
    private BufferedReader in;

    public TCPClientThreadIn(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {

        try {
            while (true) {

                String str;
                str = in.readLine();

                if (str == null)
                    break;

                System.out.println(str);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
