import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class StylistTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  // UNIT TESTING

  @Test
  public void stylist_instantiatesCorrectly_true() {
    Stylist monica = new Stylist("Monica Sellers");
    assertEquals(true, monica instanceof Stylist);
  }

  @Test
  public void all_returnsEmptyAtFirstFromDatabase_true() {
    assertEquals(0, Stylist.all().size());
  }

  @Test
  public void save_returnsTrueIfNameIsSame_true() {
    Stylist monica = new Stylist("Monica Sellers");
    monica.save();
    assertTrue(Stylist.all().get(0).equals(monica));
  }

  @Test
  public void save_addsIdToStylistObjectLocally_true() {
    Stylist sabrina = new Stylist("Sabrina Childs");
    sabrina.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(savedStylist.getId(), sabrina.getId());
  }

  @Test
  public void update_updatesNameOfStylist_SabrinaChilds() {
    Stylist sabrina = new Stylist("Sabrina");
    sabrina.save();
    sabrina.update("Sabrina Childs");
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals("Sabrina Childs", sabrina.getName());
    assertEquals("Sabrina Childs", savedStylist.getName());
  }

  @Test
  public void delete_deletesSpecificStylistFromDB_false() {
    Stylist monica = new Stylist("Monica Sellers");
    Stylist sabrina = new Stylist("Sabrina Childs");
    monica.save();
    sabrina.save();
    monica.delete();
    assertFalse(Stylist.all().contains(monica));
    assertTrue(Stylist.all().contains(sabrina));
  }

}
