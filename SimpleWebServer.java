import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

public class SimpleWebServer {
    private static final int PORT = 8080;
    private static final String HTML_TEMPLATE = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Java Web Server</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 40px; }
                .container { max-width: 800px; margin: 0 auto; }
                .header { background: #f0f0f0; padding: 20px; border-radius: 5px; }
                .endpoint { background: #e8f4fd; padding: 15px; margin: 10px 0; border-left: 4px solid #2196F3; }
                code { background: #f5f5f5; padding: 2px 4px; border-radius: 3px; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>🚀 Java HTTP Server</h1>
                    <p>Built with com.sun.net.httpserver</p>
                </div>
                
                <h2>Available Endpoints:</h2>
                <div class="endpoint">
                    <strong>GET /</strong> - This homepage
                </div>
                <div class="endpoint">
                    <strong>GET /hello</strong> - Simple greeting
                </div>
                <div class="endpoint">
                    <strong>GET /time</strong> - Current server time
                </div>
                <div class="endpoint">
                    <strong>POST /echo</strong> - Echo back posted data
                </div>
                <div class="endpoint">
                    <strong>GET /file/{filename}</strong> - Serve static files
                </div>
                
                <h2>Server Info:</h2>
                <ul>
                    <li>Port: <code>%d</code></li>
                    <li>Threads: <code>%d</code></li>
                    <li>Java Version: <code>%s</code></li>
                </ul>
            </div>
        </body>
        </html>
        """;

    static class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = String.format(HTML_TEMPLATE, PORT, Runtime.getRuntime().availableProcessors(), 
                                              System.getProperty("java.version"));
                sendResponse(exchange, 200, response);
            } else {
                sendResponse(exchange, 405, "Method Not Allowed");
            }
        }
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = """
                {
                    "message": "Hello from Java Server!",
                    "timestamp": "%s",
                    "status": "success"
                }
                """.formatted(java.time.LocalDateTime.now());
            sendResponse(exchange, 200, response, "application/json");
        }
    }

    static class TimeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = """
                {
                    "current_time": "%s",
                    "timezone": "UTC",
                    "server": "Java_HTTP_Server"
                }
                """.formatted(java.time.Instant.now());
            sendResponse(exchange, 200, response, "application/json");
        }
    }

    static class EchoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Read request body
                InputStream requestBody = exchange.getRequestBody();
                String body = new String(requestBody.readAllBytes());
                
                String response = """
                    {
                        "echo": "%s",
                        "received_at": "%s",
                        "length": %d
                    }
                    """.formatted(body.replace("\"", "\\\""), 
                                 java.time.LocalDateTime.now(), body.length());
                sendResponse(exchange, 200, response, "application/json");
            } else {
                sendResponse(exchange, 405, "Only POST method allowed");
            }
        }
    }

    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String filename = path.substring("/file/".length());
            
            try {
                String content = Files.readString(Paths.get(filename));
                sendResponse(exchange, 200, content, getContentType(filename));
            } catch (IOException e) {
                String response = """
                    {
                        "error": "File not found",
                        "filename": "%s",
                        "message": "%s"
                    }
                    """.formatted(filename, e.getMessage());
                sendResponse(exchange, 404, response, "application/json");
            }
        }
        
        private String getContentType(String filename) {
            if (filename.endsWith(".html")) return "text/html";
            if (filename.endsWith(".css")) return "text/css";
            if (filename.endsWith(".js")) return "application/javascript";
            if (filename.endsWith(".json")) return "application/json";
            return "text/plain";
        }
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) 
            throws IOException {
        sendResponse(exchange, statusCode, response, "text/html");
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response, String contentType) 
            throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.getResponseHeaders().set("Server", "Java-HTTP-Server/1.0");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        // Register handlers
        server.createContext("/", new HomeHandler());
        server.createContext("/hello", new HelloHandler());
        server.createContext("/time", new TimeHandler());
        server.createContext("/echo", new EchoHandler());
        server.createContext("/file/", new FileHandler());
        
        // Set executor for better performance
        server.setExecutor(Executors.newFixedThreadPool(10));
        
        server.start();
        
        System.out.println("🌐 Server started on http://localhost:" + PORT);
        System.out.println("📚 Available endpoints:");
        System.out.println("   GET  /      - Homepage");
        System.out.println("   GET  /hello - JSON greeting");
        System.out.println("   GET  /time  - Current time");
        System.out.println("   POST /echo  - Echo POST data");
        System.out.println("   GET  /file/ - Serve files");
        System.out.println("\nPress Ctrl+C to stop the server...");
    }

    public static void main(String[] args) {
        try {
            startServer();
            
            // Keep server running
            Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
