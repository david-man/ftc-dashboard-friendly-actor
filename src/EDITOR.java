import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class EDITOR {
    private static enum MODE{FILE_SELECTION, FILE_EDITING, RUNNING, FILE_SAVING}
    private static MODE currentMode = MODE.FILE_SELECTION;
    private static int pointer = 0;
    private static String file;

    public static void main(String[] args) throws IOException {
        BufferedReader SystemReader = new BufferedReader(new InputStreamReader(System.in));
        boolean toBreak = false;
        boolean writeRequested = false;
        boolean breakRequested = false;
        File reader = new File("./src/savefiles");
        String[] fileList = reader.list();
        if(fileList.length == 0)
        {
            PrintWriter pw = new PrintWriter(new FileWriter("empty_shell.txt"));
            pw.close();
        }
        ArrayList<String> fileLines = null;
        while(!toBreak)
        {
            if(breakRequested)
            {
                if(SystemReader.readLine().charAt(0) == 'b')
                {
                    toBreak = true;
                }
                else
                {
                    breakRequested = false;
                }
            }
            if(!toBreak)
            {
                if(currentMode == MODE.FILE_SELECTION)
                {
                    for(int i = 0; i<fileList.length; i++)
                    {
                        if(i == pointer)
                        {
                            System.out.println(">>> " + fileList[i]);
                        }
                        else
                        {
                            System.out.println(fileList[i]);
                        }
                    }
                    char nextInput = SystemReader.readLine().charAt(0);
                    if(nextInput == 'u')
                    {
                        if(pointer == 0)
                        {
                            pointer = fileList.length-1;
                        }
                        else
                        {
                            pointer-=1;
                        }
                    }
                    else if(nextInput == 'd')
                    {
                        if(pointer == fileList.length-1)
                        {
                            pointer = 0;
                        }
                        else
                        {
                            pointer+=1;
                        }
                    }
                    else if(nextInput == 'e')
                    {
                        currentMode = MODE.FILE_EDITING;
                        file = "./src/savefiles/"+fileList[pointer];
                        System.out.println("EDITING: " + file);
                        pointer = 0;
                    }
                    else if(nextInput == 'b')
                    {
                        breakRequested = true;
                        System.out.println("BREAK? enter b again to confirm. enter anything else to leave");
                    }
                }
                if(currentMode == MODE.FILE_EDITING)
                {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    if(writeRequested)
                    {
                        String newLine = SystemReader.readLine();
                        if(!newLine.equals("CANCEL"))
                        {
                            fileLines.set(pointer, newLine);
                            if(pointer == fileLines.size()-1)
                            {
                                pointer = fileLines.size();
                                fileLines.add("");
                            }
                        }
                        writeRequested = false;
                    }
                    else
                    {
                        if(fileLines == null)
                        {
                            fileLines = new ArrayList<>();
                            while(true)
                            {
                                String s = br.readLine();
                                if(s==null)
                                {
                                    break;
                                }
                                else
                                {
                                    fileLines.add(s);
                                }
                            }
                            fileLines.add("");
                        }
                        for(int i = 0; i<fileLines.size(); i++)
                        {
                            if(i == pointer)
                            {
                                System.out.println(">>> * " + fileLines.get(i));
                            }
                            else
                            {
                                System.out.println("* " + fileLines.get(i));
                            }
                        }
                        char nextInput = SystemReader.readLine().charAt(0);
                        if(nextInput == 'u')
                        {
                            if(pointer == 0)
                            {
                                pointer = fileLines.size()-1;
                            }
                            else
                            {
                                pointer-=1;
                            }
                        }
                        else if(nextInput == 'd')
                        {
                            if(pointer == fileLines.size()-1)
                            {
                                pointer = 0;
                            }
                            else
                            {
                                pointer+=1;
                            }
                        }
                        else if(nextInput == 'b')
                        {
                            breakRequested = true;
                            System.out.println("BREAK? enter b again to confirm. enter anything else to leave");
                        }
                        else if(nextInput == 'r')
                        {
                            System.out.println("CHANGING CURRENT LINE");
                            writeRequested = true;
                        }
                        else if(nextInput == 's')
                        {
                            System.out.println("SAVING TO CURRENT LIST NEW FILE");
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                            LocalDateTime now = LocalDateTime.now();
                            PrintWriter pw = new PrintWriter(new FileWriter(file + "_" + dtf.format(now)+".txt"));
                            for(String s : fileLines)
                            {
                                if(!s.equals(""))
                                {
                                    pw.println(s);
                                }
                            }
                            pw.close();
                        }
                    }
                }
            }

        }

    }
}
