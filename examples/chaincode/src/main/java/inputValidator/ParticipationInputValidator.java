package inputValidator;

import com.owlike.genson.Genson;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


public class ParticipationInputValidator {
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

    if(!jsonObject.containsKey("economicEventId_EconomicEvent_Participation"))
       return false;

    if(jsonObject.get("economicEventId_EconomicEvent_Participation") == null)
       return false;

    if(!(jsonObject.get("economicEventId_EconomicEvent_Participation") instanceof String))
       return false;

    if(!jsonObject.containsKey("economicAgentId_EconomicAgent_Participation"))
       return false;

    if(jsonObject.get("economicAgentId_EconomicAgent_Participation") == null)
       return false;

    if(!(jsonObject.get("economicAgentId_EconomicAgent_Participation") instanceof String))
       return false;

    return true;
  }

}