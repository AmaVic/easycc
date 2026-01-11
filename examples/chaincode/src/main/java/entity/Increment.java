package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.increment.IncrementState;
import state.increment.IncrementAllocatedState;

public class Increment extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private IncrementState currentState;
  private String Name;
  private Float EarnedValue;
  private final String stockFlowId_StockFlow_Increment;
  private final String dualityId_Duality_Increment;
  private final String participationId_Participation_Increment;
  private final String ownershipId_Ownership_Increment;

  //========== Constructors ==========
  /**
   * Creates a new Increment with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public Increment(@JsonProperty("Name") String Name, @JsonProperty("EarnedValue") Float EarnedValue, @JsonProperty("stockFlowId_StockFlow_Increment") String stockFlowId_StockFlow_Increment, @JsonProperty("dualityId_Duality_Increment") String dualityId_Duality_Increment, @JsonProperty("participationId_Participation_Increment") String participationId_Participation_Increment, @JsonProperty("ownershipId_Ownership_Increment") String ownershipId_Ownership_Increment) {
    super();
    this.currentState = new IncrementAllocatedState();
    this.Name = Name;
    this.EarnedValue = EarnedValue;
    this.stockFlowId_StockFlow_Increment = stockFlowId_StockFlow_Increment;
    this.dualityId_Duality_Increment = dualityId_Duality_Increment;
    this.participationId_Participation_Increment = participationId_Participation_Increment;
    this.ownershipId_Ownership_Increment = ownershipId_Ownership_Increment;
  }

  public Increment(@JsonProperty("id") String id, @JsonProperty("currentState") IncrementState currentState, @JsonProperty("Name") String Name, @JsonProperty("EarnedValue") Float EarnedValue, @JsonProperty("stockFlowId_StockFlow_Increment") String stockFlowId_StockFlow_Increment, @JsonProperty("dualityId_Duality_Increment") String dualityId_Duality_Increment, @JsonProperty("participationId_Participation_Increment") String participationId_Participation_Increment, @JsonProperty("ownershipId_Ownership_Increment") String ownershipId_Ownership_Increment) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
    this.EarnedValue = EarnedValue;
    this.stockFlowId_StockFlow_Increment = stockFlowId_StockFlow_Increment;
    this.dualityId_Duality_Increment = dualityId_Duality_Increment;
    this.participationId_Participation_Increment = participationId_Participation_Increment;
    this.ownershipId_Ownership_Increment = ownershipId_Ownership_Increment;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public IncrementState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }
  public Float getEarnedValue() { return this.EarnedValue; }
  public String getStockFlowId_StockFlow_Increment() { return this.stockFlowId_StockFlow_Increment; }
  public String getDualityId_Duality_Increment() { return this.dualityId_Duality_Increment; }
  public String getParticipationId_Participation_Increment() { return this.participationId_Participation_Increment; }
  public String getOwnershipId_Ownership_Increment() { return this.ownershipId_Ownership_Increment; }

  //============ Setters =============
  public void setCurrentState(IncrementState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }
  public void setEarnedValue(Float EarnedValue) { this.EarnedValue = EarnedValue; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static Increment fromJson(String json) { return genson.deserialize(json, Increment.class); }

  //20230803 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
    String query = "{\"selector\":{\"stockFlowId_StockFlow_Increment\":\""+this.stockFlowId_StockFlow_Increment
      +"\", \"dualityId_Duality_Increment\":\""+this.dualityId_Duality_Increment
      +"\",\"participationId_Participation_Increment\":\""+this.participationId_Participation_Increment
      +"\",\"ownershipId_Ownership_Increment\":\""+this.ownershipId_Ownership_Increment+"\"}}";
    return query;
  }
}