import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class main {
    public static void main(String[] args) throws SQLException {
        /*
        You will also write a main() method in a class of your choice that will convert citations in a
        paper into actual IEEE references.

        Your main method will accept the name of an input file and then of an output file.
        The input file is a text file that contains the text of a research paper. In that file, you can find
        citations in the form of either \cite{aaa} or \cite{aaa, bbb, ccc} that indicate that one or multiple
        articles are being cited at the given location and whose article keys are aaa, bbb, and ccc.
        You will transform these citations into IEEE references. So, \cite{aaa} will become [1] in the
        output text (assuming that paper aaa is the first item in the reference list) and you will append
        text to the whole paper with [1] as having the IEEE formatted reference entry for paper aaa. In
        making that IEEE reference entry, you can assume that all but the last string in an author’s
        name is a first name to be abbreviated and that you just pick the first letter of all the first
        names (plus the full last name) in the IEEE author list.
        An entry of \cite{aaa, bbb, ccc} is then output as [1, 2, 3], assuming that article aaa is reference
        [1], article bbb is reference [2], and article ccc is reference [3].
        Output the resulting article, with the appended references to the given output file.
         */

        PublicationLibrary library = new PublicationLibrary();
        // alphanumeric string
        String identifier = "ab570421";

        // Call for research Area
        Set<String> parentArea = new HashSet<>();
        parentArea.add("A");
        parentArea.add("B");
        parentArea.add("C");
        library.addArea("Computational Geometry",parentArea);

        // call for publisherInformation
        Map<String,String> publisherInformation = new HashMap<>();
        publisherInformation.put("contact_name","Abhisha");
        publisherInformation.put("contact_email","ab@gmail.com");
        publisherInformation.put("location","NewYork");
        library.addPublisher(identifier,publisherInformation);
        //library.addPublisher();


    }
}
