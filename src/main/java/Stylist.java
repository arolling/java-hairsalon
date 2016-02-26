import org.sql2o.*;
import java.util.List;

public class Stylist {
  private int id;
  private String stylist_name;

  public Stylist(String name) {
    stylist_name = name;
  }

  // Database interaction below
  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Stylist.class);
    }
  }
}
