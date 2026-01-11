package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class OwnershipInputValidator {
  private static final Genson genson = new Genson();

  @SuppressWarnings("unchecked")
  public static boolean isValidForCreation(String json) {
    HashMap<String, Object> jsonObject = (HashMap<String, Object>)genson.deserialize(json, Map.class);

    if(!jsonObject.containsKey("Name"))
       return false;

    if(jsonObject.get("Name") == null)
       return false;

    if(!(jsonObject.get("Name") instanceof String))
       return false;

    if(!jsonObject.containsKey("economicResourceId_EconomicResource_Ownership"))
       return false;

    if(jsonObject.get("economicResourceId_EconomicResource_Ownership") == null)
       return false;

    if(!(jsonObject.get("economicResourceId_EconomicResource_Ownership") instanceof String))
       return false;

    if(!jsonObject.containsKey("economicAgentId_EconomicAgent_Ownership"))
       return false;

    if(jsonObject.get("economicAgentId_EconomicAgent_Ownership") == null)
       return false;

    if(!(jsonObject.get("economicAgentId_EconomicAgent_Ownership") instanceof String))
       return false;

    return true;
  }

}