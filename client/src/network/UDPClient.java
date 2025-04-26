package network;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class UDPClient {
    int port = 8931;
    InetAddress host = InetAddress.getLocalHost();
    SocketAddress addr = new InetSocketAddress(host,port);

    private boolean flagOfTimeOut = false;

    public boolean isFlagOfTimeOut() {
        return flagOfTimeOut;
    }

    public UDPClient() throws UnknownHostException {
    }
    public Response sendAndReceiveData(Request request) {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.configureBlocking(false); // Устанавливаем блокирующий режим работы канала
            byte[] requestData = serializeObject(request);

            ByteBuffer buffer = ByteBuffer.wrap(requestData);
            datagramChannel.send(buffer, addr);

            buffer.clear();

            // Таймаут для ожидания ответа
            long timeout = 250;
            datagramChannel.socket().setSoTimeout((int) timeout);

            // Попытка получить ответ в течение заданного времени
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < timeout) {
                buffer = ByteBuffer.allocate(65507);
                SocketAddress remoteAddr = datagramChannel.receive(buffer);
                if (remoteAddr != null) {
                    buffer.flip();
                    Response response = deserializeObject(buffer.array());
                    return response;
                }
            }
            System.err.println("Сервер недоступен");
        } catch (SocketTimeoutException e) {
            System.err.println("Превышено время ожидания");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Сервер не отвечает");
        }
        return null;
    }


    private static byte[] serializeObject(Object obj) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(obj);
        objectStream.flush();
        return byteStream.toByteArray();
    }
    private Response deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
             ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            return (Response) objectStream.readObject();
        }
    }
}













    /*
    int port = 8931;
    InetAddress host = InetAddress.getLocalHost();
    SocketAddress addr = new InetSocketAddress(host,port);

    private boolean flagOfTimeOut = false;

    public boolean isFlagOfTimeOut() {
        return flagOfTimeOut;
    }

    public UDPClient() throws UnknownHostException {
    }
    public Response sendAndReceiveData(Request request) {

        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.configureBlocking(false);
            Selector selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_READ);
            byte[] requestData = serializeObject(request);

            ByteBuffer buffer = ByteBuffer.wrap(requestData);

            datagramChannel.send(buffer, addr);

            buffer.clear();

            // таймаут
            long timeout = 250;
            long startTime = System.currentTimeMillis();

            while (true) {
                // Проверяем, прошел ли уже таймаут
                if (System.currentTimeMillis() - startTime > timeout) {
                    flagOfTimeOut = true;
                    System.err.println("Сервер недоступен");
                    break;
                }
                flagOfTimeOut = false;
                int readyChannels = selector.select(timeout); // Ждем доступных каналов или таймаута
                if (readyChannels > 0) {
                    for (SelectionKey key : selector.selectedKeys()) {
                        if (key.isReadable()) {
                            DatagramChannel channel = (DatagramChannel) key.channel();
                            buffer = ByteBuffer.allocate(65507);
                            channel.receive(buffer);
                            buffer.flip();
                            Response response = deserializeObject(buffer.array());
                            return response;
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Сервер не отвечает");
        }
        return null;
    }

    private static byte[] serializeObject(Object obj) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(obj);
        objectStream.flush();
        return byteStream.toByteArray();
    }
    private Response deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
             ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
             return (Response) objectStream.readObject();
        }
    }
}
     */

