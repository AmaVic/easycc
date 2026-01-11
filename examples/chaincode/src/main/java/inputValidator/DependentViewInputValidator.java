package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class DependentViewInputValidator {
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

    if(!jsonObject.containsKey("incrementId_Increment_DependentView"))
       return false;

    if(jsonObject.get("incrementId_Increment_DependentView") == null)
       return false;

    if(!(jsonObject.get("incrementId_Increment_DependentView") instanceof String))
       return false;

    if(!jsonObject.containsKey("decrementId_Decrement_DependentView"))
       return false;

    if(jsonObject.get("decrementId_Decrement_DependentView") == null)
       return false;

    if(!(jsonObject.get("decrementId_Decrement_DependentView") instanceof String))
       return false;

    return true;
  }

}