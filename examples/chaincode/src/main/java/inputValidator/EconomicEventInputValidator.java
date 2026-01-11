package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class EconomicEventInputValidator {
  private static final Genson genson = new Genson();

  @SuppressWarnings("unchecked")
  public static boolean isValidForCreation(String json) {
    HashMap<String, Object> jsonObject = (HashMap<String, Object>)genson.deserialize(json, Map.class);

    if(!jsonObject.containsKey("TimeStamp"))
       return false;

    if(jsonObject.get("TimeStamp") == null)
       return false;

    if(!(jsonObject.get("TimeStamp") instanceof LocalDateTime))
       return false;

    if(!jsonObject.containsKey("CountDependentViews"))
       return false;

    if(jsonObject.get("CountDependentViews") == null)
       return false;

    if(!(jsonObject.get("CountDependentViews") instanceof Integer))
       return false;

    if(!jsonObject.containsKey("Name"))
       return false;

    if(jsonObject.get("Name") == null)
       return false;

    if(!(jsonObject.get("Name") instanceof String))
       return false;

    return true;
  }

}