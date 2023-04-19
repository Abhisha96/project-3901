import java.util.Map;
import java.util.Set;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Properties;

public class PublicationLibrary {

    Connection connect = null;
    Statement statement = null;
    ResultSet resultSet = null;
    /*
    Add a publication to the library. All the publication information is in the Map where the Map
    keys are the type of information and the Map values are the actual information. Expected Map
    keys include authors, title, journal, pages, volume, issue, month, year, conference, location,
    although not all of these keys will appear for each publication.
    The author value consist of a comma-separated list of author full names (no abbreviations).
    Return true if the publication has been added and false if the publication is not added to the
    library.
    */
    boolean addPublication ( String identifier, Map<String, String> publicationInformation ){
        return true;
    }
    /*
    For the publication whose publication identifier is “identifier”, record that that paper references
    all of the publications whose publication identifiers are in “references”.
    Since the library is a growing entity, we may call addReferences multiple times for one research
    paper as its references are added to the library and get publication identifiers. When called
    repeatedly, later calls may or may not include previously-reported references.
    Return true if the references have been added and false if the references are not added to the
    library.
     */
    boolean addReferences ( String identifier, Set<String > references ){
     return true;
    }

    /*
    Add a publication venue, like a journal or a conference to the library. All the venue information
    is in the Map where the Map keys are the type of information and the Map values are the
    actual information. Expected Map keys include publisher, editor, editor_contact, location, and
    conference_year, although not all of these keys will appear for each publication.
    Return true if the venue has been added and false if the venue is not added to the library.
     */
    boolean addVenue ( String venueName, Map<String, String> venueInformation, Set<String>
            researchAreas ){
        return true;
    }
    /*
    Add a publisher to the library. All the publisher information is in the Map where the Map keys
    are the type of information and the Map values are the actual information. Expected Map keys
    include contact_name, contact_email, and location.
    Return true if the publisher has been added and false if the publisher is not added to the
    library.
     */
    boolean addPublisher ( String identifier, Map<String, String> publisherInformation )
    {
        return true;
    }

    /*
    Add a research area to the library. The research area may be a subset of zero or more other
    research areas, as provided by the parentArea set.
    Return true if the research area has been added and false if the area is not added to the library.
     */
    boolean addArea ( String researchArea, Set<String> parentArea )
    {
        return true;
    }
    /*
    Return a map of all the information the library currently stores on a specific publication, as
    identified by the publication key. The articles that this paper references will be returned with a
    Map ke  y of “references” and a comma separated string of all the publication identifiers cited by
    the article.
     */
    Map<String, String> getPublications ( String key )
    {
        return null;
    }
    /*
    Report how many publications directly cited the given author in their publications. If one
    publication cites two different papers of the author then count each citation separately.
    The author’s name will be the full name of the individual.
     */
    int authorCitations ( String author )
    {
        return 0;
    }

    /*
    A seminal paper is one that has had a huge influence in a research area. In the case of this
    method, a seminal paper is one that has few references of its own in the current research area,
    but has many other papers in the research area referencing it.
    Report the publication identifiers of the seminal research papers in the given area. The papers
    you return must not cite more than paperCitation papers in the given area and must have at
    least otherCitations papers citing it directly.
     */
    Set<String> seminalPapers ( String area, int paperCitation, int otherCitations ){
        return null;
    }

    /*
    Report the full names of all people whose author distance to the given author is at most the
    “distance” parameter value. These people represent people who the given author might be
    able to easily reach out to for collaboration opportunities, like investigating a new research
    problem or writing a research grant.
    The author distance is the length of the shortest chain that links one author to another through
    co-authorship on papers. Suppose that we have the following sets of authors on 5 papers: {A,
    B, C}, {B, D}, {B, E}, {C, F}, {F, E}. So, A published a paper with B and C. Then the author distance
    of A with themself is 0, A’s author distance to C is 1 (co-authored a paper), and A’s author
    distance to E is 2 (from A to B, to E, which is shorter than A to C to F to E).
    This method is a generalization of the Erdős number1 in mathematics or the Bacon number2 in
    Hollywood movies.
     */
    Set<String> collaborators( String author, int distance )
    {
        return null;
    }
    /*
    Report the research areas in which the given author has published at least “threshold” papers.
    When a research area is part of a larger research area, include both the specific research area
    and the broader research areas in the returned list.
     */
    Set<String> authorResearchAreas ( String author, int threshold )
    {
        return null;
    }
}