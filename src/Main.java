import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

//Maybe can create 4 csvs instead of 2 and split type between anime and TV
class main {
    static public int numResults;

    public static void main(String[] args) {
        //Loading Files
        ArrayList<Program> programs = loadData("watching.csv");
        ArrayList<Program> programsC = loadData("complete.csv");

        //Main Loop
        boolean editing = true;
        boolean complete = false;
        boolean airSec;
        boolean futSec;
        while (editing) {
            airSec = true;
            futSec = true;
            if (!complete) {
                programs.sort(new ProgramComparator());
                for (int i = 0; i < programs.size(); i++) {
                    if (i == 0) {
                        System.out.println("-----------------------------------------------------------");
                    }
                    if (programs.get(i).airing == Airing.AIRED && airSec) {
                        airSec = false;
                        System.out.println("-----------------------------------------------------------");
                    }
                    if (programs.get(i).airing == Airing.FUTURE && futSec) {
                        futSec = false;
                        System.out.println("-----------------------------------------------------------");
                    }
                    System.out.println("[" + i + "] " + programs.get(i));
                }
            } else {
                programsC.sort(new CompleteProgramComparator());
                for (int i = 0; i < programsC.size(); i++) {
                    System.out.println("[" + i + "] " + programsC.get(i));
                }
            }
            String choice;
            int choiceI;
            Scanner input = new Scanner(System.in);
            System.out.println("-----------------------------------------------------------");
            System.out.println("(i)ncrement progress, (m)ove to/from completed, (d)elete, (e)dit, (a)dd, (s)earch, s(w)itch view to/from completed, (p)rogress to next season, sa(v)e, save and (q)uit, quit wit(h)ouut saving");
            choice = input.nextLine();
            choice.toLowerCase();
            switch (choice) {
                case "a":
                    String title;
                    int epOn;
                    int epTot;
                    String airS;
                    Airing air = Airing.AIRED;
                    try {
                        System.out.println("Enter title");
                        title = input.nextLine();
                        System.out.println("Enter episode currently on");
                        epOn = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter total episodes");
                        epTot = input.nextInt();
                        input.nextLine();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("invalid choice");
                        input.nextLine();
                        input.nextLine();
                        continue;
                    }
                    System.out.println("Enter t if airing, enter n if it airing in the next season");
                    airS = input.nextLine();
                    airS.toLowerCase();
                    if (airS.equals("t")) {
                        air = Airing.AIRING;
                    } else if (airS.equals("n")) {
                        air = Airing.FUTURE;
                    }
                    if (epOn > epTot) {
                        epOn = epTot;
                    }
                    Program p = new Program(title, epOn, epTot, air);
                    if (!complete) {
                        programs.add(p);
                    } else {
                        programsC.add(p);
                    }
                    continue;
                case "p":
                    System.out.println("This will set all programs from the next season to be currently airing.\nPress y to confirm");
                    String y = input.nextLine();
                    y.toLowerCase();
                    if (y.equals("y")) {
                        for (int i = 0; i < programs.size(); i++) {
                            if (programs.get(i).airing == Airing.FUTURE) {
                                programs.get(i).airing = Airing.AIRING;
                            }
                        }
                    }
                    continue;
                case "s":
                    System.out.println("Enter Query");
                    String q = input.nextLine();
                    System.out.println("Searching for " + q);
                    System.out.println("Results:\n-----------------------------------------------------------");
                    if (!complete) {
                        System.out.println(findProgram(q, programs));
                    } else {
                        System.out.println(findProgram(q, programsC));
                    }
                    System.out.println();
                    System.out.println(numResults + " Results");
                    input.nextLine();
                    continue;
                case "w":
                    complete = !complete;
                    continue;
                case "v":
                    System.out.println("Saving");
                    save(programs, false);
                    save(programsC, true);
                    System.out.println("Complete");
                    input.nextLine();
                    System.out.println();
                    continue;
                case "q":
                    System.out.println("Saving");
                    save(programs, false);
                    save(programsC, true);
                case "h":
                    System.out.println("Quitting");
                    System.exit(0);
                case "i":
                case "m":
                case "d":
                case "e":
                    break;
                default:
                    System.out.println("invalid choice");
                    input.nextLine();
                    continue;
            }

            System.out.println("Select an entry");
            try {
                choiceI = input.nextInt();
                int i = programsC.size();
                if (!complete) {
                    i = programs.size();
                }
                if (choiceI > i - 1 || choiceI < 0) {
                    System.out.println("invalid choice");
                    input.nextLine();
                    input.nextLine();
                    continue;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("invalid choice");
                input.nextLine();
                input.nextLine();
                continue;
            }

            switch (choice) {
                case "i":
                    if (!complete) {
                        programs.get(choiceI).incrementEpisode();
                        System.out.println("Incremented " + '"' + programs.get(choiceI).getTitle() + '"' + " to " + programs.get(choiceI).currentEp);
                        input.nextLine();
                        if (programs.get(choiceI).currentEp == programs.get(choiceI).totalEp) {
                            String c2;
                            System.out.println("Do you wish to move " + programs.get(choiceI).getTitle() + " to completed Programs? y/n");
                            c2 = input.nextLine();
                            if (c2.toLowerCase().equals("y")) {
                                programs.get(choiceI).complete();
                                programsC.add(programs.get(choiceI));
                                programs.remove(choiceI);
                                System.out.println("Moved");
                            } else {
                                System.out.println("Ignored");
                            }
                        }
                        input.nextLine();
                    } else {
                        programsC.get(choiceI).incrementEpisode();
                        System.out.println("Incremented " + '"' + programsC.get(choiceI).getTitle() + '"' + " to " + programsC.get(choiceI).currentEp);
                        input.nextLine();
                        input.nextLine();
                    }
                    continue;
                case "m":
                    if (!complete) {
                        System.out.println("Moved " + programs.get(choiceI).getTitle());
                        input.nextLine();
                        input.nextLine();
                        programs.get(choiceI).complete();
                        programsC.add(programs.get(choiceI));
                        programs.remove(choiceI);
                    } else {
                        System.out.println("Moved " + programsC.get(choiceI).getTitle());
                        input.nextLine();
                        input.nextLine();
                        programs.add(programsC.get(choiceI));
                        programsC.remove(choiceI);
                    }
                    continue;
                case "d":
                    if (!complete) {
                        System.out.println("Deleted " + programs.get(choiceI).getTitle());
                        programs.remove(choiceI);
                        input.nextLine();
                        input.nextLine();
                    } else {
                        System.out.println("Deleted " + programsC.get(choiceI).getTitle());
                        programsC.remove(choiceI);
                        input.nextLine();
                        input.nextLine();
                    }
                    continue;
                case "e":
                    String prN = programsC.get(choiceI).getTitle();
                    if (!complete) {
                        prN = programs.get(choiceI).getTitle();
                    }
                    System.out.println("Editing " + prN);
                    String t;
                    int c;
                    String cS;
                    int tl;
                    String tlS;
                    Airing a = Airing.AIRED;
                    boolean e;
                    String ec;
                    input.nextLine();
                    System.out.println("New title, leave blank to ignore field");
                    t = input.nextLine();
                    try {
                        System.out.println("New current ep, leave blank to ignore field");
                        cS = input.nextLine();
                        if (cS.equals("")) {
                            c = -1;
                        } else {
                            c = Integer.parseInt(cS);
                        }
                        System.out.println("New total ep, leave blank to ignore field");
                        tlS = input.nextLine();
                        if (tlS.equals("")) {
                            tl = -1;
                        } else {
                            tl = Integer.parseInt(tlS);
                        }
                        System.out.println("New airing, use t for airing, n if it airing in the next season, anything else for not airing, leave blank to ignore field");
                        ec = input.nextLine();
                        ec.toLowerCase();
                        if (ec.equals("")) {
                            e = false;
                        } else if (ec.equals("t")) {
                            a = Airing.AIRING;
                            e = true;
                        } else if (ec.equals("n")) {
                            a = Airing.FUTURE;
                            e = true;
                        } else {
                            a = Airing.AIRED;
                            e = true;
                        }
                    } catch (java.util.InputMismatchException | java.lang.NumberFormatException ee) {
                        System.out.println("invalid choice");
                        input.nextLine();
                        continue;
                    }
                    if (!complete) {
                        programs.get(choiceI).editData(t, c, tl, a, e);
                        System.out.println("Edited Entry \"" + programs.get(choiceI) + "\"");
                    } else {
                        programsC.get(choiceI).editData(t, c, tl, a, e);
                        System.out.println("Edited Entry \"" + programsC.get(choiceI) + "\"");
                    }
                    input.nextLine();
            }
        }
    }

    private static ArrayList<Program> loadData(String file) {
        //Method to load a string int int boolean csv file into memory as a Program class array list
        ArrayList<Program> programs = new ArrayList<>();
        File watchingFile = new File(file);
        if (!watchingFile.exists()) {
            try {
                watchingFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String watchingOut = null;
        BufferedReader watchingRead = null;

        try {
            watchingRead = new BufferedReader(new FileReader(watchingFile));
            while ((watchingOut = watchingRead.readLine()) != null) {
                //Cleaning title text
                String title = watchingOut.substring(0, watchingOut.indexOf(",", watchingOut.indexOf('"', 1))).replace('"', ' ').trim();
                String remaining = watchingOut.substring(watchingOut.indexOf(",", watchingOut.indexOf('"', 1)) + 1);
                int currentEp = Integer.parseInt(remaining.substring(0, remaining.indexOf(",")));
                int totalEp = Integer.parseInt(remaining.substring(remaining.indexOf(",") + 1, remaining.indexOf(",", remaining.indexOf(",") + 1)));
                Airing airing = Airing.AIRED;
                String comp = remaining.substring(remaining.lastIndexOf(",") + 1).toUpperCase();
                if (comp.equals("AIRING")) {
                    airing = Airing.AIRING;
                }
                if (comp.equals("FUTURE")) {
                    airing = Airing.FUTURE;
                }
                programs.add(new Program(title, currentEp, totalEp, airing));

            }
            watchingRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        programs.sort(new ProgramComparator());
        return programs;
    }

    private static void save(ArrayList<Program> programs, boolean complete) {
        //Save CSV files
        try {
            String c = "watching.csv";
            if (complete) {
                c = "complete.csv";
            }
            PrintWriter pw = new PrintWriter(c, "UTF-8");
            for (int i = 0; i < programs.size(); i++) {
                pw.println('"' + programs.get(i).title + '"' + "," + programs.get(i).currentEp + "," + programs.get(i).totalEp + "," + programs.get(i).getAiring());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static String findProgram(String search, ArrayList<Program> programs) {
        numResults = 0;
        String results = "";
        for (int i = 0; i < programs.size(); i++) {
            if (Pattern.compile(Pattern.quote(search), Pattern.CASE_INSENSITIVE).matcher(programs.get(i).getTitle()).find()) {
                results = results + "\n[" + i + "] " + programs.get(i).getTitle();
                numResults++;
            }
        }
        return results;
    }

}