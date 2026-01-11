package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.duality.DualityState;
import state.duality.DualityAllocatedState;

public class Duality extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private DualityState currentState;
  private String Name;
  private final String economicEventId_Settle_Duality;
  private final String economicEventId_Claim_Duality;

  //========== Constructors ==========
  /**
   * Creates a new Duality with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public Duality(@JsonProperty("Name") String Name, @JsonProperty("economicEventId_Settle_Duality") String economicEventId_Settle_Duality, @JsonProperty("economicEventId_Claim_Duality") String economicEventId_Claim_Duality) {
    super();
    this.currentState = new DualityAllocatedState();
    this.Name = Name;
    this.economicEventId_Settle_Duality = economicEventId_Settle_Duality;
    this.economicEventId_Claim_Duality = economicEventId_Claim_Duality;
  }

  public Duality(@JsonProperty("id") String id, @JsonProperty("currentState") DualityState currentState, @JsonProperty("Name") String Name, @JsonProperty("economicEventId_Settle_Duality") String economicEventId_Settle_Duality, @JsonProperty("economicEventId_Claim_Duality") String economicEventId_Claim_Duality) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
    this.economicEventId_Settle_Duality = economicEventId_Settle_Duality;
    this.economicEventId_Claim_Duality = economicEventId_Claim_Duality;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public DualityState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }
  public String getEconomicEventId_Settle_Duality() { return this.economicEventId_Settle_Duality; }
  public String getEconomicEventId_Claim_Duality() { return this.economicEventId_Claim_Duality; }

  //============ Setters =============
  public void setCurrentState(DualityState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static Duality fromJson(String json) { return genson.deserialize(json, Duality.class); }

  //20230803 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
    String query = "{\"selector\":{\"economicEventId_Settle_Duality\":\""+this.economicEventId_Settle_Duality
    +"\", \"economicEventId_Claim_Duality\":\""+this.economicEventId_Claim_Duality+"\"}}";
    return query;
  }
}