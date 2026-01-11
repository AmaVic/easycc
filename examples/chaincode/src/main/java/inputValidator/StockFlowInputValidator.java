package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class StockFlowInputValidator {
  private static final Genson genson = new Genson();

  @SuppressWarnings("unchecked")
  public static boolean isValidForCreation(String json) {
    HashMap<String, Object> jsonObject = (HashMap<String, Object>)genson.deserialize(json, Map.class);

    if(!jsonObject.containsKey("MarketValue"))
       return false;

    if(jsonObject.get("MarketValue") == null)
       return false;

    if(!(jsonObject.get("MarketValue") instanceof Float))
       return false;

    if(!jsonObject.containsKey("Quantity"))
       return false;

    if(jsonObject.get("Quantity") == null)
       return false;

    if(!(jsonObject.get("Quantity") instanceof Integer))
       return false;

    if(!jsonObject.containsKey("Name"))
       return false;

    if(jsonObject.get("Name") == null)
       return false;

    if(!(jsonObject.get("Name") instanceof String))
       return false;

    if(!jsonObject.containsKey("economicResourceId_EconomicResource_StockFlow"))
       return false;

    if(jsonObject.get("economicResourceId_EconomicResource_StockFlow") == null)
       return false;

    if(!(jsonObject.get("economicResourceId_EconomicResource_StockFlow") instanceof String))
       return false;

    if(!jsonObject.containsKey("economicEventId_EconomicEvent_StockFlow"))
       return false;

    if(jsonObject.get("economicEventId_EconomicEvent_StockFlow") == null)
       return false;

    if(!(jsonObject.get("economicEventId_EconomicEvent_StockFlow") instanceof String))
       return false;

    return true;
  }

}