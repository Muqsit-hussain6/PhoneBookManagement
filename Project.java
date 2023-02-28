import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Queue;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Project {



    public void insertContact(String addContact, String contactfile) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(contactfile, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.println(addContact);
            sort();
            System.out.println("Data Successfully appended into file");
            pw.flush();
        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {
                // can't do anything
            }
        }
    }




    public void editContact() throws Exception {

        String filePath = "contact.txt";
        //Instantiating the Scanner class to read the file
        Scanner scc = new Scanner(new File(filePath));
        //instantiating the StringBuffer class
        StringBuffer buffer = new StringBuffer();
        //Reading lines of the file and appending them to StringBuffer
        while (scc.hasNextLine()) {
            buffer.append(scc.nextLine() + System.lineSeparator());
        }
        String fileContents = buffer.toString();
        //System.out.println("Contents of the file: " + fileContents);
        //closing the Scanner object
        sort();
        print();
        scc.close();
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Enter a contact that you want to edit: ");
        System.out.print("\nEnter First name :");
        String fname = sc2.next();
        System.out.print("Enter Last name :");
        String lname = sc2.next();
        System.out.print("Enter number :");
        String number = sc2.next();
        String oldLine = "";
        if (lname.charAt(0) != '-')
            oldLine = fname + " " + lname + " " + number;
        else
            oldLine = fname + " " + number;


        if (searchFile(oldLine) == true) {

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a new contact: ");
            System.out.print("\nEnter First name :");
            String fname1 = sc.next();
            System.out.print("Enter Last name :");
            String lname1 = sc.next();
            System.out.print("Enter number :");
            String number1 = sc.next();
            String newLine = "";
            if (lname1.charAt(0) != '-')
                newLine = fname1 + " " + lname1 + " " + number1;
            else
                newLine = fname1 + " " + number1;

            //Replacing the old line with new line
            fileContents = fileContents.replaceAll(oldLine + " ", newLine + " ");
            //instantiating the FileWriter class
            System.out.println("DONE.");
            FileWriter writer = new FileWriter(filePath);
            System.out.println("");
            //System.out.println("new data: " + fileContents);
            writer.append(fileContents);
            writer.flush();
            sort();
            print();
        }
    }



    public void callLog(Queue < String > queue) {
        try {
            //if(){
            while (!queue.isEmpty()) {
                insertContact(queue.remove(), "contact2.txt");
            }
            try {
                System.out.println("\n ==== C A L L L O G ====\n\n");
                String strpath = "contact2.txt";
                FileReader fr = new FileReader(strpath);
                BufferedReader brr = new BufferedReader(fr);
                List < String > lines = new ArrayList < String > ();
                String curLine;
                while ((curLine = brr.readLine()) != null) {
                    lines.add(curLine);
                }
                Collections.reverse(lines);
                for (String line: lines) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }


        } catch (Exception e) {}
    }




    public void deleteContact() throws Exception {

        String filePath = "contact.txt";
        //Instantiating the Scanner class to read the file
        Scanner scc = new Scanner(new File(filePath));
        //instantiating the StringBuffer class
        StringBuffer buffer = new StringBuffer();
        //Reading lines of the file and appending them to StringBuffer
        while (scc.hasNextLine()) {
            buffer.append(scc.nextLine() + System.lineSeparator());
        }
        String fileContents = buffer.toString();
        //System.out.println("Contents of the file: " + fileContents);
        sort();
        print();
        //closing the Scanner object
        scc.close();
        String oldLine = "";
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter First name :");
        String fname = sc.next();
        System.out.print("Enter Last name :");
        String lname = sc.next();
        System.out.print("Enter number :");
        String number = sc.next();
        if (lname.charAt(0) == '-')
            oldLine = fname + " " + number;
        else
            oldLine = fname + " " + lname + " " + number;
        if (searchFile(oldLine) == true) {

            fileContents = fileContents.replaceAll(oldLine + " ", "");
            //instantiating the FileWriter class


            System.out.println("Deleted.");
            FileWriter writer = new FileWriter(filePath);
            System.out.println("");
            //System.out.println("new data: "+fileContents);
            writer.append(fileContents);
            writer.flush();
            removeBlankLines("contact.txt", "temp.txt");
            removeBlankLines("temp.txt", "contact.txt");
            sort();
            print();



        } else
            System.out.println("Contact not found.");

    }

    public void print() {

        Scanner scan = new Scanner(System.in);



        String line = null;
        try {
            /* FileReader reads text files in the default encoding */
            FileReader fileReader = new FileReader("sorted_Contact.txt");

            /* always wrap the FileReader in BufferedReader */
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            /* always close the file after use */
            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("Error reading file named '" + "contact.txt" + "'");
        }
    }

    public static void removeBlankLines(String filepath1, String filepath2)

    {
        Scanner file;
        PrintWriter writer2;

        try {

            file = new Scanner(new File(filepath1));
            writer2 = new PrintWriter(filepath2);

            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.isEmpty()) {
                    writer2.write(line);
                    writer2.write("\n");
                }
            }

            file.close();
            writer2.close();


        } catch (FileNotFoundException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }

    }




    public boolean searchFile(String searchStr) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("contact.txt"));
        boolean result = false;
        while (scan.hasNext()) {
            String line = scan.nextLine().toString();

            if (line.contains((searchStr + " "))) {
                result = true;
                break;
            } else
                result = false;


        }
        return result;
    }

    public void displayContacts() {

        try {
            String filePath = "sorted_Contact.txt";
            //Instantiating the Scanner class to read the file
            Scanner scc2 = new Scanner(new File(filePath));
            //instantiating the StringBuffer class
            StringBuffer buffer = new StringBuffer();
            //Reading lines of the file and appending them to StringBuffer
            while (scc2.hasNextLine()) {
                buffer.append(scc2.nextLine() + System.lineSeparator());
            }
            String fileContents = buffer.toString();
            System.out.println("Contents of the file: \n" + fileContents);
            scc2.close();
        } catch (Exception e) {}
    }
    public void sort() {
        BufferedReader reader = null;

        BufferedWriter writer = null;

        //Create an ArrayList object to hold the lines of input file

        ArrayList < String > lines = new ArrayList < String > ();

        try {
            //Creating BufferedReader object to read the input file

            reader = new BufferedReader(new FileReader("contact.txt"));

            //Reading all the lines of input file one by one and adding them into ArrayList

            String currentLine = reader.readLine();

            while (currentLine != null) {
                lines.add(currentLine);

                currentLine = reader.readLine();
            }

            //Sorting the ArrayList

            Collections.sort(lines);

            //Creating BufferedWriter object to write into output file

            writer = new BufferedWriter(new FileWriter("Sorted_Contact.txt"));

            //Writing sorted lines into output file

            for (String line: lines) {
                writer.write(line);

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Closing the resources

            try {
                if (reader != null) {
                    reader.close();
                }

                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String args[]) throws IOException {

        Project p = new Project();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        char i;
        do {
            System.out.println("Enter 1 to Insert a Contact ");
            System.out.println("Enter 2 to Edit a Contact");
            System.out.println("Enter 3 to Show Contacts");
            System.out.println("Enter 4 to Delete a Contact");
            System.out.println("Enter 5 to Make a Phone Call");
            System.out.println("Enter 6 to Exit");
            System.out.println("Please Select any Option(1-6) ");

            choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter a contact? (n/y)");


                try {
                    Scanner sc2 = new Scanner(System.in);
                    System.out.print("\nEnter First name : ");
                    String fname = sc2.next();
                    System.out.print("\nEnter Last name : ");
                    String lname = sc2.next();
                    System.out.print("\nEnter number : ");
                    String number = sc2.next();
                    String input = "";
                    if (lname.charAt(0) != '-')
                        input = fname + " " + lname + " " + number;
                    else
                        input = fname + " " + number;
                    p.insertContact(input + " ", "contact.txt");

                } catch (IOException io) {
                    System.out.println(io);
                }
            }

            if (choice == 2) {

                try {
                    p.editContact();
                } catch (Exception io) {

                }
            }

            if (choice == 4) {
                try {
                    p.deleteContact();
                } catch (Exception io) {

                }

            }
            if (choice == 3) {
                p.displayContacts();
            }
            if (choice == 5) {
                //Method Of CallLog.

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                Scanner sc2 = new Scanner(System.in);
                Queue < String > queue = new LinkedList < String > ();
                String number;
                System.out.print("\nYou want to dial a random number or saved contact?(r/s)");
                char input = sc.next().charAt(0);
                number = "";
                if (input == 'r') {
                    System.out.print("\nEnter a number: ");
                    number = sc2.nextLine();
                    queue.add(number + "  " + dtf.format(now));
                    System.out.println("Calling " + number);

                } else if (input == 's') {
                    p.displayContacts();
                    System.out.print("\nEnter a name: ");
                    number = sc2.nextLine();
                    if (p.searchFile(number)) {
                        queue.add(number + "  " + dtf.format(now));
                        System.out.println("Calling " + number);
                    } else
                        System.out.println("Contact not found.");
                }

                p.callLog(queue);
            }
            if (choice == 6) {
                System.out.println("Program Ended ");
                break;
            }
			p.sort();
            System.out.println("\nDo You Want To Continue?(y/n)");
            i = sc.next().charAt(0);
        } while (i == 'y' || i == 'Y');



    }
}