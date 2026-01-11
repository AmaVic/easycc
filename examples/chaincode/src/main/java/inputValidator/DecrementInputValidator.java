package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class DecrementInputValidator {
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

    if(!jsonObject.containsKey("Cost"))
       return false;

    if(jsonObject.get("Cost") == null)
       return false;

    if(!(jsonObject.get("Cost") instanceof String))
       return false;

    if(!jsonObject.containsKey("stockFlowId_StockFlow_Decrement"))
       return false;

    if(jsonObject.get("stockFlowId_StockFlow_Decrement") == null)
       return false;

    if(!(jsonObject.get("stockFlowId_StockFlow_Decrement") instanceof String))
       return false;

    if(!jsonObject.containsKey("dualityId_Duality_Decrement"))
       return false;

    if(jsonObject.get("dualityId_Duality_Decrement") == null)
       return false;

    if(!(jsonObject.get("dualityId_Duality_Decrement") instanceof String))
       return false;

    if(!jsonObject.containsKey("participationId_Participation_Decrement"))
       return false;

    if(jsonObject.get("participationId_Participation_Decrement") == null)
       return false;

    if(!(jsonObject.get("participationId_Participation_Decrement") instanceof String))
       return false;

    if(!jsonObject.containsKey("ownershipId_Ownership_Decrement"))
       return false;

    if(jsonObject.get("ownershipId_Ownership_Decrement") == null)
       return false;

    if(!(jsonObject.get("ownershipId_Ownership_Decrement") instanceof String))
       return false;

    return true;
  }

}