package org.example;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.implementation.DestinationImpl;
import ru.pflb.mq.dummy.implementation.ProducerImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Session;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
       try {
           Connection connection = new ConnectionImpl(); //открываем соединение
           Session session = connection.createSession(true); //сессию
           DestinationImpl destination = new DestinationImpl("MyQueue");// новая очередь
           ProducerImpl producer =  new ProducerImpl(destination);//передаем продюсеру
           List<String> list = new ArrayList<>(); //новая коллекция
           list.add("Четыре"); //заполняем
           list.add("Пять");
           list.add("Шесть");
           Iterator<String> iterator = list.iterator();// итератор
           while (iterator.hasNext()) { //проходимся циклом
               producer.send(iterator.next()); //выводим сообщения
               TimeUnit.SECONDS.sleep(2); // задержка -  Thread.sleep(2000); можно было бы и так
           }
           session.close(); //закрываем сессию
           connection.close(); //соединение
       }
       catch (Exception e) {
           e.printStackTrace();
       }
    }
}