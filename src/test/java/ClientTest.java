import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ClientTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();

  // UNIT TESTING
  @Test
  public void client_instantiatesCorrectly_true() {
    Stylist monica = new Stylist("Monica Sellers");
    monica.save();
    Client britney = new Client("Britney", "Spears", monica.getId());
    assertEquals(true, britney instanceof Client);
  }

  @Test
  public void all_returnsEmptyAtFirstFromDatabase_true() {
    assertEquals(0, Client.all().size());
  }

  @Test
  public void save_returnsTrueIfNameAndIdAreSame_true() {
    Client britney = new Client("Britney", "Spears", 3);
    britney.save();
    assertTrue(Client.all().get(0).equals(britney));
  }


}
