package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.economicResource.EconomicResourceState;
import state.economicResource.EconomicResourceAllocatedState;

public class EconomicResource extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private EconomicResourceState currentState;
  private String Name;

  //========== Constructors ==========
  /**
   * Creates a new EconomicResource with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public EconomicResource(@JsonProperty("Name") String Name) {
    super();
    this.currentState = new EconomicResourceAllocatedState();
    this.Name = Name;
  }

  public EconomicResource(@JsonProperty("id") String id, @JsonProperty("currentState") EconomicResourceState currentState, @JsonProperty("Name") String Name) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public EconomicResourceState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }

  //============ Setters =============
  public void setCurrentState(EconomicResourceState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static EconomicResource fromJson(String json) { return genson.deserialize(json, EconomicResource.class); }

  //20230802 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
    String query = "{\"selector\":{\"shouldNotYieldAResult\":\"shouldNotYieldAResult\"}}";
    return query;
  }
}