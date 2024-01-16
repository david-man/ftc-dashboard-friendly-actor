import java.util.*;
import java.io.*;

public class format {
    public static void main(String[] args) throws IOException {
        BufferedReader SystemReader = new BufferedReader(new InputStreamReader(System.in));
        int pointer = 0;
        boolean toBreak = false;
        boolean breakRequested = false;
        File reader = new File("./src/formatfiles");
        String[] fileList = reader.list();
        if(fileList.length == 0)
        {
            System.out.println("NO FILES FOUND.");
        }
        else
        {
            while(!toBreak) {
                if (breakRequested) {
                    if (SystemReader.readLine().charAt(0) == 'b') {
                        toBreak = true;
                    } else {
                        breakRequested = false;
                    }

                }
                if(!toBreak)
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


                    }
                    else if(nextInput == 'b')
                    {
                        breakRequested = true;
                        System.out.println("BREAK? enter b again to confirm. enter anything else to leave");
                    }
                }
            }
        }
    }
}
