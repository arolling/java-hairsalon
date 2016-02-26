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

  @Test
  public void save_addsIdToClientObjectLocally_true() {
    Stylist monica = new Stylist("Monica Sellers");
    monica.save();
    Client britney = new Client("Britney", "Spears", monica.getId());
    britney.save();
    Client savedClient = Client.all().get(0);
    assertEquals(savedClient.getId(), britney.getId());
  }

  @Test
  public void updaters_updatesNameOfClient_BritneySpears() {
    Stylist sabrina = new Stylist("Sabrina");
    sabrina.save();
    Stylist monica = new Stylist("Monica Sellers");
    monica.save();
    Client britney = new Client("Brittany", "Speeres", sabrina.getId());
    britney.save();
    britney.updateFirst("Britney");
    britney.updateLast("Spears");
    britney.updateStylist(monica.getId());
    Client savedClient = Client.all().get(0);
    assertEquals("Britney", britney.getFirst());
    assertEquals("Britney", savedClient.getFirst());
    assertEquals("Spears", britney.getLast());
    assertEquals("Spears", savedClient.getLast());
    assertEquals(monica.getId(), britney.getStylistId());
    assertEquals(monica.getId(), savedClient.getStylistId());
    assertTrue(savedClient.equals(britney));
  }

  @Test
  public void delete_deletesSpecificClientFromDB_false() {
    Client britney = new Client("Britney", "Spears", 3);
    britney.save();
    Client elizabeth = new Client("Elizabeth", "Taylor", 2);
    elizabeth.save();
    britney.delete();
    assertFalse(Client.all().contains(britney));
    assertTrue(Client.all().contains(elizabeth));
  }

  @Test
  public void find_findsSpecificClientInDatabaseById_true() {
    Client britney = new Client("Britney", "Spears", 3);
    britney.save();
    Client elizabeth = new Client("Elizabeth", "Taylor", 2);
    elizabeth.save();
    assertEquals(Client.find(elizabeth.getId()), elizabeth);
  }

}
