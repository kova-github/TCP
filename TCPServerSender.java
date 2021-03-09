import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class TCPServerSender {

    private ArrayList<ClientThread> clientsArray;
    private int id = 0;

    TCPServerSender() {
        clientsArray = new ArrayList<>();
    }

    synchronized void addNewClient(ClientThread clientThread){

        clientsArray.add(clientThread);
        sendAll(clientThread.getClientName() + " Has join!", clientThread.getClientName());
        id ++;
        update(this);
    }

    synchronized void deleteClient(String name){

        for (int i = 0; i < clientsArray.size() ; i++) {

            if (clientsArray.get(i).getClientName().equals(name)){
                clientsArray.remove(i);
                break;
            }
        }
        update(this);

    }

    synchronized void update(TCPServerSender tcpServerSender){
        for (int i = 0; i < clientsArray.size() ; i++) {

            clientsArray.get(i).setTcpServerSender(tcpServerSender);

        }

    }

    synchronized String whoOnline(){

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Now online:\n");

        for (int i = 0; i < clientsArray.size() ; i++) {
            stringBuffer.append(clientsArray.get(i).getClientName() + "\n");
        }

        return stringBuffer.toString();

    }

    synchronized int getId(){
        return id + 1;
    }

    synchronized void sendAll(String message, String from){

        for (int i = 0; i < clientsArray.size() ; i++) {
            if (!clientsArray.get(i).getClientName().equals(from))
                clientsArray.get(i).sendMessage(message);
        }

    }

    synchronized void sendUser(String name, String message){

        for (int i = 0; i < clientsArray.size() ; i++) {

            if(clientsArray.get(i).getClientName().equals(name)){
                clientsArray.get(i).sendMessage(message);
            }

        }
    }

    synchronized boolean findByName(String name){

        for (int i = 0; i < clientsArray.size() ; i++) {
            if(clientsArray.get(i).getClientName().equals(name))
                return true;
        }

        return false;
    }

    synchronized boolean checkAccount (String user){

        try {

            File base = new File("C:/laba/laba/src/base.txt");
            Scanner scanner = new Scanner(base);

            while (scanner.hasNextLine()){
                if (scanner.nextLine().equals(user))
                    return true;
            }

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return false;
    }

}
