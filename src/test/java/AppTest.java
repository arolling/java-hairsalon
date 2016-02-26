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
}
