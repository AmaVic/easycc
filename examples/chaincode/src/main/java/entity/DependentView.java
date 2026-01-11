package entity;


import com.owlike.genson.annotation.JsonCreator;
import com.owlike.genson.annotation.JsonProperty;

import runtime.BusinessObject;

import state.dependentView.DependentViewState;
import state.dependentView.DependentViewAllocatedState;

public class DependentView extends BusinessObject {
   //=========== Attributes ===========
  private final boolean isParticipant = false;
  private DependentViewState currentState;
  private String Name;
  private final String incrementId_Increment_DependentView;
  private final String decrementId_Decrement_DependentView;

  //========== Constructors ==========
  /**
   * Creates a new DependentView with a Generated ID and Default State: Allocated
   */
  @JsonCreator
  public DependentView(@JsonProperty("Name") String Name, @JsonProperty("incrementId_Increment_DependentView") String incrementId_Increment_DependentView, @JsonProperty("decrementId_Decrement_DependentView") String decrementId_Decrement_DependentView) {
    super();
    this.currentState = new DependentViewAllocatedState();
    this.Name = Name;
    this.incrementId_Increment_DependentView = incrementId_Increment_DependentView;
    this.decrementId_Decrement_DependentView = decrementId_Decrement_DependentView;
  }

  public DependentView(@JsonProperty("id") String id, @JsonProperty("currentState") DependentViewState currentState, @JsonProperty("Name") String Name, @JsonProperty("incrementId_Increment_DependentView") String incrementId_Increment_DependentView, @JsonProperty("decrementId_Decrement_DependentView") String decrementId_Decrement_DependentView) {
    super(id);
    this.currentState = currentState;
    this.Name = Name;
    this.incrementId_Increment_DependentView = incrementId_Increment_DependentView;
    this.decrementId_Decrement_DependentView = decrementId_Decrement_DependentView;
  }

  //============ Getters =============
  @Override
  public boolean isParticipant() { return this.isParticipant; }
  @Override
  public String getPublicKey() { return null; }
  public DependentViewState getCurrentState() { return this.currentState; }
  public String getName() { return this.Name; }
  public String getIncrementId_Increment_DependentView() { return this.incrementId_Increment_DependentView; }
  public String getDecrementId_Decrement_DependentView() { return this.decrementId_Decrement_DependentView; }

  //============ Setters =============
  public void setCurrentState(DependentViewState currentState) { this.currentState = currentState; }
  public void setName(String Name) { this.Name = Name; }

  //===== JSON (De)serialization =====
  public String toJsonString() { return genson.serialize(this); }
  public static DependentView fromJson(String json) { return genson.deserialize(json, DependentView.class); }

    //20230803 Manual Modification to allow for uniqueness checking
    public String getUniquenessQuery(){
      String query = "{\"selector\":{\"incrementId_Increment_DependentView\":\""+this.incrementId_Increment_DependentView
      +"\", \"decrementId_Decrement_DependentView\":\""+this.decrementId_Decrement_DependentView+"\"}}";
      return query;
    }
}