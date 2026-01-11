package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class IncrementInputValidator {
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

    if(!jsonObject.containsKey("EarnedValue"))
       return false;

    if(jsonObject.get("EarnedValue") == null)
       return false;

    if(!(jsonObject.get("EarnedValue") instanceof Float))
       return false;

    if(!jsonObject.containsKey("stockFlowId_StockFlow_Increment"))
       return false;

    if(jsonObject.get("stockFlowId_StockFlow_Increment") == null)
       return false;

    if(!(jsonObject.get("stockFlowId_StockFlow_Increment") instanceof String))
       return false;

    if(!jsonObject.containsKey("dualityId_Duality_Increment"))
       return false;

    if(jsonObject.get("dualityId_Duality_Increment") == null)
       return false;

    if(!(jsonObject.get("dualityId_Duality_Increment") instanceof String))
       return false;

    if(!jsonObject.containsKey("participationId_Participation_Increment"))
       return false;

    if(jsonObject.get("participationId_Participation_Increment") == null)
       return false;

    if(!(jsonObject.get("participationId_Participation_Increment") instanceof String))
       return false;

    if(!jsonObject.containsKey("ownershipId_Ownership_Increment"))
       return false;

    if(jsonObject.get("ownershipId_Ownership_Increment") == null)
       return false;

    if(!(jsonObject.get("ownershipId_Ownership_Increment") instanceof String))
       return false;

    return true;
  }

}