import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args) {
        final int PORT = 40080;
    
        try {
            try (ServerSocket sk = new ServerSocket(PORT)) {
                while(true){
                    Socket socket = sk.accept();
                    System.out.println("Alguien se conectó");
                    HiloParaAntenderUnCliente hilo = new HiloParaAntenderUnCliente(socket);
                    hilo.start();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
