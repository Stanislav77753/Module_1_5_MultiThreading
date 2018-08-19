package main.java.com.stanislav.multithreadingApp.FilesParser;

import main.java.com.stanislav.multithreadingApp.exceptions.DirectoryIsEmptyException;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class DirectoryParser {
    File[] files;
    ExecutorService es = Executors.newFixedThreadPool(4);

    public DirectoryParser(File directory) {
        files = directory.listFiles();
    }

    public void parseFiles() throws DirectoryIsEmptyException {
        AtomicLong countFiles = new AtomicLong();
        if(files.length == 0){
            throw new DirectoryIsEmptyException("directory is empty");
        }
        else{
            for(File file: files){
                if(file.isFile()){
                    es.execute(()->{
                        String name = file.getName();
                        int index = name.lastIndexOf('.');

                        System.out.println("Имя файла - " + name.substring(0, index) + "\t" + "Расширение файла - " +
                                name.substring(index + 1) + "\t" + "Размер файла - " + file.length() + " байт" + "\t" +
                                "Поток выполнения: " + Thread.currentThread().getName());
                        countFiles.getAndIncrement();
                    });
                }
            }
            es.shutdown();
            try {
                es.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Количество файлов - " + countFiles);
    }
}
