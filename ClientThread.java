
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private String clientName;
    private TCPServerSender tcpServerSender;
    private String nameFor;
    private PrintWriter out ;
    private boolean accept;

    public ClientThread(Socket socket, int id) throws IOException {
        this.socket = socket;
        this.clientName = "Client " + id;
        this.nameFor = "ALL";
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.accept = false;
    }

    public String getClientName() {
        return clientName;
    }

    public void setTcpServerSender(TCPServerSender tcpServerSender) {
        this.tcpServerSender = tcpServerSender;
    }

    public void sendMessage(String message){
        out.println(message);
    }

    @Override
    public void run() {

        try{

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!accept){
                out.println("Input USER:PASSWORD");
                String str = in.readLine();
                accept = tcpServerSender.checkAccount(str);
            }

            out.println("You join to: " + socket.getRemoteSocketAddress());
            out.println(tcpServerSender.whoOnline());

            while (socket.isConnected()) {

                String str = in.readLine();

                if (str.equals("@myname")) {
                    out.println(clientName);
                    continue;
                }

                if (str.equals("@quit")) {
                    tcpServerSender.sendAll(clientName + " HAS LEFT", clientName);
                    tcpServerSender.deleteClient(clientName);
                    in.close();
                    out.close();
                    break;
                }

                if (str.contains("@name ")){
                    String prevTmp = clientName;
                    clientName = str.substring(clientName.indexOf("@name ") + 7);
                    out.println("New nickName is " + clientName);
                    tcpServerSender.sendAll(prevTmp + " has change name to " + clientName,"");
                    continue;
                }

                if (str.indexOf("@online") == 0) {
                    out.println(tcpServerSender.whoOnline());
                    continue;
                }

                    if (str.indexOf("@sendall") == 0){
                    nameFor = "ALL";
                    out.println("Now you chat with: " + nameFor);
                    continue;
                }

                if (str.indexOf("@senduser ") == 0){

                    String nameForTmp = str.substring(str.lastIndexOf("@senduser ") + 10);
                    out.println("Finding: " + nameForTmp);

                    if (tcpServerSender.findByName(nameForTmp)) {
                        nameFor = nameForTmp;
                        out.println("Now you chat with: " + nameFor);
                    }
                    else{
                        out.println("Name not found!");
                    }

                    continue;
                }

                if (nameFor.equals("ALL")) {
                    tcpServerSender.sendAll(clientName + ": " + str, clientName);
                    continue;
                }

                if (tcpServerSender.findByName(nameFor))
                    tcpServerSender.sendUser(nameFor,clientName + ": " + str );
                else{
                    out.println(nameFor + "is't found!");
                    nameFor = "ALL";
                    out.println("Now you chat with " + nameFor);
                }

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
