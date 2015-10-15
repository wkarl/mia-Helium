package de.prosiebensat1digital.middleware.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Service {
   @JsonProperty ("id")
   public String id;

   @JsonProperty ("url")
   private String mUrl;

   public String getId() {
      return id;
   }

   public String getUrl() {
      return mUrl;
   }
}
