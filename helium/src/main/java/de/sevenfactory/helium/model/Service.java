package de.sevenfactory.helium.model;

import com.google.gson.annotations.SerializedName;

public class Service {
   @SerializedName("id")
   public String id;

   @SerializedName("url")
   private String mUrl;

   public String getId() {
      return id;
   }

   public String getUrl() {
      return mUrl;
   }
}
