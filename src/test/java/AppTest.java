import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  //Integration testing
  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Hair Salon");
    assertThat(pageSource()).contains("No Current Stylists");
  }

  @Test
  public void addStylist() {
    goTo("http://localhost:4567/");
    fill("#new-stylist").with("Marjorie Strong");
    submit("#add-stylist");
    assertThat(pageSource()).contains("Marjorie");
    assertThat(pageSource()).doesNotContain("No Current Stylists");
  }

  @Test
  public void addClientToStylist() {
    Stylist monica = new Stylist("Monica Sellers");
    Stylist sabrina = new Stylist("Sabrina Childs");
    monica.save();
    sabrina.save();
    goTo("http://localhost:4567/");
    click("a", withText("Monica Sellers"));
    fill("#new-client-first").with("Britney");
    fill("#new-client-last").with("Spears");
    submit("#add-client");
    assertThat(pageSource()).contains("Britney Spears");
  }
  @Test
  public void deleteStylist() {
    Stylist monica = new Stylist("Monica Sellers");
    Stylist sabrina = new Stylist("Sabrina Childs");
    monica.save();
    sabrina.save();
    Client britney = new Client("Britney", "Spears", monica.getId());
    britney.save();
    goTo("http://localhost:4567/stylist/" + monica.getId());
    click("a", withText("Delete"));

    assertThat(pageSource()).doesNotContain("Monica Sellers");
    assertThat(pageSource()).contains("Sabrina");
  }

  @Test
  public void editStylist() {
    Stylist monica = new Stylist("Monica Sellers");
    Stylist sabrina = new Stylist("Sabrina Children");
    monica.save();
    sabrina.save();
    goTo("http://localhost:4567/stylist/" + sabrina.getId());
    fill("#editName").with("Sabrina Childs");
    submit("#edit");
    assertThat(pageSource()).contains("Sabrina Childs");
  }

  @Test
  public void editClientStylist() {
    Stylist monica = new Stylist("Monica Sellers");
    Stylist sabrina = new Stylist("Sabrina Childs");
    monica.save();
    sabrina.save();
    Client britney = new Client("Britney", "Spears", monica.getId());
    britney.save();
    Client elizabeth = new Client("Elizabeth", "Taylor", sabrina.getId());
    elizabeth.save();
    goTo("http://localhost:4567/stylist/" + sabrina.getId());
    click("a", withText("Elizabeth Taylor"));
    click("option", withText("Monica Sellers"));
    submit("#editClient");
    assertThat(pageSource()).contains("Client of Monica Sellers");
    assertThat(pageSource()).contains("Elizabeth Taylor");
  }

  @Test
  public void editClientName() {
    Stylist monica = new Stylist("Monica Sellers");
    monica.save();
    Client britney = new Client("Brittany", "Speeres", monica.getId());
    britney.save();
    goTo("http://localhost:4567/client/" + britney.getId());
    fill("#edit-client-first").with("Britney");
    fill("#edit-client-last").with("Spears");
    submit("#editClient");
    assertThat(pageSource()).contains("Britney Spears");
  }

  @Test
  public void deleteClient() {
    Stylist monica = new Stylist("Monica Sellers");
    monica.save();
    Client britney = new Client("Britney", "Spears", monica.getId());
    britney.save();
    goTo("http://localhost:4567/client/" + britney.getId());
    click("a", withText("Delete"));
    click("a", withText("Return to Monica Sellers"));
    assertThat(pageSource()).doesNotContain("Britney Spears");
  }
}
