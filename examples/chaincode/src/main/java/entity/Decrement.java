package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.decrement.DecrementState;
import state.decrement.DecrementAllocatedState;

public class Decrement extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private DecrementState currentState;
  private String Name;
  private String Cost;
  private final String stockFlowId_StockFlow_Decrement;
  private final String dualityId_Duality_Decrement;
  private final String participationId_Participation_Decrement;
  private final String ownershipId_Ownership_Decrement;

  //========== Constructors ==========
  /**
   * Creates a new Decrement with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public Decrement(@JsonProperty("Name") String Name, @JsonProperty("Cost") String Cost, @JsonProperty("stockFlowId_StockFlow_Decrement") String stockFlowId_StockFlow_Decrement, @JsonProperty("dualityId_Duality_Decrement") String dualityId_Duality_Decrement, @JsonProperty("participationId_Participation_Decrement") String participationId_Participation_Decrement, @JsonProperty("ownershipId_Ownership_Decrement") String ownershipId_Ownership_Decrement) {
    super();
    this.currentState = new DecrementAllocatedState();
    this.Name = Name;
    this.Cost = Cost;
    this.stockFlowId_StockFlow_Decrement = stockFlowId_StockFlow_Decrement;
    this.dualityId_Duality_Decrement = dualityId_Duality_Decrement;
    this.participationId_Participation_Decrement = participationId_Participation_Decrement;
    this.ownershipId_Ownership_Decrement = ownershipId_Ownership_Decrement;
  }

  public Decrement(@JsonProperty("id") String id, @JsonProperty("currentState") DecrementState currentState, @JsonProperty("Name") String Name, @JsonProperty("Cost") String Cost, @JsonProperty("stockFlowId_StockFlow_Decrement") String stockFlowId_StockFlow_Decrement, @JsonProperty("dualityId_Duality_Decrement") String dualityId_Duality_Decrement, @JsonProperty("participationId_Participation_Decrement") String participationId_Participation_Decrement, @JsonProperty("ownershipId_Ownership_Decrement") String ownershipId_Ownership_Decrement) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
    this.Cost = Cost;
    this.stockFlowId_StockFlow_Decrement = stockFlowId_StockFlow_Decrement;
    this.dualityId_Duality_Decrement = dualityId_Duality_Decrement;
    this.participationId_Participation_Decrement = participationId_Participation_Decrement;
    this.ownershipId_Ownership_Decrement = ownershipId_Ownership_Decrement;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public DecrementState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }
  public String getCost() { return this.Cost; }
  public String getStockFlowId_StockFlow_Decrement() { return this.stockFlowId_StockFlow_Decrement; }
  public String getDualityId_Duality_Decrement() { return this.dualityId_Duality_Decrement; }
  public String getParticipationId_Participation_Decrement() { return this.participationId_Participation_Decrement; }
  public String getOwnershipId_Ownership_Decrement() { return this.ownershipId_Ownership_Decrement; }

  //============ Setters =============
  public void setCurrentState(DecrementState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }
  public void setCost(String Cost) { this.Cost = Cost; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static Decrement fromJson(String json) { return genson.deserialize(json, Decrement.class); }

    //20230803 Manual Modification to allow for uniqueness checking
    public String getUniquenessQuery(){
      String query = "{\"selector\":{\"stockFlowId_StockFlow_Decrement\":\""+this.stockFlowId_StockFlow_Decrement
        +"\", \"dualityId_Duality_Decrement\":\""+this.dualityId_Duality_Decrement
        +"\",\"participationId_Participation_Decrement\":\""+this.participationId_Participation_Decrement
        +"\",\"ownershipId_Ownership_Decrement\":\""+this.ownershipId_Ownership_Decrement+"\"}}";
      return query;
    }
}