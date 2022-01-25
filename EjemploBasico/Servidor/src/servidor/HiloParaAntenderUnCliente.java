
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloParaAntenderUnCliente extends Thread {
    Socket sk;

    public HiloParaAntenderUnCliente(Socket sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            Inet4Address ip = (Inet4Address) sk.getInetAddress();
            String laIP = ip.getHostAddress();
            boolean x = true;
            while (x) {
                System.out.println("Esperando algo");
                //wait for something
                String linea = br.readLine();
                System.out.println(laIP + ": " + linea);
                //case about which option decided
                switch (linea) {
                    case "1":
                        System.out.println("El cliente eligió la primera opción.");
                        String[] pregunta = {
                                "Me ves en verano y no en invierno y estoy metido en entre las manos, ya sea abierto o cerrado. ¿Qué soy?",
                                "Si me tienes, quieres compartirme. Si me compartes, no me tienes. ¿Qué soy?",
                                "Se trata de adivinar una cosa que cuando la compras es de color negro, cuando la usas de color rojo, y cuando la tiras es de color gris. ¿Sabría decir qué es?",
                                "Hay algo que, aunque te pertenezca, la gente siempre lo utiliza más que tú. ¿Qué es?",
                                "Crezco a pesar de no estar vivo. No tengo pulmones, pero para vivir necesito el aire. El agua, aunque no tenga boca, me mata. ¿Qué soy?",
                                "Estando roto es más útil que sin romperse. ¿Qué es?" };
                        String respuesta[] = { "abanico","secreto","carbon","nombre", "fuego", "huevo" };
                        Integer aleatorio = (int) Math.floor(Math.random() * (pregunta.length));
                        String seleccion = pregunta[aleatorio];
                        String respuestaCorrecta = respuesta[aleatorio];
                        //Send random question
                        bw.write(seleccion);
                        bw.newLine();
                        bw.flush();
                        System.out.println("La pregunta es " + seleccion);
                        System.out.println("La respuesta es " + respuestaCorrecta);
                        //Wait the answer
                        String lineaRes = br.readLine();
                        System.out.println("La respuesta del cliente: " + lineaRes);
                        //send correct answer
                        bw.write(respuestaCorrecta);
                        //finish
                        break;
                    case "2":
                        x = false;
                        break;
                    default:
                        break;
                }
                bw.newLine();
                bw.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
