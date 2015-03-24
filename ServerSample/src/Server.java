import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server implements HttpHandler {
    private List<String> history = new ArrayList<String>();
    private MessageExchange messageExchange = new MessageExchange();
    private static PrintWriter pw;

    public static void log(String date, String event) {
        pw.println(date + " " + event);
        pw.flush();
    }

    public static String whatTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String s = formatter.format(now);
        return s;
    }

    public static void main(String[] args) throws IOException {
        pw = new PrintWriter(new FileWriter("serverLog.txt"));

//        if (args.length != 1)
//            System.out.println("Usage: java Server port");
//        else {
//            try {
                System.out.println("Server is starting...");
                log(whatTime(), " Server is starting... ");
                Integer port = 999; // Integer.parseInt(args[0]);
                HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
                System.out.println("Server started.");
                log(whatTime(), " Server started.");
                String serverHost = InetAddress.getLocalHost().getHostAddress();
                System.out.println("Get list of messages: GET http://" + serverHost + ":" + port + "/chat?token={token}");
                log(whatTime(), " method GET ");
                System.out.println("Send message: POST http://" + serverHost + ":" +
                                    port + "/chat provide body json in format {\"message\" : \"{message}\"} ");
                log(whatTime(), " method POST ");
                server.createContext("/chat", new Server());
                server.setExecutor(null);
                server.start();
//            } catch (IOException e) {
//                System.out.println("Error creating http server: " + e);
//                log(whatTime(), " Error creating http server: " + e);
//            }
//        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";

        if ("GET".equals(httpExchange.getRequestMethod())) {
            response = doGet(httpExchange);
            log(whatTime(), " method GET ");
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            doPost(httpExchange);
            log(whatTime(), " method POST ");
        } else {
            response = "Unsupported http method: " + httpExchange.getRequestMethod();
            log(whatTime(), " Unsupported http method: " + httpExchange.getRequestMethod());
        }

        sendResponse(httpExchange, response);
    }

    private String doGet(HttpExchange httpExchange) {
        String query = httpExchange.getRequestURI().getQuery();
        if (query != null) {
            Map<String, String> map = queryToMap(query);
            String token = map.get("token");
            if (token != null && !"".equals(token)) {
                int index = messageExchange.getIndex(token);
                return messageExchange.getServerResponse(history.subList(index, history.size()));
            } else {
                return " Token query parameter is absent in url: " + query;
            }
        }
        return  "Absent query in url";
    }

    private void doPost(HttpExchange httpExchange) {
        try {
            String message = messageExchange.getClientMessage(httpExchange.getRequestBody());
            System.out.println("Get Message from User : " + message);
            history.add(message);
        } catch (ParseException e) {
            System.err.println("Invalid user message: " + httpExchange.getRequestBody() + " " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange httpExchange, String response) {
        try {
            byte[] bytes = response.getBytes();
            httpExchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = httpExchange.getResponseBody();
            os.write( bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();

        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}