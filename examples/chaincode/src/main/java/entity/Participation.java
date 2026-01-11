package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.participation.ParticipationState;
import state.participation.ParticipationAllocatedState;

public class Participation extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private ParticipationState currentState;
  private String Name;
  private final String economicEventId_EconomicEvent_Participation;
  private final String economicAgentId_EconomicAgent_Participation;

  //========== Constructors ==========
  /**
   * Creates a new Participation with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public Participation(@JsonProperty("Name") String Name, @JsonProperty("economicEventId_EconomicEvent_Participation") String economicEventId_EconomicEvent_Participation, @JsonProperty("economicAgentId_EconomicAgent_Participation") String economicAgentId_EconomicAgent_Participation) {
    super();
    this.currentState = new ParticipationAllocatedState();
    this.Name = Name;
    this.economicEventId_EconomicEvent_Participation = economicEventId_EconomicEvent_Participation;
    this.economicAgentId_EconomicAgent_Participation = economicAgentId_EconomicAgent_Participation;
  }

  public Participation(@JsonProperty("id") String id, @JsonProperty("currentState") ParticipationState currentState, @JsonProperty("Name") String Name, @JsonProperty("economicEventId_EconomicEvent_Participation") String economicEventId_EconomicEvent_Participation, @JsonProperty("economicAgentId_EconomicAgent_Participation") String economicAgentId_EconomicAgent_Participation) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
    this.economicEventId_EconomicEvent_Participation = economicEventId_EconomicEvent_Participation;
    this.economicAgentId_EconomicAgent_Participation = economicAgentId_EconomicAgent_Participation;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public ParticipationState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }
  public String getEconomicEventId_EconomicEvent_Participation() { return this.economicEventId_EconomicEvent_Participation; }
  public String getEconomicAgentId_EconomicAgent_Participation() { return this.economicAgentId_EconomicAgent_Participation; }

  //============ Setters =============
  public void setCurrentState(ParticipationState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static Participation fromJson(String json) { return genson.deserialize(json, Participation.class); }

  //20230803 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
    String query = "{\"selector\":{\"economicEventId_EconomicEvent_Participation\":\""+this.economicEventId_EconomicEvent_Participation
    +"\", \"economicAgentId_EconomicAgent_Participation\":\""+this.economicAgentId_EconomicAgent_Participation+"\"}}";
    return query;
  }
}