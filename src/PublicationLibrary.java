import java.sql.*;
import java.util.*;

public class PublicationLibrary {

    Connection connect = null;
    Statement statement = null;
    ResultSet resultSet = null;

    private Map<String,Set> researchAreas = new HashMap<>();
    int reference_id;
    int publisher_id;

    private Map<String,String> getPublicationsInfo = new HashMap<>();

    /*
    Add a publication to the library. All the publication information is in the Map where the Map
    keys are the type of information and the Map values are the actual information. Expected Map
    keys include authors, title, journal, pages, volume, issue, month, year, conference, location,
    although not all of these keys will appear for each publication.
    The author value consist of a comma-separated list of author full names (no abbreviations).
    Return true if the publication has been added and false if the publication is not added to the
    library.
    */
    void databaseConnector()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Scanner sc = new Scanner(System.in);
            String username = sc.next();
            String password = sc.next();
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
            statement = connect.createStatement();
            statement.execute("use athaker");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    boolean addPublication ( String identifier, Map<String, String> publicationInformation ){
        try{
            databaseConnector();
            if(publicationInformation.containsKey("journal")) {
                String ins_add_publication = "INSERT INTO journal_info " +
                        "(publication_id, authors, title, journal, pages, volume, issue, month, year) " +
                        "VALUES( '"+identifier+"', '" + publicationInformation.get("authors") + "','" + publicationInformation.get("title") + "','"+publicationInformation.get("journal")+"','" + publicationInformation.get("pages") + "','" + publicationInformation.get("volume") + "','" + publicationInformation.get("issue") + "','" + publicationInformation.get("month") + "','" + publicationInformation.get("year") + "')";
                int no_of_rows_changed = statement.executeUpdate(ins_add_publication);
                if(no_of_rows_changed == 1){
                    String ins_publication_type = "INSERT INTO publicationtype"+
                            "(publication_id,publication_type)" +
                            "VALUES('"+identifier+"',journal)";
                    int row_changed = statement.executeUpdate(ins_publication_type);
                }
            } else if (publicationInformation.containsKey("conference")) {
                String ins_add_publication = "INSERT INTO conference_info" +
                        "(publication_id, authors, title, pages, month, year, conference_name, location_name)"+
                        "VALUES('"+identifier+"','"+publicationInformation.get("authors")+"','"+publicationInformation.get("title")+"','"+publicationInformation.get("pages")+"','"+publicationInformation.get("year")+"', '"+publicationInformation.get("conference_name")+"','"+publicationInformation.get("location_name")+"')";
                int no_of_rows_changed = statement.executeUpdate(ins_add_publication);
                if(no_of_rows_changed == 1) {
                    String ins_publication_type = "INSERT INTO publicationtype" +
                            "(publication_id,publication_type)" +
                            "VALUES('" + identifier + "',conference)";
                    int row_changed = statement.executeUpdate(ins_publication_type);
                }
            }else {
                //extend the code for adding books/web-articles(online) to the publication
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
        if(identifier == null || references == null){
            return false;
        }
        if(identifier.isEmpty() || references.isEmpty()){
            return false;
        }
        try {
            databaseConnector();
            String ins_add_references = "INSERT INTO publication_references " +
                    "(reference_id, publication_id, reference_set) " +
                    "VALUES( , '"+identifier+"','"+references+"')";
            int no_of_rows_changed = statement.executeUpdate(ins_add_references);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return true;
    }

    /*
    Add a publication venue, like a journal or a conference to the library. All the venue information
    is in the Map where the Map keys are the type of information and the Map values are the
    actual information. Expected Map keys include publisher, editor, editor_contact, location, and
    conference_year, although not all of these keys will appear for each publication.
    Return true if the venue has been added and false if the venue is not added to the library.
     */
    boolean addVenue (String venueName, Map<String, String> venueInformation, Set<String>
            researchAreas) {
        if(venueName == null || venueInformation == null || researchAreas == null)
            return false;
        if (venueName == "journal" || venueName == "conference") {
            try {
                databaseConnector();
                if (!venueInformation.isEmpty() && !researchAreas.isEmpty() && !venueName.isEmpty()) {
                    if (venueName == "journal") {
                        String ins_venue_info = "INSERT INTO venue_info (venueName, publisher_id, editor_name, editor_contact) " +
                                "VALUES('" +venueName+ "', ,'" + venueInformation.get("editor_name") + "','" + venueInformation.get("editor_contact") + "')";
                        int no_of_rows_changed = statement.executeUpdate(ins_venue_info);
                    } else if (venueName == "conference") {
                        String ins_venue_info = "INSERT INTO venue_info (venueName, publisher_id, editor_name, editor_contact, location, conference_year) " +
                              "VALUES('"+ venueInformation.get("venueName")+"',  ,'" + venueInformation.get("editor_name") + "','"+venueInformation.get("editor_contact")+"', '"+venueInformation.get("location")+"','"+venueInformation.get("conference_year")+"')";
                        int no_of_rows_changed = statement.executeUpdate(ins_venue_info);
                    } else {
                        //think of the other usecases
                    }

                    System.out.println();
                    return true;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    /*
    Add a publisher to the library. All the publisher information is in the Map where the Map keys
    are the type of information and the Map values are the actual information. Expected Map keys
    include contact_name, contact_email, and location.
    Return true if the publisher has been added and false if the publisher is not added to the
    library.
     */
    /*
    To do for later
    1. Ensure the publisher_id autoincrements
    2. identifier - as a foreign key to publication table
     */
    boolean addPublisher ( String identifier, Map<String, String> publisherInformation )  {
        if(identifier == null || publisherInformation == null)
            return false;
        try {
            databaseConnector();
            if (!publisherInformation.isEmpty() && !identifier.isEmpty()) {
                String ins_publisher_info = "INSERT INTO publisher_info (publisher_id, contact_name, contact_email, location, identifier ) " +
                        "VALUES ('"+publisher_id+"', '"+publisherInformation.get("contact_name")+"','"+publisherInformation.get("contact_email")+"','"+publisherInformation.get("location")+"','"+identifier+"')";
                int no_of_rows_changed = statement.executeUpdate(ins_publisher_info);
                System.out.println(no_of_rows_changed);
                System.out.println();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*
    Add a research area to the library. The research area may be a subset of zero or more other
    research areas, as provided by the parentArea set.
    Return true if the research area has been added and false if the area is not added to the library.
     */
    /*
    1. ensure the elements added in the database are not duplicated.
    2. identifier as a foreign key to publication table
     */
    boolean addArea ( String researchArea, Set<String> parentArea )  {
        try {
            databaseConnector();
            if (researchArea == null || parentArea == null)
                return false;

            if (!researchArea.isEmpty() && !parentArea.isEmpty()) {
                String ins_researchArea = "INSERT INTO research_areas (researchArea, parentArea) VALUES ('"+researchArea +"', '"+parentArea+"')";
                int no_of_rows_changed = statement.executeUpdate(ins_researchArea);
                System.out.println(no_of_rows_changed);
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /*
    Return a map of all the information the library currently stores on a specific publication, as
    identified by the publication key. The articles that this paper references will be returned with a
    Map key of “references” and a comma separated string of all the publication identifiers cited by
    the article.
     */
    Map<String, String> getPublications ( String key ) throws SQLException {

        if(key == null || key.isEmpty()){
            return null;
        }

        databaseConnector();

        resultSet = statement.executeQuery("select * from journal_info where publication_id = '"+key+"'");
        while(resultSet.next()){
            getPublicationsInfo.put("authors",resultSet.getString("authors"));
            getPublicationsInfo.put("title",resultSet.getString("title"));
            getPublicationsInfo.put("journal",resultSet.getString("journal"));
            getPublicationsInfo.put("pages",resultSet.getString("pages"));
            getPublicationsInfo.put("volume",resultSet.getString("volume"));
            getPublicationsInfo.put("issue",resultSet.getString("issue"));
            getPublicationsInfo.put("month",resultSet.getString("month"));
            getPublicationsInfo.put("year",resultSet.getString("year"));
        }
        ResultSet resultSet1 = statement.executeQuery("select * from conference_info where publication_id = '"+key+"'");
        while(resultSet1.next()){
            getPublicationsInfo.put("authors",resultSet1.getString("authors"));
            getPublicationsInfo.put("title",resultSet1.getString("title"));
            getPublicationsInfo.put("pages",resultSet1.getString("pages"));
            getPublicationsInfo.put("year",resultSet1.getString("year"));
            getPublicationsInfo.put("conference_name",resultSet1.getString("conference_name"));
            getPublicationsInfo.put("location_name",resultSet1.getString("location_name"));
        }

        ResultSet resultSet2 = statement.executeQuery("select * from publication_references where publication_id = '"+key+"'");

        while(resultSet2.next()){
            getPublicationsInfo.put("reference_set",resultSet2.getString("reference_set"));
        }

        return null;
    }
    /*
    Report how many publications directly cited the given author in their publications. If one
    publication cites two different papers of the author then count each citation separately.
    The author’s name will be the full name of the individual.
     */
    int authorCitations ( String author )
    {
        if(author == null || author.isEmpty()){
            return 0;
        }
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
        if(area == null){
            return null;
        }
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
        if(author == null || author.isEmpty()){
            return null;
        }
        return null;
    }
    /*
    Report the research areas in which the given author has published at least “threshold” papers.
    When a research area is part of a larger research area, include both the specific research area
    and the broader research areas in the returned list.
     */
    Set<String> authorResearchAreas ( String author, int threshold )
    {
        if(author == null || author.isEmpty()){
            return null;
        }
        return null;
    }
}