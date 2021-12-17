package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Сервер запущен.");
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Произошла ошибка при запуске или работе сервера.");
        }
    }

    public static void sendBroadcastMessage(Message message) {
        for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
            try {
                pair.getValue().send(message);
            } catch (IOException e) {
                System.out.println("Не удалось выполнить отправку соединению - " + pair.getValue().getRemoteSocketAddress());
            }
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Установленно соединение с сервером: " + this.socket.getRemoteSocketAddress());
            String userName = null;
            try (Connection connection = new Connection(this.socket);){
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);

            } catch (ClassNotFoundException | IOException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом " + socket.getRemoteSocketAddress());
            }
            if (userName != null) {
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                connectionMap.remove(userName);
            }
            ConsoleHelper.writeMessage("Соединение с " + socket.getRemoteSocketAddress() + " закрыто.");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();
                if (message.getType() != MessageType.USER_NAME) {
                    ConsoleHelper.writeMessage("Не соответствует тип сообщщения.");
                    continue;
                }
                String userName = message.getData();
                if (userName.isEmpty()) {
                    ConsoleHelper.writeMessage("Имя пользователя отсутствует.");
                    continue;
                }
                if (connectionMap.containsKey(userName)) {
                    ConsoleHelper.writeMessage("Данный пользователь уже находится в системе.");
                    continue;
                }
                connectionMap.put(userName, connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
                return userName;
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (String activeUserName : connectionMap.keySet()) {
                if (activeUserName.equals(userName))
                    continue;
                Message message = new Message(MessageType.USER_ADDED, activeUserName);
                connection.send(message);
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    String textMessage = String.format(userName + ": " + message.getData());
                    sendBroadcastMessage(new Message(MessageType.TEXT, textMessage));
                }
                else
                    ConsoleHelper.writeMessage("Пожалуйста, введите текст сообщения еще раз.");
            }
        }
    }
}
