package org.example;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.implementation.DestinationImpl;
import ru.pflb.mq.dummy.implementation.ProducerImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Session;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
       try {
           Connection connection = new ConnectionImpl();   //что-то открыли
           Session session = connection.createSession(true); //что-то начали
           DestinationImpl destination = new DestinationImpl("MyQueue"); //создали очередь
           ProducerImpl producer =  new ProducerImpl(destination); // кому-то передали
           boolean infinity = true; //для бесконечного чтения по кругу
           while (infinity) {
               BufferedReader reader = new BufferedReader(new FileReader(args[0])); //читаем файл
               while (reader.ready()) { //пока есть строки - читаем
                   producer.send(reader.readLine()); //отрправляем в консоль текущую строчку
                   TimeUnit.SECONDS.sleep(2); //ждем 2 секунды
                                      }
                            }
            //  здесь мог бы быть закрыт reader
           session.close(); //закрываем сессию
           connection.close(); // закрываем соединение
       }
       catch (Exception e) {
           e.printStackTrace();
       }
    }
}