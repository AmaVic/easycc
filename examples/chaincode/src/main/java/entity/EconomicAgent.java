package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.economicAgent.EconomicAgentState;
import state.economicAgent.EconomicAgentAllocatedState;

public class EconomicAgent extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = true;
  private String publicKey;
  private EconomicAgentState currentState;
  private String Name;

  //========== Constructors ==========
  /**
   * Creates a new EconomicAgent with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public EconomicAgent(@JsonProperty("Name") String Name, @JsonProperty("publicKey") String publicKey) {
    super();
    this.currentState = new EconomicAgentAllocatedState();
    this.Name = Name;
    this.publicKey = publicKey;
  }

  public EconomicAgent(@JsonProperty("id") String id, @JsonProperty("currentState") EconomicAgentState currentState, @JsonProperty("Name") String Name, @JsonProperty("publicKey") String publicKey) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
    this.publicKey = publicKey;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return this.publicKey; }
  public EconomicAgentState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }

  //============ Setters =============
  public void setCurrentState(EconomicAgentState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }
  public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static EconomicAgent fromJson(String json) { return genson.deserialize(json, EconomicAgent.class); }

  //20230802 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
    String query = "{\"selector\":{\"shouldNotYieldAResult\":\"shouldNotYieldAResult\"}}";
    return query;
  }
}