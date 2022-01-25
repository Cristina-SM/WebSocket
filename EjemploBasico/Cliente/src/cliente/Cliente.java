import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    final static int PORT = 40080;
    final static String HOST = "localhost";

    public static void main(String[] args) {
        try {
            Socket sk = new Socket(HOST, PORT);

            enviarMensajesAlServidor(sk);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void enviarMensajesAlServidor(Socket sk) {
        OutputStream os = null;
        InputStream is = null;
        try {
            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            try (Scanner sc = new Scanner(System.in)) {
                boolean x = true;
                while (x) {
                    System.out.println("Eligele una opción: ");
                    System.out.println("1-Acertijos");
                    System.out.println("2-Cerrar");
                    String respuesta = sc.nextLine();
                    bw.write(respuesta);
                    bw.newLine();
                    bw.flush();
                    switch (respuesta) {
                        
                        case "1":
                            //Wait server answer
                            System.out.println("Elegistes la primera opción: ");
                            String lineas = br.readLine();
                            System.out.println("El acertijo dice: " + lineas);
                            System.out.println("Introduce la respuesta: ");
                            String lineaRes = sc.nextLine();
                            //send client answer
                            bw.write(lineaRes);
                            bw.newLine();
                            bw.flush();
                            //server send the correct answer
                            String res = br.readLine();
                            System.out.println(res);
                            String[] splitRes = lineaRes.split(" ");
                            if (splitRes[1].equalsIgnoreCase(res)) {
                                System.out.println("Es correcto!!!");
                            } else {
                                System.out.println("Es incorrecto :(");

                            }
                            break;
                        case "2":
                            os.close();
                            is.close();
                            x = false;
                            break;
                        default:
                            System.out.println("Valor incorrecto debes introducir (1,2):");
                            break;
                    }

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
