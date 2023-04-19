import java.util.HashSet;
import java.util.Set;

public class Journal extends PublicationLibrary {

    String uniqueIdentifier;
    Set<String> authors = new HashSet<>();
    String paperTitle;
    String journalName;
    String pageRange;
    String volume;
    String issueNumber;
    String month;
    String year;

    /*
    Each publication venue (journals and conferences) have some organization that
    organizes the publication, an area of research that the journal or conference covers, and an
    editor or organizer with their contact information. The organizations then have their own
    contact information and home office.
    */
    void getPublicationVenue(){

    }

    void addJournal(){

    }
}
