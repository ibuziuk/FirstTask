import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
//import sun.util.logging.resources.logging;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Client implements Runnable {

    private List<String> history = new ArrayList<String>();
    private MessageExchange messageExchange = new MessageExchange();
    private String host;
    private Integer port;
    private static PrintWriter pw;

    public Client(String host, Integer port) {
        this.host = host;
        this.port = port;
        log(whatTime()," Request begin: ");
    }

    public static void log(String date, String event) {
        pw.println(date + " " + event);
        pw.flush();
    }

    public static String whatTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return (String)formatter.format(now);
    }

    public static void main(String[] args) throws IOException {
        pw = new PrintWriter(new FileWriter("clientLog.txt"));
//        if (args.length != 2)
//            System.out.println("Usage: java ChatClient host port");
//        else {
            System.out.println("Connection to server...");
            log(whatTime(), " Connection to server...");
            String serverHost = "localhost"; //args[0];
            Integer serverPort = 999; //Integer.parseInt(args[1]);
            Client client = new Client(serverHost, serverPort);
            new Thread(client).start();
            System.out.println("Connected to server: " + serverHost + ":" + serverPort);
            log(whatTime(), " Connected to server: " + serverHost + ":" + serverPort);
            client.listen();
        //}
    }

    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL("http://" + host + ":" + port + "/chat?token=" + messageExchange.getToken(history.size()));
        log(whatTime(), " Getting URL");
        return (HttpURLConnection) url.openConnection();
    }

    public List<String> getMessages() {
        List<String> list = new ArrayList<String>();
        HttpURLConnection connection = null;

        log(whatTime(), " Try to get messages from server...");
        try {
            connection = getHttpURLConnection();
            connection.connect();
            String response = messageExchange.inputStreamToString(connection.getInputStream());
            JSONObject jsonObject = messageExchange.getJSONObject(response);
            JSONArray jsonArray = (JSONArray) jsonObject.get("messages");
            for (Object o : jsonArray) {
                System.out.println(o);
                list.add(o.toString());
            }
            log(whatTime()," History size " + list.size());
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            log(whatTime(), " ERROR: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("ERROR: " + e.getMessage());
            log(whatTime(), " ERROR: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return list;
    }

    public void sendMessage(String message) {
        HttpURLConnection connection = null;

        try {
            log(whatTime(), " New Url");
            connection = getHttpURLConnection();
            log(whatTime()," Getting connection URL when message sent");
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(messageExchange.getClientSendMessageRequest(message));
            //log(whatTime()," Cod format ");
            wr.flush();
            wr.close();

            connection.getInputStream();

        } catch (IOException e) {
            System.err.println(" ERROR: " + e.getMessage());
            log(whatTime(), " ERROR: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public void listen() {
        while (true) {
            List<String> list = getMessages();
            if (list.size() > 0) {
                history.addAll(list);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("ERROR: " + e.getMessage());
                log(whatTime(), " ERROR: " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = scanner.nextLine();
            sendMessage(message);
        }
    }
}