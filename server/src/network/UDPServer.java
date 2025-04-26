package network;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class UDPServer {

    private final ForkJoinPool forkJoinPool1 = new ForkJoinPool();
    private final ForkJoinPool forkJoinPool2 = new ForkJoinPool();
    private int port = 8931;
    private SocketAddress addr = new InetSocketAddress(port);
    private DatagramChannel datagramChannel = DatagramChannel.open();

    public UDPServer() throws IOException {
        datagramChannel.bind(addr);
    }
    public void sendData(Response response) {
        // Создаем задачу для отправки данных
        Runnable task = () -> {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(65507); // Максимальный размер пакета UDP
                byte[] responseData = serializeObject(response);
                buffer = ByteBuffer.wrap(responseData);
                datagramChannel.send(buffer, addr);
            } catch (IOException e) {
                System.err.println("Слишком большое сообщение!");
            }
        };
        forkJoinPool1.execute(task);
    }


    public Request receiveData() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(65507); // Максимальный размер пакета UDP
            addr = datagramChannel.receive(buffer);
            byte[] requestData = buffer.array();

            // Создаем задачу для обработки запроса
            ForkJoinTask<Request> task = ForkJoinTask.adapt(() -> deserializeObject(requestData));

            // Асинхронно выполняем задачу в пуле и возвращаем результат
            return forkJoinPool2.invoke(task);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private byte[] serializeObject(Object obj) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(obj);
            return byteStream.toByteArray();
        }
    }

    private Request deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
             ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            return (Request) objectStream.readObject();
        }
    }


}




    /*

    int port = 8931;
    SocketAddress addr = new InetSocketAddress(port);
    DatagramChannel datagramChannel = DatagramChannel.open();

    public UDPServer() throws IOException {
        datagramChannel.bind(addr);
    }
    public void sendData(Response response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(65507); // Максимальный размер пакета UDP
            byte[] responseData = serializeObject(response);
            buffer = ByteBuffer.wrap(responseData);
            datagramChannel.send(buffer, addr);
        } catch (IOException e) {
            System.err.println("Слишком большое сообщение!");
        }
    }
    public Request receiveData() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(65507); // Максимальный размер пакета UDP
            addr = datagramChannel.receive(buffer);
            byte[] requestData = buffer.array();
            Request request = deserializeObject(requestData);
            return request;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] serializeObject(Object obj) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(obj);
            return byteStream.toByteArray();
        }
    }

    private Request deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
             ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
            return (Request) objectStream.readObject();
        }
    }
}

     */

