package entity;

import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.stockFlow.StockFlowState;
import state.stockFlow.StockFlowAllocatedState;

public class StockFlow extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private StockFlowState currentState;
  private Float MarketValue;
  private Integer Quantity;
  private String Name;
  private final String economicResourceId_EconomicResource_StockFlow;
  private final String economicEventId_EconomicEvent_StockFlow;

  //========== Constructors ==========
  /**
   * Creates a new StockFlow with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public StockFlow(@JsonProperty("MarketValue") Float MarketValue, @JsonProperty("Quantity") Integer Quantity, @JsonProperty("Name") String Name, @JsonProperty("economicResourceId_EconomicResource_StockFlow") String economicResourceId_EconomicResource_StockFlow, @JsonProperty("economicEventId_EconomicEvent_StockFlow") String economicEventId_EconomicEvent_StockFlow) {
    super();
    this.currentState = new StockFlowAllocatedState();
    this.MarketValue = MarketValue;
    this.Quantity = Quantity;
    this.Name = Name;
    this.economicResourceId_EconomicResource_StockFlow = economicResourceId_EconomicResource_StockFlow;
    this.economicEventId_EconomicEvent_StockFlow = economicEventId_EconomicEvent_StockFlow;
  }

  public StockFlow(@JsonProperty("id") String id, @JsonProperty("currentState") StockFlowState currentState, @JsonProperty("MarketValue") Float MarketValue, @JsonProperty("Quantity") Integer Quantity, @JsonProperty("Name") String Name, @JsonProperty("economicResourceId_EconomicResource_StockFlow") String economicResourceId_EconomicResource_StockFlow, @JsonProperty("economicEventId_EconomicEvent_StockFlow") String economicEventId_EconomicEvent_StockFlow) {
    super(id);
    this.currentState = currentState;
    this.MarketValue = MarketValue;
    this.Quantity = Quantity;
    this.Name = Name;
    this.economicResourceId_EconomicResource_StockFlow = economicResourceId_EconomicResource_StockFlow;
    this.economicEventId_EconomicEvent_StockFlow = economicEventId_EconomicEvent_StockFlow;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public StockFlowState getCurrentState() { return this.currentState; }
  public Float getMarketValue() { return this.MarketValue; }
  public Integer getQuantity() { return this.Quantity; }
  public String getName() { return this.Name; }
  public String getEconomicResourceId_EconomicResource_StockFlow() { return this.economicResourceId_EconomicResource_StockFlow; }
  public String getEconomicEventId_EconomicEvent_StockFlow() { return this.economicEventId_EconomicEvent_StockFlow; }

  //============ Setters =============
  public void setCurrentState(StockFlowState currentState) { this.currentState = currentState; }
  public void setMarketValue(Float MarketValue) { this.MarketValue = MarketValue; }
  public void setQuantity(Integer Quantity) { this.Quantity = Quantity; }
  public void setName(String Name) { this.Name = Name; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static StockFlow fromJson(String json) { return genson.deserialize(json, StockFlow.class); }

  //20230802 Manual Modification to allow for uniqueness checking
  public String getUniquenessQuery(){
      String query = "{\"selector\":{\"economicResourceId_EconomicResource_StockFlow\":\""+this.economicResourceId_EconomicResource_StockFlow
      +"\", \"economicEventId_EconomicEvent_StockFlow\":\""+this.economicEventId_EconomicEvent_StockFlow+"\"}}";
      return query;
  }
}