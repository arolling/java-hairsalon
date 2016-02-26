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

}
