package org.thekiddos.manager.payroll.transactions;

import lombok.SneakyThrows;
import org.thekiddos.manager.Util;
import org.thekiddos.manager.payroll.models.Employee;
import org.thekiddos.manager.transactions.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PrintPayCheckTransaction implements Transaction {
    private String wordPath = "C:\\Program Files (x86)\\Microsoft Office\\root\\Office16\\";
    private Employee employee;
    private double amount;
    private String checkPath;
    private LocalDate dateOfPayment;
    private static int printJob = 1;
    private static final String PAYCHECK_TEMPLATE = "templates/check.rtf";

    public PrintPayCheckTransaction( Employee employee, double amount, LocalDate dateOfPayment ) {
        this.employee = employee;
        this.amount = amount;
        this.dateOfPayment = dateOfPayment;
    }

    public void setWordPath( String wordPath ) {
        this.wordPath = wordPath;
    }

    @SneakyThrows
    @Override
    public void execute() {
        if ( amount == 0.0 )
            return;
        fillPayCheck();
        printPayCheck();
    }

    private void fillPayCheck() throws Exception {
        String contents = readFileContents( new File( Util.getResource( PAYCHECK_TEMPLATE ).getFile() ) );
        contents = replaceCheckPlaceholders( contents );
        savePayCheckToFile( contents );
    }

    /*
    // TODO This function must be removed after testing that the class works without it
    private File getFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }*/

    private String readFileContents( File file ) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader( file ) );

        StringBuilder contents = new StringBuilder();
        String line = reader.readLine();
        while ( line != null ) {
            contents.append( line).append( "\n" );
            line = reader.readLine();
        }

        reader.close();
        return contents.toString();
    }

    private String replaceCheckPlaceholders( String payCheckConents ) {
        payCheckConents = payCheckConents.replace( "$RESTAURANT_NAME$", "MHA" );
        payCheckConents = payCheckConents.replace( "$PAYMENT_DATE$", dateOfPayment.toString() );
        payCheckConents = payCheckConents.replace( "$ID$", employee.getId().toString() );
        payCheckConents = payCheckConents.replace( "$NAME$", employee.getName() );
        payCheckConents = payCheckConents.replace( "$TYPE$", employee.getPaymentClassification().getType() );
        payCheckConents = payCheckConents.replace( "$SALARY$", employee.getPaymentClassification().getBaseSalary() );
        payCheckConents = payCheckConents.replace( "$PAYMENT$", String.valueOf( amount ) );
        return payCheckConents;
    }

    private void savePayCheckToFile( String contents ) throws FileNotFoundException {
        File check = new File( "check" + printJob++ + ".rtf" );
        PrintWriter out = new PrintWriter( check );
        out.print( contents );
        checkPath = check.getAbsolutePath();
        out.close();
    }

    private void printPayCheck() throws IOException {
        Process p = Runtime.getRuntime().exec( "cmd" );
        PrintStream out = new PrintStream( p.getOutputStream() );

        List<String> commands = Arrays.asList( "cd /d " + wordPath,
                "WINWORD.EXE " + checkPath + " /mFilePrintDefault /mFileExit /q /n",
                "exit" );

        for ( String command : commands ) {
            out.println(command);
        }
        out.flush();
    }
}
