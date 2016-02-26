import org.sql2o.*;
import java.util.List;

public class Stylist {
  private int id;
  private String stylist_name;

  public Stylist(String name) {
    stylist_name = name;
  }

  public String getName() {
    return stylist_name;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) &&
        this.getId() == newStylist.getId();
    }

  }

  // Database interaction below
  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO stylists (stylist_name) VALUES (:name)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", stylist_name)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String newName) {
    this.stylist_name = newName;
    String sql = "UPDATE stylists SET stylist_name = :newName WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("newName", stylist_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM stylists WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static Stylist find(int id){
    String sql = "SELECT * FROM stylists WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
    }
  }

}
