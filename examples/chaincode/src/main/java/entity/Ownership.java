package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.ownership.OwnershipState;
import state.ownership.OwnershipAllocatedState;

public class Ownership extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private OwnershipState currentState;
  private String Name;
  private final String economicResourceId_EconomicResource_Ownership;
  private final String economicAgentId_EconomicAgent_Ownership;

  //========== Constructors ==========
  /**
   * Creates a new Ownership with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public Ownership(@JsonProperty("Name") String Name, @JsonProperty("economicResourceId_EconomicResource_Ownership") String economicResourceId_EconomicResource_Ownership, @JsonProperty("economicAgentId_EconomicAgent_Ownership") String economicAgentId_EconomicAgent_Ownership) {
    super();
    this.currentState = new OwnershipAllocatedState();
    this.Name = Name;
    this.economicResourceId_EconomicResource_Ownership = economicResourceId_EconomicResource_Ownership;
    this.economicAgentId_EconomicAgent_Ownership = economicAgentId_EconomicAgent_Ownership;
  }

  public Ownership(@JsonProperty("id") String id, @JsonProperty("currentState") OwnershipState currentState, @JsonProperty("Name") String Name, @JsonProperty("economicResourceId_EconomicResource_Ownership") String economicResourceId_EconomicResource_Ownership, @JsonProperty("economicAgentId_EconomicAgent_Ownership") String economicAgentId_EconomicAgent_Ownership) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
    this.economicResourceId_EconomicResource_Ownership = economicResourceId_EconomicResource_Ownership;
    this.economicAgentId_EconomicAgent_Ownership = economicAgentId_EconomicAgent_Ownership;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public OwnershipState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }
  public String getEconomicResourceId_EconomicResource_Ownership() { return this.economicResourceId_EconomicResource_Ownership; }
  public String getEconomicAgentId_EconomicAgent_Ownership() { return this.economicAgentId_EconomicAgent_Ownership; }

  //============ Setters =============
  public void setCurrentState(OwnershipState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static Ownership fromJson(String json) { return genson.deserialize(json, Ownership.class); }

  //20230802 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
    String query = "{\"selector\":{\"economicResourceId_EconomicResource_Ownership\":\""+this.economicResourceId_EconomicResource_Ownership
    +"\", \"economicAgentId_EconomicAgent_Ownership\":\""+this.economicAgentId_EconomicAgent_Ownership+"\"}}";
    return query;
  }
}