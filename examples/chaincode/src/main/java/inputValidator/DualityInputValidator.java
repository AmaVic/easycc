package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class DualityInputValidator {
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

    if(!jsonObject.containsKey("economicEventId_Settle_Duality"))
       return false;

    if(jsonObject.get("economicEventId_Settle_Duality") == null)
       return false;

    if(!(jsonObject.get("economicEventId_Settle_Duality") instanceof String))
       return false;

    if(!jsonObject.containsKey("economicEventId_Claim_Duality"))
       return false;

    if(jsonObject.get("economicEventId_Claim_Duality") == null)
       return false;

    if(!(jsonObject.get("economicEventId_Claim_Duality") instanceof String))
       return false;

    return true;
  }

}