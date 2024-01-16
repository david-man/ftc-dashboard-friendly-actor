import java.util.*;
import java.io.*;

public class RUNNER {
    public enum MODE{FILE_SELECTION, FILE_PARSING, FILE_RUNNING};
    private static MODE currentMode = MODE.FILE_SELECTION;
    private static int pointer = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader SystemReader = new BufferedReader(new InputStreamReader(System.in));
        boolean toBreak = false;
        boolean breakRequested = false;
        File reader = new File("./src/savefiles");
        String[] fileList = reader.list();
        String file_to_run = null;
        Actor actor = new Actor();
        boolean autoMoveOn = false;
        if(fileList.length == 0)
        {
            System.out.println("NO FILES TO RUN FOUND.");
        }
        else
        {
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
                    if(currentMode == RUNNER.MODE.FILE_SELECTION)
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
                            currentMode = MODE.FILE_PARSING;
                            file_to_run = "./src/savefiles/"+fileList[pointer];
                            System.out.println("RUNNING: " + file_to_run);
                        }
                        else if(nextInput == 'b')
                        {
                            breakRequested = true;
                            System.out.println("BREAK? enter b again to confirm. enter anything else to leave");
                        }
                    }
                    else if(currentMode == MODE.FILE_PARSING)
                    {
                        BufferedReader br = new BufferedReader(new FileReader(file_to_run));
                        int lineOn = 0;
                        for(String s = br.readLine(); s!=null;s = br.readLine())
                        {
                            Double timeout = null;//default vals
                            boolean parallel, perpetual = false;//default vals
                            Action action = null;

                            String[] split = s.split("!!");
                            StringTokenizer generalParamSplitter = new StringTokenizer(split[0]);
                            StringTokenizer actionParamSplitter = new StringTokenizer(split[1]);
                            ArrayList<String> generalParams = new ArrayList<>();
                            while(generalParamSplitter.hasMoreElements())
                            {
                                generalParams.add(generalParamSplitter.nextToken());
                            }
                            //find the general parameters
                            if(generalParams.size() == 1)
                            {
                                try { //timeout
                                    timeout = Double.parseDouble(generalParams.get(0));
                                }catch(NumberFormatException e) { //parallel
                                    try {
                                        parallel = parseBoolean(generalParams.get(0));
                                    }catch(BooleanFormatException b){
                                        System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                        toBreak = true;
                                    }
                                }
                            }
                            else if(generalParams.size() == 2)
                            {
                                try { //timeout, parallel
                                    timeout = Double.parseDouble(generalParams.get(0));
                                    try {
                                        parallel = parseBoolean(generalParams.get(1));
                                    }catch(BooleanFormatException b){
                                        System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                        toBreak = true;
                                    }
                                }catch(NumberFormatException e) { // parallel, perpetual
                                    try {
                                        parallel = parseBoolean(generalParams.get(0));
                                        try {
                                            perpetual = parseBoolean(generalParams.get(1));
                                        }catch(BooleanFormatException b){
                                            System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                            toBreak = true;
                                        }
                                    }catch(BooleanFormatException b){
                                        System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                        toBreak = true;
                                    }
                                }
                            }
                            else if(generalParams.size() == 3)
                            {
                                try{
                                    timeout = Double.parseDouble(generalParams.get(0));
                                    try {
                                        parallel = parseBoolean(generalParams.get(1));
                                        try {
                                            perpetual = parseBoolean(generalParams.get(2));
                                        }catch(BooleanFormatException b){
                                            System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                            toBreak = true;
                                        }
                                    }catch(BooleanFormatException b){
                                        System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                        toBreak = true;
                                    }
                                }catch(NumberFormatException e)
                                {
                                    System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                    toBreak = true;
                                }
                            }

                            ArrayList<String> actionParams = new ArrayList<>();
                            while(actionParamSplitter.hasMoreElements())
                            {
                                actionParams.add(actionParamSplitter.nextToken());
                            }

                            if(actionParams.get(0).equalsIgnoreCase("CTA"))
                            {
                                try
                                {
                                    action = new ClawAction(Double.parseDouble(actionParams.get(1)));
                                }catch(NumberFormatException e)
                                {
                                    try
                                    {
                                        action = new ClawAction(parseBoolean(actionParams.get(1)));
                                    } catch (BooleanFormatException ex) {
                                        System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                        toBreak = true;
                                    }
                                }
                            }
                            else if(actionParams.get(0).equalsIgnoreCase("COA"))
                            {
                                ClawAction.ClawStates top = null;
                                ClawAction.ClawStates bottom = null;
                                try{
                                    if(parseBoolean(actionParams.get(1)))
                                    {
                                        top = ClawAction.ClawStates.topOpen;
                                        try{
                                            if(parseBoolean(actionParams.get(2)))
                                            {
                                                bottom = ClawAction.ClawStates.bottomOpen;
                                            }
                                            else
                                            {
                                                bottom = ClawAction.ClawStates.bottomClosed;
                                            }
                                        }catch (BooleanFormatException ex) {
                                            System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                            toBreak = true;
                                        }
                                    }
                                    else
                                    {
                                        top = ClawAction.ClawStates.topClosed;
                                    }
                                }catch (BooleanFormatException ex) {
                                    System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                    toBreak = true;
                                }
                                action = new ClawAction(top, bottom);
                            }
                            else if(actionParams.get(0).equalsIgnoreCase("LA"))
                            {
                                try
                                {
                                    action = new LiftAction((int)Double.parseDouble(actionParams.get(1)), Double.parseDouble(actionParams.get(2)));
                                }catch(NumberFormatException e)
                                {
                                    System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                    toBreak = true;
                                }
                            }
                            else if(actionParams.get(0).equalsIgnoreCase("MA"))
                            {
                                if(actionParams.size() == 4)
                                {
                                    try
                                    {
                                        action = new MvntAction(new Pose2d(Double.parseDouble(actionParams.get(1)), Double.parseDouble(actionParams.get(2)),
                                                new Rotation2d(Double.parseDouble(actionParams.get(3)))));
                                    }catch(NumberFormatException e)
                                    {
                                        System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                        toBreak = true;
                                    }
                                }
                                else
                                {
                                    try
                                    {
                                        action = new MvntAction(new Pose2d(Double.parseDouble(actionParams.get(1)), Double.parseDouble(actionParams.get(2)),
                                                new Rotation2d(Double.parseDouble(actionParams.get(3)))), Double.parseDouble(actionParams.get(4)));
                                    }catch(NumberFormatException e)
                                    {
                                        System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                        toBreak = true;
                                    }
                                }

                            }
                            else
                            {
                                System.out.println("CHECK YOUR FORMATTING! ERROR ON LINE " + lineOn);
                                toBreak = true;
                            }
                            if(action!=null){
                                actor.add(action);
                            }

                            lineOn++;
                        }
                        if(!toBreak)
                        {
                            currentMode = MODE.FILE_RUNNING;

                        }
                    }
                    else if(currentMode == MODE.FILE_RUNNING)
                    {
                        int x = actor.run();
                        if(x == 0)
                        {
                            System.out.println("DONE WITH PROCESS");
                            toBreak = true;
                        }
                        else if(x == -1)
                        {
                            //placeholder for if runner isn't done with current step
                            System.out.println("CURRENTLY RUNNING STEP #" + actor.actionOn);
                        }
                        else
                        {
                            if(!autoMoveOn)
                            {
                                System.out.println("MOVE ONTO NEXT STEP?(y for yes, anything else to break)");
                                if(SystemReader.readLine().charAt(0) != 'y')
                                {
                                    toBreak = true;
                                }
                            }
                            System.out.println("MOVING ONTO NEXT STEP.");
                        }

                    }
                }
            }
        }
    }
    public static boolean parseBoolean(String s) throws BooleanFormatException
    {
        if(s.equalsIgnoreCase("true"))
        {
            return true;
        }
        else if(s.equalsIgnoreCase("false"))
        {
            return false;
        }
        else
        {
            throw new BooleanFormatException("NO CORRESPONDANCE");
        }
    }
    static class BooleanFormatException extends Exception {
        public BooleanFormatException(String errorMessage) {
            super(errorMessage);
        }
    }
}
