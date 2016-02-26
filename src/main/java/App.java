import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();


      model.put("allStylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, resonse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newName = request.queryParams("new-stylist");
      Stylist newStylist = new Stylist(newName);
      newStylist.save();

      model.put("allStylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Stylist thisStylist = Stylist.find(id);

      model.put("stylist", thisStylist);
      model.put("allStylists", Stylist.all());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylist/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Stylist thisStylist = Stylist.find(id);
      String newFirst = request.queryParams("new-client-first");
      String newLast = request.queryParams("new-client-last");
      Client newClient = new Client(newFirst, newLast, thisStylist.getId());
      newClient.save();
      model.put("stylist", thisStylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Stylist thisStylist = Stylist.find(id);
      thisStylist.delete();
      model.put("stylist", thisStylist);
      model.put("allStylists", Stylist.all());
      model.put("strandedClients", Client.getStrandedClients(id));
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylist/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      ArrayList<Client> fixedClients = new ArrayList<Client>();
      ArrayList<Client> deletedClients = new ArrayList<Client>();
      String[] strandedClients = request.queryParamsValues("clientId");
      String[] newStylists = request.queryParamsValues("reassign-stylist");
      //desperately hoping these come in in the same order
      for (int i = 0; i < strandedClients.length; i++) {
        Client fix = Client.find(Integer.parseInt(strandedClients[i]));
        int stylist = Integer.parseInt(newStylists[i]);
        if (stylist == 0) {
          fix.delete();
          deletedClients.add(fix);
        } else {
          fix.updateStylist(stylist);
          fixedClients.add(fix);
        }
      }

      model.put("fixedClients", fixedClients);
      model.put("deletedClients", deletedClients);
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylist/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Stylist thisStylist = Stylist.find(id);
      thisStylist.update(request.queryParams("editName"));
      model.put("stylist", thisStylist);
      model.put("allStylists", Stylist.all());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/client/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Client thisClient = Client.find(id);

      model.put("client", thisClient);
      model.put("allStylists", Stylist.all());
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/client/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Client thisClient = Client.find(id);
      int newStylist = Integer.parseInt(request.queryParams("edit-stylist"));
      if (newStylist != 0) {
        thisClient.updateStylist(newStylist);
      }
      String newFirst = request.queryParams("edit-client-first");
      if(newFirst.length() != 0){
        thisClient.updateFirst(newFirst);
      }
      String newLast = request.queryParams("edit-client-last");
      if(newLast.length() != 0){
        thisClient.updateLast(newLast);
      }
      model.put("client", thisClient);
      model.put("allStylists", Stylist.all());
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/client/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Client thisClient = Client.find(id);
      thisClient.delete();
      model.put("client", thisClient);
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
