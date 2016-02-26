import org.sql2o.*;
import java.util.List;

public class Client {
  private int id;
  private String first_name;
  private String last_name;
  private int stylist_id;

  public Client(String first, String last, int stylist) {
    first_name = first;
    last_name = last;
    stylist_id = stylist;
  }

  public String getFirst() {
    return first_name;
  }

  public String getLast() {
    return last_name;
  }

  public int getId() {
    return id;
  }

  public int getStylistId() {
    return stylist_id;
  }

  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getFirst().equals(newClient.getFirst()) &&
        this.getLast().equals(newClient.getLast()) &&
        this.getStylistId() == newClient.getStylistId() &&
        this.getId() == newClient.getId();
    }
  }
  // DATABASE INTERACTION below
  public static List<Client> all() {
    String sql = "SELECT * FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Client.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO clients (first_name, last_name, stylist_id) VALUES (:first_name, :last_name, :stylist_id)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", first_name)
        .addParameter("last_name", last_name)
        .addParameter("stylist_id", stylist_id)
        .executeUpdate()
        .getKey();
    }
  }

  public void updateFirst(String newFirst) {
    this.first_name = newFirst;
    String sql = "UPDATE clients SET first_name = :newFirst WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("newFirst", newFirst)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateLast(String newLast) {
    this.last_name = newLast;
    String sql = "UPDATE clients SET last_name = :newLast WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("newLast", newLast)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateStylist(int newStylist) {
    this.stylist_id = newStylist;
    String sql = "UPDATE clients SET stylist_id = :newStylist WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("newStylist", newStylist)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
