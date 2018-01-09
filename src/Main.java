 import java.sql.*;

    /**
     * Dit is een voorbeeld Java toepassing waarin je verbinding maakt met een SQLServer database.
     */
    public class Main {

        public static void main(String[] args) {
            String test = "di";

            // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien deze voor jou anders is.
            String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=TrioNetnix;integratedSecurity=true;";

            // Connection beheert informatie over de connectie met de database.
            Connection con = null;
            // Statement zorgt dat we een SQL query kunnen uitvoeren.
            Statement stmt = null;

            // ResultSet is de tabel die we van de database terugkrijgen.
            // We kunnen door de rows heen stappen en iedere kolom lezen.
            ResultSet rs = null;


            try {
                // 'Importeer' de driver die je gedownload hebt.
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // Maak de verbinding met de database.
                con = DriverManager.getConnection(connectionUrl);

                stmt = con.createStatement();
                rs =
                        stmt.executeQuery("SELECT Bekeken.Profielnaam, Aflevering.Serie AS Serie, Aflevering.AfleveringId AS Aflevering, Film.Titel AS Film FROM Bekeken\n" +
                                "JOIN Profiel\n" +
                                "ON Bekeken.Profielnaam=Profiel.Profielnaam\n" +
                                "JOIN Account\n" +
                                "ON Profiel.Abonneenummer=Account.Abonneenummer\n" +
                                "JOIN AllId\n" +
                                "ON AllId.CatalogId=Bekeken.Gezien\n" +
                                "LEFT JOIN Aflevering\n" +
                                "ON AllId.CatalogId = Aflevering.AfleveringId\n" +
                                "LEFT JOIN Film\n" +
                                "ON Film.FilmId= AllId.CatalogId\n" +
                                "WHERE (Bekeken.Gezien=Aflevering.AfleveringId OR Bekeken.Gezien=Film.FilmId) AND Profiel.Profielnaam LIKE '%" + test + "%' AND Account.Abonneenummer = '5285824';\n");



                // Voer de query uit op de database.

                System.out.print(String.format("| %-32s | %-32s |%-32s | %-31s  |\n", " ", " ", " ", " ").replace(" ", "-"));
                System.out.format("| %-32s | %-32s |%-32s | %-32s | \n", "Profielnaam", "Serie", "AfleveringId", "Titel");
                System.out.print(String.format("| %-32s | %-32s |%-32s | %-31s  |\n", " ", " ", " ", " ").replace(" ", "-"));

                // Als de resultset waarden bevat dan lopen we hier door deze waarden en printen ze.
                while (rs.next()) {
                    // Vraag per row de kolommen in die row op.
                    String Profielnaam = rs.getString("Profielnaam");
                    String Serie = rs.getString("Serie");
                    String AfleveringId = rs.getString("Aflevering");
                    String Titel = rs.getString("Film");

                    // Print de kolomwaarden.
                    // System.out.println(ISBN + " " + title + " " + author);

                    // Met 'format' kun je de string die je print het juiste formaat geven, als je dat wilt.
                    // %d = decimal, %s = string, %-32s = string, links uitgelijnd, 32 characters breed.
                    System.out.format("| %-32s | %-32s |%-32s | %-32s | \n", Profielnaam, Serie, AfleveringId, Titel);
                }
                System.out.println(String.format("| %-32s | %-32s |%-32s | %-31s  |\n", " ", " ", " ", " ").replace(" ", "-"));

            }

            // Handle any errors that may have occurred.
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (rs != null) try { rs.close(); } catch(Exception e) {}
                if (stmt != null) try { stmt.close(); } catch(Exception e) {}
                if (con != null) try { con.close(); } catch(Exception e) {}
            }
        }
    }