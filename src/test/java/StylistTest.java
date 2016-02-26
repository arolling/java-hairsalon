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


}
