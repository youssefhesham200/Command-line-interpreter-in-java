import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.List;

class Parser {
    String commandName;
    String[] args;
    String[] commands = new String[]{"cd", "pwd", "ls", "ls-r", "echo", "mkdir", "rmdir", "touch", "cp", "cp-r", "rm", "cat", "exit"};
    List<String> list = Arrays.asList(commands);
    //This method will divide the input into commandName and args
    // where "input" is the string command entered by the user
    public boolean parse(String input){
        String[] inputs = input.split("\\s+");
        commandName = inputs[0];
        args = Arrays.copyOfRange(inputs,1,inputs.length);
        return list.contains(commandName);
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}


class Terminal {
    static Parser p = new Parser();
    static Terminal t = new Terminal();

    //command: echo
    public String echo(String... args){
        String s = "";
        if(args.length < 1){
            s = "\"Error :command not found ot bad parameters entered \"";
        }
        else{
            for (String arg : args) {
                s += arg ;
                s+=" ";
            }
            if(s.length() > 2){
                s = s.substring(1,s.length()-2);
            }}
        return s;
    }

    //command: pwd
    public String pwd(String... args) throws IOException {
        String s = "";
        File f =  new File(System.getProperty("user.dir"));
        s = f.getAbsoluteFile().toString();
        if(args.length > 0 && Objects.equals(args[0], ">")){
            File file = new File(args[1]);
                FileOutputStream fos = new FileOutputStream(file, false);
                fos.write(s.getBytes());
                fos.close();
                return null;
        }
         else if(args.length > 0 && Objects.equals(args[0], ">>")){
                File fi = new File(args[1]);
                FileOutputStream fos = new FileOutputStream(fi, true);
                fos.write(s.getBytes());
                fos.close();
                return null;
        }
         else if(args.length > 0 && !Objects.equals(args[0], ">>") && !Objects.equals(args[0], ">")){
            s = "Error :command not found ot bad parameters entered";
         }
        return s;
    }

    // change dir
    public void cd(String... args){
        if(args.length == 0) {
            System.setProperty("user.dir", System.getProperty("user.home"));
        }
        else if(args.length == 1 && Objects.equals(args[0], "..")){
            File f =  new File(System.getProperty("user.dir"));
            String pa = f.getAbsoluteFile().getParent();
            System.setProperty("user.dir", pa);
        }
        else if(args.length == 1){
            File f = new File(args[0]);
            if(f.isDirectory()){
                System.setProperty("user.dir", args[0]);
            }
            else{
                System.out.println("this is not Directory");
            }
        }
    }

    public void ls(String... args) throws IOException {
        File f = new File(System.getProperty("user.dir"));
        String[] list1 = f.list();
        assert list1 != null;
        Arrays.sort(list1);
        if(args.length > 0 && Objects.equals(args[0], ">")){
            File file = new File(args[1]);
            FileOutputStream fos = new FileOutputStream(file, false);
            for(String i:list1){
                fos.write(i.getBytes());
            }
            fos.close();
        }
        else if(args.length > 0 && Objects.equals(args[0], ">>")){
            File file = new File(args[1]);
            FileOutputStream fos = new FileOutputStream(file, true);
            for(String i:list1){
                fos.write(i.getBytes());
            }
            fos.close();
        }
        else if(args.length > 0 && !Objects.equals(args[0], ">>") && !Objects.equals(args[0], ">")){
            System.out.println( "Error :command not found ot bad parameters entered");
        }
        else{
            for(String i:list1){
                System.out.println(i);
            }
        }
    }

    public void ls_r(String... args) throws IOException {
        File f = new File(System.getProperty("user.dir"));
        String[] list1 = f.list();
        assert list1 != null;
        Arrays.sort(list1, Collections.reverseOrder());
        if(args.length > 0 && Objects.equals(args[0], ">")){
            File file = new File(args[1]);
            FileOutputStream fos = new FileOutputStream(file, false);
            for(String i:list1){
                fos.write(i.getBytes());
            }
            fos.close();
        }
        else if(args.length > 0 && Objects.equals(args[0], ">>")){
            File file = new File(args[1]);
            FileOutputStream fos = new FileOutputStream(file, true);
            for(String i:list1){
                fos.write(i.getBytes());
            }
            fos.close();
        }
        else if(args.length > 0 && !Objects.equals(args[0], ">>") && !Objects.equals(args[0], ">")){
            System.out.println( "Error :command not found ot bad parameters entered");
        }
        else{
            for(String i:list1){
                System.out.println(i);
            }
        }
    }

    public void mkdir(String... args){
        for(String arg:args){
            if(arg.contains("/") || arg.contains("\\")){
                File theDir = new File(arg);
                if (!theDir.exists()){
                    theDir.mkdirs();
                }
            }
            else {
                File theDir = new File(System.getProperty("user.dir") + File.separator + arg);
                if (!theDir.exists()){
                    theDir.mkdirs();
                }
            }
        }
    }

    public void rmdir(String... args){
        if(args.length < 1){
            System.out.println("Error :command not found ot bad parameters entered");
        }
        else if(args.length == 1 && Objects.equals(args[0], "*")){
            File f = new File(System.getProperty("user.dir"));
            File[] list1 = f.listFiles();
            assert list1 != null;
            for(File k:list1){
                boolean n = k.delete();
                if(n){System.out.println("the directory is deleted");}
                else{System.out.println("can't delete file");}
            }
        }
        else if(args.length == 1){
            File t = null;
            if(args[0].contains("/") || args[0].contains("\\")){
                 t = new File(args[0]);
            }
            else {
                 t = new File(System.getProperty("user.dir") + File.separator + args[0]);
            }
            if(t.exists()){
                boolean n = t.delete();
                if(n){System.out.println("the file is deleted");}
                else{System.out.println("can't delete file");}}
            else{ System.out.println("file doesn't exists");}
        }
    }

    public void touch(String... args) throws IOException {
        File t = null;
        if(args[0].contains("/") || args[0].contains("\\")){
            t = new File(args[0]);
        }
        else {
            t = new File(System.getProperty("user.dir") + File.separator + args[0]);
        }
        t.createNewFile();
    }

    public void cp(String... args) throws IOException {
        if(args.length != 2){System.out.println("Error :command not found ot bad parameters entered");}
        else{
            BufferedReader buffer;
            File file = null;
            if(args[0].contains("/") || args[0].contains("\\")){
                 buffer  = new BufferedReader(new FileReader(args[0]));
            }
            else {
                 buffer  = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + args[0]));
            }

            if(args[1].contains("/") || args[1].contains("\\")){
                 file = new File(args[1]);
            }
            else {
                 file = new File(System.getProperty("user.dir") + File.separator + args[1]);
            }

            PrintWriter out= new PrintWriter(new BufferedWriter(new FileWriter(file)));
           String line = buffer.readLine();
           while(line != null){
                out.write(line);
                out.write("\n");
                line = buffer.readLine();
           }
           out.close();
        }
    }


    public static void copydic(File from, File to)
    {
        if (from.isDirectory())
        {
            if (!to.exists())
            {
                to.mkdirs();
            }
            String[] files = from.list();

            assert files != null;
            for (String i : files)
            {
                File src = new File(from, i);
                File dest = new File(to, i);
                copydic(src, dest);
            }
        }
    }

    public void cp_r(String... args){
        if(args.length != 2){System.out.println("Error :command not found ot bad parameters entered");}
        else{
            File from = new File(args[0]);
            File to = new File(args[1]);
            copydic(from,to);
        }
    }

    public void rm(String... args){
        if(args.length != 1){System.out.println("Error :command not found ot bad parameters entered");}
        else{
            File f = null;
            if(args[0].contains("/") || args[0].contains("\\")){
                f = new File(args[0]);
            }
            else {
                f = new File(System.getProperty("user.dir") + File.separator + args[0]);
            }

            if(f.exists()){
                if(f.isDirectory()){
                System.out.println("this is directory not file");
            }
            else{
                if(f.delete()){System.out.println("the file is deleted");}
                else{System.out.println("can't delete file");}
            }}
           else{
               System.out.println("file doesn't exists");
            }
        }
    }

    public void cat(String... args) throws IOException {
        if(args.length != 1 && args.length != 2){
            System.out.println("Error :command not found ot bad parameters entered");
        }
        else {
            BufferedReader buffer1;
            if(args[0].contains("/") || args[0].contains("\\")){
                 buffer1 = new BufferedReader(new FileReader(args[0]));
            }
            else {
                buffer1 = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + args[0]));
            }

            String line = buffer1.readLine();
            while (line != null) {
                System.out.println(line);
                line = buffer1.readLine();
            }
            if (args.length == 2) {
                BufferedReader buffer2;
                if(args[1].contains("/") || args[1].contains("\\")){
                    buffer2 = new BufferedReader(new FileReader(args[1]));
                }
                else {
                    buffer2 = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + args[1]));
                }
                String line1 = buffer2.readLine();
                while (line1 != null) {
                    System.out.println(line1);
                    line1 = buffer2.readLine();
                }
            }
        }
    }

    //choose command action
    public void chooseCommandAction(String name, String... args) throws IOException {
        switch (name) {
            case "pwd" -> {
                if(pwd(args) != null){System.out.println(pwd(args));}
            }
            case "echo" -> System.out.println(echo(args));
            case "cd" -> cd(args);
            case "ls" -> ls(args);
            case "ls-r" -> ls_r(args);
            case "mkdir" -> mkdir(args);
            case "rmdir" -> rmdir(args);
            case "touch" -> touch(args);
            case "cp" -> cp(args);
            case "cp-r" -> cp_r(args);
            case "rm" -> rm(args);
            case "cat" -> cat(args);
            case "exit" -> System.exit(0);
            default -> System.out.println("command not found");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc= new Scanner(System.in);
        String s = "";
        do {
            s = sc.nextLine();
            boolean f =  p.parse(s);
            if(f){t.chooseCommandAction(p.getCommandName(), p.getArgs());}
            else{System.out.println("command not found");}
        } while (!s.equalsIgnoreCase("exit"));
    }
}