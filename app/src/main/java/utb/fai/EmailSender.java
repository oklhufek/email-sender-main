package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    

    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        input = socket.getInputStream();
        output = socket.getOutputStream();
        
       
        readResponse();
        
        
        sendCommand("EHLO " + host + "\r\n");
        readResponse();
    }

   
    public void send(String from, String to, String subject, String text) throws IOException {
        
        sendCommand("MAIL FROM:<" + from + ">\r\n");
        readResponse();
        
        
        sendCommand("RCPT TO:<" + to + ">\r\n");
        readResponse();
        
     
        sendCommand("DATA\r\n");
        readResponse();
        
    
        StringBuilder email = new StringBuilder();
        email.append("From: ").append(from).append("\r\n");
        email.append("To: ").append(to).append("\r\n");
        email.append("Subject: ").append(subject).append("\r\n");
        email.append("\r\n");
        email.append(text).append("\r\n");
        email.append(".\r\n"); 
        
        sendCommand(email.toString());
        readResponse();
    }

    public void close() {
        try {
            sendCommand("QUIT\r\n");
            readResponse();
            
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void sendCommand(String command) throws IOException {
        byte[] buffer = command.getBytes();
        output.write(buffer, 0, buffer.length);
        output.flush();
    }
    
    
    private void readResponse() throws IOException {
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (input.available() > 0) {
            byte[] response = new byte[1024];
            int len = input.read(response);
            
        }
    }
}
