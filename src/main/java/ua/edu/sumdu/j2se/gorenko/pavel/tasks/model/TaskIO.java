package ua.edu.sumdu.j2se.gorenko.pavel.tasks.model;

import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class TaskIO {

    public static DateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");

    public static void write(TaskList tasks, OutputStream out) throws IOException {
        if (tasks == null) {
            throw new NullPointerException();
        }
        int size = tasks.size();
        DataOutputStream outStream = new DataOutputStream(out);
        try {          
                outStream.writeInt(size);
                
            for (Task t :tasks) {               
                String title = t.getTitle();
                outStream.writeInt(title.length());
                outStream.writeUTF(title);
                outStream.writeBoolean(t.isActive());
                outStream.writeInt(t.getRepeatInterval());
                outStream.writeLong(t.getStartTime().getTime());
                outStream.writeLong(t.getEndTime().getTime());
                }
            
        } finally {
            outStream.flush();
            outStream.close();
        }    
    }    

    public static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream inputStream = new DataInputStream(in);
        int size = inputStream.readInt();
//        System.out.println("size - "+size);
        try {           
            for (int i = 0; i < size; i++) {
                int titleLength = inputStream.readInt();
                String title = inputStream.readUTF();
                boolean active = inputStream.readBoolean();          
                int interval = inputStream.readInt();       
                Date start = new Date(inputStream.readLong());
                Date end = new Date(inputStream.readLong());
                Task taskNew = null;
                if (start.equals(end)) {
                    taskNew = new Task(title, start);           
                } else {
                    taskNew = new Task(title, start, end, interval);
                }
                taskNew.setActive(active);
                tasks.add(taskNew);
            }
        } finally {
            inputStream.close();
        }
        
    }

    public static void writeBinary(TaskList tasks, File file) throws IOException {
        FileOutputStream outputFile = new FileOutputStream(file);
        try {
            write(tasks, outputFile);
        } finally {
            outputFile.close();
        }
    }
    
    public static void readBinary(TaskList tasks, File file) throws IOException {
        FileInputStream inputFile = new FileInputStream(file);
        try {
            read(tasks, inputFile);
        } finally {
            inputFile.close();
        }    
    }
    
    public static void write(TaskList tasks, Writer out) throws IOException {

        BufferedWriter outStream = new BufferedWriter(out);
        int counter = 0;  
        try {
            for (Task t : tasks) {
                counter++;
                outStream.write("\"" + t.getTitle() + "\"");

                if (!t.isRepeated()) {
                    outStream.write(" at ");
                    outStream.write(dateFormat.format(t.getTime()));
                    if (!t.isActive())
                        outStream.write(" inactive");
                } else {
                    outStream.write(" from ");
                    outStream.write(dateFormat.format(t.getStartTime()));
                    outStream.write(" to ");
                    outStream.write(dateFormat.format(t.getEndTime()));      
                }

                outStream.write( ((tasks.size() == counter) ? "." : ";\n") );
            }
        } finally {
            outStream.flush();
            outStream.close();
        }
    }
    
    public static void read(TaskList tasks, Reader in) throws IOException, ParseException {
        Scanner inStream = new Scanner(in);
        String s = "";
        try {
            while (inStream.hasNextLine()) {
                 s = inStream.nextLine();
                 String readTitle = s.substring(1, s.indexOf("\"", 1));
                 Task task = null;
                 int repeated = s.indexOf("at");
                 if (repeated != -1) {
                 String readTime = s.substring(s.indexOf("[",1), s.indexOf("]") + 1);
                 Date newTime = dateFormat.parse(readTime);
                 task = new Task(readTitle, newTime);
                 } else {
                    String readStart = s.substring(s.indexOf("[",1), s.indexOf("]") + 1);
                    Date newStart = dateFormat.parse(readStart);
                    int endIndex = s.indexOf("to");
                    String readEnd = s.substring(s.indexOf("[", endIndex), s.indexOf("]", endIndex) + 1);
                    Date newEnd = dateFormat.parse(readEnd);
                    task = new Task(readTitle, newStart, newEnd, 1);
                 }
                 int active = s.indexOf("inactive");
                 if (active == -1) {
                    task.setActive(true);
                 }         
                 tasks.add(task);
            }
        } finally {
            inStream.close();
        }
    }
    
    public static void writeText(TaskList tasks, File file) throws IOException {
        FileWriter outputFile = new FileWriter(file);
        try {
            write(tasks, outputFile);
        } finally {
            outputFile.close();
        }
    }
    
    public static void readText(TaskList tasks, File file) throws IOException, ParseException  {
        FileReader inputFile = new FileReader(file);
        try {
            read(tasks, inputFile);
        } finally {
            inputFile.close();
        }
    } 
}
