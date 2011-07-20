package no.guttab.observable.example;

import java.util.ArrayList;
import java.util.List;

import no.guttab.observable.core.annotation.Observable;
import no.guttab.observable.core.annotation.ObservableCollection;

@Observable
public class Model {

   @ObservableCollection
   private List<Client> clients = new ArrayList<Client>();

   private String name;


   public List<Client> getClients() {
      return clients;
   }

   public void setClients(List<Client> clients) {
      this.clients = clients;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
